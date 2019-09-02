package post_data;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import clases.ClassConfiguracion;
import grupodesarrollo.insitu.FormInicioSession;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import clases.ClassFlujoInformacion;
import clases.ClassSession;
import dialogos.ShowDialogBox;
import global.global_var;
import sistema.Archivos;
import sistema.Bluetooth;
import sistema.SQLite;

/**
 * Created by SypelcDesarrollo on 12/02/2015.
 */

public class UpLoadFoto extends AsyncTask<String,String,Integer> implements global_var {

    URL url;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    int serverResponseCode = 0;
    FileInputStream fileInputStream;

    String upLoadServerUri = null;

    private Context context;

    private ClassSession Usuario;
    private ClassConfiguracion FcnCfg;
    private SQLite FcnSQL;
    private Archivos FcnArch;
    private ClassFlujoInformacion FcnInfo;
    private Bluetooth FcnBluetooth;

    ProgressDialog _pDialog;

    String Peticion;
    String codigo;
    String bluetooth;

    /*Variables para el tratamiento de cada foto.*/
    String fileName,sourceFileUri,cuenta,fecha_toma,ciclo;

    public UpLoadFoto(Context ctx){
        this.context = ctx;
        this.Usuario    = ClassSession.getInstance(this.context);
        this.FcnSQL     = new SQLite(this.context);
        this.FcnArch	= new Archivos(this.context, FormInicioSession.path_files_app, 10);
        this.FcnInfo    = new ClassFlujoInformacion(this.context);
        this.FcnBluetooth   = Bluetooth.getInstance();
        this.FcnCfg		= ClassConfiguracion.getInstance(this.context);
    }

    protected void onPreExecute() {

        upLoadServerUri    = "http://186.115.150.189/FotosLecturas/WSFotos/UploadToServer.php";

        Toast.makeText(this.context, "Iniciando conexion con el servidor, por favor espere...", Toast.LENGTH_SHORT).show();
        _pDialog = new ProgressDialog(this.context);
        _pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _pDialog.setMessage("Ejecutando operaciones...");
        _pDialog.setCancelable(false);
        _pDialog.setProgress(0);
        _pDialog.setMax(100);
        _pDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {

        this.Peticion      = "UploadFotos";
        this.codigo        = this.Usuario.getCodigo();
        this.bluetooth     = this.FcnBluetooth.GetOurDeviceByAddress();

        File f = new File(FormInicioSession.sub_path_pictures);
        File[] fotosTotal = f.listFiles();
        for (int j=0;j<fotosTotal.length;j++){
            if(fotosTotal[j].isDirectory()){
                File[] fotos = this.FcnArch.ListaFotos(fotosTotal[j]+"", false);
                String[] idCiclo = String.valueOf(fotosTotal[j]).split("/");
                if(fotos.length == 0){
                    serverResponseCode = 2;
                }
                else {
                    for (int i = 0; i < fotos.length; i++) {
                        String[] _foto      = fotos[i].getName().split("_");
                        this.fileName       = fotos[i].toString();
                        this.sourceFileUri  = fotos[i].toString();
                        this.cuenta         = _foto[0];

                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date date = new Date(fotos[i].lastModified());
                        this.fecha_toma =  dateFormat.format(date);

                        this.ciclo          = idCiclo[6];

                        String lineEnd = "\r\n";
                        String twoHyphens = "--";
                        String boundary = "*****";
                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1 * 1024 * 1024;
                        File sourceFile = new File(sourceFileUri);

                        try {
                            fileInputStream = new FileInputStream(sourceFile);
                            url = new URL(upLoadServerUri);


                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            conn.setUseCaches(false);
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
                            dos.writeBytes("Content-Disposition: form-data; name=\"cuenta\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(cuenta);
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

                            if (serverResponseCode == 200) {
                                Log.i("uploadFile", "File Upload Complete");
                            } else {
                                serverResponseCode = -1;
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

                            if (Boolean.parseBoolean(respuesta.get("request").toString())) {
                                this.publishProgress(fotos.length + "", cuenta, i + "");
                                this.FcnArch.DeleteFile(fotos[i].toString());
                                this.FcnInfo.deleteRegistroFoto(fotos[i].getName());
                                serverResponseCode = 1;
                            } else {
                                serverResponseCode = -3;
                            }

                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                            serverResponseCode = -2;
                        } catch (Exception e) {
                            e.printStackTrace();
                            serverResponseCode = -6;
                        } finally {
                            conn.disconnect();
                        }

                    }
                }
            }
        }
        return serverResponseCode;
    }

    protected void onPostExecute(Integer result) {
        switch (result){
            case -1:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_ERROR,"UPLOAD FOTOS","No se ha recibido respuesta del servidor.");
                break;

            case -2:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_ERROR,"UPLOAD FOTOS","Revisar configuracion conexion al Servidor.");
                break;

            case -3:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_ERROR,"UPLOAD FOTOS","No se recibio la Foto ");
                break;

            case -6:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_ERROR,"UPLOAD FOTOS","Hubo un error en el envio de la informacion al servidor.");
                break;

            case 1:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_INFORMATIVE,"UPLOAD FOTOS","Fotos Subidas Correctamente.");
                break;

            case 2:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_INFORMATIVE,"UPLOAD FOTOS","No hay Fotos pendientes por Descargar.");
                break;

            default:
                new ShowDialogBox().showDialogBox(this.context,DIALOG_ERROR,"UPLOAD FOTOS","Error desconocido.");
                break;
        }
        _pDialog.dismiss();
    }


    @Override
    protected void onProgressUpdate(String... values) {
        _pDialog.setMessage(values[1]);
        _pDialog.setMax(Integer.valueOf(values[0]));
        _pDialog.setProgress(Integer.valueOf(values[2]));
    }


    /**
     @Override
     protected Integer doInBackground(String... params) {

     this.Peticion      = "UploadFotos";
     this.codigo        = this.Usuario.getCodigo();
     this.bluetooth     = this.FcnBluetooth.GetOurDeviceByAddress();

     File[] fotos = this.FcnArch.ListaFotos(FormInicioSession.sub_path_pictures, false);
     if(fotos.length == 0){
     serverResponseCode = 2;
     }
     else {
     for (int i = 0; i < fotos.length; i++) {
     String[] _foto      = fotos[i].getName().split("_");
     this.fileName       = fotos[i].toString();
     this.sourceFileUri  = fotos[i].toString();
     this.cuenta         = _foto[0];

     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     Date date = new Date(fotos[i].lastModified());
     this.fecha_toma =  dateFormat.format(date);

     this.fecha_toma     = this.FcnSQL.StrSelectShieldWhere("registro_fotos", "fecha_toma", "nombre_foto='" + fotos[i].getName() + "'");
     this.ciclo          = this.FcnSQL.StrSelectShieldWhere("registro_fotos", "ciclo", "nombre_foto='" + fotos[i].getName() + "'");
     //this.ciclo            = this.FcnSQL.StrSelectShieldWhere("maestro_clientes", "id_ciclo", "cuenta=" + this.cuenta);


     String lineEnd = "\r\n";
     String twoHyphens = "--";
     String boundary = "*****";
     int bytesRead, bytesAvailable, bufferSize;
     byte[] buffer;
     int maxBufferSize = 1 * 1024 * 1024;
     File sourceFile = new File(sourceFileUri);

     try {
     fileInputStream = new FileInputStream(sourceFile);
     url = new URL(upLoadServerUri);


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
     dos.writeBytes("Content-Disposition: form-data; name=\"cuenta\"" + lineEnd);
     dos.writeBytes(lineEnd);
     dos.writeBytes(cuenta);
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

     if (serverResponseCode == 200) {
     Log.i("uploadFile", "File Upload Complete");
     } else {
     serverResponseCode = -1;
     }

     fileInputStream.close();
     dos.flush();
     dos.close();

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        response.append('\r');
    }
    in.close();
    JSONObject respuesta = new JSONObject(response.toString());

    if (Boolean.parseBoolean(respuesta.get("request").toString())) {
        this.publishProgress(fotos.length + "", cuenta, i + "");
        this.FcnArch.DeleteFile(fotos[i].toString());
        this.FcnInfo.deleteRegistroFoto(fotos[i].getName());
        serverResponseCode = 1;
    } else {
        serverResponseCode = -3;
    }

} catch (MalformedURLException ex) {
        ex.printStackTrace();
        serverResponseCode = -2;

        } catch (Exception e) {
        e.printStackTrace();
        serverResponseCode = -6;
        } finally {
        conn.disconnect();
        }

        }
        }
        return serverResponseCode;
        }
     **/
}

