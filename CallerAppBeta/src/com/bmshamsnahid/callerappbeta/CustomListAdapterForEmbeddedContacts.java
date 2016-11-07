package com.bmshamsnahid.callerappbeta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapterForEmbeddedContacts extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] name;
	private final Integer[] imgid;
	private final String[] number;
	String[] str;
 	
	public CustomListAdapterForEmbeddedContacts(Activity context, String[] name, Integer[] imgid, String[] number) {
		super(context, R.layout.embedded_contacts_list_row, name);
		this.context = context;
		this.name = name;
		this.imgid = imgid;
		this.number = number;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.embedded_contacts_list_row, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.text_view_contact_name_hosting_embedded_contacts_list);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.image_view_contact_image_hosting_embedded_contacts_list);
		TextView extraText = (TextView) rowView.findViewById(R.id.text_view_contact_number_hosting_embedded_contacts_list);
		
		txtTitle.setText(name[position]);
		imageView.setImageResource(R.drawable.phone_contact);
		extraText.setText(number[position]);
		
		return rowView;
	}
}
