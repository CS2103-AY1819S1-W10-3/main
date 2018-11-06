package seedu.address.commons.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.TimeTable;



/**
 * @author adjscent
 */
public class TimeTableUtil {

    /**
     * Examples
     * User selects share timetable
     * - http://modsn.us/H4v8s - is given
     * <p>
     * We will break that url down into the long url
     * https://nusmods.com/timetable/sem-1/share?CS2102=LEC:1,TUT:11&
     * CS2103=LEC:1,TUT:01&
     * CS2105=LEC:1,TUT:16&
     * CS2106=LAB:09,LEC:1,TUT:09&MA1521=LEC:1,TUT:4&
     * UCV2209=SEM:01
     * This long url contains the info we need :)
     */

    // Messages
    public static final String INVALID_URL = "Invalid NUSMODS URL";
    public static final String API_CALL_FAILURE = "Cannot retrieve NUSMODS module information";
    // URL indexs for nusmods
    private static final int SEMSTER_INDEX = 4;
    private static final int MODULE_INDEX = 5;

    // REGEX constants cos laziness
    private static final String REGEX_SLASH = "/";
    private static final String REGEX_EQUAL = "=";
    private static final String REGEX_DASH = "-";
    private static final String REGEX_COMMA = ",";
    private static final String REGEX_COLON = ":";
    private static final String REGEX_GET_SEPARATER = "\\?";
    private static final String REGEX_MODULE_SEPARATER = "&";


    /**
     * @param url
     * @return
     * @throws ParseException
     */
    public static TimeTable parseUrl(String url) throws ParseException {
        return parseLongUrl(parseShortUrl(url));
    }

    /**
     * @param urlString
     * @return
     * @throws ParseException
     */
    public static String parseShortUrl(String urlString) throws ParseException {

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

            // 3 sec timeout in case no internet or nusmods is down
            httpUrlConnection.setReadTimeout(3000);

            // Get the redirected link
            String longUrlString = httpUrlConnection.getHeaderField("Location");
            httpUrlConnection.disconnect();

            // Redirect failure handler
            if (longUrlString == null || longUrlString.equals("")) {
                throw new ParseException(INVALID_URL);
            }

            // Invalid short url handler
            if (longUrlString.equals(("http://modsn.us"))) {
                throw new ParseException(INVALID_URL);
            }

            if (httpUrlConnection.getResponseCode() == 403) {
                throw new ParseException(INVALID_URL);
            }

            return longUrlString;

        } catch (IOException e) {
            throw new ParseException(API_CALL_FAILURE);
        } catch (NullPointerException e) {
            throw new ParseException(API_CALL_FAILURE);
        }

    }

    /**
     * @param urlString
     * @return
     * @throws ParseException
     */
    public static TimeTable parseLongUrl(String urlString) throws ParseException {

        // variables
        ArrayList<Lesson> lessonList = new ArrayList<>();


        // split url into /../../../
        String[] parts = urlString.split(REGEX_SLASH);

        // get semster from url
        String semster = parts[SEMSTER_INDEX];

        // get each modules from url and remove share?
        String[] modules = parts[MODULE_INDEX].split(REGEX_GET_SEPARATER)[1].split(REGEX_MODULE_SEPARATER);

        // Get all of the (limited) module information via api call
        for (String module : modules) {
            ArrayList<Lesson> newlist = parseModule(module, semster);
            lessonList.addAll(newlist);
        }

        TimeTable timetable = new TimeTable(lessonList);
        return timetable;
    }

    /**
     * @param module
     * @param semster
     * @return
     * @throws ParseException
     */
    public static ArrayList<Lesson> parseModule(String module, String semster) throws ParseException {

        // split CS2103=LEC
        String[] moduleInfo = module.split(REGEX_EQUAL);
        String moduleCode = moduleInfo[0]; // CS2103

        // So that it is easier to select our own module slots when looking through the full list
        String[] moduleChosenSlotList = moduleInfo[1].split(REGEX_COMMA);

        HashMap<String, String> moduleChosenSlotMap = new HashMap<>();
        for (String moduleChosenSlot : moduleChosenSlotList) {
            String[] temp = moduleChosenSlot.split(REGEX_COLON);
            moduleChosenSlotMap.put(temp[0], temp[1]);
        }
        // Get Semester number
        int semsterNumber = Integer.parseInt(semster.split(REGEX_DASH)[1]);

        ArrayList<Lesson> lessonList = obtainModuleInfoFromApi(moduleCode, semsterNumber);

        ArrayList<Lesson> toBeAddedLessonList = new ArrayList<>();

        // Note that the api call will give back all timeslots of the module
        // We will need to select the ones that are relevant to us
        for (Lesson lesson : lessonList) {
            // Need to change Lecture to LEC, Tutorial to TUT... before using the hashmap
            String moduleSlotNum = moduleChosenSlotMap.get(lesson.getLessonType().substring(0, 3).toUpperCase());

            if (moduleSlotNum != null && moduleSlotNum.equals(lesson.getClassNo())) {
                toBeAddedLessonList.add(lesson);
            }
        }
        return toBeAddedLessonList;
    }


    /**
     * API call for nusmods
     *
     * @param moduleCode
     * @param semNum
     * @return
     * @throws ParseException
     */
    public static ArrayList<Lesson> obtainModuleInfoFromApi(String moduleCode, int semNum) throws ParseException {
        LocalDate currentDate = LocalDate.now();
        String acadYear;

        // Calculate current academic year
        if (currentDate.getMonthValue() <= 6) {
            acadYear = (currentDate.getYear() - 1) + "-" + (currentDate.getYear());
        } else {
            acadYear = currentDate.getYear() + "-" + (currentDate.getYear() + 1);
        }

        // Link format is correct as of 25/9/2018
        String link = "http://api.nusmods.com/" + acadYear + "/" + semNum + "/modules/" + moduleCode + ".json";
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Grab lesson info from API and store as a map
            URL url = new URL(link);
            @SuppressWarnings("unchecked")
            Map<String, Object> mappedJson = mapper.readValue(url, HashMap.class);
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, String>> lessonInfo = (ArrayList<HashMap<String, String>>)
                mappedJson.get("Timetable");

            // Parse the information from API and creates an Arraylist of all possible lessons
            ArrayList<Lesson> lessons = new ArrayList<>();
            for (HashMap<String, String> lesson : lessonInfo) {
                Lesson lessonToAdd = new Lesson(moduleCode, lesson.get("ClassNo"), lesson.get("LessonType"),
                    lesson.get("WeekText"), lesson.get("DayText"), lesson.get("StartTime"), lesson.get("EndTime"));
                lessons.add(lessonToAdd);
            }

            return lessons;
        } catch (IOException exception) {
            throw new ParseException(API_CALL_FAILURE);
        }
    }
}
