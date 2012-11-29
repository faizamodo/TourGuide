Hi.

To run this application, be sure that the Project Build Target is set to Google APIs v10 (2.3.3). It may default to Android 2.3.3 instead, which will create some build errors (namely in Locale, LocaleOverlay, and TourGuideMapActivity, as they use the Google API).

To test the location gathering abilities of the application via the emulator, do the following:

In a terminal, run: (note, 5554 is the port number. If your emulator has a different port number, use that one instead of 5554).
telnet localhost 5554

After connecting to the device, run: (note, longitude should be a value between -180 and 180, and latitude between 0 and 90).

geo fix longitude latitude

Be sure to first activate the TourGuideMapActivity to test location changes, as I've added logic to prevent calls to the activity
when it is in the background.

Otherwise, everything else should work smoothly. Enjoy!!