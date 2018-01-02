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

        /*testdb = (Button)findViewById(R.id.testbutton);
        Button testdb1 = (Button)findViewById(R.id.newmember);

        testdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new connecting_js(dbdb).execute("http:13.124.40.52:9200/api/members/member");
            }
        });*/

    }
    public void Add_NewMember(View view){
        Intent intent = new Intent(this, newmember.class);
        startActivity(intent);
    }
    public void mainact(View view){
        Intent intent = new Intent(this, MainActivity.class);
//        new connecting_js("/members","","","POST").execute("http:13.124.40.52:9200/api/members/member");
        startActivity(intent);
    }

    private void getData(JSONObject object){
        try{
            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
//            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

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
