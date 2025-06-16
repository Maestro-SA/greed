(ns com.greed.ui.core)


(defn get-budget-data [budget-items]
  (let [income-items (filterv #(= (:budget-item/type %) :income) budget-items)
        expenses-items (filterv #(= (:budget-item/type %) :expenses) budget-items)
        savings-items (filterv #(= (:budget-item/type %) :savings) budget-items)

        total-income (reduce + (map :budget-item/amount income-items))
        total-expenses (reduce + (map :budget-item/amount expenses-items))
        total-savings (reduce + (map :budget-item/amount savings-items))]
    {:total-income total-income
     :total-expenses total-expenses
     :total-savings total-savings}))
