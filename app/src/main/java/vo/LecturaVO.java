package vo;

import android.content.Context;

/**
 * Created by Julian Poveda on 18/11/2015.
 */
public class LecturaVO extends VoucherVO {
    private String anomalia;
    private String medidor;
    private String fecha;
    private String hora;
    private String strLectura;


    public LecturaVO(String cuenta, String nombre, String direccion, String municipio, int inspector){
        super(cuenta, nombre, direccion, municipio, inspector);

        this.anomalia = null;
        this.medidor = null;
        this.fecha = null;
        this.hora = null;
        this.strLectura = null;

    }



    public String getAnomalia() {
        return anomalia;
    }

    public void setAnomalia(String anomalia) {
        this.anomalia = anomalia;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStrLectura() {
        return strLectura;
    }

    public void setStrLectura(String strLectura) {
        this.strLectura = strLectura;
    }
}
