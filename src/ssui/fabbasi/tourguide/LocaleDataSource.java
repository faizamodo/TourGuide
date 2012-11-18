package ssui.fabbasi.tourguide;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * This is the data access object that creates and maintains a connection to the SQLiteDatabase and supports adding new locales and fetching all locales.
 * @author Faiz
 *
 */
public class LocaleDataSource {
	
	//Database fields
	private static SQLiteDatabase database;
	private MySQLiteHelper myHelper;
	private String[] allColumns = myHelper.getColumns();
    private SQLiteStatement insertStmt;
    
    //Insert statement
    private static final String INSERT = "insert into locales (name, latitude, longitude, description) values (?,?,?,?)";
	
	/**
	 * Initialize a new MySQLiteHelper object
	 * @param context The context of the application.
	 */
	public LocaleDataSource(Context context){
		myHelper = new MySQLiteHelper(context);
        this.insertStmt = LocaleDataSource.database.compileStatement(INSERT);
	}
	
	/**
	 * Initialize a new writable SQLiteDatabase
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = myHelper.getWritableDatabase();
	}
	
	/**
	 * Close any open database object. 
	 */
	public void close(){
		myHelper.close();
	}
	
	//Database Manipulations
	
	/**
	 * Insert a new locale into the SQLite database
	 * @param name The name of the locale
	 * @param latitude The latitude of the locale (double)
	 * @param longitude The longitude of the locale (double)
	 * @param description The description of the locale. To be shown to the user.
	 * @return
	 */
	public long insertLocale(String name, double latitude, double longitude, String description){
		this.insertStmt.bindString(1, name);
		this.insertStmt.bindDouble(2, latitude);
		this.insertStmt.bindDouble(3, longitude);
		this.insertStmt.bindString(4, description);
		
        return this.insertStmt.executeInsert();

	}
	
	/**
	 * Remove a locale from the database, based on its row ID
	 * @param rowId the id of the locale
	 */
	public void delete(int rowId){
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + "=" + rowId, null); 
	}
	
	/**
	 * Retrieve all locales in the database, in the order that they were inserted into the database.
	 * @return
	 */
	public List<Locale> getAllLocales(){
		List<Locale> locales = new ArrayList<Locale>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Locale locale = cursorToLocale(cursor);
			locales.add(locale);
			cursor.moveToNext();
		}
		
		return locales;
	}
	
	/**
	 * Call this method to prepopulate the database with data.
	 * 
	 */
	public void prepopulate(){
		database.execSQL("INSERT INTO MyTable (name, latitude, longitude, description) " +
				"VALUES (Empire State Building, 40.7484, 73.9847, The Empire State Building");
		database.execSQL("INSERT INTO MyTable (name, latitude, longitude, description) " +
				"VALUES (Golden Gate Bridge, 37.8188, 122.4784, The Golden Gate Bridge is a suspension bridge");
		database.execSQL("INSERT INTO MyTable (name, latitude, longitude, description) " +
				"VALUES (Gateway Arch, 38.6254, 90.1834, The Gateway Arch, or Gateway to the West");

	}


	private Locale cursorToLocale(Cursor cursor) {
		Locale locale = new Locale(cursor.getPosition(), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4));
		
		return locale;

	}
	

}
