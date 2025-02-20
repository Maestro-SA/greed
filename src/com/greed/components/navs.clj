(ns com.greed.components.navs)

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