package dao;

import android.content.ContentValues;
import android.content.Context;

import global.database_var;
import sistema.SQLite;
import vo.FacturaVO;
import vo.LecturaVO;

/**
 * Created by Julian Poveda on 12/05/2016.
 */
public class FacturaDAO implements database_var {
    private static SQLite  fcnSQL;
    private static FacturaDAO ourInstance;


    public static FacturaDAO getInstance(Context _ctx){
        if(ourInstance == null){
            ourInstance = new FacturaDAO();
            fcnSQL = new SQLite(_ctx);
        }

        return ourInstance;
    }




    public FacturaVO getFactura(int _idSerial1, int _idSerial2, int _idSerial3){
        FacturaVO factura = null;

        ContentValues registroBase = fcnSQL.SelectDataRegistro("maestro_clientes",
                "tipo_energia_1, tipo_energia_2, tipo_energia_3, nombre, direccion, id_municipio, marca_medidor, serie_medidor, factor_multiplicacion, cuenta, id_ciclo, lectura_anterior_1",
                "id_serial_1 = "+_idSerial1+" AND id_serial_2 = "+_idSerial2+" AND id_serial_3 = "+_idSerial3);


        ContentValues registroLectura = fcnSQL.SelectDataRegistro("toma_lectura",
                "id, anomalia, lectura1, lectura2, lectura3, id_inspector",
                "id_serial1 = "+_idSerial1+" AND id_serial2 = "+_idSerial2+" AND id_serial3 = "+_idSerial3+" ORDER BY id DESC ");


        if(registroLectura.size() > 0)
        {
            factura = new FacturaVO(registroBase.getAsString("cuenta"), registroBase.getAsString("nombre"),
                    registroBase.getAsString("direccion"),
                    this.fcnSQL.StrSelectShieldWhere("param_municipios", "municipio", "id_municipio = " + registroBase.getAsString("id_municipio")),
                    registroLectura.getAsInteger("id_inspector"));


            ContentValues registro = this.fcnSQL.SelectDataRegistro("maestro_clientes_facturas", "*",
                    FACT_CODIGO +"="+registroBase.getAsString("cuenta"));

            factura.setConsumo((registroLectura.getAsInteger("lectura1") - registroBase.getAsInteger("lectura_anterior_1"))
                    * registroBase.getAsInteger("factor_multiplicacion"));


            factura.setMes(registro.getAsString(FACT_MES));
            factura.setAnno(registro.getAsString(FACT_ANNO));
            factura.setCicloCargue(registro.getAsString(FACT_CICLO_CARGUE));
            factura.setPeriodoFact(registro.getAsString(FACT_PERIODO));
            factura.setEstrato(registro.getAsString(FACT_ESTRATO));
            factura.setClaseServicio(registro.getAsString(FACT_CLASE_SERVICIO));
            factura.setCiclo(registroBase.getAsString("id_ciclo"));
            factura.setUbicacion(registro.getAsString(FACT_UBICACION));
            factura.setEstadoPredio(registro.getAsString(FACT_ESTADO_PREDIO));
            factura.setActividadCIU(registro.getAsString(FACT_ACTIVIDAD_CIU));
            factura.setRuta(registro.getAsString(FACT_RUTA));
            factura.setNumFactura(registro.getAsString(FACT_NUM_FACTURA));
            factura.setPeriodosVencidos(registro.getAsString(FACT_PERIODOS_VENCIDOS));

            factura.setHistDescripcionM1(registro.getAsString(FACT_HIST_DESCRIPCION_M1));
            factura.setHistConsumoM1(registro.getAsString(FACT_HIST_CONSUMO_M1));

            factura.setHistDescripcionM2(registro.getAsString(FACT_HIST_DESCRIPCION_M2));
            factura.setHistConsumoM2(registro.getAsString(FACT_HIST_CONSUMO_M2));

            factura.setHistDescripcionM3(registro.getAsString(FACT_HIST_DESCRIPCION_M3));
            factura.setHistConsumoM3(registro.getAsString(FACT_HIST_CONSUMO_M3));

            factura.setHistDescripcionM4(registro.getAsString(FACT_HIST_DESCRIPCION_M4));
            factura.setHistConsumoM4(registro.getAsString(FACT_HIST_CONSUMO_M4));

            factura.setHistDescripcionM5(registro.getAsString(FACT_HIST_DESCRIPCION_M5));
            factura.setHistConsumoM5(registro.getAsString(FACT_HIST_CONSUMO_M5));

            factura.setHistDescripcionM6(registro.getAsString(FACT_HIST_DESCRIPCION_M6));
            factura.setHistConsumoM6(registro.getAsString(FACT_HIST_CONSUMO_M6));

            factura.setMedida1(registro.getAsString(FACT_MEDIDA1));
            factura.setLectAnteriorM1(registro.getAsString(FACT_LECT_ANTERIOR_M1));
            factura.setLectTomadaM1(registro.getAsString(FACT_LECT_TOMADA_M1));
            factura.setTipoObservacionM1(registro.getAsString(FACT_TIPO_OBSERVACION_M1));
            factura.setTipoConsumoM1(registro.getAsString(FACT_TIPO_CONSUMO_M1));

            factura.setMedida2(registro.getAsString(FACT_MEDIDA2));
            factura.setLectAnteriorM2(registro.getAsString(FACT_LECT_ANTERIOR_M2));
            factura.setLectTomadaM2(registro.getAsString(FACT_LECT_TOMADA_M2));
            factura.setTipoObservacionM2(registro.getAsString(FACT_TIPO_OBSERVACION_M2));
            factura.setTipoConsumoM2(registro.getAsString(FACT_TIPO_CONSUMO_M2));

            factura.setMedida3(registro.getAsString(FACT_MEDIDA3));
            factura.setLectAnteriorM3(registro.getAsString(FACT_LECT_ANTERIOR_M3));
            factura.setLectTomadaM3(registro.getAsString(FACT_LECT_TOMADA_M3));
            factura.setTipoObservacionM3(registro.getAsString(FACT_TIPO_OBSERVACION_M3));
            factura.setTipoConsumoM3(registro.getAsString(FACT_TIPO_CONSUMO_M3));

            factura.setMedida4(registro.getAsString(FACT_MEDIDA4));
            factura.setLectAnteriorM4(registro.getAsString(FACT_LECT_ANTERIOR_M4));
            factura.setLectTomadaM4(registro.getAsString(FACT_LECT_TOMADA_M4));
            factura.setTipoObservacionM4(registro.getAsString(FACT_TIPO_OBSERVACION_M4));
            factura.setTipoConsumoM4(registro.getAsString(FACT_TIPO_CONSUMO_M4));

            factura.setMedida5(registro.getAsString(FACT_MEDIDA5));
            factura.setLectAnteriorM5(registro.getAsString(FACT_LECT_ANTERIOR_M5));
            factura.setLectTomadaM5(registro.getAsString(FACT_LECT_TOMADA_M5));
            factura.setTipoObservacionM5(registro.getAsString(FACT_TIPO_OBSERVACION_M5));
            factura.setTipoConsumoM5(registro.getAsString(FACT_TIPO_CONSUMO_M5));

            factura.setPromedioCuenta(registro.getAsString(FACT_PROMEDIO_CUENTA));

            factura.setMarcaContador1(registroBase.getAsString("marca_medidor"));
            factura.setSerieContador1(registroBase.getAsString("serie_medidor"));
            factura.setFactorContador1(registroBase.getAsString("factor_multiplicacion"));
            //factura.setTransformadorContador1(registro.getAsString(FACT_TRANSFORMADOR1));

            factura.setMarcaContador2(registro.getAsString(FACT_MARCA_CONTADOR2));
            factura.setSerieContador2(registro.getAsString(FACT_SERIE_CONTADOR2));
            factura.setFactorContador2(registro.getAsString(FACT_FACTOR2));
            factura.setTransformadorContador2(registro.getAsString(FACT_TRANSFORMADOR2));

            factura.setNombreNodoGrupo(registro.getAsString(FACT_NOMBRE_NODO_GRUPO));
            factura.setNodo(registro.getAsString(FACT_NODO));
            factura.setAcumuladoFES(registro.getAsString(FACT_ACUMULADO_FES));
            factura.setAcumuladoDES(registro.getAsString(FACT_ACUMULADO_DES));
            factura.setMetaFES(registro.getAsString(FACT_META_FES));
            factura.setMetaDES(registro.getAsString(FACT_META_DES));
            factura.setCostoGeneracion(registro.getAsString(FACT_COSTO_GENERACION));
            factura.setCostoTransmision(registro.getAsString(FACT_COSTO_TRANSMISION));
            factura.setCostoDistribucion(registro.getAsString(FACT_COSTO_DISTRIBUCION));
            factura.setCostoPerdidas(registro.getAsString(FACT_COSTO_PERDIDAS));
            factura.setOtrosCostos(registro.getAsString(FACT_OTROS_COSTOS));
            factura.setCostoComercializacion(registro.getAsString(FACT_COSTO_COMERCIALIZACION));
            factura.setCostoTotal(registro.getAsString(FACT_COSTO_TOTAL));
            factura.setValorFinanciacion(registro.getAsString(FACT_VALOR_FINANCIACION));
            factura.setSaldoFinanciacion(registro.getAsString(FACT_SALDO_FINANCIACION));

            factura.setInteres(registro.getAsString(FACT_INTERES));
            factura.setCuotaActual(registro.getAsString(FACT_CUOTA_ACTUAL));
            factura.setCuotasPendientes(registro.getAsString(FACT_CUOTAS_PENDIENTES));
            factura.setNumeroCuota(registro.getAsString(FACT_NUMERO_CUOTA));
            factura.setValorCuota(registro.getAsString(FACT_VALOR_CUOTA));
            factura.setSaldoFavor(registro.getAsString(FACT_SALDO_FAVOR));
            factura.setSaldoReclamacion(registro.getAsString(FACT_SALDO_RECLAMACION));
            factura.setTituloLiquidacionEnergia(registro.getAsString(FACT_TITULO_LIQUI_ENERGIA));


            factura.setLiquidacionTarifa1(registro.getAsString(FACT_LIQUI_TARIFA1));
            factura.setValorTarifa1(registro.getAsString(FACT_VALOR_LIQUI_TARIFA1));

            factura.setLiquidacionTarifa2(registro.getAsString(FACT_LIQUI_TARIFA2));
            factura.setValorTarifa2(registro.getAsString(FACT_VALOR_LIQUI_TARIFA2));

            factura.setLiquidacionTarifa3(registro.getAsString(FACT_LIQUI_TARIFA3));
            factura.setValorTarifa3(registro.getAsString(FACT_VALOR_LIQUI_TARIFA3));

            factura.setLiquidacionTarifa4(registro.getAsString(FACT_LIQUI_TARIFA4));
            factura.setValorTarifa4(registro.getAsString(FACT_VALOR_LIQUI_TARIFA4));

            factura.setLiquidacionTarifa5(registro.getAsString(FACT_LIQUI_TARIFA5));
            factura.setValorTarifa5(registro.getAsString(FACT_VALOR_LIQUI_TARIFA5));

            factura.setLiquidacionTarifa6(registro.getAsString(FACT_LIQUI_TARIFA6));
            factura.setValorTarifa6(registro.getAsString(FACT_VALOR_LIQUI_TARIFA6));

            factura.setPorcentajeSubsidio(registro.getAsString(FACT_PORCENTAJE_SUBSUDIO));
            factura.setTituloValorSubsidio(registro.getAsString(FACT_TITULO_VALOR_SUBSIDIO));
            factura.setValorSubsidio(registro.getAsString(FACT_VALOR_SUBSIDIO));
            factura.setTituloValorFinalConsumo(registro.getAsString(FACT_TITULO_VALOR_FINAL_CONSUMO));
            factura.setValorTotalConsumo(registro.getAsString(FACT_VALOR_TOTAL_CONSUMO));

            factura.setDescOtrosConceptos1(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS1));
            factura.setValorOtrosConceptos1(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS1));

            factura.setDescOtrosConceptos2(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS2));
            factura.setValorOtrosConceptos2(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS2));

            factura.setDescOtrosConceptos3(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS3));
            factura.setValorOtrosConceptos3(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS3));

            factura.setDescOtrosConceptos4(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS4));
            factura.setValorOtrosConceptos4(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS4));

            factura.setDescOtrosConceptos5(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS5));
            factura.setValorOtrosConceptos5(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS5));

            factura.setDescOtrosConceptos6(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS6));
            factura.setValorOtrosConceptos6(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS6));

            factura.setDescOtrosConceptos7(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS7));
            factura.setValorOtrosConceptos7(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS7));

            factura.setDescOtrosConceptos8(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS8));
            factura.setValorOtrosConceptos8(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS8));

            factura.setDescOtrosConceptos9(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS9));
            factura.setValorOtrosConceptos9(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS9));

            factura.setDescOtrosConceptos10(registro.getAsString(FACT_DESC_OTROS_CONCEPTOS10));
            factura.setValorOtrosConceptos10(registro.getAsString(FACT_VALOR_OTROS_CONCEPTOS10));

            factura.setTituloSubOtrosConceptos(registro.getAsString(FACT_TITULO_SUB_OTROS_CONCEPTOS));
            factura.setValorSubOtrosConceptos(registro.getAsString(FACT_VALOR_SUB_OTROS_CONCEPTOS));

            factura.setDescripcionDescuento1(registro.getAsString(FACT_DESC_CONCEPTO_DESCUENTO1));
            factura.setValorDescuento1(registro.getAsString(FACT_VALOR_CONCEPTO_DESCUENTO1));

            factura.setDescripcionDescuento2(registro.getAsString(FACT_DESC_CONCEPTO_DESCUENTO2));
            factura.setValorDescuento2(registro.getAsString(FACT_VALOR_CONCEPTO_DESCUENTO2));

            factura.setDescripcionDescuento3(registro.getAsString(FACT_DESC_CONCEPTO_DESCUENTO3));
            factura.setValorDescuento3(registro.getAsString(FACT_VALOR_CONCEPTO_DESCUENTO3));

            factura.setDescripcionDescuento4(registro.getAsString(FACT_DESC_CONCEPTO_DESCUENTO4));
            factura.setValorDescuento4(registro.getAsString(FACT_VALOR_CONCEPTO_DESCUENTO4));

            factura.setDescripcionDescuento5(registro.getAsString(FACT_DESC_CONCEPTO_DESCUENTO5));
            factura.setValorDescuento5(registro.getAsString(FACT_VALOR_CONCEPTO_DESCUENTO5));


            factura.setTituloValorDescuentos(registro.getAsString(FACT_TITULO_VALOR_DESCUENTOS));
            factura.setValorTotalDescuentos(registro.getAsString(FACT_VALOR_TOTAL_DESCUENTOS));
            factura.setTituloSubConceptosEnergia(registro.getAsString(FACT_TITULO_SUB_CONCEPTOS_ENERGIA));
            factura.setValorConceptosEnergia(registro.getAsString(FACT_VALOR_CONCEPTOS_ENERGIA));


            factura.setFechaPagoNormal(registro.getAsString(FACT_FECHA_PAGO_NORMAL));
            factura.setFechaCorte(registro.getAsString(FACT_FECHA_CORTE));

            factura.setValorTotalFactura(registro.getAsString(FACT_VALOR_TOTAL_FACTURA));
            factura.setNombreGerente(registro.getAsString(FACT_TEXTO_NOMBRE_GERENTE));
            factura.setMensaje(registro.getAsString(FACT_TEXTO_MENSAJE));
            factura.setCodigoBarras(registro.getAsString(FACT_TEXTO_CODIGO_BARRAS));

            factura.setDescConceptoExt1(registro.getAsString(FACT_DESC_CONCEPTO_EXT1));
            factura.setValorActualConceptoExt1(registro.getAsString(FACT_VALOR_ACTUAL_CONCEPTO_EXT1));
            factura.setInteresConceptoExt1(registro.getAsString(FACT_INTERES_MORA_CONCEPTO_EXT1));
            factura.setValorCuotaConceptoExt1(registro.getAsString(FACT_VALOR_CUOTA_MES_CONCEPTO_EXT1));

            factura.setDescConceptoExt2(registro.getAsString(FACT_DESC_CONCEPTO_EXT2));
            factura.setValorActualConceptoExt2(registro.getAsString(FACT_VALOR_ACTUAL_CONCEPTO_EXT2));
            factura.setInteresConceptoExt2(registro.getAsString(FACT_INTERES_MORA_CONCEPTO_EXT2));
            factura.setValorCuotaConceptoExt2(registro.getAsString(FACT_VALOR_CUOTA_MES_CONCEPTO_EXT2));

            factura.setDescConceptoExt3(registro.getAsString(FACT_DESC_CONCEPTO_EXT3));
            factura.setValorActualConceptoExt3(registro.getAsString(FACT_VALOR_ACTUAL_CONCEPTO_EXT3));
            factura.setInteresConceptoExt3(registro.getAsString(FACT_INTERES_MORA_CONCEPTO_EXT3));
            factura.setValorCuotaConceptoExt3(registro.getAsString(FACT_VALOR_CUOTA_MES_CONCEPTO_EXT3));

            factura.setDescConceptoExt4(registro.getAsString(FACT_DESC_CONCEPTO_EXT4));
            factura.setValorActualConceptoExt4(registro.getAsString(FACT_VALOR_ACTUAL_CONCEPTO_EXT4));
            factura.setInteresConceptoExt4(registro.getAsString(FACT_INTERES_MORA_CONCEPTO_EXT4));
            factura.setValorCuotaConceptoExt4(registro.getAsString(FACT_VALOR_CUOTA_MES_CONCEPTO_EXT4));

            factura.setValorTotalConceptoExt(registro.getAsString(FACT_VALOR_TOTAL_CONCEPTOS_EXT));

            factura.setNivelTension(registro.getAsString(FACT_NIVEL_TENSION));
            factura.setSecuencia(registro.getAsString(FACT_SECUENCIA));
            factura.setTituloValorNeto(registro.getAsString(FACT_TITULO_VALOR_NETO));

            factura.setValorNeto(registro.getAsString(FACT_VALOR_NETO));
            factura.setTituloOtrosValores(registro.getAsString(FACT_TITULO_OTROS_VALORES));
            factura.setTituloDescuentos(registro.getAsString(FACT_TITULO_DESCUENTOS));
            factura.setFechaEmision(registro.getAsString(FACT_FECHA_EMISION));
            factura.setUltimoPago(registro.getAsString(FACT_ULTIMO_PAGO));
            factura.setPeriodoFacturado(registro.getAsString(FACT_PERIODO_FACTURADO));

            factura.setValorMinConsumo(registro.getAsString(FACT_VALOR_MIN_CONSUMO));
            factura.setValorMaxConsumo(registro.getAsString(FACT_VALOR_MAX_CONSUMO));
            factura.setValorFoes(registro.getAsString(FACT_VALOR_FOES));
            factura.setConsumoKWHFoes(registro.getAsString(FACT_CONSUMO_KWH_FOES));
            factura.setValorUnitarioFoes(registro.getAsString(FACT_VALOR_UNITARIO_FOES));
            factura.setFacturaFoes(registro.getAsString(FACT_FACTURA_FOES));
            factura.setFacturaCobroBio(registro.getAsString(FACT_FACTURA_COBRO_BIO));
            factura.setCodigoBio(registro.getAsString(FACT_CODIGO_BIO));
            factura.setUltFactura1Bio(registro.getAsString(FACT_ULTIMA_FACTURA1_BIO));
            factura.setUltFactura2Bio(registro.getAsString(FACT_ULTIMA_FACTURA2_BIO));
            factura.setUltFactura3Bio(registro.getAsString(FACT_ULTIMA_FACTURA3_BIO));
            factura.setUltFactura4Bio(registro.getAsString(FACT_ULTIMA_FACTURA4_BIO));
            factura.setUltFactura5Bio(registro.getAsString(FACT_ULTIMA_FACTURA5_BIO));
            factura.setUltFactura6Bio(registro.getAsString(FACT_ULTIMA_FACTURA6_BIO));

            factura.setTipoUsoBio(registro.getAsString(FACT_TIPO_USO_BIO));
            factura.setTasaMoraBio(registro.getAsString(FACT_TASA_MORATORIA_BIO));
            factura.setFactVencidasBio(registro.getAsString(FACT_FACTURAS_VENCIDAS_BIO));
            factura.setEstratoBio(registro.getAsString(FACT_ESTRATO_BIO));
            factura.setCategoriaBio(registro.getAsString(FACT_CATEGORIA_BIO));
            factura.setMtsCubicosBio(registro.getAsString(FACT_METROS_CUBICOS_BIO));
            factura.setFrBarrioBio(registro.getAsString(FACT_FR_BARRIO_BIO));
            factura.setFrRecoleccionBio(registro.getAsString(FACT_FR_RECOLECCION_BIO));

            factura.setPorcSubsidioBio(registro.getAsString(FACT_PORCENTAJE_SUBSIDIO_BIO));
            factura.setPorcContribucionBio(registro.getAsString(FACT_PORCENTAJE_CONTRIB_BIO));

            factura.setConcepto1Bio(registro.getAsString(FACT_CONCEPTO1_BIO));
            factura.setValor1Bio(registro.getAsString(FACT_VALOR1_BIO));

            factura.setConcepto2Bio(registro.getAsString(FACT_CONCEPTO2_BIO));
            factura.setValor2Bio(registro.getAsString(FACT_VALOR2_BIO));

            factura.setConcepto3Bio(registro.getAsString(FACT_CONCEPTO3_BIO));
            factura.setValor3Bio(registro.getAsString(FACT_VALOR3_BIO));

            factura.setConcepto4Bio(registro.getAsString(FACT_CONCEPTO4_BIO));
            factura.setValor4Bio(registro.getAsString(FACT_VALOR4_BIO));

            factura.setConcepto5Bio(registro.getAsString(FACT_CONCEPTO5_BIO));
            factura.setValor5Bio(registro.getAsString(FACT_VALOR5_BIO));

            factura.setConcepto6Bio(registro.getAsString(FACT_CONCEPTO6_BIO));
            factura.setValor6Bio(registro.getAsString(FACT_VALOR6_BIO));

            factura.setConcepto7Bio(registro.getAsString(FACT_CONCEPTO7_BIO));
            factura.setValor7Bio(registro.getAsString(FACT_VALOR7_BIO));

            factura.setConcepto8Bio(registro.getAsString(FACT_CONCEPTO8_BIO));
            factura.setValor8Bio(registro.getAsString(FACT_VALOR8_BIO));

            factura.setConcepto9Bio(registro.getAsString(FACT_CONCEPTO9_BIO));
            factura.setValor9Bio(registro.getAsString(FACT_VALOR9_BIO));

            factura.setConcepto10Bio(registro.getAsString(FACT_CONCEPTO10_BIO));
            factura.setValor10Bio(registro.getAsString(FACT_VALOR10_BIO));


            factura.setValorTotalBio(registro.getAsString(FACT_VALOR_TOTAL_BIO));
            factura.setConceptoFinanciadoBio(registro.getAsString(FACT_CONCEPTO_FINANCIADO_BIO));
            factura.setNumCuotasBio(registro.getAsString(FACT_NUM_CUOTAS_BIO));
            factura.setValorCuotaBio(registro.getAsString(FACT_VALOR_CUOTA_BIO));
            factura.setDescripcionBio(registro.getAsString(FACT_DESCRIPCION_BIO));
            factura.setNumCuotaMesConcepto1Bio(registro.getAsString(FACT_NUM_CUOTA_MES_CONCEPTO_EXT1));
            factura.setNumCuotaMesConcepto2Bio(registro.getAsString(FACT_NUM_CUOTA_MES_CONCEPTO_EXT2));
            factura.setNumCuotaMesConcepto3Bio(registro.getAsString(FACT_NUM_CUOTA_MES_CONCEPTO_EXT3));
            factura.setNumCuotaMesConcepto4Bio(registro.getAsString(FACT_NUM_CUOTA_MES_CONCEPTO_EXT4));

        }else
        {
            factura = new FacturaVO(null, null, null, null, -1);
        }

        return factura;
    }
}
