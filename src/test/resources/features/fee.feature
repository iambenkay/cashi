Feature: Calculate Transaction Fee

  Scenario: Calculate fee for mobile top up
    Given I submit a transaction with id "txn_001" and amount 1000
    When the transaction type is "Mobile Top Up"
    Then the rate should be 0.0015 and the fee should be 1.5

  Scenario: Calculate fee for flight booking
    Given I submit a transaction with id "txn_002" and amount 30000
    When the transaction type is "Flight Booking"
    Then the rate should be 0.04 and the fee should be 1200

  Scenario: Calculate fee for domestic transfer
    Given I submit a transaction with id "txn_003" and amount 10000
    When the transaction type is "Domestic Transfer"
    Then the rate should be 0.025 and the fee should be 250