package com.bmshamsnahid.callerappbeta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_List_Adapter_For_Search extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] contacts_name;
	private final String[] contacts_number;
	String[] str;
 	
	public Custom_List_Adapter_For_Search(Activity context, String[] contacts_name, String[] contacts_number) {
		super(context, R.layout.search_contacts_row, contacts_name);
		this.context = context;
		this.contacts_name = contacts_name;
		this.contacts_number = contacts_number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.search_contacts_row, null, true);
		
		TextView tv_contacts_name = (TextView) rowView.findViewById(R.id.text_view_contacts_name_hosting_search_contacts_adapter);
		ImageView iv_contacts_image = (ImageView) rowView.findViewById(R.id.image_view_icon_hosting_search_contacts_adapter);
		TextView tv_contacts_number = (TextView) rowView.findViewById(R.id.text_view_contacts_number_hosting_search_contacts_adapter);
		
		font_initializing();
		
		tv_contacts_name.setText(contacts_name[position]);
		iv_contacts_image.setImageResource(R.drawable.contact_image);
		tv_contacts_number.setText(contacts_number[position]);
		
		return rowView;
	}

	private void font_initializing() {
		
	}

}
