package com.hackathon.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.hackathon.R;
import com.hackathon.db.ChamadaHelper;
import com.hackathon.db.RecursoHelper;
import com.hackathon.listeners.ChamadaListener;
import com.hackathon.log.Logger;
import com.hackathon.models.Chamada;
import com.hackathon.models.Recurso;
import com.hackathon.tasks.ChamadaTask;
import com.hackathon.tasks.LocalizacaoTask;
import com.hackathon.utils.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements ChamadaListener, Emitter.Listener{

    private TextView tvStatusChamada;

    private static final int PERMISSION_REQUEST_LOCATION = 1;

    private RecursoHelper recursoHelper;
    private Recurso recurso;
    private Chamada chamada;

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializar modelos/objeto de dados
        recursoHelper = RecursoHelper.getInstance();
        recurso = recursoHelper.getSnapshot();
        chamada = new Chamada();

        // verificar se há algum recurso cadastrado no dispositivo
        if (recurso.getId().equalsIgnoreCase(getString(R.string.pref_default_string))) {

            // recurso é o mesmo que o default, então inicializar a configuração de recurso
            startActivity(new Intent(this, SetupActivity.class));
            finish();
            return;
        }

        // alterar o título da tool bar
        getSupportActionBar().setTitle(getString(R.string.Recurso, recurso.getId()));

        // Garantir permissão de Localização para API 23+
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            garantirPermissoes();
        } else {
            LocalizacaoTask.init(MainActivity.this);
        }

        // inicializar componentes
        tvStatusChamada = (TextView) findViewById(R.id.main_tv_status);

        // inicializar a busca por novas chamadas
        ChamadaTask.init(MainActivity.this);

        // adicionar o listener para aguardar o recebimento de uma nova chamada!
        ChamadaTask.getInstance().addChamadaListener(this);

        // inicializar o chat
        try {
            mSocket = IO.socket(Network.CHAT);
            mSocket.connect();
            mSocket.on("new message", this);
        } catch (URISyntaxException e) {
            Logger.debug(e.getMessage());
        }
    }

    @Override
    public void call(Object... args) {
        JSONObject data = (JSONObject) args[0];
        String username, message;
        try {
            username = data.getString("username");
            message = data.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        Logger.debug("New message from " + username +": " + message);
    }

    private void garantirPermissoes() {
        // Should we show an explanation?
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_LOCATION);
    }

    @Override
    public void onChamadaChange(Chamada chamada) {
        this.chamada = chamada;
        // alterar os estado da chamada do BD
        ChamadaHelper.getInstance().set(this.chamada);
        startActivity(new Intent(this, ChamadaActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                    LocalizacaoTask.init(MainActivity.this);
                    Logger.debug("Localização ativada!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(MainActivity.this,
                            "Não foi possível iniciar a localização!",
                            Toast.LENGTH_SHORT);
                }
                return;
            }
        }
    }

    private void encerrarSessao() {
        ChamadaTask.getInstance().destroy();
        LocalizacaoTask.getInstance().destroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sair")
                .setMessage("Tem certeza de que deseja encerrar a sessão?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        encerrarSessao();
                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }
}
