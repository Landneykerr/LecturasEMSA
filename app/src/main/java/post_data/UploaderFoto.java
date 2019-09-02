package post_data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import grupodesarrollo.insitu.FormInicioSession;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import clases.ClassFlujoInformacion;
import clases.ClassSession;
import sistema.Archivos;
import sistema.Bluetooth;
import sistema.SQLite;

/**
 * Created by User on 30/10/2015.
 */


public class UploaderFoto extends AsyncTask<String,Void,String> {

    URL url;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    int serverResponseCode = 0;
    String upLoadServerUri = null;
    FileInputStream fileInputStream;

    private Context context;

    private ClassSession Usuario;
    private SQLite FcnSQL;
    private Archivos FcnArch;
    private ClassFlujoInformacion FcnInfo;
    private Bluetooth FcnBluetooth;

    String codigo;
    String Peticion;
    String bluetooth;

    public UploaderFoto(Context ctx){
        this.context = ctx;
        this.Usuario    = ClassSession.getInstance(this.context);
        this.FcnSQL     = new SQLite(this.context);
        this.FcnArch	= new Archivos(this.context, FormInicioSession.path_files_app, 10);
        this.FcnInfo    = new ClassFlujoInformacion(this.context);
        this.FcnBluetooth   = Bluetooth.getInstance();
    }

    protected void onPreExecute() {
        this.codigo        = this.Usuario.getCodigo();
        this.Peticion      = "UploadFotos";
        this.bluetooth     = this.FcnBluetooth.GetOurDeviceByAddress();
        upLoadServerUri = "http://104.200.28.188/EMCALI_SCRR/WSFotos/UploadToServer.php";
    }

    @Override
    protected String doInBackground(String... params) {

        String fileName      = params[0];
        String sourceFileUri = params[0];
        String cuenta        = params[1];
        String fecha_toma    = this.FcnSQL.StrSelectShieldWhere("registro_fotos", "fecha_toma", "nombre_foto='" + params[2] + "'");
        String ciclo         = this.FcnSQL.StrSelectShieldWhere("registro_fotos", "ciclo", "nombre_foto='" + params[2] + "'");


        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        try {
            // open a URL connection to the Servlet
            fileInputStream = new FileInputStream(sourceFile);
            url = new URL(upLoadServerUri);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs -- Permita el envio de datos.s
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"Peticion\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Peticion);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"cuenta\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(cuenta);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"ciclo\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(ciclo);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"bluetooth\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(bluetooth);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"codigo\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(codigo);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"fecha_toma\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(fecha_toma);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + fileName + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

            if(serverResponseCode == 200){
                Log.i("uploadFile", "File Upload Complete");
            }

            fileInputStream.close();
            dos.flush();
            dos.close();

            /*Tratamiento de la respuesta Para eliminar la foto del Movil*/
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append('\r');
            }
            in.close();
            JSONObject respuesta = new JSONObject(response.toString());

            if(Boolean.parseBoolean(respuesta.get("request").toString())){
                this.FcnArch.DeleteFile(params[0]);
                this.FcnInfo.deleteRegistroFoto(params[2]);
            }else{
                //System.out.println("Error al registrar la foto.");
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Upload file", "Exception : " + e.getMessage(), e);
        }finally {
            conn.disconnect();
        }
        return serverResponseCode+"";
    }

    protected void onPostExecute(Void result) {
       /* super.onPostExecute(result);
        pDialog.dismiss();*/
    }
}


