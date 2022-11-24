package utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Logged {

    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

}
