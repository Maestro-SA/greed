(ns com.greed.app
  (:require [com.biffweb :as biff :refer [q]]
            [com.greed.middleware :as mid]
            [com.greed.ui :as ui] 
            [rum.core :as rum]
            [xtdb.api :as xt]
            [ring.adapter.jetty9 :as jetty]
            [cheshire.core :as cheshire]
            [com.greed.components.forms :as forms]
            [com.greed.components.cards :as cards]
            [com.greed.components.alerts :as alerts]
            [com.greed.components.calendars :as calendars]
            [com.greed.data.helpers :as d.helpers]
            [com.greed.components.charts :as charts]
            [com.greed.components.headers :as headers]))



(defn app [{:keys [session biff/db] :as ctx}]
  (let [{:keys [session params]} ctx
        user-id (:uid session)
        user (d.helpers/get-user ctx user-id)
        profile (d.helpers/get-profile ctx user-id)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (when (:update params) (alerts/info))
      (headers/heading
       :breadcrumbs ["Home"]
       :user user)
      [:div
       {:class "mt-12 grid grid-cols-1 md:grid-cols-3 gap-4"}
       [:div.col-span-1
        (cards/bank-card
         :bank (:profile/bank profile)
         :card-type (:profile/card-type profile)
         :income (:profile/income profile)
         :expenses (:profile/expenses profile))]
       [:div.col-span-2
        (cards/account-stats
         :income (:profile/income profile)
         :expenses (:profile/expenses profile)
         :savings (:profile/savings profile))]]
      #_(charts/revenue)
      (charts/chart)])))

(defn app-settings [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div]))

(defn update-user [ctx]
  (d.helpers/update-user ctx)
  {:status 303
   :headers {"location" "/app?update=user-updated"}})

(defn profile [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div.space-y-4
    (forms/account ctx)
    (forms/profile ctx)]))

(defn upsert-profile [ctx]
  (d.helpers/upsert-profile ctx)
  {:status 303
   :headers {"location" "/app?update=profile-updated"}})

(defn calendar [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div
    (headers/pages-heading ["Calendar"])
    (calendars/calendar)]))

(def module
  {:routes [["/app" {:middleware [mid/wrap-signed-in]}
             ["" {:get app}]
             ["/calendar" {:get calendar}]
             ["/settings" {:get app-settings}]
             ["/profile" {:get profile}]
             ["/save-user" {:post update-user}]
             ["/save-profile" {:post upsert-profile}]]]})
