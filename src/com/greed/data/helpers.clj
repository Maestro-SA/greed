(ns com.greed.data.helpers
  (:require [com.biffweb :as biff :refer [q]]
            [clojure.tools.logging :as logger]))


(defn get-users [{:keys [biff/db]}]
  (q db
     '{:find (pull user [*])
       :where [[user :user/email]]}))

(defn get-user-id [{:keys [biff/db params]}]
  (biff/lookup-id db :user/email (:email params)))

(defn get-user-id-from-session [{:keys [session]}]
  (:uid session))

(defn get-user [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull user [*])
              :in [user-id]
              :where [[user :xt/id user-id]]}
            user-id)))

(defn get-profile [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull profile [*])
              :in [user-id]
              :where [[profile :profile/user-id user-id]]}
            user-id)))

(defn upsert-user [{:keys [params] :as ctx}]
  (let [user-id (java.util.UUID/randomUUID)]
    (logger/info "Creating user...")
    (biff/submit-tx ctx
                    [{:db/doc-type :user
                      :xt/id user-id
                      :user/firstname (:firstname params)
                      :user/lastname (:lastname params)
                      :user/email (:email params)
                      :user/password (:password params)}])))

(defn update-user [{:keys [params] :as ctx}]
  (let [user-id (get-user-id ctx)
        user (get-user ctx user-id)]
    (if user
      (do
        (logger/info "Updating user...")
        (biff/submit-tx ctx
                      [{:db/doc-type :user
                        :xt/id user-id
                        :db/op :update
                        :user/firstname (:firstname params)
                        :user/lastname (:lastname params)
                        :user/email (:email params)
                        :user/password (:password params)}]))
      (logger/info "User not found"))))

(defn upsert-profile [{:keys [params] :as ctx}]
  (let [user-id (get-user-id-from-session ctx)
        profile (get-profile ctx user-id)
        profile-id (or (:xt/id profile)
                       (java.util.UUID/randomUUID))]
    (if profile
      (do
        (logger/info "Updating profile...")
        (biff/submit-tx ctx
                      [{:db/doc-type :profile
                        :xt/id profile-id
                        :db/op :update
                        :profile/bank (:bank params)
                        :profile/card-type (:card-type params)
                        :profile/income (:income params)
                        :profile/expenses (:expenses params)
                        :profile/savings (:savings params)}]))
      (do
        (logger/info "Creating profile...")
        (biff/submit-tx ctx
                      [{:db/doc-type :profile
                        :xt/id profile-id
                        :profile/user-id user-id
                        :profile/bank (:bank params)
                        :profile/income (:income params)
                        :profile/expenses (:expenses params)
                        :profile/savings (:savings params)}])))))
