package com.example.mvpexample.view;

import java.util.List;

/**
 * 自定义的视图层，用来解耦视图层和Presenter层
 * @author Jax
 *
 */
public interface IExampleView extends IBaseView {

	public static final int MESSAGE_ASYN_TESTDATA = 0;

	public static final int MESSAGE_SYN_TESTDATA = 1;

	public void onSetAsynTestData(List<String> list);

	public void onSetSynTestData(List<String> list);
}
