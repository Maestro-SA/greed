(ns com.greed.ui.app.dashboard
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.core :as c.ui]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.cards :as cards]
            [com.greed.ui.components.alerts :as alerts]
            [com.greed.ui.components.headers :as headers]))



(defn page [{:keys [session params] :as ctx}]
  (let [user-id (:uid session)
        user (data/get-user ctx user-id)
        profile (data/get-profile ctx user-id)
        income-tax-data (c.ui/get-income-tax-data profile)]
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
         :income-tax-data income-tax-data
         :expenses (:profile/expenses profile))]
       [:div.col-span-2
        (stats/account-stats
         :income-tax-data income-tax-data
         :expenses (:profile/expenses profile)
         :savings (:profile/savings profile))]]
      (stats/tax-stats
       :income-tax-data income-tax-data
       :age (:profile/age profile))])))
