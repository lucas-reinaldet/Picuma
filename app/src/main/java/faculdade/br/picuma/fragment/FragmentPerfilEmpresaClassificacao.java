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
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.util.Constantes;

public class FragmentPerfilEmpresaClassificacao extends Fragment {

    private RecyclerView recyclerViewListaEmpresa;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getEmpresaControl().getViewClassificacaoEmpresa(inflater, container);
        getEmpresaControl().solicitarComentarios();
        if ((getEmpresaControl().getListaComentario() != null) && (getEmpresaControl().
                getListaComentario().size() >= 1)) {
            recyclerViewListaEmpresa = v.findViewById(R.id.recyclerViewListaComentarioPerfilEmpresa);
            mLinearLayoutManager = new LinearLayoutManager(v.getContext());
            mLayoutManager = mLinearLayoutManager;
            recyclerViewListaEmpresa.setLayoutManager(mLayoutManager);
            mAdapter = new AdapterRecyclerListaComentario(v.getContext(), getEmpresaControl().getListaComentario());
            recyclerViewListaEmpresa.setAdapter(mAdapter);
        } else {
            TextView tvListaGrupoComentarioVazioPE = v.findViewById(R.id.tvListaGrupoComentarioVazioPE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tvListaGrupoComentarioVazioPE.setTextSize(14);
            tvListaGrupoComentarioVazioPE.setLayoutParams(layoutParams);
            tvListaGrupoComentarioVazioPE.setText(Constantes.M_AT_SEM_COMENTARIO_EMPRESA);
        }

        return v;
    }
}
