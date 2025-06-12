(ns com.greed.app
  (:require [com.biffweb :as biff :refer [q]]
            [com.greed.middleware :as mid]
            [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.forms :as forms]
            [com.greed.ui.components.cards :as cards]
            [com.greed.ui.components.alerts :as alerts]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.components.features :as features]
            [com.greed.ui.components.calendars :as calendars]))



(defn app [{:keys [session params] :as ctx}]
  (let [user-id (:uid session)
        user (data/get-user ctx user-id)
        profile (data/get-profile ctx user-id)
        income (:profile/income profile)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (when (:update params) (alerts/info params))
      (headers/home-heading
       :breadcrumbs ["Home"]
       :user user)
      [:div
       {:class "mt-12 grid grid-cols-1 md:grid-cols-3 gap-4"}
       [:div.col-span-1
        (cards/bank-card
         :bank (:profile/bank profile)
         :card-type (:profile/card-type profile)
         :income income
         :expenses (:profile/expenses profile)
         :age (:profile/age profile))]
       [:div.col-span-2
        (cards/account-stats
         :income (:profile/income profile)
         :expenses (:profile/expenses profile)
         :savings (:profile/savings profile)
         :age (:profile/age profile))]]
      (stats/tax-stats
       :income income
       :age (:profile/age profile))])))

(defn app-settings [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div]))

(defn update-user [ctx]
  (data/update-user ctx)
  {:status 303
   :headers {"location" "/app?update=user-updated"}})

(defn profile [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div.container.mx-auto.space-y-4
    (headers/pages-heading ["Account" "Settings"])
    (forms/account ctx)
    (forms/profile ctx)]))

(defn upsert-profile [ctx]
  (data/upsert-profile ctx)
  {:status 303
   :headers {"location" "/app?update=profile-updated"}})

(defn calendar [{:keys [session biff/db] :as ctx}]
  (let [user-id (:uid session)
        profile (data/get-profile ctx user-id)
        payday (:profile/payday profile)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (headers/pages-heading ["Calendar"])
      (calendars/calendar payday)])))

(defn tools [{:keys [session biff/db] :as ctx}]
  (ui/app
   ctx
   [:div.container.mx-auto
    {:x-data "{ isOpen: false }"}
    (headers/pages-heading ["Tools"])
    (features/tools)
    (features/modal-form)]))

(def module
  {:routes [["/app" {:middleware [mid/wrap-signed-in]}
             ["" {:get app}]
             ["/calendar" {:get calendar}]
             ["/tools"
              ["" {:get tools}]
              ["/income-tax-calculator" {:post features/income-tax-feature}]]
             ["/settings" {:get app-settings}]
             ["/profile" {:get profile}]
             ["/save-user" {:post update-user}]
             ["/save-profile" {:post upsert-profile}]]]})
