package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 01-Dec-17.
 */

public class Paciente extends Model {

    private String nome;
    private int idade;
    private Sexo sexo;

    @Override
    protected JSONObject getJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("nome", getNome());
        object.put("idade", getIdade());

        if (sexo == Sexo.MARCULINO) {
            object.put("sexo", "Masculino");
        } else {
            object.put("sexo", "Feminino");
        }

        return object;
    }


    public static Paciente fromJSON(JSONObject object) throws JSONException {
        Paciente pac = new Paciente();
        pac.setIdade(object.getInt("idade"));
        pac.setNome(object.getString("nome"));

        if (object.getString("sexo").equalsIgnoreCase("masculino")) {
            pac.setSexo(Sexo.MARCULINO);
        } else {
            pac.setSexo(Sexo.FEMININO);
        }
        return pac;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public enum Sexo{
        MARCULINO, FEMININO, NE
    }
}
