package org.informatorio.CuentaBancaria;

import org.informatorio.Cliente.Cliente;

public abstract class CuentaBancaria {
    private String numeroCuenta;
    private Cliente titular;
    protected double saldo;

    public CuentaBancaria(String numeroCuenta, Cliente titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public abstract void depositar(double monto);

    public abstract void retirar(double monto) throws Exception;

    public double consultarSaldo() {
        return saldo;
    }

    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
