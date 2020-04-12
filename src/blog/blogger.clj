(ns blog.blogger
  (:import [com.google.api.services.blogger BloggerRequestInitializer Blogger$Builder Blogger]
           [com.google.api.services.blogger.model Post]
           [com.google.api.client.googleapis.javanet GoogleNetHttpTransport]
           [com.google.api.client.json.jackson2 JacksonFactory]
           [com.google.auth.http HttpCredentialsAdapter]
           [com.google.auth.oauth2 GoogleCredentials]))

(def ^Blogger blogger
  (.build
    (.setBloggerRequestInitializer
      (Blogger$Builder. (GoogleNetHttpTransport/newTrustedTransport)
                        (JacksonFactory.)
                        (HttpCredentialsAdapter. (GoogleCredentials/getApplicationDefault)))
      (BloggerRequestInitializer.))))

(defn make-post [post]
  (let [{title :title, content :content} post]
    (-> (Post.) (.setTitle title) (.setContent content))))

(defn blog-by-name [name]
  (fn [blogger]
    (-> blogger (.blogs) (.getByUrl name) (.execute))))

(defn publish [blog post]
  (fn [blogger]
    (-> blogger (.posts) (.insert (.getId blog) post) (.executeUnparsed) (.parseAsString))))

(defn post-article [blog-name post]
  (let [post (make-post post)
        get-blog (blog-by-name blog-name)]
    ((publish (get-blog blogger) post) blogger)))
