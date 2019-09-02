package vo;

/**
 * Created by Julian Poveda on 17/05/2016.
 */
public class VoucherVO {
    private String  codigo;
    private String  nombre;
    private String  direccion;
    private String  municipio;
    private int     inspector;


    public VoucherVO(String codigo, String nombre, String direccion, String municipio, int inspector) {
        this.codigo = codigo;

        if(nombre != null)
        {
            if(nombre.length() > 35)
            {
                this.nombre = nombre.substring(0, 35);
            }else
            {
                this.nombre = nombre;
            }
        }else
        {
            this.nombre = nombre;
        }


        if(direccion != null)
        {
            if(direccion.length()>35)
            {
                this.direccion = direccion.substring(0, 35);
            }else
            {
                this.direccion = direccion;
            }
        }else
        {
            this.direccion = direccion;
        }

        this.municipio = municipio;
        this.inspector = inspector;

    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public int getInspector() {
        return inspector;
    }

}
