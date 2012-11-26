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


	Locale[] locale_data;
	LocaleDataSource db;
	ListView listView1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.startService(new Intent(this, LocationService.class));
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

		listView1.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				Locale l = (Locale) listView1.getItemAtPosition(position);
				int location = l.getId();
				//        	   Toast.makeText(Gallery.this, "" + position + ", " + location, Toast.LENGTH_SHORT).show();
				Intent launchView = new Intent(ListActivity.this, LocaleViewActivity.class);

				launchView.putExtra("id", location);
				startActivityForResult(launchView, 1);         
			}
		});
	}
}