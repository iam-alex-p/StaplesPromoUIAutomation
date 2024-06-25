Feature: Staples Login

  @NegativeTests @RegressionSuite
  Scenario: Staples Website Login Verification
    Given Staples Main Page is open
    When User Icon Button is clicked
    Then Verify Staples Login workflow with the following Credentials
      | username | password        | isValidUsername | isValidPassword | isAuthentication | description                                     |
      |          | 1234qA          | true            | false           | false            | Valid Username, Invalid Password                |
      |          | tH~7@*+J9YiVR   | true            | true            | false            | Non-registered, but valid Email, Valid Password |
      | [blank]  | [blank]         | false           | false           | false            | Blank Email, Blank Password                     |
      |          | [blank]         | true            | false           | false            | Non-Registered Email, Blank Password            |
      | [blank]  | tH~7@*+J9YiVrkR | false           | true            | false            | Blank Email, Valid Password                     |