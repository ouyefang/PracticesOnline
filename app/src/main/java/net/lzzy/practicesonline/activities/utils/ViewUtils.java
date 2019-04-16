package net.lzzy.practicesonline.activities.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.activities.SplashActivity;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/15
 * Description:
 */
public class ViewUtils {

    public  static  void  gotoSetting(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_setting,null);
        Pair<String,String> url=AppUtils.loadServerSetting(context);
        EditText edtIP= view.findViewById(R.id.dialog_setting_edt_ip);
        edtIP.setText(url.first);
        EditText editPort=view.findViewById(R.id.dialog_setting_edt_port);
        editPort.setText(url.second);
        new  AlertDialog.Builder(context)
                .setView(view)
                .setNeutralButton("取消",(dialog, which) -> gotoMain(context))
                .setPositiveButton("保存",(dialog, which) -> {

        String ip= edtIP.getText().toString();
        String port= editPort.getText().toString();
        if (TextUtils.isEmpty(ip)||TextUtils.isEmpty(port)){
            Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtils.saveServeSetting(ip,port,context);
            gotoMain(context);

                })
                .show();

    }


    private  static  void  gotoMain(Context context){
        if (context instanceof SplashActivity){
            ((SplashActivity)context).gotoMain();

        }
    }
}