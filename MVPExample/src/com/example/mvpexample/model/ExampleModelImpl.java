package com.example.mvpexample.model;

import java.util.ArrayList;
import java.util.List;

import com.example.mvpexample.model.bean.ExampleBean;

/**
 * 数据层实现范例，这里应当完成网络请求数据或者是数据库请求数据
 * 
 * @author Jax
 *
 */
public class ExampleModelImpl implements IExampleModel {

	@Override
	public List<ExampleBean> getAsynTestData() throws Exception {
		// TODO Auto-generated method stub
		Thread.sleep(5000);
		List<ExampleBean> list = new ArrayList<ExampleBean>();
		ExampleBean bean = new ExampleBean();
		bean.setContent("这是异步返回的消息");
		list.add(bean);
		return list;
	}

	@Override
	public List<ExampleBean> getSynTestData() throws Exception {
		// TODO Auto-generated method stub
		List<ExampleBean> list = new ArrayList<ExampleBean>();
		ExampleBean bean = new ExampleBean();
		bean.setContent("这是同步返回的消息");
		list.add(bean);
		return list;
	}

}
