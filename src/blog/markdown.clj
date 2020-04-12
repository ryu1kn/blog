(ns blog.markdown
  (:import [org.commonmark.parser Parser]
           [org.commonmark.renderer.html HtmlRenderer]
           [org.commonmark.ext.gfm.tables TablesExtension]))

(def parser (.build (.extensions (Parser/builder) [(TablesExtension/create)])))
(def renderer (.build (HtmlRenderer/builder)))

(def render-html (comp #(.render renderer %) #(.parse parser %)))
