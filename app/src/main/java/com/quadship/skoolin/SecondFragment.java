package com.quadship.skoolin;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 20/4/16.
 */
public class SecondFragment extends Fragment {


    public static Fragment newInstance(Context context) {
        System.out.println("Seconffff");
        SecondFragment f2 = new SecondFragment();
        System.out.println("Seconffff111111");
        return f2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        System.out.println("Seconffff2222");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.secondfrag, null);
        System.out.println("Seconffff333333");
        return root;
    }

}

