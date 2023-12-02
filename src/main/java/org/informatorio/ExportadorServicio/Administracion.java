package org.informatorio.ExportadorServicio;

import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancariaDAO.CuentaBancariaDAO;

import java.util.List;

public class Administracion {
    private ExportadorServicio exportadorServicio;
    private CuentaBancariaDAO cuentaBancariaDAO;

    public Administracion(ExportadorServicio exportadorServicio, CuentaBancariaDAO cuentaBancariaDAO) {
        this.exportadorServicio = exportadorServicio;
        this.cuentaBancariaDAO = cuentaBancariaDAO;
    }

    public void exportarReporteCuentas() {
        List<CuentaBancaria> cuentas = cuentaBancariaDAO.obtenerTodasLasCuentas();
        String rutaArchivo = "reporte_cuentas.csv";
        exportadorServicio.exportarDatos(cuentas, rutaArchivo);
        System.out.println("Reporte de cuentas exportado a " + rutaArchivo);
    }
}
