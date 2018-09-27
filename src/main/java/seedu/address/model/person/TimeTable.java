package seedu.address.model.person;

import java.util.ArrayList;

/**
 * @author adjscent
 */
public class TimeTable {


    /**
     * Example lessonlist
     * CS2102 1 Lecture Every Week Monday 0800 1000
     * CS2102 11 Tutorial Every Week Thursday 1600 1800
     * CS2103 1 Lecture Every Week Friday 1600 1800
     * CS2103 01 Tutorial Every Week Wednesday 1000 1100
     * CS2105 1 Lecture Every Week Monday 1400 1600
     * CS2105 16 Tutorial Every Week Friday 1300 1400
     * CS2106 09 Laboratory Every Week Friday 1000 1100
     * CS2106 1 Lecture Every Week Wednesday 1400 1600
     * CS2106 09 Tutorial Every Week Friday 0900 1000
     * MA1521 1 Lecture Every Week Tuesday 1400 1600
     * MA1521 1 Lecture Every Week Friday 1400 1600
     * MA1521 4 Tutorial 3,4,5,6,7,8,9,10,11,12,13 Monday 1500 1600
     * UCV2209 01 Seminar-Style Module Class Every Week Tuesday 1200 1400
     * UCV2209 01 Seminar-Style Module Class Every Week Thursday 1200 1400
     */

    private ArrayList<Lesson> lessonList;

    /**
     * @param lessonList
     */
    public TimeTable(ArrayList<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    /**
     * @return
     */
    public Schedule convertToSchedule() {
        Schedule schedule = new Schedule();

        for (Lesson lesson : lessonList) {
            int start = Integer.parseInt(lesson.getStartTime().substring(0, 2));
            int duration = lesson.getDuration();

            while (duration > 0) {
                String startime1;
                String startime2;
                if (start > 9) {
                    startime1 = start + "00";
                    startime2 = start + "30";
                } else {
                    startime1 = "0" + start + "00";
                    startime2 = "0" + start + "30";
                }
                schedule.setTimeDay(lesson.getDayText(), startime1, true);
                schedule.setTimeDay(lesson.getDayText(), startime2, true);
                start++;
                duration--;
            }
        }

        return schedule;
    }


    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(ArrayList<Lesson> lessonList) {
        this.lessonList = lessonList;
    }
}