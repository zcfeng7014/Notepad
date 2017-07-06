package com.example.notepad;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
public class TableActivity extends Activity {
	private MyView tv;
	private TextView type;
	private String text;
	private String dir;
	private String code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_table);
		tv = (MyView) findViewById(R.id.table);
		tv.setKeepScreenOn(true);
		type = (TextView) findViewById(R.id.code);
		Intent intent =getIntent();
		 File file=new File(intent.getStringExtra("file"));
		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] b = new byte[1024];
	           int n;
	           try {
				while ((n = bis.read(b)) != -1) {
				       out.write(b, 0, n);
				   }
				bis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        byte[] data= out.toByteArray();
	        out.close();
			code = intent.getStringExtra("code");
			dir = file.getPath();
			String filename=file.getName();
			type.setText("ÎÄ¼þ£º"+filename+"    "+code);
			setTitle(filename);
			try {
				text = new String(data,code);
				tv.setText(text);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
