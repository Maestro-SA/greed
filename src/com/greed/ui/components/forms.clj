(ns com.greed.ui.components.forms
  (:require [com.biffweb :as biff]
            [com.core :as c]
            [com.greed.tools.core :as tools]
            [com.greed.ui.components.shared :as shared]))


(defn on-error [{:keys [params]}]
  (let [config c/common-config]
    (when-some [error (:error params)]
      [:<>
       [:.h-1]
       [:.text-sm.text-red-600
        (case (tools/->keyword error)
          :recaptcha (:error/recaptcha config)
          :invalid-email (:error/invalid-email config)
          :invalid-credentials (:error/invalid-credentials config)
          :send-failed (:error/send-failed config)
          (:error/default config))]])))


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

(defn account [ctx]
  [:section
   {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
   [:h2
    {:class "text-lg font-semibold text-gray-700 capitalize"}
    "Account"]
   (biff/form
     {:action "/app/save-user"}
     [:div
      {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
      (shared/app-input ctx :id "firstname" :type "text" :label "First Name" :required? true)
      (shared/app-input ctx :id "lastname" :type "text" :label "Last Name" :required? true)
      (shared/app-input ctx :id "email" :type "text" :label "Email" :required? true)
      (shared/app-input ctx :id "password" :type "password" :label "Password" :required? true)]
     [:div
      {:class "flex justify-end mt-6"}
      [:button
       {:class "px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600"
        :type "submit"}
       "Save"]])])

(defn profile [ctx]
  (let [bank-options (:banking/banks c/common-config)
        card-type-options (:banking/card-types c/common-config)]
    [:section
     {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
     [:h2
      {:class "text-lg font-semibold text-gray-700 capitalize"}
      "Profile"]
     (biff/form
      {:action "/app/save-profile"}
      [:div
       {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
       (shared/app-select ctx :id "bank" :label "Bank" :options bank-options :required? true)
       (shared/app-select ctx :id "card-type" :label "Card Type" :options card-type-options :required? true)
       (shared/app-input ctx :id "income" :type "number" :label "Income" :required? true)
       (shared/app-input ctx :id "expenses" :type "number" :label "Expense" :required? true)
       (shared/app-input ctx :id "savings" :type "number" :label "Savings" :required? true)
       (shared/app-input ctx :id "age" :type "number" :label "Age" :required? true)
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
