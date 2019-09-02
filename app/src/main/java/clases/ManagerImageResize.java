package clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import global.database_var;
import global.global_var;


/**
 * Created by julian on 10/08/15.
 */
public class ManagerImageResize implements global_var, database_var {
    private static ManagerImageResize ourInstance = null;


    /**
     * Constructor singleton de la clase
     * **/
    public static ManagerImageResize getInstance() {
        if(ourInstance == null){
            ourInstance = new ManagerImageResize();
        }
        return ourInstance;
    }




    private ManagerImageResize(){
    }





    public void resizeImage(String _nameFile,int ciclo, int _width, int _height){
        String ruta = sub_path_pictures +File.separator+ciclo+ File.separator + _nameFile;
        byte[] _image = this.FileToArrayBytesOne(ruta);
        Bitmap b = BitmapFactory.decodeByteArray(_image, 0, _image.length);
        Bitmap newImage = Bitmap.createScaledBitmap(b, _width, _height, false);

        File pictureFile = new File(ruta);
        if (pictureFile == null) {
            //Log.d(TAG,"Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            newImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            //Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            //Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }



    private byte[] FileToArrayBytesOne(String NombreArchivo){
        int len = 0;
        InputStream is 	= null;
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024 * _SIZZE_BUFFER);
        byte[] buffer = new byte[1024*_SIZZE_BUFFER];

        try{
            if (NombreArchivo != null) {
                is = new FileInputStream(NombreArchivo);
                try {
                    while ((len = is.read(buffer)) >= 0) {
                        os.write(buffer, 0, len);
                    }
                } finally {
                    is.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                throw new IOException("Unable to open R.raw.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return os.toByteArray();
    }


}