package com.bmshamsnahid.callerappbeta;

import java.sql.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Log_Screen extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener{

	ListView list_logging_screen;
	boolean flag;
	String[] logging_screen_name;
	String[] logging_screen_number;
	String[] logging_screen_time;
	String[] logging_screen_duration;
	String[] logging_screen_type;
	String[] external_contacts_name_str;
	String[] external_contacts_number_str;
	
	String[] logging_screen_name_temp = new String[3000];
	String[] logging_screen_number_temp = new String[3000];
	String[] logging_screen_time_temp = new String[3000];
	String[] logging_screen_duration_temp = new String[3000];
	String[] logging_screen_type_temp = new String[3000];
	int index = 0;
	int selected_position = 0;
	
	
	Button b_phone_hosting_log_screen, b_contacts_hosting_log_screen, b_incoming_call_log_hosting_log_screen, b_outgoing_call_log_hosting_log_screen, b_missed_call_log_hosting_log_screen, b_log_hosting_log_screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_screen);
		
		initialize();
		
		getCallDetails();
		
		show_all_call_log();
		
		menu_setup();
	}
	
	private void show_all_call_log() {
		logging_screen_name = new String[index];
		logging_screen_number = new String[index];
		logging_screen_time = new String[index];
		logging_screen_duration = new String[index];
		logging_screen_type = new String[index];
		
		for(int i=0; i<index; i++) {
			logging_screen_name[i] = logging_screen_name_temp[i];
			logging_screen_number[i] = logging_screen_number_temp[i];
			logging_screen_time[i] = logging_screen_time_temp[i];
			logging_screen_duration[i] = logging_screen_duration_temp[i];
			logging_screen_type[i] = logging_screen_type_temp[i];
		}
		
		try {
			set_external_contact();
			Custom_List_Adapter_For_Logging_Screen adapter_logging_screen = new Custom_List_Adapter_For_Logging_Screen(this, logging_screen_name, logging_screen_number, logging_screen_time, logging_screen_duration, logging_screen_type, external_contacts_name_str, external_contacts_number_str);
			list_logging_screen = (ListView) findViewById(R.id.list_view_log_screen);
			list_logging_screen.setAdapter(adapter_logging_screen);
		} catch(Exception e) {
			Log.d("Exception", e.toString());
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void getCallDetails() {

		StringBuffer sb = new StringBuffer();
		
		Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
		
		int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER ); 
		int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
		int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
		int name = managedCursor.getColumnIndex( CallLog.Calls.CACHED_NAME);
		//sb.append( "Call Details :");
		
		while ( managedCursor.moveToNext() ) {
			String phNumber = managedCursor.getString( number );
			String callType = managedCursor.getString( type );
			String callDate = managedCursor.getString( date );
			Date callDayTime = new Date(Long.valueOf(callDate));		
			String callDuration = managedCursor.getString( duration );
			String Name = managedCursor.getString(name);
			
			String dir = null;
			
			int dircode = Integer.parseInt( callType );
			
			switch( dircode ) {
				case CallLog.Calls.OUTGOING_TYPE:
					dir = "OUTGOING";
					break;
		
				case CallLog.Calls.INCOMING_TYPE:
					dir = "INCOMING";
				break;
		
				case CallLog.Calls.MISSED_TYPE:
					dir = "MISSED";
				break;
			}
			
			logging_screen_name_temp[index] =  Name;
			logging_screen_name_temp[index] =  Name;
			logging_screen_number_temp[index] =  phNumber;
			logging_screen_time_temp[index] =  callDayTime.toString();
			logging_screen_duration_temp[index] =  callDuration;
			logging_screen_type_temp[index] = dir;
			index++;
		}
		
			managedCursor.close();
	}
	
	private void menu_setup() {
		list_logging_screen.setOnItemClickListener(this);
		list_logging_screen.setOnItemLongClickListener(this);
		registerForContextMenu(list_logging_screen);
		flag = false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.list_view_log_screen) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.call_log_menu, menu);
		}
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		selected_position = position;
		if(flag) {
			flag = false;
			return;
		}
		
		try {
			//String str = "";
			//str = logging_screen_name[position];
			//if(logging_screen_name[position].equals("null") == true) showDialog(this, "", "Unknown\n" + logging_screen_number[position]);
			//else 
				showDialog(this, "", logging_screen_name[position] + "\n" + logging_screen_number[position]);
			//showDialog(this, "", str + "\n" + logging_screen_number[position]);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
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
	        	Intent intent = new Intent(Log_Screen.this, Phone_Call_Screen.class);
	    		intent.putExtra("contacts_number", logging_screen_number[selected_position]);
	    		startActivity(intent);
	       }
	    });
	    
	    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent intent_send_message = new Intent(Log_Screen.this, Send_Message.class);
					intent_send_message.putExtra("contacts_name_to_send_message", logging_screen_name[selected_position]);
					intent_send_message.putExtra("contacts_number_to_send_message", logging_screen_number[selected_position]);
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
		if(logging_screen_number[position].equals("") == true) str_temp = "\nNULL";
		return false;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_call_call_log_menu:
			//Toast.makeText(this, "Call Selected", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Log_Screen.this, Phone_Call_Screen.class);
			intent.putExtra("contacts_number", logging_screen_number[selected_position]);
			startActivity(intent);
			break;
		case R.id.item_message_call_log_menu:
			//Toast.makeText(this, "Message Selected", Toast.LENGTH_SHORT).show();
			Intent intent_send_message = new Intent(Log_Screen.this, Send_Message.class);
			intent_send_message.putExtra("contacts_name_to_send_message", logging_screen_name[selected_position]);
			intent_send_message.putExtra("contacts_number_to_send_message", logging_screen_number[selected_position]);
			startActivity(intent_send_message);
			break;
		case R.id.item_save_as_call_log_menu:
			Intent intent_modify = new Intent(Log_Screen.this, Modify_Contacts.class);
			intent_modify.putExtra("contacts_name_to_modify", logging_screen_name[selected_position]);
			intent_modify.putExtra("contacts_number_to_modify", logging_screen_number[selected_position]);
			startActivity(intent_modify);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	

	private void initialize() {
		button_initialize();
		font_initializing();
	}

	private void font_initializing() {
		try {
			/////////Trying custom font/////////////////////////////////////////////////////
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			b_phone_hosting_log_screen.setTypeface(font_rough_simple);
			b_contacts_hosting_log_screen.setTypeface(font_rough_simple);
			b_log_hosting_log_screen.setTypeface(font_pink_t_shirt);
			
			b_incoming_call_log_hosting_log_screen.setTypeface(font_chunk_five);
			b_outgoing_call_log_hosting_log_screen.setTypeface(font_chunk_five);
			b_missed_call_log_hosting_log_screen.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void button_initialize() {
		b_phone_hosting_log_screen = (Button) findViewById(R.id.button_phone_hosting_log_screen);
		b_contacts_hosting_log_screen = (Button) findViewById(R.id.button_contacts_hosting_log_screen);
		b_incoming_call_log_hosting_log_screen = (Button) findViewById(R.id.button_incoming_call_log_hosting_log_screen);
		b_outgoing_call_log_hosting_log_screen= (Button) findViewById(R.id.button_outgoing_call_log_hosting_log_screen);
		b_missed_call_log_hosting_log_screen = (Button) findViewById(R.id.button_missed_call_log_hosting_log_screen);
		b_log_hosting_log_screen = (Button) findViewById(R.id.button_log_hosting_log_screen);
		
		b_phone_hosting_log_screen.setOnClickListener(this);
		b_contacts_hosting_log_screen.setOnClickListener(this);
		b_incoming_call_log_hosting_log_screen.setOnClickListener(this);
		b_outgoing_call_log_hosting_log_screen.setOnClickListener(this);
		b_missed_call_log_hosting_log_screen.setOnClickListener(this);
		b_log_hosting_log_screen.setOnClickListener(this);
	}
	
public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button_phone_hosting_log_screen:
			startActivity(new Intent(Log_Screen.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_log_screen:
			startActivity(new Intent(Log_Screen.this, Contacts_Screen.class));
			break;
		case R.id.button_incoming_call_log_hosting_log_screen:
			startActivity(new Intent(Log_Screen.this, Incoming_Call_List.class));
			break;
		case R.id.button_outgoing_call_log_hosting_log_screen:
			startActivity(new Intent(Log_Screen.this, Outgoing_Call_List.class));
			break;
		case R.id.button_missed_call_log_hosting_log_screen:
			startActivity(new Intent(Log_Screen.this, Missed_Call_List.class));
			break;
		case R.id.button_log_hosting_log_screen:
			//startActivity(new Intent(Log_Screen.this, Log_Screen.class));
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
			startActivity(new Intent(Log_Screen.this, Add_New_Contact.class));
			break;
		/*case R.id.item_import_phone_current_contacts_menu:
			
			break;
		case R.id.item_search_contact_menu:
			
			break;*/
		case R.id.item_search_contacts_menu:
			startActivity(new Intent(Log_Screen.this, Search_Contacts.class));
			break;
		case R.id.item_preferences_menu:
			startActivity(new Intent(Log_Screen.this, Prefs.class));
			break;
		case R.id.item_transfer_contacts_menu:
			startActivity(new Intent(Log_Screen.this, Transfer_Contacts.class));
			break;
		case R.id.item_about_us:
			startActivity(new Intent(Log_Screen.this, About_Us.class));
			break;
			
		case R.id.item_exit:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	public void set_external_contact() {
		String str_name = "";
		String str_number = "";
		
		try {
			Manage_Database info = new Manage_Database(Log_Screen.this);
			info.open();
			
			str_name = info.getName();
			str_number = info.getNumber();
			
			info.close();
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		} finally {
			external_contacts_name_str = str_name.split("--");
			external_contacts_number_str = str_number.split("--");
		}
	}
}
