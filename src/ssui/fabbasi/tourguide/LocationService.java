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
import android.widget.Toast;

public class LocationService extends Service implements LocationListener {
	
	//String commands
	public static final String MOVEMENT_UPDATE = "ssui.fabbasi.tourguide.MOVEMENT_UPDATE";
	public static final String LATITUDE = "ssui.fabbasi.tourguide.LATITUDE";
	public static final String LONGITUDE = "ssui.fabbasi.tourguide.LONGITUDE";
	
	private static final String DEBUG_TAG = "Uhh";
	private LocationManager lm;

	@Override
	public void onCreate() {
	    Log.d(DEBUG_TAG, "onCreate");
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
	    Log.d(DEBUG_TAG, "onStart");

	    lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

	    Log.d(DEBUG_TAG, lm.toString());

	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
		
		Toast toast = Toast.makeText(this, "Location Change", Toast.LENGTH_SHORT);
		toast.show();
		
		//Stuff the latitude and longitude into an intent to be received by the TourGuideMapActivity view
		Intent intent = new Intent(MOVEMENT_UPDATE);
		intent.putExtra(LATITUDE, location.getLatitude());
		intent.putExtra(LONGITUDE, location.getLongitude());
		intent.setAction("ssui.fabbasi.tourguide.mybroadcast");
		
		sendBroadcast(intent);
		
		
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
