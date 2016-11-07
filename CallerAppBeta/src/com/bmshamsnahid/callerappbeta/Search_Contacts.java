package com.bmshamsnahid.callerappbeta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Search_Contacts extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener{

	Button b_phone_hosting_search_contacts, b_contacts_hosting_search_contacts, b_log_hosting_search_contacts;
	EditText et_search_contacts_hosting_search_contacts;
	String embedded_contacts_name_str, embedded_contacts_number_str, external_contacts_name_str, external_contacts_number_str;
	ListView list_search_contacts;
	boolean flag;
	int selected_position = 0;
	String[] searching_contact_name = new String[3000];
	String[] searching_contact_number = new String[3000];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);
		initialize();
	}

	private void initialize() {
		button_initialize();
		edit_text_initialize();
		show_contacts_list("qwertyuiop");
		font_initializing();
		menu_setup();
	}

	private void font_initializing() {
		try {
			/////////Trying custom font/////////////////////////////////////////////////////
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_phone_hosting_search_contacts.setTypeface(font_rough_simple);
			b_contacts_hosting_search_contacts.setTypeface(font_rough_simple);
			b_log_hosting_search_contacts.setTypeface(font_rough_simple);
			
			et_search_contacts_hosting_search_contacts.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void show_contacts_list(String match) {
		try {
			set_external_contact();
			set_embedded_contact();
		    String contacts_name_str = embedded_contacts_name_str + external_contacts_name_str;
		    String contacts_number_str = embedded_contacts_number_str + external_contacts_number_str;
			
		    String[] contacts_name = contacts_name_str.split("--");
			String[] contacts_number = contacts_number_str.split("--");
			
			String temp_name_str = "", temp_number_str =  "";
			
			int index = contacts_name.length;
			
			for(int i=0; i<index; i++) {
				if(contacts_name[i].toLowerCase().contains(match.toLowerCase())) {
					temp_name_str += contacts_name[i];
					temp_number_str += contacts_number[i];
					temp_name_str += "--";
					temp_number_str += "--";
				}
			}
			
			String[] final_contacts_name = temp_name_str.split("--");
			String[] final_contacts_number = temp_number_str.split("--");
			
			index = final_contacts_name.length;
			
			for(int i=0; i<index; i++) {
				searching_contact_name[i] = final_contacts_name[i];
				searching_contact_number[i] = final_contacts_number[i];
			}
			
			Custom_List_Adapter_For_Search adapter_search_contacts = new Custom_List_Adapter_For_Search(this, final_contacts_name, final_contacts_number);
			list_search_contacts = (ListView) findViewById(R.id.list_view_search_contacts);
			list_search_contacts.setAdapter(adapter_search_contacts);
		} catch(Exception e) {
			Log.d("Exception", e.toString());
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void edit_text_initialize() {
		et_search_contacts_hosting_search_contacts = (EditText) findViewById(R.id.edit_text_search_contacts_hosting_search_contacts);
		et_search_contacts_hosting_search_contacts.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!s.equals("")) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				show_contacts_list(et_search_contacts_hosting_search_contacts.getText().toString());
			}
		});
	}

	private void button_initialize() {
		b_phone_hosting_search_contacts = (Button) findViewById(R.id.button_phone_hosting_search_contacts);
		b_contacts_hosting_search_contacts = (Button) findViewById(R.id.button_contacts_hosting_search_contacts);
		b_log_hosting_search_contacts = (Button) findViewById(R.id.button_log_hosting_search_contacts);
		
		b_phone_hosting_search_contacts.setOnClickListener(this);
		b_contacts_hosting_search_contacts.setOnClickListener(this);
		b_log_hosting_search_contacts.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_phone_hosting_search_contacts:
			startActivity(new Intent(Search_Contacts.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_search_contacts:
			startActivity(new Intent(Search_Contacts.this, Contacts_Screen.class));
			break;
		case R.id.button_log_hosting_search_contacts:
			startActivity(new Intent(Search_Contacts.this, Log_Screen.class));
			break;
		}
	}
	
	public void set_embedded_contact() {
		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		
		String str_name = "", str_number = "";
		
		while (phones.moveToNext())
		{
	  		//Read Contact Name
			  String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			  
			  //Read Phone Number
			  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	 
			  if(name!=null && phoneNumber !=null && (name.equals("") == false ) && (name.equals("") == false ))
			  {
				  if(phoneNumber.equals("") == true) continue;
				  
				  str_name += name;
				  str_number += phoneNumber;
				  
				  str_name += "--";
				  str_number += "--";
			  }
		}
		
		embedded_contacts_name_str = str_name;
		embedded_contacts_number_str = str_number;
	}
	
	public void set_external_contact() {
		String str_name = "";
		String str_number = "";
		
		try {
			Manage_Database info = new Manage_Database(Search_Contacts.this);
			info.open();
			
			str_name = info.getName();
			str_number = info.getNumber();
			
			info.close();
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		} finally {
			external_contacts_name_str = str_name;
			external_contacts_number_str = str_number;
		}
	}
	
	private void menu_setup() {
		list_search_contacts.setOnItemClickListener(this);
		list_search_contacts.setOnItemLongClickListener(this);
		registerForContextMenu(list_search_contacts);
		flag = false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.list_view_search_contacts) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.contacts_menu, menu);
		}
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		selected_position = position;
		if(flag) {
			flag = false;
			return;
		}
		
		showDialog(this, "", searching_contact_name[position] + "\n" + searching_contact_number[position]);
		
		//Intent intent = new Intent(Log_Screen.this, Phone_Call_Screen.class);
		//intent.putExtra("contacts_number", logging_screen_number[position]);
		//startActivity(intent);
	}
	
	public void showDialog(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.setNegativeButton("Call", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	Intent intent = new Intent(Search_Contacts.this, Phone_Call_Screen.class);
	    		intent.putExtra("contacts_number", searching_contact_number[selected_position]);
	    		startActivity(intent);
	       }
	    });
	    
	    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent intent_send_message = new Intent(Search_Contacts.this, Send_Message.class);
					intent_send_message.putExtra("contacts_name_to_send_message", searching_contact_name[selected_position]);
					intent_send_message.putExtra("contacts_number_to_send_message", searching_contact_number[selected_position]);
					startActivity(intent_send_message);
	           }
	    });
	    
	    builder.show();
	}
	
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		selected_position = position;
		flag = true;
		String str_temp = "";
		if(searching_contact_number[position].equals("") == true) str_temp = "\nNULL";
		return false;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_call:
			Intent intent = new Intent(Search_Contacts.this, Phone_Call_Screen.class);
			intent.putExtra("contacts_number", searching_contact_number[selected_position]);
			startActivity(intent);
			break;
		case R.id.item_message:
			Intent intent_send_message = new Intent(Search_Contacts.this, Send_Message.class);
			intent_send_message.putExtra("contacts_name_to_send_message", searching_contact_name[selected_position]);
			intent_send_message.putExtra("contacts_number_to_send_message", searching_contact_number[selected_position]);
			startActivity(intent_send_message);
			break;
		case R.id.item_edit:
			Intent intent_modify = new Intent(Search_Contacts.this, Modify_Contacts.class);
			intent_modify.putExtra("contacts_name_to_modify", searching_contact_name[selected_position]);
			intent_modify.putExtra("contacts_number_to_modify", searching_contact_number[selected_position]);
			startActivity(intent_modify);
			break;
		case R.id.item_delete:
			//Toast.makeText(this, "Delete Selected", Toast.LENGTH_SHORT).show();
			deleteContact(this, searching_contact_number[selected_position], searching_contact_name[selected_position]);
			startActivity(new Intent(Search_Contacts.this, Contacts_Screen.class));
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	public boolean deleteContact(Context ctx, String phone, String name) {
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
}
