(ns com.greed.schema)

(def schema
  {:user/id :uuid
   :user [:map {:closed true}
          [:xt/id                     :user/id]
          [:user/firstname            :string]
          [:user/lastname             :string]
          [:user/email                :string] 
          [:user/password             :string]]

   :profile/id :uuid
   :profile [:map {:closed true}
             [:xt/id                  :profile/id]
             [:profile/user-id        :user/id]
             [:profile/bank           :string]
             [:profile/card-type      :string]
             [:profile/income         :int]
             [:profile/expenses       :int]
             [:profile/savings        :int]
             [:profile/age            :int]
             [:profile/payday         :int]]})

(def module
  {:schema schema})
