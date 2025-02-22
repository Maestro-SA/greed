(ns com.greed.components.svgs)

(defn success []
  [:svg
   {:class "w-6 h-6 text-white fill-current",
    :viewBox "0 0 40 40",
    :xmlns "http://www.w3.org/2000/svg"}
   [:path
    {:d "M20 3.33331C10.8 3.33331 3.33337 10.8 3.33337 20C3.33337 29.2 10.8 36.6666 20 36.6666C29.2 36.6666 36.6667 29.2 36.6667 20C36.6667 10.8 29.2 3.33331 20 3.33331ZM16.6667 28.3333L8.33337 20L10.6834 17.65L16.6667 23.6166L29.3167 10.9666L31.6667 13.3333L16.6667 28.3333Z"}]])

(defn search []
  [:svg
   {:class "w-5 h-5 text-gray-400",
    :viewBox "0 0 24 24",
    :fill "none"}
   [:path
    {:d "M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z",
     :stroke "currentColor",
     :stroke-width "2",
     :stroke-linecap "round",
     :stroke-linejoin "round"}]])

(defn dashboard []
  [:svg
   {:class "w-5 h-5",
    :viewBox "0 0 24 24",
    :fill "none",
    :xmlns "http://www.w3.org/2000/svg"}
   [:path
    {:d
     "M19 11H5M19 11C20.1046 11 21 11.8954 21 13V19C21 20.1046 20.1046 21 19 21H5C3.89543 21 3 20.1046 3 19V13C3 11.8954 3.89543 11 5 11M19 11V9C19 7.89543 18.1046 7 17 7M5 11V9C5 7.89543 5.89543 7 7 7M7 7V5C7 3.89543 7.89543 3 9 3H15C16.1046 3 17 3.89543 17 5V7M7 7H17",
     :stroke "currentColor",
     :stroke-width "2",
     :stroke-linecap "round",
     :stroke-linejoin "round"}]])

(defn settings []
  [:svg
   {:class "w-5 h-5",
    :viewBox "0 0 24 24",
    :fill "none",
    :xmlns "http://www.w3.org/2000/svg"}
   [:path
    {:d
     "M10.3246 4.31731C10.751 2.5609 13.249 2.5609 13.6754 4.31731C13.9508 5.45193 15.2507 5.99038 16.2478 5.38285C17.7913 4.44239 19.5576 6.2087 18.6172 7.75218C18.0096 8.74925 18.5481 10.0492 19.6827 10.3246C21.4391 10.751 21.4391 13.249 19.6827 13.6754C18.5481 13.9508 18.0096 15.2507 18.6172 16.2478C19.5576 17.7913 17.7913 19.5576 16.2478 18.6172C15.2507 18.0096 13.9508 18.5481 13.6754 19.6827C13.249 21.4391 10.751 21.4391 10.3246 19.6827C10.0492 18.5481 8.74926 18.0096 7.75219 18.6172C6.2087 19.5576 4.44239 17.7913 5.38285 16.2478C5.99038 15.2507 5.45193 13.9508 4.31731 13.6754C2.5609 13.249 2.5609 10.751 4.31731 10.3246C5.45193 10.0492 5.99037 8.74926 5.38285 7.75218C4.44239 6.2087 6.2087 4.44239 7.75219 5.38285C8.74926 5.99037 10.0492 5.45193 10.3246 4.31731Z",
     :stroke "currentColor",
     :stroke-width "2",
     :stroke-linecap "round",
     :stroke-linejoin "round"}]
   [:path
    {:d
     "M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z",
     :stroke "currentColor",
     :stroke-width "2",
     :stroke-linecap "round",
     :stroke-linejoin "round"}]])

(defn logout []
  [:svg
   {:xmlns "http://www.w3.org/2000/svg",
    :fill "none",
    :viewBox "0 0 24 24",
    :stroke-width "1.5",
    :stroke "currentColor",
    :class "w-5 h-5"}
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d "M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9"}]])

(defn visa []
  [:img {:src "/img/visa.svg"}])

(defn mastercard []
  [:img {:src "/img/mastercard.svg"}])
