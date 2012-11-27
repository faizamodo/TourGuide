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

public class TourGuideMapActivity extends MapActivity {

	//Button to keep track of download click request
	private Button change;

	MapController mapController;
	MapView mapview;
	static LocationListener locationListener;
	LocaleDataSource db;
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

		locationListener = new LocationListener() {

			private TextView locationDetails;

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
					Location loc = new Location("dummyprovider");
					//Set the latitude and longitude of the new location, with the current locale
					loc.setLatitude(l.getLat());
					loc.setLongitude(l.getLon());
					System.out.println(loc.getLatitude() + " " + loc.getLongitude());
					System.out.println(location.getLatitude() + " " + location.getLongitude());
					boolean close = location.distanceTo(loc) < 10;

					if(close && !notified){
						notified = true;
						AlertDialog.Builder adb = new AlertDialog.Builder(TourGuideMapActivity.this);
						adb.setTitle("Point of Interest");
						adb.setMessage("Within range of " + l.getName());
						adb.setPositiveButton("View More", new OnClickListener() 
						{     

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent launchView = new Intent(TourGuideMapActivity.this, LocaleViewActivity.class);

								launchView.putExtra("id", l.getId());
								startActivity(launchView);

							}      
						}); 

						adb.setNegativeButton("Close", null);
						adb.show();           
					}
				}
				int lon = (int) (location.getLongitude()*1E6);
				int lat = (int) (location.getLatitude()*1E6);
				System.out.println("Lat: " + (lat) + " Lon: " + (lon));

				//Create new GeoPoint to point the map to.
				GeoPoint geopoint = new GeoPoint(lat, lon);
				mapController.animateTo(geopoint);
				//Invalidate map to cause redraw
				mapview.invalidate();
			}

		};

		mapview = (MapView)findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true);
		mapController = mapview.getController();
		//Set initial center to the University Center
		mapController.setCenter(new GeoPoint((int)(40.443524*1E6), (int) (-79.942004*1E6)));
		mapController.setZoom(20);

		//Assign the appropriate button
		change = (Button) findViewById(R.id.button1);

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


	static public class LocaleServiceReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)//this method receives broadcast messages. Be sure to modify AndroidManifest.xml file in order to enable message receiving
		{
			long latitude = intent.getLongExtra(LocationService.LATITUDE, 0);
			long longitude = intent.getLongExtra(LocationService.LONGITUDE, 0);

			Location loc = new Location("dummyprovider");
			loc.setLatitude((int)latitude);
			loc.setLongitude((int)longitude);
			if(loc != null && locationListener != null){
				locationListener.onLocationChanged(loc);
			}

		}
	}





}
