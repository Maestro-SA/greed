(ns com.greed.ui.app.finances
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.headers :as headers]))


(defn page [{:keys [session] :as ctx}]
  (let [user-id (:uid session)
        budget-items (data/get-budget-items ctx user-id)]
    (ui/app
     ctx
     [:div.container.mx-auto.space-y-4
      (headers/pages-heading ["Finances"])
      (stats/expense-tracker-stats budget-items)])))
