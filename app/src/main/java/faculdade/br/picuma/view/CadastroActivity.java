package faculdade.br.picuma.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import faculdade.br.picuma.R;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btParaCadastroCliente;
    private Button btParaCadastroEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btParaCadastroCliente = findViewById(R.id.btParaCadastroCliente);
        btParaCadastroEmpresa = findViewById(R.id.btParaCadastroEmpresa);

        btParaCadastroCliente.setOnClickListener(this);
        btParaCadastroEmpresa.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btParaCadastroEmpresa:

                startActivity(new Intent(getApplicationContext(), CadastroEmpresaActivity.class));

                break;

            case R.id.btParaCadastroCliente:

                startActivity(new Intent(getApplicationContext(), CadastroClienteActivity.class));

                break;

        }

    }

    @Override
    public void finish() {
        super.finish();
    }
}
