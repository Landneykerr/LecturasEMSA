/**
 * Created by Julian Poveda on 18/11/2015.
 */

package post_data;

    import android.content.Context;
    import android.os.AsyncTask;


    import java.io.BufferedReader;
    import java.io.DataOutputStream;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;

    import clases.ClassConfiguracion;
    import dialogos.ShowDialogBox;
    import global.global_var;
    import sistema.Bluetooth;
    import sistema.DateTime;
    import sistema.SQLite;


public class SyncDateTime extends AsyncTask<String, String, StringBuffer> implements global_var {
    /**Instancias a clases**/
    private ClassConfiguracion  FcnCfg;
    private SQLite              FcnSQL;
    private DateTime            FcnTime;
    private Bluetooth           FcnBluetooth;

    /**Variables Locales**/
    private Context ConnectServerContext;


    private URL url;
    private HttpURLConnection conn;



    //Contructor de la clase
    public SyncDateTime(Context context){
        this.ConnectServerContext 	= context;
        this.FcnCfg					= ClassConfiguracion.getInstance(this.ConnectServerContext);
        this.FcnSQL                 = new SQLite(this.ConnectServerContext);
        this.conn                   = null;
        this.FcnTime                = DateTime.getInstance();
        this.FcnBluetooth           = Bluetooth.getInstance();
    }


    //Operaciones antes de realizar la conexion con el servidor
    protected void onPreExecute(){
    }


    //Conexion con el servidor en busca de actualizaciones de trabajo programado
    @Override
    protected StringBuffer doInBackground(String... params) {
        try {
            this.url    = new URL(this.FcnCfg.getIp_server()+":"+this.FcnCfg.getPort()+"/"+FcnCfg.getModule_web_service()+"/"+FcnCfg.getWeb_service());
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
            String data = "bluetooth="+this.FcnBluetooth.GetOurDeviceByAddress()+"&fecha_hora="+this.FcnTime.getDateTimeLocal()+"&Peticion=SyncDate";
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
            return response;
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
    protected void onPostExecute(StringBuffer rta) {
        if(rta == null){
            this.FcnCfg.setHora_sincronizada(true);
        }else if(rta.toString().isEmpty()){
            this.FcnCfg.setHora_sincronizada(true);
        }else{
            String respuesta[]  = rta.toString().split("\\|");
            if(respuesta[0].equals(_ERROR_DATE)) {
                new ShowDialogBox().showDialogBox(this.ConnectServerContext, DIALOG_ERROR, "ERROR FECHA", "Error de sincronizacion, la fecha del movil no coincide con la fecha del servidor. Hora del servidor (" + respuesta[1] + ").");
                this.FcnCfg.setHora_sincronizada(false);
            }else if(respuesta[0].equals(_ERROR_TIME)){
                new ShowDialogBox().showDialogBox(this.ConnectServerContext, DIALOG_ERROR, "ERROR HORA", "Error de sincronizacion,  la hora del movil no coincide con la hora del servidor o esta desfasada mas de 5 minutos. Hora del servidor (" + respuesta[1] + ").");
                this.FcnCfg.setHora_sincronizada(false);
            }else{
                this.FcnCfg.setHora_sincronizada(true);
            }
        }
    }


    @Override
    protected void onProgressUpdate(String... values) {
    }
}