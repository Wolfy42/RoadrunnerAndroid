package at.roadrunner.android;

import java.text.SimpleDateFormat;

public class Config {

	// local database
	public static final String LOCAL_INI = "/sdcard/couch/etc/couchdb/local.ini";
	
	// CouchDB Packages
	public static final String COUCHDB_PACKAGE = "com.couchone.couchdb";
	public static final String COUCHDB_SERVICE = "com.couchone.couchdb.CouchService";
	
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
	public static final String TIME_PORT = "10010";
	
	public static final String HOST = PROTOCOL + URL + ":" + PORT + "/";
	public static final String AUTH_HOST = PROTOCOL + USERNAME + ":" + PASSWORD + "@" + URL + ":" + PORT + "/";
	public static final String REMOTE_HOST = PROTOCOL + ROADRUNNER_SERVER_IP + "/";
	public static final String TIME_HOST = PROTOCOL + ROADRUNNER_SERVER_IP + ":" + TIME_PORT + "/";
	public static final String REMOTE_DB_HOST = PROTOCOL + ROADRUNNER_SERVER_IP + ":" + ROADRUNNER_SERVER_PORT + "/";
	
	public static final String DB_HOST = PROTOCOL + URL + ":" + PORT + "/" + DATABASE + "/";
	
	public static boolean ERROR_LOG = true;
	
	// dateformat (german)
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	
	// CouchDB - Service
	public static final String COUCH_SERVICE = "com.couchone.libcouch.ICouchService";
	
	// TemperatureSensor
	public static final String TEMPERATUR_SENSOR_URL_1 = PROTOCOL + ROADRUNNER_SERVER_IP + ":" + "10001"; 

	// Service Settings
	public static final long MONITORING_SERVICE_INTERVAL = 100000;
	public static final long LOGGING_SERVICE_INTERAL = 300000;
	public static final long REPLICATOR_SERVICE_INTERAL = 10000;
	
	public static final String DEFAULT_TRANSPORTATION = "UNKNOWN";
	
	//For Time-Synchronisation
	public static final long SERVER_RESPONSE_DELAY = 10; //Server should answer with timestamp in <= 2 seconds
	public static final long SERVER_OFFSET_FOR_CORRECTION = 5; //If the time-offset is >= 5 seconds it will be corrected
}
