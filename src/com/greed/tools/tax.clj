(ns com.greed.tools.tax
  (:require [com.core :as c]))

(defn calculate-income-tax
  "Calculates South African income tax for individuals based on SARS tax rates.

   Parameters:
   - annual-income: Annual taxable income in Rand
   - age: Age of the taxpayer in years

   Returns a map with tax calculation details"
  [annual-income age]

  ; Input validation
  (when (or (not (number? annual-income)) (neg? annual-income))
    (throw (IllegalArgumentException. "Annual income must be a positive number")))

  (when (or (not (number? age)) (neg? age))
    (throw (IllegalArgumentException. "Age must be a positive number")))


  (let [tax c/tax-config
        ;; Primary, secondary and tertiary rebates for 2024/2025
        primary-rebate 17235
        secondary-rebate 9444  ;; For persons 65 and older
        tertiary-rebate 3145   ;; For persons 75 and older
        ;; Calculate tax threshold based on age
        tax-threshold (cond
                        (>= age 75) 165000
                        (>= age 65) 135000
                        :else 91250)
        ;; Determine which tax bracket applies
        applicable-bracket (->> tax
                                :tax-brackets
                                (filter #(<= (:threshold %) annual-income))
                                last)
        #_(last (filter #(<= (:threshold %) annual-income) tax-brackets))

        ;; Calculate gross tax
        excess (- annual-income (:threshold applicable-bracket))
        gross-tax (+ (:base-tax applicable-bracket) (* excess (:rate applicable-bracket)))
        ;; Apply rebates based on age
        rebates (cond
                  (>= age 75) (+ primary-rebate secondary-rebate tertiary-rebate)
                  (>= age 65) (+ primary-rebate secondary-rebate)
                  :else primary-rebate)

        ;; Final tax calculation
        net-tax (max 0 (- gross-tax rebates))]

    ;; Return detailed tax calculation
    {:annual-income annual-income
     :age age
     :tax-threshold tax-threshold
     :gross-tax gross-tax
     :rebates rebates
     :net-tax net-tax
     :net-income (- annual-income net-tax)
     :effective-rate (if (pos? annual-income)
                       (* 100 (/ net-tax annual-income))
                       0)}))


(comment

  (calculate-income-tax 600000 28)

  )
