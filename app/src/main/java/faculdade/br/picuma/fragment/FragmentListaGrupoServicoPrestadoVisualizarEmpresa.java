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

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaGrupoServico;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.model.GrupoServico;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaGrupoServicoPrestadoVisualizarEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;
    private ListView lvListaGrupoServico;
    private AdapterListaGrupoServico adapter;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = getVisualizarEmpresaControl().getViewGrupoServicosEmpresa(inflater, container);
        if (getVisualizarEmpresaControl().getListaGrupoServicoPrestado() != null &&
                getVisualizarEmpresaControl().getListaGrupoServicoPrestado().size() > 0) {
            getVisualizarEmpresaControl().setListadoServicoPrestado(false);
            getVisualizarEmpresaControl().setListadoFotoGaleria(false);
            getVisualizarEmpresaControl().setApresentandoFotosGaleria(false);
            fragmentManager = getFragmentManager();
            lvListaGrupoServico = v.findViewById(R.id.lvListaGrupoServico);
            adapter = new AdapterListaGrupoServico(getContext());
            lvListaGrupoServico.setAdapter(adapter);
            updateList(getVisualizarEmpresaControl().getListaGrupoServicoPrestado());
            lvListaGrupoServico.setOnItemClickListener(this);
        }
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        getVisualizarEmpresaControl().setListadoFotoGaleria(false);
        getVisualizarEmpresaControl().setApresentandoFotosGaleria(false);
        getVisualizarEmpresaControl().setParamParaListagemDeServicosPrestados(getVisualizarEmpresaControl().getListaGrupoServicoPrestado().get(position));
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaServicosPrestadosVisualizarEmpresa(), "servicos_prestados");
        fragmentTransaction.commit();
    }

    private void updateList(List<GrupoServico> dados) {
        adapter.setListaGrupoServicos(dados);
    }
}
