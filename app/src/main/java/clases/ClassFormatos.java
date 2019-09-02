package clases;

import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.io.IOException;

import global.global_var;
import printerZebra.ClassPrinter;
import printerZebra.DemoSleeper;
import sistema.Bluetooth;
import vo.LecturaVO;
import vo.VoucherVO;


/**
 * Created by JULIANEDUARDO on 13/02/2015.
 */
public class ClassFormatos implements global_var {

    private static ClassFormatos    instanceFormatos;

    private ClassConfiguracion  FcnConfiguracion;
    private Bluetooth           FcnBluetooth;
    private ClassPrinter        FcnPrinter;

    private boolean resultPrint;
    private String  mensajeError;
    private String  bluetooth;


    public static ClassFormatos getInstanceFormatos(){
        if(instanceFormatos == null) {
            instanceFormatos = new ClassFormatos();
        }
        return instanceFormatos;
    }


    private ClassFormatos(){
        this.FcnConfiguracion   = ClassConfiguracion.getInstance();
        this.FcnBluetooth       = Bluetooth.getInstance();
        this.FcnPrinter         = ClassPrinter.getInstance();
        this.bluetooth          = this.FcnConfiguracion.getPrinter();


        this.FcnPrinter.setVerticalPrinter(this.FcnConfiguracion.isVerticalVouvher());

        if(this.FcnConfiguracion.isVerticalVouvher()){
            this.FcnPrinter.SetSizePage(780, 376);
        }else{
            this.FcnPrinter.SetSizePage(580, 376);
        }

        this.FcnPrinter.SetSizeMargin(10, 1, 1, 30);

        this.FcnPrinter.setRegisterFont("INSTRUCT.CPF", "TITULO", 0, 21, 38);
        this.FcnPrinter.setRegisterFont("JACKINPU.CPF", "TEXTO", 0, 18, 18);
        this.FcnPrinter.setRegisterFont("0", "NOTA", 2, 10, 18);
        this.setResultPrint(true);
    }

    public void FormatoPrueba(){
        this.FcnPrinter.resetEtiqueta();
        this.FcnPrinter.DrawImage("EMSA.PCX", 0, 0);
        this.FcnPrinter.WriteDefaultText("TITULO", 0,0,1,"EMSA S.A. E.S.P.");
        this.FcnPrinter.WriteDefaultText("TEXTO",  0,0,1,"Estimado usuario el dia de hoy Day dd de yyyy");
        this.FcnPrinter.WriteDefaultText("TEXTO",  0,0,1,"a las hh:mm:ss estuvimos realizando la ");
        this.FcnPrinter.WriteDefaultText("TEXTO",  0,0,1,"toma de lectura con reporte:");
        this.FcnPrinter.WriteDefaultText("TITULO", 0,1,1,"LECTURA:  AS  :17347.0 ");
        this.FcnPrinter.WriteDefaultText("TEXTO",  0,1,1,"OBS      : 00-INSTALACION NORMAL");
        this.FcnPrinter.WriteDefaultText("TEXTO",  0,0,0,"CUENTA   : 306382388");
        this.FcnPrinter.WriteDefaultText("TEXTO", 400, 0, 1, "MPIO: PUERTO GAITAN");
        this.FcnPrinter.WriteDefaultText("TEXTO", 0, 0, 1, "NOMBRE   : ROSA MARIA AVILA");
        this.FcnPrinter.WriteDefaultText("TEXTO", 0, 0, 1, "DIRECCION: CL 13 05 39 BRR POPULAR");
        this.FcnPrinter.WriteDefaultText("TEXTO", 0, 0, 0, "MEDIDOR  : 110193718 - MET");
        this.FcnPrinter.WriteDefaultText("TEXTO", 500, 0, 1, "INSPECTOR: 1395");
        this.FcnPrinter.WriteDefaultText("NOTA", 0, 3, 1, "PARA MAYOR INFORMACION COMUNIQUESE AL 6828487 O AL 018000-918-615");
    }


    public void ActaLectura(VoucherVO voucherVO){
        try{

            if(this.FcnPrinter.getVerticalPrinter()){
                this.Format2Inch((LecturaVO) voucherVO);
            }else{
                this.Format3Inch((LecturaVO) voucherVO);
            }

            this.FcnPrinter.printLabel(this.bluetooth);

            this.setResultPrint(true);
        }catch (ZebraPrinterLanguageUnknownException ex){
            this.FcnBluetooth.RestartBluetooth();

            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        }catch (ConnectionException ex){
            DemoSleeper.sleep(100);
            this.FcnBluetooth.RestartBluetooth();

            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
            this.FcnPrinter.Zebra_Disconnect();
        }catch (IllegalStateException ex){
            this.FcnBluetooth.RestartBluetooth();

            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        } finally{

        }
    }

    private void Format2Inch(LecturaVO lecturaVO){
        this.FcnPrinter.resetEtiqueta();
        this.FcnPrinter.DrawMargin();
        String [] anomalia =lecturaVO.getAnomalia().split("-");
         if(anomalia[0].equals("25")||anomalia[0].equals("15")){
             this.FcnPrinter.DrawImage("EMSA_90 .PCX", 10, 130);
             this.FcnPrinter.WriteDefaultText("TITULO",  120,0,1, "EMSA S.A. E.S.P.  CONTRATO 45-4523");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, "APRECIADO USUARIO EL DIA DE HOY "+ lecturaVO.getFecha() +" SIENDO LAS ");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, lecturaVO.getHora() +" VISITAMOS SU PREDIO UBICADO EN LA ");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, lecturaVO.getDireccion().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TITULO",  120,0,1, "CON NUMERO DE CUENTA "+ lecturaVO.getCodigo().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO", 10, 1, 1, "DONDE NO FUE POSIBLE LA TOMA DE LECTURA DEL SERVICIO DE ENERGIA.");
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "SEÑOR(A):" + lecturaVO.getNombre().replace(" ", "_")+", EVITE QUE SE ACOMULE SU");
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "CONSUMO LE RECOMENDAMOS COMUNICARSE ANTES DE LAS 24 HORAS");
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "DE LA FECHA DE TOMA DE LECTURA CON LA LINEA 6680097 O AL ");
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "CORREO ELECTRONICO reportesulectura@gmail.com");
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "PARA COORDINAR NUEVA VISITA A SU PREDIO");
             this.FcnPrinter.DrawImage("SYPELC_90",   600, 80);
         }else{
             this.FcnPrinter.DrawImage("EMSA_90 .PCX", 10, 130);
             this.FcnPrinter.WriteDefaultText("TITULO",  120,0,1, "EMSA S.A. E.S.P.  CONTRATO 45-4523");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, "Estimado usuario el dia de hoy "+ lecturaVO.getFecha() +" a las ");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, lecturaVO.getHora() +" estuvimos realizando la toma de ");
             this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,2, "lectura con reporte:");
             this.FcnPrinter.WriteDefaultText("TITULO",  120,0,1, "LECT."+ lecturaVO.getStrLectura().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO", 10, 1, 1, "OBS:" + lecturaVO.getAnomalia().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,0, "CUENTA:" + lecturaVO.getCodigo() + "".replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO",   450,0,1, "MPIO:" + lecturaVO.getMunicipio().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "NOMBRE:" + lecturaVO.getNombre().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "DIRECCION:" + lecturaVO.getDireccion().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 0, "MEDIDOR:" + lecturaVO.getMedidor().replace(" ", "_"));
             this.FcnPrinter.WriteDefaultText("TEXTO",   450,0,1, "INSPECTOR:" + lecturaVO.getInspector() + "".replace(" ","_"));
             this.FcnPrinter.DrawImage("SYPELC_90",   600, 80);
             this.FcnPrinter.WriteDefaultText("NOTA",     10,3,1, "PARA MAYOR INFORMACION COMUNIQUESE AL 6680097");
         }
    }

    private void Format3Inch(LecturaVO lecturaVO){
        this.FcnPrinter.resetEtiqueta();
        this.FcnPrinter.DrawMargin();
        String [] anomalia =lecturaVO.getAnomalia().split("-");
        if(anomalia[0].equals("15")||anomalia[0].equals("25")){
            this.FcnPrinter.DrawImage("EMSA", 10, 130);
            this.FcnPrinter.WriteDefaultText("TITULO",  180,0,1, "EMSA S.A. E.S.P.  CONTRATO 45-4523");
            this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, "APRECIADO USUARIO EL DIA DE HOY "+ lecturaVO.getFecha() +" SIENDO LAS ");
            this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, lecturaVO.getHora() +" VISITAMOS SU PREDIO UBICADO EN LA ");
            this.FcnPrinter.WriteDefaultText("TEXTO",   120,0,1, lecturaVO.getDireccion().replace(" ", "_"));
            this.FcnPrinter.WriteDefaultText("TITULO",  120,0,1, "CON NUMERO DE CUENTA "+ lecturaVO.getCodigo().replace(" ", "_"));
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 1, 1, "DONDE NO FUE POSIBLE LA TOMA DE LECTURA DEL SERVICIO DE ENERGIA.");
            this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "SEÑOR(A):" + lecturaVO.getNombre().replace(" ", "_")+", EVITE QUE SE ACOMULE SU CONSUMO");
            this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "LE RECOMENDAMOS COMUNICARSE CON LA LINEA 6680097");
            this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "O AL CORREO ELECTRONICO reportesulectura@sypelcltda.com");
            this.FcnPrinter.WriteDefaultText("TEXTO",    10,0,1, "PARA COORDINAR NUEVA VISITA A SU PREDIO");
            this.FcnPrinter.DrawImage("SYPELC_90",   400, 80);
        }else {
            this.FcnPrinter.DrawImage("EMSA", 10, 30);
            this.FcnPrinter.WriteDefaultText("TITULO", 180, 0, 1,"EMSA S.A. E.S.P.  CONTRATO 45-4523");
            this.FcnPrinter.WriteDefaultText("TEXTO", 120, 0, 1, "Estimado usuario el dia de hoy " + lecturaVO.getFecha().substring(0, 9));
            this.FcnPrinter.WriteDefaultText("TEXTO", 120, 0, 1, lecturaVO.getFecha().substring(10) + " a las " + lecturaVO.getHora() + " estuvimos realizando");
            this.FcnPrinter.WriteDefaultText("TEXTO", 120, 0, 2, "la toma de lectura con reporte:");
            this.FcnPrinter.WriteDefaultText("TITULO", 120, 0, 1, "LECT. " + lecturaVO.getStrLectura());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 1, 1, "OBS      : " + lecturaVO.getAnomalia());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 1, "CUENTA   : " + lecturaVO.getCodigo());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 1, "MPIO     : " + lecturaVO.getMunicipio());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 1, "NOMBRE   : " + lecturaVO.getNombre());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 1, "DIRECCION: " + lecturaVO.getDireccion());
            this.FcnPrinter.WriteDefaultText("TEXTO", 10, 0, 0, "MEDIDOR  : " + lecturaVO.getMedidor());
            this.FcnPrinter.WriteDefaultText("TEXTO", 370, 0, 1, "INSPECTOR: " + lecturaVO.getInspector());
            this.FcnPrinter.DrawImage("SYPELC_90", 400, 30);
            this.FcnPrinter.WriteDefaultText("NOTA", 10, 1, 1.5, "PARA MAYOR INFORMACION COMUNIQUESE AL 6680097");
        }
    }


    public boolean isResultPrint() {
        return resultPrint;
    }

    private void setResultPrint(boolean resultPrint) {
        this.resultPrint = resultPrint;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    private void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
