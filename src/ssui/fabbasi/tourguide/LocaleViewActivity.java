package ssui.fabbasi.tourguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity creates the view for an individual Locale. It pulls the data of the locale (by using the id provided by the intent that called
 * this Activity) and fills the layout with that data.
 * @author Faiz
 *
 */
public class LocaleViewActivity extends Activity{

	//The id of the locale
	private int id;
	private LocaleDataSource db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locale_view);


		//reading information passed to this activity
		//Get the intent that started the activity
		Intent i = getIntent();

		//Add one to the id for displacement issue 
		id = i.getIntExtra("id", -1) + 1;

		//Create a connection to the LocaleDataSource database
		db = new LocaleDataSource(this);

		Locale l = db.getById(id);
		if(l != null){
			//Grab the necessary Views and set their values with the given locale.
			ImageView image = (ImageView) findViewById(R.id.image);
			TextView title = (TextView) findViewById(R.id.title);
			TextView desc = (TextView) findViewById(R.id.description);
			title.setText(l.getName());
			desc.setText(l.getDescription());
			image.setImageResource(l.getImage());
		}
		db.close();

	}
	
	@Override
	public void onResume(){
		super.onResume();
		BaseApplication.activityResumed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		BaseApplication.activityPaused();
	}
}
