# XTDB setup and usage

XTDB is already wired up in your Biff app. This doc summarizes how it’s configured and how to use it.

---

## 1. Current configuration

### Config (`resources/config.edn` + `config.env`)

| Setting | Value | Meaning |
|--------|--------|--------|
| `:biff.xtdb/dir` | `"storage/xtdb"` | Directory for XTDB data (relative to app working directory). |
| `:biff.xtdb/topology` | from env, default `"standalone"` | One in-process node; data on local disk. |
| `XTDB_TOPOLOGY` | `standalone` (in config.env) | Same for dev. |
| `PROD_XTDB_TOPOLOGY` | not set | Prod also uses standalone. |
| `XTDB_JDBC_URL` | not set | Only needed if you switch to JDBC (e.g. Postgres). |

### Where data lives

- **Dev:** `storage/xtdb` under your project (create it if needed; Biff will use it when you run `clj -M:dev dev`).
- **Prod (AWS):** `/home/app/storage/xtdb` on the server (created when the app runs; `ExecStart` in the systemd unit uses `/home/app` as `WorkingDirectory`).

No extra “setup” step is required: start the app and XTDB uses that directory.

---

## 2. How the app uses XTDB

- **Startup:** `com.greed` uses Biff’s `biff/use-xtdb` and `biff/use-xtdb-tx-listener` so the system starts an XTDB node and injects `biff/db` and `biff.xtdb/node` where needed.
- **Schema:** `com.greed.schema/schema` defines your entity shapes (user, finances, budget-item). The schema is used for validation; XTDB itself is schemaless.
- **Queries:** Handlers and data layer get `:biff/db` from the request/system and use `q` (Biff’s query helper). Example: `com.greed.data.core/get-user`, `get-budget-items`, etc.
- **Writes:** Same code uses `biff/submit-tx` with maps that include `:db/doc-type` (e.g. `:user`, `:finances`, `:budget-item`) and `:xt/id`.

So “setting up XTDB” for this project means: **config is already set; just run the app (dev or prod).**

---

## 3. Dev: run and inspect

1. Start the app:
   ```bash
   clj -M:dev dev
   ```
2. Use the app; data is written to `storage/xtdb` under the project.
3. (Optional) In a REPL connected to dev, you can read the DB, e.g.:
   ```clojure
   (require '[com.biffweb :as biff])
   (def db (:biff/db @com.greed/system))
   (biff/q db '{:find [(pull user [*])], :where [[user :user/email]]})
   ```

---

## 4. Prod: standalone on the server

- **Topology:** Prod uses **standalone** (single node, one server).
- **Directory:** `/home/app/storage/xtdb` (created when the app runs).
- **Backups:** The directory is just a folder on the VM. To back up:
  - Option A: Cron job on the server that tars or rsyncs `/home/app/storage/xtdb` to another volume or S3.
  - Option B: Use a managed Postgres and switch prod to JDBC (see below).

No extra XTDB setup is required for prod beyond what you already did (deploy + app running).

---

## 5. Optional: use Postgres for XTDB storage (prod)

If you want XTDB to store its backend in Postgres instead of the filesystem (e.g. for backups or multi-node later):

1. Create a Postgres DB (e.g. AWS RDS, or a small instance).
2. In `config.env` (and on the server’s env or `config.env` you deploy):
   ```bash
   PROD_XTDB_TOPOLOGY=jdbc
   XTDB_JDBC_URL=jdbc:postgresql://host:5432/dbname?user=...&password=...&sslmode=require
   ```
3. Redeploy so the prod app starts with JDBC topology. XTDB will use Postgres for storage; your app code (queries, `submit-tx`) stays the same.

Leave `XTDB_TOPOLOGY=standalone` for dev so you don’t need Postgres locally.

---

## 6. Quick reference: query and write

- **Query:** In a handler or service that receives `ctx` (with `:biff/db`):
  ```clojure
  (q (:biff/db ctx) '{:find [(pull user [*])], :where [[user :user/email email]]} email)
  ```
- **Write:** Use `biff/submit-tx` with one or more maps:
  ```clojure
  (biff/submit-tx ctx [{:db/doc-type :user
                        :xt/id (random-uuid)
                        :user/email "a@b.com"
                        ...}])
  ```
- **Schema:** Add or change entity shapes in `com.greed.schema/schema` and use `:db/doc-type` in transactions to match.

For more detail, see [Biff’s XTDB docs](https://biffweb.com/docs/reference/database/) and your existing `com.greed.data.core` and `com.greed.schema` namespaces.
