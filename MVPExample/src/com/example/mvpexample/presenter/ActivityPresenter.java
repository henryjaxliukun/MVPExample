package com.example.mvpexample.presenter;

import com.example.mvpexample.view.IBaseView;

import android.content.Intent;
/**
 * 用于Activity来继承的Presenter，
 */
public class ActivityPresenter extends BasePresenter {

	/**
	 * 构造方法
	 * @param iView
	 * @param openWorkThread
	 * @param intent 传入Activity的getIntent，这样把Intent放入Presenter中处理业务
	 */
	public ActivityPresenter(IBaseView iView, boolean openWorkThread, Intent intent) {
		super(iView, openWorkThread);
	}
}
