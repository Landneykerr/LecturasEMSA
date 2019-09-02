package clases;

import android.content.ContentValues;
import android.content.Context;

import global.database_var;
import grupodesarrollo.insitu.FormInicioSession;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Observable;

import sistema.SQLite;

/**
 * Created by JULIANEDUARDO on 03/02/2015.
 */
public class ClassFlujoInformacion extends Observable implements database_var {
    private Context             context;
    private String              _campos[];
    private ContentValues       _tempRegistro;
    private ArrayList<ContentValues> _tempTabla;

    private static SQLite FcnSQL;
    private String titulo;
    private String mensaje;
    private String progreso;
    private String total;


    public ClassFlujoInformacion(Context _ctx){
        this.context        = _ctx;
        this._tempRegistro  = new ContentValues();
        this.FcnSQL         = new SQLite(this.context);
        this.mensaje = null;
        this.progreso = "0";
    }


    public void updateInspectores(JSONArray _inspectores) throws JSONException{
        this.setTitulo("CARGANDO INSPECTORES...");
        this.setTotal(_inspectores.length()+"");
        this.FcnSQL.DeleteRegistro("param_usuarios", "perfil<>0");
        for(int i=0;i<_inspectores.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("id_inspector", _inspectores.getJSONObject(i).getString("id_inspector"));
            this._tempRegistro.put("nombre", _inspectores.getJSONObject(i).getString("nombre"));
            this._tempRegistro.put("perfil", "1");
            this._tempRegistro.put("tipo", _inspectores.getJSONObject(i).getString("tipo_inspector"));
            this.FcnSQL.InsertRegistro("param_usuarios", this._tempRegistro);

            this.setMensaje(_inspectores.getJSONObject(i).getString("nombre"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateMunicipios(JSONArray _municipios) throws JSONException{
        this.setTitulo("CARGANDO MUNICIPIOS...");
        this.setTotal(_municipios.length() + "");
        this.FcnSQL.DeleteRegistro("param_municipios","id_municipio IS NOT NULL");

        for(int i=0;i<_municipios.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("id_municipio", _municipios.getJSONObject(i).getString("id_municipio"));
            this._tempRegistro.put("municipio", _municipios.getJSONObject(i).getString("nombre_municipio"));
            this.FcnSQL.InsertRegistro("param_municipios", this._tempRegistro);

            this.setMensaje(_municipios.getJSONObject(i).getString("nombre_municipio"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateAnomalias(JSONArray _anomalias) throws JSONException{
        this.setTitulo("CARGANDO ANOMALIAS...");
        this.setTotal(_anomalias.length() + "");
        this.FcnSQL.DeleteRegistro("param_anomalias","id_anomalia IS NOT NULL");

        for(int i=0;i<_anomalias.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("id_anomalia", _anomalias.getJSONObject(i).getString("id_anomalia"));
            this._tempRegistro.put("descripcion", _anomalias.getJSONObject(i).getString("descripcion"));
            this._tempRegistro.put("aplica_residencial", _anomalias.getJSONObject(i).getString("aplica_residencial"));
            this._tempRegistro.put("aplica_no_residencial", _anomalias.getJSONObject(i).getString("aplica_no_residencial"));
            this._tempRegistro.put("lectura", _anomalias.getJSONObject(i).getString("lectura"));
            this._tempRegistro.put("mensaje", _anomalias.getJSONObject(i).getString("mensaje"));
            this._tempRegistro.put("foto", _anomalias.getJSONObject(i).getString("foto"));
            this.FcnSQL.InsertRegistro("param_anomalias", this._tempRegistro);

            this.setMensaje(_anomalias.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateCriticas(JSONArray _criticas) throws JSONException{
        this.setTitulo("CARGANDO CRITICAS...");
        this.setTotal(_criticas.length() + "");
        this.FcnSQL.DeleteRegistro("param_critica","descripcion IS NOT NULL");

        for(int i=0;i<_criticas.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("prom_minimo", _criticas.getJSONObject(i).getString("rango_minimo"));
            this._tempRegistro.put("prom_maximo", _criticas.getJSONObject(i).getString("rango_maximo"));
            this._tempRegistro.put("mensaje", _criticas.getJSONObject(i).getString("mensaje"));
            this._tempRegistro.put("descripcion", _criticas.getJSONObject(i).getString("descripcion"));
            this._tempRegistro.put("vr_incremento", _criticas.getJSONObject(i).getString("vr_incremento"));
            this._tempRegistro.put("vr_disminucion", _criticas.getJSONObject(i).getString("vr_disminucion"));
            this._tempRegistro.put("consumo", _criticas.getJSONObject(i).getString("consumo"));
            this.FcnSQL.InsertRegistro("param_critica", this._tempRegistro);

            this.setMensaje(_criticas.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateUsos(JSONArray _usos) throws JSONException{
        this.setTitulo("CARGANDO TIPOS DE USO...");
        this.setTotal(_usos.length() + "");
        this.FcnSQL.DeleteRegistro("param_tipos_uso","id_uso IS NOT NULL");

        for(int i=0;i<_usos.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("id_uso", _usos.getJSONObject(i).getString("id_uso"));
            this._tempRegistro.put("descripcion", _usos.getJSONObject(i).getString("descripcion"));
            this.FcnSQL.InsertRegistro("param_tipos_uso", this._tempRegistro);

            this.setMensaje(_usos.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateMensajes(JSONArray _mensajes) throws JSONException{
        this.setTitulo("CARGANDO MENSAJES...");
        this.setTotal(_mensajes.length() + "");
        this.FcnSQL.DeleteRegistro("param_codigos_mensajes","codigo IS NOT NULL");

        for(int i=0;i<_mensajes.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("codigo", _mensajes.getJSONObject(i).getString("codigo"));
            this._tempRegistro.put("descripcion", _mensajes.getJSONObject(i).getString("descripcion"));
            this._tempRegistro.put("macro", _mensajes.getJSONObject(i).getString("macro"));
            this.FcnSQL.InsertRegistro("param_codigos_mensajes", this._tempRegistro);

            this.setMensaje(_mensajes.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i+"");
            setChanged();
            notifyObservers();
        }
    }

    public void updateFiltroMacro(JSONArray _macro) throws JSONException{
        this.setTitulo("CARGANDO FILTROS DE MACRO...");
        this.setTotal(_macro.length() + "");
        this.FcnSQL.DeleteRegistro("param_filtro_macro", "sigla IS NOT NULL");

        for(int i=0;i<_macro.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("sigla", _macro.getJSONObject(i).getString("sigla"));
            this._tempRegistro.put("descripcion", _macro.getJSONObject(i).getString("descripcion"));
            this.FcnSQL.InsertRegistro("param_filtro_macro", this._tempRegistro);

            this.setMensaje(_macro.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i + "");
            setChanged();
            notifyObservers();
        }
    }


    public void updateFiltroCIIU(JSONArray _ciiu) throws JSONException{
        this.setTitulo("CARGANDO TIPOS DE USO...");
        this.setTotal(_ciiu.length() + "");
        this.FcnSQL.DeleteRegistro("param_filtro_ciiu", "sigla IS NOT NULL");

        for(int i=0;i<_ciiu.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("sigla", _ciiu.getJSONObject(i).getString("codigo"));
            this._tempRegistro.put("descripcion", _ciiu.getJSONObject(i).getString("descripcion"));
            this.FcnSQL.InsertRegistro("param_filtro_ciiu", this._tempRegistro);

            this.setMensaje(_ciiu.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i + "");
            setChanged();
            notifyObservers();
        }
    }


    public void updateMedidoresNC(JSONArray _medidores) throws JSONException{
        this.setTitulo("CARGANDO MEDIDORES NC...");
        this.setTotal(_medidores.length() + "");
        this.FcnSQL.DeleteRegistro("param_medidores_nc", "marca IS NOT NULL");

        for(int i=0;i<_medidores.length();i++) {
            this._tempRegistro.clear();
            this._tempRegistro.put("marca", _medidores.getJSONObject(i).getString("marca"));
            this._tempRegistro.put("descripcion", _medidores.getJSONObject(i).getString("descripcion"));
            this.FcnSQL.InsertRegistro("param_medidores_nc", this._tempRegistro);

            this.setMensaje(_medidores.getJSONObject(i).getString("descripcion"));
            this.setProgreso(i + "");
            setChanged();
            notifyObservers();
        }
    }

    public void eraseTrabajo(JSONArray _rutas, String _tipo)throws JSONException{
        for(int i=0; i<_rutas.length();i++){
            this._tempTabla = this.FcnSQL.SelectData("maestro_clientes",
                    "id_serial_1,id_serial_2,id_serial_3",
                    "id_programacion="+_rutas.getJSONObject(i).get("id_programacion")+" AND tipo = '"+_tipo+"'");

            for(int j=0; j<this._tempTabla.size();j++){
                this.FcnSQL.DeleteRegistro("toma_lectura",
                        "id_serial1=" + this._tempTabla.get(j).getAsInteger("id_serial_1") + " AND id_serial2="+this._tempTabla.get(j).getAsInteger("id_serial_2")+" AND id_serial3="+this._tempTabla.get(j).getAsInteger("id_serial_3"));
            }
            this.FcnSQL.DeleteRegistro("maestro_clientes","id_programacion="+_rutas.getJSONObject(i).get("id_programacion")+" AND tipo = '"+_tipo+"'");
            this.FcnSQL.DeleteRegistro("maestro_rutas","id_programacion="+_rutas.getJSONObject(i).get("id_programacion")+" AND tipo = '"+_tipo+"'");
        }
    }

    public void loadTrabajo(JSONArray _rutas)throws JSONException{
        for(int i=0;i<_rutas.length();i++) {
            this.setTitulo("RUTA " + _rutas.getJSONObject(i).getString("id_ciclo") + "-" + _rutas.getJSONObject(i).getString("id_municipio") + "-" + _rutas.getJSONObject(i).getString("ruta")+ "-" + _rutas.getJSONObject(i).getString("tipo")+" ("+(i+1)+" DE "+_rutas.length()+")");
            this._tempRegistro.clear();
            this._tempRegistro.put("id_programacion", _rutas.getJSONObject(i).getString("id_programacion"));
            this._tempRegistro.put("id_inspector", _rutas.getJSONObject(i).getString("id_inspector"));
            this._tempRegistro.put("id_ciclo", _rutas.getJSONObject(i).getString("id_ciclo"));
            this._tempRegistro.put("id_municipio", _rutas.getJSONObject(i).getString("id_municipio"));
            this._tempRegistro.put("mes", _rutas.getJSONObject(i).getString("mes"));
            this._tempRegistro.put("anno", _rutas.getJSONObject(i).getString("anno"));
            this._tempRegistro.put("ruta", _rutas.getJSONObject(i).getString("ruta"));
            this._tempRegistro.put("tipo", _rutas.getJSONObject(i).getString("tipo"));
            this._tempRegistro.put("foto", _rutas.getJSONObject(i).getString("foto"));
            this._tempRegistro.put("voucher", _rutas.getJSONObject(i).getString("voucher"));
            this._tempRegistro.put("fotociclo", _rutas.getJSONObject(i).getString("fotociclo"));
            this.FcnSQL.InsertRegistro("maestro_rutas", this._tempRegistro);


            JSONArray _cuentas = _rutas.getJSONObject(i).getJSONArray("cuentas");
            this.setTotal(_cuentas.length() + "");
            for(int j=0; j<_cuentas.length();j++){
                this._tempRegistro.clear();
                this._tempRegistro.put("id_programacion", _rutas.getJSONObject(i).getString("id_programacion"));
                this._tempRegistro.put("tipo", _rutas.getJSONObject(i).getString("tipo"));
                this._tempRegistro.put("foto", _rutas.getJSONObject(i).getString("foto"));
                this._tempRegistro.put("voucher", _rutas.getJSONObject(i).getString("voucher"));
                this._tempRegistro.put("id_secuencia", _cuentas.getJSONObject(j).getString("id"));
                this._tempRegistro.put("id_ciclo", _cuentas.getJSONObject(j).getString("id_ciclo"));
                this._tempRegistro.put("mes", _cuentas.getJSONObject(j).getString("mes"));
                this._tempRegistro.put("anno", _cuentas.getJSONObject(j).getString("anno"));
                this._tempRegistro.put("ruta", _cuentas.getJSONObject(j).getString("ruta"));
                this._tempRegistro.put("cuenta", _cuentas.getJSONObject(j).getString("cuenta"));
                this._tempRegistro.put("marca_medidor", _cuentas.getJSONObject(j).getString("medidor"));
                this._tempRegistro.put("serie_medidor", _cuentas.getJSONObject(j).getString("serie"));
                this._tempRegistro.put("digitos", _cuentas.getJSONObject(j).getString("digitos"));
                this._tempRegistro.put("nombre", _cuentas.getJSONObject(j).getString("nombre"));
                this._tempRegistro.put("direccion", _cuentas.getJSONObject(j).getString("direccion"));
                this._tempRegistro.put("factor_multiplicacion", _cuentas.getJSONObject(j).getString("factor"));
                this._tempRegistro.put("tipo_uso", _cuentas.getJSONObject(j).getString("tipo_uso"));
                this._tempRegistro.put("id_municipio", _cuentas.getJSONObject(j).getString("id_municipio"));
                this._tempRegistro.put("id_serial_1", _cuentas.getJSONObject(j).getString("id_serial_1"));
                this._tempRegistro.put("lectura_anterior_1", _cuentas.getJSONObject(j).getString("lectura_1"));
                this._tempRegistro.put("anomalia_anterior_1", _cuentas.getJSONObject(j).getString("anomalia_1"));
                this._tempRegistro.put("tipo_energia_1", _cuentas.getJSONObject(j).getString("tipo_energia_1"));
                this._tempRegistro.put("promedio_1", _cuentas.getJSONObject(j).getString("promedio_1"));
                this._tempRegistro.put("id_serial_2", _cuentas.getJSONObject(j).getString("id_serial_2"));
                this._tempRegistro.put("lectura_anterior_2", _cuentas.getJSONObject(j).getString("lectura_2"));
                this._tempRegistro.put("anomalia_anterior_2", _cuentas.getJSONObject(j).getString("anomalia_2"));
                this._tempRegistro.put("tipo_energia_2", _cuentas.getJSONObject(j).getString("tipo_energia_2"));
                this._tempRegistro.put("promedio_2", _cuentas.getJSONObject(j).getString("promedio_2"));
                this._tempRegistro.put("id_serial_3", _cuentas.getJSONObject(j).getString("id_serial_3"));
                this._tempRegistro.put("lectura_anterior_3", _cuentas.getJSONObject(j).getString("lectura_3"));
                this._tempRegistro.put("anomalia_anterior_3", _cuentas.getJSONObject(j).getString("anomalia_3"));
                this._tempRegistro.put("tipo_energia_3", _cuentas.getJSONObject(j).getString("tipo_energia_3"));
                this._tempRegistro.put("promedio_3", _cuentas.getJSONObject(j).getString("promedio_3"));
                this._tempRegistro.put("estado", _cuentas.getJSONObject(j).getString("estado_lectura"));
                this.FcnSQL.InsertRegistro("maestro_clientes", this._tempRegistro);

                this.setMensaje(_cuentas.getJSONObject(j).getString("cuenta")+" "+_cuentas.getJSONObject(j).getString("nombre"));
                this.setProgreso(j+"");
                setChanged();
                notifyObservers();
            }

        }
    }

    public String getRutasCargadasByTipo(String _tipo){
        String _retorno = "-1,";
        this._tempTabla = this.FcnSQL.SelectData("maestro_rutas","id_programacion" ,"tipo = '" + _tipo + "'");
        for(int i=0; i<this._tempTabla.size();i++){
            _retorno += this._tempTabla.get(i).getAsInteger("id_programacion")+",";
        }
        if(!_retorno.isEmpty()){
            _retorno = _retorno.substring(0,_retorno.length()-1);
        }
        return _retorno;
    }

    public void deleteRegistroFoto(String _nombreFoto){
        String foto = this.FcnSQL.StrSelectShieldWhere("registro_fotos","foto","nombre_foto='"+_nombreFoto+"'");
        if(foto==null){
            this.FcnSQL.DeleteRegistro("registro_fotos","nombre_foto='"+_nombreFoto+"'");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    private void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    private void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getProgreso() {
        return progreso;
    }

    private void setProgreso(String progreso) {
        this.progreso = progreso;
    }

    public String getTotal() {
        return total;
    }

    private void setTotal(String total) {
        this.total = total;
    }
}
