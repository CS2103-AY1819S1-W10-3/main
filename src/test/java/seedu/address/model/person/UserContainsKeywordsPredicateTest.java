package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class UserContainsKeywordsPredicateTest {

    @Test
    public void test() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList("first_name");
        List<String> secondPredicateNameKeywordList = Arrays.asList("first_name", "second_name");

        List<String> firstPredicatePhoneKeywordList = Collections.singletonList("first_phone");
        List<String> secondPredicatePhoneKeywordList = Arrays.asList("first_phone", "second_phone");

        List<String> firstPredicateAddressKeywordList = Collections.singletonList("first_address");
        List<String> secondPredicateAddressKeywordList = Arrays.asList("first_address", "second_address");

        List<String> firstPredicateEmailKeywordList = Collections.singletonList("first_address");
        List<String> secondPredicateEmailKeywordList = Arrays.asList("first_email", "second_email");

        List<String> firstPredicateInterestsKeywordList = Collections.singletonList("first_interest");
        List<String> secondPredicateInterestsKeywordList = Arrays.asList("first_interest", "second_interest");

        List<String> firstPredicateTagsKeywordList = Collections.singletonList("first_tag");
        List<String> secondPredicateTagsKeywordList = Arrays.asList("first_tag", "second_tag");

        UserContainsKeywordsPredicate firstPredicate =
                new UserContainsKeywordsPredicate(firstPredicateNameKeywordList,
                        firstPredicatePhoneKeywordList,
                        firstPredicateAddressKeywordList,
                        firstPredicateEmailKeywordList,
                        firstPredicateInterestsKeywordList,
                        firstPredicateTagsKeywordList);
        UserContainsKeywordsPredicate secondPredicate =
                new UserContainsKeywordsPredicate(secondPredicateNameKeywordList,
                        secondPredicatePhoneKeywordList,
                        secondPredicateAddressKeywordList,
                        secondPredicateEmailKeywordList,
                        secondPredicateInterestsKeywordList,
                        secondPredicateTagsKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UserContainsKeywordsPredicate firstPredicateCopy =
                new UserContainsKeywordsPredicate(firstPredicateNameKeywordList,
                        firstPredicatePhoneKeywordList,
                        firstPredicateAddressKeywordList,
                        firstPredicateEmailKeywordList,
                        firstPredicateInterestsKeywordList,
                        firstPredicateTagsKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywordsForNameOnly_returnsTrue() {
        // One nameKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList("Alice"),
                        null,
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Multiple nameKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        null,
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching nameKeyword
        predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        null,
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case nameKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("ALiCe", "bOb"),
                        null,
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_containsKeywordsForPhoneOnly_returnsTrue() {
        // One phoneKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        Collections.singletonList("94351253"),
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Only one matching phoneKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        Arrays.asList("94351253", "94351111"),
                        null,
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

    }

    @Test
    public void test_containsKeywordsForAddressOnly_returnsTrue() {
        // One addressKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Collections.singletonList("Jurong"),
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Multiple addressKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Arrays.asList("Jurong", "West"),
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Arrays.asList("Jurong", "Bedok"),
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Arrays.asList("JuROnG", "wESt"),
                        null,
                        null,
                        null);
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_containsKeywordsForEmailOnly_returnsTrue() {
        // One emailKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        Collections.singletonList("alice@example.com"),
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // only one matching emailKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        Arrays.asList("alice@example.com", "bob@example.com"),
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        Collections.singletonList("aLiCe@ExamPLE.coM"),
                        null,
                        null);
        assertTrue(predicate.test(ALICE));

    }

    @Test
    public void testContainsKeywordsForInterestOnly_returnsTrue() {
        // One interestKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList("study"),
                        null);
        assertTrue(predicate.test(ALICE));

        // Multiple interestKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("study", "swim"),
                        null);
        assertTrue(predicate.test(new PersonBuilder().withInterests("study", "swim").build()));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("study", "swim"),
                        null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("sTudY", "sWIm"),
                        null);
        assertTrue(predicate.test(new PersonBuilder().withInterests("study", "swim").build()));
    }

    @Test
    public void test_containsKeywordsForTagOnly_returnsTrue() {
        // One tagKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList("friends"));
        assertTrue(predicate.test(ALICE));

        // Multiple tagKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("friends", "husband"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "husband").build()));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("friends", "husband"));
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList("fRieNDS", "hUsBAnd"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "husband").build()));
    }

    @Test
    public void test_noKeywordsGiven_returnsFalse() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void testNameKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList(""),
                        null,
                        null,
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testNameKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList("      "),
                        null,
                        null,
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));

    }

    @Test
    public void testPhoneKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        Collections.singletonList(""),
                        null,
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testPhoneKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        Collections.singletonList("      "),
                        null,
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAddressKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Collections.singletonList(""),
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAddressKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        Collections.singletonList("      "),
                        null,
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testEmailKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        Collections.singletonList(""),
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testEmailKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        Collections.singletonList("      "),
                        null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testInterestKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList(""),
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testInterestKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList("       "),
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }


    @Test
    public void testTagKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testTagKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        Collections.singletonList(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAllKeywordsAreNull_returnsFalse() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void testAllKeywordsAreEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList(""),
                        Collections.singletonList(""),
                        Collections.singletonList(""),
                        Collections.singletonList(""),
                        Collections.singletonList(""),
                        Collections.singletonList(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAllKeywordsAreSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList("      "),
                        Collections.singletonList("      "),
                        Collections.singletonList("      "),
                        Collections.singletonList("      "),
                        Collections.singletonList("      "),
                        Collections.singletonList("      "));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));

    }

    @Test
    public void testNoMatchingKeywordsGiven_returnsFalse() {
        // single keyword
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList("Benson"),
                        Collections.singletonList("98765432"),
                        Collections.singletonList("Bedok"),
                        Collections.singletonList("johnd@example.com"),
                        Collections.singletonList("kayak"),
                        Collections.singletonList("fat"));
        assertFalse(predicate.test(ALICE));

        // multiple keyword
        predicate = new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "555555555"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@yahoo.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchNameOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Meir"),
                        Arrays.asList("98765432", "555555555"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@yahoo.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchPhoneOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "94351253"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@yahoo.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchAddressOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "5555555"),
                        Arrays.asList("Jurong", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@yahoo.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchEmailOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "5555555"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "alice@example.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchInterestOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "5555555"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@example.com"),
                        Arrays.asList("study", "bowl"),
                        Arrays.asList("fat", "fast"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void testMultipleKeywordsMatchTagOnly_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Benson", "Meir"),
                        Arrays.asList("98765432", "5555555"),
                        Arrays.asList("Bedok", "Serangoon"),
                        Arrays.asList("johnd@example.com", "peter@example.com"),
                        Arrays.asList("kayak", "bowl"),
                        Arrays.asList("friends", "fast"));
        assertTrue(predicate.test(ALICE));
    }


    @Test
    public void testMultipleKeywordsMatchAll_returnsTrue() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList("Alice"),
                        Arrays.asList("94351253"),
                        Arrays.asList("Jurong"),
                        Arrays.asList("alice@example.com"),
                        Arrays.asList("study"),
                        Arrays.asList("friends"));
        assertTrue(predicate.test(ALICE));
    }
}
