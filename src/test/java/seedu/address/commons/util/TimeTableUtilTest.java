package seedu.address.commons.util;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class TimeTableUtilTest {


    /*
     * Test Line
     * edit 1 tt/ http://modsn.us/H4v8s
     */

    private String shortlink = "http://modsn.us/H4v8s";
    private String actuallonglink = "https://nusmods.com/timetable/sem-1/"
            + "share?CS2102=LEC:1,TUT:11&CS2103=LEC:1,TUT:01&CS2105=LEC:1,TUT:16"
            + "&CS2106=LAB:09,LEC:1,TUT:09&MA1521=LEC:1,TUT:4&UCV2209=SEM:01";
    private String scheduleString = "0000000000000000111100000000111100000000000"
            + "00000000000000000000000000000111111110000000000000000000000000000000000001100000"
            + "0111100000000000000000000000000000000000000001111000011"
            + "11000000000000000000000000000000111100"
            + "001111111111000000000000000000000000000000000000000"
            + "000000000000000000000000000000000000000000000000000000000000000000000";

    private String shortlink2 = "http://modsn.us/lFDH3";

    private String actuallonglink2 = "https://nusmods.com/timetable/sem-1/share?CS2105=LEC:1,TUT:09";

    private String scheduleString2 = "000000000000000000000000000011110000000000000000"
        +
        "0000000000000000000000000000000000000000000000000000000000000000000"
        +
        "00000000000000000000000000000000000000000000000000000000000000000000"
        +
        "0000000000000000000000000000000000000110000000000000000000000000000"
        +
        "0000000000000000000000000000000000000000000000000000000000000000000"
        +
        "0000000000000000000";

    @Test
    public void parseShortUrl() throws ParseException {
        assert actuallonglink.equals(TimeTableUtil.parseShortUrl(shortlink));
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.parseShortUrl("http://mo.us/4v8s"); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.parseShortUrl("http://modsn.us/2322111"); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.parseShortUrl("htt://modsn.us/"); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.parseShortUrl("http://odsn.us/"); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.parseShortUrl("http://modsn.us/"); });
    }

    @Test
    public void convertToSchedule() throws ParseException {
        assert TimeTableUtil.parseUrl(shortlink).convertToSchedule()
               .valueToString().equals(scheduleString);
        assert TimeTableUtil.parseUrl(shortlink2).convertToSchedule()
            .valueToString().equals(scheduleString2);
    }

    @Test
    public void parseUrl() throws ParseException {
        // This is just a wrapper method call
        assert TimeTableUtil.parseUrl(shortlink).convertToSchedule()
            .valueToString().equals(scheduleString);
        assert TimeTableUtil.parseUrl(shortlink2).convertToSchedule()
            .valueToString().equals(scheduleString2);
    }

    @Test
    public void parseLongUrl() throws ParseException {
        assert TimeTableUtil.parseLongUrl(actuallonglink).convertToSchedule()
            .valueToString().equals(scheduleString);
        assert TimeTableUtil.parseLongUrl(actuallonglink2).convertToSchedule()
            .valueToString().equals(scheduleString2);
    }

    @Test
    public void parseModule() throws ParseException {
        assert TimeTableUtil.parseModule("CS2103=LEC:1,TUT:01", "sem-1").size() == 2;
    }

    @Test
    public void obtainModuleInfoFromApi() throws ParseException {
        assert TimeTableUtil.obtainModuleInfoFromApi("CS2103", 1).size() == 6;
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.obtainModuleInfoFromApi("123123", 1); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.obtainModuleInfoFromApi("", 0); });
        assertThrows(ParseException.class, () -> {
            TimeTableUtil.obtainModuleInfoFromApi("", 2); });
    }

    @Test
    public void emptyTimetable() throws ParseException {
        String emptyTimetableLink = "http://modsn.us/eDmp1";
        assert !TimeTableUtil.parseUrl(emptyTimetableLink).convertToSchedule()
            .valueToString().contains("1");

    }
}
