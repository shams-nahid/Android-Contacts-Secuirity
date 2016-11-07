package com.bmshamsnahid.callerappbeta;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class External_Contact_Screen extends Activity implements OnItemClickListener, OnItemLongClickListener, OnClickListener {
	
	ListView list_hosting_external_contacts;
	String[] contacts_name, contacts_number;
	
	
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
	
	Button b_phones_hosting_external_contacts_screen, b_contacts_hosting_external_contacts_screen;
	Button b_embedded_hosting_external_contacts_screen, b_external_hosting_external_contacts_screen;
	Button b_log_hosting_external_contacts_screen;
	
	ArrayList < ContentProviderOperation > ops;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.external_contact_screen);
		
		
		
		ops = new ArrayList < ContentProviderOperation > ();

		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());
		
		
		b_phones_hosting_external_contacts_screen = (Button) findViewById(R.id.button_phone_hosting_external_contacts_screen);
		b_phones_hosting_external_contacts_screen.setOnClickListener(this);
		b_contacts_hosting_external_contacts_screen = (Button) findViewById(R.id.button_contacts_hosting_external_contacts_screen);
		b_contacts_hosting_external_contacts_screen.setOnClickListener(this);
		//b_incoming_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_incoming_call_log_hosting_contacts_screen);
		//b_incoming_call_log_hosting_contacts_screen.setOnClickListener(this);
		//b_outgoing_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_outgoing_call_log_hosting_contacts_screen);
		//b_outgoing_call_log_hosting_contacts_screen.setOnClickListener(this);
		//b_missed_call_log_hosting_contacts_screen = (Button) findViewById(R.id.button_missed_call_log_hosting_contacts_screen);
		//b_missed_call_log_hosting_contacts_screen.setOnClickListener(this);
		b_embedded_hosting_external_contacts_screen = (Button) findViewById(R.id.button_embedded_contacts_hosting_external_contacts_screen);
		b_embedded_hosting_external_contacts_screen.setOnClickListener(this);
		b_external_hosting_external_contacts_screen = (Button) findViewById(R.id.button_extrenal_hosting_external_contacts_screen);
		b_external_hosting_external_contacts_screen.setOnClickListener(this);
		b_log_hosting_external_contacts_screen = (Button) findViewById(R.id.button_log_hosting_external_contacts_screen);
		b_log_hosting_external_contacts_screen.setOnClickListener(this);
		
		font_initializing();
		
		String[] contacts_name_temp = new String[3000];
		String[] contacts_number_temp = new String[3000];
		int index = 0;
		
		///////////////////////////////////////Retriving contact////////////////////////
		String name_temp = "";
		String number_temp = "";
		
		try {
			Manage_Database info = new Manage_Database(External_Contact_Screen.this);
			info.open();
			name_temp = info.getName();
			number_temp = info.getNumber();
			info.close();
			contacts_name = name_temp.split("--");
	        contacts_number = number_temp.split("--");
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
        
        
        ////////////////////////////////////////////////////////////////////////////////
        
        /*for(int i=0; i<index; i++) {
        	contacts_name[i] = contacts_name_temp[i];
        	contacts_number[i] = contacts_number_temp[i];
        }*/
        
        for(int i=0; i<contacts_name.length; i++) {
        	for(int j=i+1; j<contacts_name.length; j++) {
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
		
		CustomListAdapterForExternalContacts adapter = new CustomListAdapterForExternalContacts(this, contacts_name, imgid, contacts_number);
		list_hosting_external_contacts = (ListView) findViewById(R.id.list_external_contacts);
		list_hosting_external_contacts.setAdapter(adapter);
		
		list_hosting_external_contacts.setOnItemClickListener(this);
		list_hosting_external_contacts.setOnItemLongClickListener(this);
		
		registerForContextMenu(list_hosting_external_contacts);
		
		flag = false;
	}
	
	private void font_initializing() {
		try {	
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_contacts_hosting_external_contacts_screen.setTypeface(font_pink_t_shirt);
			b_external_hosting_external_contacts_screen.setTypeface(font_chunk_five);
			b_embedded_hosting_external_contacts_screen.setTypeface(font_chunk_five);
			b_phones_hosting_external_contacts_screen.setTypeface(font_rough_simple);
			b_log_hosting_external_contacts_screen.setTypeface(font_rough_simple);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.list_external_contacts) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.external_contacts_menu, menu);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_call_external_contacts_menu:
			Intent intent = new Intent(External_Contact_Screen.this, Phone_Call_Screen.class);
			intent.putExtra("contacts_number", contacts_number[selected_position]);
			startActivity(intent);
			break;
		case R.id.item_message_external_contacts_menu:
			Intent intent_send_message = new Intent(External_Contact_Screen.this, Send_Message.class);
			intent_send_message.putExtra("contacts_name_to_send_message", contacts_name[selected_position]);
			intent_send_message.putExtra("contacts_number_to_send_message", contacts_number[selected_position]);
			startActivity(intent_send_message);
			break;
		case R.id.item_edit_external_contacts_menu:
			Intent intent_modify = new Intent(External_Contact_Screen.this, Modify_Contacts.class);
			intent_modify.putExtra("contacts_name_to_modify", contacts_name[selected_position]);
			intent_modify.putExtra("contacts_number_to_modify", contacts_number[selected_position]);
			startActivity(intent_modify);
			break;
		case R.id.item_move_to_embedded_list_external_contacts_menu:
			try {
				create_a_contact(contacts_name[selected_position], contacts_number[selected_position]);
				delete_contact();
				startActivity(new Intent(External_Contact_Screen.this, External_Contact_Screen.class));
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item_copy_to_embedded_list_external_contacts_menu:
			try {
				create_a_contact(contacts_name[selected_position], contacts_number[selected_position]);
				//delete_contact();
				startActivity(new Intent(External_Contact_Screen.this, External_Contact_Screen.class));
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item_delete:
			
			//deleteContact(this, contacts_number[selected_position], contacts_name[selected_position]);
			delete_contact();
			startActivity(new Intent(External_Contact_Screen.this, External_Contact_Screen.class));
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void delete_contact() {
		try {
			Manage_Database db_info = new Manage_Database(External_Contact_Screen.this);
			db_info.open();
			db_info.deleteContacts(contacts_name[selected_position], contacts_number[selected_position]);
			db_info.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		selected_position = position;
		if(flag) {
			flag = false;
			return;
		}
		
		showDialog(this, "", contacts_name[position] + "\n" + contacts_number[position]);
		
		//Intent intent = new Intent(External_Contact_Screen.this, Phone_Call_Screen.class);
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
	        	Intent intent = new Intent(External_Contact_Screen.this, Phone_Call_Screen.class);
	    		intent.putExtra("contacts_number", contacts_number[selected_position]);
	    		startActivity(intent);
	       }
	    });
	    
	    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent intent_send_message = new Intent(External_Contact_Screen.this, Send_Message.class);
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
		case R.id.button_phone_hosting_external_contacts_screen:
			startActivity(new Intent(External_Contact_Screen.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_external_contacts_screen:
			startActivity(new Intent(External_Contact_Screen.this, Contacts_Screen.class));
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
		case R.id.button_embedded_contacts_hosting_external_contacts_screen:
			startActivity(new Intent(External_Contact_Screen.this, Embedded_Contact_Screen.class));
			break;
		case R.id.button_extrenal_hosting_external_contacts_screen:
			break;
		case R.id.button_log_hosting_external_contacts_screen:
			startActivity(new Intent(External_Contact_Screen.this, Log_Screen.class));
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.caller_and_contact_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_add_new_contact_menu:
			startActivity(new Intent(External_Contact_Screen.this, Add_New_Contact.class));
			break;
		/*case R.id.item_import_phone_current_contacts_menu:
			
			break;
		case R.id.item_search_contact_menu:
			
			break;*/
		case R.id.item_preferences_menu:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void create_a_contact(String str_persons_name, String str_persons_pnone_number) {
		if(str_persons_name != null) {
			ops.add(ContentProviderOperation.newInsert(
				     ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, str_persons_name)
				     .build());
		}
		
		if(str_persons_pnone_number != null) {
			ops.add(ContentProviderOperation.
				     newInsert(ContactsContract.Data.CONTENT_URI)
				     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				     .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, str_persons_pnone_number)
				     .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				     .build());
		}
		
		try {
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		 } catch (Exception e) {
		     e.printStackTrace();
		     Toast.makeText(External_Contact_Screen.this, "Sorry!!! An Internal Error Occours\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
		 }
	}

}

