package com.bmshamsnahid.callerappbeta;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modify_Contacts extends Activity implements OnClickListener{

	String  str_persons_name_modify,
		str_persons_pnone_number_modify,
		str_persons_home_number_modify,
		str_persons_work_number_modify,
		str_persons_email_id_modify,
		str_persons_company_name_modify,
		str_persons_job_title_modify;
	
	EditText  et_persons_name_modify,
		et_persons_pnone_number_modify,
		et_persons_home_number_modify,
		et_persons_work_number_modify,
		et_persons_email_id_modify,
		et_persons_company_name_modify,
		et_persons_job_title_modify;
	
	Button b_modify_contact, b_phones_hosting_modify_contacts, b_contacts_hosting_modify_contacts, b_log_hosting_modify_contacts;
	
	
	ArrayList < ContentProviderOperation > ops;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_contacts);
		
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
		if(str_persons_name_modify != null) {
			ops.add(ContentProviderOperation.newInsert(
				     ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, str_persons_name_modify)
				     .build());
		}
		
		///////////////////Number_Insert///////////////////////////////////////////
		if(str_persons_pnone_number_modify != null) {
			ops.add(ContentProviderOperation.
				     newInsert(ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, str_persons_pnone_number_modify)
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
		     Toast.makeText(Modify_Contacts.this, "Sorry!!! An Internal Error Occours\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
		 }
		
	}

	
	private void initialize() {
		edit_text_initialize();
		button_initialize();
		font_initialize();
		
		Bundle bd_get_modifing_name_number = getIntent().getExtras();
		
		String deleted_name="", deleted_number="";
		
		try {
			deleted_name = bd_get_modifing_name_number.getString("contacts_name_to_modify");
			deleted_number = bd_get_modifing_name_number.getString("contacts_number_to_modify");
			deleteContact(Modify_Contacts.this, deleted_number, deleted_name);
			
			
			Manage_Database db_info = new Manage_Database(Modify_Contacts.this);
			db_info.open();
			db_info.deleteContacts(deleted_name, deleted_number);
			db_info.close();
		
		} catch(Exception e) {
			Log.d("Modifying", "Do not get bundle string value");
		}
		
		
	}
	
	private void font_initialize() {
		try {	
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_phones_hosting_modify_contacts.setTypeface(font_rough_simple);
			b_contacts_hosting_modify_contacts.setTypeface(font_rough_simple);
			b_log_hosting_modify_contacts.setTypeface(font_rough_simple);
			
			et_persons_name_modify.setTypeface(font_chunk_five);
			et_persons_pnone_number_modify.setTypeface(font);
			b_modify_contact.setTypeface(font_chunk_five);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void button_initialize() {
		b_modify_contact = (Button) findViewById(R.id.button_modify_contact_modify);
		b_phones_hosting_modify_contacts = (Button) findViewById(R.id.button_phone_hosting_modify_contacts);
		b_contacts_hosting_modify_contacts = (Button) findViewById(R.id.button_contacts_hosting_modify_contacts);
		b_log_hosting_modify_contacts = (Button) findViewById(R.id.button_log_hosting_modify_contacts);
		
		b_phones_hosting_modify_contacts.setOnClickListener(this);
		b_contacts_hosting_modify_contacts.setOnClickListener(this);
		b_modify_contact.setOnClickListener(this);
		b_log_hosting_modify_contacts.setOnClickListener(this);
	}
	
	private void parse_string_value() {
		
		str_persons_name_modify = et_persons_name_modify.getText().toString();
		str_persons_pnone_number_modify = et_persons_pnone_number_modify.getText().toString();
		//str_persons_home_number_modify = et_persons_home_number_modify.getText().toString();
		//str_persons_work_number_modify = et_persons_work_number_modify.getText().toString();
		//str_persons_email_id_modify = et_persons_email_id_modify.getText().toString();
		//str_persons_company_name_modify = et_persons_company_name_modify.getText().toString();
		//str_persons_job_title_modify = et_persons_job_title_modify.getText().toString();
	}
	
	private void edit_text_initialize() {
		et_persons_name_modify = (EditText) findViewById(R.id.edit_text_name_modify);
		et_persons_pnone_number_modify = (EditText) findViewById(R.id.edit_text_number_modify);
		//et_persons_home_number_modify = (EditText) findViewById(R.id.edit_text_home_number_modify);
		//et_persons_work_number_modify = (EditText) findViewById(R.id.edit_text_work_number_modify);
		//et_persons_email_id_modify = (EditText) findViewById(R.id.edit_text_email_id_modify);
		//et_persons_company_name_modify = (EditText) findViewById(R.id.edit_text_company_name_modify);
		//et_persons_job_title_modify = (EditText) findViewById(R.id.edit_text_job_title_modify);
		
		Bundle bd_get_modifing_name_number = getIntent().getExtras();
		
		String name="", number="";
		
		try {
			name = bd_get_modifing_name_number.getString("contacts_name_to_modify");
			number = bd_get_modifing_name_number.getString("contacts_number_to_modify");
		} catch(Exception e) {
			Log.d("Modifying", "Do not get bundle string value");
		}
		
		et_persons_name_modify.setText(name);
		et_persons_pnone_number_modify.setText(number);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_modify_contact_modify:
			parse_string_value();
			
			SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			String values = getData.getString("list", "1");
			
			
			if(values.contentEquals("1")) create_a_contact();
			else if(values.contentEquals("2")) create_a_contact_in_custom_database();
			else {
				create_a_contact_in_custom_database();
				create_a_contact();
			}
			
			startActivity(new Intent(Modify_Contacts.this, Contacts_Screen.class));
			break;
		case R.id.button_phone_hosting_modify_contacts:
			startActivity(new Intent(Modify_Contacts.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_modify_contacts:
			startActivity(new Intent(Modify_Contacts.this, Contacts_Screen.class));
			break;
		case R.id.button_log_hosting_modify_contacts:
			startActivity(new Intent(Modify_Contacts.this, Log_Screen.class));
			break;
		}
	}

////////////////////////////////DELETE///////////////////////////////////////////
	public static boolean deleteContact(Context ctx, String phone, String name) {
		Uri contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
		Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
		try {
			if (cur.moveToFirst()) {
			do {
				if (cur.getString(cur.getColumnIndex(PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
					String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
					Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
					ctx.getContentResolver().delete(uri, null, null);
					return true;
					}
			
				} while (cur.moveToNext());
			}
		
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			cur.close();
		}
		return false;
	}
	
	private void create_a_contact_in_custom_database() {
		
		try {
			Manage_Database entry = new Manage_Database(Modify_Contacts.this);
			entry.open();
			entry.createEntry(str_persons_name_modify, str_persons_pnone_number_modify, str_persons_home_number_modify,
					str_persons_work_number_modify,
					str_persons_email_id_modify,
					str_persons_company_name_modify,
					str_persons_job_title_modify, "extra value");
			entry.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		} finally {
			
		}
	}
	
}
