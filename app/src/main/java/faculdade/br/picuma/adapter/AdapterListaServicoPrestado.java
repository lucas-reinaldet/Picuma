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
import faculdade.br.picuma.model.ServicoPrestado;

public class AdapterListaServicoPrestado extends BaseAdapter {

    private Context context;
    private List<ServicoPrestado> listaServicoPrestado = new ArrayList<>();

    public AdapterListaServicoPrestado(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaServicoPrestado.size();
    }

    @Override
    public ServicoPrestado getItem(int position) {
        return listaServicoPrestado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaServicoPrestado.get(position).getIdServicoPrestado();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_servico_prestado, parent, false);

        TextView tvNomeServicoListaServicoPrestado = view.findViewById(R.id.tvNomeServicoListaServicoPrestado);
        TextView tvInformacoesServicoListaServicoPrestado = view.findViewById(R.id.tvInformacoesServicoListaServicoPrestado);
        TextView tvTempoServicoListaServicoPrestado = view.findViewById(R.id.tvTempoServicoListaServicoPrestado);
        TextView tvValorServicoListaServicoPrestado = view.findViewById(R.id.tvValorServicoListaServicoPrestado);

        ServicoPrestado servicoPrestado = listaServicoPrestado.get(position);

        tvNomeServicoListaServicoPrestado.setText(servicoPrestado.getServico().getServico());
        tvInformacoesServicoListaServicoPrestado.setText(servicoPrestado.getInformacoesServico());
        tvTempoServicoListaServicoPrestado.setText(String.valueOf(servicoPrestado.getTempoAproxServico()));
        tvValorServicoListaServicoPrestado.setText(servicoPrestado.getValorServico().toString());
        return view;
    }

    public void setListaServicoPrestado(List<ServicoPrestado> listaServicoPrestado) {
        this.listaServicoPrestado = listaServicoPrestado;
        notifyDataSetChanged();
    }
}
