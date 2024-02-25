package coffee.ssafy.ssafee.common;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtils {

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static final ZoneOffset ZONE_OFFSET = ZonedDateTime.now(ZONE_ID).getOffset();
    public static final String LOCAL_TIME_HH_MM_PATTERN = "HH:mm";
    public static final String LOCAL_TIME_HH_MM_EXAMPLE = "13:00";

}
