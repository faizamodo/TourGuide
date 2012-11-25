package ssui.fabbasi.tourguide;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This class represents the data structure that displays and populates the locale listview.
 * @author Faiz
 *
 */
public class ListActivity extends Activity{
	
	
	Locale[] locale_data;
	LocaleDataSource db;
	ListView listView1;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_layout);
	        
	        //Set up connection to the database
	        db = new LocaleDataSource(this);
	        if(db.empty()){
	        	db.prepopulate();
	        }
			//Retrieve an array of the locales
			Locale[] locales = db.getArrayOfLocales();
	        
	        
	        
	        
	        
	        LocaleAdapter adapter = new LocaleAdapter(this, 
	                R.layout.listview_item_row, locales);
	        
	        
	        listView1 = (ListView)findViewById(R.id.listView1);
	         
	        
	        listView1.setAdapter(adapter);
	    }
}