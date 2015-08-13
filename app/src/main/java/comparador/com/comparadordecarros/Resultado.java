package comparador.com.comparadordecarros;
import android.app.ListActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import comparador.com.comparadordecarros.database.DatabaseManager;
import comparador.com.comparadordecarros.adapter_resultado.ListViewAdapterResultado;
import comparador.com.comparadordecarros.adapter_resultado.SampleItemResultado;
import comparador.com.comparadordecarros.helper.ImageLoader;


public class Resultado extends ListActivity {

    private int id1 = 0;
    private int id2 = 0;
    private int ponto1 = 0;
    private int ponto2 = 0;

    private JSONArray carroObj1;
    private JSONArray carroObj2;

    private ImageView resultado1;
    private ImageView resultado2;

    private TextView pontoText1;
    private TextView pontoText2;
    private TextView vencedor;

    private ListView stringListView;

    private ArrayAdapter adapter;

    private void _init(){
        Bundle extras = getIntent().getExtras();

        id1 = extras.getInt("position1");
        id2 = extras.getInt("position2");
    }

    public void initObjects(){
        stringListView = (ListView) findViewById(android.R.id.list);

        resultado1 = (ImageView) findViewById(R.id.resultado1);
        resultado2 = (ImageView) findViewById(R.id.resultado2);

        pontoText1 = (TextView) findViewById(R.id.pontos1);
        pontoText2 = (TextView) findViewById(R.id.pontos2);
        vencedor =   (TextView) findViewById(R.id.vencedor);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado);

        _init();
        initObjects();

        adapter = new ListViewAdapterResultado(this);
        stringListView.setAdapter(adapter);

        carroObj1 = getCarroById(id1);
        carroObj2 = getCarroById(id2);

        try {

            ImageLoader loader = new ImageLoader();

            JSONObject carro1 = carroObj1.getJSONObject(0);
            JSONObject carro2 = carroObj2.getJSONObject(0);

            resultado1.setImageBitmap(loader.loadImages(carro1.getString("Carros.img")));
            resultado2.setImageBitmap(loader.loadImages(carro2.getString("Carros.img")));

            comparaMaiorInt(carro1.getInt("Carros.ano"),
                    carro2.getInt("Carros.ano"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    2016, "Ano");

            comparaMaiorInt(carro1.getInt("Motores.hp"),
                    carro2.getInt("Motores.hp"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    400,"Cavalos");

            comparaMaiorDouble(carro1.getDouble("Motores.cilindrada"),
                    carro2.getDouble("Motores.cilindrada"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    4, "Cilindrada");

            comparaMenorDouble(carro1.getDouble("Carros.preco"),
                    carro2.getDouble("Carros.preco"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    50000, "Preço");

            comparaMenorDouble(carro1.getDouble("Carros.preco_revisao"),
                    carro2.getDouble("Carros.preco_revisao"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    2000, "Valoe médio de revisão");

            comparaMenorDouble(carro1.getDouble("Carros.preco_seguro"),
                    carro2.getDouble("Carros.preco_seguro"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    10000, "Vamor médio de seguro");

            comparaMaiorDouble(carro1.getDouble("Motores.etan"),
                    carro2.getDouble("Motores.etan"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    15, "Consumo Etan. (Km/L)");

            comparaMaiorDouble(carro1.getDouble("Motores.gas"),
                    carro2.getDouble("Motores.gas"),
                    carro1.getString("Carros.modelo"),
                    carro2.getString("Carros.modelo"),
                    20, "Consumo Gas. (Km/L)");

            pontoText1.setText("" + ponto1);
            pontoText2.setText("" + ponto2);

            if (ponto1 > ponto2)
                vencedor.setText("O carro vencedor foi o " + carro1.getString("Carros.modelo"));
            else if (ponto1 < ponto2)
                vencedor.setText("O carro vencedor foi o " + carro2.getString("Carros.modelo"));
            else if (ponto1 == ponto2)
                vencedor.setText("Hove um empate entre o " + carro1.getString("Carros.modelo") + " e o " + carro2.getString("Carros.modelo"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //Comparar int onde o maior vence (ano,hp)
    public void comparaMaiorInt(int a, int b, String carro1, String carro2, int progressBarMax, String categoria){

        int max = (a>b?a:b);
        if(a == max)
            ponto1++;
        else
            ponto2++;

        SampleItemResultado sampleItem = new SampleItemResultado(carro1,carro2,String.valueOf(a),String.valueOf(b),progressBarMax, categoria);

        adapter.insert(sampleItem, 0);
    }

    //Comparar double onde o maior vence (cilindrada)
    public void comparaMaiorDouble(Double a, Double b, String carro1, String carro2, int progressBarMax, String categoria){

        double max = (a>b?a:b);
        if(a == max)
            ponto1++;
        else
            ponto2++;

        SampleItemResultado sampleItem = new SampleItemResultado(carro1,carro2,String.valueOf(Math.round(a)),String.valueOf(Math.round(b)),progressBarMax, categoria);
        adapter.insert(sampleItem, 0);
    }

    //Comparar double onde o menor vence (preco, preco da revisao, preco do seguro)
    public void comparaMenorDouble(Double a, Double b, String carro1, String carro2, int progressBarMax, String categoria){

        double max = (a<b?a:b);
        if(a == max)
            ponto1++;
        else
            ponto2++;


        SampleItemResultado sampleItem = new SampleItemResultado(carro1,carro2,String.valueOf(Math.round(a)),String.valueOf(Math.round(b)),progressBarMax, categoria);
        adapter.insert(sampleItem, 0);
    }


    public JSONArray getCarroById(int id){

        JSONArray resultado;
        JSONObject item;

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.Creator();

        Cursor Data = databaseManager.Select("SELECT Carros.id," +
                "Carros.modelo," +
                "Carros.marca," +
                "Carros.ano," +
                "Carros.img," +
                "Carros.preco," +
                "Carros.preco_revisao," +
                "Carros.preco_seguro," +
                "Motores.hp," +
                "Motores.cilindrada," +
                "Motores.tipo," +
                "Motores.etan," +
                "Motores.gas FROM Carros INNER JOIN Motores\n" +
                " ON Motores.id = Carros.id_motor WHERE Carros.id = " + id);

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
                    item.put("Carros.preco", Data.getDouble(5));
                    item.put("Carros.preco_revisao", Data.getDouble(6));
                    item.put("Carros.preco_seguro", Data.getDouble(7));
                    item.put("Motores.hp", Data.getInt(8));
                    item.put("Motores.cilindrada", Data.getDouble(9));
                    item.put("Motores.tipo", Data.getString(10));
                    item.put("Motores.etan", Data.getDouble(11));
                    item.put("Motores.gas", Data.getDouble(12));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                resultado.put(item);

                if (!Data.moveToNext())
                    break;
            }
        }
        return resultado;
    }
}

