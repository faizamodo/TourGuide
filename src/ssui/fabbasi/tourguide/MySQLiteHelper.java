package ssui.fabbasi.tourguide;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is responsible for creating and upgrading the SQLite Database that stores locale information. It contains an onUpdate() method which will
 * delete all existing data and re-create the table. It also defines the table name and table columns.
 * 
 * It implements the SQLiteOpenHelper.
 * @author Faiz
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	//DATABASE DECLARATIONS
	//Name of the database
	public static final String DATABASE_NAME = "locations.db";
	//Version of the database
	public static final int DATABASE_VERSION = 1;

	//TABLE DECLARATIONS
	public static final String TABLE_NAME = "locales";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_DESCRIPTION = "description";

	// Database creation sql statement. Store the latitude and longitude as real to preserve their decimal numerals.
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null, " + COLUMN_LATITUDE
			+ " real not null, " + COLUMN_LONGITUDE
			+ " real not null, " + COLUMN_DESCRIPTION
			+ " text not null);";

	/**
	 * Create a helper object to create, open, and/or manage a database. 
	 * This method always returns very quickly. 
	 * The database is not actually created or opened until one of getWritableDatabase() or getReadableDatabase() is called.
	 * @param context to use to open or create the database
	 */
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(android.database.sqlite.SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(android.database.sqlite.SQLiteDatabase db,
			int oldVersion, int newVersion) {
		//Drop the table if it already exists
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		//Recreate the database
		onCreate(db);

	}

	/**
	 * Helper method that returns the columns of the database.
	 * @return A string array of column names.
	 */
	public String[] getColumns() {
		String[] columns = {"COLUMN_ID", "COLUMN_NAME", "COLUMN_LATITUDE", "COLUMN_LONGITUDE", "COLUMN_DESCRIPTION"};
		return columns;
	}

}
