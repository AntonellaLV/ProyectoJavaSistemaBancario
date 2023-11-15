package org.informatorio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancaria.OperacionesBancarias;
import org.informatorio.ExportadorCSV.IExportadorDatos;
import org.informatorio.ExportadorCSV.ExportadorCSV;
import org.informatorio.CuentaCorriente.CuentaCorriente;
import org.informatorio.Menu.MenuPrincipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class App {
    public static void main(String[] args) {
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.mostrar();
    }
}


