package com.bmshamsnahid.callerappbeta;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_List_Adapter_For_Outgoing_CAll extends ArrayAdapter<String> {
	
	private final Activity context;
	private final String[] outgoing_call_log_name;
	private final String[] outgoing_call_log_number;
	private final String[] outgoing_call_log_time;
	private final String[] outgoing_call_log_duration;
	private final String[] external_contacts_number;
	private final String[] external_contacts_name;
	
	public Custom_List_Adapter_For_Outgoing_CAll(Activity context, String[] outgoing_call_log_name, String[] outgoing_call_log_number, String[] outgoing_call_log_time, String[] outgoing_call_log_duration, String[] external_contacts_name, String[] external_contacts_number) {
		super(context, R.layout.outgoing_call_log_row, outgoing_call_log_number);
		this.context = context;
		this.outgoing_call_log_name = outgoing_call_log_name;
		this.outgoing_call_log_number = outgoing_call_log_number;
		this.outgoing_call_log_time = outgoing_call_log_time;
		this.outgoing_call_log_duration = outgoing_call_log_duration;
		this.external_contacts_name = external_contacts_name;
		this.external_contacts_number = external_contacts_number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.outgoing_call_log_row, null, true);
		
		TextView tv_outgoing_call_log_name = (TextView) rowView.findViewById(R.id.text_view_outgoing_call_log_name);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.image_view_outgoing_call_log_image);
		TextView tv_outgoing_call_log_number = (TextView) rowView.findViewById(R.id.text_view_outgoing_call_log_number);
		TextView tv_outgoing_call_log_time_and_duration = (TextView) rowView.findViewById(R.id.text_view_outgoing_call_log_time_and_duration);
		
		String duration = outgoing_call_log_duration[position];
		try {
			long hour = 0, min = 0, sec = 0, second;
			second = Long.parseLong(outgoing_call_log_duration[position]);
			hour = (second % 86400 ) / 3600 ;
			min = ((second % 86400 ) % 3600 ) / 60; 
			sec = ((second % 86400 ) % 3600 ) % 60  ;
			duration = (String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec));
		} catch(Exception e) {
			Log.d("Parse Duration", e.toString());
		}
		
		
		if(outgoing_call_log_name[position] != null && (outgoing_call_log_name[position].equals("") == false ))
		{
			tv_outgoing_call_log_name.setText(outgoing_call_log_name[position]);
		}
		else {
			tv_outgoing_call_log_name.setText(get_name(outgoing_call_log_number[position]));
			outgoing_call_log_name[position] = "Unknown";
		}
		
		tv_outgoing_call_log_number.setText(outgoing_call_log_number[position]);
		tv_outgoing_call_log_time_and_duration.setText(outgoing_call_log_time[position] + " Duration: " + duration);
		imageView.setImageResource(R.drawable.outgoing_call);
		
		return rowView;
	}
	
	public String get_name(String number) {
		
		try {
			int index = external_contacts_number.length;
			for(int i=0; i<index; i++) {
				if(external_contacts_number[i].contentEquals(number) == true) return external_contacts_name[i];
			}
		} catch(Exception e) {
			
		}
		
		return "Unknown";
	}
}
