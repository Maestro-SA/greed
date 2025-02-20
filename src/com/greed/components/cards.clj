(ns com.greed.components.cards)


(defn testiminial [& {:keys [img title body author]}]
  [:div
   {:class "w-full max-w-md px-8 py-4 mt-16 bg-white border-2 border-black rounded-lg shadow-lg"}
   [:div
    {:class "flex justify-center -mt-16 md:justify-end"}
    [:img
     {:class "object-cover w-20 h-20 border-2 border-black rounded-full",
      :alt "Testimonial avatar",
      :src img}]]
   [:h2
    {:class "mt-2 text-2xl font-giza font-semibold text-gray-800 md:mt-0"}
    title]
   [:p
    {:class "mt-2 text-md text-gray-600"}
    body]
   [:div
    {:class "flex justify-end mt-4"}
    [:a
     {:href "#",
      :class "text-lg font-medium text-gray-400",
      :tabindex "0",
      :role "link"}
     author]]])