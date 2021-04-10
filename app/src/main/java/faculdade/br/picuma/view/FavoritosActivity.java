package faculdade.br.picuma.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFavoritos;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Favoritos;
import faculdade.br.picuma.util.Constantes;

public class FavoritosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ClienteControl clienteControl;
    private ListView lvListaFavoritosCliente;
    private AdapterListaFavoritos adapter;

    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        setTitle(Constantes.NOME_ACTIVITY_FAVORITOS);
        if (getClienteControl().getCliente().getListaFavoritos() != null) {
            lvListaFavoritosCliente = findViewById(R.id.lvListaFavoritosCliente);
            adapter = new AdapterListaFavoritos(getApplicationContext());
            lvListaFavoritosCliente.setAdapter(adapter);
            updateList(getClienteControl().getCliente().getListaFavoritos());
            lvListaFavoritosCliente.setOnItemClickListener(this);
            lvListaFavoritosCliente.setOnItemLongClickListener(this);
        }
    }

    private void updateList(List<Favoritos> listaDeFavoritos) {
        adapter.setListaFavoritos(listaDeFavoritos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(getClienteControl().getCliente().getListaFavoritos().get(position).getIdEmpresa());
        getClienteControl().visualizarPerfilEmpresa(getApplicationContext(), empresa);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final CharSequence[] opcoes = {
                "Excluir",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção");
        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opcoes[which].equals("Excluir")) {
                    if (getClienteControl().desfavoritarEmpresa(getClienteControl().getCliente().getListaFavoritos().get(position))) {
                        updateList(getClienteControl().getCliente().getListaFavoritos());
                    } else {
                        Toast.makeText(getApplicationContext(), Constantes.M_FALHA_AO_RETIRAR_DE_FAVORITOS_EMPRESA, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        return true;
    }
}
