package clases;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

import sistema.SQLite;

/**
 * Created by JULIANEDUARDO on 27/02/2015.
 */
public class ClassCritica {
    private static Context              context;
    private SQLite                      FcnSQL;
    private static ClassCritica         ourInstance;
    private ArrayList<ContentValues>    _tempTabla;
    private ContentValues               _tempRegistro;

    //private static ClassCritica ourInstance = new ClassCritica();

    public static ClassCritica getInstance(Context _ctx) {
        if(ourInstance == null){
            ourInstance = new ClassCritica(_ctx);
        }else{
            context = _ctx;
        }
        return ourInstance;
    }

    private ClassCritica(Context _ctx) {
        this.context    = _ctx;
        this._tempRegistro  = new ContentValues();
        this._tempTabla     = new ArrayList<ContentValues>();
        this.FcnSQL         = new SQLite(this.context);
    }


    public double calculateCritica(int _newLectura, int _oldLectura, double _promedio, int _factorMultiplicacion){
        double critica;
        if(_promedio == 0){
            critica = ((_newLectura - _oldLectura)/(0.000001)) * _factorMultiplicacion;
        }else{
            critica = ((_newLectura - _oldLectura)/(_promedio)) * _factorMultiplicacion;
        }

        return critica;
    }

    public boolean haveCritica(String _critica){
        //String descripcion = this.FcnSQL.StrSelectShieldWhere("param_critica","descripcion","minimo<="+_critica+" AND maximo > "+_critica);
        /*if(_critica.equals("Normal")){
            return false;
        }else return true;*/
        return !_critica.equals("Normal");
    }

    public String getDescripcionCritica(double _critica){
        //Se debe retornar el valor que ya menejamos..
        return this.FcnSQL.StrSelectShieldWhere("param_critica","descripcion","minimo<="+_critica+" AND maximo > "+_critica+"");
    }

    public String getMensajeCritica(String _critica){
        //Se valida con el rangoo que tenga...
        //return this.FcnSQL.StrSelectShieldWhere("param_critica","mensaje","minimo<="+_critica+" AND maximo > "+_critica+"");
        if(_critica.equals("Consumo 0")){
          return "Consumo 0, verifique el predio.";
        }else return "Se ha generado critica, favor verificar.";
    }

    public String calcularNuevaCritica(double _newLectura, double _oldLectura, double _promedio, int _factorMultiplicacion){
        double promedio;
        double consumoActual;
        double variacion;
        String critica ="";

        if(_promedio == 0){
            _promedio = 0.000001;
        }
        ContentValues valores = new ContentValues();

        promedio = _promedio/_factorMultiplicacion;
        consumoActual = (_newLectura - _oldLectura)/_factorMultiplicacion;
        variacion   = (consumoActual - promedio)/promedio;

        if(_oldLectura == 0){
            critica = "Cliente nuevo";
        }else if(consumoActual > 0 && consumoActual <=240){
          critica = "Normal";
        }else if(consumoActual == 0){
            critica = "Consumo 0";
        }else if(consumoActual < 0){
            critica = "Consumo Negativo";
        }else {
            valores = this.FcnSQL.SelectDataRegistro("param_critica","descripcion,consumo,vr_incremento,vr_disminucion","prom_minimo<="+promedio+" AND prom_maximo >= "+promedio+"");

            //Valida si es la del primer rango.....
            if(valores.getAsInteger("consumo")!=-1){
                if(consumoActual>valores.getAsInteger("consumo")){
                    critica = valores.getAsString("descripcion");
                }else {
                    critica = "Normal";
                }
            }else{
                //Se validar si hay que tener en cuenta la variacion disminucion.
                if(valores.getAsDouble("vr_disminucion")==-1){
                    if(variacion>=valores.getAsDouble("vr_incremento")){
                        critica = valores.getAsString("descripcion");
                    }else {
                        critica = "Normal";
                    }
                }else{
                    if(variacion>=valores.getAsDouble("vr_incremento") || variacion<=valores.getAsDouble("vr_disminucion")){
                        critica = valores.getAsString("descripcion");
                    }else {
                        critica = "Normal";
                    }
                }
            }

        }

        return critica;
    }

}
