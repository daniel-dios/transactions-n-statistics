# Transactions statistics - Senior Engineer

## 🏃 To run

First, you may need to configure this:

```
JAVA_HOME=/Library/Java/JavaVirtualMachines/{your jdk 8 here}/Contents/Home/ 
```

Then just execute:

```
mvn clean install
mvn clean integration-test
```

## 🧑‍💻 About the challenge

I see challenge excellent, transactions contexts, some maths (accuracy) problems, concurrency, and Big O thing 😬. I've
applied domain-driven design with hexagonal architecture (use case, domain, and infra). Also, I've followed TDD, code
coverage is ~95% 😇.

**BTW, a big thank you to the one who created the integration test set, they were super helpful!!**

## 🐝 Hexagonal

### 👔 Domain

I have all the objects and logic for my domain here. Everything related to business that may change in future iterations

- **💰 Amount:** This object is big decimal container. It's the one that owns the logic about calculating min, max, sum,
  and divide operations. In order to **don't lose precision**, it keeps all the decimals but when somebody asks for its
  value it returns ROUND_HALF_UP.


- **⏰ TransactionTimestamp:** This object is the one that validates if a timestamp is in the last 60s range. As with the
  amount object, it returns its value with SECONDS truncated (*)


- **🤝 Transaction:** An amount, and the transaction time stamp.


- **🐜 Count:** Positive long wrapper, they can be added, incremented, and compared.


- **📊 Statistics:** This object contains sum, min, max, and count values: 4 amounts and 1 count. They have the
  knowledge to "aggregate" a transaction (will return a new object I don't like mutability 🙅), and the ability (logic)
  to be merged between them.

### 🏗 Infrastructure

You will see three things:

- **🧮 Configuration:** all beans creation.


- **🕹 Controllers:** one per use case, why? because I don't see any value in injecting save use case in delete all
  controller. Easy to test and mock and clean dependency injection.


- **🗂 Repository:** domain repository implementations, in this case I've used in memory one.

### 🔥 Use cases

There are three use cases, save transactions, delete all transactions and get statistics. I like to put all my logic
about validating requests, create and "use" the domain objects call repositories, blablaba. (cos they are use cases, no?
🤔).

## 🧩 Solution

### ☄️ Concurrency

In order to be thread-safe, I've decided to use in my in-memory repository a concurrent hashmap to put (merge) there my
statistics. I was about to use a reentrant lock that locks/unlocks the map but this map implementation is enough.

### ⏳ O(1)

To be constant in time and space I've decided to use the following algorithm:
Every transaction will be saved in a map with a bucket key referred to as the timestamp truncated by a second. The trick
is I'm not saving the transaction, I'm aggregating the transaction amount to the statistics of that second (keymap), if
there was no previous one, it will aggregate to empty statistics (Using the merge map method).

With this, I guarantee the map will never contain an infinite number of entries. Actually will contain 60 as max. Save
method will delete old entries and get will filter the ones inside the range. So max 60 items in a map (worst case) ->
O(1).

What happen if there is no more saves in a certain time window? Well, the map won't grow up, and get will filter out
those keys.

## 🧪 Tests

As mentioned, I'm a big TDD fan, so every code line was made after a test failing. I've used/added junit 5 (we should
update this skeleton challenge). There is also contract tests for controllers (with spring boot test and mockMvc). You
could disagree on how I'm testing the in memory repository: I'm using save and get to test each other, but I didn't want
to use reflections to get the map since is private or creating a public method only for testing purposes.

## 🚀 CI

I've added git hub actions in order to execute tests with every commit to master and PR open.

## 🙄 Missing something?

For going to production yes, but I see both things out of the scope of this challenge:

- I really, really, like to have a good observability 🔎 in my services, but I see enough for this challenge adding some
  logs for each flow. I'd like to have some metrics (with the proper tests) and distributed tracing mechanism (as
  sleuth).


- If "it-tests" were not present (thanks again 🤗), I'd like to have test/s dockerizing 🚢 the application and testing
  from outside as "end to end" tests but, I see "too much over-engineering" adding this to the solution.

## 🔁 Feedback

As I mentioned, I see the problem challenging, and I've enjoyed solving and coding it a lot. I see this two lines of
improvement:

- I'd like to have at least java11, junit5, and a mvn wrapper.


- Since I'm using GitHub actions to test and build the project maybe it could be helpful for reviewers going to the repo
  instead of opening my .zip submitted.

Thanks!