package com.eastereggdevelopment.minecraftworkbench;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ServerFinderC extends AppCompatActivity {

    public ListView selectCountry;
    public String[] country;
    public ArrayAdapter<String> listadapter;

    private int cView;

    //finale Variable die an die Klasse ServerFinder übergeben werden soll
    public String c;
    SharedPreferences gmData;
    public static String gmdata = "gamemodeIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findercountry);

        country = getResources().getStringArray(R.array.Country); //holt die Resourcen für das gamemode Array aus strings.xml
        gmData = getSharedPreferences(gmdata, 0); //Lade SharedPreferences File beim öffnen der Klasse

        //deklariert ListView mit dem Country Array und OnItemClick Methode
        selectCountry = (ListView) findViewById(R.id.selectCountry);
        listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,country);
        selectCountry.setAdapter(listadapter);

        //weißt der Variable c das Country zu auf das geklickt wurde
        selectCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cView = selectCountry.getPositionForView(view);
                c = country[cView];
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_SHORT).show(); //zeigt den String der gespeichert wurde (Test)

                //Speichere das Land auf das geklickt wurde in SharedPreferences
                SharedPreferences.Editor editor = gmData.edit();
                editor.putInt(c, cView);
                editor.putString("country", c);
                editor.commit();

                openServerFinder();
            }
        });
    }

    public void openServerFinder(){
        Intent intent = new Intent(this, ServerFinder.class);
        startActivity(intent);
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
