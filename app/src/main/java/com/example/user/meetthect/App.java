package com.example.user.meetthect;

import android.app.Application;

/**
 * Created by User on 3/14/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();
    }

    private void initSingletons() {
        Injector.getInstance()
                .init(this);
    }
}
