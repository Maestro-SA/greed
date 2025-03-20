(ns com.greed.time
  (:import [java.time LocalDate]))


(def date (LocalDate/now))

(def year (.getYear date))

(def month (.getMonth date))

(def day (.getDayOfMonth date))

(def days-in-month (.lengthOfMonth (LocalDate/of year month day)))

(def day-of-week (.getDayOfWeek date))

(comment

  year

  date

  month

  day

  days-in-month

  day-of-week

  )
