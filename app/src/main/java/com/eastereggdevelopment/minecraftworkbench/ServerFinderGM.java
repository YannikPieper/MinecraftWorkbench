package com.eastereggdevelopment.minecraftworkbench;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ServerFinderGM extends AppCompatActivity {

    public ListView selectGamemode;
    public String[] gamemode;
    public ArrayAdapter<String> listadapter;

    private int gmView;

    //finale Variable die an die Klasse ServerFinder übergeben werden soll
    public String gm;
    SharedPreferences gmData;
    public static String gmdata = "gamemodeIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findergamemode);

        gamemode = getResources().getStringArray(R.array.gamemodes); //holt die Resourcen für das gamemode Array aus strings.xml
        gmData = getSharedPreferences(gmdata, 0); //Lade SharedPreferences File beim öffnen der Klasse

        //deklariert ListView mit dem gamemode Array und OnItemClick Methode
        selectGamemode = (ListView) findViewById(R.id.selectGamemode);
        listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gamemode);
        selectGamemode.setAdapter(listadapter);

        //weißt der Variable gm den gamemode zu auf den geklickt wurde
        selectGamemode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gmView = selectGamemode.getPositionForView(view);
                gm = gamemode[gmView];
                Toast.makeText(getApplicationContext(), gm, Toast.LENGTH_SHORT).show(); //zeigt den String der gespeichert wurde (Test)

                //Speichere den Gamemode auf den geklickt wurde in SharedPreferences
                SharedPreferences.Editor editor = gmData.edit();
                editor.putInt(gm, gmView);
                editor.putString("gametype", gm);
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
