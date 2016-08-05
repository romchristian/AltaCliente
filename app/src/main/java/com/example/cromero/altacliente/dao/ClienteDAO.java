package com.example.cromero.altacliente.dao;

import android.util.Log;

import com.example.cromero.altacliente.modelClientService.Cliente;
import com.example.cromero.altacliente.modelRealm.ClienteRealm;

import java.util.List;

import io.realm.Realm;

/**
 * Created by christian.romero on 05/08/2016.
 */
public class ClienteDAO {

    public static int create(Cliente c) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ClienteRealm cr = realm.createObject(ClienteRealm.class);
        cargaDatos(cr, c);
        realm.commitTransaction();
        return cr.getId();
    }


    public static void cargaDatos(ClienteRealm cr, Cliente c) {
        cr.setId(getNextId());
        cr.setEmpresaId(c.getEmpresaId());
        cr.setSucursalId(c.getSucursalId());
        cr.setCodigo(c.getCodigo() != null ? c.getCodigo().longValue() : 0l);
        cr.setRazonsocial(c.getRazonsocial());
        cr.setNombreComercial(c.getNombreComercial());
        cr.setTelefono(c.getTelefono());
        cr.setRuc(c.getRuc());
        cr.setDireccion(c.getDireccion());
        cr.setFotoUrl(c.getFotoUrl());
        cr.setEstado(c.getEstado());
        cr.setLat(c.getLat());
        cr.setLng(c.getLng());
    }

    public static ClienteRealm find(int id){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ClienteRealm.class).equalTo("id",id).findFirst();
    }

    public static List<ClienteRealm> findAll(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ClienteRealm.class).findAll();
    }

    public static int getNextId(){
        Realm realm = Realm.getDefaultInstance();
        Integer id = null;
        try{
            Number last = realm.where(ClienteRealm.class).findAll().max("id");
            id = last.intValue();
        }catch (Exception e){
            Log.e("CLIENTEDAO","Error al obtener ultimo Id: "  + e.getMessage());
        }

        if(id == null){
            id = 1;
        }else{
            id = id + 1;
        }
        return id;
    }
}
