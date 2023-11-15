package org.informatorio.CuentaBancaria;

import org.informatorio.Cliente.Cliente;

public abstract class CuentaBancaria implements OperacionesBancarias {
    protected String numeroCuenta;
    protected Cliente titular;
    protected double saldo;

    // Constructor
    public CuentaBancaria(String numeroCuenta, Cliente titular, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
    }

    // Métodos abstractos
    public abstract void depositar(double monto);
    public abstract void retirar(double monto);

    // Getters y setters
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

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public double consultarSaldo() {
        return this.saldo;
    }
}
