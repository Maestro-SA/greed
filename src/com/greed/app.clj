(ns com.greed.app
  (:require [com.biffweb :as biff :refer [q]]
            [com.greed.middleware :as mid]
            [com.greed.ui :as ui] 
            [rum.core :as rum]
            [xtdb.api :as xt]
            [ring.adapter.jetty9 :as jetty]
            [cheshire.core :as cheshire]
            [com.greed.components.forms :as forms]
            [com.greed.components.cards :as cards]))



(defn app [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div
    (cards/bank-card)]))

(defn settings [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div
    (forms/app-settings ctx)]))

(def module
  {:routes ["/app" {:middleware [mid/wrap-signed-in]}
            ["" {:get app}]
            ["/settings" {:get settings}]]})
