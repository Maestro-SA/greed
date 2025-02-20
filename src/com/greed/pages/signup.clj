(ns com.greed.pages.signup
  (:require [com.greed.components.forms :as forms]))

(defn form [ctx]
   [:.mt-20
   (forms/sign-up ctx)])