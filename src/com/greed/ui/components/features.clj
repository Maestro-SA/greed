(ns com.greed.ui.components.features
  (:require [com.greed.ui :as ui]
            [com.greed.tools.tax :as tax]
            [com.greed.tools.core :as tools]
            [com.greed.ui.components.svgs :as svgs]
            [com.greed.ui.components.forms :as forms]
            [com.greed.ui.components.headers :as headers]))


(defn tool [& {:keys [title description]
               :or   {title "Tool Title"
                      description "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nostrum quam voluptatibus"}}]
  [:div
   {:class "p-8 space-y-3 border-2 border-gray-400 rounded-xl"}
   [:span
    {:class "inline-block text-orange-500"}
    (svgs/flame)]
   [:h1
    {:class "text-xl font-semibold text-gray-700 capitalize"}
    title]
   [:p
    {:class "text-gray-500"}
    description]
   [:button
    {"@click" "isOpen = true"
     :class "inline-flex p-2 text-gray-500 capitalize transition-colors duration-300 transform bg-gray-100 rounded-full hover:underline hover:text-orange-500"}
    (svgs/arrow->)]])

(defn tools []
  [:section
   [:div
    {:class "container px-6 py-10 mx-auto"}
    [:h1
     {:class "text-2xl font-semibold text-gray-800 capitalize lg:text-3xl"}
     "Finance "
     [:span {:class "underline decoration-blue-500"} "Tools"]]
    [:p
     {:class "mt-4 text-gray-500 xl:mt-6"}
     "Make informed decisions about your finances with our free tools."]
    [:div
     {:class "grid grid-cols-1 gap-8 mt-8 xl:mt-12 xl:gap-12 md:grid-cols-2 xl:grid-cols-3"}
     (tool
      :title "Income tax calculator"
      :description "Calculate your income tax in seconds"
      :link "/income-tax-calculator")]]])

(defn modal-form []
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

(defn income-tax-feature [{:keys [params] :as ctx}]
  (let [{:keys [income age]} params
        income (tools/->int income)
        age (tools/->int age)
        annual-income (tools/income->annual-income income)
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
          (tools/amount->rands income) " (Gross income p/m)"]]
        [:div
         {:class "flex items-center mt-4 text-gray-700"}
         (svgs/percent-badge)
         [:h1 {:class "px-2 text-sm"}
          (tools/->percentage effective-rate)]]
        [:div
         {:class "flex items-center mt-4 text-gray-700"}
         (svgs/dollar)
         [:h1 {:class "px-2 text-sm"}
          (-> net-income
              tools/annual-income->monthly-income
              tools/amount->rands) " (Net income p/m)"]]]]])))
