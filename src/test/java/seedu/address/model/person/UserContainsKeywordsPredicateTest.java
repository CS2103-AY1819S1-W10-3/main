package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.PART_OF_VALID_ADDRESS_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PART_OF_VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE_IN_MIXED_CASES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_STUDY_IN_MIXED_CASES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_SWIM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_SWIM_IN_MIXED_CASES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE_FIRST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_FIRST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALICE_IN_MIXED_CASES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND_IN_MIXED_CASES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
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
                new UserContainsKeywordsPredicate(firstPredicateNameKeywordList, firstPredicatePhoneKeywordList,
                        firstPredicateAddressKeywordList, firstPredicateEmailKeywordList,
                        firstPredicateInterestsKeywordList, firstPredicateTagsKeywordList);
        UserContainsKeywordsPredicate secondPredicate =
                new UserContainsKeywordsPredicate(secondPredicateNameKeywordList, secondPredicatePhoneKeywordList,
                        secondPredicateAddressKeywordList, secondPredicateEmailKeywordList,
                        secondPredicateInterestsKeywordList, secondPredicateTagsKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UserContainsKeywordsPredicate firstPredicateCopy =
                new UserContainsKeywordsPredicate(firstPredicateNameKeywordList, firstPredicatePhoneKeywordList,
                        firstPredicateAddressKeywordList, firstPredicateEmailKeywordList,
                        firstPredicateInterestsKeywordList, firstPredicateTagsKeywordList);
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
                        Collections.singletonList(VALID_NAME_ALICE_FIRST_NAME), null, null,
                        null, null, null);
        assertTrue(predicate.test(ALICE));

        // Multiple nameKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList(VALID_NAME_ALICE.split(" ")), null,
                        null, null, null, null);
        assertTrue(predicate.test(ALICE));

        // Only one matching nameKeyword
        predicate =
                new UserContainsKeywordsPredicate(
                        Arrays.asList(VALID_NAME_ALICE_FIRST_NAME, VALID_NAME_BOB_FIRST_NAME), null,
                        null, null, null, null);
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_containsKeywordsForPhoneOnly_returnsTrue() {
        // One phoneKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, Collections.singletonList(VALID_PHONE_ALICE),
                        null, null, null, null);
        assertTrue(predicate.test(ALICE));

        // Only one matching phoneKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null,
                        Arrays.asList(VALID_PHONE_ALICE, VALID_PHONE_BOB), null, null,
                        null, null);
        assertTrue(predicate.test(ALICE));

    }

    @Test
    public void test_containsKeywordsForAddressOnly_returnsTrue() {
        // One addressKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null,
                        Collections.singletonList(PART_OF_VALID_ADDRESS_ALICE), null, null, null);
        assertTrue(predicate.test(ALICE));

        // Multiple addressKeywords
        predicate =
                new UserContainsKeywordsPredicate(
                        null, null, Arrays.asList(VALID_ADDRESS_ALICE.split(" ")),
                        null, null, null);
        assertTrue(predicate.test(ALICE));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(
                        null, null,
                        Arrays.asList(PART_OF_VALID_ADDRESS_ALICE, PART_OF_VALID_ADDRESS_BOB), null,
                        null, null);
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_containsKeywordsForEmailOnly_returnsTrue() {
        // One emailKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        Collections.singletonList(VALID_EMAIL_ALICE), null, null);
        assertTrue(predicate.test(ALICE));

        // only one matching emailKeywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        Arrays.asList(VALID_EMAIL_ALICE, VALID_EMAIL_BOB), null, null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        Collections.singletonList(VALID_EMAIL_ALICE_IN_MIXED_CASES), null,
                        null);
        assertTrue(predicate.test(ALICE));

    }

    @Test
    public void testContainsKeywordsForInterestOnly_returnsTrue() {
        // One interestKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, Collections.singletonList(VALID_INTEREST_STUDY), null);
        assertTrue(predicate.test(ALICE));

        // Multiple interestKeywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, Arrays.asList(VALID_INTEREST_STUDY, VALID_INTEREST_SWIM), null);
        assertTrue(predicate.test(new PersonBuilder().withInterests(VALID_INTEREST_STUDY,
                VALID_INTEREST_SWIM).build()));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, Arrays.asList(VALID_INTEREST_STUDY, VALID_INTEREST_SWIM), null);
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null,
                        Arrays.asList(VALID_INTEREST_STUDY_IN_MIXED_CASES, VALID_INTEREST_SWIM_IN_MIXED_CASES),
                        null);
        assertTrue(predicate.test(new PersonBuilder().withInterests(VALID_INTEREST_STUDY,
                VALID_INTEREST_SWIM).build()));
    }

    @Test
    public void test_containsKeywordsForTagOnly_returnsTrue() {
        // One tagKeyword only
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, Collections.singletonList(VALID_TAG_ALICE));
        assertTrue(predicate.test(ALICE));

        // Multiple tagKeywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, Arrays.asList(VALID_TAG_ALICE, VALID_TAG_HUSBAND));
        assertTrue(predicate.test(new PersonBuilder().withTags(VALID_TAG_ALICE, VALID_TAG_HUSBAND).build()));

        // Only one matching keyword
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, Arrays.asList(VALID_TAG_ALICE, VALID_TAG_HUSBAND));
        assertTrue(predicate.test(ALICE));

        // Mixed-case keywords
        predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null,
                        Arrays.asList(VALID_TAG_ALICE_IN_MIXED_CASES, VALID_TAG_HUSBAND_IN_MIXED_CASES));
        assertTrue(predicate.test(new PersonBuilder().withTags(VALID_TAG_ALICE, VALID_TAG_HUSBAND).build()));
    }

    @Test
    public void test_noKeywordsGiven_returnsFalse() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, null);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void testNameKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(Collections.singletonList(""), null, null,
                        null, null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testNameKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList(PREAMBLE_WHITESPACE), null, null, null,
                        null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));

    }

    @Test
    public void testPhoneKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, Collections.singletonList(""), null,
                        null, null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testPhoneKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        null, Collections.singletonList(PREAMBLE_WHITESPACE), null,
                        null, null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAddressKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null,
                        Collections.singletonList(""), null, null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAddressKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null,
                        Collections.singletonList(PREAMBLE_WHITESPACE), null, null,
                        null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testEmailKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        Collections.singletonList(""), null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testEmailKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        Collections.singletonList(PREAMBLE_WHITESPACE), null, null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testInterestKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, Collections.singletonList(""), null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testInterestKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, Collections.singletonList(PREAMBLE_WHITESPACE), null);
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testTagKeywordIsEmpty_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, Collections.singletonList(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testTagKeywordIsSpacesOnly_throwsIllegalArgumentException() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, Collections.singletonList(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> predicate.test(ALICE));
    }

    @Test
    public void testAllKeywordsAreNull_returnsFalse() {
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, null, null,
                        null, null, null);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void testNoMatchingKeywordsGiven_returnsFalse() {
        // single keyword
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(
                        Collections.singletonList(VALID_NAME_BOB_FIRST_NAME),
                        Collections.singletonList(VALID_PHONE_BOB),
                        Collections.singletonList(PART_OF_VALID_ADDRESS_BOB),
                        Collections.singletonList(VALID_EMAIL_BOB),
                        Collections.singletonList(VALID_INTEREST_SWIM),
                        Collections.singletonList(VALID_TAG_HUSBAND));
        assertFalse(predicate.test(ALICE));

        // multiple keyword
        predicate = new UserContainsKeywordsPredicate(
                        Arrays.asList(VALID_NAME_BOB.split(" ")),
                        Arrays.asList(VALID_PHONE_BOB, VALID_PHONE_AMY),
                        Arrays.asList(PART_OF_VALID_ADDRESS_BOB),
                        Arrays.asList(VALID_EMAIL_BOB, VALID_EMAIL_AMY),
                        Arrays.asList(VALID_INTEREST_SWIM, VALID_INTEREST_PLAY),
                        Arrays.asList(VALID_TAG_HUSBAND, VALID_TAG_STUDENT));
        assertFalse(predicate.test(ALICE));
    }
}
