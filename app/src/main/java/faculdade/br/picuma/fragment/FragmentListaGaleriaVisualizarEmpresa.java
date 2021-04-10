package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaGaleriaEmpresa;
import faculdade.br.picuma.control.VisualizarEmpresaControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaGaleriaVisualizarEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridViewCategoriaGaleriaEmpresa;
    private AdapterListaGaleriaEmpresa adapter;
    private VisualizarEmpresaControl visualizarEmpresaControl;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = getVisualizarEmpresaControl().getViewListaGaleriaEmpresa(inflater, container);
        gridViewCategoriaGaleriaEmpresa = v.findViewById(R.id.gridViewCategoriaGaleriaEmpresa);
        adapter = new AdapterListaGaleriaEmpresa(getContext());
        gridViewCategoriaGaleriaEmpresa.setAdapter(adapter);
        adapter.setListaGaleria(getVisualizarEmpresaControl().getEmpresa().getListaGaleria());
        gridViewCategoriaGaleriaEmpresa.setOnItemClickListener(this);
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getVisualizarEmpresaControl().setListadoServicoPrestado(false);
        getVisualizarEmpresaControl().setListadoProfissionaisPorServico(false);
        getVisualizarEmpresaControl().setParamParaListagemDeFotoEmpresa(getVisualizarEmpresaControl().getEmpresa().getListaGaleria().get(position));
        getVisualizarEmpresaControl().setListadoFotoGaleria(true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentListaFotoGaleriaVisualizarEmpresa(), "fotos_galeria");
        fragmentTransaction.commit();
    }
}
