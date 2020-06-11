import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;


public class Main {
    public static void main(String[] args) {
        final LocalTime lc1 = LocalTime.parse("01:20");
        final LocalTime lc2 = LocalTime.parse("00:20");
        System.out.println(MINUTES.between(lc2, lc1));
    }
}
