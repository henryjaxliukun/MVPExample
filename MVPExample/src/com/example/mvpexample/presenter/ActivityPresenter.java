package com.example.mvpexample.presenter;

import com.example.mvpexample.view.IBaseView;

import android.content.Intent;
/**
 * ����Activity���̳е�Presenter��
 */
public class ActivityPresenter extends BasePresenter {

	/**
	 * ���췽��
	 * @param iView
	 * @param openWorkThread
	 * @param intent ����Activity��getIntent��������Intent����Presenter�д���ҵ��
	 */
	public ActivityPresenter(IBaseView iView, boolean openWorkThread, Intent intent) {
		super(iView, openWorkThread);
	}
}
