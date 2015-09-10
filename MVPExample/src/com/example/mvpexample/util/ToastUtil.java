package com.example.mvpexample.util;

import android.widget.Toast;

import com.example.mvpexample.app.MyApplication;

public class ToastUtil {
	public static void showShortToast(String text) {
		Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
	}
}
