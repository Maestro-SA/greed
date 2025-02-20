(ns com.greed.components.headers
  (:require [com.greed.components.navs :as navs]))

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