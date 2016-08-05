package com.example.cromero.altacliente.responseClientService;

/**
 * Created by christian.romero on 05/08/2016.
 */
public class OperacionResponse {
    private  int codRetorno;
    private String mensaje;
    private String id;

    public int getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(int codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
