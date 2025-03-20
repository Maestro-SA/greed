(ns com.greed.components.headers
  (:require [com.greed.components.navs :as navs]
            [com.greed.components.breadcrumbs :as breadcrumbs]))

(defn pages [& content] 
  [:header
   {:class "bg-white"}
   [:div
    {:class "container px-6 mx-auto"}
    (navs/nav)
    content]])

(defn app [ctx]
  [:header
   {:class "bg-white"}
   [:div 
    (navs/sidebar ctx)]])

(defn home-heading [& {:keys [breadcrumbs user]
                  :or {breadcrumbs ["None"]}}]
  [:div.mx-4
   (breadcrumbs/breadcumb breadcrumbs)
   [:div.text-2xl.font-semibold
    "Dashboard - "
    [:span.text-gray-600.text-3xl.font-giza
     (:user/firstname user)]]])

(defn pages-heading [breadcrumbs]
  [:div.mx-4
   (breadcrumbs/breadcumb breadcrumbs)])
