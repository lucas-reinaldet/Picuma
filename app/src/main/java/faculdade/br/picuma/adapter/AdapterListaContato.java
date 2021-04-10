package faculdade.br.picuma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.model.Contato;

public class AdapterListaContato extends BaseAdapter {

    private List<Contato> contatos = new ArrayList<>();
    private Context context;

    public AdapterListaContato(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Contato getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getIdContato();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_contato, parent, false);

        TextView tvListaTipoContato = view.findViewById(R.id.tvListaTipoContato);
        TextView tvListaContato = view.findViewById(R.id.tvListaContato);

        Contato contato = contatos.get(position);

        tvListaTipoContato.setText(contato.getTipoContato());
        tvListaContato.setText(contato.getContato());

        return view;
    }

    public void setListaContato(List<Contato> listaContato) {
        this.contatos = listaContato;
        notifyDataSetChanged();
    }
}
