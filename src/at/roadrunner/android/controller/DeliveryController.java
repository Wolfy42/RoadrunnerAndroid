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
		
		
		Address from = new Address("Franziskus Domig", "Susergasse 29", "6800", "Feldkirch", "AT");
		Address to = new Address("Matthias Schmid", "Feldkircherstraße 33a", "6820", "Frastanz", "TA");
		Delivery delivery = new Delivery("aldkfjop34ukfdjoi3u4ldf");
		delivery.addItem(new Item("7027309480934", 123098702));
		delivery.setDestination(to);
		delivery.setFrom(from);

		_deliveries.add(delivery);
		_deliveries.add(delivery);
		_deliveries.add(delivery);
		_deliveries.add(delivery);
		return _deliveries;
	}
}

