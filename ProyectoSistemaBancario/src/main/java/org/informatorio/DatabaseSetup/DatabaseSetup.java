package org.informatorio.DatabaseSetup;

import java.sql.Connection;
import java.sql.Statement;


public class DatabaseSetup {

    public static void ejecutarScript(Connection conexion) {
        String scriptSQL =
                "CREATE TABLE IF NOT EXISTS Clientes ("
                        + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                        + "Nombre VARCHAR(255) NOT NULL, "
                        + "Direccion VARCHAR(255)); "

                        + "CREATE TABLE IF NOT EXISTS CuentasBancarias ("
                        + "NumeroCuenta VARCHAR(255) PRIMARY KEY, "
                        + "ClienteID INT, "
                        + "Saldo DECIMAL(10, 2) NOT NULL, "
                        + "Tipo VARCHAR(50) NOT NULL, "
                        + "TasaInteres DECIMAL(5, 2), " // NULL para cuentas corrientes
                        + "LimiteSobregiro DECIMAL(10, 2), " // NULL para cuentas de ahorro
                        + "FOREIGN KEY (ClienteID) REFERENCES Clientes(ID));";

        try (Statement statement = conexion.createStatement()) {
            statement.execute(scriptSQL);
            // Configuración adicional aquí.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

