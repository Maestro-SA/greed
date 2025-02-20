(ns com.greed.data.helpers
  (:require [com.biffweb :as biff :refer [q]]))


(defn get-user-id [{:keys [biff/db params]}]
  (biff/lookup-id db :user/email (:email params)))

(defn get-user [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull user [*])
              :in [user-id]
              :where [[user :xt/id user-id]]}
            user-id)))