package grupodesarrollo.insitu;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import Adapter.RutasAdapter;
import Adapter.RutasData;
import connectivity.CustomPhoneStateListener;
import connectivity.Network;
import global.global_var;
import gps.CustomListenerGPS;
import gps.PointGPS;
import post_data.SyncDateTime;
import post_data.UploadLecturasJson;
import clases.ClassConfiguracion;
import clases.ClassSession;
import dialogos.ShowDialog;
import sistema.Archivos;
import sistema.Bluetooth;
import sistema.DateTime;
import sistema.SQLite;
import dialogos.ShowDialogBox;
import sistema.Utilidades;

import static global.global_var._GET_DATE;

/**
 * Created by SypelcDesarrollo on 04/02/2015.
 *
 * Modificado el 06-11-2015
 * En el listado de rutas se agregan dos campos los cuales son foto e impresion, los cuales se configuran
 * desde el cargue de la ruta y se almacenan en la bd como 1 o 0, para ser consultados y mostrados en
 * pantalla. De esta forma el inspector sabe de antemano si la ruta esta configurada para tomar fotos
 * e imprimir o no.
 */
public class FormInformacionRutas extends Activity implements Observer{

    private String  ruta_seleccionada;
    private int     pend_ruta_seleccionada;
    private String  impresion;

    private Intent              new_form;

    private CustomPhoneStateListener fcnPhone;
    private Network myNetwork;

    private LocationManager     managerLocation;
    private CustomListenerGPS   listenerGPS;
    private PointGPS            pointGPS;
    private DateTime            dateTime;
    private Utilidades          utilidades;


    private ListView            listadoRutas;
    private SQLite              sqlConsulta;
    private ClassSession        FcnSession;
    private ClassConfiguracion  FcnCfg;
    private Bluetooth           FcnBluetooth;
    private Archivos            FcnArchivos;

    private RutasAdapter listadoRutasAdapter;
    private ArrayList<RutasData> arrayListadoRutas = new ArrayList<>();

    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();
    private ContentValues _tempRegistro 		= new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_rutas);

        Bundle bundle = getIntent().getExtras();
        listenerGPS    = CustomListenerGPS.getInstance(this);


        this.FcnSession     = ClassSession.getInstance(this);
        this.FcnCfg         = ClassConfiguracion.getInstance(this);
        this.FcnBluetooth   = Bluetooth.getInstance();
        this.FcnArchivos    = new Archivos(this,FormInicioSession.path_files_app,10);
        this.dateTime       = DateTime.getInstance();
        this.utilidades     = Utilidades.getInstance();

        sqlConsulta = new SQLite(this);
        listadoRutasAdapter = new RutasAdapter(FormInformacionRutas.this, arrayListadoRutas);

        listadoRutas = (ListView)findViewById(R.id.InfoListRutas);
        listadoRutas.setAdapter(listadoRutasAdapter);

        arrayListadoRutas.clear();

        this._tempTabla = sqlConsulta.SelectData("maestro_rutas","id_programacion,id_ciclo,id_municipio,ruta,tipo,foto,voucher","id_inspector="+this.FcnSession.getCodigo());
        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            Integer totalR;
            Integer totalP;
            Integer totalL;
            String foto = "No";
            String impresion = "No";

            if(this._tempRegistro.getAsInteger("foto") == 1){
                foto = "Si";
            }

            if(this._tempRegistro.getAsInteger("voucher") == 1){
                impresion = "Si";
            }

            totalR = sqlConsulta.CountRegistrosWhere("maestro_clientes","id_ciclo="+this._tempRegistro.getAsInteger("id_ciclo")+" AND id_programacion="+this._tempRegistro.getAsInteger("id_programacion")+" AND ruta='"+this._tempRegistro.getAsString("ruta")+"'");
            totalP = sqlConsulta.CountRegistrosWhere("maestro_clientes","id_ciclo="+this._tempRegistro.getAsInteger("id_ciclo")+" AND id_programacion="+this._tempRegistro.getAsInteger("id_programacion")+" AND ruta='"+this._tempRegistro.getAsString("ruta")+"' AND estado='P'");
            totalL = totalR - totalP;

            arrayListadoRutas.add(new RutasData(this._tempRegistro.getAsString("id_ciclo")+"-"+this._tempRegistro.getAsString("id_municipio")+"-"+this._tempRegistro.getAsString("ruta")+"-"+this._tempRegistro.getAsString("tipo"),
                                                String.valueOf(totalP),
                                                String.valueOf(totalL),
                                                String.valueOf(totalR),
                                                foto,
                                                impresion));
        }
        listadoRutasAdapter.notifyDataSetChanged();
        registerForContextMenu(this.listadoRutas);

    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        new SyncDateTime(this).execute();

        /**
         * Instancias a objetos encargados de controlar el listener de cambios en la conexion de datos
         * **/
        //builder = new AlertDialog.Builder(this);

        fcnPhone = new CustomPhoneStateListener(this);
        myNetwork = Network.getInstance();
        myNetwork.addObserver(this);

        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(this.fcnPhone, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        /**
         * Listener para detectar cambios en el gps, tanto en el funcionamiento como en la localizacion
         */
        try {
            pointGPS = PointGPS.getInstance();
            this.managerLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            this.managerLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    global_var._GPS_INTERVAL_SECONDS, global_var._GPS_INTERVAL_METERS, listenerGPS);

            pointGPS.addObserver(this);
        }catch(SecurityException se){

        }catch(Exception ex){
            Log.i("Error GPS", ex.toString());
        }

    }


    /**Funciones para el control del menu contextual del listview que muestra las ordenes de trabajo**/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        this.ruta_seleccionada      = arrayListadoRutas.get(info.position).getCodigoRuta();
        this.pend_ruta_seleccionada = Integer.parseInt(arrayListadoRutas.get(info.position).getTotalPendientes());
        this.impresion              = arrayListadoRutas.get(info.position).get_impresion();

        switch(v.getId()){
            case R.id.InfoListRutas:

                if(myNetwork.isConectado() && pointGPS.isEstadoGPS() || validarCodigo() && pointGPS.isEstadoGPS() )
                {
                    menu.setHeaderTitle("Ruta " + this.ruta_seleccionada);
                    super.onCreateContextMenu(menu, v, menuInfo);
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.menu_lista_rutas, menu);
                }
                break;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.RutasMenuIniciar:
                if (this.pend_ruta_seleccionada == 0) {

                    String DatosSeleccion[] = this.ruta_seleccionada.split("\\-");
                    new ShowDialog().showLoginDialog(this, Integer.parseInt(DatosSeleccion[0]),Integer.parseInt(DatosSeleccion[1]), DatosSeleccion[2]);

                }else if(this.FcnCfg.getPrinter().isEmpty() && this.impresion.equals("Si") ){

                    new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR,
                            "ERROR IMPRESORA", "No ha seleccionado la impresora con cual trabajar, verfique los parametros de configuracion.");

                }else if(!this.FcnBluetooth.EnabledBluetoth() && this.impresion.equals("Si")) {

                    new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR,
                            "ERROR BLUETOOTH", "Error con el bluetooth, verifique que se encuentre encendido.");
                }else{

                    this.new_form = new Intent(this, FormTomarLectura.class);
                    this.new_form.putExtra("Ruta", this.ruta_seleccionada);
                    startActivity(this.new_form);
                }
                return true;

            case R.id.RutasMenuSincronizar:
                new UploadLecturasJson(this).execute(this.ruta_seleccionada);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (!(myNetwork.isConectado() && pointGPS.isEstadoGPS()))
        {
            if(!(validarCodigo()&&pointGPS.isEstadoGPS())){
                new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR, "ERROR RED Y/O GPS",
                        "Verifique su conexion a internet y GPS.");
                listadoRutas.setEnabled(false);
            } else {
                listadoRutas.setEnabled(true);
            }

        }else{
            listadoRutas.setEnabled(true);
        }

    }

    public boolean validarCodigo(){
        String codigo = sqlConsulta.StrSelectShieldWhere("param_configuracion","valor","item='Codigo'");
        String codigoCalculado = utilidades.getMD5(this.FcnSession.getCodigo()+this.dateTime.getDateTimeLocal(_GET_DATE)).substring(0,6);
        if (codigo.equals(codigoCalculado)){
            return true;
        }
        else return  false;
    }
}
