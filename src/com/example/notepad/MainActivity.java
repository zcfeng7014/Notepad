package com.example.notepad;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;

import Utils.CodeUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TxtSearchListAdapter fla=null;;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fla=new TxtSearchListAdapter(getApplicationContext());
		lv = (ListView) findViewById(R.id.filelist);
		setTitle("本机文本列表");
		lv.setAdapter(fla);
		initlistener();
	}

	private void initlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
					String code;
					try {
						code = CodeUtils.getCharset(fla.getList().get(position));
				          Intent intent=new Intent(MainActivity.this,TableActivity.class);
				          intent.putExtra("file", fla.getList().get(position).getPath());
				          intent.putExtra("code", code);
				          startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
			new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")  
		     .setMessage("您确认退出？")
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() {
		         @Override  
		  
		         public void onClick(DialogInterface dialog, int which) {
		             finish();  
		         }  
		     }).setNegativeButton("返回",new DialogInterface.OnClickListener() {
		         @Override  
		  
		         public void onClick(DialogInterface dialog, int which) { 
		         }  
		  
		     }).show();
	
}
}
