package at.roadrunner.android.activity;

import java.io.IOException;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import at.roadrunner.android.R;
import at.roadrunner.android.model.Delivery;
import at.roadrunner.android.util.AddressOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class DeliveryMap extends MapActivity {
	private Geocoder _geocoder;
	private MapView _mapView;
	private static final double FACTOR = 1E6;
	private AddressOverlay _addressOverlay;
	private List<Overlay> _addressOverlays;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    
	    // get the Delivery Object
	    Bundle extras = getIntent().getExtras();
        Delivery delivery = (Delivery) extras.getSerializable("Delivery");
        
        if (delivery != null) {
        	_geocoder = new Geocoder(this);
        	_mapView = (MapView) findViewById(R.id.mapview);
        	_mapView.invalidate();
        	GeoPoint geoFrom = null;
        	GeoPoint geoTo = null;
        	
        	// create overlays
        	_addressOverlays = _mapView.getOverlays();
       	    Drawable drawable = this.getResources().getDrawable(R.drawable.ic_deliverymap_marker);
       	    _addressOverlay = new AddressOverlay(drawable, getApplicationContext());
        	
       	    // get lat/lng values of addresses
	       	List<Address> from = null;
	       	List<Address> to = null;
       	    try {
				from = _geocoder.getFromLocationName(delivery.getAddressFrom().getFormatedAddressForGoogleMap(), 1);
				to = _geocoder.getFromLocationName(delivery.getAddressTo().getFormatedAddressForGoogleMap(), 1);
				
				if (from != null) {
					if (from.size() > 0) {
						geoFrom = new GeoPoint((int) (from.get(0).getLatitude() * FACTOR), (int) (from.get(0).getLongitude() * FACTOR));
						_addressOverlay.addOverlay(new OverlayItem(geoFrom, "From", delivery.getAddressFrom().toString()));
					}
				}
				
				if (to != null) {
					if (to.size() > 0) {
						geoTo = new GeoPoint((int) (to.get(0).getLatitude() * FACTOR), (int) (to.get(0).getLongitude() * FACTOR));
						_addressOverlay.addOverlay(new OverlayItem(geoTo, "To", delivery.getAddressTo().toString()));
					}
				}
				_addressOverlays.add(_addressOverlay);
				
				if (geoFrom != null) { 
					_mapView.getController().animateTo(geoFrom);
					_mapView.getController().setZoom(15);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
