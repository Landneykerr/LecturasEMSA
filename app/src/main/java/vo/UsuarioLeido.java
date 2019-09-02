package vo;

import clases.ClassAnomalias;

/**
 * Created by JULIANEDUARDO on 20/02/2015.
 */
public class UsuarioLeido extends UsuarioEMSA {
    private ClassAnomalias  FcnAnomalias;

    private int     newTipoUso;

    private int     lectura1;
    private int     lectura2;
    private int     lectura3;

    private int     oldLectura1;
    private int     oldLectura2;
    private int     oldLectura3;

    private String  critica1;
    private String  critica2;
    private String  critica3;

    private String  msjCritica1;
    private String  msjCritica2;
    private String  msjCritica3;

    private int     anomalia;
    private int     intentos;
    private int     countFotos;

    private String  mensaje;
    private String  strAnomalia;
    private String  descripcionCritica;
    private String  longitudGPS;
    private String  latitudGPS;

    private boolean needLectura;
    private boolean needFotoByAnomalia;
    private boolean needMensaje;
    private boolean leido;
    private boolean haveCritica;
    private boolean confirmLectura;
    private int fotoCiclo;


    //Atributos para la captura de la fecha y hora de impresion utilizado para la reimpresion
    private String  fechaImpresion;
    private String  horaImpresion;
    private int     idLector;


    //Atributos usados para cuando se realiza una busqueda
    private boolean flagSearch;
    public String   backupMunicipio;
    public String   backupRuta;
    public int      backupConsecutivo;


    public UsuarioLeido(){
        super();
        this.FcnAnomalias = ClassAnomalias.getInstance();
    }

    public int getLectura1() {
        return lectura1;
    }

    public void setLectura1(int lectura1) {
        this.lectura1 = lectura1;
    }

    public int getLectura2() {
        return lectura2;
    }

    public void setLectura2(int lectura2) {
        this.lectura2 = lectura2;
    }

    public int getLectura3() {
        return lectura3;
    }

    public void setLectura3(int lectura3) {
        this.lectura3 = lectura3;
    }

    public String getMsjCritica1() {
        return msjCritica1;
    }

    public void setMsjCritica1(String msjCritica1) {
        this.msjCritica1 = msjCritica1;
    }

    public String getMsjCritica2() {
        return msjCritica2;
    }

    public void setMsjCritica2(String msjCritica2) {
        this.msjCritica2 = msjCritica2;
    }

    public String getMsjCritica3() {
        return msjCritica3;
    }

    public void setMsjCritica3(String msjCritica3) {
        this.msjCritica3 = msjCritica3;
    }

    public int getAnomalia() {
        return anomalia;
    }

    public String getStrAnomalia() {
        return strAnomalia;
    }

    public void setAnomalia(int _anomalia, String _strAnomalia) {
        this.anomalia           = _anomalia;
        this.strAnomalia        = _strAnomalia;
        this.needLectura        = this.FcnAnomalias.needLectura(_anomalia);
        this.needFotoByAnomalia = this.FcnAnomalias.needFoto(_anomalia);
        this.needMensaje        = this.FcnAnomalias.needMensaje(_anomalia);
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isNeedLectura() {
        return needLectura;
    }

    public boolean isNeedFotoByAnomalia() {
        return needFotoByAnomalia;
    }

    public boolean isNeedMensaje() {
        return needMensaje;
    }

    public int getCountFotos() {
        return countFotos;
    }

    public void setCountFotos(int countFotos) {
        this.countFotos = countFotos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isHaveCritica() {
        return haveCritica;
    }

    public void setHaveCritica(boolean haveCritica) {
        this.haveCritica = haveCritica;
    }

    public boolean isFlagSearch() {
        return flagSearch;
    }

    public void setFlagSearch(boolean flagSearch) {
        this.flagSearch = flagSearch;
    }

    public String getBackupRuta() {
        return backupRuta;
    }

    public String getBackupMunicipio() {
        return backupMunicipio;
    }

    public void setBackupMunicipio(String backupMunicipio) {
        this.backupMunicipio = backupMunicipio;
    }

    public void setBackupRuta(String backupRuta) {
        this.backupRuta = backupRuta;
    }

    public int getBackupConsecutivo() {
        return backupConsecutivo;
    }

    public void setBackupConsecutivo(int backupConsecutivo) {
        this.backupConsecutivo = backupConsecutivo;
    }

    public int getOldLectura1() {
        return oldLectura1;
    }

    public void setOldLectura1(int oldLectura1) {
        this.oldLectura1 = oldLectura1;
    }

    public int getOldLectura2() {
        return oldLectura2;
    }

    public void setOldLectura2(int oldLectura2) {
        this.oldLectura2 = oldLectura2;
    }

    public int getOldLectura3() {
        return oldLectura3;
    }

    public void setOldLectura3(int oldLectura3) {
        this.oldLectura3 = oldLectura3;
    }

    public boolean isConfirmLectura() {
        return confirmLectura;
    }

    public void setConfirmLectura(boolean confirmLectura) {
        this.confirmLectura = confirmLectura;
    }

    public int getNewTipoUso() {
        return newTipoUso;
    }

    public void setNewTipoUso(int newTipoUso) {
        this.newTipoUso = newTipoUso;
    }

    public String getDescripcionCritica() {
        return descripcionCritica;
    }

    public void setDescripcionCritica(String descripcionCritica) {
        this.descripcionCritica = descripcionCritica;
    }

    public String getLongitudGPS() {
        return longitudGPS;
    }

    public void setLongitudGPS(String longitudGPS) {
        this.longitudGPS = longitudGPS;
    }

    public String getLatitudGPS() {
        return latitudGPS;
    }

    public void setLatitudGPS(String latitudGPS) {
        this.latitudGPS = latitudGPS;
    }

    public String getHoraImpresion() {
        return horaImpresion;
    }

    public void setHoraImpresion(String horaImpresion) {
        this.horaImpresion = horaImpresion;
    }

    public String getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(String fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public int getIdLector() {
        return idLector;
    }

    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }

    public int getFotoCiclo() {
        return fotoCiclo;
    }

    public void setFotoCiclo(int fotoCiclo) {
        this.fotoCiclo = fotoCiclo;
    }

    public String getCritica1() {
        return critica1;
    }

    public void setCritica1(String critica1) {
        this.critica1 = critica1;
    }

    public String getCritica2() {
        return critica2;
    }

    public void setCritica2(String critica2) {
        this.critica2 = critica2;
    }

    public String getCritica3() {
        return critica3;
    }

    public void setCritica3(String critica3) {
        this.critica3 = critica3;
    }
}
