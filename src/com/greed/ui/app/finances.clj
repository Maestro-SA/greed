(ns com.greed.ui.app.finances
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.components.lists :as lists]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.alerts :as alerts]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.components.buttons :as buttons]))


(defn budget-lists [budget-items]
  [:div
   {:class "grid grid-cols-1 md:grid-cols-3 gap-4"}
   (lists/budget-list
    :title "income"
    :items (filterv #(= (:budget-item/type %) :income) budget-items))
   (lists/budget-list
    :title "expenses"
    :items (filterv #(= (:budget-item/type %) :expenses) budget-items))
   (lists/budget-list
    :title "savings"
    :items (filterv #(= (:budget-item/type %) :savings) budget-items))])

(defn page [{:keys [session params] :as ctx}]
  (let [user-id (:uid session)
        budget-items (data/get-budget-items ctx user-id)]
    (ui/app
     ctx
     [:div.container.mx-auto
      {:x-data "{ isOpen: false }"}
      (when (:alert params) (alerts/info params))
      (headers/pages-heading ["Tools" "Budget Tracker"])
      (stats/expense-tracker-stats budget-items)
      (budget-lists budget-items)
      (buttons/modal-button
       :title "Add budget item")
      (lists/budget-modal)])))
