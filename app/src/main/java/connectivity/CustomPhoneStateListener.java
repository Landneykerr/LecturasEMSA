package connectivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;

/**
 * Created by Julian Poveda on 24/08/2016.
 */
public class CustomPhoneStateListener extends PhoneStateListener {

    Context     mContext;
    Network     netMovil = Network.getInstance();

    public static String LOG_TAG = "CustomPhoneStateListener";

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength)
    {
        super.onSignalStrengthsChanged(signalStrength);

        if(signalStrength.isGsm())
        {
            netMovil.setIntensidadGSM(signalStrength.getGsmSignalStrength());
        }

        /*Log.i(LOG_TAG, "onSignalStrengthsChanged: " + signalStrength);
        if (signalStrength.isGsm()) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                    + signalStrength.getGsmBitErrorRate());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                    + signalStrength.getGsmSignalStrength());
        } else if (signalStrength.getCdmaDbm() > 0) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaDbm "
                    + signalStrength.getCdmaDbm());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaEcio "
                    + signalStrength.getCdmaEcio());
        } else {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoDbm "
                    + signalStrength.getEvdoDbm());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoEcio "
                    + signalStrength.getEvdoEcio());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoSnr "
                    + signalStrength.getEvdoSnr());
        }*/

        // Reflection code starts from here
        try {
            Method[] methods = android.telephony.SignalStrength.class
                    .getMethods();
            for (Method mthd : methods) {
                if (mthd.getName().equals("getLteSignalStrength"))
                {
                    netMovil.setIntensidadLTE(Integer.parseInt(mthd.invoke(signalStrength)+""));

                    /*Log.i(LOG_TAG,
                            "onSignalStrengthsChanged: " + mthd.getName() + " "
                                    + mthd.invoke(signalStrength));*/
                }


                /*if (mthd.getName().equals("getLteSignalStrength")
                        || mthd.getName().equals("getLteRsrp")
                        || mthd.getName().equals("getLteRsrq")
                        || mthd.getName().equals("getLteRssnr")
                        || mthd.getName().equals("getLteCqi")) {
                    Log.i(LOG_TAG,
                            "onSignalStrengthsChanged: " + mthd.getName() + " "
                                    + mthd.invoke(signalStrength));
                }*/
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // Reflection code ends here
    }


    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);

        //statusDataConnection = state;

        netMovil.setEstadoMovil(state == TelephonyManager.DATA_CONNECTED);

        /*switch (state) {

            case TelephonyManager.DATA_DISCONNECTED:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_DISCONNECTED");
                break;
            case TelephonyManager.DATA_CONNECTING:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_CONNECTING");
                break;
            case TelephonyManager.DATA_CONNECTED:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_CONNECTED");
                break;
            case TelephonyManager.DATA_SUSPENDED:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_SUSPENDED");
                break;
            default:
                Log.w(LOG_TAG, "onDataConnectionStateChanged: UNKNOWN " + state);
                break;
        }*/

        netMovil.setTipoMovil(networkType);

        /*switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_CDMA:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_CDMA");
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EDGE");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EVDO_0");
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_GPRS");
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSDPA");
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSPA");
                break;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_IDEN");
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_LTE");
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UMTS");
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UNKNOWN");
                break;
            default:
                Log.w(LOG_TAG, "onDataConnectionStateChanged: Undefined Network: "
                        + networkType);
                break;
        }*/
        //checkStatusNet();
        //Toast.makeText(mContext, "Conexion de Datos ha cambiado", Toast.LENGTH_SHORT).show();
    }


    /*private void getEstadoWifi(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork)
        {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                netMovil.setEstadoWifi(activeNetwork.isConnected());

            }else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                netMovil.setEstadoWifi(false);

            }else
            {
                netMovil.setEstadoWifi(false);

            }
        }
    }*/


    /*public ObsNetwork getObsNetwork()
    {
        return obsNetwork;
    }*/



    /*private void checkStatusNet()
    {
        //getEstadoWifi(mContext);

        //if(!netMovil.isEstadoWifi())
        //{
            //if(netMovil.getEstadoMovil() != TelephonyManager.DATA_CONNECTED)
            //{
                obsNetwork.notificarDesconexion();
            //}
        //}
    }*/


    /*public class ObsNetwork extends Observable {

        private void notificarDesconexion(){
            setChanged();
            notifyObservers();
        }
    }*/


}