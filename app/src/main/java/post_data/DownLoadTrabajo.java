package post_data;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Observable;
import java.util.Observer;

import clases.ClassConfiguracion;
import clases.ClassFlujoInformacion;

/**
 * Created by Julian Poveda on 07/10/2015.
 */
public class DownLoadTrabajo extends AsyncTask<String, String, String> implements Observer {
    /**Instancias a clases**/
    private ClassConfiguracion      FcnCfg;
    private ClassFlujoInformacion   FcnInformacion;

    /**Variables Locales**/
    private Context ConnectServerContext;


    private URL url;
    private HttpURLConnection conn;

    ProgressDialog _pDialog;


    //Contructor de la clase
    public DownLoadTrabajo(Context context){
        this.ConnectServerContext 		= context;
        this.FcnCfg						= ClassConfiguracion.getInstance(this.ConnectServerContext);
        this.FcnInformacion             = new ClassFlujoInformacion(this.ConnectServerContext);
        this.FcnInformacion.addObserver(this);
        this.conn = null;
    }


    //Operaciones antes de realizar la conexion con el servidor
    protected void onPreExecute(){
        Toast.makeText(this.ConnectServerContext, "Iniciando conexion con el servidor, por favor espere...", Toast.LENGTH_SHORT).show();
        _pDialog = new ProgressDialog(this.ConnectServerContext);
        _pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _pDialog.setTitle("CONECTANDO CON EL SERVIDOR");
        _pDialog.setMessage("Conectando con el servidor...");
        _pDialog.setCancelable(false);
        _pDialog.setProgress(0);
        _pDialog.setMax(100);
        _pDialog.show();
    }



    //Conexion con el servidor en busca de actualizaciones de trabajo programado
    @Override
    protected String doInBackground(String... params) {
        try {
            String urlParameters = "Peticion=" + URLEncoder.encode("Trabajo", "UTF-8")
                    + "&Inspector=" + URLEncoder.encode(params[0], "UTF-8")
                    + "&RutasLecturas=" + URLEncoder.encode(this.FcnInformacion.getRutasCargadasByTipo("L"), "UTF-8")
                    + "&RutasRecuperaciones=" + URLEncoder.encode(this.FcnInformacion.getRutasCargadasByTipo("R"), "UTF-8")
                    + "&RutasVerificaciones=" + URLEncoder.encode(this.FcnInformacion.getRutasCargadasByTipo("V"), "UTF-8");

            //this.url    = new URL(this.FcnCfg.getIp_server()+":"+this.FcnCfg.getPort()+"/InSitu/JSONService/DownLoad.php");
            this.url    = new URL(this.FcnCfg.getIp_server()+":"+this.FcnCfg.getPort()+"/"+FcnCfg.getModule_web_service()+"/"+FcnCfg.getWeb_service());
            this.conn   = (HttpURLConnection) url.openConnection();
            this.conn.setReadTimeout(55000);
            this.conn.setConnectTimeout(60000);
            this.conn.setRequestMethod("POST");
            this.conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            this.conn.setRequestProperty("Content-Language", "en-US");

            this.conn.setUseCaches (false);
            this.conn.setDoInput(true);
            this.conn.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (this.conn.getOutputStream ());
            wr.writeBytes (urlParameters);
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
                JSONObject jsonObj = new JSONObject(response.toString());
                this.FcnInformacion.loadTrabajo(jsonObj.getJSONArray("TrabajoProgramado"));
                this.FcnInformacion.loadTrabajo(jsonObj.getJSONArray("TrabajoRecuperaciones"));
                this.FcnInformacion.loadTrabajo(jsonObj.getJSONArray("TrabajoVerificaciones"));
                this.FcnInformacion.eraseTrabajo(jsonObj.getJSONArray("LecturasTerminadas"), "L");
                this.FcnInformacion.eraseTrabajo(jsonObj.getJSONArray("VerificacionesTerminadas"),"V");
                this.FcnInformacion.eraseTrabajo(jsonObj.getJSONArray("RecuperacionesTerminadas"),"R");
                //Log.e("JSONException", "Error: " + jsonObj.toString());
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
        //Toast.makeText(this.ConnectServerContext,"Carga de parametros finalizada. "+rta, Toast.LENGTH_LONG).show();

        /*try {
            JSONObject jsonObj = new JSONObject(rta);
            this.onProgressUpdate("Cargando Inspectores");
            this.FcnInformacion.updateInspectores(jsonObj.getJSONArray("Inspectores"));

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)*/

        _pDialog.dismiss();
    }


    @Override
    protected void onProgressUpdate(String... values) {
        this._pDialog.setTitle(values[0]);
        this._pDialog.setMessage(values[1]);
        this._pDialog.setProgress(Integer.parseInt(values[2]));
        this._pDialog.setMax(Integer.parseInt(values[3]));
    }


    @Override
    public void update(Observable observable, Object data) {
        this.publishProgress(this.FcnInformacion.getTitulo(), this.FcnInformacion.getMensaje(), this.FcnInformacion.getProgreso(), this.FcnInformacion.getTotal());
    }
}

