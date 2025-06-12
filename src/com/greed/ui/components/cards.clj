(ns com.greed.ui.components.cards
  (:require [com.core :as c]
            [com.greed.tools.tax :as tax]
            [com.greed.tools.core :as tools]
            [com.greed.ui.components.svgs :as svgs]))


(defn testiminial [& {:keys [img title body author]}]
  [:div
   {:class "w-full max-w-md px-8 py-4 mt-16 bg-white border-2 border-black rounded-lg shadow-lg"}
   [:div
    {:class "flex justify-center -mt-16 md:justify-end"}
    [:img
     {:class "object-cover w-20 h-20 border-2 border-black rounded-full",
      :alt "Testimonial avatar",
      :src img}]]
   [:h2
    {:class "mt-2 text-2xl font-giza font-semibold text-gray-800 md:mt-0"}
    title]
   [:p
    {:class "mt-2 text-md text-gray-600"}
    body]
   [:div
    {:class "flex justify-end mt-4"}
    [:a
     {:href "#",
      :class "text-lg font-medium text-gray-400",
      :tabindex "0",
      :role "link"}
     author]]])

(defn get-card-type [card-type]
  (case (tools/->keyword card-type)
                    :visa (svgs/visa)
                    :mastercard (svgs/mastercard)
                    (svgs/visa)))

(defn get-card-colour [bank]
  (let [card-colour-config (:banking/card-colours c/common-config)
        bank (tools/->keyword bank)]
    (if (contains? card-colour-config bank)
      (get card-colour-config bank)
      (:default card-colour-config))))

(defn bank-card [& {:keys [bank card-type income expenses age]}]
  (let [income (or income 500)

        age (or age 18)

        annual-income (tools/income->annual-income income)

        {:keys [net-income]} (tax/calculate-income-tax annual-income age)

        expenses (or expenses 150)

        balance (- (tools/annual-income->monthly-income net-income) expenses)

        card-type (get-card-type card-type)

        card-colour (get-card-colour bank)]
    [:div
     {:class "py-4 px-4"}
     [:div
      [:div
       {:class (str "h-48 w-80 p-6 rounded-xl shadow-md text-white bg-gradient-to-tl " card-colour)}
       [:div
        {:class "h-full flex flex-col justify-between"}
        [:div
         {:class "flex items-start justify-between space-x-4"}
         [:div
          {:class "text-xl font-semibold tracking-tigh"}
          (or bank "Bank")]
         [:div
          {:class "inline-flex flex-col items-center justify-center"}
          card-type]]
        [:div
         {:class "inline-block w-12 h-8 bg-gradient-to-tl from-yellow-200 to-yellow-100 rounded-md shadow-inner overflow-hidden"}
         [:div
          {:class "relative w-full h-full grid grid-cols-2 gap-1"}
          [:div
           {:class "absolute border border-gray-900 rounded w-4 h-6 left-4 top-1"}]
          [:div {:class "border-b border-r border-gray-900 rounded-br"}]
          [:div {:class "border-b border-l border-gray-900 rounded-bl"}]
          [:div]
          [:div]
          [:div {:class "border-t border-r border-gray-900 rounded-tr"}]
          [:div {:class "border-t border-l border-gray-900 rounded-tl"}]]]
        [:div
         {:class ""}
         [:div {:class "text-xs font-semibold tracking-tight"} "balance"]
         [:div {:class "text-2xl font-semibold"}
          (tools/amount->rands balance)]]]]]]))

(defn account-stats [& {:keys [income expenses savings age]}]
  (let [income (or income 500)

        age (or age 18)

        annual-income (tools/income->annual-income income)

        {:keys [net-income]} (tax/calculate-income-tax annual-income age)

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
          [:span {:class "font-bold"} "6%"]
          (svgs/uptrend)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (-> net-income
              tools/annual-income->monthly-income
              tools/amount->rands)]
        [:p {:class "text-lg text-center text-green-800"}
         "Income"]]]
      [:div
       {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern bg-white border border-gray-300 rounded-xl sm:mt-0"}
       [:div
        [:div
         [:p
          {:class "flex items-center justify-end text-red-500 text-md"}
          [:span {:class "font-bold"} "6%"]
          (svgs/downtrend)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (tools/amount->rands expenses)]
        [:p {:class "text-lg text-center text-red-800"} "Expenses"]]]
      [:div
       {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern bg-white border border-gray-300 rounded-xl sm:mt-0"}
       [:div
        [:div
         [:p
          {:class "flex items-center justify-end text-gray-500 text-md"}
          [:span {:class "font-bold"} "0%"]
          (svgs/stable)]]
        [:p
         {:class "text-3xl font-semibold text-center text-black"}
         (tools/amount->rands savings)]
        [:p {:class "text-lg text-center text-gray-800"} "Savings"]]]]]))
