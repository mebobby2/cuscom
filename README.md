# Customer Compliance App

## Run
mvn -q -X compile exec:java -Dexec.mainClass="com.company.cuscom.App"

## Run tests
mvn compile test

run specific test: mvn compile test -Dtest=NetValueBeanTest#testFileParsedHigher

## Java Notes
* Java has four access modifiers: public, private, protected and default (no keyword).
  * The default access modifier is also called package-private, which means that all members are visible within the same package but aren't accessible from other packages

## Design Notes
### SEDA
https://stackoverflow.com/questions/3570610/what-is-seda-staged-event-driven-architecture

Thread Architecture vs Staged Event-Drive Architecture in real life: Imagine you have a restaurant. Now, how it will work?

#### with "Thread Architecture":
1. A customer arrives
2. Waiter(a) goes to him/her
3. Waiter(a) takes him/her to one available table
4. Waiter(a) takes the order
5. Waiter(a) cooks the order
6. Waiter(a) takes to order to the table
7. Waiter(a) waits until the client finishes his/her meal to pay
8, Waiter(a) walks the client out
9. In this case, the waiter is with the client during the whole process.

If the server has 10 threads, can handle 10 connections concurrently.
#### with SEDA:
1. A customer arrives
2. Waiter(a) goes to him/her
3. Waiter(a) takes him/her to one available table (and comes back for 4. another client to come)
4. Waiter(b) takes the order (lots of I/O, takes time)
5. Cook cooks the order
6. Waiter(c) takes to order to the table
7. Waiter(d) waits until the client finishes his/her meal to pay
8. Waiter(e) walks the client out

A Stage is analogous to an "Event". To simplify the idea, think of SEDA as a series of events sending messages between them.

One reason to use this kind of architecture, I think, is that you fragment the logic and can connect it and decouple each event, mainly for high performance services with low latency requirements fits well.

If you use Java TPE, you could monitor the health, throughput, errors, latency of each stage, and quickly find where the performance bottleneck is. And as a nice side effect, with smaller pieces of code, you can easily test them and increment your code coverage (that was my case).

For the record, this is the internal architecture of Cassandra (NoSQL), and Mule ESB (AFAIK).

#### Cassandra
http://what-when-how.com/Tutorial/topic-17159lu1r/Cassandra-The-Definitive-Guide-115.html

n a typical application, a single unit of work is often performed within the confines of asingle thread. A write operation, for example, will start and end within the same thread. Cas-sandra, however, is different: its concurrency model is based on SEDA, so a single operationmay start with one thread, which then hands off the work to another thread, which may handit off to other threads. But it's not up to the current thread to hand off the work to anotherthread. Instead, work is subdivided into what are called stages, and the thread pool (really, ajava.util.concurrent.ExecutorService) associated with the stage determines execution. Because each stage can be handled by a different thread pool, Cassandra ex-periences a massive performance improvement.

A stage in SEDA is a basic unit of work. A stage consists of an incoming event queue, an event handler, and an associated threadpool. Each stage also has a
controller for scheduling & thread allocation.

## Upto
Page 122

It should display that all tests ran successfully
