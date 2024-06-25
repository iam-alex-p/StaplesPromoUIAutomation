Feature: Staples Passwords
  Background:
    Given Staples Main Page is open

  @Passwords @Security @RegressionSuite
  Scenario: Staples Password Change Verification
    When Create Account Button is clicked
    And User Account with following Data is created
      |  |
      |  |
    Given Staples Main Page is open
    When I Login as 1 User from the List
    When I change User Password to a Random 5 times
    Then I should not be able to use old Passwords as New Ones

  @Passwords @Security @RegressionSuite @Bug
  Scenario: Staples [52-128] Characters Password Change Verification
    When Create Account Button is clicked
    And User Account with following Data is created
      | password                                                     | passwordConfirmation                                         | description                      |
      | jyE3aLbzm1qLxvqqwkhkkz3DCMe1uy5DRK4EwEVSgPPLFYUtN4Qy38wjmMKE | jyE3aLbzm1qLxvqqwkhkkz3DCMe1uy5DRK4EwEVSgPPLFYUtN4Qy38wjmMKE | Password Length is 60 Characters |
    Given Staples Main Page is open
    When I Login as 1 User from the List
    When I change User Password to a Random one with Length within the Range of 52 and 128
    Then Message about successful Password Change should appear