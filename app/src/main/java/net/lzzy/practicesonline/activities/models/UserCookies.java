package net.lzzy.practicesonline.activities.models;

import android.content.SharedPreferences;

import java.util.List;

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
    private  static final UserCookies OUR_INSTANCE=new UserCookies();
    private SharedPreferences spCommit;
    private SharedPreferences spTime;
    private SharedPreferences spChecked;
    private  SharedPreferences spPosition;
    private  SharedPreferences spReadCount;
    private  UserCookies(){

    }
    public UserCookies getOurInstance(){
        return  null;
    }
    public   boolean isPracticeCommitted(String practice){
        return  false;
    }
    public void commitPractice(String practice){

    }
    public String getLastRefresgTime(){
        return null;
    }
    public  void  changeOptionState(Option option,boolean b,boolean c){

    }
    private  String trunkSplittected(String s){
        return  null;

    }
    public boolean isOptionSelected(Option option){
        return  false;
    }
    public List<QuestionResult> getResultFromCookies(List<Question> questions){
        return  null;
    }

    private class QuestionResult {
    }

}
