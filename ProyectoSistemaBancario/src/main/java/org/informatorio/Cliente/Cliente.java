package org.informatorio.Cliente;

import org.informatorio.CuentaBancaria.CuentaBancaria;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private String dirección;
    private List<CuentaBancaria> cuentas;

    public Cliente(String nombre, String dirección) {
        this.nombre = nombre;
        this.dirección = dirección;
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public void eliminarCuenta(CuentaBancaria cuenta) {
        cuentas.remove(cuenta);
    }

    public double consultarSaldoTotal() {
        return cuentas.stream().mapToDouble(CuentaBancaria::consultarSaldo).sum();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDireccion(String direccion) {
        this.dirección = direccion;
    }

    public CuentaBancaria[] getCuentas() {
        return new CuentaBancaria[0];
    }
}
