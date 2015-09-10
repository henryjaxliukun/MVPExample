package com.example.mvpexample.view;

import android.content.Intent;
import android.os.Message;

/**
 * 用于解耦的视图层接口父类，子类中写视图层自定义回调方法和用于异步回调的消息what值
 *
 */
public interface IBaseView {

	static final int MESSAGE_ERROR = 1000;

	/**
	 * 错误时回调
	 * @param msg
	 */
	void onError(Message msg);

	static final int MESSAGE_DEFAULT = 1001;

	/**
	 * 默认或无返回数据时回调
	 * @param msg
	 */
	void onDefault(Message msg);

	static final int MESSAGE_WAIT = 1002;
	static final int MESSAGE_WAIT_BEGIN = 1;
	static final int MESSAGE_WAIT_END = 2;

	/**
	 * 等待数据时回调
	 * @param msg
	 */
	void onWait(Message msg);

	/**
	 * 初始化时回调
	 */
	void onInit();

	/**
	 * 打开新Activity时回调（Intent应当在Presenter层包装好，发到View层进行打开新Activity的操作）
	 * @param intent
	 */
	void onStartActivity(Intent intent);
}
