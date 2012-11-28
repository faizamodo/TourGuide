package ssui.fabbasi.tourguide;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;

/**
 * This class represents a Locale object. It contains the name, text description, coordinates, and image of the locale. This class is the
 * model and contains the data we will save in the database and show in the user interface.
 * @author Faiz
 *
 */
public class Locale {
	
	//Our fields. Set to private/final to prevent overriding of the values.
	private final int id;
	private final String name;
	private final String description;
	private final double lat;
	private final double lon;
	private final int image;
		

	/**
	 * Constructor for a locale object. It takes double values for the latitude and longitude, and converts them into the appropriate int value.
	 * @param _name The name of this locale
	 * @param _description The text description that is shown to a user.
	 * @param _latitude A double representation of the latitude of the locale.
	 * @param _longitude A double representation of the longitude of the locale.
	 * @param _image An double referring to the image of the locale.
	 */
	public Locale(int _id, String _name, double _latitude, double _longitude, String _description, double _image) {
		id = _id;
		name = _name;
		description = _description;
		image = (int) _image;

		//Ensure that the coordinates provided are within the appropriate range, otherwise set the point to 0,0.
		if(_latitude > -90 && _latitude < 90 && _longitude > -180 && _longitude < 180){
			//If so, convert the double latitude and longitude into equivalent int values, to create a GeoPoint.
			lat = _latitude;
			lon = _longitude;
		}
		//Set the lat and lon to zero if the given latitude and longitude are out of range.
		else{
			lat = 0;
			lon = 0;
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
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @return the image
	 */
	public int getImage() {
		return image;
	}
}
