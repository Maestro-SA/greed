(ns com.greed.validation
  (:require [com.biffweb :as biff :refer [q]]
            [clojure.tools.logging :as logger]))


(defn valid-params? [user & {:keys [type]
                         :or {type :signin}}]
  (cond
    (= :signin type) (and (some? (:email user))
                          (some? (:password user)))
    (= :signup type) (and (some? (:email user))
                          (some? (:password user))
                          (some? (:firstname user))
                          (some? (:lastname user)))))

(defn existing-user? [users user]
  (some? (first (filter #(= (:user/email %) (:email user)) users))))

(defn signup [{:keys [params] :as ctx}]
  (let [user-id (random-uuid)]
    (logger/info "Creating new user" (:email params))
    (biff/submit-tx ctx
                    [{:db/doc-type :user
                      :xt/id user-id 
                      :user/firstname (:firstname params)
                      :user/lastname (:lastname params)
                      :user/email (:email params)
                      :user/password (:password params)}])))

(defn signup!? [{:keys [biff/db params] :as ctx}]
  (let [users (q db
                 '{:find (pull user [*])
                   :where [[user :user/email]]})
        valid? (and (valid-params? params :type :signup)
                    (not (existing-user? users params)))]
    (when valid? 
      (signup ctx))
    valid?))

(defn validate-password? [param-password db-password]
  (= param-password db-password))

(defn signin? [{:keys [biff/db params]}]
  (let [user-id (biff/lookup-id db :user/email (:email params))
        user (first (q db
                       '{:find (pull user [*])
                         :in [user-id]
                         :where [[user :xt/id user-id]]}
                       user-id))
        valid-password? (validate-password? (:password params) (:user/password user))
        valid? (and (valid-params? params :type :signin)
                    valid-password?)]
    (if valid?
      (logger/info "Signing in" (:email params))
      (logger/info "Invalid sign in" (:email params)))
    valid?))

(defn authenticate! [{:keys [uri] :as ctx}]
  (if (= "/authenticate/signup" uri)
    (signup!? ctx)
    (signin? ctx)))