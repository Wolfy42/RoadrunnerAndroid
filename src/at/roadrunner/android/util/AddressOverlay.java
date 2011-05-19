package at.roadrunner.android.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class AddressOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> _overlays = new ArrayList<OverlayItem>();
	private Context _context;
	
	public AddressOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public AddressOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		_context = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
		_overlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return _overlays.get(i);
	}
	
	@Override
	public int size() {
		return _overlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
		if (_context == null) return false;
		
		OverlayItem item = _overlays.get(index);
		Toast.makeText(_context, item.getSnippet(), Toast.LENGTH_LONG).show();
		
		return true;
	}

}