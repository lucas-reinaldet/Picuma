package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterRecyclerListaEmpresa;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaEmpresaPorGrupoServicoPerfilCliente extends Fragment {

    private ArrayList<Empresa> listaEmpresa;

    public FragmentListaEmpresaPorGrupoServicoPerfilCliente() {
        super();
    }

    private RecyclerView recyclerViewListaEmpresa;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ClienteControl clienteControl;


    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_empresas, container, false);

        if (getClienteControl().getParamParaListagemDeEmpresaPorCategoria() != null && getClienteControl().getListaEmpresa() != null) {
            listaEmpresa = new ArrayList<>();
            for (Empresa empresa : getClienteControl().getListaEmpresa()) {
                for (ServicoPrestado servicoPrestado : empresa.getListaServicoPrestado()) {
                    if (servicoPrestado.getServico().getGrupoServico().getIdGrupoServico() == getClienteControl().getParamParaListagemDeEmpresaPorCategoria().getIdGrupoServico()) {
                        listaEmpresa.add(empresa);
                        break;
                    }
                }
            }

            if (listaEmpresa != null && listaEmpresa.size() > 0) {

                recyclerViewListaEmpresa = v.findViewById(R.id.recyclerViewListaEmpresa);
                mLinearLayoutManager = new LinearLayoutManager(v.getContext());
                mLayoutManager = mLinearLayoutManager;
                recyclerViewListaEmpresa.setLayoutManager(mLayoutManager);
                getClienteControl().setListadoEmpresaPorCategoria(true);
                mAdapter = new AdapterRecyclerListaEmpresa(v.getContext(), listaEmpresa);
                recyclerViewListaEmpresa.setAdapter(mAdapter);

            } else {
                Toast.makeText(v.getContext(), Constantes.M_AT_SEM_EMPRESA_PRESTADORA_DE_SERVICO, Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaGrupoServicoPerfilCliente(), "categorias");
                fragmentTransaction.commit();
            }
        } else {
            Toast.makeText(v.getContext(), Constantes.M_AT_SEM_EMPRESA, Toast.LENGTH_LONG).show();
        }
        return v;
    }
}
