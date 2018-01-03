package com.example.user.cs496_project2_sjh;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018-01-02.
 */

public class add_mycontact_db {
    ArrayList<Map<String, String>> dataList;
    Context context;
    public add_mycontact_db(Context context){
        this.context = context;
    }
    public ArrayList<Map<String, String>> function(){
        dataList = new ArrayList<Map<String, String>>();
        Cursor c = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");
        while(c.moveToNext()){
            HashMap<String, String> map = new HashMap<String, String>();
            //연락처 id값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //연락처 대표 이름
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            map.put("name", name);
            // ID로 전화 정보 조회

            Cursor phoneCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);
            if (phoneCursor.moveToFirst()) {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                map.put("phone", number);
            }
            phoneCursor.close();
            dataList.add(map);
        }//end while
        c.close();
        return dataList;
    }

}
