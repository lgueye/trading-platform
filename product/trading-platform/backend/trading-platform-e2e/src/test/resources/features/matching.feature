Feature: order matching

  Scenario: should persist initiation event, then match order
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
      | 76141572-62e6-404f-9659-3b7f51a4066f | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 10.0     | booked | 2020-03-19T09:10:05.000Z |
      | bd70bbe0-ef55-4a5e-9a22-bb9c6e3c0fd0 | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 15.0     | booked | 2020-03-19T10:10:05.000Z |

    # clock API
    And clock 2020-03-20T10:10:00.000Z
    When initiation event
      | order                                | account                              | instrument   | quantity |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     |
    # order events API
    Then within PT5S, order events
      | orderId                              | account                              | instrument   | quantity | status    | timestamp                |
      | 76141572-62e6-404f-9659-3b7f51a4066f | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 10.0     | booked    | 2020-03-19T09:10:05.000Z |
      | bd70bbe0-ef55-4a5e-9a22-bb9c6e3c0fd0 | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 15.0     | booked    | 2020-03-19T10:10:05.000Z |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     | initiated | 2020-03-20T10:10:00.000Z |
    # accounts API
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"philipp","cash":100000.0,"valuation":100000.0,"orders":[{"orderId":"31b79aef-9780-462d-8eea-2d03c3759650","instrument":"LU-000000600","price":600.0,"quantity":10.0,"status":"initiated","timestamp":"2020-03-20T10:10:00.000Z"}]} |
      | {"id":"8fb8f48b-23c6-4023-bbb5-1395c47aefd8","owner":"alice","cash":120000.0,"valuation":135000.0,"assets":[{"instrument":"LU-000000600","price":600.0,"quantity":25.0}]}                                                                                             |

    When clock 2020-03-20T10:10:05.000Z
    And matching event
      | order                                | counterpart                          |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 |
    Then within PT5S, order events
      | orderId                              | account                              | instrument   | quantity | status    | timestamp                |
      | 76141572-62e6-404f-9659-3b7f51a4066f | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 10.0     | booked    | 2020-03-19T09:10:05.000Z |
      | bd70bbe0-ef55-4a5e-9a22-bb9c6e3c0fd0 | 8fb8f48b-23c6-4023-bbb5-1395c47aefd8 | LU-000000600 | 15.0     | booked    | 2020-03-19T10:10:05.000Z |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     | initiated | 2020-03-20T10:10:00.000Z |
      | 31b79aef-9780-462d-8eea-2d03c3759650 | 32e7480a-4aef-45ec-bf1a-bc5c49932e9f | LU-000000600 | 10.0     | matched   | 2020-03-20T10:10:05.000Z |
    And within PT5S, accounts
      | {"id":"32e7480a-4aef-45ec-bf1a-bc5c49932e9f","owner":"philipp","cash":100000.0,"orders":[{"orderId":"31b79aef-9780-462d-8eea-2d03c3759650","instrument":"LU-000000600","price":600.0,"quantity":10.0,"status":"matched","timestamp":"2020-03-20T10:10:05.000Z"}]} |
      | {"id":"8fb8f48b-23c6-4023-bbb5-1395c47aefd8","owner":"alice","cash":120000.0,"assets":[{"instrument":"LU-000000600","price":600.0,"quantity":25.0}]}                                                                                           |
