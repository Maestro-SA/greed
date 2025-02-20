(ns com.greed.home
  (:require [clj-http.client :as http]
            [com.biffweb :as biff]
            [com.greed.middleware :as mid]
            [com.greed.components.headers :as headers]
            [com.greed.pages.home :as p.home]
            [com.greed.pages.signin :as p.signin]
            [com.greed.pages.signup :as p.signup]
            [com.greed.ui :as ui] 
            [rum.core :as rum]
            [xtdb.api :as xt]))

(defn home-page [ctx]
  (ui/static-page
   ctx
   (headers/home)
   (p.home/page)))

(defn signin-page [ctx]
  (ui/form-page
   ctx
   (headers/pages)
   (p.signin/form ctx)))

(defn signup-page [ctx]
  (ui/form-page
   ctx
   (headers/pages)
   (p.signup/form ctx)))

(def module
  {:routes [["" {:middleware [mid/wrap-redirect-signed-in]}
             ["/"                  {:get home-page}]]
            ["/signin"             {:get signin-page}]
            ["/signup"             {:get signup-page}]]})
