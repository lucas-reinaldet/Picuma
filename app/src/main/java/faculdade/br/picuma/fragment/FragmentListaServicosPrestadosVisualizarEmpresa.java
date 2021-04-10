package faculdade.br.picuma.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaServicoPrestado;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaServicosPrestadosVisualizarEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;
    private ListView lvListaServicosPrestados;
    private ArrayList<ServicoPrestado> listaServicoPrestado;
    private AdapterListaServicoPrestado adapter;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_servicos_prestados, container, false);

        if (getVisualizarEmpresaControl().getParamParaListagemDeServicosPrestados() != null && getVisualizarEmpresaControl().getEmpresa().getListaServicoPrestado() != null) {
            listaServicoPrestado = new ArrayList<>();
            for (ServicoPrestado servicoPrestado : getVisualizarEmpresaControl().getEmpresa().getListaServicoPrestado()) {
                if (servicoPrestado.getServico().getGrupoServico().getIdGrupoServico() == getVisualizarEmpresaControl().getParamParaListagemDeServicosPrestados().getIdGrupoServico()) {
                    listaServicoPrestado.add(servicoPrestado);
                }
            }

            if (listaServicoPrestado != null && listaServicoPrestado.size() > 0) {
                getVisualizarEmpresaControl().setListadoServicoPrestado(true);
                fragmentManager = getFragmentManager();
                lvListaServicosPrestados = v.findViewById(R.id.lvListaServicosPrestados);
                adapter = new AdapterListaServicoPrestado(getActivity());
                lvListaServicosPrestados.setAdapter(adapter);
                updateList(listaServicoPrestado);
                lvListaServicosPrestados.setOnItemClickListener(this);
            } else {
                Toast.makeText(v.getContext(), Constantes.M_AT_SEM_SERVICO_PRESTADO_PARA_ESSE_GRUPO, Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaGrupoServicoPrestadoVisualizarEmpresa(), "area_atuacao");
                fragmentTransaction.commit();
            }
        } else{
            Toast.makeText(v.getContext(), Constantes.M_AT_SEM_SERVICO_PRESTADO_PARA_ESSE_GRUPO, Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaGrupoServicoPrestadoVisualizarEmpresa(), "area_atuacao");
            fragmentTransaction.commit();
        }
        return v;
    }

    private void updateList(List<ServicoPrestado> dados) {
        adapter.setListaServicoPrestado(dados);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        List<Funcionario> listaFuncionarioServico = new ArrayList<>();
        for (Funcionario funcionario : getVisualizarEmpresaControl().getEmpresa().getListaFuncionarios()) {
            for (Integer idFuncionario : listaServicoPrestado.get(position).getListaIdFuncionario()) {
                if (funcionario.getIdFuncionario() == idFuncionario) {
                    listaFuncionarioServico.add(funcionario);
                }
            }
        }

        if (listaFuncionarioServico.size() > 0) {
            getVisualizarEmpresaControl().setServicoPrestadoParaAgendamento(listaServicoPrestado.get(position));
            getVisualizarEmpresaControl().setParamParaListagemDeProfissional(listaFuncionarioServico);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaProfissionaisPorServicoPrestadoVisualizarEmpresa(), "Empresas");
            fragmentTransaction.commit();
        } else {
            Toast.makeText(getContext(), Constantes.M_AT_SEM_PROFISSIONAL_PARA_SERVICO_PRESTADO, Toast.LENGTH_SHORT).show();
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
                        getVisualizarEmpresaControl().getDadosAgendamento().setServicoPrestado(listaServicoPrestado.get(position));
                        getVisualizarEmpresaControl().iniciarCadastroAgendamento(getContext());
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }
}
