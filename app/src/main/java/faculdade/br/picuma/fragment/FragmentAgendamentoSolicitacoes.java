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
import android.widget.Toast;

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
public class FragmentAgendamentoSolicitacoes extends Fragment implements AdapterView.OnItemClickListener {

    private AdapterListaHorarioMarcado adapterListaHorario;
    private ListView lvAgendamentoAbertos;
    private List<HorarioMarcado> listaHorariosEmAberto;
    private AlertDialog dialogAlert;
    private ImageView ivDialogFotoAgendamento;
    private TextView tvDialogNomeAgendamento;
    private ListView lvDialogContatoAgendamento;
    private TextView tvDialogServicoAgendamento;
    private TextView tvDialogDataAgendamento;
    private TextView tvDialogInicioHorarioAgendamento;
    private TextView tvDialogHorarioFimAgendamento;
    private TextView tvDialogProfissionalAgendamento;
    private Button btDialogCancelarAgendamento;
    private Button btDialogRecusarAgendamento;
    private Button btDialogConfirmarAgendamento;
    private AdapterListaContato adapterListaContato;
    private LinearLayout llDialogProfissionalEscolhido;

    public FragmentAgendamentoSolicitacoes() {
        super();
    }


    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_agendamento_abertos, container, false);

        lvAgendamentoAbertos = v.findViewById(R.id.lvAgendamentoAbertos);
        adapterListaHorario = new AdapterListaHorarioMarcado(getContext(), true);
        lvAgendamentoAbertos.setAdapter(adapterListaHorario);
        lvAgendamentoAbertos.setOnItemClickListener(this);

        listaHorariosEmAberto = new ArrayList<>();
        if (getEmpresaControl().getListaHorarioMarcado() != null) {
            for (HorarioMarcado horarioMarcado : getEmpresaControl().getListaHorarioMarcado()) {
                if (horarioMarcado.getStatus().equals(Constantes.STATUS_ABERTO)) {
                    listaHorariosEmAberto.add(horarioMarcado);
                }
            }
        }

        if (listaHorariosEmAberto.size() > 0) {
            updateLista(listaHorariosEmAberto);
        }

        return v;
    }

    private void updateLista(List<HorarioMarcado> listaHorarioMarcado) {
        adapterListaHorario.setListaHorarioMarcado(listaHorarioMarcado);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_dados_agendamentos_aberto, null);

        ivDialogFotoAgendamento = mView.findViewById(R.id.ivDialogFotoAgendamentoA);
        tvDialogNomeAgendamento = mView.findViewById(R.id.tvDialogNomeAgendamentoA);
        lvDialogContatoAgendamento = mView.findViewById(R.id.lvDialogContatoAgendamentoA);
        tvDialogServicoAgendamento = mView.findViewById(R.id.tvDialogServicoAgendamentoA);
        tvDialogDataAgendamento = mView.findViewById(R.id.tvDialogDataAgendamentoA);
        tvDialogInicioHorarioAgendamento = mView.findViewById(R.id.tvDialogInicioHorarioAgendamentoA);
        tvDialogHorarioFimAgendamento = mView.findViewById(R.id.tvDialogHorarioFimAgendamentoA);
        llDialogProfissionalEscolhido = mView.findViewById(R.id.llDialogProfissionalEscolhidoA);
        tvDialogProfissionalAgendamento = mView.findViewById(R.id.tvDialogProfissionalAgendamentoA);

        btDialogCancelarAgendamento = mView.findViewById(R.id.btDialogCancelarAgendamentoA);
        btDialogRecusarAgendamento = mView.findViewById(R.id.btDialogRecusarAgendamentoA);
        btDialogConfirmarAgendamento = mView.findViewById(R.id.btDialogConfirmarAgendamentoA);

        final HorarioMarcado horarioMarcado = listaHorariosEmAberto.get(position);

        if (horarioMarcado.getCliente().getFotoCliente() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(horarioMarcado.getCliente().getFotoCliente(), 0, horarioMarcado.getCliente().getFotoCliente().length);
            ivDialogFotoAgendamento.setImageBitmap(bitmap);
        } else {
            ivDialogFotoAgendamento.setImageResource(R.drawable.imagem_padrao_sem_foto);
        }
        tvDialogNomeAgendamento.setText(horarioMarcado.getCliente().getNomeCliente());

        adapterListaContato = new AdapterListaContato(getContext());
        lvDialogContatoAgendamento.setAdapter(adapterListaContato);
        adapterListaContato.setListaContato(horarioMarcado.getCliente().getListaContato());

        tvDialogServicoAgendamento.setText(horarioMarcado.getServicoPrestado().getServico().getServico());
        tvDialogDataAgendamento.setText(Util.formatacaoCalendarDataToString(horarioMarcado.getDataMarcada()));
        tvDialogInicioHorarioAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioInicio()));
        tvDialogHorarioFimAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(horarioMarcado.getHorarioFim()));

        if (horarioMarcado.getFuncionario() != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llDialogProfissionalEscolhido.setLayoutParams(layoutParams);

            tvDialogProfissionalAgendamento.setText(horarioMarcado.getFuncionario().getNomeFuncionario());
        }

        btDialogCancelarAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        btDialogRecusarAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEmpresaControl().recusarAgendamento(horarioMarcado)) {
                    listaHorariosEmAberto.remove(position);
                    adapterListaHorario.setListaHorarioMarcado(listaHorariosEmAberto);
                } else {
                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_RECUSAR_AGENDAMENTO, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });

        btDialogConfirmarAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEmpresaControl().confirmarAgendamento(horarioMarcado)) {
                    listaHorariosEmAberto.remove(position);
                    adapterListaHorario.setListaHorarioMarcado(listaHorariosEmAberto);
                } else {
                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_ACEITAR_AGENDAMENTO, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();
    }
}
