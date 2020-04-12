(ns blog.core
  (:require [blog.blogger :refer [post-article]]
            [blog.markdown :refer [render-html]])
  (:gen-class))

(defn -main [& args]
  (let [article (slurp (first args))]
    (post-article
      "https://blog.ryuichi.io"
      {:title "My Test Post 2"
       :content (render-html article)})))
