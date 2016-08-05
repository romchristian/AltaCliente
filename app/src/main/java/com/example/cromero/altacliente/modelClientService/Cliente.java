package com.example.cromero.altacliente.modelClientService;

import com.example.cromero.altacliente.modelRealm.ClienteRealm;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by christian.romero on 05/08/2016.
 */
public class Cliente implements Serializable{
    private Integer id;
    private Short empresaId;
    private Short sucursalId;
    private BigInteger codigo;
    private String razonsocial;
    private String nombreComercial;
    private String telefono;
    private String ruc;
    private String direccion;
    private String fotoUrl;
    private String estado;
    private Double lat;
    private Double lng;


    public Cliente() {
    }

    public Cliente(ClienteRealm c) {
        this.id = c.getId();
        this.empresaId = c.getEmpresaId();
        this.sucursalId = c.getSucursalId();
        this.codigo = new BigInteger(c.getCodigo()+"");
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Short empresaId) {
        this.empresaId = empresaId;
    }

    public Short getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Short sucursalId) {
        this.sucursalId = sucursalId;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
