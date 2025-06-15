(ns com.greed.ui.app.finances
  (:require [com.greed.ui :as ui]
            [com.greed.data.core :as data]
            [com.greed.ui.components.headers :as headers]))


(defn page [{:keys [session] :as ctx}]
  (let [user-id (:uid session)
        profile (data/get-profile ctx user-id)
        income (:profile/income profile)]
    (ui/app
     ctx
     [:div.container.mx-auto.space-y-4
      (headers/pages-heading ["Finances"])
      ])))
