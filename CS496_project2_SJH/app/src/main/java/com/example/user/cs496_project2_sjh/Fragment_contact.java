package com.example.user.cs496_project2_sjh;

import android.content.pm.PackageInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_contact extends Fragment {
    public Fragment_contact()
    {
        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myfragmentView = inflater.inflate(R.layout.fragment_contact,container,false);
        return myfragmentView;
    }


}