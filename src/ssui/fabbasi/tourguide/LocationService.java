package ssui.fabbasi.tourguide;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service implements LocationListener {
	
	private static final String DEBUG_TAG = "Uhh";
	private LocationManager lm;

	@Override
	public void onCreate() {
	    Log.d(DEBUG_TAG, "onCreate");
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
	    Log.d(DEBUG_TAG, "onStart");

	    lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10f, this);
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,
	            300f, this);

	    Log.d(DEBUG_TAG, lm.toString());

	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	

}
