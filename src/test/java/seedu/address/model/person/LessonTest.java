package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class LessonTest {
    @Test
    void testMethods() throws ParseException {
        String moduleCode = "CS2103";
        String classNo = "1";
        String lessonType = "LEC";
        String weekText = "1";
        String dayText = "1";
        String startTime = "0800";
        String endTime = "0900";
        int duration = Integer.parseInt(endTime.substring(0, 2))
            - Integer.parseInt(startTime.substring(0, 2));

        Lesson lesson = new Lesson(moduleCode, classNo, lessonType, weekText, dayText, startTime, endTime);
        assertEquals(lesson.getDayText(), dayText);
        assertEquals(lesson.getStartTime(), startTime);
        assertEquals(lesson.getEndTime(), endTime);
        assertEquals(lesson.getDuration(), duration);
        assertEquals(lesson.getClassNo(), classNo);
        assertEquals(lesson.getLessonType(), lessonType);
        assertEquals(lesson.getModuleCode(), moduleCode);
        assertEquals(lesson.getWeekText(), weekText);
        lesson.toString();
    }
}
