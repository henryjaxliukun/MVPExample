package com.example.mvpexample.activity;

import java.util.List;

import com.example.mvpexample.R;
import com.example.mvpexample.presenter.ExamplePresenter;
import com.example.mvpexample.view.IBaseView;
import com.example.mvpexample.view.IExampleView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements IExampleView {

	private ExamplePresenter mPresenter;
	private TextView tvResult;
	private Button btnRequestAsynData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPresenter=new ExamplePresenter(this, true, getIntent());
		mPresenter.doCreateView();
	}
	
	@Override
	public void onError(Message msg) {
		// TODO Auto-generated method stub
		tvResult.setText("数据错误");
	}

	@Override
	public void onDefault(Message msg) {
		// TODO Auto-generated method stub
		tvResult.setText("无数据返回");
	}

	@Override
	public void onWait(Message msg) {
		// TODO Auto-generated method stub
		//数据等待的时候，走这个方法
		if(msg.arg1==IBaseView.MESSAGE_WAIT_BEGIN){
			//异步请求数据中
			tvResult.setText("数据请求ing...");
		}else if(msg.arg1==IBaseView.MESSAGE_WAIT_END){
			//异步请求数据已经完成
		}else{
			//同步请求数据中
		}
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		//这里完成findviewbyid等操作
		tvResult=(TextView) findViewById(R.id.tv_result);
		btnRequestAsynData=(Button) findViewById(R.id.btn_request_asyn_data);
		btnRequestAsynData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPresenter.doRequestAsynData();
			}
		});
	}

	@Override
	public void onStartActivity(Intent intent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetAsynTestData(List<String> list) {
		// TODO Auto-generated method stub
		//这里设置异步回调的数据
		tvResult.setText(list.get(0));
	}

	@Override
	public void onSetSynTestData(List<String> list) {
		// TODO Auto-generated method stub
		//这里设置同步回调的数据
		tvResult.setText(list.get(0));
	}
	
	
}
