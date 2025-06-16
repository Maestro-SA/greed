(ns com.greed.ui.components.lists
  (:require [com.greed.ui.components.svgs :as svgs]
            [com.greed.utilities.core :as utilities]
            [com.greed.ui.components.forms :as forms]))


(defn budget-modal []
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
     (forms/budget-item-form)]]])


(defn budget-list-item [item]
  (let [{:budget-item/keys [title amount]
         :or {title "Title"
              amount 0}}                  item]
    [:div
     {:class "grid grid-cols-6 gap-4 py-2 border-b mt-4"}
     [:div
      {:class "col-span-3"}
      [:p {:class "text-gray-800"}
       title]]
     [:div
      {:class "col-span-2"}
      [:p {:class "font-light text-end text-gray-800"}
       (utilities/amount->rands amount)]]
     [:div
      {:class "col-span-1"}
      [:a
       {:href (str "/app/finances/delete-budget-item?budget-item-id=" (:xt/id item))
        :class "flex justify-center text-gray-500 hover:text-red-600"}
       (svgs/x)]]]))

(defn budget-list [& {:keys [title items]
                       :or {title "title"
                            items []}}]
  [:div
   [:div
    {:class "border-t border-gray-200 p-4 py-8"}
    [:div
     {:class "bg-white mx-auto max-w-sm shadow-lg rounded-lg overflow-hidden"}
     [:div
      {:class "sm:flex sm:items-center px-2 py-4"}
      [:div
       {:class "flex-grow"}
       [:h3 {:class "font-normal px-2 py-3 leading-tight capitalize"}
        title]
       [:input
        {:type "text",
         :placeholder (str "Search for " title),
         :class "my-2 w-full text-sm bg-gray-100 text-gray-800 rounded h-10 p-3 focus:outline-none"}]
       [:div
        {:class "w-full"}
        (for [item items]
          (budget-list-item item))]]]]]])
