package seedu.address.model.event;

public class Time {
    private int hour;
    private int minute;
    
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
    
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
