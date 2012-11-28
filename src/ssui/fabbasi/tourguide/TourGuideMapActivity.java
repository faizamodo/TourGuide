package ssui.fabbasi.tourguide;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the mapview of the TourGuide application. It contains a connection to the database and a locationManager, and a broadcast listener.
 * When the application's location service notices a location change, this class picks up the broadcast and compares the new location to the locales in the
 * database, and creates a notification if the new location is within a given range of any of the locales.
 * @author Faiz
 *
 */
public class TourGuideMapActivity extends MapActivity {

	//Button to keep track of download click request
	private Button change;

	MapController mapController;
	MapView mapview;
	static LocationListener locationListener;
	LocaleDataSource db;
	//Iterator for the Locale list
	int i;
	List<Locale> locales;
	public double latitude;
	public double longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		db = new LocaleDataSource(this);
		locales = db.getAllLocales();
		i = 0;
		LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		//Set up the mapview
		mapview = (MapView)findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true);
		mapController = mapview.getController();
		//Set initial center to the UC Turnaround
		mapController.setCenter(new GeoPoint((int)(40.444199*1E6), (int) (-79.941867*1E6)));
		mapController.setZoom(20);
		
		//Set up the methods for our locationListener
		locationListener = new LocationListener() {


			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLocationChanged(Location location) {
				makeUseOfNewLocation(location);

			}

			private void makeUseOfNewLocation(Location location) {
				boolean notified = false;
				for(final Locale l : locales){
					//Create a fake location for comparisons
					Location loc = new Location("dummyprovider");
					//Set the latitude and longitude of the new location, with the current locale
					loc.setLatitude(l.getLat());
					loc.setLongitude(l.getLon());
					System.out.println(loc.getLatitude() + " " + loc.getLongitude());
					System.out.println(location.getLatitude() + " " + location.getLongitude());
					boolean close = location.distanceTo(loc) < 10;

					//If the new location is close to a locale, and a notification hasn't already been sent for the new location change,
					//we create a new notification.
					if(close && !notified){
						//Change the notified boolean to true so we don't send another notification to the user.
						notified = true;
						//Create a new alertDialog notification
						AlertDialog.Builder adb = new AlertDialog.Builder(TourGuideMapActivity.this);
						adb.setTitle("Point of Interest");
						adb.setMessage("You're near " + l.getName());
						//Set a positive button that sends the user to the individual locale view
						adb.setPositiveButton("View More", new OnClickListener() 
						{     
							//When clicked, we create a new intent that points to the new Locale individual view. We pass the id of the locale
							//to the intent to use to generate the necessary data for the Locale view.
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent launchView = new Intent(TourGuideMapActivity.this, LocaleViewActivity.class);

								launchView.putExtra("id", l.getId());
								startActivity(launchView);

							}      
						}); 
						//The negative button will just close the alertDialog notification.
						adb.setNegativeButton("Close", null);
						adb.show();           
					}
				}
				//Transform the new location's latitude and longitude into int values, so we can change the center of the mapview to reflect where the user is
				int lon = (int) (location.getLongitude()*1E6);
				int lat = (int) (location.getLatitude()*1E6);

				//Create new GeoPoint to point the map to.
				GeoPoint geopoint = new GeoPoint(lat, lon);
				mapController.animateTo(geopoint);
				//Invalidate map to cause redraw
				mapview.invalidate();
			}

		};


		//Assign the appropriate button
		change = (Button) findViewById(R.id.button1);

		//When the change button is clicked, we iterate through the locales in the database.
		change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(locales.size() > 0){
					i = i % locales.size();
					Locale l = locales.get(i);
					Location loc = new Location("dummyprovider");
					i++;
					loc.setLatitude(l.getLat());
					loc.setLongitude(l.getLon());

					locationListener.onLocationChanged(loc);
				}
			}
		});

		//Request updates from the phone
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
	}



	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * This class listens for location updates given by our Locale Service. When it receives a new location, it attempts to change the location of the mapView
	 * by calling the onLocationChanged(Location loc) method
	 * @author Faiz
	 *
	 */
	static public class LocaleServiceReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)//this method receives broadcast messages. Be sure to modify AndroidManifest.xml file in order to enable message receiving
		{
			//Get the latitude and longitude stuffed into the intent that was captured.
			long latitude = intent.getLongExtra(LocationService.LATITUDE, 0);
			long longitude = intent.getLongExtra(LocationService.LONGITUDE, 0);

			//Create a fake location with our lat and lon values
			Location loc = new Location("dummyprovider");
			loc.setLatitude((int)latitude);
			loc.setLongitude((int)longitude);
			if(loc != null && locationListener != null){
				locationListener.onLocationChanged(loc);
			}

		}
	}





}
