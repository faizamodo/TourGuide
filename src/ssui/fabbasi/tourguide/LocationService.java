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

/**
 * This Service starts the LocationManager that tracks the users current location. It runs in the background, and sends location changes to the TourGuideMapActivity,
 * which determines if the user is within range of a POI. It does so by broadcasting a new intent if the accuracy of the location change is better than a 50 meter error
 * fix
 * @author Faiz
 *
 */
public class LocationService extends Service implements LocationListener {
	
	//String commands
	public static final String MOVEMENT_UPDATE = "ssui.fabbasi.tourguide.MOVEMENT_UPDATE";
	public static final String LATITUDE = "ssui.fabbasi.tourguide.LATITUDE";
	public static final String LONGITUDE = "ssui.fabbasi.tourguide.LONGITUDE";
	
	private static final String DEBUG_TAG = "Uhh";
	private LocationManager lm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * When the Service is started, we create a new LocationManager, and set up the request updates.
	 */
	@Override
	public void onStart(Intent intent, int startid) {

	    lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

	}

	/**
	 * Called when the location has changed. Creates a new intent that includes the latitude and longitude of the new location change, and broadcasts
	 * the intent to be received by another activity, where corresponding actions take place.
	 */
	@Override
	public void onLocationChanged(Location location) {
		
		//Ensure that the accuracy is within 50 meters.
		if(location.getAccuracy() < 50){
			//Stuff the latitude and longitude into an intent to be received by the TourGuideMapActivity view
			Intent intent = new Intent(MOVEMENT_UPDATE);
			intent.putExtra(LATITUDE, location.getLatitude());
			intent.putExtra(LONGITUDE, location.getLongitude());
			intent.setAction("ssui.fabbasi.tourguide.mybroadcast");
			//Broadcast the intent to be picked up by TourGuideMapActivity
			sendBroadcast(intent);
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {

		
	}

	@Override
	public void onProviderEnabled(String provider) {

		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		
	}
	
	

}
