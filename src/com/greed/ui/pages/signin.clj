(ns com.greed.ui.pages.signin
  (:require [com.greed.ui.components.forms :as forms]))

(defn form [ctx]
  [:.mt-20
   (forms/sign-in ctx)])
