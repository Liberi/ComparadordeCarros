package comparador.com.comparadordecarros;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.database.Cursor;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comparador.com.comparadordecarros.adapter.ListViewAdapter;
import comparador.com.comparadordecarros.adapter.SampleItem;
import comparador.com.comparadordecarros.database.DatabaseManager;
import comparador.com.comparadordecarros.helper.ImageLoader;
import comparador.com.comparadordecarros.helper.Setup;



public class Main extends ListActivity {

    private JSONArray resultado;
    private JSONObject item;

    private ImageView img1;
    private ImageView img2;
    private ImageView fechar_img1;
    private ImageView fechar_img2;

    private Button btncomparar;

    private ListView stringListView;

    private int img1_control = 0;
    private int img2_control = 0;
    private int position1 = 0;
    private int position2 = 0;

    private int loadruns;
    private SharedPreferences sharedPreferences;

    private static final String PREFERENCES = "Preferences";

    static protected SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void SaveRuns(String key, int value) {
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void LoadRuns() {
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        loadruns = sharedPreferences.getInt("runs", 1);
    }


    private void _init(){

        LoadRuns();

        //Verifico se é a primeira execução pra copiar os arquivos do assets e gerar o sqlite
        if (loadruns == 1) {
            Setup setup = new Setup(this);
            setup.init();
        }

        loadruns++;
        SaveRuns("runs", loadruns);

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.Creator();

        //Select para popular a listview
        Cursor Data = databaseManager.Select("SELECT Carros.id, Carros.modelo, Carros.marca, Carros.ano ,Carros.img, Motores.tipo, Motores.cilindrada FROM Carros INNER JOIN Motores\n" +
                " ON Motores.id = Carros.id_motor;");

        resultado = new JSONArray();

        Data.moveToFirst();

        if (Data.getCount() > 0) {
            while (true) {
                item = new JSONObject();
                try {
                    item.put("Carros.id", Data.getString(0));
                    item.put("Carros.modelo", Data.getString(1));
                    item.put("Carros.marca", Data.getString(2));
                    item.put("Carros.ano", Data.getInt(3));
                    item.put("Carros.img", Data.getString(4));
                    item.put("Motores.tipo", Data.getString(5));
                    item.put("Motores.cilindrada", Data.getDouble(6));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                resultado.put(item);

                if (!Data.moveToNext())
                    break;

            }
        }

    }

    private void initObjects(){
        //instancia objetos
        img1 = (ImageView) findViewById(R.id.carro1);
        img2 = (ImageView) findViewById(R.id.carro2);

        fechar_img1 = (ImageView) findViewById(R.id.fechar_carro1);
        fechar_img2 = (ImageView) findViewById(R.id.fechar_carro2);

        btncomparar = (Button) findViewById(R.id.btncomparar);

        stringListView = (ListView) findViewById(android.R.id.list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _init();
        initObjects();

        btncomparar.setEnabled(false);

        btncomparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Resultado.class);
                i.putExtra("position1", position1);
                i.putExtra("position2", position2);
                startActivity(i);
            }
        });

        fechar_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1_control = 0;
                img1.setImageResource(R.drawable.defaultcar);
                btncomparar.setEnabled(false);
            }
        });

        fechar_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2_control = 0;
                img2.setImageResource(R.drawable.defaultcar);
                btncomparar.setEnabled(false);
            }
        });

        ArrayAdapter adapter = new ListViewAdapter(this);

        try {

            JSONObject obj;

            for (int i = 0; i < resultado.length(); i++){

                obj = resultado.getJSONObject(i);

                SampleItem sampleItem = new SampleItem(
                        obj.getString("Carros.modelo") + " / " + obj.getString("Carros.marca"),
                        obj.getInt("Carros.ano"),
                        "Motor " + obj.getDouble("Motores.cilindrada") + " " + obj.getString("Motores.tipo"),
                        obj.getString("Carros.img"));

                adapter.insert(sampleItem, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        stringListView.setAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.Creator();

        Cursor Data = databaseManager.Select("SELECT Carros.id," +
                " Carros.modelo," +
                " Carros.marca," +
                " Carros.ano," +
                " Carros.img," +
                " Motores.tipo," +
                " Motores.cilindrada FROM Carros INNER JOIN Motores\n" +
                " ON Motores.id = Carros.id_motor WHERE Carros.id = " + (position + 1));

        resultado = new JSONArray();

        Data.moveToFirst();
            item = new JSONObject();
                try {
                    item.put("Carros.id", Data.getString(0));
                    item.put("Carros.modelo", Data.getString(1));
                    item.put("Carros.marca", Data.getString(2));
                    item.put("Carros.ano", Data.getInt(3));
                    item.put("Carros.img", Data.getString(4));
                    item.put("Motores.tipo", Data.getString(5));
                    item.put("Motores.cilindrada", Data.getDouble(6));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                resultado.put(item);
        try {

            ImageLoader loader = new ImageLoader();

            //Controla a adesão e remoção de imagens
            if ((img1_control == 0) && (img2_control == 0)){
                img1.setImageBitmap(loader.loadImages(resultado.getJSONObject(0).getString("Carros.img")));
                img1_control = 1;
                position1 = position + 1;
            } else if (img1_control == 1){
                img2_control = 1;
                img2.setImageBitmap(loader.loadImages(resultado.getJSONObject(0).getString("Carros.img")));
                position2 = position + 1;
            } else if (img1_control == 0){
                img1.setImageBitmap(loader.loadImages(resultado.getJSONObject(0).getString("Carros.img")));
                img1_control = 1;
                position1 = position + 1;
            } else if (img2_control == 0){
                img2_control = 1;
                img2.setImageBitmap(loader.loadImages(resultado.getJSONObject(0).getString("Carros.img")));
                position2 = position + 1;
            }

            if ((img1_control == 1) && (img2_control == 1))
                btncomparar.setEnabled(true);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

