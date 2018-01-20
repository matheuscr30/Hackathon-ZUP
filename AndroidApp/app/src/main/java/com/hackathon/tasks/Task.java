package com.hackathon.tasks;

import android.content.Context;

import com.hackathon.log.Logger;

import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marcus on 02-Dec-17.
 */

/**
 * Use esta classe como <b></b>Singleton</b>! Essa é a explicação pelo construtor ser "protected"
 */
public abstract class Task {

    protected String idRecurso;
    protected final Context context;
    protected final ScheduledExecutorService scheduler;
    private final int atualizacao;
    private boolean requesting, stop;
    private boolean start = false;

    protected Task(Context context, int atualizacaoSeg) {
        this.context = context;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.atualizacao = atualizacaoSeg;
    }

    protected abstract void Do();

    protected final void start() {
        if (start) {
            return;
        }
        start = true;
        this.scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    Do();
                }
            }
        }, atualizacao, atualizacao, TimeUnit.SECONDS);
    }

    public final void stop() {
        this.stop = true;
    }

    public final boolean isPaused() {
        return this.stop;
    }

    public void resume() {
        if (this.scheduler.isShutdown()) {
            throw new RuntimeException("Escalonador já foi desligado!");
        }

        if (this.stop) {
            this.stop = false;
            //this.scheduler.notify();
        }
    }

    public void destroy() {
        this.scheduler.shutdown();
    }
}
