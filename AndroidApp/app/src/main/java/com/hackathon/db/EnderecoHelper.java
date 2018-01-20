package com.hackathon.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.models.Endereco;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class EnderecoHelper extends Helper<Endereco> {

    public static EnderecoHelper instance = null;

    public static EnderecoHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException();
        }
        return instance;
    }

    public static EnderecoHelper getInstance(Context context) {
        if (instance == null) {
            instance = new EnderecoHelper(context);
        }
        return instance;
    }

    private EnderecoHelper() {
        super(null, null);
    }

    private EnderecoHelper(Context ctx) {
        super(ctx, ctx.getString(R.string.pref_endereco));
    }

    @Override
    public Endereco getSnapshot() {
        String end = preferences.getString(
                context.getString(R.string.pref_endereco_),
                context.getString(R.string.pref_default_string));

        if (end.equalsIgnoreCase(context.getString(R.string.pref_default_string))) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setEndereco(end);

        endereco.setReferenca(preferences.getString(
                context.getString(R.string.pref_endereco_referencia),
                context.getString(R.string.pref_default_string)));

        endereco.setMunicipio(preferences.getString(
                context.getString(R.string.pref_endereco_municipio),
                context.getString(R.string.pref_default_string)));

        endereco.setBairro(preferences.getString(
                context.getString(R.string.pref_endereco_bairro),
                context.getString(R.string.pref_default_string)));


        endereco.setNumero(preferences.getInt(
                context.getString(R.string.pref_endereco_numero),
                Integer.valueOf(context.getString(R.string.pref_default_int))));

        return endereco;
    }

    @Override
    public void set(Endereco endereco) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.pref_endereco_), endereco.getEndereco());
        editor.putString(context.getString(R.string.pref_endereco_bairro), endereco.getEndereco());
        editor.putString(context.getString(R.string.pref_endereco_municipio), endereco.getMunicipio());
        editor.putInt(context.getString(R.string.pref_endereco_numero), endereco.getNumero());
        editor.putString(context.getString(R.string.pref_endereco_referencia), endereco.getReferenca());
    }
}
