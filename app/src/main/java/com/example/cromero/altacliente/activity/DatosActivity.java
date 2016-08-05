package com.example.cromero.altacliente.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cromero.altacliente.R;
import com.example.cromero.altacliente.modelClientService.Cliente;

public class DatosActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "DATOS";
    public static final String DATO_EXTRA = "CLIENTE_ACTUAL";
    EditText razonSocial;
    EditText nombreComercial;
    EditText ruc;
    EditText direccion;
    EditText telefono;

    Button btnCancelar;
    Button btnSiguiente;

    Cliente actual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_datos);
        init();
    }

    public void init(){
        razonSocial = (EditText) findViewById(R.id.razonSocial);
        nombreComercial = (EditText)findViewById(R.id.nombreComercial);
        ruc = (EditText) findViewById(R.id.ruc);
        direccion =(EditText) findViewById(R.id.direccion);
        telefono = (EditText) findViewById(R.id.telefono);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);
        btnSiguiente = (Button) findViewById(R.id.btn_siguiente);

        btnCancelar.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancelar:
                cancelarOperacion();
                break;
            case R.id.btn_siguiente:
                siguiente();
                break;
        }
    }

    private void siguiente() {
        llenaDesdeUI();
        Intent intent = new Intent(this,MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATO_EXTRA,actual);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void llenaDesdeUI() {
        if(actual == null){
            actual = new Cliente();
            actual.setEmpresaId((short) 1);
            actual.setSucursalId((short) 1);
            actual.setNombreComercial(nombreComercial.getText().toString());
            actual.setRazonsocial(razonSocial.getText().toString());
            actual.setRuc(ruc.getText().toString());
            actual.setDireccion(direccion.getText().toString());
            actual.setTelefono(telefono.getText().toString());
            actual.setEstado("ACTIVO");
        }
    }

    private void cancelarOperacion() {
        limpiar();
        finish();
    }

    @Override
    protected void onDestroy() {
        limpiar();
        super.onDestroy();
    }

    private  void limpiar(){
        actual = null;
    }
}
