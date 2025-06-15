(ns com.greed.ui.app.calendar
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.components.calendars :as calendars]))


(defn page [{:keys [session] :as ctx}]
  (let [user-id (:uid session)
        finances (data/get-finances ctx user-id)
        payday (:finances/payday finances)]
    (ui/app
     ctx
     [:div.container.mx-auto
      (headers/pages-heading ["Calendar"])
      (calendars/calendar payday)])))
