(ns com.greed.data.core
  (:require [com.biffweb :as biff :refer [q]]
            [clojure.tools.logging :as logger]
            [com.greed.utilities.tax :as tax]
            [com.greed.utilities.core :as utilities]
            [com.greed.data.validation :as validation]))


(defn get-users [{:keys [biff/db]}]
  (q db
     '{:find (pull user [*])
       :where [[user :user/email]]}))

(defn get-user-id [{:keys [biff/db params]}]
  (biff/lookup-id db :user/email (:email params)))

(defn get-user-id-from-session [{:keys [session]}]
  (:uid session))

(defn get-user [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull user [*])
              :in [user-id]
              :where [[user :xt/id user-id]]}
            user-id)))

(defn get-finances [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull finances [*])
              :in [user-id]
              :where [[finances :finances/user-id user-id]]}
            user-id)))

(defn get-budget-item [{:keys [biff/db]} budget-item-id]
  (first (q db
            '{:find (pull budget-item [*])
              :in [budget-item-id]
              :where [[budget-item :xt/id budget-item-id]]}
            budget-item-id)))

(defn get-budget-items [{:keys [biff/db]} user-id]
  (q db
     '{:find (pull budget-item [*])
       :in [user-id]
       :where [[budget-item :budget-item/user-id user-id]]}
     user-id))

(defn get-salary-budget-item [{:keys [biff/db]} user-id]
  (first (q db
            '{:find (pull budget-item [*])
              :in [user-id]
              :where [[budget-item :budget-item/user-id user-id]
                      [budget-item :budget-item/title "Salary"]]}
            user-id)))

(defn upsert-user [{:keys [params] :as ctx}]
  (let [user-id (java.util.UUID/randomUUID)]
    (logger/info "Creating user...")
    (biff/submit-tx ctx
                    [{:db/doc-type :user
                      :xt/id user-id
                      :user/email (:email params)
                      :user/password (:password params)
                      :user/firstname (:firstname params)
                      :user/lastname (:lastname params)
                      :user/age (utilities/->int (:age params))}])))

(defn update-user [{:keys [params] :as ctx}]
  (let [user-id (get-user-id ctx)
        user (get-user ctx user-id)]
    (if user
      (do
        (logger/info "Updating user...")
        (biff/submit-tx ctx
                      [{:db/doc-type :user
                        :xt/id user-id
                        :db/op :update
                        :user/email (:email params)
                        :user/password (:password params)
                        :user/firstname (:firstname params)
                        :user/lastname (:lastname params)
                        :user/age (utilities/->int (:age params))}]))
      (logger/info "User not found"))))

(defn upsert-finances [{:keys [params] :as ctx}]
  (let [user-id (get-user-id-from-session ctx)
        {:user/keys [age]
         :or {age 21}}   (get-user ctx user-id)
        salary (utilities/->int (:salary params))
        annual-income (utilities/income->annual-income salary)
        {:keys [net-income]} (tax/calculate-income-tax annual-income age)]
    (logger/info "Creating finances...")
    (biff/submit-tx ctx
                    [{:db/doc-type :finances
                      :xt/id (java.util.UUID/randomUUID)
                      :finances/user-id user-id
                      :finances/bank (utilities/->keyword (:bank params))
                      :finances/card-type (utilities/->keyword (:card-type params))
                      :finances/salary (utilities/->int (:salary params))
                      :finances/payday (validation/->valid-payday (:payday params))}])
    (logger/info "Creating budget item...")
    (biff/submit-tx ctx
                    [{:db/doc-type :budget-item
                      :xt/id (java.util.UUID/randomUUID)
                      :budget-item/user-id user-id
                      :budget-item/title "Salary"
                      :budget-item/type :income
                      :budget-item/amount (-> net-income
                                              utilities/annual-income->monthly-income
                                              int)}])))

(defn update-finances [{:keys [params] :as ctx}]
  (let [user-id (get-user-id-from-session ctx)
        finances (get-finances ctx user-id)
        finances-id (:xt/id finances)
        salary-budget-item (get-salary-budget-item ctx user-id)
        _ (clojure.pprint/pprint salary-budget-item)
        salary-budget-item-id (:xt/id salary-budget-item)
        {:user/keys [age]
         :or {age 21}}   (get-user ctx user-id)
        salary (utilities/->int (:salary params))
        annual-income (utilities/income->annual-income salary)
        {:keys [net-income]} (tax/calculate-income-tax annual-income age)]
    (if (and finances salary-budget-item)
      (do (logger/info "Updating finances...")
          (biff/submit-tx ctx
                          [{:db/doc-type :finances
                            :xt/id finances-id
                            :db/op :update
                            :finances/bank (utilities/->keyword (:bank params))
                            :finances/card-type (utilities/->keyword (:card-type params))
                            :finances/salary (utilities/->int (:salary params))
                            :finances/payday (validation/->valid-payday (:payday params))}])
          (logger/info "Updating budget item...")
          (biff/submit-tx ctx
                          [{:db/doc-type :budget-item
                            :xt/id salary-budget-item-id
                            :db/op :update
                            :budget-item/title "Salary"
                            :budget-item/type :income
                            :budget-item/amount (-> net-income
                                                    utilities/annual-income->monthly-income
                                                    int)}]))
      (logger/info "Finances not found"))))


(defn upsert-budget-item [{:keys [params] :as ctx}]
  (let [user-id (get-user-id-from-session ctx)
        budget-item-id (java.util.UUID/randomUUID)]
    (logger/info "Creating budget item...")
    (biff/submit-tx ctx
                    [{:db/doc-type :budget-item
                      :xt/id budget-item-id
                      :budget-item/user-id user-id
                      :budget-item/title (:title params)
                      :budget-item/type (utilities/->keyword (:type params))
                      :budget-item/amount (validation/->valid-amount (:amount params))}])))

(defn update-budget-item [{:keys [params] :as ctx}]
  (let [user-id (get-user-id-from-session ctx)
        budget-item (get-budget-item ctx user-id)
        budget-item-id (:xt/id budget-item)]
    (if budget-item
      (do
        (logger/info "Updating budget item...")
        (biff/submit-tx ctx
                        [{:db/doc-type :budget-item
                          :xt/id budget-item-id
                          :db/op :update
                          :budget-item/title (:title params)
                          :budget-item/type (utilities/->keyword (:type params))
                          :budget-item/amount (validation/->valid-amount (:amount params))}]))
      (logger/info "Budget item not found"))))

(defn delete-budget-item [{:keys [params] :as ctx}]
  (let [{:keys [budget-item-id]} params
        budget-item-id (utilities/->uuid budget-item-id)
        budget-item (get-budget-item ctx budget-item-id)]
    (if budget-item
      (do (logger/info "Deleting budget item...")
          (biff/submit-tx ctx
                          [{:db/doc-type :budget-item
                            :xt/id budget-item-id
                            :db/op :delete}]))
      (logger/info "Budget item not found"))))
