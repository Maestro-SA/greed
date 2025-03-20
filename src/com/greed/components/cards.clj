(ns com.greed.components.cards
  (:require [com.greed.components.svgs :as svgs]))


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

(defn bank-card [& {:keys [bank card-type income expenses]
                    :or {bank "Capitec"
                         card-type "Visa",
                         income 500,
                         expenses 0}}]
  (let [balance (- (Integer/parseInt income) (Integer/parseInt expenses))
        card-type (case card-type
                    "Visa" (svgs/visa)
                    "Mastercard" (svgs/mastercard))]
    [:div
     {:class "py-4 px-4"}
     [:div
      [:div
       {:class "h-48 w-80 p-6 rounded-xl shadow-md bg-gradient-to-tl from-gray-600 to-black text-white"}
       [:div
        {:class "h-full flex flex-col justify-between"}
        [:div
         {:class "flex items-start justify-between space-x-4"}
         [:div
          {:class "text-xl font-semibold tracking-tigh"}
          bank]
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
         [:div {:class "text-2xl font-semibold"} (str "R" balance)]]]]]]))

(defn account-stats [& {:keys [income expenses savings]
                        :or {income 500,
                             expenses 0
                             savings 200}}]
  [:div
   {:class "px-4 py-4"}
   [:div
    {:class "sm:grid sm:h-48 sm:grid-flow-row sm:gap-4 sm:grid-cols-3"}
    [:div
     {:class "flex flex-col justify-center px-4 py-4 pattern  border border-gray-300 rounded-xl"}
     [:div
      [:div
       [:p
        {:class "flex items-center justify-end text-green-500 text-md"}
        [:span {:class "font-bold"} "6%"]
        (svgs/uptrend)]]
      [:p
       {:class "text-3xl font-semibold text-center text-black"}
       income]
      [:p {:class "text-lg text-center text-green-800"}
       "Income"]]]
    [:div
     {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern border border-gray-300 rounded-xl sm:mt-0"}
     [:div
      [:div
       [:p
        {:class "flex items-center justify-end text-red-500 text-md"}
        [:span {:class "font-bold"} "6%"]
        (svgs/downtrend)]]
      [:p
       {:class "text-3xl font-semibold text-center text-black"}
       expenses]
      [:p {:class "text-lg text-center text-red-800"} "Expenses"]]]
    [:div
     {:class "flex flex-col justify-center px-4 py-4 mt-4 pattern border border-gray-300 rounded-xl sm:mt-0"}
     [:div
      [:div
       [:p
        {:class "flex items-center justify-end text-gray-500 text-md"}
        [:span {:class "font-bold"} "0%"]
        (svgs/stable)]]
      [:p
       {:class "text-3xl font-semibold text-center text-black"}
       savings]
      [:p {:class "text-lg text-center text-gray-800"} "Savings"]]]]])
