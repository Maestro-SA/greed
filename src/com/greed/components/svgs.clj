(ns com.greed.components.svgs)

(defn close-menu []
  [:svg
   {:x-show "!isOpen",
    :xmlns "http://www.w3.org/2000/svg",
    :class "w-6 h-6",
    :fill "none",
    :viewBox "0 0 24 24",
    :stroke "currentColor",
    :stroke-width "2"}
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d "M4 8h16M4 16h16"}]])

(defn open-menu []
  [:svg
   {:x-show "isOpen",
    :xmlns "http://www.w3.org/2000/svg",
    :class "w-6 h-6",
    :fill "none",
    :viewBox "0 0 24 24",
    :stroke "currentColor",
    :stroke-width "2"}
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d "M6 18L18 6M6 6l12 12"}]])