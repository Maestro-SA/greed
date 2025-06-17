(ns com.greed.ui.components.tables
  (:require [com.greed.ui.components.svgs :as svgs]
            [com.greed.ui.components.forms :as forms]
            [com.greed.utilities.core :as utilities]))


(defn add-modal []
  [:div
   {:class "relative flex justify-center"}
   [:div
    {:role "dialog",
     :x-show "isAddButtonOpen",
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
       :aria-hidden "true"}]
     (forms/budget-item-form)]]])

(defn action-modal [item]
  [:div
   {:class "relative flex justify-center"}
   [:div
    {:role "dialog",
     :x-show "isActionModalOpen",
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
       :aria-hidden "true"}]
     (forms/budget-action-form item)]]])

(defn add-button
  "Render a button to add a new budget item."
  []
  [:div
   [:button
   {:class "relative align-middle select-none font-sans font-medium text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none w-10 max-w-[40px] h-10 max-h-[40px] rounded-lg text-xs text-blue-gray-500 hover:bg-blue-gray-500/10 active:bg-blue-gray-500/30",
    :type "button"
    "@click" "isAddButtonOpen = true"}
   [:span
    {:class "absolute top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2"}
    (svgs/add)]]
   (add-modal)])

(defn table-row
  "Render a table row with the given cells."
  [& {:keys [item salary-budget-item]}]
  (let [{:budget-item/keys [title amount]
         :or {title "Title"
              amount 0}}                  item
        salary-item? (= title (:budget-item/title salary-budget-item))]
    [:div
     [:tr
      [:td
       {:class "p-4 border-b border-blue-gray-50"}
       [:p
        {:class "block antialiased font-sans text-sm leading-normal text-blue-gray-900 font-normal"}
        title]]
      [:td
       {:class "p-4 border-b border-blue-gray-50"}
       [:p
        {:class "block antialiased font-sans text-sm leading-normal text-blue-gray-900 font-normal"}
        (utilities/amount->rands amount)]]
      [:td
       {:class "p-4 border-b border-blue-gray-50"}
       [:button
        {:class "relative align-middle select-none font-sans font-medium text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none w-10 max-w-[40px] h-10 max-h-[40px] rounded-lg text-xs text-blue-gray-500 hover:bg-blue-gray-500/10 active:bg-blue-gray-500/30",
         :type "button"
         :hidden salary-item?
         "@click" "isActionModalOpen = true"}
        [:span
         {:class "absolute top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2"}
         (svgs/action)]]]]
     (action-modal item)]))

(defn budget-table
  "Render a table with the given headers and rows."
  [{:keys [title items salary-budget-item]
    :or {salary-budget-item nil}}]
  [:div
    {:class "p-6 overflow-scroll px-0"}
    [:table
     {:class "mt-4 w-full min-w-max table-auto text-left"}
     [:thead
      [:tr
       [:th
        {:class "cursor-pointer border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors hover:bg-blue-gray-50"}
        [:p
         {:class "antialiased font-sans text-sm text-blue-gray-900 capitalize flex items-center justify-between gap-2 font-normal leading-none opacity-70"}
         title
         (svgs/sort!)]]
       [:th
        {:class "cursor-pointer border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors hover:bg-blue-gray-50"}
        [:p
         {:class "antialiased font-sans text-sm text-blue-gray-900 flex items-center justify-between gap-2 font-normal leading-none opacity-70"}
         "Amount"
         (svgs/sort!)]]
       [:th
        {:class "cursor-pointer border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors hover:bg-blue-gray-50"}
        [:p
         {:class "antialiased font-sans text-sm text-blue-gray-900 flex items-center justify-between gap-2 font-normal leading-none opacity-70"}
         "Actions"]]]]
     [:tbody
      (for [item items]
        (table-row
         :item item
         :salary-budget-item salary-budget-item))]]])
