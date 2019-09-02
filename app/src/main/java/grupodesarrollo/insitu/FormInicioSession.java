package grupodesarrollo.insitu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import beacon.ListenerBeacon;
import beacon.TimerCountDown;
import connectivity.Network;
import dialogos.ShowDialogBox;
import gps.PointGPS;
import post_data.DownLoadParametros;
import post_data.DownLoadTrabajo;
import post_data.UpLoadErrorPrinter;
import post_data.UploadLecturasJson;
import post_data.SyncDateTime;
import post_data.UpLoadFoto;

import clases.ClassConfiguracion;
import clases.ClassFlujoInformacion;
import clases.ClassSession;
import connectivity.CustomPhoneStateListener;
import sistema.Bluetooth;
import sistema.DateTime;
import global.global_var;
import gps.CustomListenerGPS;


public class FormInicioSession extends ActionBarActivity implements OnClickListener, global_var, Observer, ListenerBeacon{

    private Intent                  new_form;
    private TimerCountDown          beacon;
    private ListenerBeacon          listenBeacon;

    private CustomPhoneStateListener fcnPhone;
    private Network                 myNetwork;

    private LocationManager         managerLocation;
    private CustomListenerGPS       listenerGPS;
    private PointGPS                pointGPS;


    private ClassSession            FcnSession;
    private ClassConfiguracion      FcnCfg;
    private ClassFlujoInformacion   FcnInf;
    private DateTime                FcnTime;

    private Button      _btnLoggin;
    private EditText    _txtCodigo;
    private TextView    _lblNombre, _lblVersionSoft, _lblVersionBD, _lblMacBluetooth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);

        this.FcnSession     = ClassSession.getInstance(this);
        this.FcnCfg         = ClassConfiguracion.getInstance(this);
        this.FcnInf         = new ClassFlujoInformacion(this);
        this.FcnTime        = DateTime.getInstance();

        this.listenerGPS    = CustomListenerGPS.getInstance(this);
        this.listenBeacon   = this;

        this._btnLoggin     = (Button) findViewById(R.id.LoginBtnIngresar);
        this._txtCodigo     = (EditText) findViewById(R.id.LoginEditTextCodigo);
        this._lblNombre     = (TextView) findViewById(R.id.LoginTxtNombre);
        this._lblVersionBD  = (TextView) findViewById(R.id.LoginTxtVersionBD);
        this._lblVersionSoft= (TextView) findViewById(R.id.LoginTxtVersionSoft);
        this._lblMacBluetooth=(TextView) findViewById(R.id.LoginTxtBluetooth);

        this._lblVersionBD.setText("Version BD "+this.FcnCfg.getVersion_bd());
        this._lblVersionSoft.setText("Version Software "+this.FcnCfg.getVersion_software());
        this._lblMacBluetooth.setText("MAC Bluetooth "+ Bluetooth.getInstance().GetOurDeviceByAddress());


        beacon = new TimerCountDown(86400000, 15000, listenBeacon);
        beacon.start();

        invalidateOptionsMenu();
        this._btnLoggin.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        new SyncDateTime(this).execute();

        /**
         * Instancias a objetos encargados de controlar el listener de cambios en la conexion de datos
         * **/
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio_session, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.InicioCargarParametros).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.InicioCargarRuta).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.InicioVerRutas).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.InicioCrearBackup).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.InicioConfiguracion).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.InicioSincronizarFotos).setEnabled(this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());

        this._txtCodigo.setEnabled(!this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS() );
        this._btnLoggin.setEnabled(!this.FcnSession.isInicio_sesion() && myNetwork.isConectado() && pointGPS.isEstadoGPS());
        this._lblNombre.setText(this.FcnSession.getNombre());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.InicioCargarParametros:
                new DownLoadParametros(this).execute(this.FcnSession.getCodigo()+"");
                break;

            case R.id.InicioCargarRuta:
                new DownLoadTrabajo(this).execute(this.FcnSession.getCodigo()+"");
                break;

            case R.id.InicioVerRutas:
                this.new_form = new Intent(this, FormInformacionRutas.class);
                startActivity(this.new_form);
                break;

            case R.id.InicioConfiguracion:
                this.new_form = new Intent(this, FormConfiguracion.class);
                this.new_form.putExtra("Tecnico", this.FcnSession.getCodigo());
                startActivity(this.new_form);
                break;

            case R.id.InicioCrearBackup:
                break;

            case R.id.InicioSincronizarFotos:
                new UpLoadFoto(this).execute();
                break;

            case R.id.InicioMenuSalir:
                this.FcnSession.IniciarSession(null);
                invalidateOptionsMenu();
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.LoginBtnIngresar:
                if(!this._txtCodigo.getText().toString().isEmpty())
                {
                    if(!this.FcnSession.IniciarSession(this._txtCodigo.getText().toString()))
                    {
                        new ShowDialogBox().showDialogBox(this,DIALOG_ERROR,"INICIO DE SESION", "Codigo incorrecto.");
                    }
                }
                invalidateOptionsMenu();
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        if (!(myNetwork.isConectado() && pointGPS.isEstadoGPS()))
        {
            new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR, "ERROR RED Y/O GPS",
                    "Verifique su conexion a internet y GPS."+myNetwork.getIntensidadGSM()+"--"+myNetwork.getIntensidadLTE());
        }
        invalidateOptionsMenu();
    }


    @Override
    public void finishBeacon() {
        new UploadLecturasJson(this).execute("");
        new UpLoadErrorPrinter(this).execute();
    }

}
