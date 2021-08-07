import java.util.EnumMap;
import java.util.EnumSet;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/7/30
 */
public class Calculate {

    public static void main(String[] args) {
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
        EnumMap.
    }
}
