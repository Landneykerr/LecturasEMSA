package dao;

import android.content.ContentValues;
import android.content.Context;

import global.database_var;
import sistema.DateTime;
import sistema.SQLite;
import vo.LecturaVO;

/**
 * Created by Julian Poveda on 17/05/2016.
 */


public class LecturaDAO implements database_var {
    private static SQLite  fcnSQL;
    private static LecturaDAO ourInstance;



    public static LecturaDAO getInstance(Context _ctx){
        if(ourInstance == null){
            ourInstance = new LecturaDAO();
            fcnSQL = new SQLite(_ctx);
        }

        return ourInstance;
    }



    public LecturaVO getLectura(int _idSerial1, int _idSerial2, int _idSerial3){
        String strLectura = "";
        LecturaVO lectura;

        ContentValues registroBase = fcnSQL.SelectDataRegistro("maestro_clientes",
                "tipo_energia_1, tipo_energia_2, tipo_energia_3, nombre, direccion, id_municipio, marca_medidor, serie_medidor, cuenta",
                "id_serial_1 = "+_idSerial1+" AND id_serial_2 = "+_idSerial2+" AND id_serial_3 = "+_idSerial3);


        ContentValues registroLectura = fcnSQL.SelectDataRegistro("toma_lectura",
                "anomalia, lectura1, lectura2, lectura3, id_inspector, fecha_toma",
                "id_serial1 = "+_idSerial1+" AND id_serial2 = "+_idSerial2+" AND id_serial3 = "+_idSerial3+"  ORDER BY fecha_toma DESC ");



        if(registroLectura.size() > 0)
        {
            if(registroLectura.getAsInteger("lectura1") != -1)
            {
                strLectura += registroBase.getAsString("tipo_energia_1")+":"+registroLectura.getAsString("lectura1")+" ";
            }

            if(registroLectura.getAsInteger("lectura2") != -1)
            {
                strLectura += registroBase.getAsString("tipo_energia_2")+":"+registroLectura.getAsString("lectura2")+" ";
            }

            if(registroLectura.getAsInteger("lectura3") != -1)
            {
                strLectura += registroBase.getAsString("tipo_energia_3")+":"+registroLectura.getAsString("lectura3")+" ";
            }

            lectura = new LecturaVO(registroBase.getAsString("cuenta"), registroBase.getAsString("nombre"),
                    registroBase.getAsString("direccion"),
                    this.fcnSQL.StrSelectShieldWhere("param_municipios","municipio","id_municipio = "+registroBase.getAsString("id_municipio")),
                    registroLectura.getAsInteger("id_inspector"));

            lectura.setStrLectura(strLectura);

            lectura.setAnomalia(registroLectura.getAsString("anomalia") + "-"
                    + fcnSQL.StrSelectShieldWhere("param_anomalias", "descripcion", "id_anomalia = " + registroLectura.getAsString("anomalia")));

            lectura.setFecha(DateTime.getInstance().InvDateWithNameMonthShort(registroLectura.getAsString("fecha_toma").substring(0, 10), "-"));
            lectura.setHora(registroLectura.getAsString("fecha_toma").substring(10));
        }else
        {
            lectura = new LecturaVO(null, null, null, null, -1);
            lectura.setStrLectura(null);
            lectura.setAnomalia(null);
            lectura.setFecha(null);
        }

        lectura.setMedidor(registroBase.getAsString("marca_medidor") + " " + registroBase.getAsString("serie_medidor"));
        return lectura;
    }

    public String getCodigoValidacion(){
        return fcnSQL.StrSelectShieldWhere("param_configuracion","valor","item='Codigo'");
    }
}
