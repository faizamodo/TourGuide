package ssui.fabbasi.tourguide;

import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * This class represents the data structure that displays and populates the locale listview.
 * @author Faiz
 *
 */
public class ListActivity extends Activity{


	//This locale array is passed to the LocaleAdapter, used to create the list view.
	Locale[] locale_data;
	//Connection to the database.
	LocaleDataSource db;
	//The listView that we're displaying the content to
	ListView listView1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO Remove this call
		this.startService(new Intent(this, LocationService.class));
		setContentView(R.layout.list_layout);

		//Set up connection to the database
		db = new LocaleDataSource(this);
		//If the database has no data, prepopulate.
		if(db.empty()){
			db.prepopulate();
		}
		//Retrieve an array of the locales
		Locale[] locales = db.getArrayOfLocales();


		//Create the adapter with our locale array and locale item design layout
		LocaleAdapter adapter = new LocaleAdapter(this, R.layout.listview_item_row, locales);
		
		//Retrieve the listView and assign it to our listView variable
		listView1 = (ListView)findViewById(R.id.listView1);
		//Set the adapter to our listView to populate it with the locale data
		listView1.setAdapter(adapter);

		//Set the itemClickListener to create a new intent, which sends the user to the LocaleViewActivity for the clicked locale.
		listView1.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				//We retrieve the id of the locale at the clicked position, to be sent to the LocaleViewActivity
				Locale l = (Locale) listView1.getItemAtPosition(position);
				int locale_id = l.getId();
				
				//Create a new intent that will launch a new LocaleViewActivity
				Intent launchView = new Intent(ListActivity.this, LocaleViewActivity.class);

				//Add the id as an extra value to the intent
				launchView.putExtra("id", locale_id);
				startActivityForResult(launchView, 0);         
			}
		});
	}
}