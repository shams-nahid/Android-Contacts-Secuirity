package com.bmshamsnahid.callerappbeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Manage_Database {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_CONTACTS_NAME = "contacts_name";
	public static final String KEY_CONTACTS_NUMBER = "contacts_number";
	public static final String KEY_CONTACTS_EX_ONE = "contacts_extra_one";
	public static final String KEY_CONTACTS_EX_TWO = "contacts_extra_two";
	public static final String KEY_CONTACTS_EX_THREE = "contacts_extra_three";
	public static final String KEY_CONTACTS_EX_FOUR = "contacts_extra_four";
	public static final String KEY_CONTACTS_EX_FIVE = "contacts_extra_five";
	public static final String KEY_CONTACTS_EX_SIX = "contacts_extra_six";
	
	private static final String DATABASE_NAME = "contacts_database";
	private static final String DATABASE_TABLE = "contacts_table";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(
					"CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					KEY_CONTACTS_NAME + " TEXT NOT NULL, " +
					KEY_CONTACTS_NUMBER + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_ONE + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_TWO + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_THREE + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_FOUR + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_FIVE + " TEXT NOT NULL, " +
					KEY_CONTACTS_EX_SIX + " TEXT NOT NULL);"
			);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public Manage_Database(Context C) {
		ourContext = C;
	}
	
	public Manage_Database open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() throws SQLException {
		ourHelper.close();
	}
	
	public long createEntry(String contact_name_str, String contact_number_str, String contact_extra_one_str, String contact_extra_two_str, String contact_extra_three_str, String contact_extra_four_str, String contact_extra_five_str, String contact_extra_six_str) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CONTACTS_NAME, contact_name_str);
		cv.put(KEY_CONTACTS_NUMBER, contact_number_str);
		cv.put(KEY_CONTACTS_EX_ONE, "ex1");
		cv.put(KEY_CONTACTS_EX_TWO, "ex2");
		cv.put(KEY_CONTACTS_EX_THREE, "ex3");
		cv.put(KEY_CONTACTS_EX_FOUR, "ex4");
		cv.put(KEY_CONTACTS_EX_FIVE, "ex5");
		cv.put(KEY_CONTACTS_EX_SIX, "ex6");
		
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	public String getData() {
		String[] columns = new String[] { KEY_ROWID, KEY_CONTACTS_NAME, KEY_CONTACTS_NUMBER, KEY_CONTACTS_EX_ONE, KEY_CONTACTS_EX_TWO, KEY_CONTACTS_EX_THREE, KEY_CONTACTS_EX_FOUR, KEY_CONTACTS_EX_FIVE, KEY_CONTACTS_EX_SIX};
		
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		String results = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iContacts_Name = c.getColumnIndex(KEY_CONTACTS_NAME);
		int iContacts_Number = c.getColumnIndex(KEY_CONTACTS_NUMBER);
		int iContacts_Extra_One = c.getColumnIndex(KEY_CONTACTS_EX_ONE);
		int iContacts_Extra_Two = c.getColumnIndex(KEY_CONTACTS_EX_TWO);
		int iContacts_Extra_Three = c.getColumnIndex(KEY_CONTACTS_EX_THREE);
		int iContacts_Extra_Four = c.getColumnIndex(KEY_CONTACTS_EX_FOUR);
		int iContacts_Extra_Five = c.getColumnIndex(KEY_CONTACTS_EX_FIVE);
		int iContacts_Extra_Six = c.getColumnIndex(KEY_CONTACTS_EX_SIX);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			results = results + c.getString(iRow) + " " + c.getString(iContacts_Name) + " " + c.getString(iContacts_Number) + "\n";
		}
		return results;
	}
	
	public void deleteEntry(long lRow1) throws SQLException {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
	
	public void updateEntry(long lRow1, String contact_name_str, String contact_number_str, String contact_extra_one_str, String contact_extra_two_str, String contact_extra_three_str, String contact_extra_four_str, String contact_extra_five_str, String contact_extra_six_str) throws SQLException{
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_CONTACTS_NAME, contact_name_str);
		cvUpdate.put(KEY_CONTACTS_NUMBER, contact_number_str);
		cvUpdate.put(KEY_CONTACTS_EX_ONE, "ex1");
		cvUpdate.put(KEY_CONTACTS_EX_TWO, "ex2");
		cvUpdate.put(KEY_CONTACTS_EX_THREE, "ex3");
		cvUpdate.put(KEY_CONTACTS_EX_FOUR, "ex4");
		cvUpdate.put(KEY_CONTACTS_EX_FIVE, "ex5");
		cvUpdate.put(KEY_CONTACTS_EX_SIX, "ex6");
		lRow1 = getRowId(contact_name_str, contact_number_str);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + lRow1 , null);
	}
	
	private long getRowId(String name_finding_id, String number_finding_id) {
		String[] columns = new String[] { KEY_ROWID, KEY_CONTACTS_NAME, KEY_CONTACTS_NUMBER, KEY_CONTACTS_EX_ONE, KEY_CONTACTS_EX_TWO, KEY_CONTACTS_EX_THREE, KEY_CONTACTS_EX_FOUR, KEY_CONTACTS_EX_FIVE, KEY_CONTACTS_EX_SIX};
		
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		String results = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iContacts_Name = c.getColumnIndex(KEY_CONTACTS_NAME);
		int iContacts_Number = c.getColumnIndex(KEY_CONTACTS_NUMBER);
		int iContacts_Extra_One = c.getColumnIndex(KEY_CONTACTS_EX_ONE);
		int iContacts_Extra_Two = c.getColumnIndex(KEY_CONTACTS_EX_TWO);
		int iContacts_Extra_Three = c.getColumnIndex(KEY_CONTACTS_EX_THREE);
		int iContacts_Extra_Four = c.getColumnIndex(KEY_CONTACTS_EX_FOUR);
		int iContacts_Extra_Five = c.getColumnIndex(KEY_CONTACTS_EX_FIVE);
		int iContacts_Extra_Six = c.getColumnIndex(KEY_CONTACTS_EX_SIX);
		
		long index = -1;
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String row_id = c.getString(iRow);
			String name = c.getString(iContacts_Name);
			String number = c.getString(iContacts_Number);
			
			if(name.equals(name_finding_id) == true) {
				index = Long.parseLong(row_id);
				return  index;
			}
		}
		return index;
	}
	
	public String getName() {
		String[] columns = new String[] { KEY_ROWID, KEY_CONTACTS_NAME, KEY_CONTACTS_NUMBER, KEY_CONTACTS_EX_ONE, KEY_CONTACTS_EX_TWO, KEY_CONTACTS_EX_THREE, KEY_CONTACTS_EX_FOUR, KEY_CONTACTS_EX_FIVE, KEY_CONTACTS_EX_SIX};
		
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		String results = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iContacts_Name = c.getColumnIndex(KEY_CONTACTS_NAME);
		int iContacts_Number = c.getColumnIndex(KEY_CONTACTS_NUMBER);
		int iContacts_Extra_One = c.getColumnIndex(KEY_CONTACTS_EX_ONE);
		int iContacts_Extra_Two = c.getColumnIndex(KEY_CONTACTS_EX_TWO);
		int iContacts_Extra_Three = c.getColumnIndex(KEY_CONTACTS_EX_THREE);
		int iContacts_Extra_Four = c.getColumnIndex(KEY_CONTACTS_EX_FOUR);
		int iContacts_Extra_Five = c.getColumnIndex(KEY_CONTACTS_EX_FIVE);
		int iContacts_Extra_Six = c.getColumnIndex(KEY_CONTACTS_EX_SIX);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			results = results  + c.getString(iContacts_Name) + "--";
		}
		return results;
	}
	
	public String getNumber() {
		String[] columns = new String[] { KEY_ROWID, KEY_CONTACTS_NAME, KEY_CONTACTS_NUMBER, KEY_CONTACTS_EX_ONE, KEY_CONTACTS_EX_TWO, KEY_CONTACTS_EX_THREE, KEY_CONTACTS_EX_FOUR, KEY_CONTACTS_EX_FIVE, KEY_CONTACTS_EX_SIX};
		
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		String results = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iContacts_Name = c.getColumnIndex(KEY_CONTACTS_NAME);
		int iContacts_Number = c.getColumnIndex(KEY_CONTACTS_NUMBER);
		int iContacts_Extra_One = c.getColumnIndex(KEY_CONTACTS_EX_ONE);
		int iContacts_Extra_Two = c.getColumnIndex(KEY_CONTACTS_EX_TWO);
		int iContacts_Extra_Three = c.getColumnIndex(KEY_CONTACTS_EX_THREE);
		int iContacts_Extra_Four = c.getColumnIndex(KEY_CONTACTS_EX_FOUR);
		int iContacts_Extra_Five = c.getColumnIndex(KEY_CONTACTS_EX_FIVE);
		int iContacts_Extra_Six = c.getColumnIndex(KEY_CONTACTS_EX_SIX);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			results = results  + c.getString(iContacts_Number) + "--";
		}
		return results;
	}
	
	public void deleteContacts(String contacts_name_to_delete, String contacts_number_to_delete) throws SQLException {
		long lRow1 = getRowId(contacts_name_to_delete, contacts_number_to_delete);
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}

}
