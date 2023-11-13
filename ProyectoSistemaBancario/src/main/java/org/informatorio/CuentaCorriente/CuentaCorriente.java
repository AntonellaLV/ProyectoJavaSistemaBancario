package org.informatorio.CuentaCorriente;

import org.informatorio.Cliente.Cliente;
import org.informatorio.CuentaBancaria.CuentaBancaria;

public class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro;

    public CuentaCorriente(String numeroCuenta, Cliente titular, double limiteSobregiro) {
        super(numeroCuenta, titular);
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (monto > saldo + limiteSobregiro) {
            throw new Exception("Límite de sobregiro excedido.");
        }
        saldo -= monto;
    }

// Getters y Setters
public double getLimiteSobregiro() {
    return limiteSobregiro;
}

    public void setLimiteSobregiro(double limiteSobregiro) {
        this.limiteSobregiro = limiteSobregiro;
    }
}

