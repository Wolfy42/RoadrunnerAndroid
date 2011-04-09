package at.roadrunner.android.model;

public class Address {
	private String _recipient;
	private String _company;
	private String _street;
	private String _zip;
	private String _city;
	private String _country;
	
	public Address() {
	}

	public String getRecipient() {
		return _recipient;
	}

	public void setRecipient(String recipient) {
		_recipient = recipient;
	}

	public String getCompany() {
		return _company;
	}

	public void setCompany(String company) {
		_company = company;
	}

	public String getStreet() {
		return _street;
	}

	public void setStreet(String street) {
		_street = street;
	}

	public String getZip() {
		return _zip;
	}

	public void setZip(String zip) {
		_zip = zip;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public String getCountry() {
		return _country;
	}

	public void setCountry(String country) {
		_country = country;
	}
}
