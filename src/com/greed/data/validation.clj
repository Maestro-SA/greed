(ns com.greed.data.validation
  (:require [com.greed.tools.core :as tools]))


(defn valid-payday? [payday]
  (let [day (tools/->int payday)]
   (and (>= day 1)
       (<= day 31))))
