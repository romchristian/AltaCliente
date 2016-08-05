package com.example.cromero.altacliente.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by cromero on 16/01/2015.
 */
public class GsonRequestNoAuth<T> extends JsonRequest<T> {

    private final Gson mGson;
    private final Class<T> mClassType;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    public GsonRequestNoAuth(int method, String url, Class<T> classType, JSONObject jsonRequest,
                             Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, classType, null, jsonRequest, listener, errorListener);
    }

    public GsonRequestNoAuth(int method, String url, final Class<T> classType, String body,
                             Response.Listener<T> listener, final Context context) {
        super(method, url,body, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null) {
                            Log.e("ERROR", error.getMessage()+"");
                            Toast.makeText(context, "No hay conexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        mClassType = classType;
        mHeaders = null;
        mListener = listener;
    }

    public GsonRequestNoAuth(int method, String url, Class<T> classType, Map<String, String> headers,
                             JSONObject jsonRequest, Response.Listener<T> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        mGson = new Gson();
        mClassType = classType;
        mHeaders = headers;
        mListener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset
                    (networkResponse.headers));
            return Response.success(mGson.fromJson(json, mClassType),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}