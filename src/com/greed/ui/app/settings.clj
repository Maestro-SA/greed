(ns com.greed.ui.app.settings
  (:require [com.greed.ui :as ui]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.components.skeleton :as skeleton]))


(defn page [ctx]
  (ui/app
   ctx
   [:div.container.mx-auto.space-y-4
    (headers/pages-heading ["Setting"])
    (skeleton/horizontal-card)]))
