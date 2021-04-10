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
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

public class AdapterListaHorarioMarcado extends BaseAdapter {

    private List<HorarioMarcado> listaHorarioMarcado = new ArrayList<>();
    private Context context;
    private boolean isEmpresa;

    public AdapterListaHorarioMarcado(Context context, boolean isEmpresa) {
        this.context = context;
        this.isEmpresa = isEmpresa;
    }

    @Override
    public int getCount() {
        return listaHorarioMarcado.size();
    }

    @Override
    public HorarioMarcado getItem(int position) {
        return listaHorarioMarcado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaHorarioMarcado.get(position).getIdhorarioMarcado();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_horario_marcado, parent, false);

        ImageView ivAdapterFotoHorarioMarcado = view.findViewById(R.id.ivAdapterFotoHorarioMarcado);
        TextView tvAdapterNomeHorarioMarcado = view.findViewById(R.id.tvAdapterNomeHorarioMarcado);
        TextView tvAdapterServicoHorarioMarcado = view.findViewById(R.id.tvAdapterServicoHorarioMarcado);
        TextView tvAdapterDataHorarioMarcado = view.findViewById(R.id.tvAdapterDataHorarioMarcado);
        TextView tvAdapterHorarioInicioHorarioMarcado = view.findViewById(R.id.tvAdapterHorarioInicioHorarioMarcado);
        TextView tvAdapterHorarioFimHorarioMarcado = view.findViewById(R.id.tvAdapterHorarioFimHorarioMarcado);
        TextView tvAdapterStatusHorarioMarcado = view.findViewById(R.id.tvAdapterStatusHorarioMarcado);

        HorarioMarcado horarioMarcado = listaHorarioMarcado.get(position);

        if (isEmpresa) {
            if (horarioMarcado.getCliente().getFotoCliente() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getCliente().getFotoCliente(), 0, horarioMarcado.getCliente().getFotoCliente().length);
                ivAdapterFotoHorarioMarcado.setImageBitmap(bitmap);
            } else {
                ivAdapterFotoHorarioMarcado.setImageResource(R.drawable.imagem_padrao_sem_foto);
            }
            tvAdapterNomeHorarioMarcado.setText(horarioMarcado.getCliente().getNomeCliente());
        } else {
            if (horarioMarcado.getEmpresa().getLogoEmpresa() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getEmpresa().getLogoEmpresa(), 0, horarioMarcado.getEmpresa().getLogoEmpresa().length);
                ivAdapterFotoHorarioMarcado.setImageBitmap(bitmap);
            } else if (horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao(), 0, horarioMarcado.getEmpresa().getAreaAtuacao().getFotoAreaAtuacao().length);
                ivAdapterFotoHorarioMarcado.setImageBitmap(bitmap);
            }
            tvAdapterNomeHorarioMarcado.setText(horarioMarcado.getEmpresa().getNomeFantasia());
        }

        tvAdapterServicoHorarioMarcado.setText(horarioMarcado.getServicoPrestado().getServico().getServico());
        tvAdapterDataHorarioMarcado.setText(Util.formatacaoCalendarDataToString(horarioMarcado.getDataMarcada()));
        tvAdapterHorarioInicioHorarioMarcado.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioInicio()));
        tvAdapterHorarioFimHorarioMarcado.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioFim()));

        if (horarioMarcado.getStatus().equals(Constantes.STATUS_CONFIRMADO)) {
            tvAdapterStatusHorarioMarcado.setText(Constantes.TEXTO_STATUS_CONFIRMADO);
        } else if (horarioMarcado.getStatus().equals(Constantes.STATUS_RECUSADO)) {
            tvAdapterStatusHorarioMarcado.setText(Constantes.TEXTO_STATUS_RECUSADO);
        } else if (horarioMarcado.getStatus().equals(Constantes.STATUS_SERVICO_REALIZADO)) {
            if (isEmpresa) {
                tvAdapterStatusHorarioMarcado.setText(Constantes.TEXTO_STATUS_REALIZADO_EMPRESA);
            } else {
                tvAdapterStatusHorarioMarcado.setText(Constantes.TEXTO_STATUS_REALIZADO_CLIENTE);
            }
        } else if (horarioMarcado.getStatus().equals(Constantes.STATUS_ABERTO)) {
            tvAdapterStatusHorarioMarcado.setText(Constantes.TEXTO_STATUS_ABERTO);
        }
        return view;
    }

    public void setListaHorarioMarcado(List<HorarioMarcado> listaHorarioMarcado) {
        this.listaHorarioMarcado = listaHorarioMarcado;
        notifyDataSetChanged();
    }
}
