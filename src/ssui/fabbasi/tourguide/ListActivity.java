package ssui.fabbasi.tourguide;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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
			db.prepopulate();
	        
	        //Fetch the list of locales
	        List<Locale> list = db.getAllLocales();
	        
	        //Transform into an array
	        Locale[] locales = list.toArray(new Locale[list.size()]);
	        
	        
	        
	        
	        
	        LocaleAdapter adapter = new LocaleAdapter(this, 
	                R.layout.listview_item_row, locales);
	        
	        
	        listView1 = (ListView)findViewById(R.id.listView1);
	         
	        
	        listView1.setAdapter(adapter);
	    }
}