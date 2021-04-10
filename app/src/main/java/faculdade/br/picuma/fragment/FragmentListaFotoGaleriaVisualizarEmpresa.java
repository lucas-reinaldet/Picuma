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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFotoGaleriaEmpresa;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.model.FotoGaleria;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaFotoGaleriaVisualizarEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridViewFotoGaleriaEmpresa;
    private AdapterListaFotoGaleriaEmpresa adapter;
    private FragmentManager fragmentManager;

    public FragmentListaFotoGaleriaVisualizarEmpresa() {
        super();
    }

    private VisualizarEmpresaControl visualizarEmpresaControl;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_foto_galeria_empresa, container, false);
        getVisualizarEmpresaControl().setListadoFotoGaleria(true);
        gridViewFotoGaleriaEmpresa = v.findViewById(R.id.gridViewFotoGaleriaEmpresa);

        if (getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa() != null &&
                getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().size() > 0) {
            adapter = new AdapterListaFotoGaleriaEmpresa(getContext(), getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria());
            gridViewFotoGaleriaEmpresa.setAdapter(adapter);
            gridViewFotoGaleriaEmpresa.setOnItemClickListener(this);
        } else {
            Toast.makeText(getContext(), Constantes.M_AT_SEM_FOTO_GALERIA, Toast.LENGTH_SHORT).show();
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentListaGaleriaVisualizarEmpresa(), "galeria");
            fragmentTransaction.commit();
        }
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getVisualizarEmpresaControl().setApresentandoFotosGaleria(true);

        List<byte[]> listaBytes = new ArrayList<>();

        listaBytes.add(getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getFotoAntesGaleria());
        listaBytes.add(getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getFotoDepoisGaleria());

        for (FotoGaleria fotoGaleria : getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria()) {
            if (getVisualizarEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getIdFotoGaleria() != fotoGaleria.getIdFotoGaleria()) {
                listaBytes.add(fotoGaleria.getFotoAntesGaleria());
                listaBytes.add(fotoGaleria.getFotoDepoisGaleria());
            }
        }

        if (listaBytes != null && listaBytes.size() > 0) {
            getVisualizarEmpresaControl().setListaByteFotos(listaBytes);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentVisualizadorFotoGaleriaVisualizarEmpresa(), "apresentar_fotos");
            fragmentTransaction.commit();
        }
    }
}

