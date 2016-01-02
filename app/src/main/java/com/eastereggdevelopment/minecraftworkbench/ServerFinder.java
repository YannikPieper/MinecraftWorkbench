package com.eastereggdevelopment.minecraftworkbench;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerFinder extends AppCompatActivity {

    private String gm1;
    public ListView gamemodes;

    //Variablen für UI
    public String newItem; //Name des Gametypes passend zum int gametype
    public String selectedCountry;

    //Variablen für JSON Objekt
    public int gametype;
    public int selCountry;
    public int minSize;
    public int maxSize;

    SharedPreferences gmData;
    public static String gmdata = "gamemodeIndex";

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    //Parameter für JSON
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finder);

        gmData = getSharedPreferences(gmdata, 0); //Lade SharedPreferences File beim öffnen der Klasse
        newItem = gmData.getString("gametype", "Nothing Selected");
        selectedCountry = gmData.getString("country", "Country");

        gametype = gmData.getInt(newItem, 0);
        selCountry = gmData.getInt(selectedCountry, 0);

        //gm1 = "Select a Gamemode";

        //erstellt ListView und zeigt Daten
        gamemodes = (ListView) findViewById(R.id.gamemodeList); //deklariert listView
        String[] items={}; //setzt listView Items fest
        arrayList=new ArrayList<>(Arrays.asList(items));
        arrayAdapter=new ArrayAdapter<String>(this, R.layout.list_item,R.id.txtitem,arrayList);
        gamemodes.setAdapter(arrayAdapter);

        //fügt ausgewählten gamemode von ServerFinderGM hinzu und bestätigt
        arrayList.add(newItem);
        arrayAdapter.notifyDataSetChanged();
        showToast();

        //ändert Text des Country Button auf ausgewähltes Land
        Button country = (Button) findViewById(R.id.setCountry);
        country.setText(selectedCountry);
    }

    //öffnet die Klasse ServerFinderGM um einen gamemode hinzuzufügen
    public void setGamemode(View view){
        Intent intent = new Intent(this, ServerFinderGM.class);
        startActivity(intent);
    }

    public void setCountry(View view){
        Intent intent = new Intent(this, ServerFinderC.class);
        startActivity(intent);
    }

    public void showToast(){
        Toast.makeText(getApplicationContext(), "Gametype: " + gametype, Toast.LENGTH_SHORT).show();
    }

    //Methode um die Server Suche zu starten(JSON)
    public void startSearch(View view){
        EditText minS = (EditText) findViewById(R.id.minSize);
        EditText maxS = (EditText) findViewById(R.id.maxSize);

        minSize = Integer.valueOf(minS.getText().toString()).intValue();
        maxSize = Integer.valueOf(maxS.getText().toString()).intValue();

        Toast.makeText(getApplicationContext(), "min Size: " + minSize, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "max Size: " + maxSize, Toast.LENGTH_SHORT).show();

        StringBuilder sb = new StringBuilder();

        String http = "myURL";


        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type","application/json");

            urlConnection.setRequestProperty("Host", "myUrl");
            urlConnection.connect();

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("gamemode", gametype);
            jsonParam.put("country", selCountry);
            jsonParam.put("min_size", minSize);
            jsonParam.put("max_size", maxSize);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println(""+sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }


        //JSON Objekt vom Server
        /*requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "myURL",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray = response.getJSONArray("");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
