package comparador.com.comparadordecarros.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Carlos on 13/08/2015.
 */
public class ImageLoader {
    public Bitmap loadImages(String path){
        //Find the directory for the SD Card using the API
        //*Don't* hardcode "/sdcard"
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        File file = new File(sdcard + path);

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            return myBitmap;

        } else {
            Log.d("Erro", "Erro!");

            return null;
        }
    }
}
