Introduction
----

Show-case application to experiment architectures, technologies, frameworks and techniques

The idea is to start with a very simple application whose functions are inherently distributed across multiple systems.

The application will also be event-driven therefore asynchronous 

A trading system is a good candidate because there are events everywhere:

- when a user expresses its intent trade goods on the market (buy/sell a quantity of a tradable)
- when the intent is matched on the market by a counterpart
- when the trade is booked (as in book-keeping)
- when a price change impacts its portfolio valuation

One can hardly imagine the complexity of the impacts of such events in real life but we'll keep it intentionally simple

If I were to demonstrate a POC for this I would like to:

- provision the application and its backing infrastructure on a cloud provider
- start injecting events into the system
- vizualize the evolution of accounts/portfolios on a UI

Allright, we have the use case, and a very rough idea of a way to demonstrate our point.

Before diving in the details let identify our core runtimes:

- various backends to process the actual logic (mostly reactions on events)
- a web server to host our UI
- a persistence storage
- a messaging solution
- a discovery component
- infrastructure components required to make the system observable (logs, metrics, alerting, monitoring, tracing)

The UI
---

- SPA with 4 pages (subscribe, login, home, place trade)
- connected to a REST API
- connected to a web socket endpoint

The backends
---

- iam: identification and authorization management backend
- market: initiation and matching processing
- account: consolidates all portfolios valuations by processing order events and position events
- instrument: stores price events
- booking: process booking (store in a book)
- traffic-generator: randomly matches trades

The platform components
----

- clock: used in e2e to make tests predictable (In production the client will be different, the server will not be deployed)
- messaging: messaging solution (will be deployed on the real platform) 
- persistence: state holder (will be deployed on the real platform)
- traffic: generates traffic in demo mode (not meant to be deployed). Allows one to configure traffic scenarios (accounts scope, instruments scope, price variations, event frequency)

The regular build
---

Compile, package, unit and integration tests

```mvn clean install```

The e2e profile
---

Used as non regression tests for the whole system

The profile will perform the following sequence:
- start the `H2` DB in `tcp mode` encapsulated in a spring-boot backend (spring-boot backend started through `process-exec-maven-plugin`, `test` phase)
- create the schema (`sql-maven-plugin`, `prepare-package` phase)
- apply the DDL (`flyway-maven-plugin`, `package` phase)
- populate the db (`sql-maven-plugin`, `pre-integration-test` phase)
- start `messaging` (`activemq`), `clock`, `instrument`, `account`, `booking`, `market` and `iam` backends (spring-boot backends started through `process-exec-maven-plugin`, `pre-integration-test` phase)
- run e2e tests (maven-failsafe-plugin, `integration-test` phase)

```cd trading-platform-e2e && mvn clean install -Pe2e && cd -```

The demo profile
---

Meant to demonstrate the behavior on the local machine.

The profile will perform the following sequence:
- start the `H2` DB in `tcp mode` encapsulated in a spring-boot backend (spring-boot backend started through `process-exec-maven-plugin`, `test` phase)
- create the schema (`sql-maven-plugin`, `prepare-package` phase)
- apply the DDL (`flyway-maven-plugin`, `package` phase)
- populate the db (`sql-maven-plugin`, `pre-integration-test` phase)
- start `messaging` (`activemq`), `clock`, `instrument`, `account`, `booking`, `market` and `iam` backends (spring-boot backends started through `process-exec-maven-plugin`, `pre-integration-test` phase)
- start `traffic` backend (spring-boot backends started through `process-exec-maven-plugin`, `pre-integration-test` phase). The following behaviors can be controlled
  - `start` (-Dstart= or spring boot --start=): when the traffic backend should start sending events, iso8601 java 8 instant format, ex: `2020-04-15T10:00:00.00Z` (empty value will start immediately) 
  - `duration` (-Dduration= or spring boot --duration=): for how long the traffic backend should send events, iso8601 java 8 instant format, ex: `PT20M` (default is `PT10M`) 
  - `trades.delay-interval` (-Dtrades.delay-interval= or spring boot --trades.delay-interval=) : delay between 2 trades (lower bound and upper bound), iso8601 java 8 instant format. ex PT0.5S,PT1S will instruct the traffic server to schedule next trade not less than PT0.5S but not more than PT1S after previous trade   
  - `trades.transition-interval` (-Dtrades.transition-interval= or spring boot --trades.transition-interval=) : delay between 2 transition of the same trade (lower bound and upper bound), iso8601 java 8 instant format. ex PT0.5S,PT1S will instruct the traffic server to schedule next trade transition (matched, booked, etc) not less than PT0.5S but not more than PT1S after previous transition   
  - `trades.max-quantity` (-Dtrades.max-quantity= or spring boot --trades.max-quantity=) : max tradable quantity, will vary between [-max , +max], negative quantities are SELL, positive are BUY, 0 is excluded, integer.    
  - `prices.delay-interval` (-Dprices.delay-interval= or spring boot --prices.delay-interval=) : delay between 2 price events (lower bound and upper bound), iso8601 java 8 instant format. ex PT0.2S,PT0.4S will instruct the traffic server to schedule next price event not less than PT0.2S but not more than PT0.4S after previous price event   
  - `prices.max-variation` (-Dtrades.max-variation= or spring boot --trades.max-variation=) : max price variation, will vary between [-max , +max], integer.    


```cd trading-platform-e2e && mvn clean install -Pstandalone``` => starts traffic immediately after the traffic backends is fully setup (uses defaults)

```cd trading-platform-e2e && mvn verify -Pstandalone -Dstart=2020-04-22T16:58:00.00Z -Dduration=PT1M -Dtrades.delay-interval=PT0.2S,PT0.3S -Dtrades.transition-interval=PT0.5S,PT1S -Dtrades.max-quantity=100 -Dprices.delay-interval=PT0.5S,PT1S -Dprices.max-variation=5``` => starts traffic at provided time and uses the configuration provided on command line

Hit `Enter` to finish the build at any time (tear down all backends) 
