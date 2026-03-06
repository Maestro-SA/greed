# Data and application security (overview)

This is an overview of how secure your data and app are in the current setup (Biff + XTDB on AWS). No code or config changes are required; this is for your awareness.

---

## 1. Data in transit (browser ↔ server)

- **HTTPS:** You use nginx + Let’s Encrypt and `DOMAIN=...nip.io`, so traffic between users’ browsers and your server is encrypted (TLS). Passwords, session cookies, and all request/response data are protected in transit.
- **Prod middleware:** `:biff.middleware/secure` is `true` in prod, so the app expects HTTPS and can enforce secure cookies.

**Summary:** Traffic to and from the app is encrypted. Good.

---

## 2. Data at rest (on the server)

- **XTDB storage:** Data lives under `/home/app/storage/xtdb` (files on the EC2 instance’s disk). XTDB does **not** encrypt this data; it’s stored in plain form.
- **Disk encryption:** By default, EC2 instance storage is **not** encrypted. If someone gets access to the volume (e.g. via the cloud provider or a stolen snapshot), they can read the files. You can enable encryption (e.g. AWS EBS encryption) when creating the volume or the instance for an extra layer of protection.
- **Passwords:** Your app stores user passwords in XTDB. Biff’s auth typically hashes them (e.g. bcrypt); the hash is what’s at rest. If the disk is compromised, an attacker could try to crack those hashes, but not see the raw passwords.

**Summary:** Data at rest is not encrypted by the app; disk is only encrypted if you turned on EBS encryption. Passwords are stored as hashes.

---

## 3. Who can access what

- **Application access:** Only signed-in users (via your Biff auth and session) can use the app. Routes are protected by middleware (e.g. `wrap-signed-in`). Session is tied to a signed cookie (using `COOKIE_SECRET`). So “data security” for users is: only they (and anyone with their session) can see their own data as exposed by your UI and API.
- **Database access:** XTDB is not exposed on a separate port. Only the Clojure app process reads/writes it. So there is no direct “database port” for the internet to hit.
- **Server access:** Only people with (1) your SSH private key (for `ubuntu`/`app`) or (2) your AWS console credentials can get into the VM or the AWS account. If someone has the key or the console, they can read everything on the server (code, config, XTDB files, env vars).

**Summary:** Access is controlled by login and sessions; the DB is not publicly exposed; server access is limited to SSH (and AWS) and is as secure as your keys and account.

---

## 4. Secrets (passwords, API keys, signing keys)

- **Where they live:** `config.env` (and env vars on the server) hold `COOKIE_SECRET`, `JWT_SECRET`, and any API keys (e.g. MailerSend, reCAPTCHA). That file is deployed to the server (in `:biff.tasks/deploy-untracked-files`), so secrets sit on the VM.
- **How they’re used:** Cookie and JWT secrets sign session and token data; they are not sent to the browser. If an attacker gets the server (or a copy of `config.env`), they can forge sessions and impersonate users until you rotate those secrets and redeploy.
- **Best practice:** Keep `config.env` out of git (it is in `.gitignore` in typical Biff setups). Restrict who has SSH and AWS access. For higher sensitivity, use a secrets manager (e.g. AWS Secrets Manager) and inject env vars at runtime instead of deploying `config.env` as a file.

**Summary:** Secrets are on the server and in `config.env`; protect the server and the file. No automatic rotation unless you add it.

---

## 5. Things that improve security (optional)

- **EBS encryption:** Turn on encryption for the instance’s root (and any extra) volume so the disk is encrypted at rest.
- **Backups:** Back up `/home/app/storage/xtdb` (and optionally `config.env`) to a separate, access-controlled location (e.g. S3 with restricted bucket policy). Improves recovery and can help limit damage from a single compromised server.
- **Updates:** Keep the OS and the JVM/dependencies updated so you get security patches.
- **Narrow SSH:** In the security group, restrict SSH (port 22) to “My IP” or a small set of IPs instead of 0.0.0.0 if you don’t need SSH from anywhere.
- **Secrets manager:** For stricter control, store secrets in AWS Secrets Manager (or similar) and load them into the app’s environment instead of deploying `config.env`.

---

## 6. Short summary

| Aspect              | How secure / what to know |
|---------------------|----------------------------|
| **In transit**      | HTTPS (TLS) via nginx + Let’s Encrypt. Traffic is encrypted. |
| **At rest**         | XTDB data and config on disk are not encrypted by the app. Use EBS encryption if you want encrypted disk. Passwords are stored as hashes. |
| **App access**      | Only signed-in users; sessions and routes are protected. |
| **DB access**       | No public DB port; only the app process uses XTDB. |
| **Server access**   | Whoever has your SSH key or AWS console can access everything on the server. |
| **Secrets**         | Stored on the server in `config.env`; protect the server and restrict who can deploy. |

For a typical small app with moderate sensitivity, this setup is **reasonably secure** if you protect SSH keys and AWS access, keep HTTPS, and (optionally) add EBS encryption and backups. For highly sensitive data (e.g. health, payments), you’d add encryption at rest, stricter secrets management, and possibly compliance-focused controls.
