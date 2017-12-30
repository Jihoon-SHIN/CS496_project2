package com.example.user.cs496_project2_sjh;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_free extends Fragment {
    public Fragment_free()
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
        View myfragmentView = inflater.inflate(R.layout.fragment_free,container,false);
        return myfragmentView;
    }

}