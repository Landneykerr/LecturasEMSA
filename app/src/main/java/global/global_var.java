package global;

import android.os.Environment;

import java.io.File;

/**
 * Created by julian on 10/07/15.
 */
public interface global_var {
    final int DIALOG_WARNING        = 1;
    final int DIALOG_ERROR          = 2;
    final int DIALOG_INFORMATIVE    = 3;


    final int _GET_HOUR             = 1;
    final int _GET_DATE             = 2;

    final String _ERROR_DATE        ="Bad_1";
    final String _ERROR_TIME        ="Bad_2";


    /**
     * Constantes para el manejo de las fotos
     */
    final int   _SIZZE_BUFFER       = 5;
    final int   _WIDTH_PICTURE      = 620;
    final int   _HEIGHT_PICTURE     = 430;


    /**
     * Constantes para el manejo del GPS
     */
    final int _GPS_INTERVAL_SECONDS = 5000;
    final int _GPS_INTERVAL_METERS  = 3;


    //public boolean setEstado(boolean _estado);
    public static String path_files_app     = Environment.getExternalStorageDirectory() + File.separator + "TomaLecturas";
    public static String sub_path_pictures  = Environment.getExternalStorageDirectory() + File.separator + "TomaLecturas" +File.separator+ "Fotos";

}
