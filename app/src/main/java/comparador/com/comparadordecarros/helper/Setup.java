package comparador.com.comparadordecarros.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import comparador.com.comparadordecarros.database.DatabaseManager;
import comparador.com.comparadordecarros.objects.Carros;
import comparador.com.comparadordecarros.objects.Motores;

/**
 * Created by Carlos on 13/08/2015.
 */
public class Setup {

    Context context;

    public Setup(Context context){
        this.context = context;
    }

    public void init (){
        Carros carros = new Carros();
        Motores motores = new Motores();

        carros.setCarros();
        motores.setMotores();

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.Creator();

        databaseManager.Insert(motores.getMotores(), "Motores", "'hp','cilindrada','tipo','etan','gas'");
        databaseManager.Insert(carros.getCarros(), "Carros", "'modelo','id_motor','marca','ano','preco','preco_revisao','preco_seguro','img'");

        copyAssets();
    }

    private void copyAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;

        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        Log.d("vazio", "" + Arrays.toString(files));

        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = assetManager.open(filename);
                File outFile = new File(Environment.getExternalStorageDirectory(), filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
