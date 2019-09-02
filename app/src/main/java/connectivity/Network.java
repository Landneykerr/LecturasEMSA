package connectivity;

import java.util.Observable;

/**
 * Created by Julian Poveda on 26/08/2016.
 */
public class Network extends Observable{

    protected static final int _NET_NOT_FOUND = -1;
    protected static final int _NET_WIFI = 0;
    protected static final int _NET_MOVIL = 1;

    public static final int MIN_STRENGTH_GSM = 10;
    public static final int MIN_STRENGTH_LTE = 8;


    private static Network myNet;

    private int tipoMovil;
    private int intensidadGSM;
    private int intensidadLTE;
    private boolean estadoMovil;
    private boolean estadoWifi;

    private boolean conectado;


    public static Network getInstance()
    {
        if (myNet == null)
        {
            myNet = new Network();
        }
        return myNet;
    }


    private Network(){
        this.estadoMovil = true;
        this.estadoWifi = false;
    }

    public int getTipoMovil() {
        return tipoMovil;
    }

    protected void setTipoMovil(int tipoMovil) {
        this.tipoMovil = tipoMovil;
    }

    public int getIntensidadGSM() {
        return intensidadGSM;
    }

    protected void setIntensidadGSM(int intensidadGSM) {
        this.intensidadGSM = intensidadGSM;
    }

    public int getIntensidadLTE() {
        return intensidadLTE;
    }

    protected void setIntensidadLTE(int intensidadLTE) {
        this.intensidadLTE = intensidadLTE;
    }

    public boolean isEstadoMovil() {
        return estadoMovil;
    }

    protected void setEstadoMovil(boolean estadoMovil) {

        if(estadoMovil != this.estadoMovil)
        {
            this.estadoMovil = estadoMovil;

            setChanged();
            notifyObservers();
        }
    }

    public boolean isEstadoWifi() {
        return estadoWifi;
    }

    protected void setEstadoWifi(boolean estadoWifi) {

        if(estadoWifi != this.estadoWifi)
        {
            this.estadoWifi = estadoWifi;

            setChanged();
            notifyObservers();
        }

    }

    public boolean isConectado() {
        return this.estadoMovil | this.estadoWifi;
    }
}
