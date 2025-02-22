(ns com.greed.components.forms
  (:require [com.biffweb :as biff]
            [com.greed.components.shared :as shared]))


(defn on-error [{:keys [params]}]
  (when-some [error (:error params)]
    [:<>
     [:.h-1]
     [:.text-sm.text-red-600
      (case error
        "recaptcha" (str "You failed the recaptcha test. Try again, "
                         "and make sure you aren't blocking scripts from Google.")
        "invalid-email" "Invalid email. Try again with a different address."
        "invalid-credentials" "Invalid credentials. Try again."
        "send-failed" (str "We weren't able to send an email to that address. "
                           "If the problem persists, try another address.")
        "There was an error.")]]))


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
                 :data-callback "submitSignin"})
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

(defn app-settings [ctx]
  [:section
   {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
   [:h2
    {:class "text-lg font-semibold text-gray-700 capitalize"}
    "Account settings"]
   [:form
    [:div
     {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
     (shared/app-input ctx :id "firstname" :type "text" :label "First Name")
     (shared/app-input ctx :id "lastname" :type "text" :label "Last Name")
     (shared/app-input ctx :id "email" :type "text" :label "Email")
     (shared/app-input ctx :id "password" :type "password" :label "Password")]
    [:div
     {:class "flex justify-end mt-6"}
     [:button
      {:class "px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600"}
      "Save"]]]])

(defn profile [ctx]
  [:section
   {:class "container p-6 mx-auto bg-white rounded-md shadow-md"}
   [:h2
    {:class "text-lg font-semibold text-gray-700 capitalize"}
    "Profile"]
   [:form
    [:div
     {:class "grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2"}
     (shared/app-select ctx :id "bank" :label "Bank")
     (shared/app-select ctx :id "card-type" :label "Card Type" :options ["Visa" "Mastercard"])
     (shared/app-input ctx :id "income" :type "number" :label "Income")
     (shared/app-input ctx :id "expenses" :type "number" :label "Expenses")
     (shared/app-input ctx :id "savings" :type "number" :label "Savings")]
    [:div
     {:class "flex justify-end mt-6"}
     [:button
      {:class "px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600"}
      "Save"]]]])
