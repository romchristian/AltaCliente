package com.example.cromero.altacliente.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.cromero.altacliente.dao.ClienteDAO;
import com.example.cromero.altacliente.modelClientService.Cliente;
import com.example.cromero.altacliente.modelRealm.ClienteRealm;
import com.example.cromero.altacliente.responseClientService.OperacionResponse;
import com.example.cromero.altacliente.util.AppHelper;
import com.example.cromero.altacliente.R;
import com.example.cromero.altacliente.util.GsonRequestNoAuth;
import com.example.cromero.altacliente.util.VolleyMultipartRequest;
import com.example.cromero.altacliente.util.VolleySingleton;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FinalizarActivity extends AppCompatActivity {

    public static final String TAG = "FINALIZAR";

    private Bitmap bitmap;

    ImageView imageViewPhoto;
    ProgressDialog pg;
    private static final int CAMERA_PIC_REQUEST = 22;
    private Cliente actual;




    private String UPLOAD_URL ="http://172.16.8.78:8080/ArthyWeb/servlet/fileUpload";
    private String PREF_URL ="http://172.16.8.78:8080/ArthyWeb/webresources/servicio";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    Button btnUpload;
    Button btnPhoto;
    private Uri mImageUri;
    File photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);
        btnUpload = (Button) findViewById(R.id.btn_finalizar);
        btnPhoto = (Button) findViewById(R.id.btn_photo);
        imageViewPhoto = (ImageView) findViewById(R.id.photo);
        pg = new ProgressDialog(this);

        final FinalizarActivity me = this;
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try
                {
                    // place where to store camera taken picture
                    photo = me.createTemporaryFile("picture", ".jpg");
                    photo.delete();

                    mImageUri = Uri.fromFile(photo);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }
                catch(Exception e)
                {
                    Log.v("FinalizarActivity", "Can't create file to take picture!");
                    Toast.makeText(me, "Please check SD card! Image shot is impossible!", Toast.LENGTH_LONG);
                }

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.setTitle("Subiendo...");
                pg.show();
                uploadImage();
            }
        });


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Object dato = bundle.getSerializable(DatosActivity.DATO_EXTRA);

        if(dato != null && dato instanceof Cliente){
            actual = (Cliente) dato;
            Log.d(TAG,"Nombre Comercial: " + actual.getNombreComercial());
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    if (resultCode == RESULT_OK) {

                        //FileOutputStream out = null;
                        try {
                            //Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");

                            //imageViewPhoto.setImageBitmap(bitmap2);
                            //ContentResolver cr = this.getContentResolver();
                            //bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);

                            grabImage(imageViewPhoto);




                          /*  String path = Environment.getExternalStorageDirectory().toString();
                            File file = new File(path, "pruebra.png");
                            out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                            out.flush();
                            out.close();
*/
                            //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                           /* try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }*/
                        }


                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }



    private void uploadImage(){

        final String randomName = UUID.randomUUID().toString().replace("-","_")+".png";




        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    //String status = result.getString("status");
                    //String message = result.getString("message");
                    Log.d("Response result", result!=null?result.toString():"NADA");
                    pg.cancel();
                    imageViewPhoto.setImageBitmap(null);

                    if(actual != null) {
                        actual.setFotoUrl(randomName);
                        guarda();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                pg.cancel();
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",randomName);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                //AppHelper.scaledBitman(bitmap,480,360)
                params.put("image", new DataPart(randomName, AppHelper.getFileDataFromBitmap(bitmap), "image/png"));

                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    private void guarda() {
        int id = ClienteDAO.create(actual);

        ClienteRealm cr = ClienteDAO.find(id);
        Log.d(TAG,"Id: "+cr.getId());
        Log.d(TAG,"Empresa Id: "+cr.getEmpresaId());
        Log.d(TAG,"Sucursal Id: "+cr.getSucursalId());
        Log.d(TAG,"Razon Social: "+cr.getRazonsocial());
        Log.d(TAG,"Nombre Comercial: "+cr.getNombreComercial());
        Log.d(TAG,"RUC: "+cr.getRuc());
        Log.d(TAG,"Direccion: "+cr.getDireccion());
        Log.d(TAG,"Telefono: "+cr.getTelefono());
        Log.d(TAG,"Lat: "+cr.getLat());
        Log.d(TAG,"Lng: "+cr.getLng());
        Log.d(TAG,"URL: "+cr.getFotoUrl());
        Log.d(TAG,"Estado: "+cr.getEstado());

        enviar(cr);

    }


    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void grabImage(ImageView imageView)
    {
        this.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmapTmp;
        try
        {
            bitmapTmp = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);

            bitmap = AppHelper.scaledBitman(bitmapTmp,858,480);
            imageView.setImageBitmap(bitmap);

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.d("FinalizarActivity", "Failed to load", e);
        }
    }


    @Override
    protected void onDestroy() {
        bitmap.recycle();
        System.gc();
        super.onDestroy();
    }



    public void enviar(ClienteRealm cr){

        Cliente c = new Cliente(cr);
        final String body = new GsonBuilder().setPrettyPrinting().create().toJson(c);
        final Context context = this;

        GsonRequestNoAuth<OperacionResponse> req = new GsonRequestNoAuth<OperacionResponse>(Request.Method.POST, PREF_URL + "/altacliente",
                OperacionResponse.class, body,
                new Response.Listener<OperacionResponse>() {
                    @Override
                    public void onResponse(OperacionResponse response) {
                        if (response != null && response.getCodRetorno() == 200) {
                            String facturaId = response.getId();

                            Toast.makeText(context, "Se envió con exito ", Toast.LENGTH_LONG).show();

                            reiniciar();
                        }
                    }
                }, context);

        int socketTimeout = 50000;//50 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        VolleySingleton.getInstance(context).addToRequestQueue(req);

    }


    public void reiniciar(){
        Intent intent = new Intent(this,DatosActivity.class);
        startActivity(intent);
        finish();
    }
}
