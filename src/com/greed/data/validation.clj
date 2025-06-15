(ns com.greed.data.validation
  (:require [com.greed.utilities.core :as tools]))


(defn valid-payday? [payday]
  (let [day (tools/->int payday)]
   (and (>= day 1)
       (<= day 31))))

(defn valid-amount? [amount]
  (let [amt (tools/->int amount)]
    (and (number? amt)
         (>= amt 0))))
