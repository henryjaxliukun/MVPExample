package com.example.mvpexample.view;

import android.content.Intent;
import android.os.Message;

/**
 * ���ڽ������ͼ��ӿڸ��࣬������д��ͼ���Զ���ص������������첽�ص�����Ϣwhatֵ
 *
 */
public interface IBaseView {

	static final int MESSAGE_ERROR = 1000;

	/**
	 * ����ʱ�ص�
	 * @param msg
	 */
	void onError(Message msg);

	static final int MESSAGE_DEFAULT = 1001;

	/**
	 * Ĭ�ϻ��޷�������ʱ�ص�
	 * @param msg
	 */
	void onDefault(Message msg);

	static final int MESSAGE_WAIT = 1002;
	static final int MESSAGE_WAIT_BEGIN = 1;
	static final int MESSAGE_WAIT_END = 2;

	/**
	 * �ȴ�����ʱ�ص�
	 * @param msg
	 */
	void onWait(Message msg);

	/**
	 * ��ʼ��ʱ�ص�
	 */
	void onInit();

	/**
	 * ����Activityʱ�ص���IntentӦ����Presenter���װ�ã�����View����д���Activity�Ĳ�����
	 * @param intent
	 */
	void onStartActivity(Intent intent);
}
