package com.hackathon.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hackathon.R;
import com.hackathon.db.RecursoHelper;
import com.hackathon.models.Recurso;

public class SetupActivity extends AppCompatActivity {

    private Recurso recurso;
    private RecursoHelper recursoHelper;

    private TextInputLayout tiIdRecurso;
    private EditText etIdRecurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // inicialização de modelos/objeto de dados
        recursoHelper = RecursoHelper.getInstance(SetupActivity.this);
        recurso = new Recurso();

        // inicialização de componentes
        etIdRecurso = (EditText) findViewById(R.id.setup_et_recurso_id);
        tiIdRecurso = (TextInputLayout) findViewById(R.id.setup_et_recurso_ti);

        // alteração do título da tool bar
        getSupportActionBar().setTitle("Identificação");
    }

    /**
     * Ação disparada quando o usuário clicar no botão para iniciar o aplicativo!
     */
    public void iniciar(View view) {
        String id = etIdRecurso.getText().toString().trim();
        // verificação se o ID é válido!
        if (!validarId(id)) {
            tiIdRecurso.setError(getString(R.string.Id_Invalido));
            return;
        }

        recurso.setId(id.toUpperCase());
        recursoHelper.set(recurso);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public boolean validarId(String idRecebido) {
        if (idRecebido.equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }
}
