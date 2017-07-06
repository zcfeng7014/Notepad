package com.example.notepad;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyView extends View {
	private static final String String = null;
	private int mScreenWidth;
	private int mScreenHeight;
	private String Text;
	private int fontSize;
	private TextPaint p;
	private TextPaint p2;
	private ArrayList<Page> content;
	private int page;
	private int lines;
	private int pages;
	private boolean loading=false;
	private boolean isfirst;
	private int pagestart=0;
	TextAsyLoadingThread thread;
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0x01){
				content.clear();
				return;
			}
			Page temp=(Page) msg.obj;
			content.add(temp);
			System.out.println(page+":");
			if(loading&&page<=content.size())
			if(content.get(page-1).index>=pagestart)
			{
				loading=false;
				
			}
			else
			{
				page++;
			}
			
			invalidate();
			super.handleMessage(msg);
		}
		
	};
	
	public MyView(Context context) {
		super(context);
		p=new TextPaint();
		p2=new TextPaint();
		content=new ArrayList<Page>();
		fontSize=60;
		isfirst=true;
		page = 1;
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		p=new TextPaint();
		p2=new TextPaint();
		content=new ArrayList<Page>();
		fontSize=60;
		isfirst=true;
		page = 1;
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		p=new TextPaint();
		p2=new TextPaint();
		content=new ArrayList<Page>();
		fontSize=60;
		isfirst=true;
		page = 1;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setText(String text) {
		Text = text;
		invalidate();
	}
	public String getText() {
		
		return Text;
	}

	private void getContent() {

		page=1;
		if(thread==null){
		thread=new TextAsyLoadingThread();
		thread.start();
		}
		else
		{
			if(thread.isAlive())
				thread.isbreak=true;
			while(thread.isAlive());
		
			loading=true;
			thread=new TextAsyLoadingThread();
			thread.start();
		}
	}

	
	@Override
	public void draw(Canvas canvas) {
		p.setTextSize(fontSize);
		if(isfirst){
			isfirst=false;
			getLines();
			getContent();
		}
		if(loading){
			getLines();
			getContent();
			p2.setTextSize(120);
			canvas.drawText("正在加载",mScreenWidth/4,mScreenHeight/3, p2);
		}
		else{
		getpages();
		int i=0;
		int textheight=(int) p.getTextSize();
		if(page<=pages)
		for (; i < content.get(page-1).content.size(); i++) {
				canvas.drawText(content.get(page-1).content.get(i),0,textheight*(i+1), p);
			}
		}
		p2.setTextSize(60);
		p2.setColor(Color.GRAY);
		canvas.drawText("页码:"+(int)(page)+ "   总页数"+pages,0,mScreenHeight-2, p2);
		super.draw(canvas);
	}
	private void getpages() {
		// TODO Auto-generated method stub
		pages=content.size();
	}
	private void getLines() {
			
		 lines=(int) (mScreenHeight/p.getTextSize())-1;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		if(mScreenWidth!=MeasureSpec.getSize(widthMeasureSpec))
			getContent();
		mScreenWidth=MeasureSpec.getSize(widthMeasureSpec);
		mScreenHeight=MeasureSpec.getSize(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println(mScreenWidth+":"+mScreenHeight);
	}
	public void nextPage() {
		// TODO Auto-generated method stub
		if(page<content.size()){
			page++;
		pagestart=content.get(page-1).index;
		}
	}
	public void proPage(){
		if(page>1){
			page--;
			pagestart=content.get(page-1).index;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_UP){
		int x=(int) event.getRawX();
		int y=(int) event.getRawY();
		if(x>mScreenWidth*2/3)
		{
			nextPage();
			invalidate();
			return false;
		}
			
		if(x<mScreenWidth/3)
			proPage();
			invalidate();
			return false;
		}
		return true;
		
	}
	
	public class TextAsyLoadingThread extends Thread {
		public boolean isbreak=false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String str="";
			Page page=new Page();
			Message msg=new Message();
			msg.what=0x01;
			msg.obj=page;
			handler.sendMessage(msg);
			page.index=0;
			int index=0;
			for (Character ch : Text.toCharArray()) {
				if(isbreak)return ;
				if(ch=='\n')
				{
					str+=ch;
					page.content.add(str);
					page.index=index;
					str="";
					if(page.content.size()==lines){
						msg=new Message();
						msg.obj=page;
						handler.sendMessage(msg);
						page=new Page();
					}
				}
				else
				if((p.measureText(str+ch))>(mScreenWidth)){
					System.out.println();
					page.content.add(str);
					page.index=index;
					str=""+ch;
					if(page.content.size()==lines){
						msg=new Message();
						msg.obj=page;
						handler.sendMessage(msg);
						page=new Page();
					}
				}
				else
				{
					str+=ch;
				}
				index++;
			}
			if(str.length()>0){
				page.content.add(str);
				page.index=index;
			}
			if(page.content.size()>0){
				msg=new Message();
				msg.obj=page;
				handler.sendMessage(msg);
			}
		
			super.run();
		}

	}
}


