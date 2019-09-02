package grupodesarrollo.insitu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import connectivity.CustomPhoneStateListener;
import connectivity.Network;
import dao.FacturaDAO;
import dao.LecturaDAO;
import dialogos.DialogoMedidorNC;
import gps.PointGPS;
import post_data.UpLoadErrorPrinter;
import post_data.UpLoadFoto;
import post_data.UploaderFoto;
import printerZebra.ListenerPrint;
import sistema.SQLite;
import sistema.Utilidades;
import vo.TomaLectura;
import post_data.SyncDateTime;
import printerZebra.PrintVoucher;
import clases.ClassAnomalias;
import clases.ClassConfiguracion;
import clases.ClassSession;
import clases.ClassTipoUso;
import clases.ManagerImageResize;
import dialogos.DialogoInformativo;
import dialogos.ShowDialog;
import dialogos.ShowDialogBox;
import global.global_var;
import sistema.Archivos;
import sistema.DateTime;
import gps.CustomListenerGPS;
import vo.VoucherVO;


public class FormTomarLectura extends ActionBarActivity implements OnClickListener, OnItemSelectedListener, global_var, Observer, ListenerPrint {
    static int 				    INICIAR_CAMARA			= 1;
    static int                  FROM_BUSCAR             = 2;
    static int                  FINAL_RUTA              = 3;
    static int                  UBICACION_TERRENO       = 4;
    static int                  REGISTRO_ANOMALIA       = 5;

    private Bundle              estadoMedidorNC;
    private Intent 			    IniciarCamara;
    private Intent              new_form;

    private String[] arrayPreguntas = {"Caja Medidor NO Conforme","Sellos Medidor NO Conforme"};
    final ArrayList selectedItems=new ArrayList();


    private CustomPhoneStateListener fcnPhone;
    private Network                 myNetwork;

    private LocationManager         managerLocation;
    private CustomListenerGPS       listenerGPS;
    private PointGPS                pointGPS;

    private ListenerPrint       listenerPrint;


    private ClassSession        FcnSession;
    private ClassConfiguracion  FcnCfg;
    private ManagerImageResize  FcnImage;


    private VoucherVO           myVoucher;
    private FacturaDAO          facturaDAO;
    private LecturaDAO          lecturaDAO;


    public static TomaLectura   FcnLectura;
    private ClassAnomalias      FcnAnomalias;
    private ClassTipoUso        FcnTipoUso;
    private DateTime            FcnDataTime;
    private Utilidades          utilidades;


    private DialogoInformativo  dialogo;
    private Bundle              argumentos;

    private TextView        _lblCuenta, _lblNombre, _lblDireccion, _lblRuta, _lblMedidor, _lblLectura1, _lblLectura2, _lblLectura3, _lblMsjCodificado;
    public EditText  _txtLectura1, _txtLectura2, _txtLectura3, _txtMensaje;
    public Spinner   _cmbTipoUso, _cmbAnomalia, _cmbMensajes;
    public Button    _btnGuardar, _btnSiguiente, _btnAnterior;

    private String          _ruta[];
    private String 		    fotoParcial;
    private String          nombre_foto;

    private ArrayAdapter<String>    AdaptadorAnomalias;
    private ArrayAdapter<String>    AdaptadorUso;

    private ArrayAdapter<String> listadoMsjCodificados;
    private ArrayList<String> arrayMensajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_lectura);

        Bundle bundle       = getIntent().getExtras();
        listenerGPS         = CustomListenerGPS.getInstance(this);
        listenerPrint       = this;

        this._ruta          = bundle.getString("Ruta").split("\\-");

        this.IniciarCamara	= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        this.lecturaDAO = LecturaDAO.getInstance(this);
        this.facturaDAO = FacturaDAO.getInstance(this);
        this.utilidades = Utilidades.getInstance();

        this.FcnImage           = ManagerImageResize.getInstance();
        this.FcnSession         = ClassSession.getInstance(this);
        this.FcnCfg             = ClassConfiguracion.getInstance(this);
        this.FcnAnomalias       = ClassAnomalias.getInstance(this);
        this.FcnTipoUso         = ClassTipoUso.getInstance(this);

        this.FcnLectura         = new TomaLectura(this, Integer.parseInt(this._ruta[0]), Integer.parseInt(this._ruta[1]),
                this._ruta[2], this._ruta[3]);


        this.dialogo            = new DialogoInformativo();
        this.FcnDataTime        = DateTime.getInstance();
        this.argumentos         = new Bundle();


        this._cmbAnomalia   = (Spinner) findViewById(R.id.LecturaSpnAnomalia);
        this._cmbTipoUso    = (Spinner) findViewById(R.id.LecturaSpnTipoUso);

        this._lblCuenta     = (TextView) findViewById(R.id.LecturaTxtCuenta);
        this._lblNombre     = (TextView) findViewById(R.id.LecturaTxtNombre);
        this._lblDireccion  = (TextView) findViewById(R.id.LecturaTxtDireccion);
        this._lblRuta       = (TextView) findViewById(R.id.LecturaTxtRuta);
        this._lblMedidor    = (TextView) findViewById(R.id.LecturaTxtMedidor);

        this._lblLectura1   = (TextView) findViewById(R.id.LecturaTxtLectura1);
        this._lblLectura2   = (TextView) findViewById(R.id.LecturaTxtLectura2);
        this._lblLectura3   = (TextView) findViewById(R.id.LecturaTxtLectura3);

        this._txtLectura1   = (EditText) findViewById(R.id.LecturaEditLectura1);
        this._txtLectura2   = (EditText) findViewById(R.id.LecturaEditLectura2);
        this._txtLectura3   = (EditText) findViewById(R.id.LecturaEditLectura3);
        this._txtMensaje    = (EditText) findViewById(R.id.LecturaEditMensaje);

        _cmbMensajes = (Spinner) findViewById(R.id.LecturasCmbMsj);


        this._btnGuardar    = (Button) findViewById(R.id.LecturasBtnGuardar);
        this._btnSiguiente  = (Button) findViewById(R.id.LecturaBtnSiguiente);
        this._btnAnterior   = (Button) findViewById(R.id.LecturaBtnAnterior);

        if(this.FcnLectura.getDatosUsuario(true)){
            this.MostrarInformacionBasica();
            this._btnGuardar.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        }


        this.AdaptadorAnomalias = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,this.FcnAnomalias.getAnomalias(this.FcnLectura.getInfUsuario().getTipo_uso()));
        this._cmbAnomalia.setAdapter(this.AdaptadorAnomalias);


        this.AdaptadorUso = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.FcnTipoUso.getTipoUso());
        this._cmbTipoUso.setAdapter(this.AdaptadorUso);

        this._cmbTipoUso.setOnItemSelectedListener(this);
        this._cmbAnomalia.setOnItemSelectedListener(this);


        /**Estado de los botones de anterior y siguiente segun se tenga habilitado buscar clientes no leidos**/
        this._btnAnterior.setEnabled(false);
        this._btnSiguiente.setEnabled(false);

        this._btnGuardar.setOnClickListener(this);
        this._btnAnterior.setOnClickListener(this);
        this._btnSiguiente.setOnClickListener(this);
        //this._lstMsjCodificados.setOnItemClickListener(this);
        _cmbMensajes.setOnItemSelectedListener(this);
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
            this.managerLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listenerGPS);
            pointGPS.addObserver(this);
        }catch(SecurityException se){

        }catch(Exception ex){
            Log.i("Error GPS", ex.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tomar_lectura, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.LecturaMenuFoto).setEnabled(myNetwork.isConectado() && pointGPS.isEstadoGPS() || validarCodigo() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.LecturaMenuBuscar).setEnabled(myNetwork.isConectado() && pointGPS.isEstadoGPS() || validarCodigo() && pointGPS.isEstadoGPS());
        menu.findItem(R.id.LecturaMenuUbicacion).setEnabled(myNetwork.isConectado() && pointGPS.isEstadoGPS() || validarCodigo() && pointGPS.isEstadoGPS());
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.LecturaMenuBuscar:
                this.new_form = new Intent(this, FormBuscar.class);
                this.new_form.putExtra("Ciclo", this.FcnLectura.getInfUsuario().getIdCiclo());
                this.new_form.putExtra("Ruta", this.FcnLectura.getInfUsuario().getRuta());
                this.new_form.putExtra("Municipio", this.FcnLectura.getInfUsuario().getId_municipio());
                this.new_form.putExtra("Tipo", this.FcnLectura.getInfUsuario().getTipo());
                startActivityForResult(this.new_form, FROM_BUSCAR);
                break;

            case R.id.LecturaMenuFoto:
                this.getFoto();
                break;

            case R.id.LecturaMenuRendimiento:
                new ShowDialog().showLoginDialog(this, Integer.parseInt(this._ruta[0]),this.FcnLectura.getInfUsuario().getId_municipio(), this.FcnLectura.getInfUsuario().getRuta());
                break;

            case R.id.LecturaMenuUbicacion:
                this.new_form =new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<4.08295833333333>,<-73.6700166666667>?q=<4.08295833333333>,<-73.6700166666667>(Cuenta "+this.FcnLectura.getInfUsuario().getCuenta()+")"));
                startActivityForResult(this.new_form, UBICACION_TERRENO);
                break;

            case R.id.LecturaMenuReImprimir:
                try{
                    if(this.myVoucher.getNombre() == null){
                        new ShowDialogBox().showDialogBox(this, DIALOG_WARNING, "IMPRESION", "No se ha realizado una impresion y el cliente actual no esta leido.");
                    }else {
                        new PrintVoucher(this, this.myVoucher, listenerPrint).execute();
                    }
                }catch (Exception e){
                    new ShowDialogBox().showDialogBox(this, DIALOG_WARNING, "IMPRESION", "No se ha realizado una impresion y el cliente actual no esta leido.");
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void MostrarInformacionBasica(){

        if(FcnLectura.isMedidorNC())
        {
            this.DialogoConformidadMedidores();
        }


        this._lblRuta.setText(this.FcnLectura.getInfUsuario().getRuta());
        this._lblCuenta.setText(this.FcnLectura.getInfUsuario().getCuenta() + "");
        this._lblMedidor.setText(this.FcnLectura.getInfUsuario().getMarca_medidor()+" "+this.FcnLectura.getInfUsuario().getSerie_medidor());
        this._lblNombre.setText(this.FcnLectura.getInfUsuario().getNombre());
        this._lblDireccion.setText(this.FcnLectura.getInfUsuario().getDireccion());

        this._lblLectura1.setText(this.FcnLectura.getInfUsuario().getTipo_energia1());
        this._lblLectura2.setText(this.FcnLectura.getInfUsuario().getTipo_energia2());
        this._lblLectura3.setText(this.FcnLectura.getInfUsuario().getTipo_energia3());
        this._txtLectura1.setText("");
        this._txtLectura2.setText("");
        this._txtLectura3.setText("");


        if(this.FcnLectura.getInfUsuario().isView_tipo_energia1() && this.FcnLectura.getInfUsuario().isNeedLectura()){
            this._lblLectura1.setVisibility(View.VISIBLE);
            this._txtLectura1.setVisibility(View.VISIBLE);
        }else{
            this._lblLectura1.setVisibility(View.INVISIBLE);
            this._txtLectura1.setVisibility(View.INVISIBLE);
        }

        if(this.FcnLectura.getInfUsuario().isView_tipo_energia2() && this.FcnLectura.getInfUsuario().isNeedLectura()){
            this._lblLectura2.setVisibility(View.VISIBLE);
            this._txtLectura2.setVisibility(View.VISIBLE);
        }else{
            this._lblLectura2.setVisibility(View.INVISIBLE);
            this._txtLectura2.setVisibility(View.INVISIBLE);
        }

        if(this.FcnLectura.getInfUsuario().isView_tipo_energia3() && this.FcnLectura.getInfUsuario().isNeedLectura()){
            this._lblLectura3.setVisibility(View.VISIBLE);
            this._txtLectura3.setVisibility(View.VISIBLE);
        }else{
            this._lblLectura3.setVisibility(View.INVISIBLE);
            this._txtLectura3.setVisibility(View.INVISIBLE);
        }

        this._txtMensaje.setText("");

        this._btnGuardar.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._cmbAnomalia.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtLectura1.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtLectura2.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtLectura3.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtMensaje.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._cmbTipoUso.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());

        //Restriccion de cantidad de digitos a ingresar
        this._txtLectura1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.FcnLectura.getInfUsuario().getDigitosMedidor())});
        this._cmbTipoUso.setSelection(0);

        /**ESTE COMBO SE MODIFICA DEBIDO A QUE LOS MENSAJES DEPENDE DEL TIPO DE USO DEL CLIENTE**/
        this.arrayMensajes = this.FcnTipoUso.getMensajesCodificados(this.FcnLectura.getInfUsuario().getTipo_uso());
        this.listadoMsjCodificados  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayMensajes);
        _cmbMensajes.setAdapter(listadoMsjCodificados);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.LecturaBtnAnterior:
                if(this.FcnLectura.getDatosUsuario(false)) {
                    this._cmbAnomalia.setSelection(0);
                    this._cmbTipoUso.setSelection(0);
                    this.MostrarInformacionBasica();
                }
                break;

            case R.id.LecturaBtnSiguiente:
                if(this.FcnLectura.getDatosUsuario(true)){
                    this._cmbAnomalia.setSelection(0);
                    this._cmbTipoUso.setSelection(0);
                    this.MostrarInformacionBasica();
                }
                break;

            case R.id.LecturasBtnGuardar:
                if(!this.FcnCfg.isHora_sincronizada()){
                    new ShowDialogBox().showDialogBox(this, DIALOG_ERROR,
                            "FECHA Y HORA", "La hora y/o fecha del movil no es valida.");

                }else if(FcnLectura.getInfUsuario().getAnomalia_anterior() == 20)
                {
                    new ShowDialogBox().showDialogBox(this, DIALOG_ERROR,
                            "ANOMALIA NO VALIDA.", "Este predio presento anomalia 20 el mes anterior.");

                }else if(FcnLectura.getInfUsuario().getAnomalia_anterior() == 36)
                {
                    new ShowDialogBox().showDialogBox(this, DIALOG_ERROR,
                            "ANOMALIA NO VALIDA.", "Este predio presento anomalia 36 el mes anterior.");

                }else if(this.FcnLectura.getInfUsuario().isNeedMensaje() && this._txtMensaje.getText().toString().isEmpty())
                {
                    this.argumentos.clear();
                    this.argumentos.putString("Titulo","ERROR.");
                    this.argumentos.putString("Mensaje","No se ha ingresado un mensaje valido.");
                    this.dialogo.setArguments(argumentos);
                    this.dialogo.show(getFragmentManager(), "SaveDialog");

                }else{
                    this.FcnLectura.preCritica(this._txtLectura1.getText().toString(),this._txtLectura2.getText().toString(),this._txtLectura3.getText().toString());

                    //El siguiente if se divide en dos partes que es cuando se necesita foto por anomalia o cuando se necesita para
                    //confirmar critica
                    if((this.FcnLectura.getInfUsuario().getTipo().equals("R") || this.FcnLectura.getInfUsuario().getTipo().equals("V"))
                            && this.FcnLectura.getInfUsuario().getCountFotos() < this.FcnCfg.getMinimo_fotos()){
                        this.getFoto();
                    }else if( this.FcnLectura.getInfUsuario().isNeedFotoByAnomalia() && this.FcnLectura.getInfUsuario().getCountFotos() < this.FcnCfg.getMinimo_fotos()
                            && this.FcnLectura.getInfUsuario().isNeedFotoByRuta()){
                        this.getFoto();
                    }else if(this.FcnLectura.getInfUsuario().isHaveCritica() && this.FcnLectura.getInfUsuario().getCountFotos() < this.FcnCfg.getMinimo_fotos() &&
                            this.FcnLectura.getInfUsuario().getIntentos() < this.FcnCfg.getIntentos_lectura() && this.FcnLectura.getInfUsuario().isNeedFotoByRuta()){
                        this.getFoto();
                    }else if(this.FcnLectura.getFotoCiclo()&& this.FcnLectura.getInfUsuario().isNeedFotoByRuta() && this.FcnLectura.getInfUsuario().getCountFotos() < this.FcnCfg.getMinimo_fotos()){
                        this.getFoto();
                    }else if(this.FcnLectura.getInfUsuario().getCritica1()=="Consumo 0" && this._txtMensaje.getText().toString().isEmpty() && this.FcnLectura.getInfUsuario().getAnomalia()==0){
                        new ShowDialogBox().showDialogBox(this,DIALOG_ERROR,"Consumo 0", "Debe Ingresar Mensaje o Anomalia.");
                    } else if(this.FcnTipoUso.isNeedCIIU(this.FcnLectura.getInfUsuario().getNewTipoUso(),this.FcnLectura.getInfUsuario().getTipo_uso())) {
                        new ShowDialogBox().showDialogBox(this,DIALOG_ERROR,"CODIGO CIIU", "No ha seleccionado un codigo CIIU valido.");
                    }else if((this.FcnLectura.getInfUsuario().isNeedLectura() && !this._txtLectura1.getText().toString().isEmpty()) ||
                                !this.FcnLectura.getInfUsuario().isNeedLectura()){

                        if(this.FcnLectura.guardarLectura(this._txtLectura1.getText().toString(),
                                this._txtLectura2.getText().toString(),
                                this._txtLectura3.getText().toString(),
                                this._txtMensaje.getText().toString(),
                                pointGPS.getLongitudGPS(),
                                pointGPS.getLatitudGPS(),
                                Integer.parseInt(this.FcnSession.getCodigo()))){

                            this._txtLectura1.setText("");
                            this._txtLectura2.setText("");
                            this._txtLectura3.setText("");

                            if (this.FcnLectura.getInfUsuario().isLeido()) {
                                this._btnGuardar.setEnabled(false);

                                this.getInformacionVoucher(this.FcnLectura.getInfUsuario().getId_serial1(),
                                        this.FcnLectura.getInfUsuario().getId_serial2(), this.FcnLectura.getInfUsuario().getId_serial3());

                                if(this.FcnLectura.getInfUsuario().isImpresion()){
                                    new PrintVoucher(this, this.myVoucher, listenerPrint).execute();
                                }

                                if (this.FcnLectura.getDatosUsuario(true))
                                {
                                    this._cmbAnomalia.setSelection(0);
                                    this._cmbTipoUso.setSelection(0);
                                    this.MostrarInformacionBasica();
                                } else {
                                    this.argumentos.clear();
                                    this.argumentos.putString("Titulo", "FIN DE RUTA.");
                                    this.argumentos.putString("Mensaje", "Se ha llegado al final de la ruta.");
                                    this.dialogo.setArguments(argumentos);
                                    this.dialogo.show(getFragmentManager(), "SaveDialog");
                                }
                            } else if (this.FcnLectura.getInfUsuario().isHaveCritica()) {
                                this.argumentos.clear();
                                this.argumentos.putString("Titulo", "ERROR.");
                                this.argumentos.putString("Mensaje", this.FcnLectura.getInfUsuario().getMsjCritica1());
                                this.dialogo.setArguments(argumentos);
                                this.dialogo.show(getFragmentManager(), "SaveDialog");
                            }
                        }else {
                            this.argumentos.clear();
                            this.argumentos.putString("Titulo", "ERROR.");
                            this.argumentos.putString("Mensaje", "No ha sido posible registrar la lectura.");
                            this.dialogo.setArguments(argumentos);
                            this.dialogo.show(getFragmentManager(), "SaveDialog");
                        }
                    }else {
                        this.argumentos.clear();
                        this.argumentos.putString("Titulo","ERROR.");
                        this.argumentos.putString("Mensaje","No ha sido posible registrar la lectura.");
                        this.dialogo.setArguments(argumentos);
                        this.dialogo.show(getFragmentManager(), "SaveDialog");
                    }
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.LecturaSpnAnomalia:
                String _anomalia[] = this._cmbAnomalia.getSelectedItem().toString().split("-");
                this.FcnLectura.getInfUsuario().setAnomalia(Integer.parseInt(_anomalia[0]), _anomalia[1]);

                if(_anomalia[0].equals("12")){
                    this.new_form = new Intent(this, FormAnomalia12.class);
                    startActivityForResult(this.new_form, REGISTRO_ANOMALIA);
                }

                if((this.FcnLectura.getInfUsuario().getAnomalia_anterior() != Integer.parseInt(_anomalia[0]))  ){
                    if(this.FcnLectura.getInfUsuario().isNeedFotoByAnomalia() && this.FcnLectura.getInfUsuario().isNeedFotoByRuta()
                            && this.FcnLectura.getInfUsuario().getCountFotos() < this.FcnCfg.getMinimo_fotos()){
                        this.getFoto();
                    }

                    this.argumentos.clear();
                    this.argumentos.putString("Titulo","ALERTA.");
                    this.argumentos.putString("Mensaje", "La anomalia seleccionada no coincide con la anomalia anterior.");
                    this.dialogo.setArguments(argumentos);
                    this.dialogo.show(getFragmentManager(), "SaveDialog");
                }

                this.MostrarInformacionBasica();
                break;

            case R.id.LecturaSpnTipoUso:
                this.FcnLectura.getInfUsuario().setNewTipoUso(this.FcnTipoUso.getCodeTipoUso(this._cmbTipoUso.getSelectedItem().toString()));
                break;


            case R.id.LecturasCmbMsj:
                if(!_cmbMensajes.getSelectedItem().toString().equals("..."))
                {
                    this._txtMensaje.append("("+this._cmbMensajes.getSelectedItem().toString()+")");
                }
                break;


            default:
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(resultCode == RESULT_OK && requestCode == FROM_BUSCAR){
                if(data.getExtras().getBoolean("response")){
                    this.FcnLectura.getInfUsuario().setFlagSearch(false);
                    this.FcnLectura.getInfUsuario().setBackupMunicipio(this.FcnLectura.getInfUsuario().getMunicipio());
                    this.FcnLectura.getInfUsuario().setBackupRuta(this.FcnLectura.getInfUsuario().getRuta());
                    this.FcnLectura.getInfUsuario().setBackupConsecutivo(this.FcnLectura.getInfUsuario().getId_consecutivo());

                    this.FcnLectura.getSearchDatosUsuario(data.getExtras().getString("cuenta"),data.getExtras().getString("medidor"));
                    this.getInformacionVoucher(this.FcnLectura.getInfUsuario().getId_serial1(),
                            this.FcnLectura.getInfUsuario().getId_serial2(), this.FcnLectura.getInfUsuario().getId_serial3());

                    this._cmbAnomalia.setSelection(0);
                    this._cmbTipoUso.setSelection(0);
                    this.MostrarInformacionBasica();
                }
            }else if(resultCode == RESULT_OK && requestCode == INICIAR_CAMARA){
                this.FcnImage.resizeImage(this.nombre_foto,this.FcnLectura.getInfUsuario().getIdCiclo(),_WIDTH_PICTURE, _HEIGHT_PICTURE);
                this.FcnLectura.getNumeroFotos(this.FcnLectura.directorioCiclo);

                this.FcnLectura.setFechaFoto(String.valueOf(this.FcnLectura.getInfUsuario().getCuenta()),this.nombre_foto,this.FcnDataTime.getDateTimeLocalFoto(),this._ruta[0],"");

            }else if(resultCode == RESULT_OK && requestCode == FINAL_RUTA){
                this.finish();
            }else if(resultCode == RESULT_OK && requestCode == REGISTRO_ANOMALIA){
                if(data.getExtras().getBoolean("response")){
                    this._txtMensaje.append(data.getExtras().getString("datosAnomalia"));
                }
            }
        }catch(Exception e){

        }
    }

    private void getFoto(){
        this.nombre_foto    = this.FcnLectura.getInfUsuario().getCuenta()+"_"+this.FcnLectura.getInfUsuario().getCountFotos()+".jpg";
        File imagesFolder   = new File(this.FcnLectura.directorioCiclo);
        File image          = new File(imagesFolder, this.nombre_foto);

        this.fotoParcial = image.toString();
        Uri uriSavedImage = Uri.fromFile(image);
        this.IniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(IniciarCamara, INICIAR_CAMARA);
    }


    private void getInformacionVoucher(int _idSerial1, int _idSerial2, int _idSerial3)
    {
        this.myVoucher = lecturaDAO.getLectura(_idSerial1, _idSerial2, _idSerial3);
    }

    @Override
    public void update(Observable observable, Object data) {

        if (!(myNetwork.isConectado() && pointGPS.isEstadoGPS()))
        {
            if(!(validarCodigo()&&pointGPS.isEstadoGPS())){
                new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR, "ERROR RED Y/O GPS",
                        "Verifique su conexion a internet y GPS.");
                this.inhabilitarFormulario(true, false);
            } else {
                this.inhabilitarFormulario(true, true);
            }

            /*new ShowDialogBox().showDialogBox(this, global_var.DIALOG_ERROR, "ERROR RED Y/O GPS",
                    "Verifique su conexion a internet y GPS.");

            this.inhabilitarFormulario(true, false);*/

        }else{
            this.inhabilitarFormulario(true, true);
        }

    }

    public boolean validarCodigo(){
        String codigoCalculado = utilidades.getMD5(this.FcnSession.getCodigo()+this.FcnDataTime.getDateTimeLocal(_GET_DATE)).substring(0,6);
        if (lecturaDAO.getCodigoValidacion().equals(codigoCalculado)){
            return true;
        }
        else return  false;
    }


    private void inhabilitarFormulario(boolean _rtaImpresion, boolean _estadoConexion)
    {
        _cmbAnomalia.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());
        _cmbTipoUso.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());

        //_btnAnterior.setEnabled(_rtaImpresion && _estadoConexion);
        //_btnSiguiente.setEnabled(_rtaImpresion && _estadoConexion);
        _btnGuardar.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());

        _txtLectura1.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());
        _txtLectura2.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());
        _txtLectura3.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());
        _txtMensaje.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());

        _cmbMensajes.setEnabled(_rtaImpresion && _estadoConexion && !FormTomarLectura.FcnLectura.getInfUsuario().isLeido());


        invalidateOptionsMenu();
    }


    @Override
    public void printFinish(boolean _status, String _msg) {
        this.inhabilitarFormulario(_status, true);

        ContentValues   tempRegistro = new ContentValues();
        SQLite          FcnSQL = new SQLite(this);

        if(!_status){
            new ShowDialogBox().showDialogBox(this, DIALOG_ERROR, "Cuenta: " + myVoucher.getCodigo(), _msg);

            tempRegistro.clear();
            tempRegistro.put("cuenta", myVoucher.getCodigo());
            tempRegistro.put("id_inspector", myVoucher.getInspector());
            tempRegistro.put("error", _msg);
            tempRegistro.put("fecha_toma", DateTime.getInstance().getDateTimeLocal());
            FcnSQL.InsertRegistro("registro_impresion", tempRegistro);
            new UpLoadErrorPrinter(this).execute();
        }

    }


    private void DialogoConformidadMedidores()
    {
        selectedItems.clear();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Conformidad Sellos y Caja Porta Medidor")
                .setMultiChoiceItems(arrayPreguntas, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        if(selectedItems.contains(0)){
                            _txtMensaje.append("(Caja NO Conforme)");
                        }
                        if(selectedItems.contains(1)){
                            _txtMensaje.append("(Sellos NO Conforme)");
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }
}
