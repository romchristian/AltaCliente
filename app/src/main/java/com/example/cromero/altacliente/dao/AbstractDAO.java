package com.example.cromero.altacliente.dao;

import com.example.cromero.altacliente.modelClientService.Cliente;
import com.example.cromero.altacliente.modelRealm.ClienteRealm;

import io.realm.Realm;

/**
 * Created by christian.romero on 05/08/2016.
 */
public abstract class AbstractDAO<T,V> {

    public abstract T getClazz();
    public abstract void cargaDatos();


}
