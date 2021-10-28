package com.example.springplayground.api.issue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @SpringBootTest
// @WebMvcTest(IssueController.class)
// @ContextConfiguration(classes = SpringPlaygroundApplication.class)
// @ActiveProfiles({"test"})
class IssueControllerTests {

    // @Autowired
    // private IssueService issueService;

    // @Test
    // @DisplayName("List Issue 200 OK")
    // void testIssueList() {
    //     // given
    //     IssueController issueController = new IssueController(issueService);
    //     SearchIssues searchIssues = new SearchIssues();
    //
    //     // when
    //     ResponseEntity<List<IssueDTO>> testIssues = issueController.getIssues(searchIssues);
    //
    //     // then
    //     Assertions.assertEquals("200", testIssues.getStatusCode().toString());
    // }

    Calculator underTest = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {
        // given
        int numberOne = 20;
        int numberTwo = 30;

        // when
        int result = underTest.add(numberOne, numberTwo);

        // then
        int expected = 50;
        assertThat(result).isEqualTo(expected);
    }

    static class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }


}