package com.bmshamsnahid.callerappbeta;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] name;
	private final Integer[] imgid;
	private final String[] number;
	String[] str;
 	
	public CustomListAdapter(Activity context, String[] name, Integer[] imgid, String[] number) {
		super(context, R.layout.mylist, name);
		this.context = context;
		this.name = name;
		this.imgid = imgid;
		this.number = number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.mylist, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView extraText = (TextView) rowView.findViewById(R.id.texttview1);
		
		font_initializing();
		
		txtTitle.setText(name[position]);
		imageView.setImageResource(R.drawable.contact_image);
		extraText.setText(number[position]);
		
		return rowView;
	}

	private void font_initializing() {
		
	}
}
