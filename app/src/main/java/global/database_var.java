package global;

import android.os.Environment;

import java.io.File;

/**
 * Created by Julian Poveda on 11/05/2016.
 */
public interface database_var {
    public static String name_database      = "TomaLecturasBD";

    /**
     * Definicion de los campos de la tabla con la informacion de facturas de los clientes
     */

    public static String  FACT_MES                  = "mes";
    public static String  FACT_ANNO                 = "anno";
    public static String  FACT_CICLO_CARGUE         = "ciclo_cargue";
    public static String  FACT_CODIGO               = "codigo";
    public static String  FACT_NOMBRE               = "nombre";
    public static String  FACT_PERIODO              = "periodo_fact";
    public static String  FACT_MUNICIPIO            = "municipio";
    public static String  FACT_DIRECCION            = "direccion_entega";
    public static String  FACT_ESTRATO              = "estrato";
    public static String  FACT_CLASE_SERVICIO       = "clase_servicio";
    public static String  FACT_CICLO                = "ciclo";
    public static String  FACT_UBICACION            = "ubicacion";
    public static String  FACT_ESTADO_PREDIO        = "estado_predio";
    public static String  FACT_ACTIVIDAD_CIU        = "actividad_ciu";
    public static String  FACT_RUTA                 = "ruta";
    public static String  FACT_NUM_FACTURA          = "num_factura";
    public static String  FACT_PERIODOS_VENCIDOS    = "periodos_vencidos";

    public static String  FACT_HIST_DESCRIPCION_M1  = "historico_descripcion_m1";
    public static String  FACT_HIST_CONSUMO_M1      = "historico_consumo_m1";
    public static String  FACT_HIST_DESCRIPCION_M2  = "historico_descripcion_m2";
    public static String  FACT_HIST_CONSUMO_M2      = "historico_consumo_m2";
    public static String  FACT_HIST_DESCRIPCION_M3  = "historico_descripcion_m3";
    public static String  FACT_HIST_CONSUMO_M3      = "historico_consumo_m3";
    public static String  FACT_HIST_DESCRIPCION_M4  = "historico_descripcion_m4";
    public static String  FACT_HIST_CONSUMO_M4      = "historico_consumo_m4";
    public static String  FACT_HIST_DESCRIPCION_M5  = "historico_descripcion_m5";
    public static String  FACT_HIST_CONSUMO_M5      = "historico_consumo_m5";
    public static String  FACT_HIST_DESCRIPCION_M6  = "historico_descripcion_m6";
    public static String  FACT_HIST_CONSUMO_M6      = "historico_consumo_m6";

    public static String  FACT_MEDIDA1              = "medida1";
    public static String  FACT_LECT_ANTERIOR_M1     = "lectura_anterior_medida1";
    public static String  FACT_LECT_TOMADA_M1       = "lectura_tomada_medida1";
    public static String  FACT_TIPO_OBSERVACION_M1  = "tipo_observacion_medida1";
    public static String  FACT_TIPO_CONSUMO_M1      = "tipo_consumo_medida1";

    public static String  FACT_MEDIDA2              = "medida2";
    public static String  FACT_LECT_ANTERIOR_M2     = "lectura_anterior_medida2";
    public static String  FACT_LECT_TOMADA_M2       = "lectura_tomada_medida2";
    public static String  FACT_TIPO_OBSERVACION_M2  = "tipo_observacion_medida2";
    public static String  FACT_TIPO_CONSUMO_M2      = "tipo_consumo_medida2";

    public static String  FACT_MEDIDA3              = "medida3";
    public static String  FACT_LECT_ANTERIOR_M3     = "lectura_anterior_medida3";
    public static String  FACT_LECT_TOMADA_M3       = "lectura_tomada_medida3";
    public static String  FACT_TIPO_OBSERVACION_M3  = "tipo_observacion_medida3";
    public static String  FACT_TIPO_CONSUMO_M3      = "tipo_consumo_medida3";

    public static String  FACT_MEDIDA4              = "medida4";
    public static String  FACT_LECT_ANTERIOR_M4     = "lectura_anterior_medida4";
    public static String  FACT_LECT_TOMADA_M4       = "lectura_tomada_medida4";
    public static String  FACT_TIPO_OBSERVACION_M4  = "tipo_observacion_medida4";
    public static String  FACT_TIPO_CONSUMO_M4      = "tipo_consumo_medida4";

    public static String  FACT_MEDIDA5              = "medida5";
    public static String  FACT_LECT_ANTERIOR_M5     = "lectura_anterior_medida5";
    public static String  FACT_LECT_TOMADA_M5       = "lectura_tomada_medida5";
    public static String  FACT_TIPO_OBSERVACION_M5  = "tipo_observacion_medida5";
    public static String  FACT_TIPO_CONSUMO_M5      = "tipo_consumo_medida5";

    public static String  FACT_PROMEDIO_CUENTA      = "promedio_cuenta";

    public static String  FACT_MARCA_CONTADOR1      = "marca_contador1";
    public static String  FACT_SERIE_CONTADOR1      = "serie_contador1";
    public static String  FACT_FACTOR1              = "factor1";
    public static String  FACT_TRANSFORMADOR1       = "transformador1";

    public static String  FACT_MARCA_CONTADOR2      = "marca_contador2";
    public static String  FACT_SERIE_CONTADOR2      = "serie_contador2";
    public static String  FACT_FACTOR2              = "factor2";
    public static String  FACT_TRANSFORMADOR2       = "transformador2";

    public static String  FACT_NOMBRE_NODO_GRUPO    = "nombre_nodo_grupo";
    public static String  FACT_NODO                 = "nodo";
    public static String  FACT_ACUMULADO_FES        = "acumulado_fes";
    public static String  FACT_ACUMULADO_DES        = "acumulado_des";
    public static String  FACT_META_FES             = "meta_fes";
    public static String  FACT_META_DES             = "meta_des";

    public static String  FACT_COSTO_GENERACION     = "costo_generacion";
    public static String  FACT_COSTO_TRANSMISION    = "costo_transmision";
    public static String  FACT_COSTO_DISTRIBUCION   = "costo_distribucion";
    public static String  FACT_COSTO_PERDIDAS       = "costo_perdidas";
    public static String  FACT_OTROS_COSTOS         = "otros_costos";
    public static String  FACT_COSTO_COMERCIALIZACION   = "costos_comercializacion";
    public static String  FACT_COSTO_TOTAL          = "costo_total";
    public static String  FACT_VALOR_FINANCIACION   = "valor_financiacion";
    public static String  FACT_SALDO_FINANCIACION   = "saldo_financiacion";

    public static String  FACT_INTERES              = "interes";
    public static String  FACT_CUOTA_ACTUAL         = "cuota_actual";
    public static String  FACT_CUOTAS_PENDIENTES    = "cuotas_pendientes";
    public static String  FACT_NUMERO_CUOTA         = "numero_cuotas";
    public static String  FACT_VALOR_CUOTA          = "valor_cuota";
    public static String  FACT_SALDO_FAVOR          = "valor_saldo_a_favor";
    public static String  FACT_SALDO_RECLAMACION    = "valor_en_reclamacion";
    public static String  FACT_TITULO_LIQUI_ENERGIA = "titulo_liquidacion_energia";

    public static String  FACT_LIQUI_TARIFA1        = "liquidacion_tarifa1";
    public static String  FACT_VALOR_LIQUI_TARIFA1  = "valor_liquidacion_tarifa1";

    public static String  FACT_LIQUI_TARIFA2        = "liquidacion_tarifa2";
    public static String  FACT_VALOR_LIQUI_TARIFA2  = "valor_liquidacion_tarifa2";

    public static String  FACT_LIQUI_TARIFA3        = "liquidacion_tarifa3";
    public static String  FACT_VALOR_LIQUI_TARIFA3  = "valor_liquidacion_tarifa3";

    public static String  FACT_LIQUI_TARIFA4        = "liquidacion_tarifa4";
    public static String  FACT_VALOR_LIQUI_TARIFA4  = "valor_liquidacion_tarifa4";

    public static String  FACT_LIQUI_TARIFA5        = "liquidacion_tarifa5";
    public static String  FACT_VALOR_LIQUI_TARIFA5  = "valor_liquidacion_tarifa5";

    public static String  FACT_LIQUI_TARIFA6        = "liquidacion_tarifa6";
    public static String  FACT_VALOR_LIQUI_TARIFA6  = "valor_liquidacion_tarifa6";

    public static String  FACT_PORCENTAJE_SUBSUDIO          = "porcentaje_subsidio";
    public static String  FACT_TITULO_VALOR_SUBSIDIO        = "titulo_valor_subsidio";
    public static String  FACT_VALOR_SUBSIDIO               = "valor_subsidio";
    public static String  FACT_TITULO_VALOR_FINAL_CONSUMO   = "titulo_valor_final_consumo";
    public static String  FACT_VALOR_TOTAL_CONSUMO          = "valor_total_consumo";

    public static String  FACT_DESC_OTROS_CONCEPTOS1        = "descripcion_otros_conceptos1";
    public static String  FACT_VALOR_OTROS_CONCEPTOS1       = "valor_otros_conceptos1";

    public static String  FACT_DESC_OTROS_CONCEPTOS2        = "descripcion_otros_conceptos2";
    public static String  FACT_VALOR_OTROS_CONCEPTOS2       = "valor_otros_conceptos2";

    public static String  FACT_DESC_OTROS_CONCEPTOS3        = "descripcion_otros_conceptos3";
    public static String  FACT_VALOR_OTROS_CONCEPTOS3       = "valor_otros_conceptos3";

    public static String  FACT_DESC_OTROS_CONCEPTOS4        = "descripcion_otros_conceptos4";
    public static String  FACT_VALOR_OTROS_CONCEPTOS4       = "valor_otros_conceptos4";

    public static String  FACT_DESC_OTROS_CONCEPTOS5        = "descripcion_otros_conceptos5";
    public static String  FACT_VALOR_OTROS_CONCEPTOS5       = "valor_otros_conceptos5";

    public static String  FACT_DESC_OTROS_CONCEPTOS6        = "descripcion_otros_conceptos6";
    public static String  FACT_VALOR_OTROS_CONCEPTOS6       = "valor_otros_conceptos6";

    public static String  FACT_DESC_OTROS_CONCEPTOS7        = "descripcion_otros_conceptos7";
    public static String  FACT_VALOR_OTROS_CONCEPTOS7       = "valor_otros_conceptos7";

    public static String  FACT_DESC_OTROS_CONCEPTOS8        = "descripcion_otros_conceptos8";
    public static String  FACT_VALOR_OTROS_CONCEPTOS8       = "valor_otros_conceptos8";

    public static String  FACT_DESC_OTROS_CONCEPTOS9        = "descripcion_otros_conceptos9";
    public static String  FACT_VALOR_OTROS_CONCEPTOS9       = "valor_otros_conceptos9";

    public static String  FACT_DESC_OTROS_CONCEPTOS10       = "descripcion_otros_conceptos10";
    public static String  FACT_VALOR_OTROS_CONCEPTOS10      = "valor_otros_conceptos10";

    public static String  FACT_TITULO_SUB_OTROS_CONCEPTOS   = "titulo_subtotal_otros_conceptos";
    public static String  FACT_VALOR_SUB_OTROS_CONCEPTOS    = "valor_subtotal_otros_conceptos";

    public static String  FACT_DESC_CONCEPTO_DESCUENTO1     = "descripcion_conceptos_descuentos1";
    public static String  FACT_VALOR_CONCEPTO_DESCUENTO1    = "valor_conceptos_descuentos1";

    public static String  FACT_DESC_CONCEPTO_DESCUENTO2     = "descripcion_conceptos_descuentos2";
    public static String  FACT_VALOR_CONCEPTO_DESCUENTO2    = "valor_conceptos_descuentos2";

    public static String  FACT_DESC_CONCEPTO_DESCUENTO3     = "descripcion_conceptos_descuentos3";
    public static String  FACT_VALOR_CONCEPTO_DESCUENTO3    = "valor_conceptos_descuentos3";

    public static String  FACT_DESC_CONCEPTO_DESCUENTO4     = "descripcion_conceptos_descuentos4";
    public static String  FACT_VALOR_CONCEPTO_DESCUENTO4    = "valor_conceptos_descuentos4";

    public static String  FACT_DESC_CONCEPTO_DESCUENTO5     = "descripcion_conceptos_descuentos5";
    public static String  FACT_VALOR_CONCEPTO_DESCUENTO5    = "valor_conceptos_descuentos5";

    public static String  FACT_TITULO_VALOR_DESCUENTOS      = "titulo_valor_descuentos";
    public static String  FACT_VALOR_TOTAL_DESCUENTOS       = "valor_total_descuentos";
    public static String  FACT_TITULO_SUB_CONCEPTOS_ENERGIA = "titulo_subtotal_conceptos_energia";
    public static String  FACT_VALOR_CONCEPTOS_ENERGIA      = "valor_conceptos_energia";

    public static String  FACT_FECHA_PAGO_NORMAL            = "fecha_pago_normal";
    public static String  FACT_FECHA_CORTE                  = "fecha_corte";

    public static String  FACT_VALOR_TOTAL_FACTURA          = "valor_total_factura";
    public static String  FACT_TEXTO_NOMBRE_GERENTE         = "texto_nombre_gerente";
    public static String  FACT_TEXTO_MENSAJE                = "texto_mesaje";
    public static String  FACT_TEXTO_CODIGO_BARRAS          = "texto_codigo_barras";

    public static String  FACT_DESC_CONCEPTO_EXT1           = "descripcion_concepto_externo1";
    public static String  FACT_VALOR_ACTUAL_CONCEPTO_EXT1   = "valor_actual_concepto_externo1";
    public static String  FACT_INTERES_MORA_CONCEPTO_EXT1   = "interes_mora_concepto_externo1";
    public static String  FACT_VALOR_CUOTA_MES_CONCEPTO_EXT1= "valor_cuota_mes_concepto_externo1";

    public static String  FACT_DESC_CONCEPTO_EXT2           = "descripcion_concepto_externo2";
    public static String  FACT_VALOR_ACTUAL_CONCEPTO_EXT2   = "valor_actual_concepto_externo2";
    public static String  FACT_INTERES_MORA_CONCEPTO_EXT2   = "interes_mora_concepto_externo2";
    public static String  FACT_VALOR_CUOTA_MES_CONCEPTO_EXT2= "valor_cuota_mes_concepto_externo2";

    public static String  FACT_DESC_CONCEPTO_EXT3           = "descripcion_concepto_externo3";
    public static String  FACT_VALOR_ACTUAL_CONCEPTO_EXT3   = "valor_actual_concepto_externo3";
    public static String  FACT_INTERES_MORA_CONCEPTO_EXT3   = "interes_mora_concepto_externo3";
    public static String  FACT_VALOR_CUOTA_MES_CONCEPTO_EXT3= "valor_cuota_mes_concepto_externo3";

    public static String  FACT_DESC_CONCEPTO_EXT4           = "descripcion_concepto_externo4";
    public static String  FACT_VALOR_ACTUAL_CONCEPTO_EXT4   = "valor_actual_concepto_externo4";
    public static String  FACT_INTERES_MORA_CONCEPTO_EXT4   = "interes_mora_concepto_externo4";
    public static String  FACT_VALOR_CUOTA_MES_CONCEPTO_EXT4= "valor_cuota_mes_concepto_externo4";

    public static String  FACT_VALOR_TOTAL_CONCEPTOS_EXT    = "valor_total_conceptos_externos";

    public static String  FACT_NIVEL_TENSION                = "nivel_de_tension";
    public static String  FACT_SECUENCIA                    = "secuencia";
    public static String  FACT_TITULO_VALOR_NETO            = "titulo_valor_neto";
    public static String  FACT_VALOR_NETO                   = "valor_neto";
    public static String  FACT_TITULO_OTROS_VALORES         = "titulo_otros_valores";
    public static String  FACT_TITULO_DESCUENTOS            = "titulo_descuentos";
    public static String  FACT_FECHA_EMISION                = "fecha_emision";
    public static String  FACT_ULTIMO_PAGO                  = "ultimo_pago";
    public static String  FACT_PERIODO_FACTURADO            = "periodo_facturado";

    public static String  FACT_VALOR_MIN_CONSUMO            = "valor_minimo_consumo";
    public static String  FACT_VALOR_MAX_CONSUMO            = "valor_maximo_consumo";
    public static String  FACT_VALOR_FOES                   = "valor_foes";
    public static String  FACT_CONSUMO_KWH_FOES             = "consumo_kwh_foes";
    public static String  FACT_VALOR_UNITARIO_FOES          = "valor_unitario_foes";
    public static String  FACT_FACTURA_FOES                 = "factura_foes";
    public static String  FACT_FACTURA_COBRO_BIO            = "factura_cobro_bio";
    public static String  FACT_CODIGO_BIO                   = "codigo_bio";
    public static String  FACT_ULTIMA_FACTURA1_BIO          = "ult_factura1_bio";
    public static String  FACT_ULTIMA_FACTURA2_BIO          = "ult_factura2_bio";
    public static String  FACT_ULTIMA_FACTURA3_BIO          = "ult_factura3_bio";
    public static String  FACT_ULTIMA_FACTURA4_BIO          = "ult_factura4_bio";
    public static String  FACT_ULTIMA_FACTURA5_BIO          = "ult_factura5_bio";
    public static String  FACT_ULTIMA_FACTURA6_BIO          = "ult_factura6_bio";

    public static String  FACT_TIPO_USO_BIO                 = "tipo_uso_bio";
    public static String  FACT_TASA_MORATORIA_BIO           = "tasa_moratoria_bio";
    public static String  FACT_FACTURAS_VENCIDAS_BIO        = "facturas_vencidas_bio";
    public static String  FACT_ESTRATO_BIO                  = "estrato_bio";
    public static String  FACT_CATEGORIA_BIO                = "categoria_bio";
    public static String  FACT_METROS_CUBICOS_BIO           = "metros_cubicos_bio";
    public static String  FACT_FR_BARRIO_BIO                = "fr_barrio_bio";
    public static String  FACT_FR_RECOLECCION_BIO           = "fr_recoleccion_bio";

    public static String  FACT_PORCENTAJE_SUBSIDIO_BIO      = "porc_subsidio_bio";
    public static String  FACT_PORCENTAJE_CONTRIB_BIO       = "porc_contribucion_bio";

    public static String  FACT_CONCEPTO1_BIO                = "concepto1_bio";
    public static String  FACT_VALOR1_BIO                   = "valor1_bio";

    public static String  FACT_CONCEPTO2_BIO                = "concepto2_bio";
    public static String  FACT_VALOR2_BIO                   = "valor2_bio";

    public static String  FACT_CONCEPTO3_BIO                = "concepto3_bio";
    public static String  FACT_VALOR3_BIO                   = "valor3_bio";

    public static String  FACT_CONCEPTO4_BIO                = "concepto4_bio";
    public static String  FACT_VALOR4_BIO                   = "valor4_bio";

    public static String  FACT_CONCEPTO5_BIO                = "concepto5_bio";
    public static String  FACT_VALOR5_BIO                   = "valor5_bio";

    public static String  FACT_CONCEPTO6_BIO                = "concepto6_bio";
    public static String  FACT_VALOR6_BIO                   = "valor6_bio";

    public static String  FACT_CONCEPTO7_BIO                = "concepto7_bio";
    public static String  FACT_VALOR7_BIO                   = "valor7_bio";

    public static String  FACT_CONCEPTO8_BIO                = "concepto8_bio";
    public static String  FACT_VALOR8_BIO                   = "valor8_bio";

    public static String  FACT_CONCEPTO9_BIO                = "concepto9_bio";
    public static String  FACT_VALOR9_BIO                   = "valor9_bio";

    public static String  FACT_CONCEPTO10_BIO               = "concepto10_bio";
    public static String  FACT_VALOR10_BIO                  = "valor10_bio";

    public static String  FACT_VALOR_TOTAL_BIO              = "valor_total_bio";
    public static String  FACT_CONCEPTO_FINANCIADO_BIO      = "concepto_financiado_bio";
    public static String  FACT_NUM_CUOTAS_BIO               = "numero_cuotas_bio";
    public static String  FACT_VALOR_CUOTA_BIO              = "valor_cuota_bio";
    public static String  FACT_DESCRIPCION_BIO              = "descripcion_bio";
    public static String  FACT_NUM_CUOTA_MES_CONCEPTO_EXT1  = "num_cuotas_mes_concepto_externo1";
    public static String  FACT_NUM_CUOTA_MES_CONCEPTO_EXT2  = "num_cuotas_mes_concepto_externo2";
    public static String  FACT_NUM_CUOTA_MES_CONCEPTO_EXT3  = "num_cuotas_mes_concepto_externo3";
    public static String  FACT_NUM_CUOTA_MES_CONCEPTO_EXT4  = "num_cuotas_mes_concepto_externo4";

}
