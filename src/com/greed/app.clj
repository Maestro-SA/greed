(ns com.greed.app
  (:require [com.greed.middleware :as mid]
            [com.greed.ui.app.tools :as tools]
            [com.greed.ui.app.profile :as profile]
            [com.greed.ui.app.settings :as settings]
            [com.greed.ui.tools.income-tax-calculator :as income-tax-calculator]
            [com.greed.ui.tools.budget-tracker :as budget-tracker]
            [com.greed.ui.app.finances :as finances]
            [com.greed.ui.app.calendar :as calendar]
            [com.greed.ui.app.dashboard :as dashboard]))



(def module
  {:routes [["/app" {:middleware [mid/wrap-signed-in]}
             ["" {:get dashboard/page}]
             ["/finances" {:get finances/page}]
             ["/calendar" {:get calendar/page}]
             ["/tools"
              ["" {:get tools/page}]
              ["/income-tax-calculator" {:post income-tax-calculator/page}]
              ["/budget-tracker"
               ["/" {:get budget-tracker/page}]
               ["/add-finance-item" {:post mid/upsert-finance-item}]]]
             ["/settings" {:get settings/page}]
             ["/profile" {:get profile/page}]
             ["/save-user" {:post mid/update-user}]
             ["/save-profile" {:post mid/upsert-profile}]]]})
