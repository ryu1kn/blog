(ns blog.core
  (:require [blog.blogger :refer [post-article]]
            [blog.markdown :refer [make-post]])
  (:gen-class))

(defn -main [& args]
  (let [article (slurp (first args))]
    (post-article "https://blog.ryuichi.io" (make-post article))))
