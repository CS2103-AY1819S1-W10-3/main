package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    public static final String COMMA = ",";

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter "
                + "should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} seperated/not seperated by comma.<br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);
        try {
            ArrayList<Integer> values = new ArrayList<>();
            if (s.contains(COMMA) && countCommas(s) <= 1) { // check if more than two indexes are passed
                values = splitIntegersWithComma(s);
            } else {
                values.add(Integer.parseInt(s));
            }
            for (int value : values) {
                if (!(value > 0 && !s.startsWith("+"))) { // "+1" successfully parsed by Integer#parseInt(String)
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Given a string that separates two integers with a comma (eg "1,2"), this function returns
     * an ArrayList that contains the two integers
     */
    public static ArrayList<Integer> splitIntegersWithComma(String s) throws NumberFormatException {
        ArrayList<Integer> values = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(s, COMMA);
        while (st.hasMoreTokens()) {
            values.add(Integer.parseInt(st.nextToken()));
        }
        return values;
    }

    /**
     * Given a string that separates two integers with a comma (eg "1,2,3"), this function returns
     * the number of commas in the string.
     */
    public static int countCommas(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                count++;
            }
        }
        return count;
    }
}
