(ns blog.core
  (:require [blog.blogger :refer [post-article]]
            [blog.markdown :refer [render-html]])
  (:gen-class))

(defn -main [& _]
  (post-article "https://blog.ryuichi.io" {:title "My Test Post 2"
                                           :content (render-html "With *exciting* content!!")}))
