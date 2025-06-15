(ns com.greed.ui.tools.budget-tracker
  (:require [com.greed.ui :as ui]
            [com.greed.ui.core :as c.ui]
            [com.greed.data.core :as data]
            [com.greed.utilities.core :as utilities]
            [com.greed.ui.components.lists :as lists]
            [com.greed.ui.components.stats :as stats]
            [com.greed.ui.components.headers :as headers]))


(defn budget-lists [& {:keys [income-tax-data finance-items]}]
  (let [{:keys [net-income]} income-tax-data
        monthly-net-income (-> net-income
                               utilities/annual-income->monthly-income
                               utilities/double->int)
        _ (println "Monthly Net Income:" monthly-net-income)
        income-items (filterv #(= (:finances/type %) :income) finance-items)]
    [:div
     {:class "grid grid-cols-1 md:grid-cols-3 gap-4"}
     (lists/finance-list
      :title "income"
      :items (cond-> [{:finances/title "Salary"
                       :finances/type :income
                       :finances/amount monthly-net-income}]
               (some? income-items) (conj income-items)))
     (lists/finance-list
      :title "expenses"
      :items (filterv #(= (:finances/type %) :expenses) finance-items))
     (lists/finance-list
      :title "savings"
      :items (filterv #(= (:finances/type %) :savings) finance-items))]))

(defn page [{:keys [session] :as ctx}]
  (let [user-id (:uid session)
        profile (data/get-profile ctx user-id)
        income-tax-data (c.ui/get-income-tax-data profile)
        finance-items (data/get-finance-items ctx user-id)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (headers/pages-heading ["Tools" "Budget Tracker"])
      (stats/expense-tracker-stats
       :income-tax-data income-tax-data)
      (budget-lists
       :income-tax-data income-tax-data
       :finance-items finance-items)])))
