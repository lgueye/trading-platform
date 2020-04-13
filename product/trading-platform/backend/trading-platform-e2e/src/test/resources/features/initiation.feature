Feature: order initiation

  Scenario: should persist initiation event
    # instruments API
    Given instruments
      | id           | price |
      | LU-000000050 | 50    |
      | LU-000000100 | 100   |
      | LU-000000150 | 150   |
      | LU-000000200 | 200   |
      | LU-000000250 | 250   |
      | LU-000000300 | 300   |
      | LU-000000350 | 350   |
      | LU-000000400 | 400   |
      | LU-000000450 | 450   |
      | LU-000000500 | 500   |
      | LU-000000550 | 550   |
      | LU-000000600 | 600   |
      | LU-000000650 | 650   |
      | LU-000000700 | 700   |
      | LU-000000750 | 750   |
      | LU-000000800 | 800   |
      | LU-000000850 | 850   |
      | LU-000000900 | 900   |
      | LU-000000950 | 950   |
      | LU-000001000 | 1000  |
    # accounts API
    And accounts
      | id                                   | owner   | cash     |
      | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | philipp | 100000.0 |
    # clock API
    And clock 2020-03-20T10:10:00.000Z
    When initiation event
      | order                                | account                              | instrument   | quantity |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     |
    # order events API
    Then within PT5S, order events
      | orderId                              | account                              | instrument   | quantity | status    | timestamp                |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     | initiated | 2020-03-20T10:10:00.000Z |
    # accounts API
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"philipp","cash":100000.0,"valuation":100000.0,"orders":[{"orderId":"31b79aef-9780-462d-8eea-2d03c3759650","instrument":"LU-000000600","quantity":10.0,"status":"initiated","timestamp":"2020-03-20T10:10:00.000Z"}]} |
