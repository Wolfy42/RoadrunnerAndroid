package at.roadrunner.android.couchdb;

public class CouchDBException extends Exception {

	private static final long serialVersionUID = 1L;

	public CouchDBException(String message) {
		super(message);
	}

	public static CouchDBException badRequest() {
		return new CouchDBException("400: Bad Request");
	}

	public static CouchDBException internalServerError() {
		return new CouchDBException("500: Internal Server Error");
	}

}
