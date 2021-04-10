package faculdade.br.picuma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterRecyclerListaEmpresa;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.util.Constantes;

/**
 * Created by lucas on 18/04/2018.
 */

public class FragmentPerfilClienteListaEmpresas extends Fragment {

    private RecyclerView recyclerViewListaEmpresa;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

private ClienteControl clienteControl;

    public ClienteControl getClienteControl() {
        if(clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_empresas, container, false);

        if ((getClienteControl().getListaEmpresa() != null) && (getClienteControl().getListaEmpresa().size() >= 1)){

            recyclerViewListaEmpresa = v.findViewById(R.id.recyclerViewListaEmpresa);

            mLinearLayoutManager = new LinearLayoutManager(v.getContext());

            mLayoutManager = mLinearLayoutManager;

            recyclerViewListaEmpresa.setLayoutManager(mLayoutManager);

            mAdapter = new AdapterRecyclerListaEmpresa(v.getContext(), getClienteControl().getListaEmpresa());
            recyclerViewListaEmpresa.setAdapter(mAdapter);
        } else {
            Toast.makeText(v.getContext(), Constantes.M_AT_SEM_EMPRESA, Toast.LENGTH_LONG).show();
        }
        return v;
    }
}
