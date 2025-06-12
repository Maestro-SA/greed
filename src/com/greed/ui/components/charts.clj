(ns com.greed.ui.components.charts
  (:require [com.greed.tools.time :as time]))


(defn revenue []
  [:div
   {:class "flex items-center justify-center px-5 py-5"}
   [:div
    {:class "bg-white text-gray-800 rounded-xl shadow-xl py-5 px-5 w-full ",
     :x-data "{chartData:chartData()}",
     :x-init "chartData.fetch()"}
    [:div
     {:class "flex flex-wrap items-end"}
     [:div
      {:class "flex-1"}
      [:h3 {:class "text-lg font-semibold leading-tight"} "Income"]]
     [:div
      {:class "relative",
       "@click.away" "chartData.showDropdown=false"}
      [:button
       {:class "text-xs hover:text-gray-600 h-6 focus:outline-none",
        "@click" "chartData.showDropdown=!chartData.showDropdown"}
       [:span
        {:x-text "chartData.options[chartData.selectedOption].label"}]
       [:i {:class "ml-1 mdi mdi-chevron-down"}]]
      [:div
       {:x-show "chartData.showDropdown",
        :x-transition:leave-end "opacity-0 translate-y-4",
        :x-transition:leave-start "opacity-100 translate-y-0",
        :x-transition:enter "transition ease duration-300 transform",
        :class "bg-gray-800 shadow-lg rounded text-sm absolute top-auto right-0 min-w-full w-32 z-30 mt-1 -mr-3",
        :x-transition:enter-start "opacity-0 translate-y-2",
        :x-transition:enter-end "opacity-100 translate-y-0",
        :x-transition:leave "transition ease duration-300 transform"}
       [:span
        {:class "absolute top-0 right-0 w-3 h-3 bg-gray-700 transform rotate-45 -mt-1 mr-3"}]
       [:div
        {:class "bg-gray-700 rounded w-full relative z-10 py-1"}
        [:ul
         {:class "list-reset text-xs"}
         [:template {:x-for "(item,index) in chartData.options"}]]]]]]
    [:div
     {:class "flex flex-wrap items-end mb-5"}
     [:h4
      {:class "text-2xl lg:text-3xl text-black font-semibold leading-tight inline-block mr-2",
       :x-text "'$'+(chartData.data?chartData.data[chartData.date].total.comma_formatter():0)"}
      "0"]
     [:span
      {:class "inline-block",
       ::class "chartData.data&&chartData.data[chartData.date].upDown<0?'text-red-500':'text-green-500'"}
      [:span
       {:x-text "chartData.data&&chartData.data[chartData.date].upDown<0?'▼':'▲'"}
       "0"]
      [:span
       {:x-text "chartData.data?chartData.data[chartData.date].upDown:0"}
       "0"]
      "%"]]
    [:div [:canvas {:id "chart", :class "w-full"}]]]])

(defn chart []
  [:div
   {:class "py-16 px-4"}
   [:div
    {:class "flex flex-col items-center w-full p-6 pb-6 bg-gray-50 rounded-lg shadow-xl sm:p-8"}
    [:h2 {:class "text-4xl font-semibold font-giza"} "Monthly Revenue"]
    [:span {:class "text-2xl font-semibold text-gray-500"} time/year]
    [:div
     {:class "flex items-end flex-grow w-full mt-2 space-x-2 sm:space-x-3"}
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$37,500"]
      [:div
       {:class "relative flex justify-center w-full h-16 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-32 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Jan"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$45,000"]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-6 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-20 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Feb"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$47,500"]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-20 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Mar"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$50,000"]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-6 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-24 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Apr"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$47,500"]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-20 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "May"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$55,000"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-24 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Jun"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$60,000"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-16 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-20 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Jul"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$57,500"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-24 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Aug"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$67,500"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-10 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-32 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Sep"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$65,000"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full bg-green-400 h-28"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Oct"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$70,000"]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-40 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Nov"]]
     [:div
      {:class "relative flex flex-col items-center flex-grow pb-5 group"}
      [:span
       {:class
        "absolute top-0 hidden -mt-6 text-xs font-bold group-hover:block"}
       "$75,000"]
      [:div
       {:class "relative flex justify-center w-full h-12 bg-gray-200"}]
      [:div
       {:class "relative flex justify-center w-full h-8 bg-red-300"}]
      [:div
       {:class "relative flex justify-center w-full h-40 bg-green-400"}]
      [:span {:class "absolute bottom-0 text-xs font-bold"} "Dec"]]]
    [:div
     {:class "flex w-full mt-3"}
     [:div
      {:class "flex items-center ml-auto"}
      [:span {:class "block w-4 h-4 bg-gray-400"}]
      [:span {:class "ml-1 text-xs font-medium"} "Savings"]]
     [:div
      {:class "flex items-center ml-4"}
      [:span {:class "block w-4 h-4 bg-red-400"}]
      [:span {:class "ml-1 text-xs font-medium"} "Expenses"]]
     [:div
      {:class "flex items-center ml-4"}
      [:span {:class "block w-4 h-4 bg-green-400"}]
      [:span {:class "ml-1 text-xs font-medium"} "Income"]]]]])
