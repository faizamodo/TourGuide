package ssui.fabbasi.tourguide;

import java.util.Hashtable;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LocaleViewActivity extends Activity{

	int location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locale_view);


		//reading information passed to this activity
		//Get the intent that started the activity
		Intent i = getIntent();

		location = i.getIntExtra("id", -1);
		String intent_id = Integer.toString(location);


		LocaleDataSource db = new LocaleDataSource(this);

		Locale l = db.getById(location);
		if(l != null){
			ImageView image = (ImageView) findViewById(R.id.image);
			TextView title = (TextView) findViewById(R.id.title);
			TextView desc = (TextView) findViewById(R.id.description);
			image.setImageResource(l.getImage());
			title.setText(l.getName());
			desc.setText(l.getDescription());
		}

	}
}
