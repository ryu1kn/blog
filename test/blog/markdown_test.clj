(ns blog.markdown-test
  (:require [clojure.test :refer :all]
            [blog.markdown :refer :all]))

(deftest html-rendering-test
  (testing "emphasis"
    (is (= "<p>This is <em>Sparta</em></p>\n" (render-html "This is *Sparta*")))))
