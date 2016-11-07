package com.bmshamsnahid.callerappbeta;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phone_Call_Screen extends Activity implements OnClickListener{
	
	Button b_make_call, b_contacts_hosting_phone_call_screen, b_incoming_call_log_hosting_phone_call_screen, b_outgoing_call_log_hosting_phone_call_screen, b_missed_call_log_hosting_phone_call_screen, b_log_hosting_phone_call_screen;
	EditText et_phone_number_input;
	Button b_call_hosting_phone_call_screen, b_message_hosting_phone_call_screen;
	Button b_gap_one_hosting_phone_call_screen, b_gap_two_hosting_phone_call_screen;
	Button b_phone_hosting_phone_call_screen;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_call_screen);
		
		initialize();
		et_phone_number_input.requestFocus();
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
			startActivity(new Intent(Phone_Call_Screen.this, Add_New_Contact.class));
			break;
		/*case R.id.item_import_phone_current_contacts_menu:
			
			break;
		case R.id.item_search_contact_menu:
			
			break;*/
		case R.id.item_search_contacts_menu:
			startActivity(new Intent(Phone_Call_Screen.this, Search_Contacts.class));
			break;
		case R.id.item_preferences_menu:
			startActivity(new Intent(Phone_Call_Screen.this, Prefs.class));
			break;
		case R.id.item_transfer_contacts_menu:
			startActivity(new Intent(Phone_Call_Screen.this, Transfer_Contacts.class));
			break;
		case R.id.item_about_us:
			startActivity(new Intent(Phone_Call_Screen.this, About_Us.class));
			break;
		case R.id.item_exit:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initialize() {
		
		et_phone_number_input = (EditText) findViewById(R.id.edit_text_phone_number_input);
		
		b_make_call = (Button) findViewById(R.id.button_make_call);
		b_contacts_hosting_phone_call_screen = (Button) findViewById(R.id.button_contacts_hosting_phone_call_screen);
		//b_incoming_call_log_hosting_phone_call_screen = (Button) findViewById(R.id.button_incoming_call_log_hosting_phone_call_screen);
		//b_outgoing_call_log_hosting_phone_call_screen= (Button) findViewById(R.id.button_outgoing_call_log_hosting_phone_call_screen);
		//b_missed_call_log_hosting_phone_call_screen = (Button) findViewById(R.id.button_missed_call_log_hosting_phone_call_screen);
		b_log_hosting_phone_call_screen = (Button) findViewById(R.id.button_log_hosting_phone_call_screen);
		b_call_hosting_phone_call_screen = (Button) findViewById(R.id.button_call_hosting_phone_call_screen);
		b_message_hosting_phone_call_screen = (Button) findViewById(R.id.button_message_hosting_phone_call_screen);
		b_gap_one_hosting_phone_call_screen = (Button) findViewById(R.id.button_gap_one_hosting_phone_call_screen);
		b_gap_two_hosting_phone_call_screen = (Button) findViewById(R.id.button_gap_two_hosting_phone_call_screen);
		b_phone_hosting_phone_call_screen = (Button) findViewById(R.id.button_phone_hosting_phone_call_screen);
		
		b_make_call.setOnClickListener(this);
		b_contacts_hosting_phone_call_screen.setOnClickListener(this);
		b_gap_one_hosting_phone_call_screen.setOnClickListener(this);
		b_gap_two_hosting_phone_call_screen.setOnClickListener(this);
		//b_incoming_call_log_hosting_phone_call_screen.setOnClickListener(this);
		//b_outgoing_call_log_hosting_phone_call_screen.setOnClickListener(this);
		//b_missed_call_log_hosting_phone_call_screen.setOnClickListener(this);
		b_log_hosting_phone_call_screen.setOnClickListener(this);
		b_call_hosting_phone_call_screen.setOnClickListener(this);
		b_message_hosting_phone_call_screen.setOnClickListener(this);
		b_phone_hosting_phone_call_screen.setOnClickListener(this);
	
		try {
	/////////Trying custom font/////////////////////////////////////////////////////
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			b_call_hosting_phone_call_screen.setTypeface(font_chunk_five);
			et_phone_number_input.setTypeface(font);
			b_message_hosting_phone_call_screen.setTypeface(font_chunk_five);
			b_gap_one_hosting_phone_call_screen.setTypeface(font_chunk_five);
			b_gap_two_hosting_phone_call_screen.setTypeface(font_chunk_five);
			b_make_call.setTypeface(font_chunk_five);
			b_phone_hosting_phone_call_screen.setTypeface(font_pink_t_shirt);
			b_contacts_hosting_phone_call_screen.setTypeface(font_rough_simple);
			b_log_hosting_phone_call_screen.setTypeface(font_rough_simple);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		
		try {
			Bundle bd_contact_number = getIntent().getExtras();
			if((bd_contact_number.getString("contacts_number")).equals("") == false) {
				et_phone_number_input.setText(bd_contact_number.getString("contacts_number"));
			}
		} catch(Exception e) {
			
		}
		
		//preference_initialize();
	}
	
	private void preference_initialize() {
		SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String values = getData.getString("list", "1");
		Toast.makeText(this, values, Toast.LENGTH_LONG).show();
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button_make_call:
			String phone_number = et_phone_number_input.getText().toString();
			make_call(phone_number);
			break;
		case R.id.button_contacts_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Contacts_Screen.class));
			break;
		/*case R.id.button_incoming_call_log_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Incoming_Call_List.class));
			break;
		case R.id.button_outgoing_call_log_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Outgoing_Call_List.class));
			break;
		case R.id.button_missed_call_log_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Missed_Call_List.class));
			break;*/
		case R.id.button_log_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Log_Screen.class));
			break;
		case R.id.button_call_hosting_phone_call_screen:
			break;
		case R.id.button_message_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Send_Message.class));
			break;
		case R.id.button_gap_one_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Contacts_Screen.class));
			break;
		case R.id.button_gap_two_hosting_phone_call_screen:
			startActivity(new Intent(Phone_Call_Screen.this, Log_Screen.class));
			break;
		case R.id.button_phone_hosting_phone_call_screen:
			break;
		}
	}
	
	private void make_call(String phone_number) {
		
		try {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phone_number));
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
}
