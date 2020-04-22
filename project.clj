(defproject blog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"]
                 [com.google.apis/google-api-services-blogger "v3-rev20190917-1.30.9"]
                 [com.google.auth/google-auth-library-oauth2-http "0.20.0"]
                 [com.atlassian.commonmark/commonmark "0.14.0"]
                 [com.atlassian.commonmark/commonmark-ext-gfm-tables "0.14.0"]]
  :main ^:skip-aot blog.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
