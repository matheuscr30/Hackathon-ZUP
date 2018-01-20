package com.hackathon.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hackathon.R;
import com.hackathon.db.ChamadaHelper;
import com.hackathon.db.EnderecoHelper;
import com.hackathon.models.Chamada;
import com.hackathon.models.Endereco;
import com.hackathon.tasks.ChamadaTask;
import com.hackathon.tasks.LocalizacaoTask;

public class ChamadaActivity extends AppCompatActivity {

    private Chamada chamada;

    private Button btnInformacoes, btnGPS;
    private FloatingActionButton fabCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // carregar o modelo
        chamada = ChamadaHelper.getInstance().getSnapshot();

        if (chamada.getQueixa().isEmergencia()) {
            setContentView(R.layout.activity_chamada);
        } else {
            setContentView(R.layout.activity_chamada_ngrave);
        }
    }

    @Override
    public void onBackPressed() {
        return; // do nothing!
    }

    public void informacoes(View v) {
        startActivity(new Intent(this, ChamadaDetalhesActivity.class));
    }

    public void checkout(View v) {
        ChamadaTask.getInstance().resume();
        LocalizacaoTask.getInstance().resume();

        // alterar o status do chamado
        // alterar o status do recurso
    }

    public void abrirGps(View v) {
        try {
            // Launch Waze to look for Hawaii:
            String url = "https://waze.com/ul?q=";
            Endereco endereco = EnderecoHelper.getInstance().getSnapshot();
            if (endereco == null) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Nenhum endereço encontrado!")
                        .setMessage("Você ainda não está despachado para uma ocorrência!")
                        .setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
                return;
            }
            String data = endereco.getEndereco() + ", " + endereco.getNumero() + ", " +
                    endereco.getBairro() + ", " + endereco.getMunicipio();
            url += data;
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
            startActivity( intent );
        }
        catch (ActivityNotFoundException ex  ) {
            // If Waze is not installed, open it in Google Play:
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
            startActivity(intent);
        }
    }

}
