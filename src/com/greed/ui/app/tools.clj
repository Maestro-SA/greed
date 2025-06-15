(ns com.greed.ui.app.tools
  (:require [com.greed.ui :as ui]
            [com.greed.ui.tools.core :as tools]
            [com.greed.ui.components.headers :as headers]
            [com.greed.ui.tools.income-tax-calculator :as income-tax-calculator]))


(defn page [ctx]
  (ui/app
   ctx
   [:div.container.mx-auto
    {:x-data "{ isOpen: false }"}
    (headers/pages-heading ["Tools"])
    (tools/tools)
    (income-tax-calculator/income-tax-modal)]))
