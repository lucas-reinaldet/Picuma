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
import faculdade.br.picuma.model.Favoritos;

public class AdapterListaFavoritos extends BaseAdapter {

    private List<Favoritos> listaFavoritos = new ArrayList<>();
    private Context context;

    public AdapterListaFavoritos(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaFavoritos.size();
    }

    @Override
    public Favoritos getItem(int position) {
        return listaFavoritos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaFavoritos.get(position).getIdEmpresa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_favoritos, parent, false);

        ImageView ivAdapterLogoEmpresaFavoritos = view.findViewById(R.id.ivAdapterLogoEmpresaFavoritos);
        TextView tvAdapterNomeEmpresaFavoritos = view.findViewById(R.id.tvAdapterNomeEmpresaFavoritos);

        Favoritos favoritos = listaFavoritos.get(position);

        tvAdapterNomeEmpresaFavoritos.setText(favoritos.getNomeFantasia());

        if (favoritos.getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(favoritos.getLogoEmpresa(), 0, favoritos.getLogoEmpresa().length);
            ivAdapterLogoEmpresaFavoritos.setImageBitmap(bitmap);
        } else if (favoritos.getFotoAreaAtuacao() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(favoritos.getFotoAreaAtuacao(), 0, favoritos.getFotoAreaAtuacao().length);
            ivAdapterLogoEmpresaFavoritos.setImageBitmap(bitmap);        }
        return view;
    }

    public void setListaFavoritos(List<Favoritos> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
        notifyDataSetChanged();
    }
}
