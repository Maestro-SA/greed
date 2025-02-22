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
                         card-type (svgs/visa),
                         income 500,
                         expenses 0}}]
  (let [balance (- income expenses)]
    [:div
     {:class "container py-16 px-4"}
     [:div
      {:class "flex flex-col space-y-8 w-full px-16 max-w-xl"}
      [:div
       {:class "bg-gradient-to-tl from-gray-600 to-black text-white h-56 w-96 p-6 rounded-xl shadow-md"}
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
