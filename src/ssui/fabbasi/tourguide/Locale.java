package ssui.fabbasi.tourguide;

import com.google.android.maps.GeoPoint;

/**
 * This class represents a Locale object. It contains the name, text description, and the coordinates of the locale. This class is the
 * model and contains the data we will save in the database and show in the user interface.
 * @author Faiz
 *
 */
public class Locale {
	
	private final int id;
	private final String name;
	private final String description;
	private final GeoPoint point;
	
	/**
	 * Constructor for a locale object.
	 * @param _name The name of this locale
	 * @param _description The text description that is shown to a user.
	 * @param _latitude An int representation of the latitude of the locale.
	 * @param _longitude An int representation of the longitude of the locale.
	 */
	public Locale(int _id, String _name, String _description, int _latitude, int _longitude){
		id = _id;
		name = _name;
		description = _description;
		//Ensure that the coordinates provided are within the appropriate range, otherwise set the point to 0,0.
		if(_latitude > -90 && _latitude < 90 && _longitude > -180 && _longitude < 180){
			point = new GeoPoint(_latitude, _longitude);
		}
		else{
			point = new GeoPoint(0,0);
		}
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the point
	 */
	public GeoPoint getPoint() {
		return point;
	}
}
