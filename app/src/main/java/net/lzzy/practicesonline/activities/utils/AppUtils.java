package net.lzzy.practicesonline.activities.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Pair;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author lzzy_gxy
 * @date 2019/3/11
 * Description:
 */
public class AppUtils extends Application {

    private static final String URL_PORT = "urlPort";
    private static final String SP_SETTING = "spSettING";
    public  static final String URL_IP = "urlIp";
    private static List<Activity> activities=new LinkedList<>();
    private static WeakReference<Context> wContext;

    private static  String runningActivity;



    @Override
    public void onCreate() {
        super.onCreate();
        wContext = new WeakReference<>(this);
    }


    public static Context getContext(){
        return wContext.get();
    }
    public  static  void  addActivity(Activity activity){
        activities.add(activity);
}
    public  static  void  removeActivity(Activity activity){
        activities.remove(activity);
}
    public  static void  exit(){
        for (Activity activity:activities){
            if (activity!=null){
                activity.finish();
            }

        }
        System.exit(0);
}
    public static  Activity getRunningActivity(){
        for (Activity activity:activities){
            String name=activity.getLocalClassName();
            if (AppUtils.runningActivity.equals(name)){
                return  activity;
            }
        }
        return  null;
}

    public static void setRunning(String runningActivity) {
        AppUtils.runningActivity = runningActivity;
    }
    public  static  void  setStopped(String stoppedActivity){
        if (stoppedActivity.equals(AppUtils.runningActivity)){
            AppUtils.runningActivity="";
        }
    }
    public  static boolean isNetworkAvailable(){
        ConnectivityManager manager=(ConnectivityManager)getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();

        return info.isConnected();
    }
    private  static final int CUP_COUNT=Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE=Math.max(2,Math.min(CUP_COUNT-1,4));
    private static  final int MAX_POOL_SIZE=CUP_COUNT *2+1;
    private static final  int KEEP_ALIVE_SECONDS=30;
    private static final ThreadFactory THREAD_FACTORY=new ThreadFactory() {
        private  final AtomicInteger count=new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {

            return new Thread(r,"thread #"+count.getAndIncrement());

        }
    };
    private  static  final BlockingQueue<Runnable> POOL_QUEUE=new LinkedBlockingDeque<>(128);

    public static ThreadPoolExecutor getExecutor() {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,POOL_QUEUE,THREAD_FACTORY);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }


    public  static  void tryConnectServer(String address) throws IOException {
        URL url=new URL(address);
       HttpURLConnection connection= (HttpURLConnection) url.openConnection();
       connection.setConnectTimeout(5000);
       connection.getContent();
    }
    public  static  void  saveServeSetting(String ip,String port,Context context){

        SharedPreferences spSetting=context.getSharedPreferences(SP_SETTING,MODE_PRIVATE);

        spSetting.edit()
        .putString(URL_IP,ip)
                .putString(URL_PORT,port)
                .apply();
    }
    public  static Pair<String,String> loadServerSetting(Context context) {

        SharedPreferences spSetting =context.getSharedPreferences(SP_SETTING,MODE_PRIVATE);
        String ip=spSetting.getString(URL_IP,"10.88.91.102");
        String port=spSetting.getString(URL_PORT,"8888");
        return  new Pair<>(ip,port);

        }



        /**
         * 获取各类网路的mac地址
         *
         *
         *
         * @return 包括移动数据网络的mac 地址
         * **/

        public static List<String> getMacAddress(){
            try {
                Enumeration<NetworkInterface> interfaces=NetworkInterface.getNetworkInterfaces();
                List<String> items=new ArrayList<>();
                while (interfaces.hasMoreElements()){
                    NetworkInterface ni=interfaces.nextElement();
                    byte[] address=ni.getHardwareAddress();
                    if (address==null||address.length==0){
                        continue;
                    }
                    StringBuffer buffer=new StringBuffer();
                    for (byte a:address){
                        buffer.append(String.format("%02X:",a));
                    }
                    if (buffer.length()>0){
                        buffer.deleteCharAt(buffer.length()-1);
                    }
                    if (ni.isUp()){
                        items.add(ni.getName()+":"+buffer.toString());
                    }
                }
                return items;

            } catch (SocketException e) {
                return new ArrayList<>();

            }

        }


}
