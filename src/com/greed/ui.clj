(ns com.greed.ui
  (:require [rum.core :as rum]
            [com.biffweb :as biff]
            [clojure.java.io :as io]
            [ring.util.response :as ring-response]
            [ring.middleware.anti-forgery :as csrf]
            [com.greed.settings :as settings]
            [com.greed.ui.components.headers :as headers]))

(defn static-path [path]
  (if-some [last-modified (some-> (io/resource (str "public" path))
                                  ring-response/resource-data
                                  :last-modified
                                  (.getTime))]
    (str path "?t=" last-modified)
    path))

(defn base [{:keys [::recaptcha] :as ctx} & body]
  (apply
   biff/base-html
   (-> ctx
       (merge #:base{:title settings/app-name
                     :lang "en-US"
                     :icon "/img/g.png"
                     :description (str settings/app-name " Description")
                     :image "/img/g.png"})
       (update :base/head (fn [head]
                            (concat [[:link {:rel "stylesheet" :href (static-path "/css/main.css")}]
                                     [:script {:src (static-path "/js/main.js")}]
                                     [:script {:src "https://unpkg.com/htmx.org@1.9.12"}]
                                     [:script {:src "https://unpkg.com/htmx.org@1.9.12/dist/ext/ws.js"}]
                                     [:script {:src "https://unpkg.com/hyperscript.org@0.9.8"}]
                                     [:script {:src "https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" :defer "defer"}]
                                     [:link {:href "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" :rel "stylesheet"}]
                                     [:script {:src "https://cdn.jsdelivr.net/npm/chart.js@4.4.8/dist/chart.umd.min.js"}]
                                     (when recaptcha
                                       [:script {:src "https://www.google.com/recaptcha/api.js"
                                                 :async "async" :defer "defer"}])]
                                    head))))
   body))

(defn page [ctx & body]
  (base
   ctx
   [:.pattern.h-screen 
    body]))

(defn app [ctx & body]
  (base
   ctx
   [:.flex.flex-col.h-screen
    [:.
     {:class "flex-none fixed top-0 left-0 w-72"}
     (headers/app ctx)]
    [:.
     {:class "flex-grow overflow-auto p-4 mt-20 md:mt-0 md:ml-72 md:bg-gray-50"}
     body]]))

(defn on-error [{:keys [status ex] :as ctx}]
  {:status status
   :headers {"content-type" "text/html"}
   :body (rum/render-static-markup
          (page
           ctx
           [:h1.text-lg.font-bold
            (if (= status 404)
              "Page not found."
              "Something went wrong.")]))})
