package net.lzzy.practicesonline.activities.network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/19
 * Description:
 */
public class ApiService {

    public String get(String address) throws IOException {
        URL url=new URL(address);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        try {
            conn.setRequestMethod("CET");
            conn.setConnectTimeout(6*100);
            conn.setReadTimeout(6*1000);
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer buffer=new StringBuffer();
            String line;
            while ((line=reader.readLine())!=null){
                buffer.append(line).append("\n");

            }
            reader.close();
            return buffer.toString();
        }finally {
            conn.disconnect();

        }
        //todo:1.HttpURLConnection get
    }
    public  void  post(String address, JSONObject json){
        //todo:1.HttpURLConnection post
    }

}
