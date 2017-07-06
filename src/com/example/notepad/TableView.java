package com.example.notepad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;

public class TableView extends ScrollView {
	EditText  tv;
	float textsize=18;
	public TableView(Context context) {
		super(context);
		
	}
	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 LayoutInflater.from(context).inflate(R.layout.tableview, this, true);   
	        tv = (EditText) findViewById(R.id.show);
	        tv.setTextSize(18);
	        
	}
	public TableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 LayoutInflater.from(context).inflate(R.layout.tableview, this, true);   
	        tv = (EditText) findViewById(R.id.show); 
	        tv.setTextSize(18);
	
	}
	public void setText(String text) {
		tv.setText(text);
	}
	public String getText() {
		return tv.getText().toString();
	}
	public void textsizesmaller() {
		tv.setTextSize(--textsize);

	}
	public void textsizebigger() {
		tv.setTextSize(++textsize);
	}
	public void textsizereset() {
		textsize=18;
		tv.setTextSize(textsize);
	}
	
}
