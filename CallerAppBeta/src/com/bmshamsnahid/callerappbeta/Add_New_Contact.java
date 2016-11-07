package com.bmshamsnahid.callerappbeta;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_New_Contact extends Activity implements OnClickListener {

	String  str_persons_name,
			str_persons_pnone_number,
			str_persons_home_number,
			str_persons_work_number,
			str_persons_email_id,
			str_persons_company_name,
			str_persons_job_title;
	
	EditText  et_persons_name,
			et_persons_pnone_number,
			et_persons_home_number,
			et_persons_work_number,
			et_persons_email_id,
			et_persons_company_name,
			et_persons_job_title;
	
	Button b_add_contact, b_phone_hosting_add_new_contact, b_contacts_hosting_add_new_contact, b_log_hosting_add_new_contact;
	
	
	ArrayList < ContentProviderOperation > ops;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_contact);
		
		ops = new ArrayList < ContentProviderOperation > ();

		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());
		
		initialize();
	}

	private void create_a_contact() {
		
		
		
		//////////////Name_Insert//////////////////////////////////////////////////
		if(str_persons_name != null) {
			ops.add(ContentProviderOperation.newInsert(
				     ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, str_persons_name)
				     .build());
		}
		
		///////////////////Number_Insert///////////////////////////////////////////
		if(str_persons_pnone_number != null) {
			ops.add(ContentProviderOperation.
				     newInsert(ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, str_persons_pnone_number)
				     .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				     .build());
		}
		
		/*if(str_persons_home_number != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
			     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
			     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, str_persons_home_number)
			     .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
			     .build());
		}
		
		if(str_persons_work_number != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
			     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
			     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, str_persons_work_number)
			     .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
			     .build());
		}
		
		if(str_persons_email_id != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
			     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
			     .withValue(ContactsContract.CommonDataKinds.Email.DATA, str_persons_email_id)
			     .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
			     .build());
		}
		
		if(str_persons_company_name != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
			     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
			     .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, str_persons_company_name)
			     .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
			     .build());
		}
		
		if(str_persons_job_title != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
			     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, str_persons_job_title)
				 .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				 .build());
		}*/
		
		try {
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		 } catch (Exception e) {
		     e.printStackTrace();
		     Toast.makeText(Add_New_Contact.this, "Sorry!!! An Internal Error Occours\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
		 }
		
	}

	private void initialize() {
		edit_text_initialize();
		button_initialize();
		font_initialize();
	}

	private void font_initialize() {
		try {
			/////////Trying custom font/////////////////////////////////////////////////////
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_phone_hosting_add_new_contact.setTypeface(font_rough_simple);
			b_contacts_hosting_add_new_contact.setTypeface(font_rough_simple);
			b_log_hosting_add_new_contact.setTypeface(font_rough_simple);
			
			et_persons_name.setTypeface(font_chunk_five);
			et_persons_pnone_number.setTypeface(font);
			b_add_contact.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void button_initialize() {
		b_add_contact = (Button) findViewById(R.id.button_add_contact);
		b_phone_hosting_add_new_contact = (Button) findViewById(R.id.button_phone_hosting_add_new_contact);
		b_contacts_hosting_add_new_contact = (Button) findViewById(R.id.button_contacts_hosting_add_new_contact);
		b_log_hosting_add_new_contact = (Button) findViewById(R.id.button_log_hosting_add_new_contact);		
		
		b_phone_hosting_add_new_contact.setOnClickListener(this);
		b_contacts_hosting_add_new_contact.setOnClickListener(this);
		b_add_contact.setOnClickListener(this);
		b_log_hosting_add_new_contact.setOnClickListener(this);
	}

	private void parse_string_value() {
		str_persons_name = et_persons_name.getText().toString();
		str_persons_pnone_number = et_persons_pnone_number.getText().toString();
		//str_persons_home_number = et_persons_home_number.getText().toString();
		//str_persons_work_number = et_persons_work_number.getText().toString();
		//str_persons_email_id = et_persons_email_id.getText().toString();
		//str_persons_company_name = et_persons_company_name.getText().toString();
		//str_persons_job_title = et_persons_job_title.getText().toString();
	}

	private void edit_text_initialize() {
		et_persons_name = (EditText) findViewById(R.id.edit_text_name);
		et_persons_pnone_number = (EditText) findViewById(R.id.edit_text_number);
		//et_persons_home_number = (EditText) findViewById(R.id.edit_text_home_number);
		//et_persons_work_number = (EditText) findViewById(R.id.edit_text_work_number);
		//et_persons_email_id = (EditText) findViewById(R.id.edit_text_email_id);
		//et_persons_company_name = (EditText) findViewById(R.id.edit_text_company_name);
		//et_persons_job_title = (EditText) findViewById(R.id.edit_text_job_title);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_add_contact:
			parse_string_value();
			
			SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			String values = getData.getString("list", "1");
			
			if(values.contentEquals("1")) {
				create_a_contact();
			}
			
			if(values.contentEquals("2")) {
				create_a_contact_in_custom_database();
			}
			
			
			else {
				create_a_contact();
				create_a_contact_in_custom_database();
			}
			startActivity(new Intent(Add_New_Contact.this, Contacts_Screen.class));
			break;
		case R.id.button_phone_hosting_add_new_contact:
			startActivity(new Intent(Add_New_Contact.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_add_new_contact:
			startActivity(new Intent(Add_New_Contact.this, Contacts_Screen.class));
			break;
		case R.id.button_log_hosting_add_new_contact:
			startActivity(new Intent(Add_New_Contact.this, Log_Screen.class));
			break;
		}
		
	}

	private void create_a_contact_in_custom_database() {
		
		try {
			Manage_Database entry = new Manage_Database(Add_New_Contact.this);
			entry.open();
			entry.createEntry(str_persons_name, str_persons_pnone_number, str_persons_home_number,
					str_persons_work_number,
					str_persons_email_id,
					str_persons_company_name,
					str_persons_job_title, "extra value");
			entry.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		} 
	}

}
