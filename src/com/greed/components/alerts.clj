(ns com.greed.components.alerts
  (:require [com.greed.components.svgs :as svgs]))


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