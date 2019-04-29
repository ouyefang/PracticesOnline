package net.lzzy.practicesonline.activities.models;

import android.content.Context;
import android.content.SharedPreferences;

import net.lzzy.practicesonline.activities.utils.AppUtils;
import net.lzzy.practicesonline.activities.utils.DateTimeUtils;

import java.util.Date;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/17
 * Description:
 */
public class UserCookies {
    private  static final String KEY_TIME = "time";
    private  static final String ID_SPLITTER=",";
    private  static final int FLAG_COMMIT_Y=1;
    private  static final int FLAG_COMMIT_N=0;
    private  static final UserCookies INSTANCE =new UserCookies();
    private SharedPreferences spCommit;
    private SharedPreferences spTime;
    private SharedPreferences spChecked;
    private  SharedPreferences spPosition;
    private  SharedPreferences spReadCount;
    private  UserCookies(){
        spTime= AppUtils.getContext()
                .getSharedPreferences("refresh_time", Context.MODE_PRIVATE);

    }
    public static UserCookies getInstance(){
        return  INSTANCE;
    }
    public  void  updateLastRefreshTime(){
        String time= DateTimeUtils.DATE_TIME_FORMAT.format(new Date());
        spTime.edit().putString(KEY_TIME,time).apply();

    }

    public String getLastRefreshTime(){
        return null;
    }


}
