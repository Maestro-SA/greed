(ns com.greed.ui.components.stats
  (:require [com.greed.tools.tax :as tax]
            [com.greed.tools.core :as tools]))


(defn tax-stats [& {:keys [income age]}]
  (let [income (or income 500)

        age (or age 18)

        annual-income (tools/income->annual-income income)

        {:keys [rebates
                net-income
                tax-threshold
                effective-rate]} (tax/calculate-income-tax annual-income age)]
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
          {:class "text-3xl font-semibold text-gray-800 sm:text-base"}
          "Net annual income"]
         [:p
          {:class "text-2xl font-bold text-gray-800"}
          (tools/amount->rands net-income)]]
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Tax threshold"]
         [:p
          {:class "text-2xl font-bold text-gray-800"}
          (tools/amount->rands tax-threshold)]]
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Tax rebates"]
         [:p
          {:class "text-2xl font-bold text-gray-800"}
          (tools/amount->rands rebates)]]]
       [:div
        {:class "flex flex-col justify-between p-10"}
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Age"]
         [:p
          {:class "text-2xl font-bold text-deep-purple-accent-400"}
          age]]
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Effective rate"]
         [:p
          {:class "text-2xl font-bold text-deep-purple-accent-400"}
          (tools/->percentage effective-rate)]]
        [:div
         [:p
          {:class "text-xl font-semibold text-gray-800 sm:text-base"}
          "Net monthly income"]
         [:p
          {:class "text-2xl font-bold text-green-700"}
          (-> net-income
              tools/annual-income->monthly-income
              tools/amount->rands)]]]]]]))
