(ns com.greed.pages.home
  (:require [com.greed.components.cards :as cards]))

(defn page []
  [:.container.mx-auto.px-10
   [:div
   {:class "flex flex-col items-center py-6 lg:h-[36rem] lg:flex-row"}
   [:div
    {:class "lg:w-1/2"}
    [:h2
     {:class "text-2xl font-semibold text-black lg:text-4xl"}
     "Get Greedy."]
    [:h3
     {:class "mt-2 text-2xl font-semibold text-gray-800"}
     "Welcome to"
     [:span {:class "ml-2 text-4xl font-giza-stencil text-gray-600"} "greed."]]
    [:p
     {:class "mt-4 text-lg text-gray-800"}
     "Greed is a ditial platform that allows you to take control of your finances by providing you with tools to ensure your money does not go to greed. We provide you with tools to help you save, invest, and spend wisely."]]
   [:div
    {:class "flex mt-8 lg:w-1/2 lg:justify-end lg:mt-0"}
    (cards/testiminial
     {:img "/img/avatar.jpg"
      :title "We got Greedy"
      :body "Greed has helped me save money and invest wisely. I have been able to save money and invest in stocks and crypto. I have also been able to track my spending and see where my money is going."
      :author "Rebecca"})]]])