package faculdade.br.picuma.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaContato;
import faculdade.br.picuma.adapter.AdapterListaHorarioMarcado;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAgendamentoRecusados extends Fragment implements AdapterView.OnItemClickListener {

    public FragmentAgendamentoRecusados() {
        super();
    }

    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    private AdapterListaHorarioMarcado adapter;
    private ListView lvAgendamentoRecusados;
    private List<HorarioMarcado> listaHorariosRecusados;
    private AlertDialog dialogAlert;
    private ImageView ivDialogFotoAgendamentoRCR;
    private TextView tvDialogNomeAgendamentoRCR;
    private ListView lvDialogContatoAgendamentoRCR;
    private TextView tvDialogServicoAgendamentoRCR;
    private TextView tvDialogDataAgendamentoRCR;
    private TextView tvDialogInicioHorarioAgendamentoRCR;
    private TextView tvDialogHorarioFimAgendamentoRCR;
    private TextView tvDialogProfissionalAgendamentoRCR;
    private Button btDialogFecharAgendamentoRCR;
    private Button btDialogServicoRealizadoAgendamentoRCR;
    private AdapterListaContato adapterListaContato;
    private LinearLayout llDialogProfissionalEscolhidoRCR;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_agendamento_recusados, container, false);

        lvAgendamentoRecusados = v.findViewById(R.id.lvAgendamentoRecusados);
        adapter = new AdapterListaHorarioMarcado(getContext(),true);
        lvAgendamentoRecusados.setAdapter(adapter);
        lvAgendamentoRecusados.setOnItemClickListener(this);

        listaHorariosRecusados = new ArrayList<>();
        if (getEmpresaControl().getListaHorarioMarcado() != null) {
            for (HorarioMarcado horarioMarcado : getEmpresaControl().getListaHorarioMarcado()) {
                if (horarioMarcado.getStatus().equals(Constantes.STATUS_RECUSADO)) {
                    listaHorariosRecusados.add(horarioMarcado);
                }
            }
        }

        if (listaHorariosRecusados.size() > 0) {
            updateLista(listaHorariosRecusados);
        }

        return v;
    }

    private  void updateLista(List<HorarioMarcado> listaHorarioMarcado) {
        adapter.setListaHorarioMarcado(listaHorarioMarcado);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_dados_agendamentos_recusados_confirmados_realizados, null);

        ivDialogFotoAgendamentoRCR = mView.findViewById(R.id.ivDialogFotoAgendamentoRCR);
        tvDialogNomeAgendamentoRCR = mView.findViewById(R.id.tvDialogNomeAgendamentoRCR);
        lvDialogContatoAgendamentoRCR = mView.findViewById(R.id.lvDialogContatoAgendamentoRCR);
        tvDialogServicoAgendamentoRCR = mView.findViewById(R.id.tvDialogServicoAgendamentoRCR);
        tvDialogDataAgendamentoRCR = mView.findViewById(R.id.tvDialogDataAgendamentoRCR);
        tvDialogInicioHorarioAgendamentoRCR = mView.findViewById(R.id.tvDialogInicioHorarioAgendamentoRCR);
        tvDialogHorarioFimAgendamentoRCR = mView.findViewById(R.id.tvDialogHorarioFimAgendamentoRCR);
        llDialogProfissionalEscolhidoRCR = mView.findViewById(R.id.llDialogProfissionalEscolhidoRCR);
        tvDialogProfissionalAgendamentoRCR = mView.findViewById(R.id.tvDialogProfissionalAgendamentoRCR);

        btDialogFecharAgendamentoRCR = mView.findViewById(R.id.btDialogFecharAgendamentoRCR);
        btDialogServicoRealizadoAgendamentoRCR = mView.findViewById(R.id.btDialogServicoRealizadoAgendamentoRCR);

        btDialogServicoRealizadoAgendamentoRCR.setEnabled(false);

        final HorarioMarcado horarioMarcado = listaHorariosRecusados.get(position);

        if (horarioMarcado.getCliente().getFotoCliente() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getCliente().getFotoCliente(), 0, horarioMarcado.getCliente().getFotoCliente().length);
            ivDialogFotoAgendamentoRCR.setImageBitmap(bitmap);
        } else {
            ivDialogFotoAgendamentoRCR.setImageResource(R.drawable.imagem_padrao_sem_foto);
        }
        tvDialogNomeAgendamentoRCR.setText(horarioMarcado.getCliente().getNomeCliente());

        adapterListaContato = new AdapterListaContato(getContext());
        lvDialogContatoAgendamentoRCR.setAdapter(adapterListaContato);
        adapterListaContato.setListaContato(horarioMarcado.getCliente().getListaContato());

        tvDialogServicoAgendamentoRCR.setText(horarioMarcado.getServicoPrestado().getServico().getServico());
        tvDialogDataAgendamentoRCR.setText(Util.formatacaoCalendarDataToString(horarioMarcado.getDataMarcada()));
        tvDialogInicioHorarioAgendamentoRCR.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioInicio()));
        tvDialogHorarioFimAgendamentoRCR.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioFim()));

        if (horarioMarcado.getFuncionario() != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llDialogProfissionalEscolhidoRCR.setLayoutParams(layoutParams);

            tvDialogProfissionalAgendamentoRCR.setText(horarioMarcado.getFuncionario().getNomeFuncionario());
        }

        btDialogFecharAgendamentoRCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();
    }
}
