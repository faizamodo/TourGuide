package ssui.fabbasi.tourguide;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * This class creates overlays to be shown on our mapview. 
 * @author Faiz
 *
 */
public class LocaleOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();

	/**
	 * Adjusts a drawable's bounds so that (0,0) is a pixel in the center of the 
	 * bottom row of the drawable. Also calls populate() method.
	 * @param defaultMarker the Drawable to be used as the pin
	 */
	public LocaleOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		populate();
	}
	
	/**
	 * Create a new overlay item
	 * @param p The GeoPoint of the overlay
	 * @param title The title for the overlay
	 * @param snippet A description of the overlay
	 */
	public void addItem(GeoPoint p, String title, String snippet) {
		OverlayItem newItem = new OverlayItem(p, title, snippet);
		overlayItemList.add(newItem);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {

		return overlayItemList.get(i);
	}

	@Override
	public int size() {

		return overlayItemList.size();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		super.draw(canvas, mapView, shadow);
	}

}
