package at.roadrunner.android;

public class Config {

	public static String LOCAL_INI = "/sdcard/couch/etc/couchdb/local.ini";
	
	public static String USERNAME = "roadrunner";
	public static String PASSWORD = "roadrunner";
	
	public static String PROTOCOL = "http://";
	public static String URL = "127.0.0.1";
	public static String PORT = "5984";
	
	public static String HOST = PROTOCOL + URL + ":" + PORT + "/";;
	public static String AUTH_HOST = PROTOCOL + USERNAME + ":" + PASSWORD + "@" + URL + ":" + PORT + "/";
	
}
