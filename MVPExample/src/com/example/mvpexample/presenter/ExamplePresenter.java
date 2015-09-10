package com.example.mvpexample.presenter;

import java.util.ArrayList;
import java.util.List;

import com.example.mvpexample.model.ExampleModelImpl;
import com.example.mvpexample.model.IExampleModel;
import com.example.mvpexample.model.bean.ExampleBean;
import com.example.mvpexample.view.IExampleView;

import android.content.Intent;
import android.os.Message;

/**
 * Presenter层实现范例，解耦数据层和视图层
 * 
 * @author Jax
 *
 */
public class ExamplePresenter extends ActivityPresenter {

	IExampleView mView;
	IExampleModel mModel;

	// 用于替代本类中线程而设置的消息
	public static final int MESSAGE_REQUEST_ASYN_TESTDATA = 0;

	public ExamplePresenter(IExampleView iView, boolean openWorkThread, Intent intent) {
		super(iView, openWorkThread, intent);
		// TODO Auto-generated constructor stub
		mView = iView;
		mModel = new ExampleModelImpl();

	}

	/**
	 * 重写来在这里进行界面刚启动就要进行的操作
	 */
	@Override
	public void doCreateView() {
		// TODO Auto-generated method stub
		super.doCreateView();
		requestSynData();
	}

	/**
	 * 处理activity中的请求（可以带参数）
	 */
	public void doRequestAsynData() {
		requestAsynData();
	}

	/**
	 * 请求同步数据范例
	 */
	private void requestSynData() {
		// TODO Auto-generated method stub
		try {
			List<ExampleBean> list = mModel.getSynTestData();
			if (list.size() == 0) {
				mView.onDefault(null);
			} else {
				List<String> result = getStringListByExampleBeanList(list);
				mView.onSetSynTestData(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			mView.onError(null);
		}

	}

	/**
	 * 请求异步数据范例
	 */
	private void requestAsynData() {
		// TODO Auto-generated method stub
		getWorkHandler().sendMessage(obtianWorkMessage(MESSAGE_REQUEST_ASYN_TESTDATA, 0, 0, null));
		// getWorkHandler().sendEmptyMessage(MESSAGE_REQUEST_ASYN_TESTDATA);
	}

	@Override
	protected void onHandleWorkMessage(Message msgWork) {
		// TODO Auto-generated method stub
		// 用于代替此类中的工作线程而设置的消息处理器
		switch (msgWork.what) {
		case MESSAGE_REQUEST_ASYN_TESTDATA:
			sendOnWaitBegin(null);
			try {
				List<ExampleBean> list = mModel.getAsynTestData();
				if (list.size() == 0) {
					sendOnDefault();
					sendOnWaitEnd(null);
				} else {
					Message msg = Message.obtain();
					msg.what = IExampleView.MESSAGE_ASYN_TESTDATA;
					List<String> result = getStringListByExampleBeanList(list);
					msg.obj = result;
					getViewHandler().sendMessage(msg);
					sendOnWaitEnd(null);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// 如果有错误，则通知界面回调onError
				sendOnError();
				sendOnWaitEnd(null);
			}
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onHandleViewMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case IExampleView.MESSAGE_ASYN_TESTDATA:
			List<String> list = (List<String>) msg.obj;
			mView.onSetAsynTestData(list);
			break;
		case IExampleView.MESSAGE_WAIT_BEGIN:
			mView.onWait(msg);
		default:
			break;
		}
	}

	/**
	 * 把数据层传过来的实体类封装成视图层尸体类（实现数据层和视图层的解耦）
	 * 
	 * @param list
	 * @return
	 */
	private List<String> getStringListByExampleBeanList(List<ExampleBean> list) {
		List<String> result = new ArrayList<String>();
		for (ExampleBean b : list) {
			result.add(b.getContent());
		}
		return result;
	}

	/**
	 * 打开新的Activity
	 */
	public void doStartActivity() {
		Intent intent = new Intent();
		// 这里把应该封装的intent封装进来
		mView.onStartActivity(intent);
	}
}
