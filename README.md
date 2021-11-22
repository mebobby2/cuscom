# CusCom

## Run
mvn -q -X compile exec:java -Dexec.mainClass="com.company.cuscom.App"

## Run tests
mvn compile test

## Java Notes
* Java has four access modifiers: public, private, protected and default (no keyword).
  * The default access modifier is also called package-private, which means that all members are visible within the same package but aren't accessible from other packages

## Upto
Page 73

If you now run our Camel application, it still works as it used to

Before that:
Why the Camel Context configured via spring dsl is not picking up the camel routes
