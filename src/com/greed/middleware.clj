(ns com.greed.middleware
  (:require [com.biffweb :as biff :refer [q]]
            [muuntaja.middleware :as muuntaja]
            [ring.middleware.anti-forgery :as csrf]
            [ring.middleware.defaults :as rd]
            [com.greed.data.core :as data]
            [com.greed.authentication :as auth]))

(defn wrap-redirect-signed-in [handler]
  (fn [{:keys [session] :as ctx}]
    (if (some? (:uid session))
      {:status 303
       :headers {"location" "/app"}}
      (handler ctx))))

(defn wrap-signed-in [handler]
  (fn [{:keys [session] :as ctx}]
    (if (some? (:uid session))
      (handler ctx)
      {:status 303
       :headers {"location" "/signin?error=not-signed-in"}})))

(defn wrap-authenticate [handler]
  (fn [{:keys [uri db] :as ctx}]
    (let [error-location (if (= "/authenticate/signup" uri)
                           "/signup?error=invalid-email"
                           "/signin?error=invalid-credentials")]
      (if (auth/authenticate! ctx)
        (handler ctx)
        {:status 303
         :headers {"location" error-location}}))))

(defn save-user [ctx]
  (let [user-id (data/get-user-id ctx)
        user (data/get-user ctx user-id)]
    (if user
      (data/update-user ctx)
      (data/upsert-user ctx)))
  {:status 303
   :headers {"location" "/app?alert=user-saved"}})

(defn save-finances [ctx]
  (let [user-id (data/get-user-id-from-session ctx)
        finances (data/get-finances ctx user-id)]
    (if finances
      (data/update-finances ctx)
      (data/upsert-finances ctx)))
  {:status 303
   :headers {"location" "/app?alert=finances-saved"}})

(defn create-budget-item [ctx]
  (data/upsert-budget-item ctx)
  {:status 303
   :headers {"location" "/app/tools/budget-tracker?alert=budget-item-saved"}})

(defn save-budget-item [ctx]
  (let [user-id (data/get-user-id-from-session ctx)
        budget-item (data/get-budget-item ctx user-id)]
    (if budget-item
      (data/update-budget-item ctx)
      (data/upsert-budget-item ctx)))
  {:status 303
   :headers {"location" "/app/tools/budget-tracker?alert=budget-item-saved"}})

(defn delete-budget-item [ctx]
  (data/delete-budget-item ctx)
  {:status 303
   :headers {"location" "/app/tools/budget-tracker?alert=budget-item-deleted"}})

(defn logout [{:keys [session]}]
  {:status 303
   :headers {"location" "/"}
   :session (dissoc session :uid)})

;; Stick this function somewhere in your middleware stack below if you want to
;; inspect what things look like before/after certain middleware fns run.
(defn wrap-debug [handler]
  (fn [ctx]
    (let [response (handler ctx)]
      (println "REQUEST")
      (biff/pprint ctx)
      (def ctx* ctx)
      (println "RESPONSE")
      (biff/pprint response)
      (def response* response)
      response)))

(defn wrap-site-defaults [handler]
  (-> handler
      biff/wrap-render-rum
      biff/wrap-anti-forgery-websockets
      csrf/wrap-anti-forgery
      biff/wrap-session
      muuntaja/wrap-params
      muuntaja/wrap-format
      (rd/wrap-defaults (-> rd/site-defaults
                            (assoc-in [:security :anti-forgery] false)
                            (assoc-in [:responses :absolute-redirects] true)
                            (assoc :session false)
                            (assoc :static false)))))

(defn wrap-api-defaults [handler]
  (-> handler
      muuntaja/wrap-params
      muuntaja/wrap-format
      (rd/wrap-defaults rd/api-defaults)))

(defn wrap-base-defaults [handler]
  (-> handler
      biff/wrap-https-scheme
      biff/wrap-resource
      biff/wrap-internal-error
      biff/wrap-ssl
      biff/wrap-log-requests))
