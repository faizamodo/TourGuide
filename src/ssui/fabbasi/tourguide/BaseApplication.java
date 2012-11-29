package ssui.fabbasi.tourguide;

import android.app.Application;

/**
 * This class extends the Application class to give the ability to tell if the application is in the foreground or background.
 * @author Faiz
 *
 */
public class BaseApplication extends Application {

	public static boolean isActivityVisible() {
		return activityVisible;
	}  

	public static void activityResumed() {
		activityVisible = true;
	}

	public static void activityPaused() {
		activityVisible = false;
	}

	private static boolean activityVisible;
}