package ssui.fabbasi.tourguide;

import android.content.Context;
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
	
	public long insertLocale(String name, double latitude, double longitude, String description){
		this.insertStmt.bindString(1, name);
		this.insertStmt.bindDouble(2, latitude);
		this.insertStmt.bindDouble(3, longitude);
		this.insertStmt.bindString(4, description);
		
        return this.insertStmt.executeInsert();

	}
	
	public void delete(int rowId){
        database.delete(myHelper.TABLE_NAME, myHelper.COLUMN_ID + "=" + rowId, null); 
	}
	

}
