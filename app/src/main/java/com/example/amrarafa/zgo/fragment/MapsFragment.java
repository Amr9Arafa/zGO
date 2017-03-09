package com.example.amrarafa.zgo.fragment;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amrarafa.zgo.DBHelper;
import com.example.amrarafa.zgo.POJO.Example;
import com.example.amrarafa.zgo.R;
import com.example.amrarafa.zgo.RetrofitMaps;
import com.example.amrarafa.zgo.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback
        {


    private MapView mapView;
    private GoogleMap mMap;
    private boolean mapReady;
    private MarkerOptions markerOptions1;
    private MarkerOptions markerOptions2;
    private LatLng origin;
    private LatLng dest;
    private Polyline line;
    ArrayList<LatLng> MarkerPoints;
    private TextView ShowDistanceDuration;
    private TextView absoluteDistance;
    private DBHelper dbHelper;
    private View mView;


    public MapsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =inflater.inflate(R.layout.fragment_map, container, false);
        ShowDistanceDuration= (TextView)mView.findViewById(R.id.show_distance_time);
        absoluteDistance=(TextView)mView.findViewById(R.id.absolute_distance);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getActivity());

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        MarkerPoints = new ArrayList<>();



        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        mMap = map;
        mapReady=true;


        LatLng newYork=new LatLng(40.7484,-73.9875);
        CameraPosition target=CameraPosition.builder().target(newYork).zoom(14).build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

//                if (markerOptions1==null){
//                    markerOptions1= new MarkerOptions().position(latLng).title("1");
//
//                    mMap.addMarker(markerOptions1);
//                }
//
//                else if(markerOptions2==null){
//                    markerOptions2= new MarkerOptions().position(latLng).title("2");
//                    mMap.addMarker(markerOptions2);
//
//
//                    mMap.addPolyline(new PolylineOptions().geodesic(true)
//                            .add(markerOptions1.getPosition())
//                            .add(markerOptions2.getPosition()));
//
//                    Location l= new Location("a");
//
//
//                    Double distance= calculationByDistance(markerOptions1.getPosition(),markerOptions2.getPosition());
//                    Toast.makeText(getActivity(),""+distance,Toast.LENGTH_SHORT).show();
//
//                }

//                Toast.makeText(getActivity(),"put markers",Toast.LENGTH_SHORT).show();


                if (MarkerPoints.size() > 1) {
                    mMap.clear();
                    MarkerPoints.clear();
                    MarkerPoints = new ArrayList<>();
                    ShowDistanceDuration.setText("");
                    absoluteDistance.setText("");
                    //no distance
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (MarkerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (MarkerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }


                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    origin = MarkerPoints.get(0);
                    dest = MarkerPoints.get(1);
                    build_retrofit_and_get_response("driving");
                    mMap.addPolyline(new PolylineOptions().geodesic(true).add(origin).add(dest));
                    Double distance=calculationByDistance(origin,dest)/1000;
                    absoluteDistance.setText("absoluteDistance:"+String.format("%2f",distance)+"KM");
                }



            }
        });

    }



            public double calculationByDistance(LatLng startP, LatLng endP) {
                Location locationA = new Location("point A");
                locationA.setLatitude(startP.latitude);
                locationA.setLongitude(startP.longitude);
                Location locationB = new Location("point B");
                locationB.setLatitude(endP.latitude);
                locationB.setLongitude(endP.longitude);
                double distance = locationA.distanceTo(locationB);
                return distance;
            }


            private void build_retrofit_and_get_response(String type) {

                String url = "https://maps.googleapis.com/maps/";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitMaps service = retrofit.create(RetrofitMaps.class);

                Call<Example> call = service.getDistanceDuration("metric", origin.latitude + "," + origin.longitude,dest.latitude + "," + dest.longitude, type);

                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Response<Example> response, Retrofit retrofit) {

                        try {
                            //Remove previous line from map
                            if (line != null) {
                                line.remove();
                            }
                            // This loop will go through all the results and add marker on each location.
                            for (int i = 0; i < response.body().getRoutes().size(); i++) {
                                String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                                String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                                ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                                captureMapScreen();
                                Toast.makeText(getActivity(),"Distance:" + distance + ", Duration:" + time ,Toast.LENGTH_LONG).show();
                                String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                                List<LatLng> list = decodePoly(encodedString);
                                line = mMap.addPolyline(new PolylineOptions()
                                        .addAll(list)
                                        .width(20)
                                        .color(Color.RED)
                                        .geodesic(true)
                                );
                            }
                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("onFailure", t.toString());
                    }
                });

            }


            private List<LatLng> decodePoly(String encoded) {
                List<LatLng> poly = new ArrayList<LatLng>();
                int index = 0, len = encoded.length();
                int lat = 0, lng = 0;

                while (index < len) {
                    int b, shift = 0, result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lat += dlat;

                    shift = 0;
                    result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lng += dlng;

                    LatLng p = new LatLng( (((double) lat / 1E5)),
                            (((double) lng / 1E5) ));
                    poly.add(p);
                }

                return poly;
            }


             private  void saveImageInDB(Bitmap bitmap) {


                byte[] image=Utility.getImageBytes(bitmap);
                try {
                    dbHelper.open();
                    dbHelper.insertImage(image);


                } catch (Exception e) {


                }

            }




            public void captureMapScreen() {
                GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

                    @Override
                    public void onSnapshotReady(Bitmap snapshot) {
                        try {
                            mView.setDrawingCacheEnabled(true);
                            Bitmap backBitmap = mView.getDrawingCache();
                            Bitmap bmOverlay = Bitmap.createBitmap(
                                    backBitmap.getWidth(), backBitmap.getHeight(),
                                    backBitmap.getConfig());
                            Canvas canvas = new Canvas(bmOverlay);
                            canvas.drawBitmap(snapshot, new Matrix(), null);
                            canvas.drawBitmap(backBitmap, 0, 0, null);
                            saveImageInDB(bmOverlay);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                mMap.snapshot(callback);

            }

}
