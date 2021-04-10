package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaGrupoServico;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaGrupoServicoPerfilCliente extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lvListaGrupoServico;
    private AdapterListaGrupoServico adapter;
    private FragmentManager fragmentManager;
    private ClienteControl clienteControl;
    private ArrayList<GrupoServico> listaGrupoServico;


    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_grupo_servico, container, false);

        if (getClienteControl().getParamParaListagemDeGrupoServicoPorAreaAtuacao() != null && getClienteControl().getListaGrupoServico() != null) {
            listaGrupoServico = new ArrayList<>();
            for (GrupoServico grupoServico : getClienteControl().getListaGrupoServico()) {
                if (grupoServico.getIdAreaAtuacao() == getClienteControl().getParamParaListagemDeGrupoServicoPorAreaAtuacao().getIdAreaAtuacao()) {
                    listaGrupoServico.add(grupoServico);
                }
            }

            if (listaGrupoServico != null && listaGrupoServico.size() > 0) {
                fragmentManager = getFragmentManager();
                lvListaGrupoServico = v.findViewById(R.id.lvListaGrupoServico);
                adapter = new AdapterListaGrupoServico(getActivity());
                lvListaGrupoServico.setAdapter(adapter);
                updateList(listaGrupoServico);
                lvListaGrupoServico.setOnItemClickListener(this);
                getClienteControl().setListadoGrupoServico(true);
            } else {
                Toast.makeText(v.getContext(), Constantes.M_AT_SEM_GRUPO_SERVICO_PARA_ESSA_AREA_ATUACAO, Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaAreaAtuacao(), "area_atuacao");
                fragmentTransaction.commit();
            }
        } else {
            Toast.makeText(v.getContext(), Constantes.M_AT_SEM_EMPRESA, Toast.LENGTH_LONG).show();
        }
        return v;
    }

    private void updateList(List<GrupoServico> dados) {
        adapter.setListaGrupoServicos(dados);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showFragment(listaGrupoServico.get(position));
    }

    private void showFragment(GrupoServico grupoServico) {
        getClienteControl().setParamParaListagemDeEmpresaPorCategoria(grupoServico);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaEmpresaPorGrupoServicoPerfilCliente(), "Empresas");
        fragmentTransaction.commit();
    }
}
