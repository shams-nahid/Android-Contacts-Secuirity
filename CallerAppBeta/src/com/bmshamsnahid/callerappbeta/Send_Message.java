package com.bmshamsnahid.callerappbeta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Send_Message extends Activity implements OnClickListener{

	Button bt_send_message, b_contacts_hosting_send_message, b_phone_hosting_send_message, b_log_hosting_send_message;
	EditText et_number_send_message, et_body_send_message;
	Button b_call_hosting_send_message, b_message_hosting_send_message;
	TextView tv_to_hosting_send_message, tv_message_body_hosting_send_message;
	boolean flag = false;
	String name = "", number = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);
		
		initialize();
		
	}

	private void initialize() {
		edittext_initialize();
		button_initialize();
		text_view_initialize();
		font_setting();
	}

	private void font_setting() {
		try {	
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			b_message_hosting_send_message.setTypeface(font_chunk_five);					
			et_number_send_message.setTypeface(font);
			tv_to_hosting_send_message.setTypeface(font_chunk_five);
			tv_message_body_hosting_send_message.setTypeface(font_chunk_five);
			et_body_send_message.setTypeface(font_chunk_five);
			bt_send_message.setTypeface(font_chunk_five);
			b_call_hosting_send_message.setTypeface(font_chunk_five);
			b_phone_hosting_send_message.setTypeface(font_pink_t_shirt);
			b_contacts_hosting_send_message.setTypeface(font_rough_simple);
			b_log_hosting_send_message.setTypeface(font_rough_simple);
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void text_view_initialize() {
		tv_to_hosting_send_message = (TextView) findViewById(R.id.text_view_to_hosting_send_message);
		tv_message_body_hosting_send_message = (TextView) findViewById(R.id.text_view_message_body_hosting_send_message);
	}

	private void button_initialize() {
		bt_send_message = (Button) findViewById(R.id.button_send_message);
		b_contacts_hosting_send_message = (Button) findViewById(R.id.button_contacts_hosting_send_message);
		b_phone_hosting_send_message = (Button) findViewById(R.id.button_phone_hosting_send_message);
		b_log_hosting_send_message = (Button) findViewById(R.id.button_log_hosting_send_message);
		b_call_hosting_send_message = (Button) findViewById(R.id.button_call_hosting_send_message);
		b_message_hosting_send_message = (Button) findViewById(R.id.button_message_hosting_send_message);
		
		bt_send_message.setOnClickListener(this);
		b_contacts_hosting_send_message.setOnClickListener(this);
		b_phone_hosting_send_message.setOnClickListener(this);
		b_log_hosting_send_message.setOnClickListener(this);
		b_call_hosting_send_message.setOnClickListener(this);
		b_message_hosting_send_message.setOnClickListener(this);
	}

	private void edittext_initialize() {
		et_number_send_message = (EditText) findViewById(R.id.edit_text_number_send_message);
		et_body_send_message = (EditText) findViewById(R.id.edit_text_body_send_message);
		
		try {
			Bundle bd_send_message = getIntent().getExtras();
			name = bd_send_message.getString("contacts_name_to_send_message");
			number = bd_send_message.getString("contacts_number_to_send_message");
			et_number_send_message.setText(number);
			flag = true;
		} catch (Exception e) {
			flag = false;
			Log.d("Bundel send message", "Can not retrive name or number or both");
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_send_message:
			
			try {
				//String[] name_to_message = et_number_send_message.getText().toString().split("[");
				send_message(et_number_send_message.getText().toString(), et_body_send_message.getText().toString());

			} catch(Exception e) {
				Log.d("Send Message", e.toString());
			}
			break;
		case R.id.button_contacts_hosting_send_message:
			startActivity(new Intent(Send_Message.this, Contacts_Screen.class));
			break;
		case R.id.button_phone_hosting_send_message:
			startActivity(new Intent(Send_Message.this, Phone_Call_Screen.class));
			break;
		case R.id.button_log_hosting_send_message:
			startActivity(new Intent(Send_Message.this, Log_Screen.class));
			break;
		case R.id.button_call_hosting_send_message:
			startActivity(new Intent(Send_Message.this, Phone_Call_Screen.class));
			break;
		case R.id.button_message_hosting_send_message:
			break;
		}
	}

	private void send_message(String message_number, String message_body) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(message_number, null, message_body, null, null);
		} catch (Exception e) {
			Log.d("In Send Message", e.toString());
		}
	}
	
}
