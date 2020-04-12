(ns blog.markdown
  (:import [org.commonmark.parser Parser]
           [org.commonmark.renderer.html HtmlRenderer AttributeProviderFactory AttributeProvider]
           [org.commonmark.ext.gfm.tables TablesExtension]
           [java.util Map]))

(def parser (.build (.extensions (Parser/builder) [(TablesExtension/create)])))

(def attribute-provider
  (proxy [AttributeProvider] []
    (setAttributes [_node tag-name attributes]
      ; https://github.com/atlassian/commonmark-java/issues/74
      (if (= tag-name "pre") (.put ^Map attributes "class" "prettyprint")))))

(def attribute-provider-factory
  (proxy [AttributeProviderFactory] []
    (create [_context] attribute-provider)))

(def renderer (-> (HtmlRenderer/builder) (.attributeProviderFactory attribute-provider-factory) (.build)))

(def render-html (comp #(.render renderer %) #(.parse parser %)))
