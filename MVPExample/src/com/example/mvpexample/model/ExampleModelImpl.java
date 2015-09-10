package com.example.mvpexample.model;

import java.util.ArrayList;
import java.util.List;

import com.example.mvpexample.model.bean.ExampleBean;

/**
 * ���ݲ�ʵ�ַ���������Ӧ����������������ݻ��������ݿ���������
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
		bean.setContent("�����첽���ص���Ϣ");
		list.add(bean);
		return list;
	}

	@Override
	public List<ExampleBean> getSynTestData() throws Exception {
		// TODO Auto-generated method stub
		List<ExampleBean> list = new ArrayList<ExampleBean>();
		ExampleBean bean = new ExampleBean();
		bean.setContent("����ͬ�����ص���Ϣ");
		list.add(bean);
		return list;
	}

}
