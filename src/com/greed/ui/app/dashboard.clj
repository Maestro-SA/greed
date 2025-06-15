(ns com.greed.ui.app.dashboard
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.core :as c.greed]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.cards :as cards]
            [com.greed.ui.components.alerts :as alerts]
            [com.greed.ui.components.headers :as headers]))



(defn page [{:keys [session params] :as ctx}]
  (let [user-id (:uid session)
        user (data/get-user ctx user-id)
        finances (data/get-finances ctx user-id)
        income-tax-data (c.greed/get-income-tax-data user finances)
        budget-items (data/get-budget-items ctx user-id)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (when (:alert params) (alerts/info params))
      (headers/home-heading
       :breadcrumbs ["Home"]
       :user user)
      [:div
       {:class "mt-12 grid grid-cols-1 md:grid-cols-3 gap-4"}
       [:div.col-span-1
        (cards/bank-card
         :finances finances
         :income-tax-data income-tax-data
         :expenses 0)]
       [:div.col-span-2
        (stats/account-stats budget-items)]]
      (stats/tax-stats income-tax-data)])))
