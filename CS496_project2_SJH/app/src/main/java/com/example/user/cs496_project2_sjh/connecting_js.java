package com.example.user.cs496_project2_sjh;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 2018-01-01.
 */

public class connecting_js  extends AsyncTask<String, String,String>{
    String url1 = "http:13.124.40.52:9200/api";
    String members;
    String input_string;
    String how_to;
    String what;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public connecting_js(JSONArray jsonArray, String members, String input_string, String what, String how_to)
    {
        this.jsonArray = jsonArray;
        this.members = members;
        this.input_string = input_string;
        this.what = what;
        this.how_to = how_to;
    }
    @Override
    protected String doInBackground(String... strings) {
//        try {
            //JSON Object를 만들고 key value형식으로 값을 저장해준다.
            HttpURLConnection con = null;
            BufferedReader reader = null;
            try{
                url1 = url1+members+input_string+what;
                URL url = new URL(url1);
                //연결을 함
                con = (HttpURLConnection) url.openConnection();

                if(how_to.equals("POST")){
                    con.setRequestMethod("POST"); // POST OR GET
                    con.setRequestProperty("Cache-Control", "no-cache"); //캐시설정
                    con.setRequestProperty("Content-Type","application/json"); //application json 형식으로 전송
                    con.setRequestProperty("Accept","application/json"); //서버에 response 데이터를 json로 받음
                    con.setDoOutput(true); //Outstream으로 post데이터를 넘겨주겠다는 의미
                    con.setDoInput(true); //Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
//                outStream.write(jsonObject.toString().getBytes());

                    outStream.write(jsonArray.toString().getBytes());
                    outStream.close();
                    con.getResponseCode();
                }else{
                    con.connect();
                }
                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (con != null) {
                    con.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        Log.d("result", result);
    }
}
