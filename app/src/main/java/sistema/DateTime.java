package sistema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import global.global_var;

/**
 * Created by JULIANEDUARDO on 19/02/2015.
 */

public class DateTime implements global_var {
    protected static String     NameOfMonth[]   = { "Enero",
                                                    "Febrero",
                                                    "Marzo",
                                                    "Abril",
                                                    "Mayo",
                                                    "Junio",
                                                    "Julio",
                                                    "Agosto",
                                                    "Septiembre",
                                                    "Octubre",
                                                    "Noviembre",
                                                    "Diciembre"};

    protected static String     NameOfMonthShort[]   = {"Ene",
                                                        "Feb",
                                                        "Mar",
                                                        "Abr",
                                                        "May",
                                                        "Jun",
                                                        "Jul",
                                                        "Ago",
                                                        "Sept",
                                                        "Oct",
                                                        "Nov",
                                                        "Dic"};
    protected static DateTime   instance        = null;

    public static DateTime getInstance(){
        if(instance == null){
            instance = new DateTime();
        }
        return instance;
    }

    private DateTime(){

    }


    /*public String GetFecha(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df1.format(c.getTime());
        return formattedDate;
    }*/


    public String DateWithNameMonth(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate[] = df1.format(c.getTime()).split("/");
        return NameOfMonth[Integer.parseInt(formattedDate[1])]+" "+formattedDate[0]+"-"+formattedDate[2];
    }

    public String DateWithNameMonthShort() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate[] = df1.format(c.getTime()).split("/");
        return NameOfMonthShort[Integer.parseInt(formattedDate[1])-1]+" "+formattedDate[0]+" de "+formattedDate[2];
    }


    public String InvDateWithNameMonthShort(String date, String split) {
        String formattedDate[] = date.split("\\"+split);
        return NameOfMonthShort[Integer.parseInt(formattedDate[1])-1]+" "+formattedDate[2]+" de "+formattedDate[0];
    }


    public String getDateTimeLocalFoto(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df1.format(c.getTime());
        return formattedDate;
    }


    public String getDateTimeLocal(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = df1.format(c.getTime());
        return formattedDate;
    }


    public String getDateTimeLocal(String _formato){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat(_formato);
        String formattedDate = df1.format(c.getTime());
        return formattedDate;
    }



    public String getDateTimeLocal(int _parameter){
        SimpleDateFormat df1 = null;
        Calendar c = Calendar.getInstance();
        if(_parameter == _GET_HOUR) {
            df1 = new SimpleDateFormat("HH:mm:ss");
        }else{
            df1 = new SimpleDateFormat("dd/MM/yyyy");
        }
        String formattedDate = df1.format(c.getTime());
        return formattedDate;
    }




    public String getDateTimeUTC(){
        String _retorno = null;
        try{
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            f.setTimeZone(TimeZone.getTimeZone("UTC"));
            _retorno = f.format(new Date());
        }catch(Exception ex){

        }
        return _retorno;
    }


    public Date sumarRestarDiasFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }


    public Date sumarRestarHorasFecha(Date fecha, int horas){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }

}