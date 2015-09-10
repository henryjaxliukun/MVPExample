package com.example.mvpexample.view;

import java.util.List;

/**
 * �Զ������ͼ�㣬����������ͼ���Presenter��
 * @author Jax
 *
 */
public interface IExampleView extends IBaseView {

	public static final int MESSAGE_ASYN_TESTDATA = 0;

	public static final int MESSAGE_SYN_TESTDATA = 1;

	public void onSetAsynTestData(List<String> list);

	public void onSetSynTestData(List<String> list);
}
