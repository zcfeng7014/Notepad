package com.example.notepad;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FileListAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<File> list;
	public List<File> getList() {
		return list;
	}
	public void setList(List<File> list) {
		this.list = list;
	}
	private File sdpath;
	Context context;
	Drawable d=null;
	Drawable f=null;
	public FileListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		
		this.context=context;
		d= context.getResources().getDrawable(R.drawable.directory);
		f= context.getResources().getDrawable(R.drawable.file);
		d.setBounds(0, 0, 160, 160);
		f.setBounds(0, 0, 160, 160);
		mInflater=LayoutInflater.from(context);
		list=new ArrayList();
		File SDFile =Environment.getExternalStorageDirectory();
		sdpath = new File(SDFile.getAbsolutePath());
		updatelist();
		         
	}
	public void in(File path){
		sdpath=path;
	}
	public void updatelist() {
		// TODO Auto-generated method stub
	
		list.clear();
		ArrayList<File> f=new ArrayList<File>();
		if (sdpath.listFiles().length > 0) {  
            for (File file : sdpath.listFiles()) { 
            	if(file.isDirectory())
            		list.add(file);
            	else
            		f.add(file);
            }  
            list.addAll(f);
        }
		
	}
	public String getpath(){
		return sdpath.getName();
	}
	public boolean back(){
		if(sdpath.getName().equals("storage"))
			return false;
		sdpath=sdpath.getParentFile();
		return true;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	if(position==0)
	{
		TextView textView=new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setText("返回上一级");
		textView.setHeight(80);
		return textView;
	}
		LinearLayout ll=(LinearLayout) mInflater.inflate( R.layout.directorylist, null);
		TextView tv=(TextView) ll.findViewById(R.id.filename);
		tv.setText(list.get(position-1).getName());
		if(!list.get(position-1).isFile())
			tv.setCompoundDrawables(d,null,null,null);
		else
		{
			FileInputStream fis = null;
            
			try {
				tv.append(" \n"+getFileSizes(list.get(position-1)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
		
						// TODO Auto-generated catch block
						tv.append("\n 0B");
			
			}
			   
			tv.setCompoundDrawables(f,null,null,null);
		}
		return tv;
	}
	public String getFileSizes(File f) throws Exception{//取得文件大小
        double s=0;
        if(f.exists()){
        FileInputStream fis = null;
      
        fis = new FileInputStream(f);
        s= fis.available();
        }
        int level=0;
        while(s>1024){
        	s/=1024;
        	level++;
        }
        s=((double)(int)(s*1000))/1000;
        switch(level){
        case 0: return s+"B";
        case 1:return s+"KB";
        case 2:return s+"MB";
        case 3:return s+"GB";
        case 4:return s+"TB";
        default:
        	return "超出范围";
        }
    }
	
}
