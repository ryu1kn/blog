(ns blog.core
  (:require [blog.blogger :refer [post-article]])
  (:gen-class))

(defn -main [& _]
  (post-article "https://blog.ryuichi.io" {:title "My Test Post 2"
                                           :content "With <b>exciting</b> content!!"}))
