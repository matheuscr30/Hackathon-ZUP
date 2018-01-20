package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 01-Dec-17.
 */

public class Queixa extends Model {

    private String descricao;
    private String observacoes;
    private boolean emergencia;

    @Override
    protected JSONObject getJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("descricao", getDescricao());
        object.put("observacoes", getObservacoes());
        object.put("emergencia", isEmergencia());
        return object;
    }

    public static Queixa fromJSON(JSONObject object) throws JSONException {
        Queixa queixa = new Queixa();
        queixa.setDescricao(object.getString("descricao"));
        queixa.setEmergencia(object.getBoolean("emergencia"));
        queixa.setObservacoes(object.getString("observacoes"));
        return queixa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isEmergencia() {
        return emergencia;
    }

    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
    }
}
