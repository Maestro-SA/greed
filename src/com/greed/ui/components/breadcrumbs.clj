(ns com.greed.ui.components.breadcrumbs
  (:require [com.greed.ui.components.svgs :as svgs]))


(defn breadcumb [breadcrumbs]
  [:div
   {:class "flex items-center overflow-x-auto whitespace-nowrap"}
   (svgs/home)
   (for [breadcrumb breadcrumbs]
     [:a
      {:href "#", :class "text-gray-600 dark:text-gray-200"}
      [:span
       {:class "mx-5 text-gray-500"}
       (svgs/->next)]
      [:a
       {:href "#",
        :class "text-gray-600 hover:underline"}
       breadcrumb]])])
