package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 01-Dec-17.
 */

public class Chamada extends Model {

    private String id;
    private String telefone;
    private Endereco endereco;
    private Paciente paciente;
    private Queixa queixa;
    private String solicitante;

    public static Chamada fromJSON(JSONObject object) throws JSONException {
        Chamada chamada = new Chamada();

        JSONObject endObj = object.getJSONObject("endereco-obj");
        JSONObject pacObj = object.getJSONObject("paciente-obj");

        chamada.setEndereco(Endereco.fromJSON(endObj));
        chamada.setPaciente(Paciente.fromJSON(pacObj));

        chamada.setId(object.getString("id"));
        chamada.setSolicitante(object.getString("solicitante"));
        chamada.setTelefone(object.getString("telefone"));
        return chamada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Queixa getQueixa() {
        return queixa;
    }

    public void setQueixa(Queixa queixa) {
        this.queixa = queixa;
    }

    @Override
    protected JSONObject getJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("telefone", getTelefone());
        object.put("endereco-obj", getEndereco().getJSON());
        object.put("paciente-obj", getPaciente().getJSON());
        object.put("queixa-obj", getQueixa().getJSON());
        object.put("solicitante", getSolicitante());
        return object;
    }
}
