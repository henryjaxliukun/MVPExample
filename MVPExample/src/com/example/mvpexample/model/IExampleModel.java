package com.example.mvpexample.model;

import java.util.List;

import com.example.mvpexample.model.bean.ExampleBean;

/**
 * 数据层接口范例
 * @author Jax
 *
 */
public interface IExampleModel {

	/**
	 * 异步获取数据
	 * @return
	 * @throws Exception 
	 */
	public List<ExampleBean> getAsynTestData() throws Exception;
	
	/**
	 * 同步获取数据
	 * @return
	 * @throws Exception 
	 */
	public List<ExampleBean> getSynTestData() throws Exception;
}
