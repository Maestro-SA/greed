(ns com.greed.ui.components.alerts
  (:require [com.core :as c]
            [com.greed.ui.components.svgs :as svgs]))


(defn success [& {:keys [type]
                  :or {type :singin}}]
  [:div
   {:class "flex w-full max-w-sm overflow-hidden bg-white border-2 border-black rounded-lg shadow-md"}
   [:div
    {:class "flex items-center justify-center w-12 bg-emerald-800"}
    (svgs/success)]
   [:div
    {:class "px-4 py-2 -mx-3"}
    [:div
     {:class "mx-3"}
     [:span
      {:class "font-semibold text-2xl text-emerald-800"}
      "Success"]
     [:p
      {:class "text-md text-gray-600"}
      (if (= type :signin)
        "You are signed in!"
        "Your account was created!")]]]])


(defn info [& {:keys [alert]
               :or {alert nil}}]
  (let [config c/alert-config
        key (keyword "alert" alert)
        message (get config key)]
    [:div
     {:class "mx-auto w-full text-white pattern bg-blue-600 rounded-full shadow-xl"}
     [:div
      {:class "container flex justify-between px-6 py-4 mx-auto"}
      [:div
       {:class "flex items-center space-x-4"}
       (svgs/info)
       [:span
        message]]
      [:a
       {:href "/"
        :class "p-1 transition-colors duration-300 transform rounded-md hover:bg-opacity-25 hover:bg-gray-600 focus:outline-none"}
       (svgs/close)]]]))
