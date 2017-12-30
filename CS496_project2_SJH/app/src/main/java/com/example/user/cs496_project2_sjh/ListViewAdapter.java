package com.example.user.cs496_project2_sjh;
/**
 * Created by user on 2017-12-26.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<listviewitem> listViewItemList = new ArrayList<listviewitem>();
    private LayoutInflater inflate; //
//    private ValueHolder viewHolder;
    private Context context;
    //ListviewAdapter의 생성자
    public ListViewAdapter(){
        //this.listViewItemList = listViewItemList; //
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_contact, parent, false);
        }
        ImageView iconImageView = (ImageView)convertView.findViewById(R.id.imageview1);
        TextView textView1 = (TextView)convertView.findViewById(R.id.textview1);
        TextView textView2 = (TextView)convertView.findViewById(R.id.textview2);

        iconImageView.setImageDrawable(listViewItemList.get(position).getIcon());
        textView1.setText(listViewItemList.get(position).getTitle());
        textView2.setText(listViewItemList.get(position).getDesc());
        return convertView;
    }
    public void addItem(Drawable icon, String title, String desc){
        listviewitem item = new listviewitem();
        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        listViewItemList.add(item);
    }
    static class ValueHolder{
        ImageView icon;
        TextView title;
        TextView desc;
    }
}