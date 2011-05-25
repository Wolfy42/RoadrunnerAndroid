package at.roadrunner.android.controller;

import java.util.ArrayList;

import android.content.Context;
import at.roadrunner.android.model.Address;
import at.roadrunner.android.model.Delivery;
import at.roadrunner.android.model.Item;

public class DeliveryController {

	private Context _context;
	
	public DeliveryController(Context context) {
		_context = context;
	}

	public ArrayList<Delivery> getDeliveries() {
		ArrayList<Delivery> _deliveries = new ArrayList<Delivery>();
		
		Address from = new Address("Franziskus Domig", "Susergasse 29", "6800", "Feldkirch", "Austria");
		Address to = new Address("Matthias Schmid", "Feldkircherstraße 33a", "6820", "Frastanz", "Austria");
		Address to1 = new Address("Stefan Gassner", "56", "6741", "Raggal", "Austria");
		Address to2 = new Address("Florian Gopp", "Bergstraße 24", "6835", "Zwischenwasser", "Austria");
		
		Delivery delivery1 = new Delivery("aldkfjop34ukfdjoi3u4ldf");
		delivery1.addItem(new Item("Notebook", 10, 20));
		delivery1.addItem(new Item("USB Stick", 0, 10));
		delivery1.addItem(new Item("Bildschirm", 20, 30));
		delivery1.setDestination(to);
		delivery1.setFrom(from);
		
		Delivery delivery2 = new Delivery("23t5124353242323");
		delivery2.addItem(new Item("Tastatur", 10, 20));
		delivery2.setDestination(to1);
		delivery2.setFrom(from);
		
		Delivery delivery3 = new Delivery("asdfasdf");
		delivery3.addItem(new Item("Mouse", 0, 10));
		delivery3.setDestination(to1);
		delivery3.setFrom(to2);

		_deliveries.add(delivery1);
		_deliveries.add(delivery2);
		_deliveries.add(delivery3);
		return _deliveries;
	}
}

