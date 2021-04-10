package faculdade.br.picuma.view;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaHorarioMarcado;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.Cliente;
import faculdade.br.picuma.model.Comentario;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.util.Constantes;

public class AgendamentosPerfilClienteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvAgendamentosPerfilCliente;
    private AdapterListaHorarioMarcado adapter;

    private ClienteControl clienteControl;
    private AlertDialog dialogAlert;
    private Button btDialogCadastroComentario;
    private Button btDialogCancelarCadastroComentario;
    private EditText etDialogComentarioCadastro;
    private TextView tvDialogNomeEmpresaCadastroComentario;
    private ImageView ivDialogFotoEmpresaCadastroComentario;

    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos_perfil_cliente);
        setTitle(Constantes.NOME_ACTIVITY_AGENDAMENTO);
        lvAgendamentosPerfilCliente = findViewById(R.id.lvAgendamentosPerfilCliente);
        lvAgendamentosPerfilCliente.setOnItemClickListener(this);
        adapter = new AdapterListaHorarioMarcado(getApplicationContext(), false);
        lvAgendamentosPerfilCliente.setAdapter(adapter);
        if (getClienteControl().getListaHorarioMarcado() != null) {
            updateLista(getClienteControl().getListaHorarioMarcado());
        } else {
            Toast.makeText(this, Constantes.M_AT_HORARIO_AGENDADO_VAZIO, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLista(List<HorarioMarcado> listaHorarioMarcado) {
        adapter.setListaHorarioMarcado(listaHorarioMarcado);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (getClienteControl().getListaHorarioMarcado().get(position).getStatus().equals(Constantes.STATUS_SERVICO_REALIZADO)) {
            final CharSequence[] opcoes = {
                    "Comentar",
                    "Perfil",
                    "Cancelar"
            };

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escolha uma opção");
            builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (opcoes[which].equals("Comentar")) {
                        comentar(position);
                    } else if (opcoes[which].equals("Perfil")) {
                        abrirPerfil(position);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } else {
            final CharSequence[] opcoes = {
                    "Perfil",
                    "Cancelar"
            };

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escolha uma opção");
            builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (opcoes[which].equals("Perfil")) {
                        abrirPerfil(position);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    private void abrirPerfil(int position) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(getClienteControl().getListaHorarioMarcado().get(position).getEmpresa().getIdEmpresa());
        getClienteControl().visualizarPerfilEmpresa(getApplicationContext(), empresa);
    }

    private void comentar(int position) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_cadastro_comentario, null);

        ivDialogFotoEmpresaCadastroComentario = mView.findViewById(R.id.ivDialogFotoEmpresaCadastroComentario);
        tvDialogNomeEmpresaCadastroComentario = mView.findViewById(R.id.tvDialogNomeEmpresaCadastroComentario);
        etDialogComentarioCadastro = mView.findViewById(R.id.etDialogComentarioCadastro);
        btDialogCancelarCadastroComentario = mView.findViewById(R.id.btDialogCancelarCadastroComentario);
        btDialogCadastroComentario = mView.findViewById(R.id.btDialogCadastroComentario);

        final HorarioMarcado horarioMarcado = getClienteControl().getListaHorarioMarcado().get(position);

        if (horarioMarcado.getEmpresa().getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getEmpresa().getLogoEmpresa(), 0, horarioMarcado.getEmpresa().getLogoEmpresa().length);
            ivDialogFotoEmpresaCadastroComentario.setImageBitmap(bitmap);
        } else if (horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao(), 0, horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao().length);
            ivDialogFotoEmpresaCadastroComentario.setImageBitmap(bitmap);
        }

        tvDialogNomeEmpresaCadastroComentario.setText(horarioMarcado.getEmpresa().getNomeFantasia());

        btDialogCancelarCadastroComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        btDialogCadastroComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comentario comentario = new Comentario();
                comentario.setComentario(etDialogComentarioCadastro.getText().toString());
                comentario.setIdEmpresa(horarioMarcado.getEmpresa().getIdEmpresa());
                Cliente cliente = new Cliente();
                cliente.setIdCliente(getClienteControl().getCliente().getIdCliente());
                comentario.setCliente(cliente);

                if (getClienteControl().cadastrarComentario(comentario)) {
                    Toast.makeText(getApplicationContext(), Constantes.M_COMENTARIO_CADASTRADO, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), Constantes.M_FALHA_AO_CADASTRAR_COMENTARIO, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });
        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();
    }
}
