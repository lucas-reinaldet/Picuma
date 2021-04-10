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

public class FragmentVisualizarGaleriaEmpresa extends Fragment {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;

    public FragmentVisualizarGaleriaEmpresa() {
    }

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getVisualizarEmpresaControl().getViewGaleriaEmpresa(inflater, container);
        if (getVisualizarEmpresaControl().getEmpresa().getListaGaleria() != null &&
                getVisualizarEmpresaControl().getEmpresa().getListaGaleria().size() > 0) {
            getVisualizarEmpresaControl().setListadoServicoPrestado(false);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentListaGaleriaVisualizarEmpresa(), "galeria");
            fragmentTransaction.commit();
        }

        return v;
    }
}
