package org.informatorio.CuentaAhorro;

import org.informatorio.Cliente.Cliente;
import org.informatorio.CuentaBancaria.CuentaBancaria;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String numeroCuenta, Cliente titular, double tasaInteres) {
        super(numeroCuenta, titular);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (monto > saldo) {
            throw new Exception("Fondos insuficientes.");
        }
        saldo -= monto;
    }

    public void calcularIntereses() {
        saldo += saldo * tasaInteres / 100;
    }

    // Getters y Setters
    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
}
