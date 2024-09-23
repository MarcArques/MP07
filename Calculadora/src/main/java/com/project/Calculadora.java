package com.project;

public class Calculadora {
    private double numero1 = 0;
    private double numero2 = 0;
    private String operacion = "";
    
    public void setNumero1(double numero) {
        this.numero1 = numero;
    }
    
    public void setNumero2(double numero) {
        this.numero2 = numero;
    }
    
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double calcular() throws ArithmeticException {
        switch (operacion) {
            case "+":
                return numero1 + numero2;
            case "-":
                return numero1 - numero2;
            case "x":
                return numero1 * numero2;
            case "/":
                if (numero2 == 0) throw new ArithmeticException("División por cero");
                return numero1 / numero2;
            default:
                return 0;
        }
    }
}