package ssui.fabbasi.tourguide;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * This class creates a tab menu for our application, to which between the list and map views.
 * @author Faiz
 *
 */
public class TabMenu extends TabActivity {
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);
        Resources res = getResources();
        TabHost tabHost = getTabHost();       
      
        //Create two tabs for the list and map view
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("List").setContent(new Intent(this, ListActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Map").setContent(new Intent(this, TourGuideMapActivity.class)));
        //Set the initial tab to be the first (the list).
        tabHost.setCurrentTab(0);
    }
	
	
}