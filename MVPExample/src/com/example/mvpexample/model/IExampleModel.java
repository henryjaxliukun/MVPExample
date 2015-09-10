package com.example.mvpexample.model;

import java.util.List;

import com.example.mvpexample.model.bean.ExampleBean;

/**
 * ���ݲ�ӿڷ���
 * @author Jax
 *
 */
public interface IExampleModel {

	/**
	 * �첽��ȡ����
	 * @return
	 * @throws Exception 
	 */
	public List<ExampleBean> getAsynTestData() throws Exception;
	
	/**
	 * ͬ����ȡ����
	 * @return
	 * @throws Exception 
	 */
	public List<ExampleBean> getSynTestData() throws Exception;
}
