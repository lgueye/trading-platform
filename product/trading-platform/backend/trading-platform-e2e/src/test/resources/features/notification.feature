Feature: order matching

  Scenario: should be notified of account updates

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
      | id                                   | owner       | cash     |
      | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | usr-philipp | 100000.0 |
    And order events
      | orderId                              | account                              | instrument   | quantity | status | timestamp                |
      | 76141572-62e6-404f-9659-3b7f51a4066f | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000500 | 100.0    | booked | 2020-03-17T09:10:05.000Z |
      | e4aacabe-91ae-49ee-9b03-49ae4ae38ce8 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000200 | 50.0     | booked | 2020-03-19T09:10:05.000Z |
    And signed in as philipp / philipp
    And subscribed to notifications
#    Then within PT5S, connected to notifications

    # clock API
    When clock 2020-03-20T10:10:00.000Z
    And price event
      | instrument   | price |
      | LU-000000200 | 150.0 |
    Then within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"usr-philipp","cash":100000.0,"valuation":157500.0,"assets":[{"instrument":"LU-000000500","price":500.0,"quantity":100.0},{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
    And within PT5S, notifications
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"usr-philipp","cash":100000.0,"valuation":157500.0,"assets":[{"instrument":"LU-000000500","price":500.0,"quantity":100.0},{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |

    # clock API
    When clock 2020-03-20T10:10:05.000Z
    And price event
      | instrument   | price |
      | LU-000000500 | 400.0 |
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"usr-philipp","cash":100000.0,"valuation":147500.0,"assets":[{"instrument":"LU-000000500","price":400.0,"quantity":100.0},{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
    And within PT5S, notifications
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"usr-philipp","cash":100000.0,"valuation":157500.0,"assets":[{"instrument":"LU-000000500","price":500.0,"quantity":100.0},{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"usr-philipp","cash":100000.0,"valuation":147500.0,"assets":[{"instrument":"LU-000000500","price":400.0,"quantity":100.0},{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
