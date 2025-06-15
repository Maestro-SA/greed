(ns com.greed.ui.components.buttons)


(defn modal-button [& {:keys [title]}]
  [:section
   {:class "mt-8 container mx-auto flex items-center justify-around"}
   [:div
    [:div
     {:class "flex rounded border-b-2 border-gray-200 overflow-hidden"}
     [:button
      {"@click" "isOpen = true"
       :class "block text-white text-sm shadow-border bg-gray-600 hover:bg-gray-800 text-sm py-1 px-2 tracking-wide uppercase"}
      title]
     [:div
      {:class "bg-gray-400 shadow-border p-1"}
      [:div
       {:class "px-1"}
       "+"]]]]])
