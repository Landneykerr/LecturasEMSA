package clases;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import global.global_var;
import grupodesarrollo.insitu.FormInicioSession;
import sistema.SQLite;

/**
 * Created by GrupoDesarrollo on 16/03/2017.
 */

public class GuardarFoto extends AsyncTask<String, Integer, Integer> implements global_var {

    private Context context;
    private SQLite fcnSql;

    public GuardarFoto(Context _context){
        this.context = _context;
        this.fcnSql  = new SQLite(context);
    }



    protected void onPreExecute(Context context){
    }



    //Conexion con el servidor en busca de actualizaciones de trabajo programado
    @Override
    protected Integer doInBackground(String... params) {
        ContentValues datosCuenta = new ContentValues();
        datosCuenta = this.fcnSql.SelectDataRegistro("maestro_clientes","id_ciclo,cuenta","id_serial_1="+params[0]);

        ArrayList<ContentValues> fotos =new ArrayList<ContentValues>();
        fotos = this.fcnSql.SelectData("registro_fotos","nombre_foto","cuenta="+datosCuenta.getAsString("cuenta"));

        if(fotos.size()>0) {
            for (ContentValues registro : fotos) {
                ContentValues datos = new ContentValues();
                String ruta = FormInicioSession.sub_path_pictures + File.separator+ datosCuenta.getAsString("id_ciclo")+ File.separator +registro.getAsString("nombre_foto");
                Bitmap bm = BitmapFactory.decodeFile(ruta);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                byte[] b = baos.toByteArray();

                datos.put("foto", Base64.encodeToString(b, Base64.DEFAULT));
                this.fcnSql.UpdateRegistro("registro_fotos", datos, "nombre_foto='" + registro.getAsString("nombre_foto") + "'");
            }
        }else{
            ContentValues datos = new ContentValues();
            Bitmap bm = BitmapFactory.decodeFile(FormInicioSession.path_files_app + File.separator + FormInicioSession.sub_path_pictures + File.separator + datosCuenta.getAsString("id_ciclo") + File.separator + datosCuenta.getAsString("cuenta")+"_0.jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] b = baos.toByteArray();

            File foto = new File(FormInicioSession.path_files_app + File.separator + FormInicioSession.sub_path_pictures + File.separator + datosCuenta.getAsString("id_ciclo") + File.separator + datosCuenta.getAsString("cuenta")+"_0.jpg");
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(foto.lastModified());

            datos.put("cuenta",datosCuenta.getAsString("cuenta"));
            datos.put("nombre_foto",datosCuenta.getAsString("cuenta")+"_0.jpg");
            datos.put("ciclo",datosCuenta.getAsString("ciclo"));
            datos.put("fecha_toma",dateFormat.format(date));
            datos.put("foto", Base64.encodeToString(b, Base64.DEFAULT));
            this.fcnSql.InsertRegistro("registro_fotos",datos);
        }

        return 0;
    }



    //Operaciones despues de finalizar la conexion con el servidor
    @Override
    protected void onPostExecute(Integer rta){

    }
}

