package com.example.mvpexample.presenter;

import java.util.concurrent.CountDownLatch;

import com.example.mvpexample.view.IBaseView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * ����Presenter�࣬ͬ���ص������ֱ�ӵ���IBaseView����Ļص��������첽ͨ������Ϣ��ʵ�ֻص�
 * ע1��IBaseView���ڽ��Ҳ���ڻص�����
 * ע2�������в�Ҫ�����µ��̣߳���ͳһ�������ṩ��getWorkHandle()����дonHandleWorkMessage(Message msg)������ʵ���첽����
 * ע3����getViewHandler()��ʵ��onHandleViewMessage(Message msg)�����ڽ��������첽ˢ�ºͻص�����������send�������������첽
 */
abstract public class BasePresenter {
	private IBaseView mIview = null;
	//�����̣߳����ڴ���Presenter���е��̲߳���
	private Thread mWorkThread = null;
	//�������߳���Ϣ��handler
	private Handler mWorkHandler = null;
	private CountDownLatch oneLatch = null;
	//���첽֪ͨ����ˢ�£�֪ͨ�ӿڻص�������Handler
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
	 * ���췽��
	 * @param iView 
	 * @param openWorkThread �Ƿ�򿪹����̣߳�����򿪾Ϳ��Թ����ﴦ��Ĺ����߳��������첽����
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
	 * ����д�����ڽ����ʼ��ʱ����
	 */
	public void doCreateView() {
		mIview.onInit();
	}

	/**
	 * �������£�����View������������µ�Activity����дӦ�Ѵ��µ�Activity��Intent��װ�ã��ٻص�view���onStartActivity(Intent intent)�������Ѱ�װ�õ�Intent����ȥ
	 */
	public void doStartActivity(){
		
	}
	
	/**
	 * �첽������Ϣ��ʹ�ӿڻص�onDefault����
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
	 * �첽������Ϣ��ʹ�ӿڻص�onDefault����
	 */
	void sendOnDefault() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_DEFAULT;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * �첽������Ϣ��ʹ�ӿڻص�onError����
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
	 * �첽������Ϣ��ʹ�ӿڻص�onError����
	 */
	void sendOnError() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_ERROR;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * �첽������Ϣ��ʹ�ӿڻص�onWaitBegin����
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
	 * �첽������Ϣ��ʹ�ӿڻص�onWaitEnd����
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
	 * �첽������Ϣ��ʹ�ӿڻص�onWait����
	 */
	void sendOnWait() {
		Message msg = Message.obtain();
		msg.what = IBaseView.MESSAGE_WAIT;
		mViewHandler.sendMessage(msg);
	}

	/**
	 * �첽������Ϣ��ʹ�ӿڻص�onWait����
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
	 * ��ȡ֪ͨ����ˢ�µ�Handler
	 * @return
	 */
	protected Handler getViewHandler() {
		return mViewHandler;
	}

	/**
	 * �˷���������д���������ˢ�µ��Զ�����Ϣ����Ϣ������1000��1002����Ϊ�⼸���Ѿ���IBaseView�ж�����ˣ�
	 * @param msg
	 */
	protected void onHandleViewMessage(Message msg) {
		
	}

	/**
	 * �˷���������д�����ڹ����̷߳�������Ϣ���д������췽����openWorkThreadΪtrue�����ã�
	 * @param msg
	 */
	protected void onHandleWorkMessage(Message msg) {
		
	}

	/**
	 * ��ȡ�����̵߳�Handler
	 * @return
	 */
	protected Handler getWorkHandler() {
		return mWorkHandler;
	}

	/**
	 * �����װ��Ϣ�����ڹ����̴߳�����Ϣ
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
	 * ���㹤���̷߳���Ϣ
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
