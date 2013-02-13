(ns yolk.bacon-test
  (:require [qunit.core :refer [ok? equal? module]]
            [yolk.bacon :as b]
            [jayq.core :refer [$] :as j])
  (:use-macros [qunit.macros :only [deftest]]))

(module "Bacon Tests")

(deftest "fail"
  (equal? true true))
