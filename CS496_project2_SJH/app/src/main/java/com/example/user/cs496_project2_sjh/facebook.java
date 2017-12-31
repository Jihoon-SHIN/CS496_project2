package com.example.user.cs496_project2_sjh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class facebook extends AppCompatActivity {
    CallbackManager callbackManager;
    TextView txtEmail, txtBirthday, txtFriends;
    ProgressDialog mDialog;
    ImageView imgAvatar;


    Button testdb;
    TextView dbdb;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        callbackManager = CallbackManager.Factory.create();

        txtBirthday = (TextView)findViewById(R.id.txtBirthday);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtFriends = (TextView)findViewById(R.id.txtFriends);

        imgAvatar = (ImageView)findViewById(R.id.avatar);

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        dbdb = (TextView)findViewById(R.id.dbdb);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(facebook.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response",response.toString());
                        getData(object);

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        if(AccessToken.getCurrentAccessToken()!=null){
//            txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

        testdb = (Button)findViewById(R.id.testbutton);
        testdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://13.124.40.52:8089/post");
            }
        });
    }
    public class JSONTask extends AsyncTask<String, String,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSON Object를 만들고 key value형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id","andoridTest");
                jsonObject.accumulate("namve","yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://13.124.40.52:20000");
                    URL url = new URL(strings[0]);
                    //연결을 함

                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST"); // POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache"); //캐시설정
                    con.setRequestProperty("Content-Type","application/json"); //application json 형식으로 전송
                    con.setRequestProperty("Accept","text/html"); //서버에 response 데이터를 html로 받음
                    con.setDoOutput(true); //Outstream으로 post데이터를 넘겨주겠다는 의미
                    con.setDoInput(true); //Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서보로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();

                    writer.close(); //버퍼를 받아줌
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dbdb.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
    }

    public void mainact(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getData(JSONObject object){
        try{
            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

            txtBirthday.setText(object.getString("birthday"));
            txtFriends.setText(object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));
            Log.i("Friends",object.getString("friends"));

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.user.cs496_project2_sjh", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

}
