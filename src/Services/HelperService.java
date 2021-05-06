package Services;

import Classes.Section;
import Classes.SectionType;

import java.util.Calendar;
import java.util.Date;

public class HelperService<T> {
    /**
     * Creating a date with the given year, month and day
     */
    public static Date makeDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * method to create a section with its type given as string
     */
    public static Section createSectionWithSectionType(String type){
        Section section = new Section();
        switch (type.toUpperCase()) {
            case "FICTIONAL" -> {
                section = new Section(SectionType.FICTIONAL);
            }
            case "REFERENCE" -> {
                section = new Section(SectionType.REFERENCE);
            }
            case "NEWS" -> {
                section = new Section(SectionType.NEWS);
            }
            case "CHILDREN" -> {
                section = new Section(SectionType.CHILDREN);
            }
            case "NONFICTION" -> {
                section = new Section(SectionType.NONFICTION);
            }
            case "POEMS" -> {
                section = new Section(SectionType.POEMS);
            }
            default -> {
                System.out.println("Section Type is wrong!");
                System.exit(0);
            }
        }
        return section;
    }
}
