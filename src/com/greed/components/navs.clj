(ns com.greed.components.navs 
  (:require [com.greed.data.helpers :as d.helpers]
            [com.greed.components.svgs :as svgs]))

(defn nav []
  [:nav
   {:class "flex flex-col py-6 sm:flex-row sm:justify-between sm:items-center"}
   [:a
    {:href "/"}
    [:span
     {:class "text-6xl font-giza font-semibold text-black md:text-8xl"}
     "greed."]]
   [:div
    {:class "flex items-center mt-2 -mx-2 sm:mt-0"}
    [:a
     {:href "/signin",
      :class "px-3 py-1 text-sm font-semibold text-black transition-colors duration-300 transform border-2 border-black rounded-md hover:bg-black hover:text-white"}
     "Sign In"]
    [:a
     {:href "/signup",
      :class "px-3 py-2 mx-2 text-sm font-semibold text-white transition-colors duration-300 transform bg-black rounded-md hover:bg-gray-800"}
     "Sign Up"]]])

(defn sidebar [{:keys [session] :as ctx}]
  (let [user-id (:uid session)
        {:user/keys [firstname
                     lastname]} (d.helpers/get-user ctx user-id)]
    [:aside
     {:class "flex flex-col w-72 h-screen px-4 py-8 overflow-y-auto bg-white border-r"}
     [:.
      [:span
       {:class "text-6xl font-giza font-semibold text-black md:text-8xl"}
       "greed."]]
     [:div
      {:class "relative mt-6"}
      [:span
       {:class "absolute inset-y-0 left-0 flex items-center pl-3"}
       (svgs/search)]
      [:input
       {:type "text",
        :class "w-full py-2 pl-10 pr-4 text-gray-700 bg-white border rounded-md focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring",
        :placeholder "Search"}]]
     [:div
      {:class "flex flex-col justify-between flex-1 mt-6"}
      [:nav
       [:a
        {:class "flex items-center px-4 py-2 text-gray-700 bg-gray-100 rounded-md",
         :href "/"}
        (svgs/dashboard)
        [:span {:class "mx-4 font-medium"} "Dashboard"]]
       [:hr {:class "my-6 border-gray-200"}]
       [:a
        {:class "flex items-center px-4 py-2 mt-5 text-gray-600 transition-colors duration-300 transform rounded-md hover:bg-gray-100 hover:text-gray-700",
         :href "/app/settings"}
        (svgs/search)
        [:span {:class "mx-4 font-medium"} "Settings"]]]
      [:div
         {:class "flex items-center justify-between mt-6"}
       [:a
        {:href "/app/profile",
         :class "flex item-center justify-between px-2"}
        [:span
         {:class "font-medium text-sm text-gray-800 px-2 py-1 border-b-2 border-black hover:border-2 hover:rounded-md transition duration-300"}
         (str firstname " " lastname)]]
       [:a
        {:href "/logout"
         :class "flex items-center px-2 py-1 border-2 border-black rounded-md hover:bg-black hover:text-white"}
        (svgs/logout)]]]]))
