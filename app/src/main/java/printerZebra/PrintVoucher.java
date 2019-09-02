package printerZebra;

/**
 * Created by DesarrolloJulian on 11/06/2015.
 * Version: 1.0
 * Novedades:   Se realiza esta tarea asincronna con el fin de poder enviar la impresion del tiquete
 *              sin que esto genere retrasos en el formulario toma lectura y de esta forma poder agilizar
 *              el proceso de toma de lectura.
 */


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import clases.ClassFormatos;
import post_data.UpLoadErrorPrinter;
import clases.ClassConfiguracion;
import dialogos.ShowDialogBox;
import global.global_var;
import sistema.Archivos;
import sistema.DateTime;
import sistema.SQLite;
import vo.VoucherVO;


public class PrintVoucher extends AsyncTask<String, Integer, Boolean> implements global_var { //doInBakGround, Progress, onPostExecute
    /*
    Instancias a clases
     */
    //private FormatosVoucher FcnFormatos;
    private ClassFormatos  fcnFormatos;
    private VoucherVO       MyVoucher;
    private ListenerPrint   listenerPrint;

    /**Instancias a clases**/
    private Archivos            FcnArch;
    private ClassConfiguracion  FcnCfg;
    //private SQLite              FcnSQL;

    /**Variables Locales**/
    private Context ConnectServerContext;

    private ContentValues tempRegistro;

    //Contructor de la clase
    public PrintVoucher(Context context, VoucherVO voucherImpresion, ListenerPrint _listenPrint){
        ConnectServerContext 	= context;
        listenerPrint   = _listenPrint;
        MyVoucher       = voucherImpresion;
        //FcnFormatos     = FormatosVoucher.getInstanceFormatos();
        fcnFormatos     = ClassFormatos.getInstanceFormatos();
        tempRegistro    = new ContentValues();
    }


    //Operaciones antes de realizar la conexion con el servidor
    protected void onPreExecute(){
    }



    //Conexion con el servidor en busca de actualizaciones de trabajo programado
    @Override
    protected Boolean doInBackground(String... params) {
        //this.FcnFormatos.ImpresionVoucher(this.MyVoucher);
        this.fcnFormatos.ActaLectura(this.MyVoucher);
        return false;
    }



    //Operaciones despues de finalizar la conexion con el servidor
    @Override
    protected void onPostExecute(Boolean rta){
        listenerPrint.printFinish(fcnFormatos.isResultPrint(), fcnFormatos.getMensajeError());
    }
}