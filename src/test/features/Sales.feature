@tag
Feature: Sales file verification

  @tag1
  Scenario: File is accessed, read and verified
    Given I access the test data file
    When The file information are read
    Then It should verify the number of lines successfully imported
    And It should verify the number of customers with invalid CPF
    And It should verify the name of the oldest customer
    And It should verify the name of the worst salesman
    And It should verify the id of the most expensive sale
    And It should generate a flat file with the test result
