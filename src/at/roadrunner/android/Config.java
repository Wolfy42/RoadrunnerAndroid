package at.roadrunner.android;

public class Config {

	// local database
	public static final String LOCAL_INI = "/sdcard/couch/etc/couchdb/local.ini";
	
	// Default preferences settings
	public static final String ROADRUNNER_SERVER_NAME = "roadrunner";
	public static final String ROADRUNNER_SERVER_IP = "172.16.102.224";
	public static final String ROADRUNNER_SERVER_PORT = "5984";
	public static final String ROADRUNNER_AUTHENTICATION_USER = "roadrunner";
	public static final String ROADRUNNER_AUTHENTICATION_PASSWORD = "roadrunner";
	
	public static final String DATABASE = "roadrunner";
	public static final String USERNAME = "roadrunner";
	public static final String PASSWORD = "roadrunner";
	
	public static final String PROTOCOL = "http://";
	public static final String URL = "127.0.0.1";
	public static final String PORT = "5984";
	
	public static final String HOST = PROTOCOL + URL + ":" + PORT + "/";
	public static final String AUTH_HOST = PROTOCOL + USERNAME + ":" + PASSWORD + "@" + URL + ":" + PORT + "/";
	
	public static final String DB_HOST = PROTOCOL + URL + ":" + PORT + "/" + DATABASE + "/";
	
	public static boolean ERROR_LOG = true;
}
