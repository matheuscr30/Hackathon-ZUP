package com.hackathon.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.models.Queixa;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class QueixaHelper extends Helper<Queixa>{

    private static QueixaHelper instance = null;

    public static QueixaHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException();
        }
        return instance;
    }

    public static QueixaHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new QueixaHelper(ctx);
        }
        return instance;
    }

    private QueixaHelper(Context ctx) {
        super(ctx, ctx.getString(R.string.pref_queixa));
    }

    @Override
    public Queixa getSnapshot() {
        String descricao = preferences.getString(
                context.getString(R.string.pref_queixa_descricao),
                context.getString(R.string.pref_default_string));

        if (descricao.equalsIgnoreCase(context.getString(R.string.pref_default_string))) {
            return null;
        }

        Queixa queixa = new Queixa();
        queixa.setDescricao(descricao);

        queixa.setObservacoes(preferences.getString(
                context.getString(R.string.pref_queixa_observacoes),
                context.getString(R.string.pref_default_string)));

        queixa.setEmergencia(preferences.getBoolean(
                context.getString(R.string.pref_queixa_emergencia),
                false));
        return queixa;
    }

    @Override
    public void set(Queixa queixa) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.pref_queixa_descricao), queixa.getDescricao());
        editor.putBoolean(context.getString(R.string.pref_queixa_emergencia), queixa.isEmergencia());
        editor.putString(context.getString(R.string.pref_queixa_observacoes), queixa.getObservacoes());
        editor.commit();
    }
}
