(ns blog.blogger
  (:require [clojure.string :as s])
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

(defn to-blogger-post [post]
  (let [{title :title, content :content} post]
    (-> (Post.) (.setTitle title) (.setContent content))))

(def blogpost-apis {
  :create-post (fn [post blog-id blogger]
                 (-> blogger (.posts) (.insert blog-id post) (.execute)))
  :update-post (fn [post-id post blog-id blogger]
                 (-> blogger (.posts) (.update blog-id post-id post) (.execute)))
  :fetch-posts (fn [blog-id blogger]
                 (-> blogger (.posts) (.list blog-id) (.execute) (get "items")))
})

(defn publish-article [apis blog-id post blogger]
  (let [{create-post :create-post
         update-post :update-post
         fetch-posts :fetch-posts} apis
        posts-with-id (filter (comp #(s/includes? % (str "<!-- POST_ID: " (:id post) " -->")) #(get % "content")) (fetch-posts blog-id blogger))
        post-id (#(if (not-empty %) (-> % (first) (get "id"))) posts-with-id)
        publish (if post-id #(apply update-post (into [post-id] %&)) create-post)
        ]
    (publish (to-blogger-post post) blog-id blogger)))
