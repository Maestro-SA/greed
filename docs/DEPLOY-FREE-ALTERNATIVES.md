# Free Alternatives for Deploying Your Biff App

If Oracle Cloud won’t let you sign up, use one of these. Each gives you an **Ubuntu VPS**; after you have the VM and open ports, the same Biff steps apply (domain/nip.io, `config.env`, `server-setup.sh`, deploy).

Your app and XTDB run on one VM; XTDB uses **standalone** topology and stores data under `storage/xtdb`.

---

## Option 1: DigitalOcean (recommended)

**Biff’s official recommendation.** You get **$200 free credit for 60 days** via a referral link (no ongoing free tier after that).

1. Sign up using a referral link that gives new users credit, e.g. the one from [Biff’s deploy tutorial](https://biffweb.com/docs/tutorial/deploy/): [DigitalOcean referral](https://m.do.co/c/141610534c91) ($200 credit for 60 days — check the link on biffweb.com for the current one).
2. **Create a Droplet:**
   - **Image:** Ubuntu 22.04 LTS
   - **Plan:** Basic, **Regular** (not Premium). Choose at least **1 GB RAM** (e.g. $6/mo); 2 GB is more comfortable for JVM + XTDB.
   - **Datacenter:** Any
   - **Authentication:** SSH key (add your public key)
3. Create the droplet and note the **IP address**.
4. **Firewall:** DigitalOcean droplets allow SSH (22) by default. Open **HTTP (80)** and **HTTPS (443):**
   - In the control panel: **Networking** → **Firewalls** → Create Firewall, or add rules to the droplet’s firewall so **Inbound** allows TCP 80 and 443 from `0.0.0.0/0` (and keep 22 for SSH).

Then go to [Common steps](#common-steps-all-providers) below.

---

## Option 2: Google Cloud

**$300 free credit for 90 days** for new accounts, or an **always-free e2-micro** VM (small; may be tight for JVM + XTDB).

1. Sign up at [Google Cloud](https://cloud.google.com/) (credit card required for verification; free tier doesn’t charge beyond the credit).
2. Create a project, then go to **Compute Engine** → **VM instances** → **Create instance**.
3. **Machine configuration:** **e2-micro** (0.25–1 vCPU, 1 GB RAM) for free tier, or a larger machine if you’re using the $300 credit.
4. **Boot disk:** Ubuntu 22.04 LTS.
5. **Firewall:** Allow **HTTP** and **HTTPS** (check the two boxes). SSH is usually allowed by default.
6. Create the instance and note the **External IP**.
7. If you use a **firewall / VPC** rules, ensure ingress TCP **22, 80, 443** from `0.0.0.0/0` are allowed.

Then go to [Common steps](#common-steps-all-providers) below.

---

## Option 3: AWS Free Tier

**12 months free** (after that you pay). Includes a **t2.micro** (1 vCPU, 1 GB RAM). You already have an AWS account, so start from step 2.

### Create the EC2 instance

1. In the [AWS Console](https://console.aws.amazon.com/), go to **EC2** → **Instances** → **Launch instance**.

2. **Name:** e.g. `greed-app`.

3. **Application and OS Images (AMI):** **Ubuntu**, then choose **Ubuntu Server 22.04 LTS**.

4. **Instance type:** **t2.micro** (Free tier eligible). 1 vCPU, 1 GB RAM — enough for Biff + XTDB; the JVM may take 30–60 seconds to start.

5. **Key pair (login):** **Create new key pair** (or use an existing one you have the `.pem` for).
   - Name it e.g. `greed-app-key`, type **RSA**, format **.pem**.
   - Download and store the `.pem` file. From a terminal: `chmod 400 path/to/greed-app-key.pem` so only you can read it.

6. **Network settings:** **Edit** and create / use a security group that allows:
   - **SSH (22)** — Source: **My IP** (or **Anywhere** `0.0.0.0/0` if you need to SSH from anywhere).
   - **HTTP (80)** — Source: **Anywhere** `0.0.0.0/0`.
   - **HTTPS (443)** — Source: **Anywhere** `0.0.0.0/0`.

7. **Storage:** Leave default (8 GB is enough).

8. Click **Launch instance**. Wait until the instance state is **Running**, then note its **Public IPv4 address** (e.g. `54.123.45.67`).

### Run server setup on the instance

On AWS, the default login user for Ubuntu is **ubuntu**, not root. Use your key and the instance’s public IP.

From your **project root** (where `server-setup.sh` is):

```bash
# Use your .pem key and the instance's public IP (no domain yet)
scp -i path/to/your-key.pem server-setup.sh ubuntu@<PUBLIC-IP>:
ssh -i path/to/your-key.pem ubuntu@<PUBLIC-IP>
```

On the **instance**, the setup script expects root’s SSH keys to exist so it can copy them for the `app` user. On Ubuntu AMI only `ubuntu` has the key, so do this once before running the script:

```bash
sudo mkdir -p /root/.ssh
sudo cp /home/ubuntu/.ssh/authorized_keys /root/.ssh/authorized_keys
sudo bash server-setup.sh
```

When **certbot** asks for the domain name, use your **nip.io** hostname (e.g. `54.123.45.67.nip.io` — replace with your instance’s real public IP). Then reboot:

```bash
sudo reboot
```

Wait a minute, then SSH back in to confirm:

```bash
ssh -i path/to/your-key.pem ubuntu@<PUBLIC-IP>
```

For **deploy** and **logs**, the Biff tasks use the `app` user. So you’ll use:

- `app@<PUBLIC-IP>` or `app@<PUBLIC-IP>.nip.io` when the deploy task asks for the host.
- SSH key: the same `.pem` (the script copied it into `app`’s `authorized_keys`).

If you use a hostname (e.g. nip.io), set `DOMAIN=<PUBLIC-IP>.nip.io` in `config.env` and use that hostname when running `clj -M:dev deploy` (e.g. `app@54.123.45.67.nip.io`). You may need to add the host to `~/.ssh/config` so `ssh` and the deploy task know which key to use, for example:

```
Host 54.123.45.67.nip.io
  User app
  IdentityFile path/to/your-key.pem
```

(Use your real IP and key path.) Then from your machine: `clj -M:dev deploy` and use `54.123.45.67.nip.io` (or your IP) as the host.

Then follow the [Common steps](#common-steps-all-providers) for `config.env`, deploy, and verify. Use your instance **Public IP** or **`<PUBLIC-IP>.nip.io`** wherever the guide says “your-ip-or-hostname”.

---

## Common steps (all providers)

Once you have a VM with a **public IP** and ports **22, 80, 443** open, do the following. Use your VM’s IP or a [nip.io](https://nip.io) hostname (e.g. `123.45.67.89.nip.io`) if you don’t have a domain.

### 1. Domain or free hostname

- **With a domain:** Create an A record pointing to your VM’s public IP.
- **Without a domain:** Use **nip.io**: `<your-ip>.nip.io` (e.g. `123.45.67.89.nip.io`). Use this as your “domain” everywhere below.

### 2. Prepare `config.env` locally

```bash
clj -M:dev generate-config   # if you don’t have config.env yet
```

Edit `config.env`:

- `DOMAIN` = your domain or nip.io hostname (e.g. `123.45.67.89.nip.io`)
- Set any secrets your app needs: `COOKIE_SECRET`, `JWT_SECRET`, and optionally `MAILERSEND_*`, `RECAPTCHA_*`, etc.

Do **not** set `PROD_XTDB_TOPOLOGY` or `XTDB_JDBC_URL`.

### 3. Run server setup on the VM

From your project root:

```bash
scp server-setup.sh root@<your-ip-or-hostname>:
ssh root@<your-ip-or-hostname>
```

On the server:

```bash
bash server-setup.sh
```

When **certbot** asks for the domain name, enter the same value as `DOMAIN` (e.g. your nip.io hostname). Then:

```bash
reboot
```

Wait a minute and confirm you can SSH back in.

### 4. (Optional) Git remote for deploy

If you don’t have `rsync`, add the prod remote (use your IP or hostname):

```bash
git remote add prod ssh://app@<your-ip-or-hostname>/home/app/repo.git
```

If your default branch is `main`, set in `resources/config.edn`:

```clojure
:biff.tasks/git-deploy-cmd ["git" "push" "prod" "main:master"]
```

### 5. Deploy the app

From your **local** machine:

```bash
clj -M:dev deploy
```

When prompted, use the **app** user (e.g. `app@123.45.67.89.nip.io` or `app@<your-ip>`).

### 6. Verify

```bash
clj -M:dev logs
```

Wait for “System started” (or your app’s startup message). Then open `https://<your-domain-or-nip.io>` in a browser.

---

## Summary

| Provider        | Free offer              | Notes                                      |
|----------------|-------------------------|--------------------------------------------|
| **DigitalOcean** | $200 credit, 60 days     | Easiest; same workflow as Biff tutorial   |
| **Google Cloud**  | $300 credit, 90 days or e2-micro | Need to enable Compute and open HTTP(S)   |
| **AWS**           | 12 months, t2.micro     | Open security group for 22, 80, 443       |

After the free period, DigitalOcean and others are a few dollars per month for a small VPS. For more detail on nip.io, backups, and troubleshooting, see [DEPLOY-ORACLE.md](DEPLOY-ORACLE.md) (steps 3–10 and the troubleshooting section apply to any Ubuntu VPS).

### ssh-add / SSH agent (Windows)

If deploy fails with **"Error connecting to agent: No such file or directory"**, the deploy task is trying to run `ssh-add` but the SSH agent isn’t running. Add to `resources/config.edn` (or uncomment if present):

```clojure
:biff.tasks/skip-ssh-agent true
```

Then the task will use the key from your `~/.ssh/config` (IdentityFile) instead of the agent.

### Double .nip.io (e.g. 13.51.196.165.nip.io.nip.io)

If the deploy task appends `.nip.io` to the server and you already set `DOMAIN=13.51.196.165.nip.io`, you get a double suffix. Fix: in `config.env` set **DEPLOY_HOST=13.51.196.165** (plain IP) and keep **DOMAIN=13.51.196.165.nip.io**. In `resources/config.edn`, set:

```clojure
:biff.tasks/server #or [#biff/env "DEPLOY_HOST" #biff/env "DOMAIN"]
```

Then the deploy uses `DEPLOY_HOST` (IP only), the task adds `.nip.io` → `13.51.196.165.nip.io`, and the app still uses `DOMAIN` for the base URL.

### fatal: 'prod' does not appear to be a git repository

The deploy task runs `git push prod master` to push code to the server. Add the `prod` remote once (use your server IP or hostname):

```bash
git remote add prod ssh://app@13.51.196.165/home/app/repo.git
```

Replace `13.51.196.165` with your server’s public IP or nip.io hostname. Then run `clj -M:dev deploy` again.

### scp: dest open "target/resources/public/css/main.css": No such file or directory

The deploy task uploads the built CSS to the server; the remote directory may not exist yet. Create it on the server (use your deploy host), then deploy again:

```bash
ssh app@13.51.196.165 "mkdir -p target/resources/public/css"
```

Replace `13.51.196.165` with your server IP or nip.io hostname. Then run `clj -M:dev deploy` again.

### Windows line endings (CRLF)

If `server-setup.sh` fails with `set: -` or `$'\r': command not found`, the script has Windows line endings. **On the server** run: `sed -i 's/\r$//' server-setup.sh`, then `sudo bash server-setup.sh` again. The repo’s copy of `server-setup.sh` has been converted to Unix (LF) line endings so re-copying the script and trying again should also fix it.
