package seedu.address.model.person;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;


class TimeTableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TimeTable(null));
    }

    @Test
    void testMethods() throws ParseException {
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("CS2103", "1", "LEC", "1", "tuesday", "0800", "0900"));
        lessons.add(new Lesson("CS2103", "1", "LEC", "1", "tuesday", "1100", "1230"));
        TimeTable t = new TimeTable(lessons);
        t.convertToSchedule();
        lessons = t.getLessonList();
        t.setLessonList(lessons);
    }

}
