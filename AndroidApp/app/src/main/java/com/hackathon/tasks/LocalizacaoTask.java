package com.hackathon.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hackathon.db.RecursoHelper;
import com.hackathon.log.Logger;
import com.hackathon.models.Recurso;
import com.hackathon.utils.Network;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by Marcus on 02-Dec-17.
 */
public class LocalizacaoTask extends Task {

    private static LocalizacaoTask instance = null;
    // latitude e longitude
    private LocationRequest mLocationRequest;
    private Recurso recurso;

    private static final long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private static final long FASTEST_INTERVAL = 5 * 1000; /* 5 sec */
    private static final int SEND_LOCATION_UPDATE = 15;

    public static synchronized LocalizacaoTask getInstance() {
        if (instance == null) {
            throw new NullPointerException("Please, init service before!");
        }
        return instance;
    }

    public synchronized static void init(Context context) {
        if (instance == null) {
            instance = new LocalizacaoTask(context);
        }
    }

    @SuppressLint("MissingPermission")
    private void getUltimaLocalizacao() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(context);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            Logger.debug("Ultima localizacao obtida!");
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Logger.debug("Erro ao tentar buscar a ultima localizacao");
                        e.printStackTrace();
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                        //Toast.makeText(context, "Location changed!", Toast.LENGTH_SHORT).show();
                    }
                },
                Looper.myLooper());

        getUltimaLocalizacao();
    }

    @SuppressLint("MissingPermission")
    private LocalizacaoTask(Context context) {
        super(context, SEND_LOCATION_UPDATE);

        this.recurso = RecursoHelper.getInstance().getSnapshot();
        startLocationUpdates();

        start();
    }

    @Override
    protected void Do() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(Network.HOST + "recurso/" + recurso.getId());

        try {
            //add data
            List<NameValuePair> nameValuePairs = new ArrayList<>(3);
            nameValuePairs.add(new BasicNameValuePair("long", String.valueOf(recurso.getLongitude())));
            nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(recurso.getLatitude())));
            nameValuePairs.add(new BasicNameValuePair("status", recurso.getStatus().value));
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //execute http post
            HttpResponse response = httpclient.execute(httpPut);
            Logger.debug("Send result " + EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            Logger.debug("Do(Location): " + e.getMessage());
        } catch (IOException e) {
            Logger.debug("Do(Location): " + e.getMessage());
        } finally {
            Logger.debug("[Location]Connection closed!");
            // close the connection!!
            try {
                httpPut.getEntity().consumeContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onLocationChanged(Location location) {
        this.recurso.setLatitude(location.getLatitude());
        this.recurso.setLongitude(location.getLongitude());

        Logger.debug("Nova Longi.: " + recurso.getLatitude() + " nova lat.: " + recurso.getLongitude());
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
