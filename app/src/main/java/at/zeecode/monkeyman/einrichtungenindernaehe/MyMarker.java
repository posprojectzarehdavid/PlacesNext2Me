package at.zeecode.monkeyman.einrichtungenindernaehe;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MonkeyMan on 22.06.2016.
 */
public class MyMarker{
    LatLng ll;
    Bitmap myMarkerBitmap;
    String myMarkerName, myMarkerAddresse;

    public MyMarker(LatLng ll, Bitmap bitmap, String name, String addresse) {
        this.ll = ll;
        this.myMarkerBitmap = bitmap;
        this.myMarkerName = name;
        this.myMarkerAddresse = addresse;
    }

    public Bitmap getMyMarkerBitmap() {
        return myMarkerBitmap;
    }

    public String getMyMarkerName() {
        return myMarkerName;
    }

    public String getMyMarkerAddresse() {
        return myMarkerAddresse;
    }
}
