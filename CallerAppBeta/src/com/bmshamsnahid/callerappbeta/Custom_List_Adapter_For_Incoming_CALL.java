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

public class Custom_List_Adapter_For_Incoming_CALL extends ArrayAdapter<String> {
	
	private final Activity context;
	private final String[] incoming_call_log_name;
	private final String[] incoming_call_log_number;
	private final String[] incoming_call_log_time;
	private final String[] incoming_call_log_duration;
	private final String[] external_contacts_number;
	private final String[] external_contacts_name;
	
	public Custom_List_Adapter_For_Incoming_CALL(Activity context, String[] incoming_call_log_name, String[] incoming_call_log_number, String[] incoming_call_log_time, String[] incoming_call_log_duration, String[] external_contacts_name, String[] external_contacts_number ) {
		super(context, R.layout.incoming_call_log_row, incoming_call_log_number);
		this.context = context;
		this.incoming_call_log_name = incoming_call_log_name;
		this.incoming_call_log_number = incoming_call_log_number;
		this.incoming_call_log_time = incoming_call_log_time;
		this.incoming_call_log_duration = incoming_call_log_duration;
		this.external_contacts_name = external_contacts_name;
		this.external_contacts_number = external_contacts_number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.incoming_call_log_row, null, true);
		
		TextView tv_incoming_call_log_name = (TextView) rowView.findViewById(R.id.text_view_incoming_call_log_name);
		ImageView imageView_incoming_call_log_image = (ImageView) rowView.findViewById(R.id.image_view_incoming_call_log_image);
		TextView tv_incoming_call_log_number = (TextView) rowView.findViewById(R.id.text_view_incoming_call_log_number);
		TextView tv_incoming_call_log_time_and_duration = (TextView) rowView.findViewById(R.id.text_view_incoming_call_log_time_and_duration);
		
		String duration = incoming_call_log_duration[position];
		try {
			long hour = 0, min = 0, sec = 0, second;
			second = Long.parseLong(incoming_call_log_duration[position]);
			hour = (second % 86400 ) / 3600 ;
			min = ((second % 86400 ) % 3600 ) / 60; 
			sec = ((second % 86400 ) % 3600 ) % 60  ;
			duration = (String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec));
		} catch(Exception e) {
			Log.d("Parse Duration", e.toString());
		}
		
		
		if(incoming_call_log_name[position] != null && (incoming_call_log_name[position].equals("") == false ))
		{
			tv_incoming_call_log_name.setText(incoming_call_log_name[position]);
		}
		else {
			tv_incoming_call_log_name.setText(get_name(incoming_call_log_number[position]));
			incoming_call_log_name[position] = "Unknown";
		}
		
		tv_incoming_call_log_number.setText(incoming_call_log_number[position]);
		tv_incoming_call_log_time_and_duration.setText(incoming_call_log_time[position] + " Duration: " + duration);
		imageView_incoming_call_log_image.setImageResource(R.drawable.incoming_call);
		
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
