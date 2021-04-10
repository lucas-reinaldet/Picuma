package faculdade.br.picuma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.VisualizarEmpresaControl;

public class FragmentVisualizarServicosEmpresa extends Fragment {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getVisualizarEmpresaControl().getViewServicosEmpresa(inflater, container);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaGrupoServicoPrestadoVisualizarEmpresa(), "galeria");
        fragmentTransaction.commit();
        return v;
    }
}
