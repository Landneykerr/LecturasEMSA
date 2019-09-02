package grupodesarrollo.insitu;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import dialogos.DialogValidacion;
import post_data.SyncDateTime;
import clases.ClassConfiguracion;
import clases.ClassSession;
import dialogos.ShowDialogBox;
import global.global_var;
import sistema.Bluetooth;
import sistema.DateTime;
import sistema.Utilidades;


public class FormConfiguracion extends ActionBarActivity implements OnClickListener, OnSeekBarChangeListener, global_var, DialogValidacion.DialogCodigoAperturaListener{
    private ClassConfiguracion  FcnCfg;
    private ClassSession        FcnUsuario;
    private Bluetooth           FcnBluetooth;
    private Utilidades          utilidades;
    private DateTime            time;

    private ArrayAdapter<String> AdapLstImpresoras;
    private ArrayList<String>   _listaImpresoras = new ArrayList<>();

    private ArrayAdapter<String> AdapLstOrientacion;
    private ArrayList<String>   _listaOrientacion = new ArrayList<>();

    private SeekBar             _barIntentos, _barFotos;
    private TextView            _lblIntentos, _lblFotos;
    private EditText            _txtServidor, _txtPuerto, _txtModulo, _txtWebService;
    private CheckBox            _chkTrabajoDatos, _chkBusqueda, _chkVoucherVertical;
    private Spinner             _cmbImpresora;
    private Button              _btnGuardar;
    private String              tecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Bundle bundle       = getIntent().getExtras();
        this.tecnico        = bundle.getString("Tecnico");

        this.FcnCfg         = ClassConfiguracion.getInstance(this);
        this.FcnUsuario     = ClassSession.getInstance(this);
        this.FcnBluetooth   = Bluetooth.getInstance();
        this.utilidades     = Utilidades.getInstance();
        this.time           = DateTime.getInstance();

        this._barIntentos   = (SeekBar) findViewById(R.id.ConfiguracionBarIntentos);
        this._barFotos      = (SeekBar) findViewById(R.id.ConfiguracionBarFotos);

        this._lblFotos      = (TextView) findViewById(R.id.ConfiguracionLblNumFotos);
        this._lblIntentos   = (TextView) findViewById(R.id.ConfiguracionLblNumIntentos);

        this._txtServidor   = (EditText) findViewById(R.id.ConfiguracionTxtServidor);
        this._txtPuerto     = (EditText) findViewById(R.id.ConfiguracionTxtPuerto);
        this._txtModulo     = (EditText) findViewById(R.id.ConfiguracionTxtModulo);
        this._txtWebService = (EditText) findViewById(R.id.ConfiguracionTxtWebService);
        this._cmbImpresora  = (Spinner) findViewById(R.id.ConfiguracionCmbImpresora);
        this._btnGuardar    = (Button) findViewById(R.id.ConfiguracionBtnGuardar);

        this._chkVoucherVertical= (CheckBox) findViewById(R.id.ConfiguracionChkVoucherVertical);
        this._chkTrabajoDatos = (CheckBox) findViewById(R.id.ConfiguracionChkDatos);
        this._chkBusqueda       = (CheckBox) findViewById(R.id.ConfiguracionChkBusqueda);

        this._listaImpresoras   = this.FcnBluetooth.GetDeviceBluetoothByAddress();
        this.AdapLstImpresoras  = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,this._listaImpresoras);


        this._cmbImpresora.setAdapter(this.AdapLstImpresoras);
        this._cmbImpresora.setSelection(this.AdapLstImpresoras.getPosition(this.FcnCfg.getPrinter()));

        this._txtServidor.setText(this.FcnCfg.getIp_server());
        this._txtPuerto.setText(this.FcnCfg.getPort());
        this._txtModulo.setText(this.FcnCfg.getModule_web_service());
        this._txtWebService.setText(this.FcnCfg.getWeb_service());
        this._chkTrabajoDatos.setChecked(this.FcnCfg.isFacturas_en_sitio());
        this._chkBusqueda.setChecked(this.FcnCfg.isBusqueda());
        this._chkVoucherVertical.setChecked(this.FcnCfg.isVerticalVouvher());
        this._barIntentos.setProgress(this.FcnCfg.getIntentos_lectura() - 1);
        this._barFotos.setProgress(this.FcnCfg.getMinimo_fotos() - 1);
        this._lblIntentos.setText(String.valueOf(this.FcnCfg.getIntentos_lectura()));
        this._lblFotos.setText(String.valueOf(this.FcnCfg.getMinimo_fotos()));


        if(this.FcnUsuario.getNivel()==0){
            this._barFotos.setEnabled(true);
            this._barIntentos.setEnabled(true);
            this._txtServidor.setEnabled(true);
            this._txtPuerto.setEnabled(true);
            this._txtModulo.setEnabled(true);
            this._txtWebService.setEnabled(true);
            this._chkTrabajoDatos.setEnabled(true);
            this._chkBusqueda.setEnabled(true);
        }else{
            this._barFotos.setEnabled(false);
            this._barIntentos.setEnabled(false);
            this._txtServidor.setEnabled(false);
            this._txtPuerto.setEnabled(false);
            this._txtModulo.setEnabled(false);
            this._txtWebService.setEnabled(false);
            this._chkTrabajoDatos.setEnabled(true);
            this._chkBusqueda.setEnabled(false);
        }
        this._btnGuardar.setOnClickListener(this);
        this._barFotos.setOnSeekBarChangeListener(this);
        this._barIntentos.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        new SyncDateTime(this).execute();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ConfiguracionBtnGuardar:
                if(this.FcnCfg.isFacturas_en_sitio()){
                    showEditDialog();
                }
                else {
                    guardarDatosConfiguracion();
                }

                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.ConfiguracionBarIntentos:
                this._lblIntentos.setText(String.valueOf(progress+1));
                break;

            case R.id.ConfiguracionBarFotos:
                this._lblFotos.setText(String.valueOf(progress+1));
                break;
        }
    }

    public void guardarDatosConfiguracion(){
        this.FcnCfg.setIp_server(this._txtServidor.getText().toString());
        this.FcnCfg.setPort(this._txtPuerto.getText().toString());
        this.FcnCfg.setModule_web_service(this._txtModulo.getText().toString());
        this.FcnCfg.setWeb_service(this._txtWebService.getText().toString());
        this.FcnCfg.setPrinter(this._cmbImpresora.getSelectedItem().toString());
        this.FcnCfg.setFacturas_en_sitio(this._chkTrabajoDatos.isChecked());
        this.FcnCfg.setVerticalVouvher(this._chkVoucherVertical.isChecked());
        this.FcnCfg.setBusqueda(this._chkBusqueda.isChecked());
        this.FcnCfg.setMinimo_fotos(this._barFotos.getProgress() + 1);
        this.FcnCfg.setIntentos_lectura(this._barIntentos.getProgress() + 1);

        new ShowDialogBox().showDialogBox(this, DIALOG_INFORMATIVE, "CONFIGURACION", "Configuracion guardada correctamente..");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void showEditDialog(){
        FragmentManager fm = getSupportFragmentManager();
        DialogValidacion dialogCodigoApertura = new DialogValidacion();
        dialogCodigoApertura.show(fm,"fragment_codigo_activacion");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        String codigo =utilidades.getMD5(this.tecnico+this.time.getDateTimeLocal(_GET_DATE)).substring(0,6);
        if(codigo.equals(inputText)){
            if(FcnCfg.guardarCodigoValidacion(codigo)){
                guardarDatosConfiguracion();
            }
        }else{
            new ShowDialogBox().showDialogBox(this, DIALOG_ERROR, "CONFIGURACION", "EL codigo de Activacion No Coincide");
        }
    }
}
