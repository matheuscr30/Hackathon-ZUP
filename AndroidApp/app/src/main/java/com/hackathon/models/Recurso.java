package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class Recurso extends Model{

    private String id;
    private double latitude;
    private double longitude;
    private Status status;

    @Override
    protected JSONObject getJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("latitude", getLatitude());
        object.put("longitude", getLongitude());

        String val = "";
        switch (status) {
            case OCUPADO:
                val = "Ocupado";
                break;

            case DISPONIVEL:
                val = "Disponível";
                break;
        }

        object.put("status", val);
        return object;
    }

    public Recurso() {
        this.status = Status.DISPONIVEL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        OCUPADO("ocupado"), DISPONIVEL("disponivel");

        public final String value;

        Status(String value) {
            this.value = value;
        }
    }
}
