package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterVisualizadorFotosGaleria;
import faculdade.br.picuma.control.VisualizarEmpresaControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVisualizadorFotoGaleriaVisualizarEmpresa extends Fragment {

    private AdapterVisualizadorFotosGaleria adapter;
    private ArrayList<byte[]> listaFotoGaleria;
    private FragmentManager fragmentManager;
    private ViewGroup viewGroup;

    public FragmentVisualizadorFotoGaleriaVisualizarEmpresa() {
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

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizador_fotos_galeria, container, false);
        ViewPager vpVisualizadorFotosGaleria = v.findViewById(R.id.vpVisualizadorFotosGaleria);
        adapter = new AdapterVisualizadorFotosGaleria(getContext(), getVisualizarEmpresaControl().getListaByteFotos());
        vpVisualizadorFotosGaleria.setAdapter(adapter);
        return v;
    }
}
