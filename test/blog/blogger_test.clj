(ns blog.blogger-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [blog.blogger :refer :all]))

(def equals #(is (= %1 %2)))
(def format-args #(s/join " / " %&))

(def post-1 {"content" "<p>Hello</p>\n<!-- POST_ID: my-post-id-001 -->\n",
             "id" "001"})
(def post-2 {"content" "<p>Hi</p>\n<!-- POST_ID: my-post-id-002 -->\n",
             "id" "002"})

(deftest foo-test
  (testing "New post"
    (let [apis {:create-post format-args
                :fetch-posts (constantly [])}
          post {:id "my-post-id-002"}]
      (equals "{:id \"my-post-id-002\"} / blog-id / BLOGGER"
              (publish-article' apis identity "blog-id" post "BLOGGER"))))

  (testing "Update post"
    (let [apis {:update-post format-args
                :fetch-posts (constantly [post-1 post-2])}
          post {:id "my-post-id-002"}]
      (equals
        "002 / {:id \"my-post-id-002\"} / blog-id / BLOGGER"
        (publish-article' apis identity "blog-id" post "BLOGGER"))))
  )
