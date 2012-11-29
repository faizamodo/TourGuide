package ssui.fabbasi.tourguide;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
	private Context context;

	/**
	 * Adjusts a drawable's bounds so that (0,0) is a pixel in the center of the 
	 * bottom row of the drawable. Also calls populate() method.
	 * @param defaultMarker the Drawable to be used as the pin
	 */
	public LocaleOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;

		populate();
	}

	/**
	 * Creates an AlertDialog when the user taps on an overlay. It gives the name of the locale and a link to the locale item view.
	 */
	@Override
	protected boolean onTap(final int index) {

		OverlayItem itemClicked = overlayItemList.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(itemClicked.getTitle());
		dialog.setMessage(itemClicked.getSnippet());
		dialog.setCancelable(true);
		dialog.setPositiveButton("View More", new OnClickListener() 
		{     
			//When clicked, we create a new intent that points to the new Locale individual view. We pass the id of the locale
			//to the intent to use to generate the necessary data for the Locale view.
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				Intent launchView = new Intent(context, LocaleViewActivity.class);

				launchView.putExtra("id", index);
				context.startActivity(launchView);

			}      
		}); 
		//The negative button will just close the alertDialog notification.
		dialog.setNegativeButton("Close", null);
		dialog.show();
		return true;
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
