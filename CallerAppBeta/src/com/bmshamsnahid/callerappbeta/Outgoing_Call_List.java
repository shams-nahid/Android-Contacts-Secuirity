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
import android.view.ContextMenu;
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
import android.widget.TextView;
import android.widget.Toast;

public class Outgoing_Call_List extends Activity implements OnItemClickListener, OnItemLongClickListener, OnClickListener{

	ListView list_outgoing_call_log;
	boolean flag;
	String[] outgoing_call_log_name;
	String[] outgoing_call_log_number;
	String[] outgoing_call_log_time;
	String[] outgoing_call_log_duration;
	String[] external_contacts_name_str;
	String[] external_contacts_number_str;
	
	String[] outgoing_call_log_name_temp = new String[3000];
	String[] outgoing_call_log_number_temp = new String[3000];
	String[] outgoing_call_log_time_temp = new String[3000];
	String[] outgoing_call_log_duration_temp = new String[3000];
	int index = 0;
	int selected_position = 0;
	
	Button b_phone_hosting_outgoing_call_log, b_contacts_hosting_outgoing_call_log;
	Button b_incoming_call_log_hosting_outgoing_call_log, b_outgoing_call_log_hosting_outgoing_call_log, b_missed_call_log_hosting_outgoing_call_log, b_log_hosting_outgoing_call_log;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outgoing_call_screen);
		
		initialize();
		
		getCallDetails();
		
		show_outgoing_call_log();
		
		menu_setup();
	}
	
	private void show_outgoing_call_log() {
		outgoing_call_log_name = new String[index];
		outgoing_call_log_number = new String[index];
		outgoing_call_log_time = new String[index];
		outgoing_call_log_duration = new String[index];
		
		for(int i=0; i<index; i++) {
			outgoing_call_log_name[i] = outgoing_call_log_name_temp[i];
			outgoing_call_log_number[i] = outgoing_call_log_number_temp[i];
			outgoing_call_log_time[i] = outgoing_call_log_time_temp[i];
			outgoing_call_log_duration[i] = outgoing_call_log_duration_temp[i];
		}
		set_external_contact();
		Custom_List_Adapter_For_Outgoing_CAll adapter_outgoing_call = new Custom_List_Adapter_For_Outgoing_CAll(this, outgoing_call_log_name, outgoing_call_log_number, outgoing_call_log_time, outgoing_call_log_duration, external_contacts_name_str, external_contacts_number_str);
		list_outgoing_call_log = (ListView) findViewById(R.id.list_view_outgoing_call_log);
		list_outgoing_call_log.setAdapter(adapter_outgoing_call);
		
		
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
					sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
					sb.append("\n----------------------------------");
					
					outgoing_call_log_name_temp[index] =  Name;
					outgoing_call_log_name_temp[index] =  Name;
					outgoing_call_log_number_temp[index] =  phNumber;
					outgoing_call_log_time_temp[index] =  callDayTime.toString();
					outgoing_call_log_duration_temp[index] =  callDuration;
					index++;
					break;
		
				case CallLog.Calls.INCOMING_TYPE:
				dir = "INCOMING";
				break;
		
				case CallLog.Calls.MISSED_TYPE:
				dir = "MISSED";
				break;
			}
			//if(dir.equals("OUTGOING") == true)
			//{
				//sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
				//sb.append("\n----------------------------------");
			//}
		}
		
			managedCursor.close();
	}
	
	private void menu_setup() {
		list_outgoing_call_log.setOnItemClickListener(this);
		list_outgoing_call_log.setOnItemLongClickListener(this);
		registerForContextMenu(list_outgoing_call_log);
		flag = false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.list_view_outgoing_call_log) {
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
		
		showDialog(this, "", outgoing_call_log_name[position] + "\n" + outgoing_call_log_number[position]);
		
		//Intent intent = new Intent(Outgoing_Call_List.this, Phone_Call_Screen.class);
		//intent.putExtra("contacts_number", outgoing_call_log_number[position]);
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
	        	Intent intent = new Intent(Outgoing_Call_List.this, Phone_Call_Screen.class);
	    		intent.putExtra("contacts_number", outgoing_call_log_number[selected_position]);
	    		startActivity(intent);
	       }
	    });
	    
	    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent intent_send_message = new Intent(Outgoing_Call_List.this, Send_Message.class);
					intent_send_message.putExtra("contacts_name_to_send_message", outgoing_call_log_name[selected_position]);
					intent_send_message.putExtra("contacts_number_to_send_message", outgoing_call_log_number[selected_position]);
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
		if(outgoing_call_log_number[position].equals("") == true) str_temp = "\nNULL";
		return false;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_call_call_log_menu:
			//Toast.makeText(this, "Call Selected", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Outgoing_Call_List.this, Phone_Call_Screen.class);
			intent.putExtra("contacts_number", outgoing_call_log_number[selected_position]);
			startActivity(intent);
			break;
		case R.id.item_message_call_log_menu:
			//Toast.makeText(this, "Message Selected", Toast.LENGTH_SHORT).show();
			Intent intent_send_message = new Intent(Outgoing_Call_List.this, Send_Message.class);
			intent_send_message.putExtra("contacts_name_to_send_message", outgoing_call_log_name[selected_position]);
			intent_send_message.putExtra("contacts_number_to_send_message", outgoing_call_log_number[selected_position]);
			startActivity(intent_send_message);
			break;
			
		case R.id.item_save_as_call_log_menu:
			Intent intent_modify = new Intent(Outgoing_Call_List.this, Modify_Contacts.class);
			intent_modify.putExtra("contacts_name_to_modify", outgoing_call_log_name[selected_position]);
			intent_modify.putExtra("contacts_number_to_modify", outgoing_call_log_number[selected_position]);
			startActivity(intent_modify);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void initialize() {
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
			
			b_phone_hosting_outgoing_call_log.setTypeface(font_rough_simple);
			b_contacts_hosting_outgoing_call_log.setTypeface(font_rough_simple);
			b_log_hosting_outgoing_call_log.setTypeface(font_pink_t_shirt);
			
			b_incoming_call_log_hosting_outgoing_call_log.setTypeface(font_chunk_five);
			b_outgoing_call_log_hosting_outgoing_call_log.setTypeface(font_chunk_five);
			b_missed_call_log_hosting_outgoing_call_log.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void button_initialize() {
		b_phone_hosting_outgoing_call_log = (Button) findViewById(R.id.button_phone_hosting_outgoing_call_log);
		b_contacts_hosting_outgoing_call_log = (Button) findViewById(R.id.button_contacts_hosting_outgoing_call_log);
		b_incoming_call_log_hosting_outgoing_call_log = (Button) findViewById(R.id.button_incoming_call_log_hosting_outgoing_call_log);
		b_outgoing_call_log_hosting_outgoing_call_log = (Button) findViewById(R.id.button_outgoing_call_log_hosting_outgoing_call_log);
		b_missed_call_log_hosting_outgoing_call_log = (Button) findViewById(R.id.button_missed_call_log_hosting_outgoing_call_log);
		b_log_hosting_outgoing_call_log = (Button) findViewById(R.id.button_log_hosting_outgoing_call_log);
		
		b_phone_hosting_outgoing_call_log.setOnClickListener(this);
		b_contacts_hosting_outgoing_call_log.setOnClickListener(this);
		b_incoming_call_log_hosting_outgoing_call_log.setOnClickListener(this);
		b_outgoing_call_log_hosting_outgoing_call_log.setOnClickListener(this);
		b_missed_call_log_hosting_outgoing_call_log.setOnClickListener(this);
		b_log_hosting_outgoing_call_log.setOnClickListener(this);
	}



	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_phone_hosting_outgoing_call_log:
			startActivity(new Intent(Outgoing_Call_List.this, Phone_Call_Screen.class));
			break;
		case R.id.button_contacts_hosting_outgoing_call_log:
			startActivity(new Intent(Outgoing_Call_List.this, Contacts_Screen.class));
			break;
		case R.id.button_incoming_call_log_hosting_outgoing_call_log:
			startActivity(new Intent(Outgoing_Call_List.this, Incoming_Call_List.class));
			break;
		case R.id.button_outgoing_call_log_hosting_outgoing_call_log:
			
			break;
		case R.id.button_missed_call_log_hosting_outgoing_call_log:
			startActivity(new Intent(Outgoing_Call_List.this, Missed_Call_List.class));
			break;
		case R.id.button_log_hosting_outgoing_call_log:
			startActivity(new Intent(Outgoing_Call_List.this, Log_Screen.class));
			break;
		}
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
			Manage_Database info = new Manage_Database(Outgoing_Call_List.this);
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
