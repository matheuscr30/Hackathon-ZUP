package com.hackathon.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.models.Recurso;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class RecursoHelper extends Helper<Recurso> {

    private static RecursoHelper instance = null;

    public static synchronized RecursoHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RecursoHelper(context);
        }

        return instance;
    }

    public static synchronized RecursoHelper getInstance() {
        return instance;
    }

    private RecursoHelper(){
        super(null, null);
        throw new UnsupportedOperationException();
    }

    private RecursoHelper(Context context) {
        super(context, context.getString(R.string.pref_recurso));
    }

    @Override
    public Recurso getSnapshot() {
        Recurso rec = new Recurso();
        @SuppressLint("ResourceType")
        String defaultValue = context.getResources().getString(R.string.pref_default_string);
        rec.setId(preferences.getString(context.getString(R.string.pref_recurso_id), defaultValue));
        return rec;
    }

    @Override
    public void set(Recurso recurso) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.pref_recurso_id), recurso.getId());
        editor.commit();
    }
}
