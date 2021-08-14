import java.io.Serializable;
import java.util.Date;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class PeriodSerializationProxy implements Serializable {

    private static final long serialVersionUID = -5710092583871857758L;

    private final Date start;

    private final Date end;

    public PeriodSerializationProxy(Period p) {
        start = p.start();
        end = p.end();
    }

    private Object readResolve() {
        return new Period(start, end);
    }
}
