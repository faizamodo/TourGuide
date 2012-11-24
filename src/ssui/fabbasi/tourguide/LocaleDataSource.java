package ssui.fabbasi.tourguide;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * This is the data access object that creates and maintains a connection to the SQLiteDatabase and supports adding new locales and fetching all locales.
 * @author Faiz
 *
 */
public class LocaleDataSource {
	
	//Database fields
	private Context context;
	private static SQLiteDatabase database;
	private MySQLiteHelper myHelper;
	private String[] allColumns;
    private SQLiteStatement insertStmt;
    
    //Insert statement
    private static final String INSERT = "insert into locales (name, latitude, longitude, description) values (?,?,?,?)";
	
	/**
	 * Initialize a new MySQLiteHelper object
	 * @param context The context of the application.
	 */
	public LocaleDataSource(Context context){
		this.context = context;
		myHelper = new MySQLiteHelper(this.context);
		open();
        this.insertStmt = LocaleDataSource.database.compileStatement(INSERT);
	}
	
	/**
	 * Initialize a new writable SQLiteDatabase
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = myHelper.getWritableDatabase();
		System.out.println(database.toString());

	}
	
	/**
	 * Close any open database object. 
	 */
	public void close(){
		myHelper.close();
	}
	
	//Database Manipulations
	
	/**
	 * Insert a new locale into the SQLite database
	 * @param name The name of the locale
	 * @param latitude The latitude of the locale (double)
	 * @param longitude The longitude of the locale (double)
	 * @param description The description of the locale. To be shown to the user.
	 * @return
	 */
	public long insertLocale(String name, double latitude, double longitude, String description, int icLauncher){
		this.insertStmt.bindString(1, name);
		this.insertStmt.bindDouble(2, latitude);
		this.insertStmt.bindDouble(3, longitude);
		this.insertStmt.bindString(4, description);
		this.insertStmt.bindDouble(5, icLauncher);
		
        return this.insertStmt.executeInsert();

	}
	
	/**
	 * Remove a locale from the database, based on its row ID
	 * @param rowId the id of the locale
	 */
	public void delete(int rowId){
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + "=" + rowId, null); 
	}
	
	/**
	 * Retrieve all locales in the database, in the order that they were inserted into the database.
	 * @return
	 */
	public List<Locale> getAllLocales(){
		List<Locale> locales = new ArrayList<Locale>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Locale locale = cursorToLocale(cursor);
			locales.add(locale);
			cursor.moveToNext();
		}
		
		return locales;
	}
	
	/**
	 * Call this method to prepopulate the database with data.
	 * 
	 */
	public void prepopulate(){
		//UC
		insertLocale("University Center", 40.443403, -79.942104, "Located next to Gesling Stadium, the University Center (UC) is the student hub " +
				"of activity on campus. You can pick up your snail mail, get career advice, visit the chapel or find a quiet " +
				"space to study. There are several dining options, recreational fitness facilities, a retail store, a convenience " +
				"store and a 450-seat state-of-the-art theater – these are only a few reasons why you would visit the UC at least once per day.", R.drawable.ic_launcher);
		//Purnell
		insertLocale("Purnell Center for the Arts", 40.443567, -79.943546, "The Purnell Center for the Arts is located across “the Cut” from the " +
				"University Center and is home to the School of Drama, the oldest degree-granting drama program in the country. Purnell includes " +
				"state-of-the-art performance and education facilities including the Philip Chosky Theater, a studio theater, dance and movement studios, " +
				"in addition to faculty offices and classrooms. Also in Purnell you will find the Regina Gouger Miller Gallery, a non-collecting public " +
				"art gallery and a bridge connecting to the Gates Center for Computer Science.", R.drawable.ic_launcher);
		//Hamburg
		insertLocale("Hamburg Hall", 40.444326, -79.945537, "This building houses the H. John Heinz III College and is the only one of Carnegie Mellon’s seven schools " +
				"and colleges focused entirely on graduate degree programs. Founded in 1968, the school was restructured and renamed Heinz College, " +
				"after Senator John Heinz, in 2008. The college consists of the School of Information Systems and Management, and the School of Public " +
				"Policy and Management. This structure allows the college to leverage what it does best: combine technical expertise and the social " +
				"sciences to help solve real-world problems.", R.drawable.ic_launcher);
		//Newell Simon
		insertLocale("Newell Simon Hall", 40.443416, -79.945665, "This building was named after Allen Newell and Herbert A. Simon – two founders of artificial intelligence. " +
				"It houses offices, meeting facilities and project labs for School of Computer Science (SCS) faculty and staff and includes the Human-Computer Interaction, " +
				"Robotics and Language Technologies Institutes. There are bridges to Wean Hall and the Gates Center for Computer Science and Smith Hall, also home to " +
				"CS and engineering facilities, is close by.", R.drawable.ic_launcher);
		//Doherty
		insertLocale("Doherty Hall", 40.442444, -79.944544, "Doherty is one of the first of a few buildings you will find that creates a U-shape surrounding " +
				"the grassy area known as “the Mall.”  If you’re a first-year student, you will probably have a class in this building as it contains some of " +
				"Carnegie Mellon’s largest lecture style classrooms.  Doherty has newly renovated lab spaces for our Mellon College of Science students in addition " +
				"to lab and studio spaces for other departments like engineering, chemistry, biology and art.", R.drawable.ic_launcher);
		//Gates
		insertLocale("Gates Center for Computer Science", 40.443497, -79.944539, "The Gates Center for Computer Science and Hillman Center for Future-Generation Technologies " +
				"were made possible by lead gifts from the Bill and Melinda Gates and the Henry L. Hillman Foundations. These Gold LEED Certified Centers and Newell-Simon Hall " +
				"are home to the School of Computer Science and research centers like the Ray and Stephanie Lane Center for Computational Biology. The East-West Campus Walkway " +
				"is extended through these buildings via several bridges and walkways, like the Randy Pausch Memorial Footbridge, which connects Gates to the Purnell Center for the Arts.", R.drawable.ic_launcher);
		//Warner
		insertLocale("Warner Hall", 40.444163, -79.943353, "Warner Hall is home to the Office of Undergraduate Admission. The admission lobby on " +
				"the first floor is where prospective students can check in for their interviews and on-campus tours. Other administrative offices " +
				"in this building include the offices of the President and the Provost, in addition to the Office of International Education and " +
				"Enrollment Services. Immediately next to Warner Hall is Cyert Hall, home to Computing Services.", R.drawable.ic_launcher);
		//Wean
		insertLocale("Wean Hall", 40.442644,-79.945713, "Wean Hall is one of the buildings surrounding the grassy area known as “the Mall” and is connected to both " +
				"Doherty Hall and Newell-Simon Hall. This building is home to some of the School of Computer Science, Mellon College of Science and Carnegie Institute of " +
				"Technology (engineering) departments in addition to the Bruce and Astrid McWilliams Center for Cosmology.  Wean is one of the eight locations on campus " +
				"that has computer clusters featuring Windows, Mac and Linux platforms.", R.drawable.ic_launcher);
		
		//Hamerschlag Hall
		insertLocale("Hamerschlag Hall", 40.442407,-79.946872, "The iconic rotunda on top of Hamerschlag Hall is probably the most frequently photographed part of Carnegie Mellon. " +
				"The rotunda is actually a smokestack that leads to a former coal room, now a “clean room.” Hamerschlag is home to electrical and computer engineering, " +
				"labs for mechanical engineering, and connects to Roberts Engineering Hall which houses mostly materials engineering. Hamerschlag also boasts a living " +
				"roof – originally a small undergraduate research grant (SURG) project and has since been preserved by Carnegie Mellon’s Green Practices Committee.", R.drawable.ic_launcher);
		
		//Scaife Hall
		insertLocale("Scaife Hall", 40.441823,-79.947317, "Down the steps next to Hamerschlag Hall, is Scaife Hall, which contains the Carnegie Institute of Technology " +
				"(College of Engineering) dean’s office and the department of mechanical engineering. More engineering facilities can be found in Roberts Engineering Hall " +
				"next to Scaife. Despite the unique shape of Scaife’s roof, Carnegie Mellon’s astronomy club made a home on a more level part of Scaife’s rooftop with the " +
				"club’s observatory and its 10” Newtonian telescope to view the night sky.", R.drawable.ic_launcher);
		
		//Porter Hall
		insertLocale("Porter Hall", 40.441754,-79.946282, "Completed in 1906, Porter Hall is the oldest building on campus and is one of the architectural creations of " +
				"Henry Hornbostel, the first dean of our College of Fine Arts. Porter is home to various programs like civil and environmental engineering, information " +
				"systems, and social and decision sciences.  The building’s main entrance faces Frew Street and is adjoined with Baker Hall via a long, sloped hallway.", R.drawable.ic_launcher);		
		//Baker Hall
		insertLocale("Baker Hall", 40.441562,-79.945279, "Baker Hall is connected to Porter Hall and was one of the first buildings on campus. It is one of the most " +
				"recognizable on campus, created by the first dean of the College of Fine Arts, Henry Hornbostel. Baker is home of the Dietrich College of Humanities " +
				"and Social Sciences (DC), the second largest college on campus with more than 60 majors and minors available. In the lobby of Baker, you’ll find a " +
				"sculpture of the university’s first president, Arthur A. Hamerschlag, and you might notice that his nose is a bit worn away, due to a legend of luck.", R.drawable.ic_launcher);		
		//CFA
		insertLocale("College of Fine Arts", 40.441533, -79.942876, "This building is one of the most beautiful on campus and is an architectural creation of Henry " +
				"Hornbostel, the first dean for the College of Fine Arts (CFA). CFA is comprised of five different schools: architecture, art, design, drama and music, " +
				"as well as three unique interdisciplinary programs – the bachelor’s of humanities & arts, science & arts, and computer science & arts. A point of " +
				"interest in this building is its “green room” found backstage of Kresge Theatre – view the ‘green room’ video to learn more.", R.drawable.ic_launcher);
		//Hunt
		insertLocale("Hunt Library", 40.441133,-79.943696, "Thanks to a gift from Roy and Rachel Hunt, Hunt Library opened in 1961 as the university’s first dedicated " +
				"library building. It is the largest of five on campus with more than 600,000 volumes. When you visit campus you can’t miss Hunt Library’s unique " +
				"appearance, made out of almost all aluminum and glass – Roy Hunt was a prominent figure in the aluminum industry. On the first floor of Hunt, stop " +
				"by the Maggie Murph Café (named for Margaret Morrison Carnegie College alumnae) for a healthy snack or a quick boost of caffeine – or take a quick " +
				"nap in the EnergyPod.", R.drawable.ic_launcher);
		//Posner Center
		insertLocale("Posner Center", 40.441399, -79.942527, "The Posner Center is a gift of Henry Posner Jr. and Helen Posner. This 11,400 square foot LEED certified " +
				"building contains a collection of rare and fine books, and includes one of only four extant copies of the first printing Bill of Rights. This building " +
				"is also home to a state-of-the art executive conference room with 35 monitors capable of video conferencing and the Kraus Campo, a rooftop sculpture " +
				"garden made possible by alumna Jill Kraus, designed by alumnus and internationally acclaimed artist Mel Bochner, and landscape architect Michael Van Valkenburgh.", R.drawable.ic_launcher);
		//Posner Hall
		insertLocale("Posner Hall", 40.44117,-79.942178, "Posner Hall is home to the Tepper School of Business, named after alumnus David A. Tepper " +
				"(and his wife Marlene), who gave a naming gift of $55 million to the university - the largest in Carnegie Mellon’s history. Tepper boasts " +
				"eight Nobel laureates among its alumni and faculty ranks, and offers undergraduate, graduate, PhD and executive education programs. " +
				"While visiting Posner Hall, stop by The Exchange (formerly Ginger’s Deli) for a bite – it's been unofficially rated by students as the best place to eat on campus.", R.drawable.ic_launcher);		
		//Skibo Gym
		insertLocale("Skibo Gymnasium", 40.44108,-79.941175, "Skibo Gymnasium was built in 1924 and is the home to Carnegie Mellon’s athletic department. " +
				"Skibo hosts varsity basketball and volleyball games among a variety of other sporting recreational activities and events. Carnegie Mellon is " +
				"in the NCAA’s Division III, competes in the UAA conference and offers varsity, intramural and club sports, of which almost 80% of the student " +
				"population competes in at some level. Don’t forget to look for a certain Scottish Terrier – the university’s official mascot – at select sporting " +
				"events as “Scotty” cheers on the Tartans.", R.drawable.ic_launcher);		
		//Margaret Morrison
		insertLocale("Margaret Morrison", 40.442109,-79.941567, "Margaret Morrison Carnegie College opened in 1906 as a vocational school for women. Now called Margaret " +
				"Morrison Carnegie Hall, it hosts facilities for the School of Architecture and Design, among other disciplines, and is home to the Robert L. Preger " +
				"Intelligent Workplace – the environmentally-friendly office setting that continually adapts spaces and technologies as needed to improve the quality " +
				"of life in the workplace.", R.drawable.ic_launcher);		


	}


	private Locale cursorToLocale(Cursor cursor) {
		Locale locale = new Locale(cursor.getPosition(), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4), cursor.getDouble(5));
		
		return locale;

	}
	

}
