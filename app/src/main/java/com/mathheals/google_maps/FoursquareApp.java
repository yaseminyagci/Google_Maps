package com.mathheals.google_maps;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 20.02.2018.
 */

public class FoursquareApp extends Activity {

    TextView edit2;
    String lat, lng;
    String link = null;
    final List<Yer> ad = new ArrayList<>();
    private static final long UZAKLIK_DEGISIMI = 20;
    private static final long SURE = 1000 * 30;
    LocationListener konumDinleyicisi;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ListView listemiz = (ListView) findViewById(R.id.listView1);


        //konumumuzu bulma
    /*    LocationManager konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         konumDinleyicisi = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "gpsAcildi", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "gpsKapatildi", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onLocationChanged(Location loc) {
               lat= toString().valueOf(loc.getLatitude());
                lng=toString().valueOf(loc.getLongitude());


                link=bul(lat,lng);

                String Text = "Bulunduğunuz list_adapter bilgileri : \n" + "Latitud = " + loc.getLatitude() + "\nLongitud = " + loc.getLongitude();
                Toast.makeText(FoursquareApp.this, Text, Toast.LENGTH_SHORT).show();


            }

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        konumYoneticisi.requestLocationUpdates(LocationManager.GPS_PROVIDER, SURE, UZAKLIK_DEGISIMI, konumDinleyicisi);
*/



        //Verilerin alındığı kısım

        try{

            JSONObject object =(JSONObject)new JSONTokener(bul()).nextValue();
            JSONObject responsem=object.getJSONObject("response");
            JSONArray venues=responsem.getJSONArray("venues");
            JSONObject j_obj;
            JSONObject location;

            for (int i=0;i<venues.length();i++){

                j_obj=venues.getJSONObject(i);
                location=j_obj.getJSONObject("location");
                String lng=location.getString("lng");
                String lat=location.getString("lat");
                Toast.makeText(this, "lat ve lng "+lng+lat, Toast.LENGTH_SHORT).show();
                String name=j_obj.getString("name");
                ad.add(new Yer(name,lat,lng));

            }

            list_adapter adaptorumuz=new list_adapter(this, ad);
            listemiz.setAdapter(adaptorumuz);
        }

        catch(JSONException e){
        }

        //listview de tıkalananı yakalarız

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                Toast.makeText(FoursquareApp.this, "gönderiyor", Toast.LENGTH_SHORT).show();
                i.putExtra("lat",ad.get(position).getLat() );
                i.putExtra("lng", ad.get(position).getLng());
                startActivity(i);


                Toast.makeText(FoursquareApp.this, "isim"+ad.get(position).getIsim()+"lat "+ad.get(position).getLat()+"lng "+ad.get(position).getLng(), Toast.LENGTH_SHORT).show();


            }
        });




    }
    //http ile içeriği çekiyoruz
   protected String bul(){
       String string_url = "https://api.foursquare.com/v2/venues/search?client_id=SBBWGNTIJI22FJL5M4FFKTKUDJONKQW1UYMM3D0E5FBHLT15&client_secret=BAIRZF1JWSJT4AC2DIWEHBRKDYPIUD4ODGSCLLZCEGQDFCJN&ll=40.7583583333334,30.3526966666663&radius=1000&v=20180212&limit=15";

       try {

           URL urlim = new URL(string_url);
           HttpURLConnection urlConnection = (HttpURLConnection) urlim.openConnection();
           try {
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
               StringBuilder stringBuilder = new StringBuilder();
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   Toast.makeText(this, "yükleniyir", Toast.LENGTH_SHORT).show();
                   stringBuilder.append(line).append("\n");
               }
               bufferedReader.close();
               return stringBuilder.toString();

           } finally {
               urlConnection.disconnect();
           }
       } catch (Exception e) {
           Log.e("ERROR", e.getMessage(), e);
           return null;

       }
   }


}





