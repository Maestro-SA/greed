(ns com.greed.pages.signin
  (:require [com.greed.components.forms :as forms]))

(defn form [ctx]
  [:.mt-20
   (forms/sign-in ctx)])