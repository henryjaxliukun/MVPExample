package com.example.mvpexample.presenter;

import java.util.concurrent.CountDownLatch;

import com.example.mvpexample.view.IBaseView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 基础Presenter类，同步回调界面层直接调用IBaseView子类的回调方法，异步通过发消息来实现回调
 * 注1：IBaseView用于解耦，也用于回调方法
 * 注2：子类中不要出现新的线程，都统一用这里提供的getWorkHandle()和重写onHandleWorkMessage(Message msg)方法来实现异步操作
 * 注3：用getViewHandler()和实现onHandleViewMessage(Message msg)来对于界面层进行异步刷新和回调方法，里面send方法都是用于异步
 */
abstract public class BasePresenter {
	private IBaseView mIview = null;
	//工作线程，用于代替Presenter层中的线程操作
	private Thread mWorkThread = null;
	//处理工作线程消息的handler
	private Handler mWorkHandler = null;
	private CountDownLatch oneLatch = null;
	//（异步通知界面刷新）通知接口回调方法的Handler
	private Handler mViewHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IBaseView.MESSAGE_DEFAULT:
				mIview.onDefault(msg);
				break;
			case IBaseView.MESSAGE_ERROR:
				mIview.onError(msg);
				break;
			case IBaseView.MESSAGE_WAIT:
				mIview.onWait(msg);
				break;
			default:
				onHandleViewMessage(msg);
			}
		}
	};

	/**
	 * 构造方法
	 * @param iView 
	 * @param openWorkThread 是否打开工作线程，如果打开就可以供这里处理的工作线程来处理异步操作
	 */
	public BasePresenter(IBaseView iView, boolean openWorkThread) {
		mIview = iView;
		if (openWorkThread) {
			try {
				oneLatch = new CountDownLatch(1);
				mWorkThread = new Thread() {
					@Override
					public void run() {
						Looper.prepare();
						mWorkHandler = new Handler() {
							@Override
							public void handleMessage(Message msg) {
								onHandleWorkMessage(msg);
							}
						};
						oneLatch.countDown();
						Looper.loop();
					}
				};
				mWorkThread.start();
				oneLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 可重写，用于界面初始化时调用
	 */
	public void doCreateView() {
		mIview.onInit();
	}

	/**
	 * 用于重新，用于View层调用它来打开新的Activity，重写应把打开新的Activity的Intent包装好，再回调view层的onStartActivity(Intent intent)方法来把包装好的Intent发过去
	 */
	public void doStartActivity(){
		
	}
	
	/**
	 * 异步发送消息，使接口回调onDefault方法
	 * @param msg
	 */
	void sendOnDefault(Message msg) {
		if (msg != null) {
			msg.what = IBaseView.MESSAGE_DEFAULT;
			mViewHandler.sendMessage(msg);
		} else
			sendOnDefault();
	}

	/**
	 * 异步发送消息，使接口回调onDefault方法
	 */
	void sendOnDefault() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_DEFAULT;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * 异步发送消息，使接口回调onError方法
	 * @param msg
	 */
	void sendOnError(Message msg) {
		if (msg != null) {
			msg.what = IBaseView.MESSAGE_ERROR;
			mViewHandler.sendMessage(msg);
		} else {
			sendOnError();
		}
	}

	/**
	 * 异步发送消息，使接口回调onError方法
	 */
	void sendOnError() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_ERROR;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * 异步发送消息，使接口回调onWaitBegin方法
	 * @param obj
	 */
	void sendOnWaitBegin(Object obj) {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_WAIT;
		msg.arg1 = IBaseView.MESSAGE_WAIT_BEGIN;
		msg.obj = obj;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * 异步发送消息，使接口回调onWaitEnd方法
	 * @param obj
	 */
	void sendOnWaitEnd(Object obj) {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_WAIT;
		msg.arg1 = IBaseView.MESSAGE_WAIT_END;
		msg.obj = obj;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * 异步发送消息，使接口回调onWait方法
	 */
	void sendOnWait() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_WAIT;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * 异步发送消息，使接口回调onWait方法
	 * @param msg
	 */
	void sendOnWait(Message msg) {
		if (msg != null) {
			msg.what = IBaseView.MESSAGE_WAIT;
			mViewHandler.sendMessage(msg);
		} else {
			sendOnWait();
		}
	}

	/**
	 * 获取通知界面刷新的Handler
	 * @return
	 */
	protected Handler getViewHandler() {
		return mViewHandler;
	}

	/**
	 * 此方法用于重写，处理界面刷新的自定义消息（消息不可用1000～1002，因为这几个已经在IBaseView中定义过了）
	 * @param msg
	 */
	protected void onHandleViewMessage(Message msg) {
		
	}

	/**
	 * 此方法用于重写，对于工作线程发出的消息进行处理（构造方法中openWorkThread为true才能用）
	 * @param msg
	 */
	protected void onHandleWorkMessage(Message msg) {
		
	}

	/**
	 * 获取工作线程的Handler
	 * @return
	 */
	protected Handler getWorkHandler() {
		return mWorkHandler;
	}

	/**
	 * 方便封装消息，用于工作线程处理消息
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param obj
	 * @return
	 */
	protected Message obtianWorkMessage(int what, int arg1, int arg2, Object obj) {
		if (mWorkHandler != null)
			return mWorkHandler.obtainMessage(what, arg1, arg2, obj);
		return null;
	}

	/**
	 * 方便工作线程发消息
	 * @param msg
	 * @return
	 */
	boolean sendWorkMessage(Message msg) {
		if (mWorkHandler != null) {
			return mWorkHandler.sendMessage(msg);
		}
		return false;
	}
}
