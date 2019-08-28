(ns juxt.crux-ui.frontend.views.commons.datepicker-native
  (:require [garden.core :as garden]
            [juxt.crux-ui.frontend.logging :as log]
            [juxt.crux-ui.frontend.functions :as f]
            [juxt.crux-ui.frontend.logic.time :as time]
            [juxt.crux-ui.frontend.views.commons.input :as input]
            [reagent.core :as r]))


(defn- on-time-change--native [on-change-external evt]
  (try
    (let [v (f/jsget evt "target" "value")
          ts (js/Date.parse v)]
      (if (js/isNaN ts)
        (on-change-external nil)
        (on-change-external (js/Date. ts))))
    (catch js/Error err
      (on-change-external nil)
      (log/error err))))


(def style
  [:style
   (garden/css
     [:.native-date-time-picker
      [:&__label
       {:width :100%
        :display :block
        :font-size :1.1em
        :letter-spacing :.04em}]
      [:&__input
       input/styles-src
       {:padding "4px 0"
        :width :auto}]])])


(defn picker [{:keys [label ^js/Date value on-change] :as prms}]
  [:div.native-date-time-picker
   (if label
     [:label.native-date-time-picker__label label])
   [:input.native-date-time-picker__input
    {:type "datetime-local"
     :defaultValue (if value (time/format-for-dt-local value))
     :value (if value (time/format-for-dt-local value))
     :on-change (r/partial on-time-change--native on-change)}]])
