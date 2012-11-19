package ssui.fabbasi.tourguide;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

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
	LocationListener locationListener;
	LocaleDataSource db;
	int i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new LocaleDataSource(this);
        db.prepopulate();
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
				
				
				double lon = (double) (location.getLongitude());
				double lat = (double) (location.getLatitude());
				System.out.println("The lat is " + lat);
				
				int lontitue = (int)lon;
				int latitute = (int)lat;
				System.out.println("The latitute is " + latitute);

				
				GeoPoint geopoint = new GeoPoint(latitute, lontitue);
				mapController.animateTo(geopoint);

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
        final List<Locale> locales = db.getAllLocales();
        
        change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				i = i % locales.size();
				Locale l = locales.get(i);
            	Location loc = new Location("dummyprovider");
            	i++;
            	loc.setLatitude(l.getPoint().getLatitudeE6());
            	loc.setLongitude(l.getPoint().getLongitudeE6());

            	Toast.makeText(getApplicationContext(), l.getDescription(), Toast.LENGTH_SHORT).show();
            	locationListener.onLocationChanged(loc);
            }
		});
    
    }
    


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

    
}
