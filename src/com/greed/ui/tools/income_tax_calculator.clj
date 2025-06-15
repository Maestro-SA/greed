(ns com.greed.ui.tools.income-tax-calculator
  (:require [com.greed.ui :as ui]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.components.svgs :as svgs]
            [com.greed.utilities.core :as utilities]
            [com.greed.utilities.tax :as tax]
            [com.greed.ui.components.forms :as forms]))


(defn income-tax-modal []
  [:div
   {:class "relative flex justify-center"}
   [:div
    {:role "dialog",
     :x-show "isOpen",
     :x-transition:leave-end "translate-y-4 opacity-0 sm:translate-y-0 sm:scale-95",
     :x-transition:leave-start "translate-y-0 opacity-100 sm:scale-100",
     :aria-labelledby "modal-title",
     :x-transition:enter "transition duration-300 ease-out",
     :aria-modal "true",
     :class "fixed inset-0 z-10 overflow-y-auto",
     :x-transition:enter-start "translate-y-4 opacity-0 sm:translate-y-0 sm:scale-95",
     :x-transition:enter-end "translate-y-0 opacity-100 sm:scale-100",
     :x-transition:leave "transition duration-150 ease-in"}
    [:div
     {:class "flex items-end justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0"}
     [:span
      {:class "hidden sm:inline-block sm:h-screen sm:align-middle",
       :aria-hidden "true"}
      "​"]
     (forms/income-tax-form)]]])

(defn page [{:keys [params] :as ctx}]
  (let [{:keys [income age]} params
        income (utilities/->int income)
        age (utilities/->int age)
        annual-income (utilities/income->annual-income income)
        {:keys [net-income
                effective-rate]} (tax/calculate-income-tax annual-income age)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (headers/pages-heading ["Tools" "Income tax calculation"])
      [:div
       {:class "w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-lg"}
       [:img
        {:class "object-cover object-center w-full h-56",
         :src "https://images.unsplash.com/photo-1683884361203-69b7f969e9ff?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
         :alt "avatar"}]
       [:div
        {:class "flex items-center px-6 py-3 bg-gray-900"}
        [:h1 {:class "text-lg font-semibold text-white"}
         "Income tax breakdown"]]
       [:div
        {:class "px-6 py-4"}
        [:p
         {:class "py-2 text-gray-700"}
         "This is a summary of your income tax calculation."]
        [:div
         {:class "flex items-center mt-4 text-gray-700"}
         (svgs/suit-case)
         [:h1 {:class "px-2 text-sm"}
          (utilities/amount->rands income) " (Gross income p/m)"]]
        [:div
         {:class "flex items-center mt-4 text-gray-700"}
         (svgs/percent-badge)
         [:h1 {:class "px-2 text-sm"}
          (utilities/->percentage effective-rate)]]
        [:div
         {:class "flex items-center mt-4 text-gray-700"}
         (svgs/dollar)
         [:h1 {:class "px-2 text-sm"}
          (-> net-income
              utilities/annual-income->monthly-income
              utilities/amount->rands) " (Net income p/m)"]]]]])))
