(ns blog.markdown
  (:import [org.commonmark.parser Parser]
           [org.commonmark.renderer.html HtmlRenderer AttributeProviderFactory AttributeProvider]
           [org.commonmark.ext.gfm.tables TablesExtension]
           [java.util Map]
           (org.commonmark.node Heading)))

(def parser (.build (.extensions (Parser/builder) [(TablesExtension/create)])))
(def parse #(.parse parser %))

(def attribute-provider
  (proxy [AttributeProvider] []
    (setAttributes [_node tag-name attributes]
      ; https://github.com/atlassian/commonmark-java/issues/74
      (if (= tag-name "pre") (.put ^Map attributes "class" "prettyprint")))))

(def attribute-provider-factory
  (proxy [AttributeProviderFactory] []
    (create [_context] attribute-provider)))

(def renderer (-> (HtmlRenderer/builder) (.attributeProviderFactory attribute-provider-factory) (.build)))
(def render #(.render renderer %))

(defn extract-title [node] (-> node (.getFirstChild) (.getLiteral)))

(defn find-top-heading [node]
  (if (and (instance? Heading node) (= 1 (.getLevel node)))
    node
    (find-top-heading node)))

(defn make-post [article]
  (let [doc (parse article)
        first-top-heading (find-top-heading (.getFirstChild doc))
        title (extract-title first-top-heading)]
    (do (.unlink first-top-heading)
        {:title title, :content (render doc)})))
