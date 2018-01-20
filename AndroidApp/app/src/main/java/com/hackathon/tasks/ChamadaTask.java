package com.hackathon.tasks;

import android.content.Context;

import com.hackathon.listeners.ChamadaListener;
import com.hackathon.log.Logger;
import com.hackathon.models.Chamada;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marcus on 02-Dec-17.
 */
public class ChamadaTask extends Task {

    private static ChamadaTask instance = null;

    private final List<ChamadaListener> listeners;
    private boolean requesting;

    public static synchronized ChamadaTask getInstance() {
        if (instance == null) {
            throw new NullPointerException("Please, init service before!");
        }
        return instance;
    }

    public synchronized static void init(Context context) {
        if (instance == null) {
            instance = new ChamadaTask(context);
        }
    }

    private ChamadaTask(Context context) {
        super(context, 10);
        this.idRecurso = "Retrieve recurso in offline database!";
        this.listeners = new LinkedList<>();
        this.requesting = false;

        start();
    }

    public final void addChamadaListener(ChamadaListener listener) {
        this.listeners.add(listener);
    }

    public final boolean removeListener(ChamadaListener listener) {
        return this.listeners.remove(listener);
    }

    @Override
    protected void Do() {
        // verificar se há alguma requisição em adamento, caso
        // positivo, aguardar para realizar uma nova!
        while (requesting) {}
        requesting = true;

        String result = "", line;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("http://172.10.100.60:3000/chamada");
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException ex) {
            Logger.debug("IOException(doInBackground): " + ex.getMessage());
        } finally {
            urlConnection.disconnect();
            Logger.debug("Task is executed!");
        }

        onRequestResult(result);
    }

    private void onRequestResult(String result) {
        if (!result.equals("")) {
            try {
                Chamada chamada = Chamada.fromJSON(new JSONObject(result));
                for (ChamadaListener listener : listeners) {
                    listener.onChamadaChange(chamada);
                }
            } catch (JSONException e) {
                Logger.debug("JSONException(onPostExecute): " + e.getMessage());
            }
        }

        requesting = false;
        Logger.debug("onPostExecuted");
    }
}
