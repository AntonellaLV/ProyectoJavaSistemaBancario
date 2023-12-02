package org.informatorio.ExportadorServicio;

import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.ExportadorCSV.IExportadorDatos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorServicio {
    private IExportadorDatos exportador;

    public ExportadorServicio(IExportadorDatos exportador) {
        this.exportador = exportador;
    }

    public void exportarDatos(List<CuentaBancaria> cuentas, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            // Encabezado del CSV
            writer.append("NumeroCuenta,Cliente,Saldo,Tipo\n");

            // Iterar sobre cada cuenta y escribir sus datos
            for (CuentaBancaria cuenta : cuentas) {
                writer.append(cuenta.getNumeroCuenta())
                        .append(",")
                        .append(cuenta.getTitular().getNombre())
                        .append(",")
                        .append(String.valueOf(cuenta.getSaldo()))
                        .append(",")
                        .append(cuenta.getTipo())
                        .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al exportar datos: " + e.getMessage());
        }
    }
}
