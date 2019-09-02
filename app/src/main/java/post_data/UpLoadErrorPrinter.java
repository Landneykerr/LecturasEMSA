package post_data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import grupodesarrollo.insitu.FormInicioSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import clases.ClassConfiguracion;
import sistema.SQLite;

/**
 * Created by Julian Poveda on 13/10/2015.
 */

public class UpLoadErrorPrinter extends AsyncTask<String, String, String> {
    /**Instancias a clases**/
    private ClassConfiguracion  FcnCfg;
    private SQLite              FcnSQL;

    /**Variables Locales**/
    private Context ConnectServerContext;

    private ContentValues _tempRegistro = new ContentValues();
    private ArrayList<ContentValues> _tempTabla  = new ArrayList<ContentValues>();

    private URL url;
    private HttpURLConnection conn;



    //Contructor de la clase
    public UpLoadErrorPrinter(Context context){
        this.ConnectServerContext 		= context;
        this.FcnCfg						= ClassConfiguracion.getInstance(this.ConnectServerContext);
        this.FcnSQL                     = new SQLite(this.ConnectServerContext);
        this.conn = null;
    }


    //Operaciones antes de realizar la conexion con el servidor
    protected void onPreExecute(){
    }



    //Conexion con el servidor en busca de actualizaciones de trabajo programado
    @Override
    protected String doInBackground(String... params) {
        JSONObject  datos = new JSONObject();
        JSONArray   datosarray = new JSONArray();
        try {
            this._tempTabla = this.FcnSQL.SelectData("registro_impresion","id_serial,cuenta,id_inspector,error,fecha_toma","id_serial is not null");
            for(int i=0;i<this._tempTabla.size();i++){
                this._tempRegistro = this._tempTabla.get(i);
                JSONObject  datostemp = new JSONObject();
                datostemp.put("id",this._tempRegistro.getAsString("id_serial"));
                datostemp.put("cuenta",this._tempRegistro.getAsString("cuenta"));
                datostemp.put("id_inspector",this._tempRegistro.getAsString("id_inspector"));
                datostemp.put("error",this._tempRegistro.getAsString("error"));
                datostemp.put("fecha_toma",this._tempRegistro.getAsString("fecha_toma"));
                datosarray.put(datostemp);
            }
            datos.put("Impresion",datosarray);

            this.url    = new URL(this.FcnCfg.getIp_server()+":"+this.FcnCfg.getPort()+"/DesarrolloLecturas/JSONService/DownLoad.php");
            //this.url    = new URL("http://192.168.0.12:80/DesarrolloLecturas/JSONService/DownLoad.php");
            this.conn   = (HttpURLConnection) url.openConnection();
            this.conn.setReadTimeout(10000);
            this.conn.setConnectTimeout(15000);
            this.conn.setRequestMethod("POST");
            this.conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //this.conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            this.conn.setRequestProperty("Content-Language", "en-US");

            this.conn.setUseCaches (false);
            this.conn.setDoInput(true);
            this.conn.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (this.conn.getOutputStream ());
            String data = "datosimpresion="+datos.toString()+"&Peticion=UploadErrorPrinter";
            wr.writeBytes(data);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = this.conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            try {
                JSONArray jsonObj = new JSONArray(response.toString());
                for(int i=0;i<jsonObj.length();i++){
                    this.FcnSQL.DeleteRegistro("registro_impresion", "id_serial=" + jsonObj.getJSONObject(i).getInt("id"));
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(this.conn != null) {
                this.conn.disconnect();
            }
        }
    }



    //Operaciones despues de finalizar la conexion con el servidor
    @Override
    protected void onPostExecute(String rta) {
    }


    @Override
    protected void onProgressUpdate(String... values) {
    }
}