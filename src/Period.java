import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public final class Period {
    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
        if (start.after(end)) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    private Object writeReplace() {
        return new PeriodSerializationProxy(this);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}
