Feature: User Registration
  As a new user
  I want to register an account
  So that I can access personalized features

  Scenario Outline: User Registration
    Given I am on the registration page on "<browser>"
    When I select "<day>", "<month>", "<year>" as date of birth
    And I enter "<firstname>" as first name
    And I enter "<lastname>" as last name
    And I enter "<email>" as email
    And I enter "<confirmEmail>" as confirm email
    And I enter "<password>" as password
    And I enter "<confirmPassword>" as confirm password
    And I "<checkTerms>" the terms and conditions
    And I "<checkEthics>" the code of ethics and conduct
    And I submit the registration form
    Then I should see message

    Examples:
      | day | month | year | firstname | lastname | email                    | confirmEmail             | password | confirmPassword | checkTerms | checkEthics | browser |
      | 30  | Sep   | 1991 | Peter     | Babamov  | Pepi-xp+1337@hotmail.com | Pepi-xp+1337@hotmail.com | pass1337 | pass1337        | Yes        | Yes         | Chrome  |
      | 30  | Sep   | 1991 |           |          |                          |                          |          |                 | NO         | nO          | edge    |
      | 30  | Sep   | 1991 | Peter     |          | pepi-xp!@hotmail.com     | pepi-xp@hotmail.com      | pass123  | pass321         | yEs        |             | eDge    |
      | 30  | Sep   | 1991 | Peter     | Babamov  | pepi-xp@hotmail.com      |                          |          | pass123         |            | YES         | ChRoMe  |
      | 30  | Sep   | 1991 | Peter     | Babamov  |                          | pepi-xp@hotmail.com      | pass123  |                 | nO         |             | Chrome  |