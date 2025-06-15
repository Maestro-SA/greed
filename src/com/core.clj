(ns com.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))


(defn read-edn-resource
  "Read an EDN file from the resources directory.
   File should be in one of the paths specified in deps.edn:
   :paths [\"src\" \"resources\" \"target/resources\"]"
  [resource-path]
  (if-let [resource (io/resource resource-path)]
    (-> resource
        slurp
        edn/read-string)
    (throw (IllegalArgumentException.
            (str "Resource not found: " resource-path)))))

(def tax-config
  (read-edn-resource "config/tax.edn"))

(def common-config
  (read-edn-resource "config/common.edn"))

(def alert-config
  (->> common-config
       (filter #(= "alert" (namespace (key %))))
       (into {})))

(comment

  tax-config
  common-config
  alert-config

  (get alert-config :alert/budget-item-saved)

  (:banking/banks common-config)

  (:finance/types common-config)

  (let [annual-income 1000000]
    (->> tax-config
         :tax-brackets
         (filter #(<= (:threshold %) annual-income))
         last))

  )
