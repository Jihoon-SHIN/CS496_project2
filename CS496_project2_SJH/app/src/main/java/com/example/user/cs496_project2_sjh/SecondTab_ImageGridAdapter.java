package com.example.user.cs496_project2_sjh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;


public class SecondTab_ImageGridAdapter extends BaseAdapter {

    Context context = null;
    Context view = null;
    Activity activity = null;

    //-----------------------------------------------------------
    // imageIDs는 이미지 파일들의 리소스 ID들을 담는 배열입니다.
    // 이 배열의 원소들은 자식 뷰들인 ImageView 뷰들이 무엇을 보여주는지를
    // 결정하는데 활용될 것입니다.

    int[] imageIDs = null;

    public SecondTab_ImageGridAdapter(Context context, int[] imageIDs, Activity activity) {
        this.context = context;
        this.imageIDs = imageIDs;
        this.activity = activity;
    }
    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }
    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if (null != convertView)
            imageView = (ImageView)convertView;
        else {
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의합니다.
            // 그리고 그것의 크기를 320*240으로 줄입니다.
            // 크기를 줄이는 이유는 메모리 부족 문제를 막을 수 있기 때문입니다.

            Bitmap bmp
                    = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);

            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰들을 정의합니다.
            // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체입니다.

            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);

            imageView.setImageBitmap(bmp);

            //---------------------------------------------------------------
            // 지금은 사용하지 않는 코드입니다.

            //imageView.setMaxWidth(320);
            //imageView.setMaxHeight(240);
            //imageView.setImageResource(imageIDs[position]);

            //---------------------------------------------------------------
            // 사진 항목들의 클릭을 처리하는 SecondTab_ImageClickListener 객체를 정의합니다.
            // 그리고 그것을 ImageView의 클릭 리스너로 설정합니다.

            SecondTab_ImageClickListener imageViewClickListener = new SecondTab_ImageClickListener(context,imageIDs[position]);
            imageView.setOnClickListener(imageViewClickListener);
        }

        return imageView;
    }

    public class SecondTab_ImageClickListener implements View.OnClickListener {


        Context context;
        View view;

        //-----------------------------------------------------------
        // imageID는 확대해서 보여줄 이미지의 리소스 ID입니다.

        int imageID;

        public SecondTab_ImageClickListener(Context context, int imageID) {
            this.context = context;
            this.imageID = imageID;
            this.view = view;
        }

        public void onClick(View v) {
            //---------------------------------------------------------
            // 확대된 이미지를 보여주는 액티비티를 실행하기 위해 인텐트 객체를 정의합니다.
            // 그리고 이 액티비티에 전달할 imageID의 값을 이 객체에 저장합니다.
            // 인텐트 객체를 정의 후 이 액티비티를 실행합니다.
//새로운 화면 전환
//            Intent intent = new Intent(context, SecondTab_ImageActivity.class);
//            intent.putExtra("image ID", imageID);
//            context.startActivity(intent);

// 큰 화면 전환
//            ImageView label;
//            label = (ImageView)activity.findViewById(R.id.imageView2);
//            label.setImageResource(imageID);



        }
    }


}