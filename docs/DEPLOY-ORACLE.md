# Deploying to Oracle Cloud Free Tier

This guide walks through hosting your Biff app (with XTDB) on Oracle Cloud Always Free. The app and XTDB run on one VM; XTDB uses the default **standalone** topology and stores data under `storage/xtdb`.

**If Oracle won’t let you sign up,** use **[DEPLOY-FREE-ALTERNATIVES.md](DEPLOY-FREE-ALTERNATIVES.md)** for DigitalOcean (recommended), Google Cloud, or AWS.

---

## 1. Oracle Cloud setup

### Create an account

1. Go to [Oracle Cloud Free Tier](https://www.oracle.com/cloud/free/).
2. Sign up (credit card may be required for verification; Always Free resources are not charged).

### Create a compute instance (VM)

1. In the Oracle Cloud Console, open **Menu** → **Compute** → **Instances**.
2. Click **Create instance**.
3. **Name:** e.g. `greed-app`.
4. **Placement:** keep default (your home region).
5. **Image and shape:**
   - **Image:** Pick **Ubuntu 22.04** (or latest LTS).
   - **Shape:** **Change shape** → **Ampere** → **VM.Standard.A1.Flex** (Always Free).
     - OCPUs: **2**, Memory: **12 GB** (fits in free tier and is more comfortable for JVM + XTDB).
     - Alternatively, **AMD** → **VM.Standard.E2.1.Micro** (1 OCPU, 1 GB RAM) is the minimum; use it only for light testing.
6. **Networking:** Create new VCN or use existing. Ensure **Assign a public IPv4 address** is checked.
7. **Add SSH keys:** Upload your public key or paste it (e.g. `~/.ssh/id_rsa.pub` or `~/.ssh/id_ed25519.pub`).
8. Click **Create**.

Wait for the instance to reach **Running**. Note the **Public IP address**.

---

## 2. Open HTTP/HTTPS in the firewall

Oracle uses a **security list** (and optionally **iptables** on the instance). Open ports so nginx and certbot can serve HTTP/HTTPS.

1. In the console, go to your instance → **Subnet** (under Primary VNIC).
2. Open the **Security List** for that subnet.
3. **Add Ingress Rule:**
   - **Source:** `0.0.0.0/0`
   - **IP Protocol:** TCP
   - **Destination port range:** `80`
   - **Description:** HTTP
4. Add another **Ingress Rule** for port **443** (HTTPS), same source and protocol.
5. Ensure port **22** (SSH) is allowed (default admin list often includes it).

---

## 3. Domain and DNS

### If you have a domain

1. Point your domain (or subdomain) to the VM’s **Public IP** with an **A record**.
   - Example: `app.yourdomain.com` → `<public-ip>`.
2. Wait for DNS to propagate (a few minutes to an hour).

### If you don’t have a domain: use a free hostname (recommended)

You can get a **free hostname that points to your VM’s IP** — no signup, no DNS setup.

- **[nip.io](https://nip.io):** `<your-public-ip>.nip.io` → resolves to `<your-public-ip>`  
  Example: VM at `123.45.67.89` → use `123.45.67.89.nip.io`
- **[sslip.io](https://sslip.io):** same idea, e.g. `123.45.67.89.sslip.io`

Use that hostname everywhere you’d use a domain:

- In **config.env:** `DOMAIN=123.45.67.89.nip.io` (replace with your VM’s public IP).
- When **certbot** runs during server setup (step 5), enter that same hostname (e.g. `123.45.67.89.nip.io`).
- In the browser: `https://123.45.67.89.nip.io`

You get a valid hostname and **HTTPS** (Let’s Encrypt works with nip.io/sslip.io). No need to buy a domain.

---

## 4. Prepare `config.env` locally

1. If you don’t have `config.env`, run:
   ```bash
   clj -M:dev generate-config
   ```
2. Edit `config.env` and set at least:
   - `DOMAIN` = the host for your app: either your real domain (e.g. `app.yourdomain.com`) or your nip.io/sslip.io hostname (e.g. `123.45.67.89.nip.io`).
   - Any secrets your app needs: `COOKIE_SECRET`, `JWT_SECRET`, and (if you use them) `MAILERSEND_*`, `RECAPTCHA_*`, etc.

Do **not** set `PROD_XTDB_TOPOLOGY` or `XTDB_JDBC_URL`; XTDB will use standalone and `storage/xtdb` on the server.

---

## 5. Run server setup on the VM

From your project root (where `server-setup.sh` lives):

```bash
# Copy the setup script to the server (use your domain or public IP)
scp server-setup.sh root@<your-domain-or-public-ip>:

# SSH in and run it
ssh root@<your-domain-or-public-ip>
```

On the server:

```bash
bash server-setup.sh
```

- Answer the prompts. When **certbot** runs, enter the **same domain** you set in `config.env` (e.g. `app.yourdomain.com`).
- When the script finishes, reboot:

  ```bash
  reboot
  ```

Wait a minute, then SSH again as **root** to confirm you can still log in. The script creates an `app` user; deploys will use that.

---

## 6. (Optional) Git remote for deploy

If you don’t have `rsync` on your machine, Biff will use git to deploy. Add the prod remote (use your real domain):

```bash
git remote add prod ssh://app@<your-domain>/home/app/repo.git
```

If your default branch is `main` instead of `master`, edit `resources/config.edn` and set:

```clojure
:biff.tasks/git-deploy-cmd ["git" "push" "prod" "main:master"]
```

---

## 7. Deploy the app

From your **local** machine, in the project root:

```bash
# Ensure config.env has DOMAIN and secrets, then:
clj -M:dev deploy
```

When prompted, use the **app** user (e.g. `app@<your-domain>`), not root. The task will copy your code and config and restart the app on the server.

---

## 8. Verify

1. **Logs:**
   ```bash
   clj -M:dev logs
   ```
   Wait until you see something like “System started” or your app’s startup message.

2. **Browser:** Open `https://<your-domain>`. You should see your app; HTTPS is handled by nginx + Let’s Encrypt from the setup script.

---

## 9. XTDB and backups

- **XTDB** runs in-process with your app and uses **standalone** topology. Data is stored under `/home/app/storage/xtdb` on the VM.
- For a bit of safety, add a simple backup (e.g. cron on the server that tars or rsyncs `/home/app/storage/xtdb` to another disk or object storage). Oracle Object Storage has a free tier if you want off-server backups.

---

## 10. Useful commands

| Task              | Command                    |
|-------------------|----------------------------|
| Deploy            | `clj -M:dev deploy`        |
| View logs         | `clj -M:dev logs`          |
| SSH as app user   | `ssh app@<your-domain>`    |
| Restart app       | `ssh app@<your-domain> 'sudo systemctl restart app'` |

---

## Troubleshooting

- **502 Bad Gateway:** App may still be starting (JVM can take 30–60 s). Check `clj -M:dev logs` and `ssh app@<domain> 'sudo journalctl -u app -f'`.
- **Certificate errors:** Ensure the hostname you use (domain or nip.io/sslip.io) matches what you gave to certbot and that ports 80 and 443 are open in the security list.
- **No domain / only an IP:** Use a free hostname like `<your-ip>.nip.io` so certbot can issue a cert and your app can use HTTPS. Using the raw IP with HTTPS is not supported by Let’s Encrypt.
- **Out of memory:** If you used the 1 GB AMD shape, consider recreating the instance with the Ampere A1.Flex shape (e.g. 2 OCPUs, 12 GB RAM) for more headroom.
