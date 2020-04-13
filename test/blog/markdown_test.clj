(ns blog.markdown-test
  (:require [clojure.test :refer :all]
            [blog.markdown :refer :all]))

(def render-html (comp render parse))

(deftest html-rendering-test
  (testing "emphasis"
    (is (= "<p>This is <em>Sparta</em></p>\n" (render-html "This is *Sparta*"))))

  (testing "pretty code block"
    (is (= "<pre class=\"prettyprint\"><code class=\"language-sh\">echo Hi\n</code></pre>\n" (render-html "```sh\necho Hi\n```"))))
  )

(deftest make-post-test
  (testing "Create a post out of markdown"
    (is (= {:title "Title", :content "<p>Content</p>\n"} (make-post "# Title\n\nContent"))))
  )
