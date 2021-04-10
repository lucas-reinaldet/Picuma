package faculdade.br.picuma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.model.HorarioEmpresa;
import faculdade.br.picuma.util.Util;

public class AdapterListaHorarioEmpresa extends BaseAdapter {

    private List<HorarioEmpresa> listaHorarioEmpresa = new ArrayList<>();
    private Context context;

    public AdapterListaHorarioEmpresa(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaHorarioEmpresa.size();
    }

    @Override
    public HorarioEmpresa getItem(int position) {
        return listaHorarioEmpresa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaHorarioEmpresa.get(position).getIdHorarioEmpresa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_horario_empresa, parent, false);

        TextView etDiaSemanaVisualizarPerfilEmpresa = view.findViewById(R.id.etDiaSemanaVisualizarPerfilEmpresa);
        EditText etHorarioInicioExpedienteVisualizarPerfilEmpresa = view.findViewById(R.id.etHorarioInicioExpedienteVisualizarPerfilEmpresa);
        EditText etHorarioFimExpedienteVisualizarPerfilEmpresa = view.findViewById(R.id.etHorarioFimExpedienteVisualizarPerfilEmpresa);
        LinearLayout llHorarioIntervaloVisualizarPerfilEmpresa = view.findViewById(R.id.llHorarioIntervaloVisualizarPerfilEmpresa);
        EditText etHorarioInicioIntervaloVisualizarPerfilEmpresa = view.findViewById(R.id.etHorarioInicioIntervaloVisualizarPerfilEmpresa);
        EditText etHorarioFimIntervaloVisualizarPerfilEmpresa = view.findViewById(R.id.etHorarioFimIntervaloVisualizarPerfilEmpresa);

        HorarioEmpresa horarioEmpresa = listaHorarioEmpresa.get(position);

        etDiaSemanaVisualizarPerfilEmpresa.setText(horarioEmpresa.getDiaSemana().getDiaSemana());
        etHorarioInicioExpedienteVisualizarPerfilEmpresa.setText(Util.formatacaoCalendarParaHoraMinuto(horarioEmpresa.getInicioExpediente()));
        etHorarioFimExpedienteVisualizarPerfilEmpresa.setText(Util.formatacaoCalendarParaHoraMinuto(horarioEmpresa.getFimExpediente()));

        if (horarioEmpresa.getInicioIntervalo() != null && horarioEmpresa.getFimIntervalo() != null) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llHorarioIntervaloVisualizarPerfilEmpresa.setLayoutParams(layoutParams);

            etHorarioInicioIntervaloVisualizarPerfilEmpresa.setText(Util.formatacaoCalendarParaHoraMinuto(horarioEmpresa.getInicioIntervalo()));
            etHorarioFimIntervaloVisualizarPerfilEmpresa.setText(Util.formatacaoCalendarParaHoraMinuto(horarioEmpresa.getFimIntervalo()));
        }
        return view;
    }

    public void setListaHorarioEmpresa(List<HorarioEmpresa> listaHorarioEmpresa) {
        this.listaHorarioEmpresa = listaHorarioEmpresa;
        notifyDataSetChanged();
    }
}
