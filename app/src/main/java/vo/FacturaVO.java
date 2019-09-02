package vo;

import java.util.ArrayList;

/**
 * Created by Julian Poveda on 11/05/2016.
 */
public class FacturaVO extends VoucherVO{
    //private String  codigo;
    //private String  nombre;
    //private String  direccionEntrega;
    //private String  municipio;

    private String  mes;
    private String  anno;
    private String  cicloCargue;


    private String  periodoFact;
    private String  estrato;
    private String  claseServicio;
    private String  ciclo;
    private String  ubicacion;
    private String  estadoPredio;
    private String  actividadCIU;
    private String  ruta;
    private String  numFactura;
    private String  periodosVencidos;

    //private ArrayList<FactHistConsumo> listHistConsumo;
    private String histDescripcionM1;
    private String histConsumoM1;
    private String histDescripcionM2;
    private String histConsumoM2;
    private String histDescripcionM3;
    private String histConsumoM3;
    private String histDescripcionM4;
    private String histConsumoM4;
    private String histDescripcionM5;
    private String histConsumoM5;
    private String histDescripcionM6;
    private String histConsumoM6;


    //private ArrayList<FactMedida> listMedida;
    private String medida1;
    private String lectAnteriorM1;
    private String lectTomadaM1;
    private String tipoObservacionM1;
    private String tipoConsumoM1;

    private String medida2;
    private String lectAnteriorM2;
    private String lectTomadaM2;
    private String tipoObservacionM2;
    private String tipoConsumoM2;

    private String medida3;
    private String lectAnteriorM3;
    private String lectTomadaM3;
    private String tipoObservacionM3;
    private String tipoConsumoM3;

    private String medida4;
    private String lectAnteriorM4;
    private String lectTomadaM4;
    private String tipoObservacionM4;
    private String tipoConsumoM4;

    private String medida5;
    private String lectAnteriorM5;
    private String lectTomadaM5;
    private String tipoObservacionM5;
    private String tipoConsumoM5;




    private String promedioCuenta;

    //private ArrayList<FactContador> listContador;
    private String marcaContador1;
    private String serieContador1;
    private String factorContador1;
    private String transformadorContador1;

    private String marcaContador2;
    private String serieContador2;
    private String factorContador2;
    private String transformadorContador2;



    private String  nombreNodoGrupo;
    private String  nodo;
    private String  acumuladoFES;
    private String  acumuladoDES;
    private String metaFES;
    private String metaDES;
    private String costoGeneracion;
    private String costoTransmision;
    private String costoDistribucion;
    private String costoPerdidas;
    private String otrosCostos;
    private String costoComercializacion;
    private String costoTotal;
    private String valorFinanciacion;
    private String saldoFinanciacion;
    private String interes;
    private String cuotaActual;
    private String cuotasPendientes;
    private String numeroCuota;
    private String valorCuota;
    private String saldoFavor;
    private String saldoReclamacion;
    private String tituloLiquidacionEnergia;


    //private ArrayList<FactLiquidacionTarifa> listLiquiFactura;
    private String liquidacionTarifa1;
    private String valorTarifa1;
    private String liquidacionTarifa2;
    private String valorTarifa2;
    private String liquidacionTarifa3;
    private String valorTarifa3;
    private String liquidacionTarifa4;
    private String valorTarifa4;
    private String liquidacionTarifa5;
    private String valorTarifa5;
    private String liquidacionTarifa6;
    private String valorTarifa6;


    private String porcentajeSubsidio;
    private String tituloValorSubsidio;
    private String valorSubsidio;
    private String tituloValorFinalConsumo;
    private String valorTotalConsumo;



    //private ArrayList<FactOtrosConceptos> listOtrosConceptos;
    private String descOtrosConceptos1;
    private String valorOtrosConceptos1;

    private String descOtrosConceptos2;
    private String valorOtrosConceptos2;

    private String descOtrosConceptos3;
    private String valorOtrosConceptos3;

    private String descOtrosConceptos4;
    private String valorOtrosConceptos4;

    private String descOtrosConceptos5;
    private String valorOtrosConceptos5;

    private String descOtrosConceptos6;
    private String valorOtrosConceptos6;

    private String descOtrosConceptos7;
    private String valorOtrosConceptos7;

    private String descOtrosConceptos8;
    private String valorOtrosConceptos8;

    private String descOtrosConceptos9;
    private String valorOtrosConceptos9;

    private String descOtrosConceptos10;
    private String valorOtrosConceptos10;



    private String tituloSubOtrosConceptos;
    private String valorSubOtrosConceptos;



    //private ArrayList<FactDescuentos> listDescuentos;
    private String descripcionDescuento1;
    private String valorDescuento1;

    private String descripcionDescuento2;
    private String valorDescuento2;

    private String descripcionDescuento3;
    private String valorDescuento3;

    private String descripcionDescuento4;
    private String valorDescuento4;

    private String descripcionDescuento5;
    private String valorDescuento5;




    private String tituloValorDescuentos;
    private String valorTotalDescuentos;
    private String tituloSubConceptosEnergia;
    private String valorConceptosEnergia;
    private String fechaPagoNormal;
    private String fechaCorte;
    private String valorTotalFactura;
    private String nombreGerente;
    private String mensaje;
    private String codigoBarras;


    //private ArrayList<FactConceptoExt> lisConceptoExterno;
    private String descConceptoExt1;
    private String valorActualConceptoExt1;
    private String interesConceptoExt1;
    private String valorCuotaConceptoExt1;

    private String descConceptoExt2;
    private String valorActualConceptoExt2;
    private String interesConceptoExt2;
    private String valorCuotaConceptoExt2;

    private String descConceptoExt3;
    private String valorActualConceptoExt3;
    private String interesConceptoExt3;
    private String valorCuotaConceptoExt3;

    private String descConceptoExt4;
    private String valorActualConceptoExt4;
    private String interesConceptoExt4;
    private String valorCuotaConceptoExt4;


    private String valorTotalConceptoExt;
    private String nivelTension;
    private String secuencia;
    private String tituloValorNeto;
    private String valorNeto;
    private String tituloOtrosValores;
    private String tituloDescuentos;
    private String fechaEmision;
    private String ultimoPago;
    private String periodoFacturado;
    private String valorMinConsumo;
    private String valorMaxConsumo;
    private String valorFoes;
    private String consumoKWHFoes;
    private String valorUnitarioFoes;
    private String facturaFoes;


    //CAMPOS CORRESPONDIENTES A LA FACTURA DE BIOAGRICOLA
    private String facturaCobroBio;
    private String codigoBio;
    private String ultFactura1Bio;
    private String ultFactura2Bio;
    private String ultFactura3Bio;
    private String ultFactura4Bio;
    private String ultFactura5Bio;
    private String ultFactura6Bio;
    private String tipoUsoBio;
    private String tasaMoraBio;
    private String factVencidasBio;
    private String estratoBio;
    private String categoriaBio;
    private String mtsCubicosBio;
    private String frBarrioBio;
    private String frRecoleccionBio;
    private String porcSubsidioBio;
    private String porcContribucionBio;

    //private ArrayList<FactConceptoBio> listConceptoBio;
    private String concepto1Bio;
    private String valor1Bio;

    private String concepto2Bio;
    private String valor2Bio;

    private String concepto3Bio;
    private String valor3Bio;

    private String concepto4Bio;
    private String valor4Bio;

    private String concepto5Bio;
    private String valor5Bio;

    private String concepto6Bio;
    private String valor6Bio;

    private String concepto7Bio;
    private String valor7Bio;

    private String concepto8Bio;
    private String valor8Bio;

    private String concepto9Bio;
    private String valor9Bio;

    private String concepto10Bio;
    private String valor10Bio;

    private String valorTotalBio;
    private String conceptoFinanciadoBio;
    private String numCuotasBio;
    private String valorCuotaBio;
    private String descripcionBio;
    private String numCuotaMesConcepto1Bio;
    private String numCuotaMesConcepto2Bio;
    private String numCuotaMesConcepto3Bio;
    private String numCuotaMesConcepto4Bio;



    /**Variables para el calculo del consumo de energia y valor a cancelar**/
    private int consumo;




    public FacturaVO(String codigo, String nombre, String direccion, String municipio, int inspector) {
        super(codigo, nombre, direccion, municipio, inspector);
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getCicloCargue() {
        return cicloCargue;
    }

    public void setCicloCargue(String cicloCargue) {
        this.cicloCargue = cicloCargue;
    }

    /*public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }*/

    /*public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }*/

    public String getPeriodoFact() {
        return periodoFact;
    }

    public void setPeriodoFact(String periodoFact) {
        this.periodoFact = periodoFact;
    }

    /*public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }*/

    /*public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }*/

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getClaseServicio() {
        return claseServicio;
    }

    public void setClaseServicio(String claseServicio) {
        this.claseServicio = claseServicio;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstadoPredio() {
        return estadoPredio;
    }

    public void setEstadoPredio(String estadoPredio) {
        this.estadoPredio = estadoPredio;
    }

    public String getActividadCIU() {
        return actividadCIU;
    }

    public void setActividadCIU(String actividadCIU) {
        this.actividadCIU = actividadCIU;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getPeriodosVencidos() {
        return periodosVencidos;
    }

    public void setPeriodosVencidos(String periodosVencidos) {
        this.periodosVencidos = periodosVencidos;
    }

    public String getHistConsumoM6() {
        return histConsumoM6;
    }

    public void setHistConsumoM6(String histConsumoM6) {
        this.histConsumoM6 = histConsumoM6;
    }

    public String getHistDescripcionM6() {
        return histDescripcionM6;
    }

    public void setHistDescripcionM6(String histDescripcionM6) {
        this.histDescripcionM6 = histDescripcionM6;
    }

    public String getHistConsumoM5() {
        return histConsumoM5;
    }

    public void setHistConsumoM5(String histConsumoM5) {
        this.histConsumoM5 = histConsumoM5;
    }

    public String getHistDescripcionM5() {
        return histDescripcionM5;
    }

    public void setHistDescripcionM5(String histDescripcionM5) {
        this.histDescripcionM5 = histDescripcionM5;
    }

    public String getHistConsumoM4() {
        return histConsumoM4;
    }

    public void setHistConsumoM4(String histConsumoM4) {
        this.histConsumoM4 = histConsumoM4;
    }

    public String getHistDescripcionM4() {
        return histDescripcionM4;
    }

    public void setHistDescripcionM4(String histDescripcionM4) {
        this.histDescripcionM4 = histDescripcionM4;
    }

    public String getHistConsumoM3() {
        return histConsumoM3;
    }

    public void setHistConsumoM3(String histConsumoM3) {
        this.histConsumoM3 = histConsumoM3;
    }

    public String getHistDescripcionM3() {
        return histDescripcionM3;
    }

    public void setHistDescripcionM3(String histDescripcionM3) {
        this.histDescripcionM3 = histDescripcionM3;
    }

    public String getHistConsumoM2() {
        return histConsumoM2;
    }

    public void setHistConsumoM2(String histConsumoM2) {
        this.histConsumoM2 = histConsumoM2;
    }

    public String getHistDescripcionM2() {
        return histDescripcionM2;
    }

    public void setHistDescripcionM2(String histDescripcionM2) {
        this.histDescripcionM2 = histDescripcionM2;
    }

    public String getHistConsumoM1() {
        return histConsumoM1;
    }

    public void setHistConsumoM1(String histConsumoM1) {
        this.histConsumoM1 = histConsumoM1;
    }

    public String getHistDescripcionM1() {
        return histDescripcionM1;
    }

    public void setHistDescripcionM1(String histDescripcionM1) {
        this.histDescripcionM1 = histDescripcionM1;
    }

    public String getMedida1() {
        return medida1;
    }

    public void setMedida1(String medida1) {
        this.medida1 = medida1;
    }

    public String getLectAnteriorM1() {
        return lectAnteriorM1;
    }

    public void setLectAnteriorM1(String lectAnteriorM1) {
        this.lectAnteriorM1 = lectAnteriorM1;
    }

    public String getLectTomadaM1() {
        return lectTomadaM1;
    }

    public void setLectTomadaM1(String lectTomadaM1) {
        this.lectTomadaM1 = lectTomadaM1;
    }

    public String getTipoObservacionM1() {
        return tipoObservacionM1;
    }

    public void setTipoObservacionM1(String tipoObservacionM1) {
        this.tipoObservacionM1 = tipoObservacionM1;
    }

    public String getTipoConsumoM1() {
        return tipoConsumoM1;
    }

    public void setTipoConsumoM1(String tipoConsumoM1) {
        this.tipoConsumoM1 = tipoConsumoM1;
    }

    public String getMedida2() {
        return medida2;
    }

    public void setMedida2(String medida2) {
        this.medida2 = medida2;
    }

    public String getLectAnteriorM2() {
        return lectAnteriorM2;
    }

    public void setLectAnteriorM2(String lectAnteriorM2) {
        this.lectAnteriorM2 = lectAnteriorM2;
    }

    public String getLectTomadaM2() {
        return lectTomadaM2;
    }

    public void setLectTomadaM2(String lectTomadaM2) {
        this.lectTomadaM2 = lectTomadaM2;
    }

    public String getTipoObservacionM2() {
        return tipoObservacionM2;
    }

    public void setTipoObservacionM2(String tipoObservacionM2) {
        this.tipoObservacionM2 = tipoObservacionM2;
    }

    public String getTipoConsumoM2() {
        return tipoConsumoM2;
    }

    public void setTipoConsumoM2(String tipoConsumoM2) {
        this.tipoConsumoM2 = tipoConsumoM2;
    }

    public String getMedida3() {
        return medida3;
    }

    public void setMedida3(String medida3) {
        this.medida3 = medida3;
    }

    public String getLectAnteriorM3() {
        return lectAnteriorM3;
    }

    public void setLectAnteriorM3(String lectAnteriorM3) {
        this.lectAnteriorM3 = lectAnteriorM3;
    }

    public String getLectTomadaM3() {
        return lectTomadaM3;
    }

    public void setLectTomadaM3(String lectTomadaM3) {
        this.lectTomadaM3 = lectTomadaM3;
    }

    public String getTipoObservacionM3() {
        return tipoObservacionM3;
    }

    public void setTipoObservacionM3(String tipoObservacionM3) {
        this.tipoObservacionM3 = tipoObservacionM3;
    }

    public String getTipoConsumoM3() {
        return tipoConsumoM3;
    }

    public void setTipoConsumoM3(String tipoConsumoM3) {
        this.tipoConsumoM3 = tipoConsumoM3;
    }

    public String getMedida4() {
        return medida4;
    }

    public void setMedida4(String medida4) {
        this.medida4 = medida4;
    }

    public String getLectAnteriorM4() {
        return lectAnteriorM4;
    }

    public void setLectAnteriorM4(String lectAnteriorM4) {
        this.lectAnteriorM4 = lectAnteriorM4;
    }

    public String getLectTomadaM4() {
        return lectTomadaM4;
    }

    public void setLectTomadaM4(String lectTomadaM4) {
        this.lectTomadaM4 = lectTomadaM4;
    }

    public String getTipoObservacionM4() {
        return tipoObservacionM4;
    }

    public void setTipoObservacionM4(String tipoObservacionM4) {
        this.tipoObservacionM4 = tipoObservacionM4;
    }

    public String getTipoConsumoM4() {
        return tipoConsumoM4;
    }

    public void setTipoConsumoM4(String tipoConsumoM4) {
        this.tipoConsumoM4 = tipoConsumoM4;
    }

    public String getMedida5() {
        return medida5;
    }

    public void setMedida5(String medida5) {
        this.medida5 = medida5;
    }

    public String getLectAnteriorM5() {
        return lectAnteriorM5;
    }

    public void setLectAnteriorM5(String lectAnteriorM5) {
        this.lectAnteriorM5 = lectAnteriorM5;
    }

    public String getLectTomadaM5() {
        return lectTomadaM5;
    }

    public void setLectTomadaM5(String lectTomadaM5) {
        this.lectTomadaM5 = lectTomadaM5;
    }

    public String getTipoObservacionM5() {
        return tipoObservacionM5;
    }

    public void setTipoObservacionM5(String tipoObservacionM5) {
        this.tipoObservacionM5 = tipoObservacionM5;
    }

    public String getTipoConsumoM5() {
        return tipoConsumoM5;
    }

    public void setTipoConsumoM5(String tipoConsumoM5) {
        this.tipoConsumoM5 = tipoConsumoM5;
    }

    public String getPromedioCuenta() {
        return promedioCuenta;
    }

    public void setPromedioCuenta(String promedioCuenta) {
        this.promedioCuenta = promedioCuenta;
    }


    public String getMarcaContador1() {
        return marcaContador1;
    }

    public void setMarcaContador1(String marcaContador1) {
        this.marcaContador1 = marcaContador1;
    }

    public String getSerieContador1() {
        return serieContador1;
    }

    public void setSerieContador1(String serieContador1) {
        this.serieContador1 = serieContador1;
    }

    public String getFactorContador1() {
        return factorContador1;
    }

    public void setFactorContador1(String factorContador1) {
        this.factorContador1 = factorContador1;
    }

    public String getTransformadorContador1() {
        return transformadorContador1;
    }

    public void setTransformadorContador1(String transformadorContador1) {
        this.transformadorContador1 = transformadorContador1;
    }

    public String getMarcaContador2() {
        return marcaContador2;
    }

    public void setMarcaContador2(String marcaContador2) {
        this.marcaContador2 = marcaContador2;
    }

    public String getSerieContador2() {
        return serieContador2;
    }

    public void setSerieContador2(String serieContador2) {
        this.serieContador2 = serieContador2;
    }

    public String getFactorContador2() {
        return factorContador2;
    }

    public void setFactorContador2(String factorContador2) {
        this.factorContador2 = factorContador2;
    }

    public String getTransformadorContador2() {
        return transformadorContador2;
    }

    public void setTransformadorContador2(String transformadorContador2) {
        this.transformadorContador2 = transformadorContador2;
    }

    public String getNombreNodoGrupo() {
        return nombreNodoGrupo;
    }

    public void setNombreNodoGrupo(String nombreNodoGrupo) {
        this.nombreNodoGrupo = nombreNodoGrupo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getAcumuladoFES() {
        return acumuladoFES;
    }

    public void setAcumuladoFES(String acumuladoFES) {
        this.acumuladoFES = acumuladoFES;
    }

    public String getAcumuladoDES() {
        return acumuladoDES;
    }

    public void setAcumuladoDES(String acumuladoDES) {
        this.acumuladoDES = acumuladoDES;
    }

    public String getMetaFES() {
        return metaFES;
    }

    public void setMetaFES(String metaFES) {
        this.metaFES = metaFES;
    }

    public String getMetaDES() {
        return metaDES;
    }

    public void setMetaDES(String metaDES) {
        this.metaDES = metaDES;
    }

    public String getCostoGeneracion() {
        return costoGeneracion;
    }

    public void setCostoGeneracion(String costoGeneracion) {
        this.costoGeneracion = costoGeneracion;
    }

    public String getCostoTransmision() {
        return costoTransmision;
    }

    public void setCostoTransmision(String costoTransmision) {
        this.costoTransmision = costoTransmision;
    }

    public String getCostoDistribucion() {
        return costoDistribucion;
    }

    public void setCostoDistribucion(String costoDistribucion) {
        this.costoDistribucion = costoDistribucion;
    }

    public String getCostoPerdidas() {
        return costoPerdidas;
    }

    public void setCostoPerdidas(String costoPerdidas) {
        this.costoPerdidas = costoPerdidas;
    }

    public String getOtrosCostos() {
        return otrosCostos;
    }

    public void setOtrosCostos(String otrosCostos) {
        this.otrosCostos = otrosCostos;
    }

    public String getCostoComercializacion() {
        return costoComercializacion;
    }

    public void setCostoComercializacion(String costoComercializacion) {
        this.costoComercializacion = costoComercializacion;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getValorFinanciacion() {
        return valorFinanciacion;
    }

    public void setValorFinanciacion(String valorFinanciacion) {
        this.valorFinanciacion = valorFinanciacion;
    }

    public String getSaldoFinanciacion() {
        return saldoFinanciacion;
    }

    public void setSaldoFinanciacion(String saldoFinanciacion) {
        this.saldoFinanciacion = saldoFinanciacion;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    public String getCuotaActual() {
        return cuotaActual;
    }

    public void setCuotaActual(String cuotaActual) {
        this.cuotaActual = cuotaActual;
    }

    public String getCuotasPendientes() {
        return cuotasPendientes;
    }

    public void setCuotasPendientes(String cuotasPendientes) {
        this.cuotasPendientes = cuotasPendientes;
    }

    public String getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(String numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public String getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(String valorCuota) {
        this.valorCuota = valorCuota;
    }

    public String getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(String saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public String getSaldoReclamacion() {
        return saldoReclamacion;
    }

    public void setSaldoReclamacion(String saldoReclamacion) {
        this.saldoReclamacion = saldoReclamacion;
    }

    public String getTituloLiquidacionEnergia() {
        return tituloLiquidacionEnergia;
    }

    public void setTituloLiquidacionEnergia(String tituloLiquidacionEnergia) {
        this.tituloLiquidacionEnergia = tituloLiquidacionEnergia;
    }

    public String getPorcentajeSubsidio() {
        return porcentajeSubsidio;
    }

    public void setPorcentajeSubsidio(String porcentajeSubsidio) {
        this.porcentajeSubsidio = porcentajeSubsidio;
    }

    public String getTituloValorSubsidio() {
        return tituloValorSubsidio;
    }

    public void setTituloValorSubsidio(String tituloValorSubsidio) {
        this.tituloValorSubsidio = tituloValorSubsidio;
    }

    public String getValorSubsidio() {
        return valorSubsidio;
    }

    public void setValorSubsidio(String valorSubsidio) {
        this.valorSubsidio = valorSubsidio;
    }

    public String getTituloValorFinalConsumo() {
        return tituloValorFinalConsumo;
    }

    public void setTituloValorFinalConsumo(String tituloValorFinalConsumo) {
        this.tituloValorFinalConsumo = tituloValorFinalConsumo;
    }

    public String getValorTotalConsumo() {
        return valorTotalConsumo;
    }

    public void setValorTotalConsumo(String valorTotalConsumo) {
        this.valorTotalConsumo = valorTotalConsumo;
    }

    public String getTituloSubOtrosConceptos() {
        return tituloSubOtrosConceptos;
    }

    public void setTituloSubOtrosConceptos(String tituloSubOtrosConceptos) {
        this.tituloSubOtrosConceptos = tituloSubOtrosConceptos;
    }

    public String getValorSubOtrosConceptos() {
        return valorSubOtrosConceptos;
    }

    public void setValorSubOtrosConceptos(String valorSubOtrosConceptos) {
        this.valorSubOtrosConceptos = valorSubOtrosConceptos;
    }


    public String getDescripcionDescuento1() {
        return descripcionDescuento1;
    }

    public void setDescripcionDescuento1(String descripcionDescuento1) {
        this.descripcionDescuento1 = descripcionDescuento1;
    }

    public String getValorDescuento1() {
        return valorDescuento1;
    }

    public void setValorDescuento1(String valorDescuento1) {
        this.valorDescuento1 = valorDescuento1;
    }

    public String getDescripcionDescuento2() {
        return descripcionDescuento2;
    }

    public void setDescripcionDescuento2(String descripcionDescuento2) {
        this.descripcionDescuento2 = descripcionDescuento2;
    }

    public String getValorDescuento2() {
        return valorDescuento2;
    }

    public void setValorDescuento2(String valorDescuento2) {
        this.valorDescuento2 = valorDescuento2;
    }

    public String getDescripcionDescuento3() {
        return descripcionDescuento3;
    }

    public void setDescripcionDescuento3(String descripcionDescuento3) {
        this.descripcionDescuento3 = descripcionDescuento3;
    }

    public String getValorDescuento3() {
        return valorDescuento3;
    }

    public void setValorDescuento3(String valorDescuento3) {
        this.valorDescuento3 = valorDescuento3;
    }

    public String getDescripcionDescuento4() {
        return descripcionDescuento4;
    }

    public void setDescripcionDescuento4(String descripcionDescuento4) {
        this.descripcionDescuento4 = descripcionDescuento4;
    }

    public String getValorDescuento4() {
        return valorDescuento4;
    }

    public void setValorDescuento4(String valorDescuento4) {
        this.valorDescuento4 = valorDescuento4;
    }

    public String getDescripcionDescuento5() {
        return descripcionDescuento5;
    }

    public void setDescripcionDescuento5(String descripcionDescuento5) {
        this.descripcionDescuento5 = descripcionDescuento5;
    }

    public String getValorDescuento5() {
        return valorDescuento5;
    }

    public void setValorDescuento5(String valorDescuento5) {
        this.valorDescuento5 = valorDescuento5;
    }

    public String getTituloValorDescuentos() {
        return tituloValorDescuentos;
    }

    public void setTituloValorDescuentos(String tituloValorDescuentos) {
        this.tituloValorDescuentos = tituloValorDescuentos;
    }

    public String getValorTotalDescuentos() {
        return valorTotalDescuentos;
    }

    public void setValorTotalDescuentos(String valorTotalDescuentos) {
        this.valorTotalDescuentos = valorTotalDescuentos;
    }

    public String getTituloSubConceptosEnergia() {
        return tituloSubConceptosEnergia;
    }

    public void setTituloSubConceptosEnergia(String tituloSubConceptosEnergia) {
        this.tituloSubConceptosEnergia = tituloSubConceptosEnergia;
    }

    public String getValorConceptosEnergia() {
        return valorConceptosEnergia;
    }

    public void setValorConceptosEnergia(String valorConceptosEnergia) {
        this.valorConceptosEnergia = valorConceptosEnergia;
    }

    public String getFechaPagoNormal() {
        return fechaPagoNormal;
    }

    public void setFechaPagoNormal(String fechaPagoNormal) {
        this.fechaPagoNormal = fechaPagoNormal;
    }

    public String getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getValorTotalFactura() {
        return valorTotalFactura;
    }

    public void setValorTotalFactura(String valorTotalFactura) {
        this.valorTotalFactura = valorTotalFactura;
    }

    public String getNombreGerente() {
        return nombreGerente;
    }

    public void setNombreGerente(String nombreGerente) {
        this.nombreGerente = nombreGerente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getValorTotalConceptoExt() {
        return valorTotalConceptoExt;
    }

    public void setValorTotalConceptoExt(String valorTotalConceptoExt) {
        this.valorTotalConceptoExt = valorTotalConceptoExt;
    }

    public String getNivelTension() {
        return nivelTension;
    }

    public void setNivelTension(String nivelTension) {
        this.nivelTension = nivelTension;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public String getTituloValorNeto() {
        return tituloValorNeto;
    }

    public void setTituloValorNeto(String tituloValorNeto) {
        this.tituloValorNeto = tituloValorNeto;
    }

    public String getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(String valorNeto) {
        this.valorNeto = valorNeto;
    }

    public String getTituloOtrosValores() {
        return tituloOtrosValores;
    }

    public void setTituloOtrosValores(String tituloOtrosValores) {
        this.tituloOtrosValores = tituloOtrosValores;
    }

    public String getTituloDescuentos() {
        return tituloDescuentos;
    }

    public void setTituloDescuentos(String tituloDescuentos) {
        this.tituloDescuentos = tituloDescuentos;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(String ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getPeriodoFacturado() {
        return periodoFacturado;
    }

    public void setPeriodoFacturado(String periodoFacturado) {
        this.periodoFacturado = periodoFacturado;
    }

    public String getValorMinConsumo() {
        return valorMinConsumo;
    }

    public void setValorMinConsumo(String valorMinConsumo) {
        this.valorMinConsumo = valorMinConsumo;
    }

    public String getValorMaxConsumo() {
        return valorMaxConsumo;
    }

    public void setValorMaxConsumo(String valorMaxConsumo) {
        this.valorMaxConsumo = valorMaxConsumo;
    }

    public String getValorFoes() {
        return valorFoes;
    }

    public void setValorFoes(String valorFoes) {
        this.valorFoes = valorFoes;
    }

    public String getConsumoKWHFoes() {
        return consumoKWHFoes;
    }

    public void setConsumoKWHFoes(String consumoKWHFoes) {
        this.consumoKWHFoes = consumoKWHFoes;
    }

    public String getValorUnitarioFoes() {
        return valorUnitarioFoes;
    }

    public void setValorUnitarioFoes(String valorUnitarioFoes) {
        this.valorUnitarioFoes = valorUnitarioFoes;
    }

    public String getFacturaFoes() {
        return facturaFoes;
    }

    public void setFacturaFoes(String facturaFoes) {
        this.facturaFoes = facturaFoes;
    }

    public String getFacturaCobroBio() {
        return facturaCobroBio;
    }

    public void setFacturaCobroBio(String facturaCobroBio) {
        this.facturaCobroBio = facturaCobroBio;
    }

    public String getCodigoBio() {
        return codigoBio;
    }

    public void setCodigoBio(String codigoBio) {
        this.codigoBio = codigoBio;
    }

    public String getUltFactura1Bio() {
        return ultFactura1Bio;
    }

    public void setUltFactura1Bio(String ultFactura1Bio) {
        this.ultFactura1Bio = ultFactura1Bio;
    }

    public String getUltFactura2Bio() {
        return ultFactura2Bio;
    }

    public void setUltFactura2Bio(String ultFactura2Bio) {
        this.ultFactura2Bio = ultFactura2Bio;
    }

    public String getUltFactura3Bio() {
        return ultFactura3Bio;
    }

    public void setUltFactura3Bio(String ultFactura3Bio) {
        this.ultFactura3Bio = ultFactura3Bio;
    }

    public String getUltFactura4Bio() {
        return ultFactura4Bio;
    }

    public void setUltFactura4Bio(String ultFactura4Bio) {
        this.ultFactura4Bio = ultFactura4Bio;
    }

    public String getUltFactura5Bio() {
        return ultFactura5Bio;
    }

    public void setUltFactura5Bio(String ultFactura5Bio) {
        this.ultFactura5Bio = ultFactura5Bio;
    }

    public String getUltFactura6Bio() {
        return ultFactura6Bio;
    }

    public void setUltFactura6Bio(String ultFactura6Bio) {
        this.ultFactura6Bio = ultFactura6Bio;
    }

    public String getTipoUsoBio() {
        return tipoUsoBio;
    }

    public void setTipoUsoBio(String tipoUsoBio) {
        this.tipoUsoBio = tipoUsoBio;
    }

    public String getTasaMoraBio() {
        return tasaMoraBio;
    }

    public void setTasaMoraBio(String tasaMoraBio) {
        this.tasaMoraBio = tasaMoraBio;
    }

    public String getFactVencidasBio() {
        return factVencidasBio;
    }

    public void setFactVencidasBio(String factVencidasBio) {
        this.factVencidasBio = factVencidasBio;
    }

    public String getEstratoBio() {
        return estratoBio;
    }

    public void setEstratoBio(String estratoBio) {
        this.estratoBio = estratoBio;
    }

    public String getCategoriaBio() {
        return categoriaBio;
    }

    public void setCategoriaBio(String categoriaBio) {
        this.categoriaBio = categoriaBio;
    }

    public String getMtsCubicosBio() {
        return mtsCubicosBio;
    }

    public void setMtsCubicosBio(String mtsCubicosBio) {
        this.mtsCubicosBio = mtsCubicosBio;
    }

    public String getFrBarrioBio() {
        return frBarrioBio;
    }

    public void setFrBarrioBio(String frBarrioBio) {
        this.frBarrioBio = frBarrioBio;
    }

    public String getFrRecoleccionBio() {
        return frRecoleccionBio;
    }

    public void setFrRecoleccionBio(String frRecoleccionBio) {
        this.frRecoleccionBio = frRecoleccionBio;
    }

    public String getPorcSubsidioBio() {
        return porcSubsidioBio;
    }

    public void setPorcSubsidioBio(String porcSubsidioBio) {
        this.porcSubsidioBio = porcSubsidioBio;
    }

    public String getPorcContribucionBio() {
        return porcContribucionBio;
    }

    public void setPorcContribucionBio(String porcContribucionBio) {
        this.porcContribucionBio = porcContribucionBio;
    }

    public String getValorTotalBio() {
        return valorTotalBio;
    }

    public void setValorTotalBio(String valorTotalBio) {
        this.valorTotalBio = valorTotalBio;
    }

    public String getConceptoFinanciadoBio() {
        return conceptoFinanciadoBio;
    }

    public void setConceptoFinanciadoBio(String conceptoFinanciadoBio) {
        this.conceptoFinanciadoBio = conceptoFinanciadoBio;
    }

    public String getNumCuotasBio() {
        return numCuotasBio;
    }

    public void setNumCuotasBio(String numCuotasBio) {
        this.numCuotasBio = numCuotasBio;
    }

    public String getValorCuotaBio() {
        return valorCuotaBio;
    }

    public void setValorCuotaBio(String valorCuotaBio) {
        this.valorCuotaBio = valorCuotaBio;
    }

    public String getDescripcionBio() {
        return descripcionBio;
    }

    public void setDescripcionBio(String descripcionBio) {
        this.descripcionBio = descripcionBio;
    }

    public String getNumCuotaMesConcepto1Bio() {
        return numCuotaMesConcepto1Bio;
    }

    public void setNumCuotaMesConcepto1Bio(String numCuotaMesConcepto1Bio) {
        this.numCuotaMesConcepto1Bio = numCuotaMesConcepto1Bio;
    }

    public String getNumCuotaMesConcepto2Bio() {
        return numCuotaMesConcepto2Bio;
    }

    public void setNumCuotaMesConcepto2Bio(String numCuotaMesConcepto2Bio) {
        this.numCuotaMesConcepto2Bio = numCuotaMesConcepto2Bio;
    }

    public String getNumCuotaMesConcepto3Bio() {
        return numCuotaMesConcepto3Bio;
    }

    public void setNumCuotaMesConcepto3Bio(String numCuotaMesConcepto3Bio) {
        this.numCuotaMesConcepto3Bio = numCuotaMesConcepto3Bio;
    }

    public String getNumCuotaMesConcepto4Bio() {
        return numCuotaMesConcepto4Bio;
    }

    public void setNumCuotaMesConcepto4Bio(String numCuotaMesConcepto4Bio) {
        this.numCuotaMesConcepto4Bio = numCuotaMesConcepto4Bio;
    }

    public String getLiquidacionTarifa1() {
        return liquidacionTarifa1;
    }

    public void setLiquidacionTarifa1(String liquidacionTarifa1) {
        this.liquidacionTarifa1 = liquidacionTarifa1;
    }

    public String getValorTarifa1() {
        return valorTarifa1;
    }

    public void setValorTarifa1(String valorTarifa1) {
        this.valorTarifa1 = valorTarifa1;
    }

    public String getLiquidacionTarifa2() {
        return liquidacionTarifa2;
    }

    public void setLiquidacionTarifa2(String liquidacionTarifa2) {
        this.liquidacionTarifa2 = liquidacionTarifa2;
    }

    public String getValorTarifa2() {
        return valorTarifa2;
    }

    public void setValorTarifa2(String valorTarifa2) {
        this.valorTarifa2 = valorTarifa2;
    }

    public String getLiquidacionTarifa3() {
        return liquidacionTarifa3;
    }

    public void setLiquidacionTarifa3(String liquidacionTarifa3) {
        this.liquidacionTarifa3 = liquidacionTarifa3;
    }

    public String getValorTarifa3() {
        return valorTarifa3;
    }

    public void setValorTarifa3(String valorTarifa3) {
        this.valorTarifa3 = valorTarifa3;
    }

    public String getLiquidacionTarifa4() {
        return liquidacionTarifa4;
    }

    public void setLiquidacionTarifa4(String liquidacionTarifa4) {
        this.liquidacionTarifa4 = liquidacionTarifa4;
    }

    public String getValorTarifa4() {
        return valorTarifa4;
    }

    public void setValorTarifa4(String valorTarifa4) {
        this.valorTarifa4 = valorTarifa4;
    }

    public String getLiquidacionTarifa5() {
        return liquidacionTarifa5;
    }

    public void setLiquidacionTarifa5(String liquidacionTarifa5) {
        this.liquidacionTarifa5 = liquidacionTarifa5;
    }

    public String getValorTarifa5() {
        return valorTarifa5;
    }

    public void setValorTarifa5(String valorTarifa5) {
        this.valorTarifa5 = valorTarifa5;
    }

    public String getLiquidacionTarifa6() {
        return liquidacionTarifa6;
    }

    public void setLiquidacionTarifa6(String liquidacionTarifa6) {
        this.liquidacionTarifa6 = liquidacionTarifa6;
    }

    public String getValorTarifa6() {
        return valorTarifa6;
    }

    public void setValorTarifa6(String valorTarifa6) {
        this.valorTarifa6 = valorTarifa6;
    }

    public String getDescOtrosConceptos1() {
        return descOtrosConceptos1;
    }

    public void setDescOtrosConceptos1(String descOtrosConceptos1) {
        this.descOtrosConceptos1 = descOtrosConceptos1;
    }

    public String getValorOtrosConceptos1() {
        return valorOtrosConceptos1;
    }

    public void setValorOtrosConceptos1(String valorOtrosConceptos1) {
        this.valorOtrosConceptos1 = valorOtrosConceptos1;
    }

    public String getDescOtrosConceptos2() {
        return descOtrosConceptos2;
    }

    public void setDescOtrosConceptos2(String descOtrosConceptos2) {
        this.descOtrosConceptos2 = descOtrosConceptos2;
    }

    public String getValorOtrosConceptos2() {
        return valorOtrosConceptos2;
    }

    public void setValorOtrosConceptos2(String valorOtrosConceptos2) {
        this.valorOtrosConceptos2 = valorOtrosConceptos2;
    }

    public String getDescOtrosConceptos3() {
        return descOtrosConceptos3;
    }

    public void setDescOtrosConceptos3(String descOtrosConceptos3) {
        this.descOtrosConceptos3 = descOtrosConceptos3;
    }

    public String getValorOtrosConceptos3() {
        return valorOtrosConceptos3;
    }

    public void setValorOtrosConceptos3(String valorOtrosConceptos3) {
        this.valorOtrosConceptos3 = valorOtrosConceptos3;
    }

    public String getDescOtrosConceptos4() {
        return descOtrosConceptos4;
    }

    public void setDescOtrosConceptos4(String descOtrosConceptos4) {
        this.descOtrosConceptos4 = descOtrosConceptos4;
    }

    public String getValorOtrosConceptos4() {
        return valorOtrosConceptos4;
    }

    public void setValorOtrosConceptos4(String valorOtrosConceptos4) {
        this.valorOtrosConceptos4 = valorOtrosConceptos4;
    }

    public String getDescOtrosConceptos5() {
        return descOtrosConceptos5;
    }

    public void setDescOtrosConceptos5(String descOtrosConceptos5) {
        this.descOtrosConceptos5 = descOtrosConceptos5;
    }

    public String getValorOtrosConceptos5() {
        return valorOtrosConceptos5;
    }

    public void setValorOtrosConceptos5(String valorOtrosConceptos5) {
        this.valorOtrosConceptos5 = valorOtrosConceptos5;
    }

    public String getDescOtrosConceptos6() {
        return descOtrosConceptos6;
    }

    public void setDescOtrosConceptos6(String descOtrosConceptos6) {
        this.descOtrosConceptos6 = descOtrosConceptos6;
    }

    public String getValorOtrosConceptos6() {
        return valorOtrosConceptos6;
    }

    public void setValorOtrosConceptos6(String valorOtrosConceptos6) {
        this.valorOtrosConceptos6 = valorOtrosConceptos6;
    }

    public String getDescOtrosConceptos7() {
        return descOtrosConceptos7;
    }

    public void setDescOtrosConceptos7(String descOtrosConceptos7) {
        this.descOtrosConceptos7 = descOtrosConceptos7;
    }

    public String getValorOtrosConceptos7() {
        return valorOtrosConceptos7;
    }

    public void setValorOtrosConceptos7(String valorOtrosConceptos7) {
        this.valorOtrosConceptos7 = valorOtrosConceptos7;
    }

    public String getDescOtrosConceptos8() {
        return descOtrosConceptos8;
    }

    public void setDescOtrosConceptos8(String descOtrosConceptos8) {
        this.descOtrosConceptos8 = descOtrosConceptos8;
    }

    public String getValorOtrosConceptos8() {
        return valorOtrosConceptos8;
    }

    public void setValorOtrosConceptos8(String valorOtrosConceptos8) {
        this.valorOtrosConceptos8 = valorOtrosConceptos8;
    }

    public String getDescOtrosConceptos9() {
        return descOtrosConceptos9;
    }

    public void setDescOtrosConceptos9(String descOtrosConceptos9) {
        this.descOtrosConceptos9 = descOtrosConceptos9;
    }

    public String getValorOtrosConceptos9() {
        return valorOtrosConceptos9;
    }

    public void setValorOtrosConceptos9(String valorOtrosConceptos9) {
        this.valorOtrosConceptos9 = valorOtrosConceptos9;
    }

    public String getDescOtrosConceptos10() {
        return descOtrosConceptos10;
    }

    public void setDescOtrosConceptos10(String descOtrosConceptos10) {
        this.descOtrosConceptos10 = descOtrosConceptos10;
    }

    public String getValorOtrosConceptos10() {
        return valorOtrosConceptos10;
    }

    public void setValorOtrosConceptos10(String valorOtrosConceptos10) {
        this.valorOtrosConceptos10 = valorOtrosConceptos10;
    }

    public String getDescConceptoExt1() {
        return descConceptoExt1;
    }

    public void setDescConceptoExt1(String descConceptoExt1) {
        this.descConceptoExt1 = descConceptoExt1;
    }

    public String getValorActualConceptoExt1() {
        return valorActualConceptoExt1;
    }

    public void setValorActualConceptoExt1(String valorActualConceptoExt1) {
        this.valorActualConceptoExt1 = valorActualConceptoExt1;
    }

    public String getInteresConceptoExt1() {
        return interesConceptoExt1;
    }

    public void setInteresConceptoExt1(String interesConceptoExt1) {
        this.interesConceptoExt1 = interesConceptoExt1;
    }

    public String getValorCuotaConceptoExt1() {
        return valorCuotaConceptoExt1;
    }

    public void setValorCuotaConceptoExt1(String valorCuotaConceptoExt1) {
        this.valorCuotaConceptoExt1 = valorCuotaConceptoExt1;
    }

    public String getDescConceptoExt2() {
        return descConceptoExt2;
    }

    public void setDescConceptoExt2(String descConceptoExt2) {
        this.descConceptoExt2 = descConceptoExt2;
    }

    public String getValorActualConceptoExt2() {
        return valorActualConceptoExt2;
    }

    public void setValorActualConceptoExt2(String valorActualConceptoExt2) {
        this.valorActualConceptoExt2 = valorActualConceptoExt2;
    }

    public String getInteresConceptoExt2() {
        return interesConceptoExt2;
    }

    public void setInteresConceptoExt2(String interesConceptoExt2) {
        this.interesConceptoExt2 = interesConceptoExt2;
    }

    public String getValorCuotaConceptoExt2() {
        return valorCuotaConceptoExt2;
    }

    public void setValorCuotaConceptoExt2(String valorCuotaConceptoExt2) {
        this.valorCuotaConceptoExt2 = valorCuotaConceptoExt2;
    }

    public String getDescConceptoExt3() {
        return descConceptoExt3;
    }

    public void setDescConceptoExt3(String descConceptoExt3) {
        this.descConceptoExt3 = descConceptoExt3;
    }

    public String getValorActualConceptoExt3() {
        return valorActualConceptoExt3;
    }

    public void setValorActualConceptoExt3(String valorActualConceptoExt3) {
        this.valorActualConceptoExt3 = valorActualConceptoExt3;
    }

    public String getInteresConceptoExt3() {
        return interesConceptoExt3;
    }

    public void setInteresConceptoExt3(String interesConceptoExt3) {
        this.interesConceptoExt3 = interesConceptoExt3;
    }

    public String getValorCuotaConceptoExt3() {
        return valorCuotaConceptoExt3;
    }

    public void setValorCuotaConceptoExt3(String valorCuotaConceptoExt3) {
        this.valorCuotaConceptoExt3 = valorCuotaConceptoExt3;
    }

    public String getDescConceptoExt4() {
        return descConceptoExt4;
    }

    public void setDescConceptoExt4(String descConceptoExt4) {
        this.descConceptoExt4 = descConceptoExt4;
    }

    public String getValorActualConceptoExt4() {
        return valorActualConceptoExt4;
    }

    public void setValorActualConceptoExt4(String valorActualConceptoExt4) {
        this.valorActualConceptoExt4 = valorActualConceptoExt4;
    }

    public String getInteresConceptoExt4() {
        return interesConceptoExt4;
    }

    public void setInteresConceptoExt4(String interesConceptoExt4) {
        this.interesConceptoExt4 = interesConceptoExt4;
    }

    public String getValorCuotaConceptoExt4() {
        return valorCuotaConceptoExt4;
    }

    public void setValorCuotaConceptoExt4(String valorCuotaConceptoExt4) {
        this.valorCuotaConceptoExt4 = valorCuotaConceptoExt4;
    }

    public String getConcepto1Bio() {
        return concepto1Bio;
    }

    public void setConcepto1Bio(String concepto1Bio) {
        this.concepto1Bio = concepto1Bio;
    }

    public String getValor1Bio() {
        return valor1Bio;
    }

    public void setValor1Bio(String valor1Bio) {
        this.valor1Bio = valor1Bio;
    }

    public String getConcepto2Bio() {
        return concepto2Bio;
    }

    public void setConcepto2Bio(String concepto2Bio) {
        this.concepto2Bio = concepto2Bio;
    }

    public String getValor2Bio() {
        return valor2Bio;
    }

    public void setValor2Bio(String valor2Bio) {
        this.valor2Bio = valor2Bio;
    }

    public String getConcepto3Bio() {
        return concepto3Bio;
    }

    public void setConcepto3Bio(String concepto3Bio) {
        this.concepto3Bio = concepto3Bio;
    }

    public String getValor3Bio() {
        return valor3Bio;
    }

    public void setValor3Bio(String valor3Bio) {
        this.valor3Bio = valor3Bio;
    }

    public String getConcepto4Bio() {
        return concepto4Bio;
    }

    public void setConcepto4Bio(String concepto4Bio) {
        this.concepto4Bio = concepto4Bio;
    }

    public String getValor4Bio() {
        return valor4Bio;
    }

    public void setValor4Bio(String valor4Bio) {
        this.valor4Bio = valor4Bio;
    }

    public String getConcepto5Bio() {
        return concepto5Bio;
    }

    public void setConcepto5Bio(String concepto5Bio) {
        this.concepto5Bio = concepto5Bio;
    }

    public String getValor5Bio() {
        return valor5Bio;
    }

    public void setValor5Bio(String valor5Bio) {
        this.valor5Bio = valor5Bio;
    }

    public String getConcepto6Bio() {
        return concepto6Bio;
    }

    public void setConcepto6Bio(String concepto6Bio) {
        this.concepto6Bio = concepto6Bio;
    }

    public String getValor6Bio() {
        return valor6Bio;
    }

    public void setValor6Bio(String valor6Bio) {
        this.valor6Bio = valor6Bio;
    }

    public String getConcepto7Bio() {
        return concepto7Bio;
    }

    public void setConcepto7Bio(String concepto7Bio) {
        this.concepto7Bio = concepto7Bio;
    }

    public String getValor7Bio() {
        return valor7Bio;
    }

    public void setValor7Bio(String valor7Bio) {
        this.valor7Bio = valor7Bio;
    }

    public String getConcepto8Bio() {
        return concepto8Bio;
    }

    public void setConcepto8Bio(String concepto8Bio) {
        this.concepto8Bio = concepto8Bio;
    }

    public String getValor8Bio() {
        return valor8Bio;
    }

    public void setValor8Bio(String valor8Bio) {
        this.valor8Bio = valor8Bio;
    }

    public String getConcepto9Bio() {
        return concepto9Bio;
    }

    public void setConcepto9Bio(String concepto9Bio) {
        this.concepto9Bio = concepto9Bio;
    }

    public String getValor9Bio() {
        return valor9Bio;
    }

    public void setValor9Bio(String valor9Bio) {
        this.valor9Bio = valor9Bio;
    }

    public String getConcepto10Bio() {
        return concepto10Bio;
    }

    public void setConcepto10Bio(String concepto10Bio) {
        this.concepto10Bio = concepto10Bio;
    }

    public String getValor10Bio() {
        return valor10Bio;
    }

    public void setValor10Bio(String valor10Bio) {
        this.valor10Bio = valor10Bio;
    }


    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }
}
