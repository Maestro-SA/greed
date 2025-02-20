(ns com.greed.components.shared)

(defn input [& {:keys [type label]}]
  [:div
   {:class "w-full mt-4"}
   [:input
    {:class "block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-lg focus:border-blue-400 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300",
     :type type,
     :name type,
     :autocomplete type,
     :placeholder label}]])