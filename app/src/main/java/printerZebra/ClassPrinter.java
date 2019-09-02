package printerZebra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import grupodesarrollo.insitu.FormInicioSession;

/**
 * Created by JULIANEDUARDO on 12/02/2015.
 */
public class ClassPrinter {
    private static ClassPrinter instancePrinter;

    //private Bluetooth       FcnBluetooth;
    private Connection      printerConnection;
    private static ZebraPrinter    printer;

    //private boolean         statusPrinter;


    private ArrayList<ClassFonts>   lstFonts    = new ArrayList<ClassFonts>();
    private ClassFonts  font;
    //private ClassFonts  font2;
    private boolean     verticalPrinter;
    private boolean     copyInformation;

    private int         sizePageX;
    private int         sizePageY;

    private int         marginTop;
    private int         marginLeft;
    private int         marginRight;
    private int         marginBotton;

    private int         sizeLabelX;
    private int         sizeLabelY;

    private int         currentPointX;
    private int         currentPointY;

    private String      strInformation;
    private String      strFile;


    public static ClassPrinter getInstance() {
        if(instancePrinter == null){
            instancePrinter = new ClassPrinter();
        }
        return instancePrinter;
    }


    private ClassPrinter(){
        //this.FcnBluetooth       = Bluetooth.getInstance();
        this.strInformation     = "";
        this.strFile            = "";
        //this.statusPrinter      = false;
    }


    public void SetSizePage(int _sizeX, int _sizeY){
        this.sizePageX  = _sizeX;
        this.sizePageY  = _sizeY;
    }


    public void SetSizeMargin(int _marginTop, int _marginLeft, int _marginRight, int _marginBotton){
        this.marginTop      = _marginTop;
        this.marginLeft     = _marginLeft;
        this.marginRight    = _marginRight;
        this.marginBotton   = _marginBotton;

        this.sizeLabelX     = this.sizePageX - this.marginLeft - this.marginRight;
        this.sizeLabelY     = this.sizePageY - this.marginTop - this.marginBotton;

        if(this.verticalPrinter){
            this.currentPointY  = this.marginLeft;
            this.currentPointX  = this.sizePageY-this.marginTop;
        }else{
            this.currentPointY  = this.marginTop;
            this.currentPointX  = this.marginLeft;
        }
    }


    public void setVerticalPrinter(boolean _orientation){
        this.verticalPrinter = _orientation;
    }


    public boolean getVerticalPrinter(){
        return this.verticalPrinter;
    }


    public void setRegisterFont(String _nameFont, String _descriptionFont, int _sizeFont, int _widthFont, int _heightFont){
        this.font   = new ClassFonts();
        this.font.setName_font(_nameFont);
        this.font.setDescription_font(_descriptionFont);
        this.font.setWidth_font(_widthFont);
        this.font.setHeight_font(_heightFont);
        this.font.setSize_font(_sizeFont);
        this.lstFonts.add(this.font);
    }


    public void WriteDefaultText(String _typeText, int _posX, double _preIncremento, double _posIncremento, String _text){
        this.font = this.getDataFont(_typeText);
        if(this.verticalPrinter){
            this.currentPointX -= _preIncremento*this.font.getHeight_font();
            this.strInformation += "TEXT270 "+this.font.getName_font()+" "+this.font.getSize_font()+ " "+(this.currentPointX)+" "+(this.currentPointY+_posX)+" "+_text+"\r\n";
            this.currentPointX -= _posIncremento*this.font.getHeight_font();
        }else{
            this.currentPointY += _preIncremento*this.font.getHeight_font();
            this.strInformation += "TEXT "+this.font.getName_font()+" "+this.font.getSize_font()+ " "+(this.currentPointX+_posX)+" "+(this.currentPointY)+" "+_text+"\r\n";
            this.currentPointY += _posIncremento*this.font.getHeight_font();
        }
    }


    public void WriteText(String _typeText, int _posX, double _preIncremento, double _posIncremento, String _text, String _justificacion){

    }


    public void DrawMargin(){
        if(this.verticalPrinter){
            this.WrRectangle(this.marginTop, this.marginLeft,this.sizePageY-this.marginTop,this.sizePageX-this.marginRight,0);
        }else{
            this.WrRectangle(this.marginLeft, this.marginTop,this.sizePageX-this.marginTop,this.sizePageY-this.marginRight,0);
        }

    }


    public void DrawImage(String NameFile, double _posX, double _posY){
        if(this.verticalPrinter){
            NameFile = NameFile+"_90";
        }else{
            NameFile = NameFile+"_0";
        }

        if(NameFile.length()>8){
            NameFile = NameFile.substring(0,8);
        }

        if(this.verticalPrinter) {
            this.strInformation += "PCX " + (this.currentPointX - _posY) + " " + (this.currentPointY + _posX) + " !<" + NameFile+".PCX \r\n";
        }else{
            this.strInformation += "PCX " + (this.currentPointX + _posX) + " " + (this.currentPointY + _posY) + " !<" + NameFile+ ".PCX \r\n";
        }
    }


    public void WrRectangle(double _posX1, double _posY1, double _posX2, double _posY2, int Shadow){
        double IncLine = 0;
        this.strInformation += "BOX " + _posX1 + " " + _posY1 + " " + _posX2 + " " + _posY2 + " 2 \r\n";

        if (Shadow != 0){
            IncLine = (_posX2 - _posX1) / Shadow;
            for (int i = 0; i<Shadow;i++){
                this.strInformation += "BOX " + (_posX1 + (IncLine * i)) + " " + _posY1 + " " + (_posX1 + (IncLine * i)) + " " + _posY2 + " 0 \r\n";
            }
            for(int i = 0;i<Shadow;i++){
                this.strInformation += "BOX " + _posX1 + " " + (_posY1 + (IncLine * i)) + " " + _posX2 + " " + (_posY1 + (IncLine * i)) + " 0 \r\n";
            }
        }
    }


    public ClassFonts getDataFont(String _descripcionFont){
        ClassFonts localFont = new ClassFonts();
        for(int i=0;i<this.lstFonts.size();i++){
            if(this.lstFonts.get(i).getDescription_font().equals(_descripcionFont)){
                localFont   = this.lstFonts.get(i);
            }
        }
        return localFont;
    }


    public String getDoLabel(){
        if(verticalPrinter){
            this.strInformation = "! 0 200 200 " + (this.sizePageX) + " 1" + " \r\n LABEL \r\n" + this.strInformation.replaceAll("\"","");
        }else{
            this.strInformation = "! 0 200 200 " + (this.sizePageY) + " 1" + " \r\n LABEL \r\n" + this.strInformation.replaceAll("\"","");
        }
        this.strInformation += "PRINT \r\n";
        return this.strInformation;
    }


    /**
     *
     * @param _bluetooth MAC del bluetooth con el cual queremos realizar la impresion.
     */
    public void printLabel(String _bluetooth) throws IllegalStateException, ConnectionException, ZebraPrinterLanguageUnknownException {
        if (this.printer == null){
            this.printer = this.Zebra_Connect(_bluetooth);
        }

        if (this.printer != null && this.printer.getConnection().isConnected()) {
            if (this.printer.getCurrentStatus().isReadyToPrint) {
                byte[] configLabel = this.convertExtendedAscii(this.getDoLabel());
                printerConnection.write(configLabel);
                DemoSleeper.sleep(150);
            } else if (this.printer.getCurrentStatus().isHeadOpen) {
                throw new IllegalStateException("ERROR EN IMPRESION. La tapa de la impresora esta abierta.");
            } else if (this.printer.getCurrentStatus().isHeadCold) {
                throw new IllegalStateException("ERROR EN IMPRESION. El cabezal de impresion aun no esta listo.");
            } else if (this.printer.getCurrentStatus().isHeadTooHot) {
                throw new IllegalStateException("ERROR EN IMPRESION. El cabezal de impresion esta sobrecalentado.");
            } else if (this.printer.getCurrentStatus().isPaperOut) {
                throw new IllegalStateException("ERROR EN IMPRESION. Impresora sin papel.");
            } else if (this.printer.getCurrentStatus().isPaused) {
                throw new IllegalStateException("ERROR EN IMPRESION. Impresora pausada.");
            } else if (this.printer.getCurrentStatus().isReceiveBufferFull) {
                throw new IllegalStateException("ERROR EN IMPRESION. Buffer lleno.");
            }
        }
    }

    private ZebraPrinter Zebra_Connect(String _bluetooth) throws ConnectionException, ZebraPrinterLanguageUnknownException{
        this.printerConnection 	= new BluetoothConnection(_bluetooth);
        this.printerConnection.open();
        return ZebraPrinterFactory.getInstance(this.printerConnection);
    }

    public void Zebra_Disconnect() {
        try {
            if (printerConnection != null) {
                printerConnection.close();
                printer = null;
            }
        } catch (ConnectionException e) {
        } finally {
        }
    }

    private byte[] convertExtendedAscii(String input){
        int length = input.length();
        byte[] retVal = new byte[length];
        for(int i=0; i<length; i++){
            char c = input.charAt(i);
            if (c < 127){
                retVal[i] = (byte)c;
            }else{
                retVal[i] = (byte)(c - 256);
            }
        }
        return retVal;
    }

    public void resetEtiqueta(){
        this.strInformation 	= "";
        this.strFile 	        = "";
        if(this.verticalPrinter){
            this.currentPointY  = this.marginLeft;
            this.currentPointX  = this.sizePageY-this.marginTop;
        }else{
            this.currentPointY  = this.marginTop;
            this.currentPointX  = this.marginLeft;
        }
    }

    public ArrayList<ClassFonts> getLstFonts() {
        return lstFonts;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public int getMarginBotton() {
        return marginBotton;
    }

    public int getCurrentPosX() {
        return this.currentPointX;
    }

    public int getCurrentPosY() {
        return this.currentPointY;
    }

    /*public int getFinalLine() {
        return finalLine;
    }*/

    /*public int getSpaceCharacter() {
        return spaceCharacter;
    }*/

    public String getStrInformation() {
        return strInformation;
    }

    public boolean isCopyInformation() {
        return copyInformation;
    }

    public String getStrFile() {
        return strFile;
    }

    public int getSizePageX() {
        return sizePageX;
    }

    public int getSizePageY() {
        return sizePageY;
    }


    /*public boolean isStatusPrinter() {
        return statusPrinter;
    }*/

    /*private void setStatusPrinter(boolean statusPrinter) {
        this.statusPrinter = statusPrinter;
    }*/
}
