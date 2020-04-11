(ns blog.core
  (:import [com.google.api.services.blogger BloggerRequestInitializer Blogger$Builder]
           [com.google.api.services.blogger.model Post]
           [com.google.api.client.googleapis.javanet GoogleNetHttpTransport]
           [com.google.api.client.json.jackson2 JacksonFactory]
           [com.google.auth.http HttpCredentialsAdapter]
           [com.google.auth.oauth2 GoogleCredentials])
  (:gen-class))

(defn make-blogger []
  (.build
    (.setBloggerRequestInitializer
       (Blogger$Builder. (GoogleNetHttpTransport/newTrustedTransport)
                         (JacksonFactory.)
                         (HttpCredentialsAdapter. (GoogleCredentials/getApplicationDefault)))
       (BloggerRequestInitializer.))))

(defn make-post [title, contents]
  (-> (Post.) (.setTitle title) (.setContent contents)))

(defn blog-by-name [name]
  (fn [blogger]
    (-> blogger (.blogs) (.getByUrl name) (.execute))))

(defn publish [blog post]
  (fn [blogger]
    (-> blogger (.posts) (.insert (.getId blog) post) (.executeUnparsed) (.parseAsString))))

(defn -main [& args]
  (let [blogger (make-blogger)
        post (make-post "My Test Post" "With <b>exciting</b> content!!")
        get-blog (blog-by-name "https://blog.ryuichi.io")]
    ((publish (get-blog blogger) post) blogger)))
