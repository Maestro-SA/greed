(ns com.greed.ui.core
  (:require [com.greed.utilities.tax :as tax]
            [com.greed.utilities.core :as utilities]))


(defn get-income-tax-data [profile]
  (let [{:profile/keys [income age]
         :or {income 25000 age 21}} profile
        annual-income (utilities/income->annual-income income)]
   (tax/calculate-income-tax annual-income age)))
