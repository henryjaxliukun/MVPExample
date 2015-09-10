package com.example.mvpexample.app;

import android.app.Application;

public class MyApplication extends Application {
	static private MyApplication app;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		app = this;
	}

	public static MyApplication getInstance() {
		return app;
	}
}
