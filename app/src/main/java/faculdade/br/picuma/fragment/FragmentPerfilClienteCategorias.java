package faculdade.br.picuma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.ClienteControl;

public class FragmentPerfilClienteCategorias extends Fragment {

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
        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_categoria_perfil_cliente, container, false);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaAreaAtuacao(), "categorias");
        fragmentTransaction.commit();
        return v;
    }
}
