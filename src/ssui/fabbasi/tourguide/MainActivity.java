package ssui.fabbasi.tourguide;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	
	//Button to keep track of download click request
	private Button download;
	private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Assign the appropriate button
        download = (Button) findViewById(R.id.button1);
        
        
        download.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gangam);
				if(mediaPlayer != null){
					mediaPlayer.start(); // no need to call prepare(); create() does that for you
				}
				
			}
		});
    
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	mediaPlayer.release();
    	mediaPlayer = null;
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	if(mediaPlayer != null && mediaPlayer.isPlaying()){
    		mediaPlayer.pause();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
