package post_data;

/**
 * Created by Julian Poveda on 10/11/2015.
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import clases.ClassConfiguracion;
import clases.ClassSession;

import global.global_var;
import sistema.Archivos;
import sistema.Bluetooth;
import gps.GeoConvert;
import sistema.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.zebra.sdk.device.FileUtil;

import grupodesarrollo.insitu.FormInicioSession;


public class UploadLecturasJson extends AsyncTask<String,Void,Integer> {
    /**
     * Variables para el envio JSOn
     */
    URL url;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    int serverResponseCode = 0;
    String upLoadServerUri = null;
    FileInputStream fileInputStream;


    private Intent new_form;
    private ClassConfiguracion FcnCfg;
    private Archivos FcnArch;
    private SQLite FcnSQL;
    private ClassSession Usuario;
    private Bluetooth FcnBluetooth = Bluetooth.getInstance();

    private ContentValues _tempRegistro = new ContentValues();
    private ContentValues _tempRegistro1 = new ContentValues();

    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();
    private ArrayList<ContentValues> _tempTabla1 = new ArrayList<ContentValues>();


    private Context Context;
    private String infRuta[];
    private String Bluetooh;

    public UploadLecturasJson(Context context) {
        this.Context = context;
        this.FcnCfg = ClassConfiguracion.getInstance(this.Context);
        this.FcnSQL = new SQLite(this.Context);
        this.FcnArch = new Archivos(this.Context, FormInicioSession.path_files_app, 10);
        this.Usuario = ClassSession.getInstance(this.Context);
    }

    protected void onPreExecute() {
        upLoadServerUri = this.FcnCfg.getIp_server()+":"+this.FcnCfg.getPort()+"/DesarrolloLecturas/JSONService/UploadJSON.php";
        this.Bluetooh = this.FcnBluetooth.GetOurDeviceByAddress();
    }


    @Override
    protected Integer doInBackground(String... params) {
        int _retorno    = 0;
        JSONObject json = new JSONObject();
        JSONArray lecturasArray = new JSONArray();
        ArrayList<ContentValues> logFotos = new ArrayList<>();

        this._tempTabla = this.FcnSQL.SelectData("maestro_clientes",
                "id_serial_1, id_serial_2, id_serial_3,id_programacion,tipo, cuenta",
                "estado='T'",
                "id_serial_1",
                200);


        if (this._tempTabla.size() > 0) {
            try {
                for (int i = 0; i < this._tempTabla.size(); i++) {

                    this._tempRegistro = this._tempTabla.get(i);

                    this._tempTabla1 = this.FcnSQL.SelectData("toma_lectura",
                            "id, id_serial1, lectura1, critica1, id_serial2, lectura2, critica2, id_serial3, lectura3, critica3, anomalia, mensaje, tipo_uso,fecha_toma,longitud,latitud,id_inspector",
                            "id_serial1=" + this._tempRegistro.getAsString("id_serial_1") + " AND id_serial2=" + this._tempRegistro.getAsString("id_serial_2") + " and id_serial3=" + this._tempRegistro.getAsString("id_serial_3") + "");

                    for (int j = 0; j < this._tempTabla1.size(); j++) {
                        double[] utm = null;

                        this._tempRegistro1 = this._tempTabla1.get(j);

                        utm = GeoConvert.toUtm(Double.parseDouble(_tempRegistro1.getAsString("longitud")),
                                Double.parseDouble(_tempRegistro1.getAsString("latitud")));



                        JSONObject lecturas = new JSONObject();
                        lecturas.put("tipo", this._tempRegistro.getAsString("tipo"));
                        lecturas.put("id_programacion", this._tempRegistro.getAsInteger("id_programacion"));
                        lecturas.put("id", this._tempRegistro1.getAsInteger("id"));
                        lecturas.put("id_serial1", this._tempRegistro1.getAsInteger("id_serial1"));
                        lecturas.put("lectura1", this._tempRegistro1.getAsInteger("lectura1"));
                        lecturas.put("critica1", this._tempRegistro1.getAsString("critica1"));
                        lecturas.put("id_serial2", this._tempRegistro1.getAsInteger("id_serial2"));
                        lecturas.put("lectura2", this._tempRegistro1.getAsInteger("lectura2"));
                        lecturas.put("critica2", this._tempRegistro1.getAsString("critica2"));
                        lecturas.put("id_serial3", this._tempRegistro1.getAsInteger("id_serial3"));
                        lecturas.put("lectura3", this._tempRegistro1.getAsInteger("lectura3"));
                        lecturas.put("critica3", this._tempRegistro1.getAsString("critica3"));
                        lecturas.put("anomalia", this._tempRegistro1.getAsInteger("anomalia"));
                        lecturas.put("mensaje",  this._tempRegistro1.getAsString("mensaje"));
                        lecturas.put("tipo_uso", this._tempRegistro1.getAsInteger("tipo_uso"));
                        lecturas.put("fecha_toma", this._tempRegistro1.getAsString("fecha_toma"));
                        lecturas.put("longitud", this._tempRegistro1.getAsString("longitud"));
                        lecturas.put("latitud", this._tempRegistro1.getAsString("latitud"));
                        lecturas.put("id_inspector", this._tempRegistro1.getAsInteger("id_inspector"));
                        lecturas.put("x", utm[0]);
                        lecturas.put("y", utm[1]);

                       if(_tempRegistro1.getAsInteger("anomalia") == 22 || _tempRegistro1.getAsInteger("anomalia") == 52 )
                        {
                            JSONArray fotosArray = new JSONArray();
                            logFotos.clear();
                            logFotos = FcnSQL.SelectData("registro_fotos",
                                    "cuenta, nombre_foto, fecha_toma,foto", "cuenta = '"+ _tempRegistro.getAsString("cuenta")+"'");

                            for(ContentValues registro: logFotos)
                            {
                                JSONObject foto = new JSONObject();

                                foto.put("cuenta", registro.getAsString("cuenta"));
                                foto.put("nombre_foto", registro.getAsString("nombre_foto"));
                                foto.put("fecha_toma", registro.getAsString("fecha_toma"));
                                foto.put("foto", registro.getAsString("foto").equals("")?" ":registro.getAsString("foto"));

                                fotosArray.put(foto);
                            }
                            lecturas.put("fotos", fotosArray);
                        }

                        lecturasArray.put(lecturas);
                    }
                }
                json.put("informacion", lecturasArray);
            } catch (Exception e) {
                e.toString();
                e.printStackTrace();
            }

            try {
                url = new URL(upLoadServerUri);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(350000);
                conn.setReadTimeout(35000);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");

                dos = new DataOutputStream(conn.getOutputStream());

                String data = "informacion="+json.toString()+"&Peticion=UploadTrabajo&origen="+this.Bluetooh;
                dos.writeBytes(data);
                dos.flush();
                dos.close();


                InputStream is = this.conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                String informacion[] = new String(response.toString()).trim().split("\\|");
                if(informacion.length>0){
                    this._tempRegistro1.clear();
                    this._tempRegistro1.put("estado","E");

                    for(int i=0;i<informacion.length;i++){
                        //Con el id local se consulta los id_seriales originales para poder actualizar el registro general
                        _tempRegistro = this.FcnSQL.SelectDataRegistro("toma_lectura",
                                "id_serial1,id_serial2,id_serial3","id="+informacion[i]);

                        //Se hace el cambio de estado de (T)erminado a (E)nviado
                        this.FcnSQL.UpdateRegistro("maestro_clientes",
                                this._tempRegistro1,
                                "id_serial_1="+_tempRegistro.getAsString("id_serial1")+" AND id_serial_2="+_tempRegistro.getAsString("id_serial2")+" AND id_serial_3="+_tempRegistro.getAsString("id_serial3"));
                    }
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Upload file", "Exception : " + e.getMessage(), e);
            } finally {
                conn.disconnect();
            }
            return serverResponseCode;
        }else{
                _retorno = 0;
        }
        return _retorno;
    }


    @Override
    protected void onPostExecute(Integer rta) {
    }
}

