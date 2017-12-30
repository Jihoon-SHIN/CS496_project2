package com.example.user.cs496_project2_sjh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bt1, bt2, bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        
        bt1 = (Button)findViewById(R.id.bt_tab1);
        bt2 = (Button)findViewById(R.id.bt_tab2);
        bt3 = (Button)findViewById(R.id.bt_tab3);
    }



    public void onClick(View view){

    }
}
