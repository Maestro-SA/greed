(ns com.greed.components.headers
  (:require [com.greed.components.navs :as navs]
            [com.greed.components.cards :as cards]))

(defn home [& content] 
  [:header
   {:class "bg-white"}
   [:div
    {:class "container px-6 mx-auto"}
    (navs/nav)
    content]])

(defn pages [& content]
  [:header
   {:class "bg-white"}
   [:div
    {:class "container px-6 mx-auto"}
    (navs/nav)
    content]])