(ns com.greed.ui.components.calendars)


(defn calendar [payday]
  [:div
   {:class "antialiased sans-serif"}
   [:div
    {:x-data (str "app(" payday ")"),
     :x-init "initDate(); getNoOfDays();",
     :x-cloak ""}
    [:div
     {:class "container mx-auto px-4 py-2"}
     [:div
      {:class "bg-white rounded-lg shadow overflow-hidden"}
      [:div
       {:class "flex items-center justify-between py-2 px-6"}
       [:div
        [:span
         {:x-text "MONTH_NAMES[month]",
          :class "text-4xl font-giza font-bold text-gray-800"}]
        [:span
         {:x-text "year",
          :class "ml-1 text-3xl text-gray-600 font-giza"}]]]
      [:div
       {:class "-mx-1 -mb-1"}
       [:div
        {:class "flex flex-wrap"}
        [:template {:x-for "(day, index) in DAYS",
                    :key "index"}
         [:div {:class "px-2 py-2 w-[14.28%]"}
          [:div {:class "text-gray-600 text-sm text-center"}
           [:span {:x-text "day"}]]]]]
       [:div
        {:class "flex flex-wrap border-t border-l"}
        [:template {:x-for "blankday in blankdays"}
         [:div {:class "text-center border-r border-b px-4 pt-2 h-[120px] w-[14.28%]"}]]
        [:template {:x-for "(date, dateIndex) in no_of_days",
                    :key "dateIndex"}
         [:div {:class "text-center border-r border-b px-4 pt-2 h-[60px] sm:h-[120px] w-[14.28%]"}
          [:div {:class "inline-flex w-6 h-6 items-center justify-center cursor-pointer text-center leading-none rounded-full transition ease-in-out duration-100"}
           [:div {:class "flex flex-col items-center justify-center cursor-pointer p-2 text-xs sm:text-md text-center leading-none transition ease-in-out duration-100"
                  :x-bind:class "date === day ? 'bg-blue-500 text-white rounded-full w-full h-full flex items-center justify-center' : ''"}
            [:div.m-2
             [:span {:x-text "date"}]
             [:template {:x-if "date === payday"}
              [:span {:class "mt-1 sm:mt-2 inline-block px-1 py-0.5 sm:px-2 text-xs sm:text-sm sm:font-semibold rounded-full bg-gradient-to-r from-green-500 to-cyan-500 text-white"}
               "Payday"]]]]]]]]]]]
    (comment "Modal")
    [:div
     {:class "fixed z-40 top-0 right-0 left-0 bottom-0 h-full w-full",
      :x-show.transition.opacity "openEventModal"}
     [:div
      {:class "p-4 max-w-xl mx-auto relative absolute left-0 right-0 overflow-hidden mt-24"}
      [:div
       {:class "shadow absolute right-0 top-0 w-10 h-10 rounded-full bg-white text-gray-500 hover:text-gray-800 inline-flex items-center justify-center cursor-pointer",
        :x-on:click "openEventModal = !openEventModal"}
       [:svg
        {:class "fill-current w-6 h-6",
         :xmlns "http://www.w3.org/2000/svg",
         :viewBox "0 0 24 24"}
        [:path
         {:d "M16.192 6.344L11.949 10.586 7.707 6.344 6.293 7.758 10.535 12 6.293 16.242 7.707 17.656 11.949 13.414 16.192 17.656 17.606 16.242 13.364 12 17.606 7.758z"}]]]
      [:div
       {:class "shadow w-full rounded-lg bg-white overflow-hidden w-full block p-8"}
       [:h2
        {:class "font-bold text-2xl mb-6 text-gray-800 border-b pb-2"}
        "Add Event Details"]
       [:div
        {:class "mb-4"}
        [:label
         {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"}
         "Event title"]
        [:input
         {:class "bg-gray-200 appearance-none border-2 border-gray-200 rounded-lg w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-blue-500",
          :type "text",
          :x-model "event_title"}]]
       [:div
        {:class "mb-4"}
        [:label
         {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"}
         "Event date"]
        [:input
         {:class "bg-gray-200 appearance-none border-2 border-gray-200 rounded-lg w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-blue-500",
          :type "text",
          :x-model "event_date",
          :readonly ""}]]
       [:div
        {:class "inline-block w-64 mb-4"}
        [:label
         {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"}
         "Select a theme"]
        [:div
         {:class "relative"}
         [:select
          {"@change" "event_theme = $event.target.value;",
           :x-model "event_theme",
           :class "block appearance-none w-full bg-gray-200 border-2 border-gray-200 hover:border-gray-500 px-4 py-2 pr-8 rounded-lg leading-tight focus:outline-none focus:bg-white focus:border-blue-500 text-gray-700"}
          [:template {:x-for "(theme, index) in themes"}
           [:option {:x-text "theme"}]]]
         [:div
          {:class "pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"}
          [:svg
           {:class "fill-current h-4 w-4",
            :xmlns "http://www.w3.org/2000/svg",
            :viewBox "0 0 20 20"}
           [:path
            {:d "M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"}]]]]]
       [:div
        {:class "mt-8 text-right"}
        [:button
         {:type "button",
          :class "bg-white hover:bg-gray-100 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded-lg shadow-sm mr-2",
          "@click" "openEventModal = !openEventModal"}
         "Cancel"]
        [:button
         {:type "button",
          :class "bg-gray-800 hover:bg-gray-700 text-white font-semibold py-2 px-4 border border-gray-700 rounded-lg shadow-sm",
          "@click" "addEvent()"}
         "Save Event"]]]]]
    (comment "/Modal")]])
