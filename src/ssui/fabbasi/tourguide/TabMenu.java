package ssui.fabbasi.tourguide;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabMenu extends TabActivity {
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);
        Resources res = getResources();
        TabHost tabHost = getTabHost();       
      
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("", res.getDrawable(R.drawable.list_icon)).setContent(new Intent(this, TourGuideMapActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("", res.getDrawable(R.drawable.map_icon)).setContent(new Intent(this, TourGuideMapActivity.class)));
        tabHost.setCurrentTab(0); 
    }
	
	
}