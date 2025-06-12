(ns com.greed.authentication
  (:require [clojure.tools.logging :as logger]
            [com.greed.data.core :as data]))


(defn existing-user? [users user]
  (some? (first (filter #(= (:user/email %) (:email user)) users))))

(defn signup!? [{:keys [params] :as ctx}]
  (let [users (data/get-users ctx)
        valid? (not (existing-user? users params))]
    (when valid?
      (data/upsert-user ctx))
    valid?))

(defn validate-password? [param-password db-password]
  (let [valid-password? (= param-password db-password)]
    {:valid? valid-password?
     :message (if valid-password?
                "Password is valid"
                "Password is invalid")}))

(defn signin? [{:keys [params] :as ctx}]
  (let [user-id (data/get-user-id ctx)
        user (data/get-user ctx user-id)
        {:keys [valid?
                message]} (validate-password? (:password params)
                                              (:user/password user))]
    (logger/info message)
    valid?))

(defn authenticate! [{:keys [uri] :as ctx}]
  (if (= "/authenticate/signup" uri)
    (signup!? ctx)
    (signin? ctx)))
