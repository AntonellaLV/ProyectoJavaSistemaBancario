package org.informatorio.Cliente;

import org.informatorio.CuentaBancaria.CuentaBancaria;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class Cliente {
    private String nombre;
    private String direccion;
    private List<CuentaBancaria> cuentas;

    // Constructor
    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaBancaria> cuentas) {
        this.cuentas = cuentas;
    }
}
