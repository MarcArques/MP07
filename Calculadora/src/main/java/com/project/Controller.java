package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField textResult;

    @FXML
    private TextField textOperation;

    private Calculadora calculadora = new Calculadora();
    private boolean nuevaOperacion = true;
    private boolean operacionSeleccionada = false;
    private boolean resultadoParcial = false;

    private String operacionActual = "";

    @FXML
    private void addNum(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String num = boton.getText();

        if (nuevaOperacion) {
            textResult.setText(num);
            nuevaOperacion = false;
        } else {
            textResult.setText(textResult.getText() + num);
        }
        operacionSeleccionada = false;
        operacionActual += num;
        textOperation.setText(operacionActual);
    }

    @FXML
    private void addOperation(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String operacion = boton.getText();

        if (!operacionSeleccionada) {
            if (resultadoParcial) {
                calculadora.setNumero2(Double.parseDouble(textResult.getText().replace(",", ".")));
                double resultado = calculadora.calcular();
                textResult.setText(String.valueOf(resultado).replace(".", ","));
                calculadora.setNumero1(resultado);
            } else {
                calculadora.setNumero1(Double.parseDouble(textResult.getText().replace(",", ".")));
                resultadoParcial = true;
            }

            calculadora.setOperacion(operacion);
            nuevaOperacion = true;
        }
        operacionSeleccionada = true;
        operacionActual += " " + operacion + " ";
        textOperation.setText(operacionActual);
    }

    @FXML
    private void calcularResultado(ActionEvent event) {
        if (resultadoParcial && !operacionSeleccionada) {
            calculadora.setNumero2(Double.parseDouble(textResult.getText().replace(",", ".")));

            try {
                double resultado = calculadora.calcular();
                textResult.setText(String.valueOf(resultado).replace(".", ","));
            } catch (ArithmeticException e) {
                textResult.setText("Error");
            }

            nuevaOperacion = true;
            operacionSeleccionada = true;
            resultadoParcial = false;
            operacionActual = "";
            textOperation.setText("");
        }
    }

    @FXML
    private void clearScreen(ActionEvent event) {
        textResult.setText("0");
        textOperation.setText("");
        operacionActual = "";
        nuevaOperacion = true;
        operacionSeleccionada = false;
        resultadoParcial = false;
    }

    @FXML
    private void addDecimal(ActionEvent event) {
        if (!textResult.getText().contains(",")) {
            textResult.setText(textResult.getText() + ",");
            operacionActual += ",";
            textOperation.setText(operacionActual);
        }
    }
}