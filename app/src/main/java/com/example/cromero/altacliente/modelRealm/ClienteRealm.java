package com.example.cromero.altacliente.modelRealm;

import com.example.cromero.altacliente.modelClientService.Cliente;

import java.io.Serializable;
import java.math.BigInteger;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by christian.romero on 05/08/2016.
 */
public class ClienteRealm extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;
    private short empresaId;
    private short sucursalId;
    private long codigo;
    private String razonsocial;
    private String nombreComercial;
    private String telefono;
    private String ruc;
    private String direccion;
    private String fotoUrl;
    private String estado;
    private double lat;
    private double lng;


    public ClienteRealm() {

    }

    public ClienteRealm(Cliente c) {
        this.id = c.getId();
        this.empresaId = c.getEmpresaId();
        this.sucursalId = c.getSucursalId();
        this.codigo = c.getCodigo()!=null?c.getCodigo().longValue():0l;
        this.razonsocial = c.getRazonsocial();
        this.nombreComercial = c.getNombreComercial();
        this.telefono = c.getTelefono();
        this.ruc = c.getRuc();
        this.direccion = c.getDireccion();
        this.fotoUrl = c.getFotoUrl();
        this.estado = c.getEstado();
        this.lat = c.getLat();
        this.lng = c.getLng();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(short empresaId) {
        this.empresaId = empresaId;
    }

    public short getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(short sucursalId) {
        this.sucursalId = sucursalId;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
