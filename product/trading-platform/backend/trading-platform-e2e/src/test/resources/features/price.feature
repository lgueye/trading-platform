Feature: order matching

  Scenario: should persist price event, then update account valuation
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
      | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | alice   | 120000.0 |
    And order events
      | orderId                              | account                              | instrument   | quantity | status | timestamp                |
      | 76141572-62e6-404f-9659-3b7f51a4066f | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000200 | 50.0     | booked | 2020-03-19T09:10:05.000Z |
      | bd70bbe0-ef55-4a5e-9a22-bb9c6e3c0fd0 | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 25.0     | booked | 2020-03-19T10:10:05.000Z |
    # clock API
    When clock 2020-03-20T10:10:00.000Z
    And price event
      | instrument   | price |
      | LU-000000200 | 150.0 |
    # order events API
    Then within PT5S, instruments
      | id           | price |
      | LU-000000050 | 50    |
      | LU-000000100 | 100   |
      | LU-000000150 | 150   |
      | LU-000000200 | 150   |
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
    And within PT5S, price events
      | instrument   | price | timestamp                |
      | LU-000000200 | 150.0 | 2020-03-20T10:10:00.000Z |
    # accounts API
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"philipp","cash":100000.0,"valuation":107500.0,"assets":[{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
      | {"id":"8fb8f48b-23c6-4023-bbb5-1395c47aefd8","owner":"alice","cash":120000.0,"valuation":135000.0,"assets":[{"instrument":"LU-000000600","price":600.0,"quantity":25.0}]}   |

    When clock 2020-03-20T10:10:05.000Z
    And price event
      | instrument   | price |
      | LU-000000600 | 500.0 |
    Then within PT5S, instruments
      | id           | price |
      | LU-000000050 | 50    |
      | LU-000000100 | 100   |
      | LU-000000150 | 150   |
      | LU-000000200 | 150   |
      | LU-000000250 | 250   |
      | LU-000000300 | 300   |
      | LU-000000350 | 350   |
      | LU-000000400 | 400   |
      | LU-000000450 | 450   |
      | LU-000000500 | 500   |
      | LU-000000550 | 550   |
      | LU-000000600 | 500   |
      | LU-000000650 | 650   |
      | LU-000000700 | 700   |
      | LU-000000750 | 750   |
      | LU-000000800 | 800   |
      | LU-000000850 | 850   |
      | LU-000000900 | 900   |
      | LU-000000950 | 950   |
      | LU-000001000 | 1000  |
    And within PT5S, price events
      | instrument   | price | timestamp                |
      | LU-000000200 | 150.0 | 2020-03-20T10:10:00.000Z |
      | LU-000000600 | 500.0 | 2020-03-20T10:10:05.000Z |
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"philipp","cash":100000.0,"valuation":107500.0,"assets":[{"instrument":"LU-000000200","price":150.0,"quantity":50.0}]} |
      | {"id":"8fb8f48b-23c6-4023-bbb5-1395c47aefd8","owner":"alice","cash":120000.0,"valuation":132500.0,"assets":[{"instrument":"LU-000000600","price":500.0,"quantity":25.0}]}   |
