package at.zeecode.monkeyman.einrichtungenindernaehe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import at.zeecode.monkeyman.sehenswuerdigkeitenindernaehe.R;

/**
 * Created by MonkeyMan on 16.06.2016.
 */
public class Karte extends Activity{
    GoogleMap map;
    LatLng latLng;
    CameraPosition camera;
    MyPlace p;
    MyMarker myMarker;
    private HashMap<Marker, MyMarker> mMarkersHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        p = null;
        if (params != null) {
            p = (MyPlace) params.get("MyPlace");
        }
        mMarkersHashMap = new HashMap<>();
        new HttpGetBitmap().execute(p.getIcon());
    }

    class HttpGetBitmap extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String link = params[0];
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(link);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setAllowUserInteraction(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.i("abc", e.getLocalizedMessage());
            } finally {
                if (null != connection) {
                    connection.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            latLng = new LatLng(p.getLatitude(), p.getLongitude());
            MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map_fragment);
            map = mapFragment.getMap();
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.setMyLocationEnabled(true);
            map.setInfoWindowAdapter(new MyMarkerAdapter());
            camera = CameraPosition.builder().target(latLng).zoom(14).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(camera));
            myMarker = new MyMarker(latLng, bitmap, p.getName(), p.getAddress());
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(p.getName()).
                    snippet(p.getAddress()));
            mMarkersHashMap.put(marker, myMarker);
            super.onPostExecute(bitmap);
        }
    }

    class MyMarkerAdapter implements GoogleMap.InfoWindowAdapter{
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            MyMarker m = mMarkersHashMap.get(marker);
            View v = getLayoutInflater().inflate(R.layout.infowindowview,null);
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            TextView tvName = (TextView) v.findViewById(R.id.textViewName);
            TextView tvAdresse = (TextView) v.findViewById(R.id.textViewAdress);
            img.setImageBitmap(m.getMyMarkerBitmap());
            tvName.setText(m.getMyMarkerName());
            tvAdresse.setText(m.getMyMarkerAddresse());
            return v;
        }
    }
}
