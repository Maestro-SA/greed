(ns com.greed.schema)

(def schema
  {:user/id :uuid
   :user [:map {:closed true}
          [:xt/id                     :user/id]
          [:user/firstname            :string]
          [:user/lastname             :string]
          [:user/email                :string] 
          [:user/password             :string]]})

(def module
  {:schema schema})
