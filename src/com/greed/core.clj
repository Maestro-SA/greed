(ns com.greed.core
  (:require [com.greed.utilities.tax :as tax]
            [com.greed.utilities.core :as utilities]))


(defn get-income-tax-data [user finances]
  (let [{:user/keys [age]
         :or {age 21}}    user
        {:finances/keys [salary]
         :or {salary 0}}     finances
        annual-income (utilities/income->annual-income salary)]
   (tax/calculate-income-tax annual-income age)))
