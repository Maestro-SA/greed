(ns com.greed.home
  (:require [com.biffweb :as biff :refer [q]]
            [com.greed.middleware :as mid]
            [com.greed.ui :as ui]
            [com.greed.ui.pages.home :as p.home]
            [com.greed.ui.pages.signin :as p.signin]
            [com.greed.ui.pages.signup :as p.signup]
            [com.greed.ui.components.headers :as headers]))

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
