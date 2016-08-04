package com.example.user.smgapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DBController  extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	public static SQLiteDatabase database;

	
	public DBController(Context applicationcontext) {
       // super(applicationcontext, "iceportprojectsqlitelatest61.db", null, 1);
        super(applicationcontext, "pincode.db", null, 1); // client db
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query, codequery,invoicebillingquery,compprofilequery,itemsquery,carddetailsquery,paymentquery,cardcodenamequery,updateyesininvoicequery,invoicecodebillnumquery,paymentcodebillnumquery,devicesquery;
		query = "CREATE TABLE pincodetable ( PINCODE TEXT, PARISHABLEHAVE TEXT, SERVICEHAVE TEXT, CITY TEXT)";
		

		
		database.execSQL(query);

		 
		 
		 
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query, codequery,invoicebillingquery,itemsquery,compprofilequery,carddetailsquery,paymentquery,cardcodenamequery,updateyesininvoicequery,invoicecodebillnumquery,paymentcodebillnumquery,devicesquery;
		query = "DROP TABLE IF EXISTS pincodetable";

		database.execSQL(query);

        onCreate(database);
	}
	
	/* public DBController open() throws SQLException {
	
	        return this;
	    }*/
	
	
	/**
	 * Inserts User into SQLite DB
	 * @param queryValues
	 */
	

	/*public void insertPincode(HashMap<String, String> queryValues) {
		database = this.getWritableDatabase();

        database.beginTransaction();

		ContentValues values = new ContentValues();

		values.put("PINCODE", queryValues.get("Pincode"));
		values.put("PARISHABLEHAVE", queryValues.get("Parishable_have"));
        values.put("SERVICEHAVE", queryValues.get("Service_have"));
        values.put("CITY", queryValues.get("City"));

		
		database.insert("pincodetable", null, values);

        database.setTransactionSuccessful();
        database.endTransaction();

        // getAllPincodeCity();

		System.out.println("PIN CODE DATA INSERTED IN SQLITE---->");
		//database.close();
	}*/

	public void insertPincode(HashMap<String, String> queryValues) {
		database = this.getWritableDatabase();

        String sql = "INSERT INTO pincodetable (PINCODE,PARISHABLEHAVE,SERVICEHAVE,CITY) VALUES (?,?,?,?)";
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < queryValues.size(); i++) {
            stmt.bindString(1, queryValues.get("Pincode"));
            stmt.bindString(2, queryValues.get("Parishable_have"));
            stmt.bindString(3, queryValues.get("Service_have"));
            stmt.bindString(4, queryValues.get("City"));




            stmt.execute();
            stmt.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();

		// getAllPincodeCity();

		System.out.println("PIN CODE DATA INSERTED IN SQLITE---->");
		//database.close();
	}



	public String getSingleCityEntry(String pin) {


        database = this.getWritableDatabase();
        Cursor cursor = database.query("pincodetable", null, " PINCODE=?",
                new String[] { pin }, null, null, null);




        if (cursor.getCount() < 1) {

            cursor.close();
            return "NOT EXIST";

        }
        cursor.moveToFirst();
        String city = cursor.getString(cursor.getColumnIndex("CITY"));
        cursor.close();

        return city;
    }


/*
public ArrayList<HashMap<String, String>> getAllPincodeCity() {
		ArrayList<String> cityList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM navigationdrawertable ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("maincategoryid", cursor.getString(0));
				map.put("subcategoryid", cursor.getString(1));
				map.put("categoryname", cursor.getString(2));
				map.put("categorytype", cursor.getString(3));


				cityList.add(map);
			} while (cursor.moveToNext());
		}
		database.close();
		return cityList;
	}
*/



	public ArrayList<HashMap<String, String>> getAllPincodeCity() {
		ArrayList<HashMap<String, String>> cityList;
		cityList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM pincodetable ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("pincode", cursor.getString(0));
				map.put("parishablehave", cursor.getString(1));
				map.put("servicehave", cursor.getString(2));
                map.put("city", cursor.getString(3));

				cityList.add(map);


			} while (cursor.moveToNext());
		}
		database.close();
		return cityList;
	}



	public int getPinCodeCount() {
		String countQuery = "SELECT  * FROM pincodetable";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}




	
}
