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

The e2e profile
---
Used as non regression tests for the whole system, runs at each run

```mvn clean install```


The demo profile
---
Meant to demonstrate the behavior on the local machine

```mvn clean verify -Pdemo --traffic.price.volatility= --traffic.price.min.frequency= --traffic.price.max.frequency= --traffic.trade.min.frequency= --traffic.trade.max.frequency=```

The remote profile
---
Meant to demonstrate the behavior whith a real remote platform

```mvn clean verify -Premote --traffic.price.volatility= --traffic.price.min.frequency= --traffic.price.max.frequency= --traffic.trade.min.frequency= --traffic.trade.max.frequency=```


