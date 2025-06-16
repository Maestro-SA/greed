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
       {:class "flex flex-col lg:items-center lg:flex-row mt-12"}
       [:div
        (cards/bank-card
         :finances finances
         :budget-items budget-items)]
       [:div
        (stats/savings-stat budget-items)]]
      (stats/tax-stats income-tax-data)])))
