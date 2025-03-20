(ns com.greed.components.charts
  (:require [com.greed.time :as time]))


(defn revenue []
  [:div
   {:class "min-w-screen min-h-screen bg-gray-900 flex items-center justify-center px-5 py-5"}
   [:div
    {:class "bg-gray-800 text-gray-500 rounded shadow-xl py-5 px-5 w-full sm:w-2/3 md:w-1/2 lg:w-1/3",
     :x-data "{cardOpen:false,cardData:cardData()}",
     :x-init "$watch('cardOpen', value => value?(cardData.countUp($refs.total,0,11602,null,0.8),cardData.sessions.forEach((el,i) => cardData.countUp($refs[`device${i}`],0,cardData.sessions[i].size,null,1.6))):null);setTimeout(()=>{cardOpen=true},100)"}
    [:div
     {:class "flex w-full"}
     [:h3
      {:class "text-lg font-semibold leading-tight flex-1"}
      "TOTAL SESSIONS"]
     [:div
      {:class "relative h-5 leading-none"}
      [:button
       {:class "text-xl text-gray-500 hover:text-gray-300 h-6 focus:outline-none",
        "@click.prevent" "cardOpen=!cardOpen"}
       [:i
        {:class "mdi",
         ::class "'mdi-chevron-'+(cardOpen?'up':'down')"}]]]]
    [:div
     {:class "relative overflow-hidden transition-all duration-500",
      :x-ref "card",
      :x-bind:style "`max-height:${cardOpen?$refs.card.scrollHeight:0}px; opacity:${cardOpen?1:0}`"}
     [:div
      [:div
       {:class "pb-4 lg:pb-6"}
       [:h4
        {:class "text-2xl lg:text-3xl text-white font-semibold leading-tight inline-block",
         :x-ref "total"}
        "0"]]
      [:div
       {:class "pb-4 lg:pb-6"}
       [:div
        {:class "overflow-hidden rounded-full h-3 bg-gray-800 flex transition-all duration-500",
         ::class "cardOpen?'w-full':'w-0'"}
        [:template {:x-for "(item,index) in cardData.sessions"}]]]
      [:div
       {:class "flex -mx-4"}
       [:template {:x-for "(item,index) in cardData.sessions"}]]]]]])

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
