package faculdade.br.picuma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterRecyclerListaComentario;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.util.Constantes;

public class FragmentVisualizarClassificacaoEmpresa extends Fragment {

    private RecyclerView recyclerViewListaEmpresa;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private VisualizarEmpresaControl visualizarEmpresaControl;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getVisualizarEmpresaControl().getViewComentarioEmpresa(inflater, container);
        if (getVisualizarEmpresaControl().primeiraExecucaoComentarioEmpresa()) {
            if ((getVisualizarEmpresaControl().getListaComentario() != null) && (getVisualizarEmpresaControl().
                    getListaComentario().size() >= 1)) {
                recyclerViewListaEmpresa = v.findViewById(R.id.recyclerViewListaComentarioVisualizarEmpresa);
                mLinearLayoutManager = new LinearLayoutManager(v.getContext());
                mLayoutManager = mLinearLayoutManager;
                recyclerViewListaEmpresa.setLayoutManager(mLayoutManager);
                mAdapter = new AdapterRecyclerListaComentario(v.getContext(), getVisualizarEmpresaControl().getListaComentario());
                recyclerViewListaEmpresa.setAdapter(mAdapter);
            } else {
                TextView tvListaGrupoComentarioVazio = v.findViewById(R.id.tvListaGrupoComentarioVazio);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                tvListaGrupoComentarioVazio.setTextSize(14);
                tvListaGrupoComentarioVazio.setLayoutParams(layoutParams);
                tvListaGrupoComentarioVazio.setText(Constantes.M_AT_SEM_COMENTARIO_EMPRESA);
            }
        }
        return v;
    }
}
