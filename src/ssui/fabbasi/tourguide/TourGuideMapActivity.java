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

			public void makeUseOfNewLocation(Location location) {
				for(final Locale l : locales){
					Location loc = new Location("dummyprovider");
					loc.setLatitude(l.getPoint().getLatitudeE6());
					loc.setLongitude(l.getPoint().getLongitudeE6());

					if(loc.distanceTo(location) < 10){
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
								startActivityForResult(launchView, 1);  

							}      
						}); 
						
						adb.setNegativeButton("Close", null);
						adb.show();           
					}
				}



				int lon = (int) (location.getLongitude());
				int lat = (int) (location.getLatitude());

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
					loc.setLatitude(l.getPoint().getLatitudeE6());
					loc.setLongitude(l.getPoint().getLongitudeE6());

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
        	double latitude = intent.getDoubleExtra(LocationService.LATITUDE, 0);
        	double longitude = intent.getDoubleExtra(LocationService.LONGITUDE, 0);

			Location loc = new Location("dummyprovider");
			loc.setLatitude((int)latitude*1E6);
			loc.setLongitude((int)longitude*1E6);
			
			locationListener.onLocationChanged(loc);
        	
        }
    }





}
