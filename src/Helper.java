import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Objects;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/7/30
 */
public class Helper {

    public static void main(String[] args) {
        // Objects.requireNonNull(args, "args can not be null");

        localDate();
    }

    private static void calculate() {
        double aScore = 7.1, bScore = 14.2, cScore = 3.55;
        int aNum = 25, bNum = 15, cNum = 10;

        double totalScore = aScore * aNum + bScore * bNum + cScore * cNum;
        double targetScore1 = 307, targetScore2 = 413.5, diff = 0.000001;

        for (int i = 0; i <= aNum; i++) {
            for (int j = 0; j <= bNum; j++) {
                for (int k = 0; k <= cNum; k++) {
                    double score = i * aScore + j * bScore + k * cScore;
                    if (score <= 300) {
                        continue;
                    }
                    // System.out.println(score);
                    if (Math.abs(score - targetScore1) <= diff ||
                            Math.abs(score - targetScore2) <= diff) {
                        System.out.println("aNum = " + i + ", " +
                                "bNum = " + j + ", " +
                                "cNum = " + k + ", " +
                                "score = " + score);
                        return;
                    }
                }
            }
        }
        System.out.println("Not Found.");
    }

    /**
     * https://zhuanlan.zhihu.com/p/146000139
     */
    private static void localDate() {
        LocalDate today = LocalDate.now();
        System.out.println("Today's local date: " + today);

        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year：%d Month：%d day: %d %n", year, month, day);

        LocalDate dateOfBirth = LocalDate.of(2018, 1, 12);
        System.out.println("The special date is: " + dateOfBirth);

        System.out.println(today.equals(dateOfBirth));

        MonthDay birthday = MonthDay.of(dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);
        if (currentMonthDay.equals(birthday)) {
            System.out.println("Many happy returns of the day !!");
        } else {
            System.out.println("Sorry, today is not your birthday");
        }

        // TODO more
    }
}
