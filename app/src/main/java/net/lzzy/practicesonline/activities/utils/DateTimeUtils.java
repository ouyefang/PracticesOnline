package net.lzzy.practicesonline.activities.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/24
 * Description:
 */
public class DateTimeUtils {
    public  static final SimpleDateFormat DATE_TIME_FORMAT
            =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public  static final SimpleDateFormat DATE_FORMAT
            =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

}
