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
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TxtSearchListAdapter extends BaseAdapter {
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
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			list.add((File) msg.obj);
			notifyDataSetChanged();
			super.handleMessage(msg);
		}
	};
	public TxtSearchListAdapter(Context context) {
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
	private void updatelist() {
		list.clear();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getFile(sdpath);
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "搜索完成 搜索到"+list.size()+"本", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		}).start();
	}
	public void getFile(File root) {
		// TODO Auto-generated method stub
		if (root.listFiles().length > 0) {  
            for (File file : root.listFiles()) {
            	if(file.isDirectory())
            		getFile(file);
            	else
            	{
            		String filename=file.getName();
					int index=filename.indexOf(".");
					String type=filename.substring(index+1);
					try {
						if(type.equals("txt")&&getFileSize(file)>0){
							Message msg=new Message();
							msg.obj=file;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }
        }
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		LinearLayout ll=(LinearLayout) mInflater.inflate( R.layout.directorylist, null);
		TextView tv=(TextView) ll.findViewById(R.id.filename);
		tv.setText(list.get(position).getName());
		FileInputStream fis = null;
		try {
			tv.append("   "+getFileSizeString(list.get(position)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
	
					// TODO Auto-generated catch block
					tv.append("  0B");
		
		}
		tv.append(" \n"+list.get(position).getPath());
		tv.setCompoundDrawables(f,null,null,null);
		return tv;
	}
	public double getFileSize(File f) throws Exception{//取得文件大小
        double s=0;
        if(f.exists()){
        FileInputStream fis = null;
      
        fis = new FileInputStream(f);
        s= fis.available();
        }
      
      
        	return s;
        
    }
	public String getFileSizeString(File f) throws Exception{//取得文件大小
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
