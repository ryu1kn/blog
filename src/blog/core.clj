(ns blog.core
  (:require [clojure.data.json :as json]
            [blog.blogger :refer [publish-article blogpost-apis blogger]]
            [blog.markdown :refer [make-post]])
  (:gen-class))

(defn -main [& args]
  (let [article (slurp (first args))
        blog-id (-> "__config.json" (slurp) (json/read-str) (get "blog_id"))]
    (publish-article blogpost-apis blog-id (make-post article) blogger)))
