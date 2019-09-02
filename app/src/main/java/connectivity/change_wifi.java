package connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import connectivity.Network;

public class change_wifi extends BroadcastReceiver {
    Network netMovil = Network.getInstance();

    public change_wifi() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        //WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

        //netMovil.setEstadoWifi(wm);

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getActiveNetworkInfo();

        if(mWifi == null){
            netMovil.setEstadoWifi(false);
        }else{
            netMovil.setEstadoWifi(mWifi.isConnected());
        }


        //Toast.makeText(context, "Conexion Wifi ha cambiado", Toast.LENGTH_SHORT).show();
    }
}
