package ssui.fabbasi.tourguide;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends MapActivity {
	
	//Button to keep track of download click request
	private Button change;
	
	MapController mapController;
	MapView mapview;
	LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapview = (MapView)findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true);
		mapController = mapview.getController();
	    mapController.setZoom(12);
        
        //Assign the appropriate button
        change = (Button) findViewById(R.id.button1);
        
        
        change.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
			}
		});
    
    }
    


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

    
}
