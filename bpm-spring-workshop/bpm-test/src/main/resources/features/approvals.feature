Feature: Vacation Approvals

  Scenario: Auto approved short vacation request
    Given a vacation request that is 1 day long
    When I submit the vacation request
    Then the request is approved

  Scenario: Manager approved long vacation
    Given a vacation request that is 3 days long
    When I submit the vacation request
    And my manager approves the request
    Then the request is approved

  Scenario: Manager approved long vacation - manager does not approve
    Given a vacation request that is 3 days long
    When I submit the vacation request
    Then the request is not approved

  Scenario: Manager approved long vacation - manager asks for more info
    Given a vacation request that is 3 days long
    When I submit the vacation request
    And my manager asks for more information
    And I reply with a acceptable answer
    And my manager approves the request
	Then the request is approved
