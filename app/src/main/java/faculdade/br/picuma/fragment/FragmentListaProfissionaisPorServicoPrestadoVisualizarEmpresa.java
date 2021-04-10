package faculdade.br.picuma.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFuncionario;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.model.Funcionario;

public class FragmentListaProfissionaisPorServicoPrestadoVisualizarEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;
    private ListView lvListaProfissionaisPorServicosPrestados;
    private AdapterListaFuncionario adapter;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_profissionais_por_servico_prestado, container, false);
        getVisualizarEmpresaControl().setListadoProfissionaisPorServico(true);
        fragmentManager = getFragmentManager();
        lvListaProfissionaisPorServicosPrestados = v.findViewById(R.id.lvListaProfissionaisPorServicosPrestados);
        adapter = new AdapterListaFuncionario(getContext());
        lvListaProfissionaisPorServicosPrestados.setAdapter(adapter);
        updateList(getVisualizarEmpresaControl().getParamParaListagemDeProfissional());
        lvListaProfissionaisPorServicosPrestados.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final CharSequence[] opcoes = {
                "Agendar Serviço",
                "Cancelar"
        };
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Agendar Serviço")) {
                    getVisualizarEmpresaControl().getDadosAgendamento().setServicoPrestado(getVisualizarEmpresaControl().getServicoPrestadoParaAgendamento());
                    getVisualizarEmpresaControl().getDadosAgendamento().setFuncionario(getVisualizarEmpresaControl().getParamParaListagemDeProfissional().get(position));
                    getVisualizarEmpresaControl().iniciarCadastroAgendamento(getContext());
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void updateList(List<Funcionario> dados) {
        adapter.setListaFuncionario(dados);
    }
}
