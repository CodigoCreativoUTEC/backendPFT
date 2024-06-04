package codigocreativo.uy.servidorapp.enumerados;

import java.io.Serializable;

public enum Sectores implements Serializable {
    POLICLINICO("Policlínico"),
    INTERNACION("Internación"),
    EMERGENCIA("Emergencia"),
    CTI("CTI"),
    OTRO("Otro");

    private String valor;

    Sectores(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
