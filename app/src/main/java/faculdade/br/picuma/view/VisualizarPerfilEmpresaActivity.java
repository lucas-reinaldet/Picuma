package faculdade.br.picuma.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterGerenciadorFragmentVisualizarPerfilEmpresa;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.dialog.DialogDatePicker;
import faculdade.br.picuma.dialog.DialogTimePicker;
import faculdade.br.picuma.fragment.FragmentListaFotoGaleriaVisualizarEmpresa;
import faculdade.br.picuma.fragment.FragmentListaGaleriaVisualizarEmpresa;
import faculdade.br.picuma.fragment.FragmentListaGrupoServicoPrestadoVisualizarEmpresa;
import faculdade.br.picuma.fragment.FragmentListaServicosPrestadosVisualizarEmpresa;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

public class VisualizarPerfilEmpresaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TabLayout tabLayoutVisualizarPerfilEmpresa;
    private ViewPager viewPagerVisualizarPerfilEmpresa;
    private VisualizarEmpresaControl visualizarEmpresaControl;
    private FragmentManager fragmentManager;
    private DialogTimePicker dialogTimePicker;
    private boolean dialogAberto = false;
    private EditText etDialogHorarioEscolhidaAgendamento;
    private EditText etDialogHorarioTerminoAgendamento;
    private DialogDatePicker dialogDatePicker;
    private AlertDialog dialogAgendamento;
    private EditText etDialogDataEscolhidaAgendamento;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    public DialogTimePicker getDialogTimePicker() {
        if (dialogTimePicker == null) {
            dialogTimePicker = new DialogTimePicker();
        }
        return dialogTimePicker;
    }

    public DialogDatePicker getDialogDatePicker() {
        if (dialogDatePicker == null) {
            dialogDatePicker = new DialogDatePicker();
        }
        return dialogDatePicker;
    }
    
    private int[] iconTab = {
            R.drawable.ic_perfil,
            R.drawable.ic_servicos,
            R.drawable.ic_classificacao,
            R.drawable.ic_galeria
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_empresa);
        setTitle(getVisualizarEmpresaControl().getEmpresa().getNomeFantasia());
        tabLayoutVisualizarPerfilEmpresa = findViewById(R.id.tabLayoutVisualizarPerfilEmpresa);
        viewPagerVisualizarPerfilEmpresa = findViewById(R.id.viewPagerPerfilCliente);
        viewPagerVisualizarPerfilEmpresa.setAdapter(new AdapterGerenciadorFragmentVisualizarPerfilEmpresa(getSupportFragmentManager(), getResources().getStringArray(R.array.title_tab_visualizar_perfil_empresa)));
        tabLayoutVisualizarPerfilEmpresa.setupWithViewPager(viewPagerVisualizarPerfilEmpresa);

        for (int i = 0; i < tabLayoutVisualizarPerfilEmpresa.getTabCount(); i++) {
            tabLayoutVisualizarPerfilEmpresa.getTabAt(i).setIcon(iconTab[i]);
        }
        getVisualizarEmpresaControl().setFragmentManager(getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        if (getVisualizarEmpresaControl().isListadoFotoGaleria()) {
            if (getVisualizarEmpresaControl().isApresentandoFotosGaleria()) {
                fragmentManager = getSupportFragmentManager();
                getVisualizarEmpresaControl().setApresentandoFotosGaleria(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentListaFotoGaleriaVisualizarEmpresa(), "fotos_galeria");
                fragmentTransaction.commit();
            } else {
                fragmentManager = getSupportFragmentManager();
                getVisualizarEmpresaControl().setListadoFotoGaleria(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_galeria_visualizar_perfil_empresa, new FragmentListaGaleriaVisualizarEmpresa(), "galeria");
                fragmentTransaction.commit();
            }
        } else if (getVisualizarEmpresaControl().isListadoServicoPrestado()) {
            if (getVisualizarEmpresaControl().getListadoProfissionaisPorServico()) {
                fragmentManager = getSupportFragmentManager();
                getVisualizarEmpresaControl().setListadoProfissionaisPorServico(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaServicosPrestadosVisualizarEmpresa(), "servicos_prestados");
                fragmentTransaction.commit();
            } else {
                fragmentManager = getSupportFragmentManager();
                getVisualizarEmpresaControl().setListadoServicoPrestado(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_servicos_visualizar_perfil_empresa, new FragmentListaGrupoServicoPrestadoVisualizarEmpresa(), "grupos_servicos_prestados");
                fragmentTransaction.commit();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void openTimePicker() {
        getDialogTimePicker().show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!dialogAberto) {
            getVisualizarEmpresaControl().getDadosAgendamento().setDataMarcada(Util.getStringDataToCalender(dayOfMonth + "/" + month + "/" + year));
            openTimePicker();
        } else {
            getVisualizarEmpresaControl().getDadosAgendamento().setDataMarcada(Util.getStringDataToCalender(dayOfMonth + "/" + month + "/" + year));
            etDialogDataEscolhidaAgendamento.setText(Util.formatacaoCalendarDataToString(getVisualizarEmpresaControl().getDadosAgendamento().getDataMarcada()));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!dialogAberto) {
            getVisualizarEmpresaControl().getDadosAgendamento().setHorarioInicio(Util.getStringTimeToCalender(hourOfDay, minute));
            Calendar gc = Util.getStringTimeToCalender(hourOfDay, minute);
            gc.add(Calendar.MINUTE, getVisualizarEmpresaControl().getDadosAgendamento().getServicoPrestado().getTempoAproxServico());
            getVisualizarEmpresaControl().getDadosAgendamento().setHorarioFim(gc);
            chamarDialogConfirmacaoAgendamento();
        } else {
            getVisualizarEmpresaControl().getDadosAgendamento().setHorarioInicio(Util.getStringTimeToCalender(hourOfDay, minute));
            Calendar gc = Util.getStringTimeToCalender(hourOfDay, minute);
            gc.add(Calendar.MINUTE, getVisualizarEmpresaControl().getDadosAgendamento().getServicoPrestado().getTempoAproxServico());
            getVisualizarEmpresaControl().getDadosAgendamento().setHorarioFim(gc);
            etDialogHorarioEscolhidaAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(getVisualizarEmpresaControl().getDadosAgendamento().getHorarioInicio()));
            etDialogHorarioTerminoAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(getVisualizarEmpresaControl().getDadosAgendamento().getHorarioFim()));
        }
    }

    private void chamarDialogConfirmacaoAgendamento() {
        dialogAberto = true;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_horario_marcado, null);
        TextView tvDialogServicoEscolhidoAgendamento = mView.findViewById(R.id.tvDialogServicoEscolhidoAgendamento);
        LinearLayout llDialogDadosApresentadosFuncionarioAgendamento = mView.findViewById(R.id.llDialogDadosApresentadosFuncionarioAgendamento);
        ImageView ivFotoProfissionalEscolhidoAgendamento = mView.findViewById(R.id.ivFotoProfissionalEscolhidoAgendamento);
        TextView tvDialogFuncionarioEscolhidoAgendamento = mView.findViewById(R.id.tvDialogFuncionarioEscolhidoAgendamento);
        etDialogDataEscolhidaAgendamento = mView.findViewById(R.id.etDialogDataEscolhidaAgendamento);
        etDialogHorarioEscolhidaAgendamento = mView.findViewById(R.id.etDialogHorarioEscolhidaAgendamento);
        etDialogHorarioTerminoAgendamento = mView.findViewById(R.id.etDialogHorarioTerminoAgendamento);
        TextView tvDialogCustoAgendamento = mView.findViewById(R.id.tvDialogCustoAgendamento);
        Button btDialogCancelarCadastroAgendamento = mView.findViewById(R.id.btDialogCancelarCadastroAgendamento);
        Button btDialogAgendarCadastroAgendamento = mView.findViewById(R.id.btDialogAgendarCadastroAgendamento);
        tvDialogServicoEscolhidoAgendamento.setText(getVisualizarEmpresaControl().getDadosAgendamento().getServicoPrestado().getServico().getServico());

        if (getVisualizarEmpresaControl().getDadosAgendamento().getFuncionario() != null) {
            if (getVisualizarEmpresaControl().getDadosAgendamento().getFuncionario().getFotoFuncionario() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(getVisualizarEmpresaControl().getDadosAgendamento().getFuncionario().getFotoFuncionario(), 0, getVisualizarEmpresaControl().getDadosAgendamento().getFuncionario().getFotoFuncionario().length);
                ivFotoProfissionalEscolhidoAgendamento.setImageBitmap(bitmap);
            }
            tvDialogFuncionarioEscolhidoAgendamento.setText(getVisualizarEmpresaControl().getDadosAgendamento().getFuncionario().getNomeFuncionario());
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            llDialogDadosApresentadosFuncionarioAgendamento.setLayoutParams(layoutParams);
        }

        tvDialogCustoAgendamento.setText(getVisualizarEmpresaControl().getDadosAgendamento().getServicoPrestado().getValorServico().toString());
        etDialogDataEscolhidaAgendamento.setText(Util.formatacaoCalendarDataToString(getVisualizarEmpresaControl().getDadosAgendamento().getDataMarcada()));
        etDialogHorarioEscolhidaAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(getVisualizarEmpresaControl().getDadosAgendamento().getHorarioInicio()));
        etDialogHorarioTerminoAgendamento.setText(Util.formatacaoCalendarParaHoraMinuto(getVisualizarEmpresaControl().getDadosAgendamento().getHorarioFim()));

        etDialogDataEscolhidaAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogDatePicker().show(getSupportFragmentManager(), "dialog");
            }
        });

        etDialogHorarioEscolhidaAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogTimePicker().show(getSupportFragmentManager(), "dialog");
            }
        });

        btDialogCancelarCadastroAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisualizarEmpresaControl().setDadosAgendamento(null);
                dialogAberto = false;
                dialogAgendamento.dismiss();
            }
        });

        btDialogAgendarCadastroAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisualizarEmpresaControl().cadastrarAgendamento()) {
                    Toast.makeText(getApplicationContext(), Constantes.M_INF_CADASTRO_AGENDAMENTO_REALIZADO, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), Constantes.M_FALHA_AO_CADASTRAR_AGENDAMENTO, Toast.LENGTH_SHORT).show();
                }
                dialogAberto = false;
                dialogAgendamento.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAgendamento = alertDialog.create();
        dialogAgendamento.show();

    }
}
