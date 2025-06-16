(ns com.greed.ui.components.stats
  (:require [com.greed.ui.core :as c.ui]
            [com.greed.utilities.core :as utilities]
            [com.greed.ui.components.svgs :as svgs]))


(defn savings-stat [budget-items]
  (let [{:keys [total-savings]} (c.ui/get-budget-data budget-items)]
    [:div
     {:class "px-4 py-4"}
     [:article
      {:class "flex items-center gap-4 rounded-lg border border-gray-100 p-6 sm:justify-between bg-gradient-to-r from-gray-50 to-gray-300 shadow-sm"}
      [:span
       {:class "rounded-full bg-gray-200 p-3 text-gray-800 sm:order-last"}
       (svgs/money)]
      [:div
       [:p
        {:class "text-2xl font-medium text-gray-900"}
        (utilities/amount->rands total-savings)]
       [:p
        {:class "text-sm text-gray-500"}
        "Total Savings"]]]]))

(defn tax-stat [& {:keys [title value]}]
  [:article
   {:class "flex flex-col gap-4 rounded-lg border border-gray-100 p-6 bg-gradient-to-r from-gray-200 to-gray-50"}
   [:div
    {:class "inline-flex gap-2 self-end rounded-sm bg-green-100 p-1 text-green-600"}
    (svgs/uptrend)]
   [:div
    [:strong {:class "block text-sm font-medium text-gray-500"}
     title]
    [:p
     [:span {:class "text-2xl font-medium text-gray-900"}
      value]]]])

(defn tax-stats [income-tax-data]
  (let [{:keys [age
                rebates
                net-income
                tax-threshold
                effective-rate]} income-tax-data]
    [:section
     {:class "bg-gray-100 -mt-24 rounded-xl"}
     [:div
      {:class "mx-auto px-4"}
      [:div
       {:class "flex flex-wrap"}
       [:div
        {:class "lg:pt-12 pt-6 w-full md:w-4/12 px-4 text-center"}
        [:div
         {:class "relative flex flex-col min-w-0 break-words bg-white w-full mb-1 shadow-lg rounded-lg"}
         [:div {:class "px-4 flex-auto"}]]]]
      [:div
       {:class "flex flex-wrap items-center mt-16"}
       [:div
        {:class "w-full md:w-1/2 px-4 mr-auto ml-auto"}
        [:div
         {:class "text-gray-500 p-3 text-center inline-flex items-center justify-center w-14 h-14 mb-6 shadow-lg rounded-full bg-white"}
         (svgs/credit-card)]
        [:h3
         {:class "text-3xl mb-2 font-semibold leading-normal"}
         "Taxation overview"]
        [:p
         {:class
          "text-lg font-light leading-relaxed mt-4 mb-4 text-gray-600"}
         "Know your tax obligations and how to manage your finances."]
        [:p
         {:class"text-lg font-light leading-relaxed mt-0 mb-4 text-gray-600"}
         "Taxation is a necessary part of life. It helps fund public services and infrastructure. However, it can also be a burden if not managed properly."]]
       [:div
        {:class "w-full md:w-1/2 px-4 mr-auto ml-auto"}
        [:div
         {:class "flex flex-col min-w-0 break-words w-full mb-6 "}
         [:div
          {:class "px-4 py-5 flex-auto"}
          [:div
           {:class "grid grid-cols-1 sm:grid-cols-2 gap-4"}
           (tax-stat
            :title "Net annual income"
            :value (utilities/amount->rands net-income))
           (tax-stat
            :title "Tax threshold"
            :value (utilities/amount->rands tax-threshold))
           (tax-stat
            :title "Age"
            :value age)
           (tax-stat
            :title "Tax rebates"
            :value (utilities/amount->rands rebates))
           (tax-stat
            :title "Effective rate"
            :value (utilities/->percentage effective-rate))
           (tax-stat
            :title "Net monthly income"
            :value (-> net-income
                        utilities/annual-income->monthly-income
                        utilities/amount->rands))]]]

        ]]]]))


(defn expense-tracker-stats [budget-items]
  (let [{:keys [total-income
                total-expenses
                total-savings]} (c.ui/get-budget-data budget-items)]
    [:div
     {:class "px-4 py-4"}
     [:div
      {:class "grid lg:grid-cols-3 md:grid-cols-2 gap-6 w-full"}
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class "flex flex-shrink-0 items-center justify-center bg-green-200 h-16 w-16 rounded"}
        (svgs/uptrend)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"}
         (utilities/amount->rands total-income)]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Monthly income"]
         [:span
          {:class "text-green-500 text-sm font-semibold ml-2"}
          "+12.6%"]]]]
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class
         "flex flex-shrink-0 items-center justify-center bg-red-200 h-16 w-16 rounded"}
        (svgs/downtrend)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"}
         (utilities/amount->rands total-expenses)]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Monthly expenses"]
         [:span
          {:class "text-red-500 text-sm font-semibold ml-2"}
          "-8.1%"]]]]
      [:div
       {:class "flex items-center p-4 bg-white rounded"}
       [:div
        {:class
         "flex flex-shrink-0 items-center justify-center bg-gray-200 h-16 w-16 rounded"}
        (svgs/banknotes)]
       [:div
        {:class "flex-grow flex flex-col ml-4"}
        [:span {:class "text-xl font-bold"}
         (utilities/amount->rands total-savings)]
        [:div
         {:class "flex items-center justify-between"}
         [:span {:class "text-gray-500"}
          "Total savings"]
         [:span
          {:class "text-green-500 text-sm font-semibold ml-2"}
          "+28.4%"]]]]]]))
