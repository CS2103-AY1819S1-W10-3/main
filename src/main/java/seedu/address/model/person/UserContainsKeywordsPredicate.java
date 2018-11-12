package seedu.address.model.person;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code attributes} matches any of the associated keywords given.
 */
public class UserContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> addressKeywords;
    private final List<String> emailKeywords;
    private final List<String> interestKeywords;
    private final List<String> tagKeywords;
    private boolean nameKeywordsExist;
    private boolean phoneKeywordsExist;
    private boolean addressKeywordsExist;
    private boolean emailKeywordsExist;
    private boolean interestKeywordsExist;
    private boolean tagKeywordsExist;

    public UserContainsKeywordsPredicate(List<String> nameKeywords, List<String> phoneKeywords,
                                         List<String> addressKeywords, List<String> emailKeywords,
                                         List<String> interestKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.nameKeywordsExist = (nameKeywords != null);

        this.phoneKeywords = phoneKeywords;
        this.phoneKeywordsExist = (phoneKeywords != null);

        this.addressKeywords = addressKeywords;
        this.addressKeywordsExist = (addressKeywords != null);

        this.emailKeywords = emailKeywords;
        this.emailKeywordsExist = (emailKeywords != null);

        this.interestKeywords = interestKeywords;
        this.interestKeywordsExist = (interestKeywords != null);

        this.tagKeywords = tagKeywords;
        this.tagKeywordsExist = (tagKeywords != null);
    }

    @Override
    public boolean test(Person person) {
        if (!nameKeywordsExist && !phoneKeywordsExist && !addressKeywordsExist && !emailKeywordsExist
                && !interestKeywordsExist && !tagKeywordsExist) {
            return false;
        }

        boolean matchesNameKeywords = (!nameKeywordsExist)
                || attributeMatchesKeywords(person.getName().value, nameKeywords);
        boolean matchesPhoneKeywords = (!phoneKeywordsExist)
                || attributeMatchesKeywords(person.getPhone().value, phoneKeywords);
        boolean matchesAddressKeywords = (!addressKeywordsExist)
                || attributeMatchesKeywords(person.getAddress().value, addressKeywords);
        boolean matchesEmailKeywords = (!emailKeywordsExist)
                || attributeMatchesKeywords(person.getEmail().value, emailKeywords);
        boolean matchesInterestKeywords = (!interestKeywordsExist) || interestKeywords.stream()
                    .allMatch(interestKeyword -> StringUtil.containsWordIgnoreCase(person.getInterests()
                            .stream()
                            .map(x -> x.interestName)
                            .collect(joining(" ")), interestKeyword));
        boolean matchesTagKeywords = (!tagKeywordsExist) || tagKeywords.stream()
                    .allMatch(tagKeyword -> StringUtil.containsWordIgnoreCase(person.getTags()
                            .stream()
                            .map(x -> x.tagName)
                            .collect(joining(" ")), tagKeyword));

        return matchesNameKeywords && matchesPhoneKeywords && matchesAddressKeywords && matchesEmailKeywords
                && matchesInterestKeywords && matchesTagKeywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserContainsKeywordsPredicate // instanceof handles nulls
                && (nameKeywords == null
                || nameKeywords.equals(((UserContainsKeywordsPredicate) other).nameKeywords))
                && (phoneKeywords == null
                || phoneKeywords.equals(((UserContainsKeywordsPredicate) other).phoneKeywords))
                && (addressKeywords == null
                || addressKeywords.equals(((UserContainsKeywordsPredicate) other).addressKeywords))
                && (emailKeywords == null
                || emailKeywords.equals(((UserContainsKeywordsPredicate) other).emailKeywords))
                && (interestKeywords == null
                || interestKeywords.equals(((UserContainsKeywordsPredicate) other).interestKeywords))
                && (tagKeywords == null
                || tagKeywords.equals(((UserContainsKeywordsPredicate) other).tagKeywords)));
    }

    private boolean attributeMatchesKeywords(String attribute, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(attribute, keyword));
    }
}
