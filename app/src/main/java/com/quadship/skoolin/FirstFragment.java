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
public class FirstFragment extends Fragment {


        public static Fragment newInstance(Context context) {
            FirstFragment f = new FirstFragment();

            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            ViewGroup root = (ViewGroup) inflater.inflate(R.layout.firstfrag, null);
            return root;

        }

    }


