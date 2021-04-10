package faculdade.br.picuma.view;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaContato;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.dialog.DialogTimePicker;
import faculdade.br.picuma.model.AreaAtuacao;
import faculdade.br.picuma.model.Contato;
import faculdade.br.picuma.model.DiaSemana;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.model.HorarioEmpresa;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

import static android.os.Environment.getExternalStorageDirectory;

public class CadastroEmpresaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemLongClickListener {

    //Botoes do topo do cadastro
    private Button btTopoCadastroEmpresaDados;
    private Button btTopoCadastroEmpresaContato;
    private Button btTopoCadastroEmpresaHorario;
    private Button btTopoCadastroEmpresaEndereco;

    //Constantes
    private static final String TEXTO_BOTAO_FINAL_CADASTRO = "Finalizar";
    private static final String TEXTO_BOTAO_PROXIMO_CADASTRO = "Próximo";

    //Variaveis de controle
    private boolean responsavelDados = true;
    private boolean responsavelContato = false;
    private boolean responsavelHorario = false;
    private boolean responsavelEndereco = false;

    private LinearLayout linearLayoutCadastroEmpresaDados;
    private LinearLayout linearLayoutCadastroEmpresaContato;
    private LinearLayout linearLayoutCadastroEmpresaHorario;
    private LinearLayout linearLayoutCadastroEmpresaEndereco;

    //Variaveis relacionadas ao formulario de dados do cadastro de empresa

    private EditText etNomeEmpresaCadastro;
    private EditText etCpfOuCnpjEmpresaCadastro;
    private EditText etUsuarioCadastroEmpresa;
    private EditText etSenhaCadastroEmpresa;
    private EditText etLoginGoogleCadastroEmpresa;

    private RadioButton rbCnpjEmpresaCadastro;
    private RadioButton rbCpfEmpresaCadastro;

    private ImageButton ibApresentacaoPreviaFotoEmpresa;

    private Button btCancelarCadastroEmpresa;
    private Button btParaCadastrarEmpresa;

    private String caminhoImagemCamera;

    private File arquivoImagemEmpresa;
    private byte[] byteImagem = null;

    private AreaAtuacao areaAtuacaoParaCadastro;

    //Variaveis relacionadas ao formulario de horario do cadastro de empresa

    private RadioButton rbAtribuirHorarioGenericoATodosCadastroEmpresa;
    private RadioButton rbAtribuirHorarioIndividualmenteCadastroEmpresa;

    private LinearLayout llHorarioFuncionamentoGenericoCadastroEmpresa;
    private LinearLayout llHorarioFuncionamentoIndividualCadastroEmpresa;

    private EditText etHorarioInicioExpedienteGenericoCadastroEmpresa;
    private EditText etHorarioFimExpedienteGenericoCadastroEmpresa;
    private EditText etHorarioInicioIntervaloGenericoCadastroEmpresa;
    private EditText etHorarioFimIntervaloGenericoCadastroEmpresa;

    private CheckBox cbAtribuirHorarioGenericoDomingoCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoSegundaCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoTercaCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoQuartaCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoQuintaCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoSextaCadastroEmpresa;
    private CheckBox cbAtribuirHorarioGenericoSabadoCadastroEmpresa;

    private CheckBox cbHorarioSegundaCadastroEmpresa;
    private CheckBox cbHorarioTercaCadastroEmpresa;
    private CheckBox cbHorarioQuartaCadastroEmpresa;
    private CheckBox cbHorarioQuintaCadastroEmpresa;
    private CheckBox cbHorarioSextaCadastroEmpresa;
    private CheckBox cbHorarioSabadoCadastroEmpresa;
    private CheckBox cbHorarioDomingoCadastroEmpresa;

    private EditText etSegundaInicioExpedienteCadastroEmpresa;
    private EditText etSegundaFimExpedienteCadastroEmpresa;
    private EditText etSegundaInicioIntervaloCadastroEmpresa;
    private EditText etSegundaFimIntervaloCadastroEmpresa;

    private EditText etTercaInicioExpedienteCadastroEmpresa;
    private EditText etTercaFimExpedienteCadastroEmpresa;
    private EditText etTercaInicioIntervaloCadastroEmpresa;
    private EditText etTercaFimIntervaloCadastroEmpresa;

    private EditText etQuartaInicioExpedienteCadastroEmpresa;
    private EditText etQuartaFimExpedienteCadastroEmpresa;
    private EditText etQuartaInicioIntervaloCadastroEmpresa;
    private EditText etQuartaFimIntervaloCadastroEmpresa;

    private EditText etQuintaInicioExpedienteCadastroEmpresa;
    private EditText etQuintaFimExpedienteCadastroEmpresa;
    private EditText etQuintaInicioIntervaloCadastroEmpresa;
    private EditText etQuintaFimIntervaloCadastroEmpresa;

    private EditText etSextaInicioExpedienteCadastroEmpresa;
    private EditText etSextaFimExpedienteCadastroEmpresa;
    private EditText etSextaInicioIntervaloCadastroEmpresa;
    private EditText etSextaFimIntervaloCadastroEmpresa;

    private EditText etSabadoInicioExpedienteCadastroEmpresa;
    private EditText etSabadoFimExpedienteCadastroEmpresa;
    private EditText etSabadoInicioIntervaloCadastroEmpresa;
    private EditText etSabadoFimIntervaloCadastroEmpresa;

    private EditText etDomingoInicioExpedienteCadastroEmpresa;
    private EditText etDomingoFimExpedienteCadastroEmpresa;
    private EditText etDomingoInicioIntervaloCadastroEmpresa;
    private EditText etDomingoFimIntervaloCadastroEmpresa;

    private GregorianCalendar hora;

    //Variavel do formulario contato

    private Spinner spinnerDialogTipoContato;
    private Button btAdicionarContatoCadastroEmpresa;
    private EditText etDialogCadastroContato;
    private Button btDialogCancelarCadastroContato;
    private Button btDialogCadastrarContatoMaisUm;
    private Button btDialogCadastroContato;

    private ListView lvListaContatoCadastroEmpresa;
    private AdapterListaContato adapterListaContato;

    private AlertDialog dialogContato;

    private List<Contato> listContato = new ArrayList<>();

    private String tipoContato;

    private Spinner spinnerAreaAtuacaoCadastroEmpresa;

    //Variaveis do formulario de endereco

    private EditText etCepCadastroEmpresa;
    private EditText etNumeroCadastroEmpresa;
    private EditText etLogradouroCadastroEmpresa;
    private EditText etComplementoCadastroEmpresa;
    private EditText etBairroCadastroEmpresa;
    private EditText etCidadeCadastroEmpresa;
    private EditText etEstadoCadastroEmpresa;

    private DialogTimePicker timePickerDialog;
    private List<AreaAtuacao> listaAreaAtuacao;
    private EmpresaControl empresaControl;
    private RadioButton rbUnissexCadastroEmpresa;
    private RadioButton rbFemininoCadastroEmpresa;
    private RadioButton rbMasculinoCadastroEmpresa;

    public EmpresaControl getEmpresaControl() {

        if (empresaControl == null) {

            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);
        setTitle(Constantes.NOME_ACTIVITY_CADASTRO_EMPRESA);
        this.setConfiguracoesIniciais();
        this.setDadosParaFormularioDados();
        this.setDadosBotoes();
        this.setDadosParaFormularioContato();
        this.setDadosParaFormularioEndereco();
        this.setDadosParaFormularioHorario();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("dados", false)) {

            this.setDadosLoginGoogle(intent);
        }
    }

    private void setDadosParaFormularioHorario() {

        rbAtribuirHorarioGenericoATodosCadastroEmpresa = findViewById(R.id.rbAtribuirHorarioGenericoATodosCadastroEmpresa);
        rbAtribuirHorarioIndividualmenteCadastroEmpresa = findViewById(R.id.rbAtribuirHorarioIndividualmenteCadastroEmpresa);

        llHorarioFuncionamentoGenericoCadastroEmpresa = findViewById(R.id.llHorarioFuncionamentoGenericoCadastroEmpresa);
        llHorarioFuncionamentoIndividualCadastroEmpresa = findViewById(R.id.llHorarioFuncionamentoIndividualCadastroEmpresa);

        etHorarioInicioExpedienteGenericoCadastroEmpresa = findViewById(R.id.etHorarioInicioExpedienteGenericoCadastroEmpresa);
        etHorarioFimExpedienteGenericoCadastroEmpresa = findViewById(R.id.etHorarioFimExpedienteGenericoCadastroEmpresa);
        etHorarioInicioIntervaloGenericoCadastroEmpresa = findViewById(R.id.etHorarioInicioIntervaloGenericoCadastroEmpresa);
        etHorarioFimIntervaloGenericoCadastroEmpresa = findViewById(R.id.etHorarioFimIntervaloGenericoCadastroEmpresa);

        etHorarioInicioExpedienteGenericoCadastroEmpresa.setOnClickListener(this);
        etHorarioFimExpedienteGenericoCadastroEmpresa.setOnClickListener(this);
        etHorarioInicioIntervaloGenericoCadastroEmpresa.setOnClickListener(this);
        etHorarioFimIntervaloGenericoCadastroEmpresa.setOnClickListener(this);

        cbAtribuirHorarioGenericoDomingoCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoDomingoCadastroEmpresa);
        cbAtribuirHorarioGenericoSegundaCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoSegundaCadastroEmpresa);
        cbAtribuirHorarioGenericoTercaCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoTercaCadastroEmpresa);
        cbAtribuirHorarioGenericoQuartaCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoQuartaCadastroEmpresa);
        cbAtribuirHorarioGenericoQuintaCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoQuintaCadastroEmpresa);
        cbAtribuirHorarioGenericoSextaCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoSextaCadastroEmpresa);
        cbAtribuirHorarioGenericoSabadoCadastroEmpresa = findViewById(R.id.cbAtribuirHorarioGenericoSabadoCadastroEmpresa);

        cbHorarioSegundaCadastroEmpresa = findViewById(R.id.cbHorarioSegundaCadastroEmpresa);
        cbHorarioTercaCadastroEmpresa = findViewById(R.id.cbHorarioTercaCadastroEmpresa);
        cbHorarioQuartaCadastroEmpresa = findViewById(R.id.cbHorarioQuartaCadastroEmpresa);
        cbHorarioQuintaCadastroEmpresa = findViewById(R.id.cbHorarioQuintaCadastroEmpresa);
        cbHorarioSextaCadastroEmpresa = findViewById(R.id.cbHorarioSextaCadastroEmpresa);
        cbHorarioSabadoCadastroEmpresa = findViewById(R.id.cbHorarioSabadoCadastroEmpresa);
        cbHorarioDomingoCadastroEmpresa = findViewById(R.id.cbHorarioDomingoCadastroEmpresa);

        etSegundaInicioExpedienteCadastroEmpresa = findViewById(R.id.etSegundaInicioExpedienteCadastroEmpresa);
        etSegundaFimExpedienteCadastroEmpresa = findViewById(R.id.etSegundaFimExpedienteCadastroEmpresa);
        etSegundaInicioIntervaloCadastroEmpresa = findViewById(R.id.etSegundaInicioIntervaloCadastroEmpresa);
        etSegundaFimIntervaloCadastroEmpresa = findViewById(R.id.etSegundaFimIntervaloCadastroEmpresa);

        etSegundaInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etSegundaFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etSegundaInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etSegundaFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etTercaInicioExpedienteCadastroEmpresa = findViewById(R.id.etTercaInicioExpedienteCadastroEmpresa);
        etTercaFimExpedienteCadastroEmpresa = findViewById(R.id.etTercaFimExpedienteCadastroEmpresa);
        etTercaInicioIntervaloCadastroEmpresa = findViewById(R.id.etTercaInicioIntervaloCadastroEmpresa);
        etTercaFimIntervaloCadastroEmpresa = findViewById(R.id.etTercaFimIntervaloCadastroEmpresa);

        etTercaInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etTercaFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etTercaInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etTercaFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etQuartaInicioExpedienteCadastroEmpresa = findViewById(R.id.etQuartaInicioExpedienteCadastroEmpresa);
        etQuartaFimExpedienteCadastroEmpresa = findViewById(R.id.etQuartaFimExpedienteCadastroEmpresa);
        etQuartaInicioIntervaloCadastroEmpresa = findViewById(R.id.etQuartaInicioIntervaloCadastroEmpresa);
        etQuartaFimIntervaloCadastroEmpresa = findViewById(R.id.etQuartaFimIntervaloCadastroEmpresa);

        etQuartaInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etQuartaFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etQuartaInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etQuartaFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etQuintaInicioExpedienteCadastroEmpresa = findViewById(R.id.etQuintaInicioExpedienteCadastroEmpresa);
        etQuintaFimExpedienteCadastroEmpresa = findViewById(R.id.etQuintaFimExpedienteCadastroEmpresa);
        etQuintaInicioIntervaloCadastroEmpresa = findViewById(R.id.etQuintaInicioIntervaloCadastroEmpresa);
        etQuintaFimIntervaloCadastroEmpresa = findViewById(R.id.etQuintaFimIntervaloCadastroEmpresa);

        etQuintaInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etQuintaFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etQuintaInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etQuintaFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etSextaInicioExpedienteCadastroEmpresa = findViewById(R.id.etSextaInicioExpedienteCadastroEmpresa);
        etSextaFimExpedienteCadastroEmpresa = findViewById(R.id.etSextaFimExpedienteCadastroEmpresa);
        etSextaInicioIntervaloCadastroEmpresa = findViewById(R.id.etSextaInicioIntervaloCadastroEmpresa);
        etSextaFimIntervaloCadastroEmpresa = findViewById(R.id.etSextaFimIntervaloCadastroEmpresa);

        etSextaInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etSextaFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etSextaInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etSextaFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etSabadoInicioExpedienteCadastroEmpresa = findViewById(R.id.etSabadoInicioExpedienteCadastroEmpresa);
        etSabadoFimExpedienteCadastroEmpresa = findViewById(R.id.etSabadoFimExpedienteCadastroEmpresa);
        etSabadoInicioIntervaloCadastroEmpresa = findViewById(R.id.etSabadoInicioIntervaloCadastroEmpresa);
        etSabadoFimIntervaloCadastroEmpresa = findViewById(R.id.etSabadoFimIntervaloCadastroEmpresa);

        etSabadoInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etSabadoFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etSabadoInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etSabadoFimIntervaloCadastroEmpresa.setOnClickListener(this);

        etDomingoInicioExpedienteCadastroEmpresa = findViewById(R.id.etDomingoInicioExpedienteCadastroEmpresa);
        etDomingoFimExpedienteCadastroEmpresa = findViewById(R.id.etDomingoFimExpedienteCadastroEmpresa);
        etDomingoInicioIntervaloCadastroEmpresa = findViewById(R.id.etDomingoInicioIntervaloCadastroEmpresa);
        etDomingoFimIntervaloCadastroEmpresa = findViewById(R.id.etDomingoFimIntervaloCadastroEmpresa);

        etDomingoInicioExpedienteCadastroEmpresa.setOnClickListener(this);
        etDomingoFimExpedienteCadastroEmpresa.setOnClickListener(this);
        etDomingoInicioIntervaloCadastroEmpresa.setOnClickListener(this);
        etDomingoFimIntervaloCadastroEmpresa.setOnClickListener(this);

        rbAtribuirHorarioGenericoATodosCadastroEmpresa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                LinearLayout.LayoutParams layoutParamsLinearLayout;
                if (isChecked) {

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    llHorarioFuncionamentoGenericoCadastroEmpresa.setLayoutParams(layoutParamsLinearLayout);
                } else {

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    llHorarioFuncionamentoGenericoCadastroEmpresa.setLayoutParams(layoutParamsLinearLayout);
                }
            }
        });

        rbAtribuirHorarioIndividualmenteCadastroEmpresa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                LinearLayout.LayoutParams layoutParamsLinearLayout;
                if (isChecked) {

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    llHorarioFuncionamentoIndividualCadastroEmpresa.setLayoutParams(layoutParamsLinearLayout);
                } else {

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    llHorarioFuncionamentoIndividualCadastroEmpresa.setLayoutParams(layoutParamsLinearLayout);
                }
            }
        });
    }

    private void setDadosParaFormularioEndereco() {

        etCepCadastroEmpresa = findViewById(R.id.etCepCadastroEmpresa);
        etNumeroCadastroEmpresa = findViewById(R.id.etNumeroCadastroEmpresa);
        etLogradouroCadastroEmpresa = findViewById(R.id.etLogradouroCadastroEmpresa);
        etComplementoCadastroEmpresa = findViewById(R.id.etComplementoCadastroEmpresa);
        etBairroCadastroEmpresa = findViewById(R.id.etBairroCadastroEmpresa);
        etCidadeCadastroEmpresa = findViewById(R.id.etCidadeCadastroEmpresa);
        etEstadoCadastroEmpresa = findViewById(R.id.etEstadoCadastroEmpresa);

        etCepCadastroEmpresa.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    String dados = etCepCadastroEmpresa.getText().toString();

                    Endereco endereco = Util.buscaCep(dados);

                    if (endereco != null) {

                        etLogradouroCadastroEmpresa.setText(endereco.getLogradouro());
                        etBairroCadastroEmpresa.setText(endereco.getBairro());
                        etCidadeCadastroEmpresa.setText(endereco.getCidade());
                        etEstadoCadastroEmpresa.setText(endereco.getEstado());

                    } else {

                        Toast.makeText(getApplicationContext(), Constantes.M_INF_ENDERECO_N_ENCONTRADO, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void setDadosParaFormularioContato() {

        btAdicionarContatoCadastroEmpresa = findViewById(R.id.btAdicionarContatoCadastroEmpresa);
        btAdicionarContatoCadastroEmpresa.setOnClickListener(this);

        lvListaContatoCadastroEmpresa = findViewById(R.id.lvListaContatoCadastroEmpresa);

        adapterListaContato = new AdapterListaContato(this);

        lvListaContatoCadastroEmpresa.setAdapter(adapterListaContato);
        lvListaContatoCadastroEmpresa.setOnItemLongClickListener(this);

    }

    private void setConfiguracoesIniciais() {

        ScrollView scrollView = new ScrollView(this);
        FrameLayout.LayoutParams layoutParamsFrameLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParamsFrameLayout);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParamsLinearLayout);
        scrollView.addView(linearLayout);

        this.timePickerDialog = new DialogTimePicker();

        linearLayoutCadastroEmpresaDados = findViewById(R.id.linearLayoutCadastroEmpresaDados);
        linearLayoutCadastroEmpresaContato = findViewById(R.id.linearLayoutCadastroEmpresaContato);
        linearLayoutCadastroEmpresaHorario = findViewById(R.id.linearLayoutCadastroEmpresaHorario);
        linearLayoutCadastroEmpresaEndereco = findViewById(R.id.linearLayoutCadastroEmpresaEndereco);

    }

    public void setDadosParaFormularioDados() {

        etNomeEmpresaCadastro = findViewById(R.id.etNomeEmpresaCadastro);
        rbCpfEmpresaCadastro = findViewById(R.id.rbCpfEmpresaCadastro);
        etCpfOuCnpjEmpresaCadastro = findViewById(R.id.etCpfOuCnpjEmpresaCadastro);
        etUsuarioCadastroEmpresa = findViewById(R.id.etUsuarioCadastroEmpresa);
        etSenhaCadastroEmpresa = findViewById(R.id.etSenhaCadastroEmpresa);
        etLoginGoogleCadastroEmpresa = findViewById(R.id.etLoginGoogleCadastroEmpresa);
        rbUnissexCadastroEmpresa = findViewById(R.id.rbUnissexCadastroEmpresa);
        rbFemininoCadastroEmpresa = findViewById(R.id.rbFemininoCadastroEmpresa);
        rbMasculinoCadastroEmpresa = findViewById(R.id.rbMasculinoCadastroEmpresa);

        ibApresentacaoPreviaFotoEmpresa = findViewById(R.id.ibApresentacaoPreviaFotoEmpresa);
        ibApresentacaoPreviaFotoEmpresa.setOnClickListener(this);

        spinnerAreaAtuacaoCadastroEmpresa = findViewById(R.id.spinnerAreaAtuacaoCadastroEmpresa);

        listaAreaAtuacao = getEmpresaControl().getAreaAtuacao();

        listaAreaAtuacao = new ArrayList<>();
        AreaAtuacao areaAtuacao = new AreaAtuacao();
        areaAtuacao.setAreaAtuacao("Salão de beleza");
        listaAreaAtuacao.add(areaAtuacao);

        ArrayAdapter<AreaAtuacao> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaAreaAtuacao);
        ArrayAdapter<AreaAtuacao> spinnerAdapter = adapter;
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAreaAtuacaoCadastroEmpresa.setAdapter(spinnerAdapter);

        spinnerAreaAtuacaoCadastroEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaAtuacaoParaCadastro = (AreaAtuacao) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setDadosBotoes() {

        btCancelarCadastroEmpresa = findViewById(R.id.btCancelarCadastroEmpresa);
        btParaCadastrarEmpresa = findViewById(R.id.btParaCadastrarEmpresa);

        btTopoCadastroEmpresaDados = findViewById(R.id.btTopoCadastroEmpresaDados);
        btTopoCadastroEmpresaContato = findViewById(R.id.btTopoCadastroEmpresaContato);
        btTopoCadastroEmpresaHorario = findViewById(R.id.btTopoCadastroEmpresaHorario);
        btTopoCadastroEmpresaEndereco = findViewById(R.id.btTopoCadastroEmpresaEndereco);

        btCancelarCadastroEmpresa.setOnClickListener(this);
        btParaCadastrarEmpresa.setOnClickListener(this);

        btTopoCadastroEmpresaDados.setOnClickListener(this);
        btTopoCadastroEmpresaContato.setOnClickListener(this);
        btTopoCadastroEmpresaHorario.setOnClickListener(this);
        btTopoCadastroEmpresaEndereco.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ibApresentacaoPreviaFotoEmpresa:

                this.pedirPermissaoDeAcesso();

                break;

            case R.id.btCancelarCadastroEmpresa:

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;

            case R.id.btParaCadastrarEmpresa:

                controleLayoutBotaoProximo();

                break;

            case R.id.btAdicionarContatoCadastroEmpresa:

                this.chamarDialogCadastroContato();

                break;

            case R.id.btDialogCancelarCadastroContato:

                dialogContato.dismiss();

                break;

            case R.id.btDialogCadastroContato:

                this.setarContatoEFecharDialog();

                break;

            case R.id.btDialogCadastrarContatoMaisUm:

                this.setarContatoEAdicionarMaisUm();

                break;

            case R.id.btTopoCadastroEmpresaDados:

                controleLayoutCadastroDeDados(2);

                break;

            case R.id.btTopoCadastroEmpresaContato:

                controleLayoutCadastroDeContato(2);

                break;

            case R.id.btTopoCadastroEmpresaHorario:

                controleLayoutCadastroDeHorario(2);

                break;

            case R.id.btTopoCadastroEmpresaEndereco:

                controleLayoutCadastroDeEndereco(2);

                break;
            case R.id.etHorarioInicioExpedienteGenericoCadastroEmpresa:

                timePickerDialog.setCodRequest(1);

                openTimePicker();

                break;

            case R.id.etHorarioFimExpedienteGenericoCadastroEmpresa:

                timePickerDialog.setCodRequest(2);

                openTimePicker();
                break;

            case R.id.etHorarioInicioIntervaloGenericoCadastroEmpresa:

                timePickerDialog.setCodRequest(3);

                openTimePicker();
                break;

            case R.id.etHorarioFimIntervaloGenericoCadastroEmpresa:

                timePickerDialog.setCodRequest(4);

                openTimePicker();
                break;

            case R.id.etSegundaInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(5);

                openTimePicker();
                break;
            case R.id.etSegundaFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(6);

                openTimePicker();
                break;
            case R.id.etSegundaInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(7);

                openTimePicker();
                break;
            case R.id.etSegundaFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(8);

                openTimePicker();
                break;
            case R.id.etTercaInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(9);

                openTimePicker();
                break;
            case R.id.etTercaFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(10);

                openTimePicker();
                break;
            case R.id.etTercaInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(11);

                openTimePicker();
                break;
            case R.id.etTercaFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(12);

                openTimePicker();
                break;
            case R.id.etQuartaInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(13);

                openTimePicker();
                break;
            case R.id.etQuartaFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(14);

                openTimePicker();
                break;
            case R.id.etQuartaInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(15);

                openTimePicker();
                break;
            case R.id.etQuartaFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(16);

                openTimePicker();
                break;
            case R.id.etQuintaInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(17);

                openTimePicker();
                break;
            case R.id.etQuintaFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(18);

                openTimePicker();
                break;
            case R.id.etQuintaInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(19);

                openTimePicker();
                break;
            case R.id.etQuintaFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(20);

                openTimePicker();
                break;
            case R.id.etSextaInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(21);

                openTimePicker();
                break;
            case R.id.etSextaFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(22);

                openTimePicker();
                break;
            case R.id.etSextaInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(23);

                openTimePicker();
                break;
            case R.id.etSextaFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(24);

                openTimePicker();
                break;
            case R.id.etSabadoInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(25);

                openTimePicker();
                break;
            case R.id.etSabadoFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(26);

                openTimePicker();
                break;
            case R.id.etSabadoInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(27);

                openTimePicker();
                break;
            case R.id.etSabadoFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(28);

                openTimePicker();
                break;
            case R.id.etDomingoInicioExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(29);

                openTimePicker();
                break;
            case R.id.etDomingoFimExpedienteCadastroEmpresa:

                timePickerDialog.setCodRequest(30);

                openTimePicker();
                break;
            case R.id.etDomingoInicioIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(31);

                openTimePicker();
                break;
            case R.id.etDomingoFimIntervaloCadastroEmpresa:

                timePickerDialog.setCodRequest(32);

                openTimePicker();
                break;

        }

    }


    private void chamarDialogCadastroContato() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_contato_cadastro, null);

        spinnerDialogTipoContato = mView.findViewById(R.id.spinnerDialogTipoContato);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_contato, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogTipoContato.setAdapter(adapter);
        spinnerDialogTipoContato.setOnItemSelectedListener(this);

        etDialogCadastroContato = mView.findViewById(R.id.etDialogCadastroContato);
        btDialogCancelarCadastroContato = mView.findViewById(R.id.btDialogCancelarCadastroContato);
        btDialogCadastrarContatoMaisUm = mView.findViewById(R.id.btDialogCadastrarContatoMaisUm);
        btDialogCadastroContato = mView.findViewById(R.id.btDialogCadastroContato);

        btDialogCancelarCadastroContato.setOnClickListener(this);
        btDialogCadastrarContatoMaisUm.setOnClickListener(this);
        btDialogCadastroContato.setOnClickListener(this);

        alertDialog.setView(mView);
        dialogContato = alertDialog.create();
        dialogContato.show();

    }


    private void setarContatoEAdicionarMaisUm() {

        if (!etDialogCadastroContato.getText().toString().isEmpty()) {

            Contato objContato = new Contato();

            objContato.setTipoContato(tipoContato);
            objContato.setContato(etDialogCadastroContato.getText().toString());

            listContato.add(objContato);

            adapterListaContato.setListaContato(listContato);

            etDialogCadastroContato.setText("");

        } else {

            Toast.makeText(getApplicationContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();

        }

    }

    private void setarContatoEFecharDialog() {

        if (!etDialogCadastroContato.getText().toString().isEmpty()) {

            Contato objContato = new Contato();

            objContato.setTipoContato(tipoContato);
            objContato.setContato(etDialogCadastroContato.getText().toString());

            listContato.add(objContato);

            adapterListaContato.setListaContato(listContato);

            dialogContato.dismiss();

        } else {

            Toast.makeText(getApplicationContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();

        }
    }


    private void setDadosLoginGoogle(Intent intent) {

        etNomeEmpresaCadastro.setText(intent.getStringExtra("nome"));

        etUsuarioCadastroEmpresa.setText(intent.getStringExtra("email"));
        etUsuarioCadastroEmpresa.setVisibility(View.INVISIBLE);
        etUsuarioCadastroEmpresa.setFocusable(false);
        etUsuarioCadastroEmpresa.setClickable(false);
        etUsuarioCadastroEmpresa.setHeight(0);

        TextView x = findViewById(R.id.tvUsuarioCadastroEmpresa);
        x.setVisibility(View.INVISIBLE);
        x.setTextSize(0);

        etSenhaCadastroEmpresa.setText(intent.getStringExtra("id_google"));
        etSenhaCadastroEmpresa.setVisibility(View.INVISIBLE);
        etSenhaCadastroEmpresa.setFocusable(false);
        etSenhaCadastroEmpresa.setClickable(false);
        etSenhaCadastroEmpresa.setHeight(0);

        x = findViewById(R.id.tvSenhaCadastroEmpresa);
        x.setVisibility(View.INVISIBLE);
        x.setTextSize(0);

        Contato objContato = new Contato();

        objContato.setTipoContato("E-mail");
        objContato.setContato(intent.getStringExtra("email"));

        listContato.add(objContato);

        adapterListaContato.setListaContato(listContato);

        TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.preto, null));
        textView.setPadding(0, 10, 0, 0);
        textView.setText("E-mail: " + intent.getStringExtra("email"));

        etLoginGoogleCadastroEmpresa.setText(intent.getStringExtra("id_google"));

        final String urlImagem = intent.getStringExtra("foto");

        Glide.with(this).load(urlImagem).into(ibApresentacaoPreviaFotoEmpresa);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL urlAdress = new URL(urlImagem);
                    InputStream is = urlAdress.openStream();
                    String filename = Uri.parse(urlAdress.toString()).getLastPathSegment();
                    File outputFile = new File(getApplicationContext().getCacheDir(), filename);
                    OutputStream os = new FileOutputStream(outputFile);
                    byteImagem = new byte[(int) outputFile.length()];

                    int length;

                    while ((length = is.read(byteImagem)) != -1) {
                        os.write(byteImagem, 0, length);

                        is.close();
                        os.close();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void pedirPermissaoDeAcesso() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA);
            } else {
                adicionarImagem();
            }
        } else {
            adicionarImagem();
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.adicionarImagem();
                } else {
                    Toast.makeText(this, Constantes.M_INF_PERMISSAO_ACESSO_GALERIA, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void receberFotoDaCameraEmpresa() {

        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{caminhoImagemCamera}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

                Log.i("Arquivo", "" + caminhoImagemCamera);
            }
        });

        arquivoImagemEmpresa = new File(caminhoImagemCamera);

        Bitmap bitMap = BitmapFactory.decodeFile(caminhoImagemCamera);

        ibApresentacaoPreviaFotoEmpresa.setImageBitmap(bitMap);
        ibApresentacaoPreviaFotoEmpresa.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void receberFotoDaGaleriaEmpresa(Intent data) {

        if (data != null) {

            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String foto = cursor.getString(0);

            arquivoImagemEmpresa = new File(foto);

            ibApresentacaoPreviaFotoEmpresa.setImageBitmap(BitmapFactory.decodeFile(foto));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constantes.COD_REQUEST_GALERIA_EMPRESA:

                this.receberFotoDaGaleriaEmpresa(data);

                break;

            case Constantes.COD_REQUEST_CAMERA_EMPRESA:

                this.receberFotoDaCameraEmpresa();

                break;
        }
    }

    private void abrirGaleria() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Selecione"), Constantes.COD_REQUEST_GALERIA_EMPRESA);
    }

    private void abrirCamera() {

        File arquivo = new File(getExternalStorageDirectory(), Constantes.CAMINHO_DIRETORIO_IMAGEM);
        boolean criada = arquivo.exists();

        if (!criada) {
            criada = arquivo.mkdirs();
        }
        if (criada) {

            Long mills = System.currentTimeMillis() / 1000;
            String nomeArquivo = mills.toString() + ".jpg";

            caminhoImagemCamera = getExternalStorageDirectory() + File.separator + Constantes.CAMINHO_DIRETORIO_IMAGEM + File.separator + nomeArquivo;

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            arquivoImagemEmpresa = new File(caminhoImagemCamera);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoImagemEmpresa));

            startActivityForResult(intent, Constantes.COD_REQUEST_CAMERA_EMPRESA);
        }
    }

    private void adicionarImagem() {

        final CharSequence[] opcoes = {
                "Pegar uma imagem",
                "Tirar uma foto",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Pegar uma imagem")) {

                    abrirGaleria();

                } else if (opcoes[which].equals("Tirar uma foto")) {

                    abrirCamera();

                } else {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void controleLayoutBotaoProximo() {

        if (responsavelDados) {

            this.controleLayoutCadastroDeDados(1);

        } else if (responsavelContato) {

            this.controleLayoutCadastroDeContato(1);

        } else if (responsavelHorario) {

            this.controleLayoutCadastroDeHorario(1);

        } else if (responsavelEndereco) {

            this.controleLayoutCadastroDeEndereco(1);

        }
    }

    private void controleLayoutCadastroDeEndereco(int param) {

        LinearLayout.LayoutParams layoutParamsLinearLayout;

        switch (param) {

            case 1:

                if (verificacaoFormularioEndereco()) {

                    this.pegarDadosParaCadastro();

                }

                break;
            case 2:

                responsavelDados = false;
                responsavelContato = false;
                responsavelHorario = false;
                responsavelEndereco = true;

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaDados.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayoutCadastroEmpresaEndereco.setLayoutParams(layoutParamsLinearLayout);

                btParaCadastrarEmpresa.setText(TEXTO_BOTAO_FINAL_CADASTRO);

                break;
        }
    }

    private void pegarDadosParaCadastro() {

//        JsonObject json = new JsonParser().parse(resposta).getAsJsonObject();

        JsonObject dadosParaCadastroEmpresa = new JsonObject();

        Empresa dadosObjEmpresa = new Empresa();

        dadosObjEmpresa.setNomeFantasia(etNomeEmpresaCadastro.getText().toString());

        if (rbCpfEmpresaCadastro.isChecked()) {

            dadosObjEmpresa.setCpfEmpresa(etCpfOuCnpjEmpresaCadastro.getText().toString());

        } else {

            dadosObjEmpresa.setCnpjEmpresa(etCpfOuCnpjEmpresaCadastro.getText().toString());

        }

        dadosObjEmpresa.setAreaAtuacao(areaAtuacaoParaCadastro);

        if (rbUnissexCadastroEmpresa.isChecked()) {

            dadosObjEmpresa.setPublicoAlvo(Constantes.UNISSEX);

        } else if (rbMasculinoCadastroEmpresa.isChecked()) {

            dadosObjEmpresa.setPublicoAlvo(Constantes.MASCULINO);

        } else if (rbFemininoCadastroEmpresa.isChecked()) {

            dadosObjEmpresa.setPublicoAlvo(Constantes.FEMININO);

        }

        if (arquivoImagemEmpresa != null) {

            int tam = (int) arquivoImagemEmpresa.length();
            byte[] fileArray = new byte[tam];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(arquivoImagemEmpresa);
                fis.read(fileArray, 0, tam);
            } catch (Exception e) {
                System.err.println(e);
            }

            dadosObjEmpresa.setLogoEmpresa(fileArray);
        } else if (byteImagem != null) {

            dadosObjEmpresa.setLogoEmpresa(byteImagem);
        }

        dadosObjEmpresa.setListaContato(listContato);

        List<HorarioEmpresa> horarioEmpresaList = new ArrayList<>();

        if (rbAtribuirHorarioGenericoATodosCadastroEmpresa.isChecked()) {

            String inicioExp[] = etHorarioInicioExpedienteGenericoCadastroEmpresa.getText().toString().split(":");
            String fimExp[] = etHorarioFimExpedienteGenericoCadastroEmpresa.getText().toString().split(":");
            String InicioInt[];
            String InicioFim[];

            Calendar fimIntCal = new GregorianCalendar();
            Calendar inicioIntCal = new GregorianCalendar();

            Calendar fimExpCal = new GregorianCalendar();
            Calendar inicioExpCal = new GregorianCalendar();

            inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
            inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

            fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
            fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

            if (!etHorarioInicioIntervaloGenericoCadastroEmpresa.getText().toString().isEmpty()) {

                InicioInt = etHorarioInicioIntervaloGenericoCadastroEmpresa.getText().toString().split(":");

                inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
            }

            if (!etHorarioFimIntervaloGenericoCadastroEmpresa.getText().toString().isEmpty()) {

                InicioFim = etHorarioFimIntervaloGenericoCadastroEmpresa.getText().toString().split(":");

                fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
            }

            if (cbAtribuirHorarioGenericoSegundaCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEGUNDA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEGUNDA);

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoTercaCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_TERCA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_TERCA);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoQuartaCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUARTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUARTA);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoQuintaCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUINTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUINTA);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoSextaCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEXTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEXTA);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoSabadoCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SABADO);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SABADO);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbAtribuirHorarioGenericoDomingoCadastroEmpresa.isChecked()) {
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_DOMINGO);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_DOMINGO);
                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }
        } else {
            if (cbHorarioSegundaCadastroEmpresa.isChecked()) {

                String inicioExp[] = etSegundaInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etSegundaFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etSegundaInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etSegundaInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etSegundaFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etSegundaFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEGUNDA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEGUNDA);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioTercaCadastroEmpresa.isChecked()) {

                String inicioExp[] = etTercaInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etTercaFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etTercaInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etTercaInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etTercaFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etTercaFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_TERCA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_TERCA);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioQuartaCadastroEmpresa.isChecked()) {

                String inicioExp[] = etQuartaInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etQuartaFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etQuartaInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etQuartaInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etQuartaFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etQuartaFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUARTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUARTA);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioQuintaCadastroEmpresa.isChecked()) {

                String inicioExp[] = etQuintaInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etQuintaFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etQuintaInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etQuintaInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etQuintaFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etQuintaFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUINTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUINTA);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioSextaCadastroEmpresa.isChecked()) {

                String inicioExp[] = etSextaInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etSextaFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etSextaInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etSextaInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etSextaFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etSextaFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEXTA);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEXTA);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioSabadoCadastroEmpresa.isChecked()) {

                String inicioExp[] = etSabadoInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etSabadoFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etSabadoInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etSabadoInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etSabadoFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etSabadoFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SABADO);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SABADO);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }

            if (cbHorarioDomingoCadastroEmpresa.isChecked()) {

                String inicioExp[] = etDomingoInicioExpedienteCadastroEmpresa.getText().toString().split(":");
                String fimExp[] = etDomingoFimExpedienteCadastroEmpresa.getText().toString().split(":");
                String InicioInt[];
                String InicioFim[];

                Calendar fimIntCal = new GregorianCalendar();
                Calendar inicioIntCal = new GregorianCalendar();

                Calendar fimExpCal = new GregorianCalendar();
                Calendar inicioExpCal = new GregorianCalendar();

                inicioExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inicioExp[0]));
                inicioExpCal.set(Calendar.MINUTE, Integer.valueOf(inicioExp[1]));

                fimExpCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fimExp[0]));
                fimExpCal.set(Calendar.MINUTE, Integer.valueOf(fimExp[1]));

                if (!etDomingoInicioIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioInt = etDomingoInicioIntervaloCadastroEmpresa.getText().toString().split(":");

                    inicioIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioInt[0]));
                    inicioIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioInt[1]));
                }

                if (!etDomingoFimIntervaloCadastroEmpresa.getText().toString().isEmpty()) {

                    InicioFim = etDomingoFimIntervaloCadastroEmpresa.getText().toString().split(":");

                    fimIntCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(InicioFim[0]));
                    fimIntCal.set(Calendar.MINUTE, Integer.valueOf(InicioFim[1]));
                }

                HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                horarioEmpresa.setInicioExpediente(inicioExpCal);
                horarioEmpresa.setInicioIntervalo(inicioIntCal);
                horarioEmpresa.setFimIntervalo(fimIntCal);
                horarioEmpresa.setFimExpediente(fimExpCal);

                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_DOMINGO);
                diaSemana.setDiaSemana(Constantes.DIA_SEMANA_DOMINGO);
                horarioEmpresa.setDiaSemana(diaSemana);
                horarioEmpresaList.add(horarioEmpresa);
            }
        }

        dadosObjEmpresa.setListaHorarioEmpresa(horarioEmpresaList);

        Endereco endereco = new Endereco();

        endereco.setEstado(etEstadoCadastroEmpresa.getText().toString());
        endereco.setCidade(etCidadeCadastroEmpresa.getText().toString());
        endereco.setBairro(etBairroCadastroEmpresa.getText().toString());
        endereco.setLogradouro(etLogradouroCadastroEmpresa.getText().toString());
        endereco.setCep(etCepCadastroEmpresa.getText().toString());
        endereco.setNumero(etNumeroCadastroEmpresa.getText().toString());
        endereco.setComplemento(etComplementoCadastroEmpresa.getText().toString());

        dadosObjEmpresa.setEndereco(endereco);

        Login login = new Login();

        login.setUsuario(etUsuarioCadastroEmpresa.getText().toString());
        login.setSenha(Util.getHash(etSenhaCadastroEmpresa.getText().toString()));


        if (!etLoginGoogleCadastroEmpresa.getText().toString().isEmpty()) {
            login.setLoginGoogle(etLoginGoogleCadastroEmpresa.getText().toString());
        }

        login.setEmpresa(dadosObjEmpresa);

        getEmpresaControl().cadastrarEmpresaControl(getApplicationContext(), login);
    }

    private boolean verificacaoFormularioEndereco() {

        boolean cep = false;
        boolean numero = false;
        boolean logradouro = false;
        boolean bairro = false;
        boolean cidade = false;
        boolean estado = false;

        if (!etCepCadastroEmpresa.getText().toString().isEmpty()) {
            cep = true;
        } else {
            cep = false;
            etCepCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }

        if (!etNumeroCadastroEmpresa.getText().toString().isEmpty()) {
            numero = true;
        } else {
            numero = false;
            etNumeroCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }


        if (!etLogradouroCadastroEmpresa.getText().toString().isEmpty()) {
            logradouro = true;
        } else {
            logradouro = false;
            etLogradouroCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }


        if (!etBairroCadastroEmpresa.getText().toString().isEmpty()) {
            bairro = true;
        } else {
            bairro = false;
            etBairroCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }


        if (!etCidadeCadastroEmpresa.getText().toString().isEmpty()) {
            cidade = true;
        } else {
            cidade = false;
            etCidadeCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }


        if (!etEstadoCadastroEmpresa.getText().toString().isEmpty()) {
            estado = true;
        } else {
            estado = false;
            etEstadoCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }


        if (cep && numero && logradouro && bairro && cidade && estado) {

            return true;

        } else {

            Toast.makeText(this, Constantes.M_AT_CADASTRO_ENDERECO, Toast.LENGTH_LONG).show();

            return false;
        }
    }


    private void controleLayoutCadastroDeHorario(int param) {

        LinearLayout.LayoutParams layoutParamsLinearLayout;

        switch (param) {

            case 1:

                if (verificacaoFormularioHorario()) {
                    responsavelDados = false;
                    responsavelContato = false;
                    responsavelHorario = false;
                    responsavelEndereco = true;

                    btTopoCadastroEmpresaEndereco.setEnabled(true);
                    btTopoCadastroEmpresaEndereco.setBackgroundColor(getResources().getColor(R.color.verde_claro, null));

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayoutCadastroEmpresaEndereco.setLayoutParams(layoutParamsLinearLayout);

                    btParaCadastrarEmpresa.setText(TEXTO_BOTAO_FINAL_CADASTRO);
                }
                break;
            case 2:

                responsavelDados = false;
                responsavelContato = false;
                responsavelHorario = true;
                responsavelEndereco = false;

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaDados.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaEndereco.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                btParaCadastrarEmpresa.setText(TEXTO_BOTAO_PROXIMO_CADASTRO);
                break;
        }
    }

    private boolean verificacaoFormularioHorario() {

        if (rbAtribuirHorarioGenericoATodosCadastroEmpresa.isChecked()) {

            boolean verificarInicioExpediente = false;
            boolean verificarFimExpediente = false;

            if (!etHorarioInicioExpedienteGenericoCadastroEmpresa.getText().toString().isEmpty()) {

                verificarInicioExpediente = true;

            } else {

                verificarInicioExpediente = false;

            }

            if (!etHorarioFimExpedienteGenericoCadastroEmpresa.getText().toString().isEmpty()) {

                verificarFimExpediente = true;

            } else {

                verificarFimExpediente = false;

            }


            if (verificarFimExpediente && verificarInicioExpediente) {

                if (cbAtribuirHorarioGenericoDomingoCadastroEmpresa.isChecked() || cbAtribuirHorarioGenericoSegundaCadastroEmpresa.isChecked()
                        || cbAtribuirHorarioGenericoTercaCadastroEmpresa.isChecked() || cbAtribuirHorarioGenericoQuartaCadastroEmpresa.isChecked()
                        || cbAtribuirHorarioGenericoQuintaCadastroEmpresa.isChecked() || cbAtribuirHorarioGenericoSextaCadastroEmpresa.isChecked()
                        || cbAtribuirHorarioGenericoSabadoCadastroEmpresa.isChecked()) {

                    return true;
                } else {

                    Toast.makeText(this, Constantes.M_AT_DIA_HORARIO_GENERICO_CADASTRO, Toast.LENGTH_LONG).show();

                    return false;
                }
            } else {

                Toast.makeText(this, Constantes.M_AT_HORARIO_GENERICO_CADASTRO, Toast.LENGTH_LONG).show();

                return false;
            }
        } else {

            boolean controle1 = false;
            boolean controle2 = false;
            boolean controle3 = false;
            boolean controle4 = false;
            boolean controle5 = false;
            boolean controle6 = false;
            boolean controle7 = false;

            boolean segunda = false;
            boolean segundaI = false;
            boolean segundaF = false;
            boolean terca = false;
            boolean tercaI = false;
            boolean tercaF = false;
            boolean quarta = false;
            boolean quartaI = false;
            boolean quartaF = false;
            boolean quinta = false;
            boolean quintaI = false;
            boolean quintaF = false;
            boolean sexta = false;
            boolean sextaI = false;
            boolean sextaF = false;
            boolean sabado = false;
            boolean sabadoI = false;
            boolean sabadoF = false;
            boolean domingo = false;
            boolean domingoI = false;
            boolean domingoF = false;

            if (cbHorarioSegundaCadastroEmpresa.isChecked()) {

                segunda = true;

                if (!etSegundaInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    segundaI = true;

                } else {

                    segundaI = false;

                }


                if (!etSegundaFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    segundaF = true;

                } else {

                    segundaF = false;

                }


                if (segundaI && segundaF) {

                    controle1 = true;

                } else {

                    controle1 = false;

                }
            }

            if (cbHorarioTercaCadastroEmpresa.isChecked()) {

                terca = true;

                if (!etTercaInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    tercaI = true;

                } else {

                    tercaI = false;

                }


                if (!etTercaFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    tercaF = true;

                } else {

                    tercaF = false;

                }


                if (tercaI && tercaF) {

                    controle2 = true;

                } else {

                    controle2 = false;

                }
            }

            if (cbHorarioQuartaCadastroEmpresa.isChecked()) {

                quarta = true;

                if (!etQuartaInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    quartaI = true;

                } else {

                    quartaI = false;

                }


                if (!etQuartaFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    quartaF = true;

                } else {

                    quartaF = false;

                }


                if (quartaF && quartaI) {

                    controle3 = true;

                } else {

                    controle3 = false;

                }
            }

            if (cbHorarioQuintaCadastroEmpresa.isChecked()) {

                quinta = true;

                if (!etQuintaInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    quintaI = true;

                } else {

                    quintaI = false;

                }


                if (!etQuintaFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    quintaF = true;

                } else {

                    quintaF = false;

                }


                if (quintaF && quintaI) {

                    controle4 = true;

                } else {

                    controle4 = false;

                }
            }

            if (cbHorarioSextaCadastroEmpresa.isChecked()) {

                sexta = true;

                if (!etSextaInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    sextaI = true;

                } else {

                    sextaI = false;

                }


                if (!etSextaFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    sextaF = true;

                } else {

                    sextaF = false;

                }


                if (sextaF && sextaI) {

                    controle5 = true;
                } else {

                    controle5 = false;
                }
            }

            if (cbHorarioSabadoCadastroEmpresa.isChecked()) {

                sabado = true;

                if (!etSabadoInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    sabadoI = true;
                } else {

                    sabadoI = false;
                }
                if (!etSabadoFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {

                    sabadoF = true;
                } else {

                    sabadoF = false;
                }
                if (sabadoF && sabadoI) {

                    controle6 = true;
                } else {
                    controle6 = false;
                }
            }
            if (cbHorarioDomingoCadastroEmpresa.isChecked()) {
                domingo = true;
                if (!etDomingoInicioExpedienteCadastroEmpresa.getText().toString().isEmpty()) {
                    domingoI = true;
                } else {
                    domingoI = false;
                }
                if (!etDomingoFimExpedienteCadastroEmpresa.getText().toString().isEmpty()) {
                    domingoF = true;
                } else {
                    domingoF = false;
                }
                if (domingoF && domingoI) {
                    controle7 = true;
                } else {
                    controle7 = false;
                }
            }
            if (segunda == controle1 && terca == controle2 && quarta == controle3 && quinta == controle4 && sexta == controle5 && sabado == controle6 && domingo == controle7) {

                if (cbHorarioSegundaCadastroEmpresa.isChecked() || cbHorarioTercaCadastroEmpresa.isChecked() || cbHorarioQuartaCadastroEmpresa.isChecked() || cbHorarioQuintaCadastroEmpresa.isChecked()
                        || cbHorarioSextaCadastroEmpresa.isChecked() || cbHorarioSabadoCadastroEmpresa.isChecked() || cbHorarioDomingoCadastroEmpresa.isChecked()) {
                    return true;
                } else {
                    Toast.makeText(this, Constantes.M_AT_DIA_HORARIO_INDIVIDUAL_CADASTRO, Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(this, Constantes.M_AT_HORARIO_INDIVIDUAL_CADASTRO, Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private void controleLayoutCadastroDeContato(int param) {

        LinearLayout.LayoutParams layoutParamsLinearLayout;

        switch (param) {

            case 1:

                if (listContato.size() > 0) {

                    responsavelDados = false;
                    responsavelContato = false;
                    responsavelHorario = true;
                    responsavelEndereco = false;

                    btTopoCadastroEmpresaHorario.setEnabled(true);
                    btTopoCadastroEmpresaHorario.setBackgroundColor(getResources().getColor(R.color.verde_claro, null));

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                    btParaCadastrarEmpresa.setText(TEXTO_BOTAO_PROXIMO_CADASTRO);
                } else {
                    Toast.makeText(this, Constantes.M_AT_CADASTRO_CONTATO, Toast.LENGTH_LONG).show();
                }
                break;
            case 2:

                responsavelDados = false;
                responsavelContato = true;
                responsavelHorario = false;
                responsavelEndereco = false;

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaDados.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaEndereco.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                btParaCadastrarEmpresa.setText(TEXTO_BOTAO_PROXIMO_CADASTRO);

                break;
        }
    }

    private void controleLayoutCadastroDeDados(int param) {

        LinearLayout.LayoutParams layoutParamsLinearLayout;

        switch (param) {
            case 1:
                if (verificacaoFormularioDados()) {

                    responsavelDados = false;
                    responsavelContato = true;
                    responsavelHorario = false;
                    responsavelEndereco = false;

                    btTopoCadastroEmpresaContato.setEnabled(true);
                    btTopoCadastroEmpresaContato.setBackgroundColor(getResources().getColor(R.color.verde_claro, null));

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    linearLayoutCadastroEmpresaDados.setLayoutParams(layoutParamsLinearLayout);

                    layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                    btParaCadastrarEmpresa.setText(TEXTO_BOTAO_PROXIMO_CADASTRO);

                } else {
                    Toast.makeText(this, Constantes.M_AT_DADOS_FORMULARIO, Toast.LENGTH_LONG).show();
                }
                break;
            case 2:

                responsavelDados = true;
                responsavelContato = false;
                responsavelHorario = false;

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaContato.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaHorario.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                linearLayoutCadastroEmpresaEndereco.setLayoutParams(layoutParamsLinearLayout);

                layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayoutCadastroEmpresaDados.setLayoutParams(layoutParamsLinearLayout);

                btParaCadastrarEmpresa.setText(TEXTO_BOTAO_PROXIMO_CADASTRO);
                break;
        }
    }

    private boolean verificacaoFormularioDados() {

        boolean verificacao = false;
        boolean controle1 = false;
        boolean controle2 = false;
        boolean controle3 = false;
        boolean controle4 = false;

        if (!etNomeEmpresaCadastro.getText().toString().isEmpty()) {
            controle1 = true;
            etNomeEmpresaCadastro.setBackgroundColor(Color.TRANSPARENT);
        } else {
            controle1 = false;
            etNomeEmpresaCadastro.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }
        if (!etCpfOuCnpjEmpresaCadastro.getText().toString().isEmpty()) {
            controle2 = true;
            etCpfOuCnpjEmpresaCadastro.setBackgroundColor(Color.TRANSPARENT);
        } else {
            controle2 = false;
            etCpfOuCnpjEmpresaCadastro.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }
        if (!etUsuarioCadastroEmpresa.getText().toString().isEmpty()) {
            controle3 = true;
            etUsuarioCadastroEmpresa.setBackgroundColor(Color.TRANSPARENT);
        } else {
            controle3 = false;
            etUsuarioCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }
        if (!etSenhaCadastroEmpresa.getText().toString().isEmpty()) {
            controle4 = true;
            etSenhaCadastroEmpresa.setBackgroundColor(Color.TRANSPARENT);
        } else {
            controle4 = false;
            etSenhaCadastroEmpresa.setBackgroundColor(getResources().getColor(R.color.rose, null));
        }
        if (controle1 && controle2 && controle3 && controle4) {
            verificacao = true;
        } else {
            verificacao = false;
        }
        return verificacao;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tipoContato = parent.getItemAtPosition(position).toString();

        switch (tipoContato) {
            case "Fixo":
                etDialogCadastroContato.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case "Celular":

                etDialogCadastroContato.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case "E-mail":
                etDialogCadastroContato.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void openTimePicker() {
        timePickerDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String horario = hourOfDay + ":" + minute;

        switch (timePickerDialog.getCodRequest()) {

            case 1:
                etHorarioInicioExpedienteGenericoCadastroEmpresa.setText(horario);
                break;
            case 2:
                etHorarioFimExpedienteGenericoCadastroEmpresa.setText(horario);
                break;
            case 3:
                etHorarioInicioIntervaloGenericoCadastroEmpresa.setText(horario);
                break;
            case 4:
                etHorarioFimIntervaloGenericoCadastroEmpresa.setText(horario);
                break;
            case 5:
                etSegundaInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 6:
                etSegundaFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 7:
                etSegundaInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 8:
                etSegundaFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 9:
                etTercaInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 10:
                etTercaFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 11:
                etTercaInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 12:
                etTercaFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 13:
                etQuartaInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 14:
                etQuartaFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 15:
                etQuartaInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 16:
                etQuartaFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 17:
                etQuintaInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 18:
                etQuintaFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 19:
                etQuintaInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 20:
                etQuintaFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 21:
                etSextaInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 22:
                etSextaFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 23:
                etSextaInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 24:
                etSextaFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 25:
                etSabadoInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 26:
                etSabadoFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 27:
                etSabadoInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 28:
                etSabadoFimIntervaloCadastroEmpresa.setText(horario);
                break;
            case 29:
                etDomingoInicioExpedienteCadastroEmpresa.setText(horario);
                break;
            case 30:
                etDomingoFimExpedienteCadastroEmpresa.setText(horario);
                break;
            case 31:
                etDomingoInicioIntervaloCadastroEmpresa.setText(horario);
                break;
            case 32:
                etDomingoFimIntervaloCadastroEmpresa.setText(horario);
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final CharSequence[] opcoes = {
                "Excluir",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Excluir")) {

                    listContato.remove(position);
                    adapterListaContato.setListaContato(listContato);

                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        return false;
    }
}
