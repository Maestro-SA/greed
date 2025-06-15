(ns com.greed.ui.components.stats
  (:require [com.greed.utilities.core :as utilities]
            [com.greed.ui.components.svgs :as svgs]))


(defn account-stats [& {:keys [income-tax-data expenses savings]}]
  (let [{:keys [net-income]} income-tax-data

        expenses (or expenses 150)

        savings (or savings 100)]
    [:div
     {:class "px-4 py-4"}
     [:div
      {:class "sm:grid sm:h-48 sm:grid-flow-row sm:gap-4 sm:grid-cols-3"}
      [:div
       {:class "flex flex-col justify-center px-4 py-4 pattern bg-white border border-gray-300 rounded-xl"}
       [:div
        [:div
         [:p
          {:class "flex items-center justify-end text-green-500 text-md"}
          (svgs/uptrend)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (-> net-income
              utilities/annual-income->monthly-income
              utilities/amount->rands)]
        [:p {:class "text-lg text-center text-green-800"}
         "Income"]]]
      [:div
       {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern bg-white border border-gray-300 rounded-xl sm:mt-0"}
       [:div
        [:div
         [:p
          {:class "flex items-center justify-end text-red-500 text-md"}
          (svgs/downtrend)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (utilities/amount->rands expenses)]
        [:p {:class "text-lg text-center text-red-800"} "Expenses"]]]
      [:div
       {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern bg-white border border-gray-300 rounded-xl sm:mt-0"}
       [:div
        [:div
         [:p
          {:class "flex items-center justify-end text-gray-500 text-md"}
          (svgs/stable)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (utilities/amount->rands savings)]
        [:p {:class "text-lg text-center text-gray-800"} "Savings"]]]]]))

(defn tax-stats [& {:keys [income-tax-data age]}]
  (let [{:keys [rebates
                net-income
                tax-threshold
                effective-rate]} income-tax-data]
    [:div
     {:class "px-4 py-16 mx-auto  md:px-24 lg:px-8 lg:py-20"}
     [:div
      {:class "grid gap-24 row-gap-4 lg:grid-cols-5"}
      [:div
       {:class "grid gap-1 lg:col-span-2"}
       [:div
        [:p {:class "mb-2 text-2xl font-medium"}
         "Taxation overview"]
        [:p
         {:class "text-gray-700 max-w-md"}
         "Know your tax obligations and how to manage your finances."]]
       [:div
        [:p {:class "mb-2 text-xl font-medium"}
         "Why?"]
        [:p
         {:class "text-gray-700 max-w-sm"}
         "Taxation is a necessary part of life. It helps fund public services and infrastructure. However, it can also be a burden if not managed properly."]]]
      [:div
       {:class "grid border divide-y rounded lg:col-span-3 sm:grid-cols-2 sm:divide-y-0 sm:divide-x"}
       [:div
        {:class "flex flex-col justify-between p-10"}
        [:div
         [:p
          {:class "text-xl sm:font-semibold text-gray-800 sm:text-base"}
          "Net annual income"]
         [:p
          {:class "text-2xl font-bold text-gray-800"}
          (utilities/amount->rands net-income)]]
        [:div
         [:p
          {:class "text-xl sm:font-semibold text-gray-800 sm:text-base"}
          "Tax threshold"]
         [:p
          {:class "text-2xl font-bold text-blue-800"}
          (utilities/amount->rands tax-threshold)]]
        [:div
         [:p
          {:class "text-xl sm:font-semibold text-gray-800 sm:text-base"}
          "Tax rebates"]
         [:p
          {:class "text-2xl font-bold text-blue-800"}
          (utilities/amount->rands rebates)]]]
       [:div
        {:class "flex flex-col justify-between p-10"}
        [:div
         [:p
          {:class "text-xl sm:font-semibold text-gray-800 sm:text-base"}
          "Age"]
         [:p
          {:class "text-2xl font-bold text-deep-purple-accent-400"}
          age]]
        [:div
         [:p
          {:class "text-xl sm:font-semibold text-gray-800 sm:text-base"}
          "Effective rate"]
         [:p
          {:class "text-2xl font-bold text-deep-purple-accent-400"}
          (utilities/->percentage effective-rate)]]
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Net monthly income"]
         [:p
          {:class "text-2xl font-bold text-green-700"}
          (-> net-income
              utilities/annual-income->monthly-income
              utilities/amount->rands)]]]]]]))


(defn expense-tracker-stats [& {:keys [income-tax-data]}]
  (let [{:keys [net-income]} income-tax-data]
    [:div
     {:class "px-4 py-4"}
     [:div
      {:class "grid lg:grid-cols-3 md:grid-cols-2 gap-6 w-full"}
      (comment "Tile 1")
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class "flex flex-shrink-0 items-center justify-center bg-green-200 h-16 w-16 rounded"}
        (svgs/uptrend)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"}
         (-> net-income
             utilities/annual-income->monthly-income
             utilities/amount->rands)]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Monthly income"]
         [:span
          {:class "text-green-500 text-sm font-semibold ml-2"}
          "+12.6%"]]]]
      (comment "Tile 2")
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class
         "flex flex-shrink-0 items-center justify-center bg-red-200 h-16 w-16 rounded"}
        (svgs/downtrend)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"} "211"]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Monthly expenses"]
         [:span
          {:class "text-red-500 text-sm font-semibold ml-2"}
          "-8.1%"]]]]
      (comment "Tile 3")
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class
         "flex flex-shrink-0 items-center justify-center bg-gray-200 h-16 w-16 rounded"}
        (svgs/banknotes)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"} "140"]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Total savings"]
         [:span
          {:class "text-green-500 text-sm font-semibold ml-2"}
          "+28.4%"]]]]]]))
