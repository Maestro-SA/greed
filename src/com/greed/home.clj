(ns com.greed.home
  (:require [clj-http.client :as http]
            [com.greed.middleware :as mid]
            [com.greed.components.headers :as headers]
            [com.greed.pages.home :as p.home]
            [com.greed.pages.signin :as p.signin]
            [com.greed.pages.signup :as p.signup] 
            [com.biffweb :as biff :refer [q]]
            [com.greed.ui :as ui] 
            [rum.core :as rum]
            [xtdb.api :as xt]))

(defn home-page [ctx]
  (ui/page
   ctx
   (headers/pages)
   (p.home/page)))

(defn signin-page [ctx]
  (ui/page
   ctx
   (headers/pages)
   (p.signin/form ctx)))

(defn signin-success-page [{:keys [biff/db session params]}]
  (let [user-id (biff/lookup-id db :user/email (:email params))]
   {:status 303
    :headers {"Location" "/app"}
    :session (assoc session :uid user-id)}))

(defn signup-page [ctx]
  (ui/page
   ctx
   (headers/pages)
   (p.signup/form ctx)))

(defn signup-success-page [{:keys [biff/db session params]}]
  (let [user-id (biff/lookup-id db :user/email (:email params))]
    {:status 303
     :headers {"Location" "/app"}
     :session (assoc session :uid user-id)}))

(def module
  {:routes [["" {:middleware [mid/wrap-redirect-signed-in]}
             ["/"                  {:get home-page}]]
            ["/signin"             {:get signin-page}]
            ["/signup"             {:get signup-page}]
            ["/authenticate" {:middleware [mid/wrap-authenticate]}
             ["/signin" {:post signin-success-page}]
             ["/signup" {:post signup-success-page}]]
            ["/logout" {:get mid/logout}]]})
