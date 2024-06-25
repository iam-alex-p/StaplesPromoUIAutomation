Feature: Verification of Product-related functionality
  Background:
    Given Staples Main Page is open
    When Create Account Button is clicked
    And User Account with following Data is created
      |  |
      |  |
    Given Staples Main Page is open
    When I Login as 1 User from the List

  @Products @RegressionSuite
  Scenario: Save Item For Later Once Verification
    And I perform Product Search for an Item with name "pen"
    And I select a Random Product from Search Result Set
    And I add a Random Product to Save For Later List
    Then Random Item should appear in Save For Later List Once
    When I remove all the Items from Save For Later List
    Then Items quantity should be 0 in the List

  @Products @RegressionSuite @Bug
  Scenario: Save Item For Later Several Times Verification
    And I perform Product Search for an Item with name "sunglasses"
    And I select a Random Product from Search Result Set
    And I add a Random Product to Save For Later List 2 times
    Then Random Item should appear in Save For Later List Once

  @Products @RegressionSuite @Coupons @Bug
  Scenario: Verify VIPSHIP Coupon
    Given Coupon "VIPSHIP" exists on the Page
    And I perform Product Search for an Item with name "Elliston Blanket"
    And I open the Product Page for this Product
    Then I Verify Product Amount for Coupon Hint