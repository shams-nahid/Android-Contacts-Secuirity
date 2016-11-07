package com.bmshamsnahid.callerappbeta;

import java.util.List;

import javax.xml.datatype.Duration;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custom_List_Adapter_For_Logging_Screen extends ArrayAdapter<String> {
	
	private final Activity context;
	private final String[] logging_screen_name;
	private final String[] logging_screen_number;
	private final String[] logging_screen_time;
	private final String[] logging_screen_duration;
	private final String[] logging_screen_type;
	private final String[] external_contacts_number;
	private final String[] external_contacts_name;
	
	public Custom_List_Adapter_For_Logging_Screen(Activity context, String[] logging_screen_name, String[] logging_screen_number, String[] logging_screen_time, String[] logging_screen_duration, String[] logging_screen_type, String[] external_contacts_name, String[] external_contacts_number) {
		super(context, R.layout.log_screen_row, logging_screen_number);
		this.context = context;
		this.logging_screen_name = logging_screen_name;
		this.logging_screen_number = logging_screen_number;
		this.logging_screen_time = logging_screen_time;
		this.logging_screen_duration = logging_screen_duration;
		this.logging_screen_type = logging_screen_type;
		this.external_contacts_name = external_contacts_name;
		this.external_contacts_number = external_contacts_number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.log_screen_row, null, true);
		
		TextView tv_logging_screen_name = (TextView) rowView.findViewById(R.id.text_view_log_screen_name);
		ImageView imageView_logging_screen_image = (ImageView) rowView.findViewById(R.id.image_view_log_screen_image);
		TextView tv_logging_screen_number = (TextView) rowView.findViewById(R.id.text_view_log_screen_number);
		TextView tv_logging_screen_time_and_duration = (TextView) rowView.findViewById(R.id.text_view_log_screen_time_and_duration);
		
		
		String duration = logging_screen_duration[position];
		try {
			long hour = 0, min = 0, sec = 0, second;
			second = Long.parseLong(logging_screen_duration[position]);
			hour = (second % 86400 ) / 3600 ;
			min = ((second % 86400 ) % 3600 ) / 60; 
			sec = ((second % 86400 ) % 3600 ) % 60  ;
			duration = (String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec));
		} catch(Exception e) {
			Log.d("Parse Duration", e.toString());
		}
		
		if(logging_screen_name[position] != null && (logging_screen_name[position].equals("") == false ))
		{
			tv_logging_screen_name.setText(logging_screen_name[position]);
		}
		else {
			tv_logging_screen_name.setText(get_name(logging_screen_number[position]));
			logging_screen_name[position] = "Unknown";
		}
		
		tv_logging_screen_number.setText(logging_screen_number[position]);
		tv_logging_screen_time_and_duration.setText(logging_screen_time[position] + " Duration: " + duration);
		
		imageView_logging_screen_image.setImageResource(R.drawable.unknown_call);
		
		try {
			if(logging_screen_type[position].contentEquals("OUTGOING")) imageView_logging_screen_image.setImageResource(R.drawable.outgoing_call);
			if(logging_screen_type[position].contentEquals("INCOMING")) imageView_logging_screen_image.setImageResource(R.drawable.incoming_call);
			if(logging_screen_type[position].contentEquals("MISSED")) imageView_logging_screen_image.setImageResource(R.drawable.missed_call);
		} catch(Exception e) {
			Log.d("LOG SCREEN", e.toString());
		}
		
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
