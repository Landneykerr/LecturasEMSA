package grupodesarrollo.insitu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class FormAnomalia12 extends ActionBarActivity implements View.OnClickListener {

    private EditText _txtLecturaStk, _serieMedidor, _marcaMedidor, _digitosMedidor;
    private Spinner  _cmbtipoMedidor;
    private String[] tipos = new String[]{"Monofasico","Bifasico","Trifasico","Digital"};
    private Button   _btonAceptar, _btonCancelar;

    private String   _datosAnomaliaDoce;
    private ArrayAdapter<String> AdaptadorTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_anomalia12);

        Bundle bundle       = getIntent().getExtras();

        this._txtLecturaStk    = (EditText)findViewById(R.id.LecturaEditAnomalia12);
        this._serieMedidor     = (EditText)findViewById(R.id.MedidorEditAnomalia12);
        this._marcaMedidor     = (EditText)findViewById(R.id.MarcaEditAnomalia12);
        this._digitosMedidor   = (EditText)findViewById(R.id.DigitosEditAnomalia12);
        this._cmbtipoMedidor   = (Spinner)findViewById(R.id.SpinnerTipoAnomalia12);
        this._btonAceptar      = (Button)findViewById(R.id.BtonAceptarAnomalia);
        this._btonCancelar     = (Button)findViewById(R.id.BtonCancelarAnomalia);

        this.AdaptadorTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipos);
        this._cmbtipoMedidor.setAdapter(this.AdaptadorTipos);

        this._btonAceptar.setOnClickListener(this);
        this._btonCancelar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_anomalia12, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.BtonAceptarAnomalia:
                    if(this._serieMedidor.getText().toString().isEmpty()){
                        Toast.makeText(this,"Debe Registrar Serie Medidor",Toast.LENGTH_SHORT).show();
                    }else if(this._marcaMedidor.getText().toString().isEmpty()){
                        Toast.makeText(this,"Debe Registrar Marca Medidor",Toast.LENGTH_SHORT).show();
                    }else if(this._digitosMedidor.getText().toString().isEmpty()){
                        Toast.makeText(this,"Debe Registrar Digitos Medidor",Toast.LENGTH_SHORT).show();
                    }else{
                        this._datosAnomaliaDoce = "LecturaSTK:"+_txtLecturaStk.getText().toString()+"-Medidor:"+_serieMedidor.getText().toString()+"-Marca:"+_marcaMedidor.getText().toString()+"-Tipo:"+_cmbtipoMedidor.getSelectedItem().toString()+"-Digitos:"+_digitosMedidor.getText().toString();
                        finish(true);
                    }
                break;

            case R.id.BtonCancelarAnomalia:
                finish(false);
                break;

            default:
                break;
        }
    }

    public void finish(boolean _caso) {
        Intent data = new Intent();
        if(_caso){
            data.putExtra("response", _caso);
            data.putExtra("datosAnomalia", _datosAnomaliaDoce );
        }
        setResult(RESULT_OK, data);
        super.finish();
    }
}
