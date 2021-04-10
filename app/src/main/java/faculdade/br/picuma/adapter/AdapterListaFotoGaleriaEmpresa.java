package faculdade.br.picuma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.model.FotoGaleria;

public class AdapterListaFotoGaleriaEmpresa extends BaseAdapter {

    private List<FotoGaleria> listaFotoGaleria;
    private Context context;
    private LayoutInflater inflater;

    public AdapterListaFotoGaleriaEmpresa(Context context, List<FotoGaleria> listaFotoGaleria) {
        this.context = context;
        this.listaFotoGaleria = listaFotoGaleria;
    }

    @Override
    public int getCount() {
        return listaFotoGaleria.size();
    }

    @Override
    public FotoGaleria getItem(int position) {
        return listaFotoGaleria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaFotoGaleria.get(position).getIdFotoGaleria();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_lista_foto_galeria, null);
        }
        ImageView ivAdapterAntesListaFotoGaleriaEmpresa = v.findViewById(R.id.ivAdapterAntesListaFotoGaleriaEmpresa);
        ImageView ivAdapterDepoisListaFotoGaleriaEmpresa = v.findViewById(R.id.ivAdapterDepoisListaFotoGaleriaEmpresa);

        FotoGaleria fotoGaleria = listaFotoGaleria.get(position);

        if (fotoGaleria.getFotoAntesGaleria() != null && fotoGaleria.getFotoDepoisGaleria() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(fotoGaleria.getFotoAntesGaleria(), 0, fotoGaleria.getFotoAntesGaleria().length);
            ivAdapterAntesListaFotoGaleriaEmpresa.setImageBitmap(bitmap);

            bitmap = BitmapFactory.decodeByteArray(fotoGaleria.getFotoDepoisGaleria(), 0, fotoGaleria.getFotoDepoisGaleria().length);
            ivAdapterDepoisListaFotoGaleriaEmpresa.setImageBitmap(bitmap);
        }
        return v;
    }

    public void setListaFotoGaleria(List<FotoGaleria> listaFotoGaleria) {
        this.listaFotoGaleria = listaFotoGaleria;
        notifyDataSetChanged();
    }
}
