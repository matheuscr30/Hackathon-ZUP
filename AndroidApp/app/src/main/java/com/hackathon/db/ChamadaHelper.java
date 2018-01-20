package com.hackathon.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.listeners.ChamadaListener;
import com.hackathon.models.Chamada;
import com.hackathon.models.Paciente;
import com.hackathon.models.Queixa;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class ChamadaHelper extends Helper<Chamada>{

    public static ChamadaHelper instance = null;

    public static synchronized ChamadaHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException();
        }
        return instance;
    }

    public static synchronized ChamadaHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ChamadaHelper(context);
        }

        return instance;
    }

    private ChamadaHelper() {
        super(null, null);
    }

    private ChamadaHelper(Context ctx) {
        super(ctx, ctx.getString(R.string.pref_chamada));
    }


    @Override
    public Chamada getSnapshot() {
        String telefone = preferences.getString(
                context.getString(R.string.pref_chamada_telefone),
                context.getString(R.string.pref_default_string));

        if (telefone.equalsIgnoreCase(context.getString(R.string.pref_default_string))) {
            return null;
        }

        Chamada chamada = new Chamada();
        chamada.setTelefone(telefone);

        chamada.setSolicitante(preferences.getString(context.getString(R.string.pref_chamada_solicitante),
                context.getString(R.string.pref_default_string)));

        chamada.setId(preferences.getString(context.getString(R.string.pref_chamada_id),
                context.getString(R.string.pref_default_string)));

        chamada.setPaciente(PacienteHelper.getInstance().getSnapshot());
        chamada.setEndereco(EnderecoHelper.getInstance().getSnapshot());
        chamada.setQueixa(QueixaHelper.getInstance().getSnapshot());
        return chamada;
    }

    @Override
    public void set(Chamada chamada) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(context.getString(R.string.pref_chamada_id),
                chamada.getId());

        editor.putString(context.getString(R.string.pref_chamada_solicitante),
                chamada.getSolicitante());

        editor.putString(context.getString(R.string.pref_chamada_telefone),
                chamada.getTelefone());

        PacienteHelper.getInstance().set(chamada.getPaciente());
        EnderecoHelper.getInstance().set(chamada.getEndereco());
        QueixaHelper.getInstance().set(chamada.getQueixa());

        editor.commit();
    }
}
