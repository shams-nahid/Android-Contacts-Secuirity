package com.bmshamsnahid.callerappbeta;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Contacts_Screen extends Activity implements OnItemClickListener, OnItemLongClickListener, OnClickListener {
	
	ListView list;
	String[] contacts_name, contacts_number, contacts_name2, contacts_number2;
	String[] itemname = {
		"Safari",
		"Camera",
		"Global", 
		"Firefox", 
		"UC Browser",
		"Android Folder", 
		"VLC Player", 
		"Cold War"
	};
	
	boolean flag;
	int selected_position = 0;
	String selected_phone_number, selected_name;
	Button b_incoming_call_log_hosting_contacts_screen, b_outgoing_call_log_hosting_contacts_screen, b_missed_call_log_hosting_contacts_screen, b_log_hosting_contacts_screen;
	
	Integer[] imgid = {
			R.drawable.ic_launcher, 
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher
	};
	
	Button b_phones_hosting_contacts_screen, b_contacts_hosting_contacts_screen;
	Button b_embedded_hosting_contacts_screen, b_external_hosting_contacts_screen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_screen);
		
		b_phones_hosting_contacts_screen = (Button) findViewById(R.id.button_phone_hosting_contacts_screen);
		b_phones_hosting_contacts_screen.setOnClickListener(this);
		b_contacts_hosting_contacts_screen = (Button) findViewById(R.id.button_contacts_hosting_contacts_screen);
		b_contacts_hosting_contacts_screen.setOnClickListener(this);
		//b_incoming_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_incoming_call_log_hosting_contacts_screen);
		//b_incoming_call_log_hosting_contacts_screen.setOnClickListener(this);
		//b_outgoing_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_outgoing_call_log_hosting_contacts_screen);
		//b_outgoing_call_log_hosting_contacts_screen.setOnClickListener(this);
		//b_missed_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_missed_call_log_hosting_contacts_screen);
		//b_missed_call_log_hosting_contacts_screen.setOnClickListener(this);
		b_embedded_hosting_contacts_screen = (Button) findViewById(R.id.button_embedded_contacts_hosting_contacts_screen);
		b_embedded_hosting_contacts_screen.setOnClickListener(this);
		b_external_hosting_contacts_screen = (Button) findViewById(R.id.button_extrenal_hosting_contacts_screen);
		b_external_hosting_contacts_screen.setOnClickListener(this);
		b_log_hosting_contacts_screen = (Button) findViewById(R.id.button_log_hosting_contacts_screen);
		b_log_hosting_contacts_screen.setOnClickListener(this);
		
		font_initializing();
		
		String[] contacts_name_temp = new String[3000];
		String[] contacts_number_temp = new String[3000];
		int index = 0;
		
		///////////////////////////////////////Retriving contact////////////////////////
		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		while (phones.moveToNext())
		{
	  		//Read Contact Name
			  String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			  
			  //Read Phone Number
			  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	 
			  if(name!=null && phoneNumber !=null && (name.equals("") == false ) && (name.equals("") == false ))
			  {
				  if(phoneNumber.equals("") == true) continue;
				   contacts_name_temp[index] = name;
				   contacts_number_temp[index] = phoneNumber;
				   index++;
			  }
		}
		
		/////////////////////////////////Retrivig contacts from database/////////////////
		String name_temp = "";
		String number_temp = "";
		int index2 = 0;
		
		try {
			Manage_Database info = new Manage_Database(Contacts_Screen.this);
			info.open();
			name_temp = info.getName();
			number_temp = info.getNumber();
			info.close();
			contacts_name2 = name_temp.split("--");
	        contacts_number2 = number_temp.split("--");
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
        
		index2 = contacts_name2.length;
		
        contacts_name = new String[index+index2];
        contacts_number = new String[index+index2];
        
        ////////////////////////////////////////////////////////////////////////////////
        
        for(int i=0; i<index; i++) {
        	contacts_name[i] = contacts_name_temp[i];
        	contacts_number[i] = contacts_number_temp[i];
        }
        int starting_value = index;
        
        for(int i=0; i<index2; i++) {
        	contacts_name[starting_value] = contacts_name2[i];
        	contacts_number[starting_value] = contacts_number2[i];
        	starting_value++;
        }
        
        for(int i=0; i<index+index2; i++) {
        	for(int j=i+1; j<index+index2; j++) {
        		if(contacts_name[j].compareTo(contacts_name[i]) < 0) {
        			String temp = contacts_name[i];
        			contacts_name[i] = contacts_name[j];
        			contacts_name[j] = temp;
        			
        			temp = contacts_number[i];
        			contacts_number[i] = contacts_number[j];
        			contacts_number[j] = temp;
        		}
        	}
        }
		////////////////////////////////////////////////////////////////////////////////
		
		CustomListAdapter adapter = new CustomListAdapter(this, contacts_name, imgid, contacts_number);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
		
		registerForContextMenu(list);
		
		flag = false;
	}
	
	private void font_initializing() {
		try {	
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_contacts_hosting_contacts_screen.setTypeface(font_pink_t_shirt);
			b_phones_hosting_contacts_screen.setTypeface(font_rough_simple);
			b_log_hosting_contacts_screen.setTypeface(font_rough_simple);
			b_embedded_hosting_contacts_screen.setTypeface(font_chunk_five);
			b_external_hosting_contacts_screen.setTypeface(font_chunk_five);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.list) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.contacts_menu, menu);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_call:
			Intent intent = new Intent(Contacts_Screen.this, Phone_Call_Screen.class);
			intent.putExtra("contacts_number", contacts_number[selected_position]);
			startActivity(intent);
			break;
		case R.id.item_message:
			Intent intent_send_message = new Intent(Contacts_Screen.this, Send_Message.class);
			intent_send_message.putExtra("contacts_name_to_send_message", contacts_name[selected_position]);
			intent_send_message.putExtra("contacts_number_to_send_message", contacts_number[selected_position]);
			startActivity(intent_send_message);
			break;
		case R.id.item_edit:
			Intent intent_modify = new Intent(Contacts_Screen.this, Modify_Contacts.class);
			intent_modify.putExtra("contacts_name_to_modify", contacts_name[selected_position]);
			intent_modify.putExtra("contacts_number_to_modify", contacts_number[selected_position]);
			startActivity(intent_modify);
			break;
		case R.id.item_delete:
			//Toast.makeText(this, "Delete Selected", Toast.LENGTH_SHORT).show();
			//Delete From Embedded
			deleteContact(this, contacts_number[selected_position], contacts_name[selected_position]);
			//Delete From External
			delete_contact();
			startActivity(new Intent(Contacts_Screen.this, Contacts_Screen.class));
			break;
		}
		return super.onContextItemSelected(item);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		selected_position = position;
		if(flag) {
			flag = false;
			return;
		}

		showDialog(this, "", contacts_name[position] + "\n" + contacts_number[position]);
		
		//Intent intent = new Intent(Contacts_Screen.this, Phone_Call_Screen.class);
		//intent.putExtra("contacts_number", contacts_number[position]);
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
	        	Intent intent = new Intent(Contacts_Screen.this, Phone_Call_Screen.class);
	    		intent.putExtra("contacts_number", contacts_number[selected_position]);
	    		startActivity(intent);
	       }
	    });
	    
	    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent intent_send_message = new Intent(Contacts_Screen.this, Send_Message.class);
					intent_send_message.putExtra("contacts_name_to_send_message", contacts_name[selected_position]);
					intent_send_message.putExtra("contacts_number_to_send_message", contacts_number[selected_position]);
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
		if(contacts_number[position].equals("") == true) str_temp = "\nNULL";
		return false;
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_phone_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_contacts_screen:
			//startActivity(new Intent(Contacts_Screen.this, Contacts_Screen.class));
			break;
		/*case R.id.button_incoming_call_log_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Incoming_Call_List.class));
			break;
		case R.id.button_outgoing_call_log_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Outgoing_Call_List.class));
			break;
		case R.id.button_missed_call_log_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Missed_Call_List.class));
			break;*/
		case R.id.button_embedded_contacts_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Embedded_Contact_Screen.class));
			break;
		case R.id.button_extrenal_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, External_Contact_Screen.class));
			break;
		case R.id.button_log_hosting_contacts_screen:
			startActivity(new Intent(Contacts_Screen.this, Log_Screen.class));
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.caller_and_contact_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_add_new_contact_menu:
			startActivity(new Intent(Contacts_Screen.this, Add_New_Contact.class));
			break;
		/*case R.id.item_import_phone_current_contacts_menu:
			
			break;
		case R.id.item_search_contact_menu:
			
			break;*/
		case R.id.item_search_contacts_menu:
			startActivity(new Intent(Contacts_Screen.this, Search_Contacts.class));
			break;
		case R.id.item_preferences_menu:
			startActivity(new Intent(Contacts_Screen.this, Prefs.class));
			break;
		case R.id.item_transfer_contacts_menu:
			startActivity(new Intent(Contacts_Screen.this, Transfer_Contacts.class));
			break;
		case R.id.item_about_us:
			startActivity(new Intent(Contacts_Screen.this, About_Us.class));
			break;
		case R.id.item_exit:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Delete From Database
	private void delete_contact() {
		try {
			Manage_Database db_info = new Manage_Database(Contacts_Screen.this);
			db_info.open();
			db_info.deleteContacts(contacts_name[selected_position], contacts_number[selected_position]);
			db_info.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

}