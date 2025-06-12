(ns com.greed.ui.pages.signup
  (:require [com.greed.ui.components.forms :as forms]))

(defn form [ctx]
   [:.mt-20
   (forms/sign-up ctx)])
