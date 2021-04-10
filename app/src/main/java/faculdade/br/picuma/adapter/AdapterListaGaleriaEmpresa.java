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
import faculdade.br.picuma.model.Galeria;

public class AdapterListaGaleriaEmpresa extends BaseAdapter {

    private List<Galeria> listaGaleria = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public AdapterListaGaleriaEmpresa(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaGaleria.size();
    }

    @Override
    public Galeria getItem(int position) {
        return listaGaleria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaGaleria.get(position).getIdGaleria();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_lista_galeria_empresa, null);
        }
        ImageView ivAdapterImagemListaGaleria = v.findViewById(R.id.ivAdapterImagemListaGaleriaEmpresa);
        TextView tvAdapterGrupoServicoListaGaleriaEmpresa = v.findViewById(R.id.tvAdapterGrupoServicoListaGaleriaEmpresa);

        Galeria galeria = listaGaleria.get(position);

        if (galeria.getGrupoServico().getFotoGrupoServico() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(galeria.getGrupoServico().getFotoGrupoServico(), 0, galeria.getGrupoServico().getFotoGrupoServico().length);
            ivAdapterImagemListaGaleria.setImageBitmap(bitmap);
        }
        tvAdapterGrupoServicoListaGaleriaEmpresa.setText(galeria.getGrupoServico().getGrupoServico());
        return v;
    }

    public void setListaGaleria(List<Galeria> listaGaleria) {
        this.listaGaleria = listaGaleria;
        notifyDataSetChanged();
    }
}
