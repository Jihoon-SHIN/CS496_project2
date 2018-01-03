package com.example.user.cs496_project2_sjh;
/**
 * Created by user on 2017-12-26.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<listviewitem> listViewItemList = new ArrayList<listviewitem>();
    private LayoutInflater inflater; //
    private List<listviewitem> list;
    private Context context;
    private ValueHolder viewHolder;
    //ListviewAdapter의 생성자
    public ListViewAdapter(List<listviewitem> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_contact, parent, false);
        }
        ImageView iconImageView = (ImageView)convertView.findViewById(R.id.imageview1);
        TextView textView1 = (TextView)convertView.findViewById(R.id.textview1);
        TextView textView2 = (TextView)convertView.findViewById(R.id.textview2);

        iconImageView.setImageResource(listViewItemList.get(position).getIcon());
        textView1.setText(listViewItemList.get(position).getTitle());
        textView2.setText(listViewItemList.get(position).getDesc());
*/
        if(convertView==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.listview_item, parent, false);
            convertView = inflater.inflate(R.layout.listview_item,null);
            viewHolder = new ValueHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageview1);
            viewHolder.title = (TextView) convertView.findViewById(R.id.textview1);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.textview2);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ValueHolder)convertView.getTag();
        }
        listviewitem listViewItem = list.get(position);
        viewHolder.icon.setImageBitmap(listViewItem.getIcon());
        viewHolder.title.setText(listViewItem.getTitle());
        viewHolder.desc.setText(listViewItem.getDesc());
        return convertView;
    }
    public void addItem(Bitmap icon, String title, String desc){
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