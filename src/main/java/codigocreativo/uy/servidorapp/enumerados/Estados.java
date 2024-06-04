package codigocreativo.uy.servidorapp.enumerados;

import java.io.Serializable;

public enum Estados implements Serializable {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    SIN_VALIDAR("Sin validar");

    private String valor;

    Estados(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
