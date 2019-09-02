package beacon;

import java.io.File;
import java.util.Observable;

import clases.ClassConfiguracion;
import connectivity.Network;
import post_data.UpLoadErrorPrinter;
import post_data.UploadLecturasJson;
import sistema.SQLite;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;

public class TimerCountDown extends CountDownTimer{
    private Network myNet;
    private ListenerBeacon listenerBeacon;


    public TimerCountDown(long _millisInFuture, long _countDownInterval, ListenerBeacon _listenBeacon) {
        // TODO Auto-generated constructor stub
        super(_millisInFuture, _countDownInterval);
        listenerBeacon = _listenBeacon;
        myNet = Network.getInstance();
    }


    @Override
    public void onTick(long millisUntilFinished) {

        if(myNet.isEstadoMovil())
        {
            if(myNet.getTipoMovil() == TelephonyManager.NETWORK_TYPE_GPRS
                    && myNet.getIntensidadGSM() > Network.MIN_STRENGTH_GSM)
            {
                listenerBeacon.finishBeacon();

            }else if(myNet.getTipoMovil() == TelephonyManager.NETWORK_TYPE_LTE
                    && myNet.getIntensidadLTE() > Network.MIN_STRENGTH_LTE)
            {
                listenerBeacon.finishBeacon();

            }else if(myNet.getTipoMovil() == TelephonyManager.NETWORK_TYPE_HSDPA)
            {
                listenerBeacon.finishBeacon();
            }
        }else if(myNet.isEstadoWifi())
        {
            listenerBeacon.finishBeacon();
        }

        //Llamado a los metodos que se deben ejercutar cada x tiempo

        //Llamado al proceso de envio de fotos en linea

        //Envio al proceso de lecturas en linea
        //new UpLoadErrorPrinter(this.TemporizadorCtx).execute("");
        //new UploadLecturasJson(this.TemporizadorCtx).execute("");

        //this.strServices.sendLecturas();
    }


    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
    }
}
