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
		tvResult.setText("���ݴ���");
	}

	@Override
	public void onDefault(Message msg) {
		// TODO Auto-generated method stub
		tvResult.setText("�����ݷ���");
	}

	@Override
	public void onWait(Message msg) {
		// TODO Auto-generated method stub
		//���ݵȴ���ʱ�����������
		if(msg.arg1==IBaseView.MESSAGE_WAIT_BEGIN){
			//�첽����������
			tvResult.setText("��������ing...");
		}else if(msg.arg1==IBaseView.MESSAGE_WAIT_END){
			//�첽���������Ѿ����
		}else{
			//ͬ������������
		}
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		//�������findviewbyid�Ȳ���
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
		//���������첽�ص�������
		tvResult.setText(list.get(0));
	}

	@Override
	public void onSetSynTestData(List<String> list) {
		// TODO Auto-generated method stub
		//��������ͬ���ص�������
		tvResult.setText(list.get(0));
	}
	
	
}
