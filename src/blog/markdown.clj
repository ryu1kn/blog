(ns blog.markdown
  (:import [org.commonmark.parser Parser]
           [org.commonmark.renderer.html HtmlRenderer AttributeProviderFactory AttributeProvider]
           [org.commonmark.ext.gfm.tables TablesExtension]
           [org.commonmark.node Heading]
           [java.util Map]))

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

(defn top-heading? [node] (and (instance? Heading node) (= 1 (.getLevel node))))

(def extract-title (comp #(.getLiteral %) #(.getFirstChild %)))

(defn find-top-heading [node]
  (if (or (nil? node) (top-heading? node))
    node
    (find-top-heading (.getNext node))))

(defn make-post [article]
  (let [doc (parse article)
        top-heading (find-top-heading (.getFirstChild doc))
        title (if top-heading (extract-title top-heading) "")
        post-id (second (re-find #"<!-- POST_ID: ([^\s]+) -->" article))]
    (do (if top-heading (.unlink top-heading))
        {:id post-id :title title, :content (render doc)})))
