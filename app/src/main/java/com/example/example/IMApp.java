package com.example.example;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;

import java.util.Vector;

public class IMApp extends Application {
    private Vector<String> serverList = new Vector<>();
    private int mActivityCount = 0;
    private User user;
    private static IMApp instance;

    public static IMApp sharedInstance() {
        if (instance == null) {
            throw new IllegalStateException("应用未初始化！");
        }
        return instance;
    }

    public boolean isBackground() {
        return (mActivityCount == 0);
    }

    public Vector<String> getServerList() {
        return serverList;
    }

    public String getUserId() {return user.getId();}

    public User getUser() {return user;}

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serverList.add("10.0.2.2 8866");
        instance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                mActivityCount++;
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                mActivityCount--;
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

}
