
# Spring DI with different styles

Spring as a dependency injection (DI) framework allows us to define our
application components' dependency tree in three different ways:

* XML
* Annotation
* Java Config

I've written a simple app, bookstore, with the three styles and
they're available in the following repository. You can take
a look and see how each style would look like.
It also has a version that uses no Spring beans for comparison.

[https://github.com/ryu1kn/spring--working-with-beans](https://github.com/ryu1kn/spring--working-with-beans)

Different styles have different pros/cons. Spring lets you mix
the styles; so you can change it as you go.

Here is my observations on each style.

## XML-based configuration

### Pros

* Weak/loose coupling with Spring framework in your app code.
  * Good for keeping the option of removing Spring later
* Class dependency information is centralised.
* Fine-grained control on the dependency definition.
* Changing only the dependency information doesn't require
  the recompilation of your app code.

### Cons

* Unless you have a good IDE support (assisting by looking both XML and
  Java code), development feedback cycle is slow as you may not notice.
  an error until you run the app.
* XML can be harder to read for its verbosity.

## Annotation-based configuration

### Pros

* Less boilerplate code to define components dependency.
* Dependency rules are written on each component.

### Cons

* Tighter coupling with Spring framework. Your app becomes
  a Spring app.
* Class dependency information is decentralised.
* Harder to comprehend if the reader is not familiar with Spring.
* Finer grained control over the dependency definition can become
  tricky and brittle.

## Java Config based configuration

### Pros

* Weak/loose coupling with Spring framework in your app code.
* Bean can be defined without a concrete class.
* Bean definition can get good editor support thanks to static typing.
* Class dependency information is centralised.
* Fine-grained control on the dependency definition.

### Cons

* Compared to XML-based configuration, test setup may be more coupled
  with Spring.

<!-- POST_ID: b2ea45b5-e74c-41d6-beac-1aff5e9d76da -->
