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
import faculdade.br.picuma.model.AreaAtuacao;

public class AdapterListaAreaAtuacao extends BaseAdapter {

    private List<AreaAtuacao> listaAreaAtuacao = new ArrayList<>();
    private Context context;

    public AdapterListaAreaAtuacao(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaAreaAtuacao.size();
    }

    @Override
    public AreaAtuacao getItem(int position) {
        return listaAreaAtuacao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaAreaAtuacao.get(position).getIdAreaAtuacao();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_area_atuacao, parent, false);

        TextView tvAdapterListaAreaAtuacao = view.findViewById(R.id.tvAdapterListaAreaAtuacao);
        ImageView ivAdapterImagemListaAreaAtuacao = view.findViewById(R.id.ivAdapterImagemListaAreaAtuacao);
        AreaAtuacao areaAtuacao = listaAreaAtuacao.get(position);
        tvAdapterListaAreaAtuacao.setText(areaAtuacao.getAreaAtuacao());
        if (areaAtuacao.getFotoAreaAtuacao() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(areaAtuacao.getFotoAreaAtuacao(), 0, areaAtuacao.getFotoAreaAtuacao().length);
            ivAdapterImagemListaAreaAtuacao.setImageBitmap(bitmap);
        }
        return view;
    }

    public void setListaAreaAtuacao(List<AreaAtuacao> listaAreaAtuacao) {
        this.listaAreaAtuacao = listaAreaAtuacao;
        notifyDataSetChanged();
    }
}
