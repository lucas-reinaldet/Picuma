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

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaAreaAtuacao;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.AreaAtuacao;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaAreaAtuacao extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lvListaAreaAtuacao;
    private AdapterListaAreaAtuacao adapter;
    private FragmentManager fragmentManager;
    private ClienteControl clienteControl;

    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_area_atuacao, container, false);
        getClienteControl().setListadoGrupoServico(false);
        fragmentManager = getFragmentManager();

        lvListaAreaAtuacao = v.findViewById(R.id.lvListaAreaAtuacao);
        adapter = new AdapterListaAreaAtuacao(getActivity());
        lvListaAreaAtuacao.setAdapter(adapter);

        if (getClienteControl().getListaAreaAtuacao() != null && getClienteControl().getListaAreaAtuacao().size() > 0) {
            updateList(getClienteControl().getListaAreaAtuacao());
        } else {
            Toast.makeText(getContext(), Constantes.M_AT_SEM_AREA_ATUACAO, Toast.LENGTH_SHORT).show();
        }
        lvListaAreaAtuacao.setOnItemClickListener(this);
        return v;
    }

    private void updateList(List<AreaAtuacao> dados) {
        adapter.setListaAreaAtuacao(dados);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showFragment(getClienteControl().getListaAreaAtuacao().get(position));
    }

    private void showFragment(AreaAtuacao areaAtuacao) {
        getClienteControl().setParamParaListagemDeGrupoServicoPorAreaAtuacao(areaAtuacao);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaGrupoServicoPerfilCliente(), "Empresas");
        fragmentTransaction.commit();
    }
}
