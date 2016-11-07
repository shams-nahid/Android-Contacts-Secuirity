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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Transfer_Contacts extends Activity implements OnClickListener{
	
	Button  b_move_all_external_to_embedded,
			b_move_single_external_to_embedded,
			b_copy_all_external_to_embedded,
			b_copy_single_external_to_embedded,
			b_move_all_embedded_to_external,
			b_move_single_embedded_to_external,
			b_copy_all_embedded_to_external,
			b_copy_single_embedded_to_exteranal;
	
	String embedded_contacts_name_str, embedded_contacts_number_str, external_contacts_name_str, external_contacts_number_str;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfer_contacts);
		
		initialize();
	}

	private void initialize() {
		button_initialize();
		contacts_initialize();
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
			
			
			b_move_all_external_to_embedded.setTypeface(font_chunk_five);
			b_move_single_external_to_embedded.setTypeface(font_chunk_five);
			b_copy_all_external_to_embedded.setTypeface(font_chunk_five);
			b_copy_single_external_to_embedded.setTypeface(font_chunk_five);
			b_move_all_embedded_to_external.setTypeface(font_chunk_five);
			b_move_single_embedded_to_external.setTypeface(font_chunk_five);
			b_copy_all_embedded_to_external.setTypeface(font_chunk_five);
			b_copy_single_embedded_to_exteranal.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void contacts_initialize() {
		set_embedded_contact();
		set_external_contact();
	}

	private void button_initialize() {
		b_move_all_external_to_embedded = (Button) findViewById(R.id.button_move_all_external_to_embedded_transfer_contacts);
		b_move_single_external_to_embedded = (Button) findViewById(R.id.button_move_single_external_to_embedded_transfer_contacts);
		b_copy_all_external_to_embedded = (Button) findViewById(R.id.button_copy_all_external_to_embedded_transfer_contacts);
		b_copy_single_external_to_embedded = (Button) findViewById(R.id.button_copy_single_external_to_embedded_transfer_contacts);
		
		b_move_all_embedded_to_external = (Button) findViewById(R.id.button_move_all_embedded_to_external_transfer_contacts);
		b_move_single_embedded_to_external = (Button) findViewById(R.id.button_move_single_embedded_to_external_transfer_contacts);
		b_copy_all_embedded_to_external = (Button) findViewById(R.id.button_copy_all_embedded_to_external_transfer_contacts);
		b_copy_single_embedded_to_exteranal = (Button) findViewById(R.id.button_copy_single_embedded_to_external_transfer_contacts);
		
		b_move_all_external_to_embedded.setOnClickListener(this);
		b_move_single_external_to_embedded.setOnClickListener(this);
		b_copy_all_external_to_embedded.setOnClickListener(this);
		b_copy_single_external_to_embedded.setOnClickListener(this);
		
		b_move_all_embedded_to_external.setOnClickListener(this);
		b_move_single_embedded_to_external.setOnClickListener(this);
		b_copy_all_embedded_to_external.setOnClickListener(this);
		b_copy_single_embedded_to_exteranal.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_move_all_external_to_embedded_transfer_contacts:
			try {
				show_dialog_move_all_external_to_embedded(this, "This may take a while.", "This process will erase your external storage contacts and move them to embedded storage.\nAre You Sure?");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_move_single_external_to_embedded_transfer_contacts:
			try {
				show_dialog_to_message(this, "", "Move A Single Contact From External Storage:\n\n1. Go to CONTACTS->EXTERNAL\n2. Long press on your desired contact and select move to embedded storage.");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_copy_all_external_to_embedded_transfer_contacts:
			try {
				show_dialog_copy_all_external_to_embedded(this, "This may take a while.", "This process will copy your external storage contacts and move them to embedded storage.\nAre You Sure?");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_copy_single_external_to_embedded_transfer_contacts:
			try {
				show_dialog_to_message(this, "", "Copy A Single Contact From External Storage:\n\n1. Go to CONTACTS->EXTERNAL\n2. Long press on your desired contact and select copy to embedded storage.");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_move_all_embedded_to_external_transfer_contacts:
			try {
				show_dialog_move_all_embedded_to_external(this, "This may take a while.", "This process will erase your embedded storage contacts and move them to external storage.\nAre You Sure?");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_move_single_embedded_to_external_transfer_contacts:
			try {
				show_dialog_to_message(this, "", "Move A Single Contact From Embedded Storage:\n\n1. Go to CONTACTS->EXTERNAL\n2. Long press on your desired contact and select move to external storage.");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_copy_all_embedded_to_external_transfer_contacts:	
			try {	
				show_dialog_copy_all_embedded_to_external(this, "This may take a while.", "This process will copy your embedded storage contacts and move them to external storage.\nAre You Sure?");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_copy_single_embedded_to_external_transfer_contacts:
			try {
				show_dialog_to_message(this, "", "Copy A Single Contact From Embedded Storage:\n\n1. Go to CONTACTS->EXTERNAL\n2. Long press on your desired contact and select copy to external storage.");
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
	
	public void show_dialog_to_message(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    //if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.show();
	}
	
	public void show_dialog_copy_all_embedded_to_external(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   copy_all_embedded_entry_to_external();
	        	   startActivity(new Intent(Transfer_Contacts.this, Contacts_Screen.class));
	           }
	    });
	    
	    builder.show();
	}
	
	public void show_dialog_move_all_embedded_to_external(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   copy_all_embedded_entry_to_external();
					delete_all_embedded_contact();
					startActivity(new Intent(Transfer_Contacts.this, Contacts_Screen.class));
	           }
	    });
	    
	    builder.show();
	}
	
	
	
	public void show_dialog_copy_all_external_to_embedded(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   copy_all_external_entry_to_embedded();
					startActivity(new Intent(Transfer_Contacts.this, Contacts_Screen.class));
	           }
	    });
	    
	    builder.show();
	}
	
		
	public void show_dialog_move_all_external_to_embedded(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

	    if (title != null) builder.setTitle(title);

	    builder.setMessage(message);
	    
	    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	    		dialog.cancel();
	       }
	    });
	    
	    builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   copy_all_external_entry_to_embedded();
					delete_all_external_entry();
					startActivity(new Intent(Transfer_Contacts.this, Contacts_Screen.class));
	           }
	    });
	    
	    builder.show();
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
			Manage_Database info = new Manage_Database(Transfer_Contacts.this);
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
	
	public boolean delete_single_embedded_contact(Context ctx, String phone, String name) {
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
	
	
	
	private void create_a_contact_in_custom_database(String str_persons_name, String str_persons_number) {
		
		try {
			Manage_Database entry = new Manage_Database(Transfer_Contacts.this);
			entry.open();
			entry.createEntry(str_persons_name, str_persons_number, 
					"str_persons_home_number_modify",
					"str_persons_work_number_modify",
					"str_persons_email_id_modify",
					"str_persons_company_name_modify",
					"str_persons_job_title_modify", "extra value");
			entry.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		} 
	}
	
	
	public void create_a_contact_in_embedded_storage(String str_persons_name, String str_persons_pnone_number) {
		ArrayList < ContentProviderOperation > ops;
		
		ops = new ArrayList < ContentProviderOperation > ();

		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());
		
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
		     Toast.makeText(this, "Sorry!!! An Internal Error Occours\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
		 }
	}
	
	private void delete_single_contact_fron_custom_database(String str_name, String str_number) {
		try {
			Manage_Database db_info = new Manage_Database(Transfer_Contacts.this);
			db_info.open();
			db_info.deleteContacts(str_name, str_number);
			db_info.close();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	public void copy_all_external_entry_to_embedded() {
		set_external_contact();
		String[] external_contacts_name = external_contacts_name_str.split("--");
		String[] external_contacts_number = external_contacts_number_str.split("--");
		int index = external_contacts_name.length;
		
		for(int i=0; i<index; i++) {
			try {
				create_a_contact_in_embedded_storage(external_contacts_name[i], external_contacts_number[i]);
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void delete_all_external_entry() {
		set_external_contact();
		String[] external_contacts_name = external_contacts_name_str.split("--");
		String[] external_contacts_number = external_contacts_number_str.split("--");
		
		int index = external_contacts_name.length;
		
		for(int i=0; i<index; i++) {
			try {
				delete_single_contact_fron_custom_database(external_contacts_name[i], external_contacts_number[i]);
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void copy_all_embedded_entry_to_external() {
		set_embedded_contact();
		String[] embedded_contacts_name = embedded_contacts_name_str.split("--");
		String[] embedded_contacts_number = embedded_contacts_number_str.split("--");
		
		int index = embedded_contacts_number.length;
		
		for(int i=0; i<index; i++) {
			try {
				create_a_contact_in_custom_database(embedded_contacts_name[i], embedded_contacts_number[i]);
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void delete_all_embedded_contact() {
		
		set_embedded_contact();
		String[] embedded_contacts_name = embedded_contacts_name_str.split("--");
		String[] embedded_contacts_number = embedded_contacts_number_str.split("--");
		
		int index = embedded_contacts_number.length;
		
		for(int i=0; i<index; i++) {
			try {
				delete_single_embedded_contact(this, embedded_contacts_number[i], embedded_contacts_name[i]);
			} catch(Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
