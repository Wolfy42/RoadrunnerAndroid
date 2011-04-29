package at.roadrunner.android.model;

import java.io.Serializable;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _recipient;
	private String _company;
	private String _street;
	private String _zip;
	private String _city;
	private String _country;
	
	public Address() {
	}
	
	public Address(String recipient, String company, String street, String zip, String city, String country) {
		_recipient = recipient;
		_company = company;
		_street = street;
		_zip = zip;
		_city = city;
		_country = country;
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

	@Override
	public String toString() {
		return _recipient + " (" + _company + ") - " + _street + ", " + _zip + " " + _city + " - " + _country;
	}
	
	
}
