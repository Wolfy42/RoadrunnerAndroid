/*
 * SensorProvider.java
 */
package at.roadrunner.android.sensor;

/**
 * Class SensorProvider
 * 
 * @author matthias schmid
 * @date 25.03.2011
 * 
 * 
 */
public class SensorProvider {

	// /**
	// * The SensorConnectionFactory for opening connections to the specific
	// * Sensors and it's Protocols
	// */
	// protected SensorConnectionFactory _connection_fact;
	//
	// /**
	// * Constructor
	// */
	// public SensorProvider() {
	// _connection_fact = new SensorConnectionFactory();
	// }
	//
	//
	// /**
	// * Discovers all available sensors
	// *
	// * @param sensorModelList the Sensor Model received from CouchDB
	// * @return List<Sensor> the registered sensors of container
	// * @throws IOException
	// * @throws JSONException
	// * @throws CouchDBNotReachableException
	// */
	// public List<Sensor> discover(
	// List<Sensor> sensorModelList)
	// throws IOException, CouchDBNotReachableException, JSONException {
	//
	// List<Sensor> sensors = new ArrayList<Sensor>();
	// for (Sensor s : sensorModelList) {
	//
	// switch(s.getProtocol()){
	// case HTTP:
	// sensors.add(new HttpSensor(new URL(s.getUri()), s.getType(),
	// _connection_fact));
	// break;
	// case BLUETOOTH:
	// case ZIGBEE:
	// throw new UnknownServiceException("The Protocol " +
	// s.getProtocol() + " is not supported at the moment.");
	// }
	// }
	// return sensors;
	// }

}
