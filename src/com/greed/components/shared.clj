(ns com.greed.components.shared
  (:require [com.greed.data.helpers :as d.helpers]))

(defn input [& {:keys [id type label required?]
                :or {required? false}}]
  [:div
   {:class "w-full mt-4"}
   [:input
    {:class "block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-lg focus:border-blue-400 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300",
     :id id
     :name id,
     :type type,
     :autocomplete type,
     :placeholder label
     :required required?}]])

(defn app-input [ctx & {:keys [id type label]}]
  (let [{:keys [session]} ctx
        user-id (:uid session)
        user (d.helpers/get-user ctx user-id)
        placeholder (keyword (str "user/" id))]
   [:div
    [:label
     {:class "text-gray-700",
      :for type}
     label]
    [:input
     {:class "block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring",
      :id id,
      :name id
      :type type
      :autocomplete type,
      :placeholder (placeholder user)}]]))

(defn app-select [ctx & {:keys [id label options]
                         :or {options ["ABSA" "Capitec" "Discovery Bank" "FNB" "Nedbank" "Standard Bank"]}}]
  (let [{:keys [session]} ctx
        user-id (:uid session)
        user (d.helpers/get-user ctx user-id)
        placeholder (keyword (str "user/" id))]
    [:div
     [:label
      {:class "text-gray-700",
       :for type}
      label]
     [:select
      {:class "block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring",
       :id id,
       :name id
       :autocomplete (placeholder user)}
      (for [option options]
        [:option option])]]))
