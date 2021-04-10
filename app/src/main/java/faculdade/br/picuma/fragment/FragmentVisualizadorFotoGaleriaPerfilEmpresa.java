package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterVisualizadorFotosGaleria;
import faculdade.br.picuma.control.EmpresaControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVisualizadorFotoGaleriaPerfilEmpresa extends Fragment {

    private AdapterVisualizadorFotosGaleria adapter;

    public FragmentVisualizadorFotoGaleriaPerfilEmpresa() {
        super();
    }

    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizador_fotos_galeria, container, false);
        getEmpresaControl().setApresentandoFotosGaleria(true);
        ViewPager vpVisualizadorFotosGaleria = v.findViewById(R.id.vpVisualizadorFotosGaleria);
        adapter = new AdapterVisualizadorFotosGaleria(getContext(), getEmpresaControl().getListaByteFotos());
        vpVisualizadorFotosGaleria.setAdapter(adapter);
        return v;
    }
}
