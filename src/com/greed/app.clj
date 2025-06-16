(ns com.greed.app
  (:require [com.greed.middleware :as mid]
            [com.greed.ui.app.tools :as tools]
            [com.greed.ui.app.account :as account]
            [com.greed.ui.app.settings :as settings]
            [com.greed.ui.app.finances :as finances]
            [com.greed.ui.app.calendar :as calendar]
            [com.greed.ui.app.dashboard :as dashboard]
            [com.greed.ui.tools.budget-tracker :as budget-tracker]
            [com.greed.ui.tools.income-tax-calculator :as income-tax-calculator]))



(def module
  {:routes [["/app" {:middleware [mid/wrap-signed-in]}
             ["" {:get dashboard/page}]
             ["/finances"
              ["/" {:get finances/page}]
              ["/create-budget-item" {:post mid/create-budget-item}]
              ["/delete-budget-item" {:get mid/delete-budget-item}]]
             ["/calendar" {:get calendar/page}]
             ["/tools"
              ["" {:get tools/page}]
              ["/income-tax-calculator" {:post income-tax-calculator/page}]]
             ["/settings" {:get settings/page}]
             ["/profile" {:get account/page}]
             ["/save-user" {:post mid/save-user}]
             ["/save-finances" {:post mid/save-finances}]]]})
