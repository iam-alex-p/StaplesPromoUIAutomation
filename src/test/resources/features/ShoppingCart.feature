Feature: Verification of Shopping Cart functionality
  Background:
    Given Staples Main Page is open
    When Create Account Button is clicked
    And User Account with following Data is created
      |  |
      |  |
    Given Staples Main Page is open
    When I Login as 1 User from the List

  @ShoppingCart @RegressionSuite
  Scenario: Add one Product To Shopping Cart
    And I perform Product Search for an Item with name "Seek Two-Way Tracker"
    And I open the Product Page for this Product
    And I upload a Product Logo from file "LogoTesting.jpg"
    When I add 400 items of the Product to the Shopping Cart
    And I open the Shopping Cart
    Then Cart Order Total Value should be calculated correctly