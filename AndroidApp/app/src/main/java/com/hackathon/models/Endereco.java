package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 01-Dec-17.
 */

public class Endereco extends Model {

    private String municipio;
    private String endereco;
    private String bairro;
    private int numero;
    private String referenca;

    @Override
    protected JSONObject getJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("municipio", getMunicipio());
        object.put("endereco", getEndereco());
        object.put("bairro", getBairro());
        object.put("numero", getNumero());
        object.put("referenca", getReferenca());
        return object;
    }

    public static Endereco fromJSON(JSONObject object) throws JSONException {
        Endereco end = new Endereco();
        end.setBairro(object.getString("bairro"));
        end.setEndereco(object.getString("endereco"));
        end.setNumero(object.getInt("numero"));
        end.setMunicipio(object.getString("municipio"));
        end.setReferenca(object.getString("referencia"));
        return end;
    }

    public String getReferenca() {
        return referenca;
    }

    public void setReferenca(String referenca) {
        this.referenca = referenca;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
