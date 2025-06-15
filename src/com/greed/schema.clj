(ns com.greed.schema)

(def schema
  {:user/id :uuid
   :user [:map {:closed true}
          [:xt/id          :user/id]
          [:user/email     :string]
          [:user/password  :string]
          [:user/firstname :string]
          [:user/lastname  :string]
          [:user/age       :int]]

   :finances/id :uuid
   :finances [:map {:closed true}
              [:xt/id              :finances/id]
              [:finances/user-id   :user/id]
              [:finances/bank      :keyword]
              [:finances/card-type :keyword]
              [:finances/salary    :int]
              [:finances/payday    :int]]

   :budget-item/id :uuid
   :budget-item [:map {:closed true}
                 [:xt/id               :budget-item/id]
                 [:budget-item/user-id :user/id]
                 [:budget-item/title   :string]
                 [:budget-item/type    :keyword]
                 [:budget-item/amount  :int]]})

(def module
  {:schema schema})
