package com.example.user.cs496_project2_sjh;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newmember extends AppCompatActivity {
    private EditText name;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);
        //커스텀 다이얼로그의 각 위젯들을 정의한다.
        name = (EditText) findViewById(R.id.message);
        phone = (EditText) findViewById(R.id.phonenumber);
        final Button okButton = (Button) findViewById(R.id.okButton);
        final Button canceButton = (Button) findViewById(R.id.cancelButton);
    }
    public void okclick(View view){
        Intent intent = new Intent(this, facebook.class);
        /*intent.putExtra("name",name.getText().toString());
        intent.putExtra("phone",phone.getText().toString());*/

        EditText login_text = (EditText)findViewById(R.id.member_id);
        EditText password_text = (EditText)findViewById(R.id.password);
        EditText password_text2 = (EditText)findViewById(R.id.check_password);
        EditText name = (EditText)findViewById(R.id.name_db);
        EditText phone = (EditText)findViewById(R.id.phonenumber);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("member1",login_text.getText().toString());
            jsonObject.accumulate("password",password_text.getText().toString());
            jsonObject.accumulate("name",name.getText().toString());
            jsonObject.accumulate("phone",phone.getText().toString());
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            new connecting_js(jsonArray,"/members","","","POST").execute("http:13.124.40.52:9200/api/members/member");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity(intent);
        Toast toast = Toast.makeText(this, "추가하였습니다.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
    public void canclick(View view){
        Intent intent = new Intent(this, facebook.class);
        startActivity(intent);
        Toast toast = Toast.makeText(this, "취소하였습니다.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
}
