package faculdade.br.picuma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.model.GrupoServico;

public class AdapterListaGrupoServico extends BaseAdapter {

    private List<GrupoServico> gruposServicos = new ArrayList<>();
    private Context context;

    public AdapterListaGrupoServico(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return gruposServicos.size();
    }

    @Override
    public GrupoServico getItem(int position) {
        return gruposServicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gruposServicos.get(position).getIdGrupoServico();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_grupo_servico, parent, false);

        TextView tvAdapterListaGrupoServico = view.findViewById(R.id.tvAdapterListaGrupoServico);
        ImageView ivAdapterImagemListaGrupoServico = view.findViewById(R.id.ivAdapterImagemListaGrupoServico);
        GrupoServico gpServico = gruposServicos.get(position);
        tvAdapterListaGrupoServico.setText(gpServico.getGrupoServico());
        if (gpServico.getFotoGrupoServico() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(gpServico.getFotoGrupoServico(), 0, gpServico.getFotoGrupoServico().length);
            ivAdapterImagemListaGrupoServico.setImageBitmap(bitmap);
        }
        return view;
    }

    public void setListaGrupoServicos(List<GrupoServico> listaGrupoServicos) {
        this.gruposServicos = listaGrupoServicos;
        notifyDataSetChanged();
    }
}
