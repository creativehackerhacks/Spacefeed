package com.example.ansh.spacefeed;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class SpaceFeedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        if(LeakCanary.isInAnalyzerProcess(this)) {
//
//            return;
//        }
//        LeakCanary.install(this);

    }


}
