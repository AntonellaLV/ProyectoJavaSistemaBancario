package org.informatorio;

import org.informatorio.ClienteServicio.ClienteServicio;
import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaBancariaServicio.CuentaBancariaServicio;
import org.informatorio.ExportadorCSV.ExportadorCSV;
import org.informatorio.ExportadorCSV.IExportadorDatos;
import org.informatorio.Menu.MenuPrincipal;
import org.informatorio.ClienteDAO.ClienteDAO;
import org.informatorio.CuentaBancariaDAO.CuentaBancariaDAO;
import org.informatorio.DatabaseSetup.DatabaseSetup;
import org.informatorio.ExportadorServicio.ExportadorServicio;
import org.informatorio.ExportadorServicio.Administracion;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        // Inicializar la base de datos
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            DatabaseSetup.ejecutarScript(conexion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos o al inicializarla.");
            return;
        }

        // Crear instancias de DAOs
        ClienteDAO clienteDAO = new ClienteDAO();
        CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO();

        // Crear instancias de servicios y pasarles los DAOs
        ClienteServicio clienteServicio = new ClienteServicio(clienteDAO);
        CuentaBancariaServicio cuentaBancariaServicio = new CuentaBancariaServicio(cuentaBancariaDAO);

        // Crear instancia para exportación de datos
        IExportadorDatos exportadorCSV = new ExportadorCSV();
        ExportadorServicio exportadorServicio = new ExportadorServicio(exportadorCSV);

        // Instancia de Administración para manejar exportaciones
        Administracion administracion = new Administracion(exportadorServicio, cuentaBancariaDAO);

        // Lanzar el menú principal pasando los servicios y la administración
        MenuPrincipal menuPrincipal = new MenuPrincipal(clienteServicio, cuentaBancariaServicio, administracion);
        menuPrincipal.mostrar();
    }
}
