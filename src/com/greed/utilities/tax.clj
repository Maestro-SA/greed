(ns com.greed.utilities.tax
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
        ;; Rebates reduce tax (primary for all, secondary 65+, tertiary 75+). Defaults match SARS 2026.
        rebates-cfg (or (:rebates tax) {})
        thresholds-cfg (or (:thresholds tax) {})
        primary (get rebates-cfg :primary 17820)
        secondary (get rebates-cfg :secondary 9765)
        tertiary (get rebates-cfg :tertiary 3249)
        tax-threshold (cond
                        (>= age 75) (get thresholds-cfg :age-75-plus 0)
                        (>= age 65) (get thresholds-cfg :age-65-plus 0)
                        :else (get thresholds-cfg :under-65 0))
        ;; Determine which tax bracket applies
        brackets (or (:tax-brackets tax) [])
        applicable-bracket (->> brackets
                                (filter #(<= (get % :threshold 0) (or annual-income 0)))
                                last)
        ;; Calculate gross tax (guard when no bracket or missing config)
        excess (if applicable-bracket
                 (- (or annual-income 0) (get applicable-bracket :threshold 0))
                 0)
        gross-tax (if applicable-bracket
                    (+ (get applicable-bracket :base-tax 0)
                       (* excess (get applicable-bracket :rate 0)))
                    0)
        ;; Apply rebates based on age
        rebates (cond
                  (>= age 75) (+ primary secondary tertiary)
                  (>= age 65) (+ primary secondary)
                  :else primary)

        ;; Final tax calculation
        net-tax (max 0 (- gross-tax (or rebates 0)))]

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
