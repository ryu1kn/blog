
# Clojure Way

* Do I have to use `partial` to do currying?
  * Yes. cf. [Rich Hickey's reason for not auto-currying Clojure functions?][1]
* Do I have to do an explicit null check? e.g. `html-node` below can be `null`.

    ```clojure
    (-> html-node (.getTextNode) (.getLiteral))
    ```

  * Seems so. Need to repeat `html-node` twice.

    ```clojure
    (if html-node (-> html-node (.getTextNode) (.getLiteral)) "")
    ```

    cf. in Scala:

    ```scala
    htmlNode.map(_.getTextNode.getLiteral).getOrElse("")
    ```

* Can I compose a function from a Java object method with only its method name?
  * No. You may wrap with a lambda. cf. [Usage of java methods as a function argument in Clojure][2]

    ```clojure
    (comp #(.getBar %) #(.getFoo %))
    ```

[1]: https://stackoverflow.com/a/31374215/1780944
[2]: https://stackoverflow.com/a/23118522/1780944
