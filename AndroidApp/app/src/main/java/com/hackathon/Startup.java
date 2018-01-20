package com.hackathon;

import android.app.Application;
import android.content.Context;

import com.hackathon.db.ChamadaHelper;
import com.hackathon.db.EnderecoHelper;
import com.hackathon.db.PacienteHelper;
import com.hackathon.db.QueixaHelper;
import com.hackathon.db.RecursoHelper;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class Startup extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // carregar todos os banco de dados "SharedPreferences"
        Context ctx = getApplicationContext();
        ChamadaHelper.getInstance(ctx);
        EnderecoHelper.getInstance(ctx);
        PacienteHelper.getInstance(ctx);
        QueixaHelper.getInstance(ctx);
        RecursoHelper.getInstance(ctx);
    }
}
