package printerZebra;

import android.util.Log;

import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.util.ArrayList;

import clases.ClassConfiguracion;
import conexion.ConexionImpresora;
import figuras.Figuras;
import global.global_var;
import recursos.Cursor;
import recursos.Dato;
import recursos.Fuente;
import recursos.Justificacion;
import sistema.Bluetooth;
import texto.Pagina;
import texto.Texto;
import vo.FacturaVO;
import vo.LecturaVO;
import vo.VoucherVO;


/**
 * Created by JULIANEDUARDO on 13/02/2015.
 */
public class FormatosVoucher implements global_var {

    private static int _ALTO_PARRAFO = 25;

    private static FormatosVoucher instanceFormatos;

    private Figuras figura;
    private Pagina  pagina;
    private Texto   imprimir;
    private Fuente  fuenteTitulo;
    private Fuente  fuenteTexto;
    private Fuente  fuenteSubtitulo;

    private ConexionImpresora   conexion;


    private ClassConfiguracion  FcnConfiguracion;
    private Bluetooth           FcnBluetooth;


    private boolean resultPrint;
    private String  mensajeError;
    private String  bluetooth;


    public static FormatosVoucher getInstanceFormatos(){
        if(instanceFormatos == null) {
            instanceFormatos = new FormatosVoucher();
        }
        return instanceFormatos;
    }


    private FormatosVoucher(){
        this.FcnConfiguracion   = ClassConfiguracion.getInstance();
        this.FcnBluetooth       = Bluetooth.getInstance();
        this.bluetooth          = this.FcnConfiguracion.getPrinter();
        this.setResultPrint(true);


        try{
            this.conexion       = ConexionImpresora.getInstance(this.bluetooth);
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (ZebraPrinterLanguageUnknownException e) {
            e.printStackTrace();

        }

        this.fuenteTitulo   = new Fuente("INSTRUCT.CPF", 5.3, 12);
        this.fuenteSubtitulo = new Fuente("LIBERATI.CPF", 4.9, 8);
        this.fuenteTexto    = new Fuente("JACKINPU.CPF", 4.4, 5);

        this.pagina = Pagina.getInstance();
        this.figura = new Figuras();

    }



    public void FormatoPrueba(){
        try
        {

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void ImpresionVoucher(VoucherVO voucher){
        try{
            if(voucher == null){
                this.FormatoPrueba();

            }else if(voucher instanceof FacturaVO)
            {
                this.Format4Inch((FacturaVO) voucher);

            }else if(voucher instanceof LecturaVO)
            {
                if(FcnConfiguracion.isVerticalVouvher())
                {
                    this.Format3Inch((LecturaVO) voucher);

                }else
                {
                    this.Format2Inch((LecturaVO) voucher);

                }
            }

            this.conexion.enviarImpresion(this.pagina.getDoLabel());
            this.setResultPrint(true);

        }catch (ZebraPrinterLanguageUnknownException ex)
        {
            //this.FcnBluetooth.RestartBluetooth();
            this.conexion.reconectarImpresora();

            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        } catch (ConnectionException ex)
        {
            this.FcnBluetooth.RestartBluetooth();
            this.conexion.reconectarImpresora();
            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        }catch (IllegalStateException ex)
        {
            this.FcnBluetooth.RestartBluetooth();
            this.conexion.reconectarImpresora();
            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        }catch(Exception ex)
        {
            Log.i("Error", ex.toString());
            this.conexion.reconectarImpresora();
            this.setResultPrint(false);
            this.setMensajeError(ex.getMessage());
        }finally{

        }
    }


    private void Format2Inch(LecturaVO lectura){
        try
        {
            pagina.configurarEtiqueta(4, 203, Pagina.NORMAL, 200);
            pagina.configurarMargenes(10, 30, 10, 50);
            pagina.mostrarMargen();

            this.imprimir = new Texto( 5, 2);

            pagina.getCursor().incPuntoXY(50, 10);
            figura.Imagen("EMSA.PCX");
            pagina.getCursor().incPuntoXY(130, -5);


            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "Estimado usuario el dia de hoy " + lectura.getFecha() + " a las " + lectura.getHora() +
                    " estuvimos realizando la toma de lectura con resporte:" +lectura.getStrLectura(), true, Texto.BORDE_NINGUNO);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Suscriptor:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, lectura.getNombre(), false, Texto.BORDE_NINGUNO);

            pagina.nuevaLinea();
            pagina.getCursor().incPuntoX(180);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Direccion:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, lectura.getDireccion(), false, Texto.BORDE_NINGUNO);

            pagina.nuevaLinea();
            pagina.getCursor().incPuntoX(180);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Ciudad:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "VILLAVICENCIO", false, Texto.BORDE_NINGUNO);

            pagina.getCursor().incPuntoY(80);
            pagina.nuevaLinea();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void Format3Inch(LecturaVO lectura){
        try
        {
            pagina.configurarEtiqueta(4, 203, Pagina.NORMAL, 420);
            pagina.configurarMargenes(10, 30, 10, 50);
            pagina.mostrarMargen();

            this.imprimir = new Texto( 5, 2);

            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "EMSA E.S.P.", false, Texto.BORDE_NINGUNO);

            pagina.getCursor().incPuntoXY(50, 10);
            figura.Imagen("EMSA.PCX");
            pagina.getCursor().incPuntoXY(150, -5);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Estimado usuario el dia de hoy " + lectura.getFecha() + " a las " + lectura.getHora() +
                    " estuvimos realizando la toma de lectura con reporte:", false, Texto.BORDE_NINGUNO, 70, Cursor.ABAJO);

            pagina.getCursor().incPuntoY(25);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "LECTURA: " + lectura.getStrLectura(), false, Texto.BORDE_NINGUNO, 70, Cursor.NUEVA_LINEA);

            pagina.getCursor().incPuntoY(25);

            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "OBS: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getAnomalia(), false, Texto.BORDE_NINGUNO, 75, Cursor.NUEVA_LINEA, _ALTO_PARRAFO);


            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "CUENTA: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getCodigo(), false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);

            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "MPIO: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getMunicipio(), false, Texto.BORDE_NINGUNO, 25, Cursor.NUEVA_LINEA, _ALTO_PARRAFO);


            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "NOMBRE: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getNombre(), false, Texto.BORDE_NINGUNO, 75, Cursor.NUEVA_LINEA, _ALTO_PARRAFO);


            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "DIRECCION: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getDireccion(), false, Texto.BORDE_NINGUNO, 75, Cursor.NUEVA_LINEA, _ALTO_PARRAFO);


            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "MEDIDOR: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getMedidor(), false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);

            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "INSPECTOR: ", false, Texto.BORDE_NINGUNO, 25, Cursor.DERECHA, _ALTO_PARRAFO);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, lectura.getInspector()+"", false, Texto.BORDE_NINGUNO, 25, Cursor.NUEVA_LINEA, _ALTO_PARRAFO);

            pagina.getCursor().incPuntoY(20);
            imprimir.texto(fuenteTexto, Justificacion.COMPLETO, "Para cualquier informacion comuniquese al 6828487 o al 018000-918-615.", false, Texto.BORDE_NINGUNO);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void Format4Inch(FacturaVO factura){
        try
        {
            pagina.configurarEtiqueta(4, 203, Pagina.NORMAL, 1800);
            pagina.configurarMargenes(10, 30, 10, 50);
            pagina.mostrarMargen();

            this.imprimir = new Texto( 5, 2);


            /************************************************** ENCABEZADO *********************************************************/
            pagina.getCursor().incPuntoXY(50, 10);
            figura.Imagen("EMSA.PCX");
            pagina.getCursor().incPuntoXY(130, -5);

            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "CODIGO DEL CLIENTE "+factura.getCodigo(), true, Texto.BORDE_NINGUNO);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Suscriptor:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, factura.getNombre(), false, Texto.BORDE_NINGUNO);

            pagina.nuevaLinea();
            pagina.getCursor().incPuntoX(180);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Direccion:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, factura.getDireccion(), false, Texto.BORDE_NINGUNO);

            pagina.nuevaLinea();
            pagina.getCursor().incPuntoX(180);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "Ciudad:", false, Texto.BORDE_NINGUNO, 20, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, factura.getMunicipio(), false, Texto.BORDE_NINGUNO);

            pagina.getCursor().incPuntoY(80);
            pagina.nuevaLinea();

            /***********************INFORMACION GRAFICA DE CONSUMO DE ENERGIA**********************/
            ArrayList<String> leyendaY = new ArrayList<>();
            leyendaY.add("0");
            leyendaY.add("100");
            leyendaY.add("200");
            leyendaY.add("300");
            leyendaY.add("400");

            ArrayList<Dato> datos = new ArrayList<>();
            datos.add(new Dato("MAR",306));
            datos.add(new Dato("ABR",324));
            datos.add(new Dato("MAY",356));
            datos.add(new Dato("JUN",229));
            datos.add(new Dato("JUL",259));
            datos.add(new Dato("AGO",315));


            figura.graficoBarras(300, 100, 30, 50, 50, 30, fuenteTitulo, "DETALLE DE CONSUMO", fuenteSubtitulo, leyendaY, datos, 400, true,
                    Cursor.ABAJO);

            /********************************************* INFORMACION TECNICA*************************************************/
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "INFORMACION TECNICA", true, Texto.BORDE_TODOS);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "MARCA CONTADOR", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "NÚMERO(S)", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "FACTOR", false, Texto.BORDE_TODOS, 30, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getMarcaContador1(), false, Texto.BORDE_DERECHO, 35, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getSerieContador1(), false, Texto.BORDE_DERECHO, 35, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getFactorContador1(), false, Texto.BORDE_NINGUNO, 30, Cursor.NUEVA_LINEA);

            /*imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getMarcaContador2(), false, Texto.BORDE_DERECHO, 35, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getSerieContador2(), false, Texto.BORDE_DERECHO, 35, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getFactorContador2(), false, Texto.BORDE_NINGUNO, 30, Cursor.NUEVA_LINEA);*/

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "FACTURACION DE 02-SEP-16", false, Texto.BORDE_TODOS);

            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "ESTRATO ", false, Texto.BORDE_TODOS, 20, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "2-Alto", false, Texto.BORDE_TODOS, 30, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "CLASE ", false, Texto.BORDE_TODOS, 20, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Residencial", false, Texto.BORDE_TODOS, 30, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "CICLO ", false, Texto.BORDE_TODOS, 20, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getCiclo(), false, Texto.BORDE_TODOS, 30, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "SERVICIO ", false, Texto.BORDE_TODOS, 20, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, " ", false, Texto.BORDE_TODOS, 30, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "CÓDIGO CIIU", false, Texto.BORDE_TODOS, 20, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, " ", false, Texto.BORDE_TODOS, 30, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "ESTADO DEL PREDIO", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "ACTIVA", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 30);

            pagina.getCursor().incPuntoY(10);


            /***************************************** INFORMACION FINANCIACION *************************************************/
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "DETALLE DE LA FACTURA", true, Texto.BORDE_TODOS);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Liquidación del consumo de energía:", false, Texto.BORDE_NINGUNO);

            if(factura.getConsumo() > 173)
            {
                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "179.06 (Valor kWh con SUBS) x 173 (Consumo en kWh)", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * 173 * 0.5) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "358.13 (Valor kWh ) x " + (factura.getConsumo() - 173) + " (Consumo en kWh)", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * (factura.getConsumo() - 173)) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                pagina.getCursor().incPuntoY(10);
                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "SUBTOTAL VALOR CONSUMO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * factura.getConsumo()) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Porcentaje de SUBSIDIO (50%)", false, Texto.BORDE_NINGUNO, 100, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Valor porcentaje de SUBSIDIO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * 173 * 0.5) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "SUBTOTAL VALOR CONSUMO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, ((int)(358.13 * 173 * 0.5) + (int)(358.13 * (factura.getConsumo() - 173))) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

            }else{

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "179.06 (Valor kWh con SUBS) x 173 (Consumo en kWh)", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * factura.getConsumo() * 0.5) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                pagina.getCursor().incPuntoY(10);
                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "SUBTOTAL VALOR CONSUMO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * factura.getConsumo()) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Porcentaje de SUBSIDIO (50%)", false, Texto.BORDE_NINGUNO, 100, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Valor porcentaje de SUBSIDIO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * factura.getConsumo() * 0.5) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);

                imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "SUBTOTAL VALOR CONSUMO", false, Texto.BORDE_NINGUNO, 80, Cursor.DERECHA);
                imprimir.texto(fuenteTexto, Justificacion.DERECHA, (int)(358.13 * factura.getConsumo() * 0.5) + "", false, Texto.BORDE_NINGUNO, 20, Cursor.NUEVA_LINEA);
            }

            pagina.getCursor().incPuntoY(10);


            /***************************************** INFORMACION COSTOS *************************************************/
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "COSTO UNITARIO DE LA PRESTACION DEL SERVICIO EMSA ($/kWh)", true, Texto.BORDE_TODOS);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "CONCEPTO", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, " ", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, "CONCEPTO", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA);
            imprimir.texto(fuenteSubtitulo, Justificacion.CENTRADO, " ", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Compra energia al generador  G $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "125.32", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 45);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Perdidas reconocidas  PR $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "24.72", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 45);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Transporte en el sistema general  T $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "20.38", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 45);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Otros costos asociados  O $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "4.13", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 45);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Transporte en el sistema local  D $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "143.12", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 45);
            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Costo de comercializacion  C $", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 45);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "40.45", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 45);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "Total costo unitario de prestacion del servicio (monomio) " +
                    "Nivel(1) CU=G+T+D+Cv+PR+R", false, Texto.BORDE_TODOS, 85, Cursor.DERECHA, 65);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, "358.13", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 65);


            pagina.getCursor().incPuntoY(10);

            /***************************************** INFORMACION FINANCIACION *************************************************/
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "DATOS DE FINANCIACIÓN", true, Texto.BORDE_TODOS);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "VALOR FINANCIACION", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "CUOTA ACTUAL", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "SALDO FINANCIACION", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "CUOTAS PENDIENTES", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "INTERESES %", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "No. DE CUOTAS", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "VALOR FINANCIACION", false, Texto.BORDE_TODOS, 50, Cursor.DERECHA, 30);
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, " ", false, Texto.BORDE_TODOS, 50, Cursor.NUEVA_LINEA, 30);

            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "SALDO A FAVOR", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.IZQUIERDA, "SALDO EN RECLAMACION", false, Texto.BORDE_TODOS, 35, Cursor.DERECHA, 30);
            imprimir.texto(fuenteSubtitulo, Justificacion.DERECHA, " ", false, Texto.BORDE_TODOS, 15, Cursor.NUEVA_LINEA, 30);


            pagina.getCursor().incPuntoY(10);

            /**************************INICIA PARTE DE OTROS CONCEPTOS*****************************/
            //Primer cobro Otros Conceptos
            /*imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescOtrosConceptos1(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorOtrosConceptos1(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getTituloOtrosValores(), false, Texto.BORDE_NINGUNO, 100, Cursor.NUEVA_LINEA);*/
            //Otros conceptos restantes pueden ir desde el 2 hasta el 10


            //TITULO SUBTOTAL OTROS CONCEPTOS
            /*imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getTituloSubOtrosConceptos(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorSubOtrosConceptos(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);*/




            /************INICIA PARTE DE LOS DESCUENTOS, PUEDEN HABER HASTA 5 DESCUENTOS************/
            /*imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getTituloDescuentos(), false, Texto.BORDE_NINGUNO, 100, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescripcionDescuento1(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorDescuento1(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescripcionDescuento2(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorDescuento2(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescripcionDescuento3(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorDescuento3(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescripcionDescuento4(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorDescuento4(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescripcionDescuento5(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorDescuento5(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            pagina.getCursor().incPuntoY(10);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getTituloValorDescuentos(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorTotalDescuentos(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getTituloSubConceptosEnergia(), false, Texto.BORDE_NINGUNO, 85, Cursor.DERECHA);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorConceptosEnergia(), false, Texto.BORDE_NINGUNO, 15, Cursor.NUEVA_LINEA);*/



            /*************************** INICIA DATOS PORTAFOLIO ***********************************/
            /*pagina.getCursor().incPuntoY(10);

            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "DETALLE PORTAFOLIO", true, Texto.BORDE_TODOS);

            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "CONVENIO", false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "CAPITAL", false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "INTERESES", false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "VALOR A PAGAR", false, Texto.BORDE_TODOS, 25, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescConceptoExt1(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorActualConceptoExt1(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getInteresConceptoExt1(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorCuotaConceptoExt1(), false, Texto.BORDE_TODOS, 25, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescConceptoExt2(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorActualConceptoExt2(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getInteresConceptoExt2(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorCuotaConceptoExt2(), false, Texto.BORDE_TODOS, 25, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescConceptoExt3(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorActualConceptoExt3(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getInteresConceptoExt3(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorCuotaConceptoExt3(), false, Texto.BORDE_TODOS, 25, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, factura.getDescConceptoExt4(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorActualConceptoExt4(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getInteresConceptoExt4(), false, Texto.BORDE_TODOS, 25, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, factura.getValorCuotaConceptoExt4(), false, Texto.BORDE_TODOS, 25, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTexto, Justificacion.IZQUIERDA, "SUBTOTAL POR CONCEPTO DE PORTAFOLIO $", false, Texto.BORDE_INFERIOR, 75, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.DERECHA, factura.getValorTotalConceptoExt(), false, Texto.BORDE_INFERIOR, 25, Cursor.NUEVA_LINEA, 25);*/


            /*************************INFORMACION GENERAL DE PAGO DE FACTURA***********************/
            pagina.getCursor().incPuntoY(10);

            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "RESUMEN FACTURA", true, Texto.BORDE_TODOS);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "Pague antes de", false, Texto.BORDE_TODOS, 33, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "Suspensión desde", false, Texto.BORDE_TODOS, 33, Cursor.DERECHA, 25);
            imprimir.texto(fuenteTexto, Justificacion.CENTRADO, "TOTAL A PAGAR", false, Texto.BORDE_TODOS, 34, Cursor.NUEVA_LINEA, 25);

            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "15-SEP-2016", false, Texto.BORDE_TODOS, 33, Cursor.DERECHA);
            imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "16-SEP-2016", false, Texto.BORDE_TODOS, 33, Cursor.DERECHA);

            if(factura.getConsumo() > 173)
            {
                imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, ((int)(358.13 * 173 * 0.5) + (int)(358.13 * (factura.getConsumo() - 173))) + "", false, Texto.BORDE_TODOS, 34, Cursor.NUEVA_LINEA);
            }else{

                imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, (int)(358.13 * factura.getConsumo() * 0.5) + "", false, Texto.BORDE_TODOS, 34, Cursor.NUEVA_LINEA);
            }
            //imprimir.texto(fuenteTitulo, Justificacion.CENTRADO, "85514.00", false, Texto.BORDE_TODOS, 34, Cursor.NUEVA_LINEA);

            /**********************************MENSAJE GENERAL A IMPRIMIR**************************/
            pagina.getCursor().incPuntoY(10);
            imprimir.texto(fuenteSubtitulo, Justificacion.COMPLETO, "Ahora usted puede recibir su factura a través de su correo electrónico: Visite nuestra página WEB www.emsa-esp.com.co", true, Texto.BORDE_TODOS);

            pagina.getCursor().incPuntoY(20);
            figura.codigoBarras(100, "128", 60, "415770999800244980200586130769F139000000070470");
            pagina.getCursor().incPuntoY(60);

            imprimir.texto(fuenteSubtitulo,Justificacion.CENTRADO, "415770999800244980200586130769F139000000070470", false, Texto.BORDE_NINGUNO);

        } catch (Exception e) {
            e.printStackTrace();
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
