package com.hackathon.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.models.Paciente;

/**
 * Created by Marcus on 02-Dec-17.
 */
public class PacienteHelper extends Helper<Paciente> {

    private static PacienteHelper instance = null;

    public static synchronized PacienteHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException();
        }

        return instance;
    }

    public static synchronized PacienteHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new PacienteHelper(ctx);
        }
        return instance;
    }

    private PacienteHelper(Context context) {
        super(context, context.getString(R.string.pref_paciente));
    }

    @Override
    public Paciente getSnapshot() {
        String nome = preferences.getString(
                context.getString(R.string.pref_paciente_nome),
                context.getString(R.string.pref_default_string));

        if (nome.equalsIgnoreCase(context.getString(R.string.pref_default_string))) {
            return null;
        }

        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        String sexo = preferences.getString(
                context.getString(R.string.pref_paciente_sexo),
                context.getString(R.string.pref_default_string));

        if (sexo.equalsIgnoreCase("masculino")) {
            paciente.setSexo(Paciente.Sexo.MARCULINO);
        } else {
            paciente.setSexo(Paciente.Sexo.FEMININO);
        }

        paciente.setIdade(preferences.getInt(
                context.getString(R.string.pref_paciente_idade),
                Integer.valueOf(context.getString(R.string.pref_default_int))));

        return paciente;
    }

    @Override
    public void set(Paciente paciente) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.pref_paciente_nome), paciente.getNome());
        editor.putInt(context.getString(R.string.pref_paciente_idade), paciente.getIdade());

        String sexo = "";
        if (paciente.getSexo() == Paciente.Sexo.MARCULINO) {
            sexo = "masculino";
        } else {
            sexo = "feminino";
        }
        editor.putString(context.getString(R.string.pref_paciente_sexo), sexo);
        editor.commit();
    }
}
