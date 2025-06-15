(ns com.greed.ui.app.profile
  (:require [com.greed.ui :as ui]
            [com.greed.ui.components.forms :as forms]
            [com.greed.ui.components.headers :as headers]))


(defn page [ctx]
  (ui/app
   ctx
   [:div.container.mx-auto.space-y-4
    (headers/pages-heading ["Account" "Settings"])
    (forms/account ctx)
    (forms/profile ctx)]))
