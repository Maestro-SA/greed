(ns com.greed.tools.core
  (:require [clojure.string :as string]))


(defn ->int
  "Converts a string to an integer.
   Parameters:
   - s: String to convert
   Returns integer value"
  [s]
   (try
    (Integer/parseInt s)
    (catch NumberFormatException _
      nil)))

(defn income->annual-income
  "Converts monthly income to annual income.
   Parameters:
   - monthly-income: Monthly income in Rand
   Returns annual income in Rand"
  [monthly-income]

  (* monthly-income 12))

(defn annual-income->monthly-income
  "Converts annual income to monthly income.
   Parameters:
   - annual-income: Annual income in Rand
   Returns monthly income in Rand"
  [annual-income]
  (/ annual-income 12))

(defn format-currency
  "Formats a number as currency.
   Parameters:
   - amount: Amount in cents
   Returns formatted currency string"
  [amount]
  (let [formatter (doto (java.text.NumberFormat/getInstance)
                  (.setMinimumFractionDigits 2)
                  (.setMaximumFractionDigits 2))]
  (str "R" (.format formatter amount))))

(defn amount->rands
  "Converts amount in cents to Rand.
   Parameters:
   - amount: Amount in cents
   Returns amount in Rand"
  [amount]
  (format-currency amount))

(defn ->percentage
  "Converts a double to a percentage.
   Parameters:
   - d: double to convert
   Returns percentage value"
  [d]
  (format "%.2f%%" (double d)))


(defn ->keyword
  "Converts a string to a keyword.
   Parameters:
   - s: String to convert
   Returns keyword value"
  [s]
  (-> s
      (string/lower-case)
      (string/trim)
      (string/replace #" " "-")
      (string/replace #"\." "-")
      (string/replace #"/" "-")
      (string/replace #"_" "-")
      keyword))

(comment

  (->keyword "Mastercard")

  )
