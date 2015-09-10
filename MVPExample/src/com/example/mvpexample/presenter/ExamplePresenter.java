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
 * Presenter��ʵ�ַ������������ݲ����ͼ��
 * 
 * @author Jax
 *
 */
public class ExamplePresenter extends ActivityPresenter {

	IExampleView mView;
	IExampleModel mModel;

	// ��������������̶߳����õ���Ϣ
	public static final int MESSAGE_REQUEST_ASYN_TESTDATA = 0;

	public ExamplePresenter(IExampleView iView, boolean openWorkThread, Intent intent) {
		super(iView, openWorkThread, intent);
		// TODO Auto-generated constructor stub
		mView = iView;
		mModel = new ExampleModelImpl();

	}

	/**
	 * ��д����������н����������Ҫ���еĲ���
	 */
	@Override
	public void doCreateView() {
		// TODO Auto-generated method stub
		super.doCreateView();
		requestSynData();
	}

	/**
	 * ����activity�е����󣨿��Դ�������
	 */
	public void doRequestAsynData() {
		requestAsynData();
	}

	/**
	 * ����ͬ�����ݷ���
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
	 * �����첽���ݷ���
	 */
	private void requestAsynData() {
		// TODO Auto-generated method stub
		getWorkHandler().sendMessage(obtianWorkMessage(MESSAGE_REQUEST_ASYN_TESTDATA, 0, 0, null));
		// getWorkHandler().sendEmptyMessage(MESSAGE_REQUEST_ASYN_TESTDATA);
	}

	@Override
	protected void onHandleWorkMessage(Message msgWork) {
		// TODO Auto-generated method stub
		// ���ڴ�������еĹ����̶߳����õ���Ϣ������
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
				// ����д�����֪ͨ����ص�onError
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
	 * �����ݲ㴫������ʵ�����װ����ͼ��ʬ���ࣨʵ�����ݲ����ͼ��Ľ��
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
	 * ���µ�Activity
	 */
	public void doStartActivity() {
		Intent intent = new Intent();
		// �����Ӧ�÷�װ��intent��װ����
		mView.onStartActivity(intent);
	}
}
