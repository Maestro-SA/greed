(ns com.greed.ui.components.forms
  (:require [clojure.tools.logging :as log]
            [com.biffweb :as biff]
            [com.core :as c]
            [com.greed.utilities.core :as tools]
            [com.greed.ui.components.shared :as shared]))


(defn on-error [{:keys [params]}]
  (let [config c/common-config]
    (when-some [error (:error params)]
      (log/error "Error during form submission:" error)
      [:<>
       [:.h-1]
       [:.text-sm.text-red-600
        (when-not (= "not-signed-in" error)
          (case (tools/->keyword error)
            :recaptcha (:error/recaptcha config)
            :invalid-email (:error/invalid-email config)
            :invalid-credentials (:error/invalid-credentials config)
            :send-failed (:error/send-failed config)
            (:error/default config)))]])))


(defn sign-in [{:keys [site-key] :as ctx}]
  [:div
   {:class "w-full max-w-sm mx-auto overflow-hidden bg-white border-2 border-black rounded-lg shadow-md"}
   [:div
    {:class "px-6 py-4"}
    [:div
     {:class "flex justify-center mx-auto"}
     [:span
      {:class "text-2xl font-giza font-semibold text-black md:text-4xl"}
      "greed."]]
    [:h3
     {:class
      "mt-3 text-xl font-medium text-center text-gray-600"}
     "Welcome Back"]
    [:p
     {:class "mt-1 text-center text-gray-500"}
     "Login or create account"]
    (biff/form
     {:action "authenticate/signin"
      :id "signin"
      :hidden {:on-error "/"}}
     (biff/recaptcha-callback "submitSignin" "signin")
     (shared/input :id "email" :type "email" :label "Email Address" :required? true)
     (shared/input :id "password" :type "password" :label "Password" :required? true)
     [:div
      {:class "flex items-center justify-between mt-4"}
      [:a
       {:href "#",
        :class "text-sm text-gray-600 hover:text-gray-500"}
       "Forget Password?"]
      [:button
       (merge (when site-key
                {:data-sitekey site-key
                 :data-callback "submitSignin"})
              {:class "px-6 py-2 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-black rounded-lg hover:bg-white hover:text-black hover:ring hover:ring-black focus:outline-none focus:ring focus:ring-black"
               :type "submit"}) 
       "Sign In"]]
     (on-error ctx))]
   [:div
    {:class
     "flex items-center justify-center py-4 text-center bg-black"}
    [:span
     {:class "text-sm text-gray-200"}
     "Don't have an account?"]
    [:a
     {:href "/signup",
      :class "mx-2 text-sm font-bold text-blue-500 hover:underline"}
     "Register"]]])

(defn sign-up [{:keys [site-key] :as ctx}]
  [:div
   {:class "w-full max-w-sm mx-auto overflow-hidden bg-white border-2 border-black rounded-lg shadow-md"}
   [:div
    {:class "px-6 py-4"}
    [:div
     {:class "flex justify-center mx-auto"}
     [:span
      {:class "text-2xl font-giza font-semibold text-black md:text-4xl"}
      "greed."]]
    [:h3
     {:class
      "mt-3 text-xl font-medium text-center text-gray-600"}
     "Welcome, let's get started"]
    [:p
     {:class "mt-1 text-center text-gray-500"}
     "Login or create account"]
    (biff/form
     {:action "authenticate/signup"
      :id "signup"
      :hidden {:on-error "/"}}
     (biff/recaptcha-callback "submitSignup" "signup")
     (shared/input :id "firstname" :type "text" :label "First Name" :required? true)
     (shared/input :id "lastname" :type "text" :label "Last Name" :required? true)
     (shared/input :id "age" :type "number" :label "Age" :required? true)
     (shared/input :id "email" :type "email" :label "Email Address" :required? true)
     (shared/input :id "password" :type "password" :label "Password" :required? true)
     [:div
      {:class "flex items-center justify-between mt-4"}
      [:a
       {:href "#",
        :class "text-sm text-gray-600 hover:text-gray-500"}
       "Forget Password?"]
      [:button
       (merge (when site-key
                {:data-sitekey site-key
                 :data-callback "submitSignup"})
              {:class "px-6 py-2 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-black rounded-lg hover:bg-white hover:text-black hover:ring hover:ring-black focus:outline-none focus:ring focus:ring-black"
               :type "submit"})
       "Sign up"]]
     (on-error ctx))]
   [:div
    {:class
     "flex items-center justify-center py-4 text-center bg-black"}
    [:span
     {:class "text-sm text-gray-200"}
     "Already have an account?"]
    [:a
     {:href "/signin",
      :class "mx-2 text-sm font-bold text-blue-500 hover:underline"}
     "Sign in"]]])

(defn user [ctx]
  [:section
   {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
   [:h2
    {:class "text-lg font-semibold text-gray-700 capitalize"}
    "User"]
   (biff/form
     {:action "/app/save-user"}
     [:div
      {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
      (shared/app-input ctx :id "firstname" :type "text" :label "First Name" :required? true)
      (shared/app-input ctx :id "lastname" :type "text" :label "Last Name" :required? true)
      (shared/app-input ctx :id "age" :type "number" :label "Age" :required? true)
      (shared/app-input ctx :id "email" :type "text" :label "Email" :required? true)
      (shared/app-input ctx :id "password" :type "password" :label "Password" :required? true)]
     [:div
      {:class "flex justify-end mt-6"}
      [:button
       {:class "px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600"
        :type "submit"}
       "Save"]])])

(defn finances [ctx]
  (let [bank-options (:banking/banks c/common-config)
        card-type-options (:banking/card-types c/common-config)]
    [:section
     {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
     [:h2
      {:class "text-lg font-semibold text-gray-700 capitalize"}
      "Finances"]
     (biff/form
      {:action "/app/save-finances"}
      [:div
       {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
       (shared/app-select ctx :id "bank" :label "Bank" :options bank-options :required? true)
       (shared/app-select ctx :id "card-type" :label "Card Type" :options card-type-options :required? true)
       (shared/app-input ctx :id "salary" :type "number" :label "Salary" :required? true)
       (shared/app-input ctx :id "payday" :type "number" :label "Pay day" :required? true)]
      [:div
       {:class "flex justify-end mt-6"}
       [:button
        {:class "px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600"
         :type "submit"}
        "Save"]])]))

(defn income-tax-form []
  [:div
   {:class "relative inline-block px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:w-full sm:max-w-sm sm:p-6 sm:align-middle"}
   [:h3
    {:class "text-lg font-medium leading-6 text-gray-800 capitalize",
     :id "modal-title"}
    "Income tax calculator"]
   [:p
    {:class "mt-2 text-sm text-gray-500"}
    "Calculate your income tax in seconds"]
   (biff/form
    {:class "mt-4"
     :action "/app/tools/income-tax-calculator"}
    (shared/modal-input :id "income" :type "number" :label "Income" :required? true)
    (shared/modal-input :id "age" :type "number" :label "Age" :required? true)
    [:div
     {:class "mt-4 sm:flex sm:items-center sm:-mx-2"}
     [:button
      {:type "button",
       "@click" "isOpen = false",
       :class "w-full px-4 py-2 text-sm font-medium tracking-wide text-gray-700 capitalize transition-colors duration-300 transform border border-gray-200 zrounded-md sm:w-1/2 sm:mx-2 hover:bg-gray-100 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-40"}
      "Cancel"]
     [:button
      {:type "submit",
       :class "w-full px-4 py-2 mt-3 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-600 rounded-md sm:mt-0 sm:w-1/2 sm:mx-2 hover:bg-blue-500 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40"}
      "Calculate"]])])

(defn budget-item-form []
  (let [budget-item-options (:budget-item/types c/common-config)]
    [:div
     {:class "relative inline-block px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:w-full sm:max-w-sm sm:p-6 sm:align-middle"}
     [:h3
      {:class "text-lg font-medium leading-6 text-gray-800 capitalize"}
      "Budget Item"]
     [:p
      {:class "mt-2 text-sm text-gray-500"}
      "Add a new budget item to your list"]
     (biff/form
      {:class "mt-4"
       :action "/app/finances/create-budget-item"}
      (shared/modal-select :id "type" :label "Budget type" :options budget-item-options :required? true)
      (shared/modal-input :id "title" :type "text" :label "Title" :required? true)
      (shared/modal-input :id "amount" :type "number" :label "Amount" :required? true)
      [:div
       {:class "mt-4 sm:flex sm:items-center sm:-mx-2"}
       [:button
        {:type "button",
         "@click" "isAddButtonOpen = false",
         :class "w-full px-4 py-2 text-sm font-medium tracking-wide text-gray-700 capitalize transition-colors duration-300 transform border border-gray-200 zrounded-md sm:w-1/2 sm:mx-2 hover:bg-gray-100 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-40"}
        "Cancel"]
       [:button
        {:type "submit",
         :class "w-full px-4 py-2 mt-3 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-gray-600 rounded-md sm:mt-0 sm:w-1/2 sm:mx-2 hover:bg-gray-500 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40"}
        "Add"]])]))

(defn budget-action-form [item]
  [:div
   {:class "relative inline-block px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:w-full sm:max-w-sm sm:p-6 sm:align-middle"}
   [:h3
    {:class "text-lg font-medium leading-6 text-gray-800 capitalize"}
    "Budget Item"]
   [:p
    {:class "mt-2 text-sm text-gray-500"}
    "Update or delete this budget item"]
   [:div
    {:class "mt-2 text-sm text-gray-500"}
    [:div
     [:span
      {:class "text-gray-800"} "Title: "]
     (:budget-item/title item)]
    [:div
     [:span
      {:class "text-gray-800"} "Type: "]
     (:budget-item/type item)]
    [:div
     [:span
      {:class "text-gray-800"} "Amount: "]
     (:budget-item/amount item)]]
   (biff/form
    {:class "mt-4"
     :action (str "/app/finances/update-budget-item?budget-item-id=" (:xt/id item))}
    (shared/modal-input :id "title" :type "text" :label "Title" :required? true)
    (shared/modal-input :id "amount" :type "number" :label "Amount" :required? true)
    [:div
     {:class "mt-4 sm:flex sm:items-center sm:-mx-2"}
     [:button
      {:type "submit",
       :class "w-full px-4 py-2 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-gray-600 rounded-md sm:mt-0 sm:w-1/2 sm:mx-2 hover:bg-gray-500"}
      "Update"]
     [:a
      {:href (str "/app/finances/delete-budget-item?budget-item-id=" (:xt/id item)),
       :class "w-full px-4 py-2 mt-3 text-sm text-center font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-red-600 rounded-md sm:mt-0 sm:w-1/2 sm:mx-2 hover:bg-red-500"}
      "Delete"]]
    [:div
     {:class "mt-4 sm:flex sm:items-center sm:-mx-2"}
     [:button
      {:type "button",
       "@click" "isActionModalOpen = false",
       :class "w-full px-4 py-2 text-sm font-medium tracking-wide text-gray-700 capitalize transition-colors duration-300 transform border border-gray-200 zrounded-md sm:mx-2 hover:bg-gray-100 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-40"}
      "Cancel"]])])
