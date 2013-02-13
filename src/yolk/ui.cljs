(ns yolk.ui
  (:require [yolk.bacon :as b]
            [jayq.core :refer [$] :as j]))

(defn ->stream [$elem event & [selector event-transformer]]
  (.asEventStream $elem event selector event-transformer))

(defn mousemove [$elem]
  (.asEventStream $elem "mousemove"))

(defn click [$elem & [selector event-transformer]]
  (-> $elem
      (->stream "click" selector event-transformer)
      (.doAction ".preventDefault")))

(defn change [$elem]
  (.asEventStream $elem "change"))

(defn inner [$elem property]
  (b/on-value property (partial j/inner $elem)))

(defn textfield-value [$elem & [init]]
  (js/Bacon.UI.textFieldValue $elem init))

(defn option-value [$elem & [init]]
  (js/Bacon.UI.optionValue $elem init))

(defn checkbox-group-value [$checkboxes & [init]]
  (js/Bacon.UI.checkBoxGroupValue $checkboxes init))

(defn ajax [params]
  (js/Bacon.UI.ajax (clj->js params)))

(defn radio-group-value [$elem & [init]]
  (js/Bacon.UI.radioGroupValue $elem init))

(defn checkbox-value [$elem & [init]]
  (js/Bacon.UI.checkBoxValue $elem init))

(defn disabled [$elem property]
  (b/on-value property
              (fn [x?]
                ((if x? j/add-class j/remove-class)
                 $elem "disabled"))))

(defn enabled [$elem property]
  (b/on-value property
              (fn [x?]
                ((if x? j/remove-class j/add-class)
                 $elem "disabled"))))
