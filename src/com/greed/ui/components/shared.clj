(ns com.greed.ui.components.shared
  (:require [com.core :as c]
            [com.greed.data.core :as data]
            [com.greed.utilities.core :as utilities]))


(defn determine-placeholder [id user profile]
  (let [config c/common-config]
    (cond
      (contains? (:user/fields config) id)
      ((keyword (str "user/" id)) user)

      (contains? (:finances/fields config) id)
      ((keyword (str "finances/" id)) profile))))

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

(defn app-input [ctx & {:keys [id type label required?]
                        :or {required? false}}]
  (let [{:keys [session]} ctx
        user-id (:uid session)
        user (data/get-user ctx user-id)
        finances (data/get-finances ctx user-id)
        placeholder (determine-placeholder id user finances)]
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
      :placeholder placeholder
      :required required?}]]))

(defn app-select [ctx & {:keys [id label options required?]
                         :or {required? false}}]
  (let [{:keys [session]} ctx
        user-id (:uid session)
        user (data/get-user ctx user-id)
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
       :autocomplete (placeholder user)
       :required required?}
      (for [option options]
        [:option {:valiue option} (utilities/->string option)])]]))

(defn modal-input [& {:keys [id type label required?]
                      :or {required? false}}]
  [:div
   [:label
    {:for type,
     :class "text-sm text-gray-700"}
    label]
   [:label
    {:class "block mt-3", :for type}
    [:input
     {:class "block w-full px-4 py-2 text-sm text-gray-700 bg-white border border-gray-200 rounded-md focus:border-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40"
      :type type,
      :name id,
      :id id,
      :required required?}]]])

(defn modal-select [& {:keys [id label options required?]
                         :or {required? false}}]
  [:div
     [:label
      {:class "text-sm text-gray-700",
       :for type}
      label]
     [:select
      {:class "block w-full px-4 py-2 text-sm text-gray-700 bg-white border border-gray-200 rounded-md focus:border-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40",
       :id id,
       :name id
       :required required?}
      (for [option options]
        [:option {:valiue option} (utilities/->string option)])]])
