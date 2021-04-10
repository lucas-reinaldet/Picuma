package faculdade.br.picuma.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaContato;
import faculdade.br.picuma.adapter.AdapterListaHorarioEmpresa;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.Contato;
import faculdade.br.picuma.model.DiaSemana;
import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.model.HorarioEmpresa;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

import static android.os.Environment.getExternalStorageDirectory;

public class FragmentPerfilEmpresaInformacoes extends Fragment implements OnMapReadyCallback, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EmpresaControl empresaControl;
    private AdapterListaContato adapterListaContato;
    private AdapterListaHorarioEmpresa adapterListaHorario;
    private GoogleMap mMap;
    private String caminhoImagemCamera;
    private File arquivoImagem;
    private AlertDialog dialogAlert;
    private EditText etDialogAlteracaoNomePerfilEmpresa;
    private RadioButton etDialogAlteracaoAtendimentoUnissexPerfilEmpresa;
    private RadioButton etDialogAlteracaoAtendimentoFemininoPerfilEmpresa;
    private RadioButton etDialogAlteracaoAtendimentoMasculinoPerfilEmpresa;
    private EditText etDialogAlteracaoDescricaoPerfilEmpresa;
    private EditText etDialogAlteracaoCepEmpresa;
    private EditText etDialogAlteracaoNumeroEmpresa;
    private EditText etDialogAlteracaoLogradouroEmpresa;
    private EditText etComplementoCadastroEmpresa;
    private EditText etDialogAlteracaoBairroEmpresa;
    private EditText etDialogAlteracaoCidadeEmpresa;
    private EditText etDialogAlteracaoEstadoEmpresa;
    private Spinner spinnerDialogTipoContato;
    private EditText etDialogCadastroContato;
    private Button btDialogCancelarCadastroContato;
    private Button btDialogCadastrarContatoMaisUm;
    private Button btDialogCadastroContato;
    private String tipoContato;
    private List<Contato> listContatoParaCadastro;
    private EditText etDialogHorarioInicioExpedienteEmpresa;
    private EditText etDialogHorarioFimExpedienteEmpresa;
    private EditText etDialogHorarioInicioIntervaloEmpresa;
    private EditText etDialogHorarioFimIntervaloEmpresa;
    private RadioButton rbAtribuirHorarioAoDomingoEmpresa;
    private RadioButton rbAtribuirHorarioAoSegundaEmpresa;
    private RadioButton rbAtribuirHorarioAoTercaEmpresa;
    private RadioButton rbAtribuirHorarioAoQuartaEmpresa;
    private RadioButton rbAtribuirHorarioAoQuintaEmpresa;
    private RadioButton rbAtribuirHorarioAoSextaEmpresa;
    private RadioButton rbAtribuirHorarioAoSabadoEmpresa;
    private Button btDialogCadastrarHorarioCancelar;
    private Button btDialogCadastrarHorarioAlterar;
    private View fragmentMapaPerfilEmpresaInformacoes;
    private SupportMapFragment supportMapFragment;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    private static FragmentPerfilEmpresaInformacoes instance;

    public static FragmentPerfilEmpresaInformacoes getInstance() {
        if (instance == null) {
            instance = new FragmentPerfilEmpresaInformacoes();
        }
        return instance;
    }

    private ImageView ivImagemLogoPerfilEmpresaInformacoes;
    private TextView tvPublicoAlvoPerfilEmpresaInformacoes;
    private TextView tvNomePerfilEmpresaInformacoes;
    private TextView tvCpfCnpjPerfilEmpresaInformacoes;
    private TextView tvAreaAtuacaoPerfilEmpresaInformacoes;
    private ListView lvListaContatoPerfilEmpresaInformacoes;
    private ListView lvListaHorarioPerfilEmpresaInformacoes;
    private TextView tvEnderecoLogradouroPerfilEmpresaInformacoes;
    private TextView tvEnderecoNumeroPerfilEmpresaInformacoes;
    private TextView tvEnderecoComplementoPerfilEmpresaInformacoes;
    private TextView tvEnderecoBairroPerfilEmpresaInformacoes;
    private TextView tvEnderecoCidadePerfilEmpresaInformacoes;
    private TextView tvEnderecoEstadoPerfilEmpresaInformacoes;
    private TextView tvDescricaoPerfilEmpresaInformacoes;

    private ImageButton ibTrocarLogoPerfilEmpresaInformacoes;
    private ImageButton ibEditarNomePublicoAlvoPerfilEmpresaInformacoes;
    private ImageButton ibEditarDescricaoPerfilEmpresaInformacoes;
    private ImageButton ibAdicionarContatoPerfilEmpresaInformacoes;
    private ImageButton ibAdicionarHorarioPerfilEmpresaInformacoes;
    private ImageButton ibEditarEnderecoPerfilEmpresaInformacoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getInstance();
        View v = getEmpresaControl().getViewInfomacoesEmpresa(inflater, container, this);
        getEmpresaControl().setFragmentPerfilEmpresaInformacoes(this);
        ivImagemLogoPerfilEmpresaInformacoes = v.findViewById(R.id.ivImagemLogoPerfilEmpresaInformacoes);
        tvPublicoAlvoPerfilEmpresaInformacoes = v.findViewById(R.id.tvPublicoAlvoPerfilEmpresaInformacoes);
        tvNomePerfilEmpresaInformacoes = v.findViewById(R.id.tvNomePerfilEmpresaInformacoes);
        tvCpfCnpjPerfilEmpresaInformacoes = v.findViewById(R.id.tvCpfCnpjPerfilEmpresaInformacoes);
        tvAreaAtuacaoPerfilEmpresaInformacoes = v.findViewById(R.id.tvAreaAtuacaoPerfilEmpresaInformacoes);
        lvListaContatoPerfilEmpresaInformacoes = v.findViewById(R.id.lvListaContatoPerfilEmpresaInformacoes);
        lvListaHorarioPerfilEmpresaInformacoes = v.findViewById(R.id.lvListaHorarioPerfilEmpresaInformacoes);
        tvEnderecoLogradouroPerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoLogradouroPerfilEmpresaInformacoes);
        tvEnderecoNumeroPerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoNumeroPerfilEmpresaInformacoes);
        tvEnderecoComplementoPerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoComplementoPerfilEmpresaInformacoes);
        tvEnderecoBairroPerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoBairroPerfilEmpresaInformacoes);
        tvEnderecoCidadePerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoCidadePerfilEmpresaInformacoes);
        tvEnderecoEstadoPerfilEmpresaInformacoes = v.findViewById(R.id.tvEnderecoEstadoPerfilEmpresaInformacoes);
        tvDescricaoPerfilEmpresaInformacoes = v.findViewById(R.id.tvDescricaoPerfilEmpresaInformacoes);

        ibTrocarLogoPerfilEmpresaInformacoes = v.findViewById(R.id.ibTrocarLogoPerfilEmpresaInformacoes);
        ibEditarNomePublicoAlvoPerfilEmpresaInformacoes = v.findViewById(R.id.ibEditarNomePublicoAlvoPerfilEmpresaInformacoes);
        ibEditarDescricaoPerfilEmpresaInformacoes = v.findViewById(R.id.ibEditarDescricaoPerfilEmpresaInformacoes);
        ibAdicionarContatoPerfilEmpresaInformacoes = v.findViewById(R.id.ibAdicionarContatoPerfilEmpresaInformacoes);
        ibAdicionarHorarioPerfilEmpresaInformacoes = v.findViewById(R.id.ibAdicionarHorarioPerfilEmpresaInformacoes);
        ibEditarEnderecoPerfilEmpresaInformacoes = v.findViewById(R.id.ibEditarEnderecoPerfilEmpresaInformacoes);
        fragmentMapaPerfilEmpresaInformacoes = v.findViewById(R.id.fragmentMapaPerfilEmpresaInformacoes);

        if (getEmpresaControl().getPrimeiraExecucaoInfoEmpresa()) {
            getEmpresaControl().setSupportMapFragment((SupportMapFragment) getActivity()
                    .getSupportFragmentManager().findFragmentById(fragmentMapaPerfilEmpresaInformacoes.getId()));
            getEmpresaControl().getSupportMapFragment().
                    getMapAsync(this);
        }

        apresentarDadosIniciais();

        ibTrocarLogoPerfilEmpresaInformacoes.setOnClickListener(this);
        ibEditarNomePublicoAlvoPerfilEmpresaInformacoes.setOnClickListener(this);
        ibEditarDescricaoPerfilEmpresaInformacoes.setOnClickListener(this);
        ibAdicionarContatoPerfilEmpresaInformacoes.setOnClickListener(this);
        ibAdicionarHorarioPerfilEmpresaInformacoes.setOnClickListener(this);
        ibEditarEnderecoPerfilEmpresaInformacoes.setOnClickListener(this);

        lvListaContatoPerfilEmpresaInformacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] opcoes = {
                        "Excluir",
                        "Cancelar"
                };

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Escolha uma opção");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opcoes[which].equals("Excluir")) {
                            if (getEmpresaControl().getEmpresa().getListaContato().size() > 1) {
                                Contato contato = getEmpresaControl().getEmpresa().getListaContato().get(position);
                                if (getEmpresaControl().removerContatoEmpresa(contato)) {
                                    getEmpresaControl().getEmpresa().getListaContato().remove(position);
                                    adapterListaContato.setListaContato(getEmpresaControl().getEmpresa().getListaContato());
                                } else {
                                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_EXCLUIR_CONTATO_EMPRESA, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), Constantes.M_AT_EXCLUSAO_CONTATO_EMPRESA, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });


        lvListaHorarioPerfilEmpresaInformacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] opcoes = {
                        "Excluir",
                        "Cancelar"
                };

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Escolha uma opção");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opcoes[which].equals("Excluir")) {
                            if (getEmpresaControl().getEmpresa().getListaHorarioEmpresa().size() > 1) {
                                HorarioEmpresa horarioEmpresa = getEmpresaControl().getEmpresa().getListaHorarioEmpresa().get(position);
                                if (getEmpresaControl().removerHorarioEmpresa(horarioEmpresa)) {
                                    getEmpresaControl().getEmpresa().getListaHorarioEmpresa().remove(position);
                                    adapterListaHorario.setListaHorarioEmpresa(getEmpresaControl().getEmpresa().getListaHorarioEmpresa());
                                } else {
                                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_EXCLUIR_HORARIO_EMPRESA, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), Constantes.M_AT_EXCLUSAO_HORARIO_EMPRESA, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        return v;
    }

    private void apresentarDadosIniciais() {
        if (getEmpresaControl().getEmpresa().getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getEmpresaControl().getEmpresa().getLogoEmpresa(), 0, getEmpresaControl().getEmpresa().getLogoEmpresa().length);
            ivImagemLogoPerfilEmpresaInformacoes.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao(), 0, getEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao().length);
            ivImagemLogoPerfilEmpresaInformacoes.setImageBitmap(bitmap);
        }
        tvNomePerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getNomeFantasia());
        tvPublicoAlvoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + getEmpresaControl().getEmpresa().getPublicoAlvo());
        tvCpfCnpjPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getCnpjEmpresa() != null ?
                Constantes.M_CNPJ + getEmpresaControl().getEmpresa().getCnpjEmpresa() : Constantes.M_CPF +
                getEmpresaControl().getEmpresa().getCpfEmpresa());
        tvAreaAtuacaoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getAreaAtuacao().getAreaAtuacao());

        adapterListaContato = new AdapterListaContato(getContext());
        adapterListaHorario = new AdapterListaHorarioEmpresa(getContext());

        lvListaContatoPerfilEmpresaInformacoes.setAdapter(adapterListaContato);
        lvListaHorarioPerfilEmpresaInformacoes.setAdapter(adapterListaHorario);
        adapterListaContato.setListaContato(getEmpresaControl().getEmpresa().getListaContato());
        adapterListaHorario.setListaHorarioEmpresa(getEmpresaControl().getEmpresa().getListaHorarioEmpresa());

        tvEnderecoBairroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getBairro());
        tvEnderecoCidadePerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getCidade());
        tvEnderecoEstadoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getEstado());
        tvEnderecoNumeroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getNumero());
        tvEnderecoLogradouroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getLogradouro());

        if (getEmpresaControl().getEmpresa().getEndereco().getComplemento() != null &&
                !getEmpresaControl().getEmpresa().getEndereco().getComplemento().isEmpty()) {
            tvEnderecoComplementoPerfilEmpresaInformacoes.setTextSize(14);
            tvEnderecoComplementoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getComplemento());
        }

        if (getEmpresaControl().getEmpresa().getDescricaoEmpresa() != null &&
                !getEmpresaControl().getEmpresa().getDescricaoEmpresa().isEmpty()) {
            tvDescricaoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getDescricaoEmpresa());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.setMinZoomPreference(13);
        LatLng latLng = new LatLng(getEmpresaControl().getEmpresa().getEndereco().getLatitude(), getEmpresaControl().getEmpresa().getEndereco().getLongitute());
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(getEmpresaControl().getEmpresa().getNomeFantasia());
        marker.snippet(getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + getEmpresaControl().getEmpresa().getPublicoAlvo());
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ibTrocarLogoPerfilEmpresaInformacoes:
                pedirPermissaoDeAcesso();
                break;

            case R.id.ibEditarNomePublicoAlvoPerfilEmpresaInformacoes:
                abrirDialogAlteracaoNomeEAtendimento();
                break;

            case R.id.ibEditarDescricaoPerfilEmpresaInformacoes:
                abrirDialogAlteracaoDescricaoEmpresa();
                break;

            case R.id.ibAdicionarContatoPerfilEmpresaInformacoes:
                chamarDialogCadastroContato();
                break;

            case R.id.ibAdicionarHorarioPerfilEmpresaInformacoes:
                chamarDialogCadastroHorario();
                break;

            case R.id.ibEditarEnderecoPerfilEmpresaInformacoes:
                abrirDialogAlteracaoEnderecoEmpresa();
                break;
        }
    }

    private void chamarDialogCadastroHorario() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_horario_cadastro, null);

        etDialogHorarioInicioExpedienteEmpresa = mView.findViewById(R.id.etDialogHorarioInicioExpedienteEmpresa);
        etDialogHorarioFimExpedienteEmpresa = mView.findViewById(R.id.etDialogHorarioFimExpedienteEmpresa);
        etDialogHorarioInicioIntervaloEmpresa = mView.findViewById(R.id.etDialogHorarioInicioIntervaloEmpresa);
        etDialogHorarioFimIntervaloEmpresa = mView.findViewById(R.id.etDialogHorarioFimIntervaloEmpresa);

        rbAtribuirHorarioAoDomingoEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoDomingoEmpresa);
        rbAtribuirHorarioAoSegundaEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoSegundaEmpresa);
        rbAtribuirHorarioAoTercaEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoTercaEmpresa);
        rbAtribuirHorarioAoQuartaEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoQuartaEmpresa);
        rbAtribuirHorarioAoQuintaEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoQuintaEmpresa);
        rbAtribuirHorarioAoSextaEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoSextaEmpresa);
        rbAtribuirHorarioAoSabadoEmpresa = mView.findViewById(R.id.rbAtribuirHorarioAoSabadoEmpresa);

        btDialogCadastrarHorarioCancelar = mView.findViewById(R.id.btDialogCadastrarHorarioCancelar);
        btDialogCadastrarHorarioAlterar = mView.findViewById(R.id.btDialogCadastrarHorarioAlterar);

        for (HorarioEmpresa horarioEmpresa : getEmpresaControl().getEmpresa().getListaHorarioEmpresa()) {
            if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_DOMINGO)) {
                rbAtribuirHorarioAoDomingoEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_SABADO)) {
                rbAtribuirHorarioAoSabadoEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_SEGUNDA)) {
                rbAtribuirHorarioAoSegundaEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_TERCA)) {
                rbAtribuirHorarioAoTercaEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_QUARTA)) {
                rbAtribuirHorarioAoQuartaEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_QUINTA)) {
                rbAtribuirHorarioAoQuintaEmpresa.setClickable(false);
            } else if (horarioEmpresa.getDiaSemana().getDiaSemana().equals(Constantes.DIA_SEMANA_SEXTA)) {
                rbAtribuirHorarioAoSextaEmpresa.setClickable(false);
            }
        }

        etDialogHorarioInicioExpedienteEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpresaControl().getPerfilEmpresaActivity().abrirDialogHorario(1);
            }
        });

        etDialogHorarioFimExpedienteEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpresaControl().getPerfilEmpresaActivity().abrirDialogHorario(2);
            }
        });

        etDialogHorarioInicioIntervaloEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpresaControl().getPerfilEmpresaActivity().abrirDialogHorario(3);
            }
        });

        etDialogHorarioFimIntervaloEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpresaControl().getPerfilEmpresaActivity().abrirDialogHorario(4);
            }
        });

        btDialogCadastrarHorarioCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        btDialogCadastrarHorarioAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDialogHorarioInicioExpedienteEmpresa.getText().toString() != null && !etDialogHorarioInicioExpedienteEmpresa.getText().toString().isEmpty()
                        && etDialogHorarioFimExpedienteEmpresa.getText().toString() != null && !etDialogHorarioFimExpedienteEmpresa.getText().toString().isEmpty()) {

                    if (rbAtribuirHorarioAoDomingoEmpresa.isChecked() || rbAtribuirHorarioAoSegundaEmpresa.isChecked() || rbAtribuirHorarioAoTercaEmpresa.isChecked()
                            || rbAtribuirHorarioAoQuartaEmpresa.isChecked() || rbAtribuirHorarioAoQuintaEmpresa.isChecked() || rbAtribuirHorarioAoSextaEmpresa.isChecked()
                            || rbAtribuirHorarioAoSabadoEmpresa.isChecked()) {

                        HorarioEmpresa horarioEmpresa = new HorarioEmpresa();

                        String dadosHorario[] = etDialogHorarioInicioExpedienteEmpresa.getText().toString().split(":");

                        horarioEmpresa.setInicioExpediente(Util.getStringTimeToCalender(Integer.valueOf(dadosHorario[0]), Integer.valueOf(dadosHorario[1])));

                        dadosHorario = etDialogHorarioFimExpedienteEmpresa.getText().toString().split(":");

                        horarioEmpresa.setFimExpediente(Util.getStringTimeToCalender(Integer.valueOf(dadosHorario[0]), Integer.valueOf(dadosHorario[1])));

                        if (etDialogHorarioInicioIntervaloEmpresa.getText().toString() != null && !etDialogHorarioInicioIntervaloEmpresa.getText().toString().isEmpty()
                                && etDialogHorarioFimIntervaloEmpresa.getText().toString() != null && !etDialogHorarioFimIntervaloEmpresa.getText().toString().isEmpty()) {

                            dadosHorario = etDialogHorarioInicioIntervaloEmpresa.getText().toString().split(":");

                            horarioEmpresa.setInicioIntervalo(Util.getStringTimeToCalender(Integer.valueOf(dadosHorario[0]), Integer.valueOf(dadosHorario[1])));

                            dadosHorario = etDialogHorarioFimIntervaloEmpresa.getText().toString().split(":");

                            horarioEmpresa.setFimIntervalo(Util.getStringTimeToCalender(Integer.valueOf(dadosHorario[0]), Integer.valueOf(dadosHorario[1])));
                        }

                        DiaSemana diaSemana = new DiaSemana();

                        if (rbAtribuirHorarioAoDomingoEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_DOMINGO);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_DOMINGO);
                        } else if (rbAtribuirHorarioAoSegundaEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEGUNDA);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEGUNDA);
                        } else if (rbAtribuirHorarioAoTercaEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_TERCA);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_TERCA);
                        } else if (rbAtribuirHorarioAoQuartaEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUARTA);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUARTA);
                        } else if (rbAtribuirHorarioAoQuintaEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_QUINTA);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_QUINTA);
                        } else if (rbAtribuirHorarioAoSextaEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SEXTA);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SEXTA);
                        } else if (rbAtribuirHorarioAoSabadoEmpresa.isChecked()) {
                            diaSemana.setDiaSemana(Constantes.DIA_SEMANA_SABADO);
                            diaSemana.setIdDiaSemana(Constantes.ID_DIA_SEMANA_SABADO);
                        }

                        horarioEmpresa.setDiaSemana(diaSemana);

                        if (getEmpresaControl().cadastrarHorarioEmpresa(horarioEmpresa)) {
                            adapterListaHorario.setListaHorarioEmpresa(getEmpresaControl().getEmpresa().getListaHorarioEmpresa());
                        } else {
                            Toast.makeText(getContext(), Constantes.M_FALHA_AO_CADASTRAR_HORARIO_EMPRESA, Toast.LENGTH_SHORT).show();
                        }
                        dialogAlert.dismiss();
                    } else {
                        Toast.makeText(getContext(), Constantes.M_AT_DIA_HORARIO_CADASTRO, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), Constantes.M_AT_HORARIO_GENERICO_CADASTRO, Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();

    }

    public void apresentarHorarioEscolhido(int hourOfDay, int minute, int paramParaEscolhaHorario) {
        String horario = hourOfDay + ":" + minute;
        switch (paramParaEscolhaHorario) {
            case 1:
                etDialogHorarioInicioExpedienteEmpresa.setText(horario);
                break;
            case 2:
                etDialogHorarioFimExpedienteEmpresa.setText(horario);
                break;
            case 3:
                etDialogHorarioInicioIntervaloEmpresa.setText(horario);
                break;
            case 4:
                etDialogHorarioFimIntervaloEmpresa.setText(horario);
                break;
        }
    }

    private void abrirDialogAlteracaoEnderecoEmpresa() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_alteracao_endereco_empresa, null);

        etDialogAlteracaoCepEmpresa = mView.findViewById(R.id.etDialogAlteracaoCepEmpresa);
        etDialogAlteracaoNumeroEmpresa = mView.findViewById(R.id.etDialogAlteracaoNumeroEmpresa);
        etDialogAlteracaoLogradouroEmpresa = mView.findViewById(R.id.etDialogAlteracaoLogradouroEmpresa);
        etComplementoCadastroEmpresa = mView.findViewById(R.id.etComplementoCadastroEmpresa);
        etDialogAlteracaoBairroEmpresa = mView.findViewById(R.id.etDialogAlteracaoBairroEmpresa);
        etDialogAlteracaoCidadeEmpresa = mView.findViewById(R.id.etDialogAlteracaoCidadeEmpresa);
        etDialogAlteracaoEstadoEmpresa = mView.findViewById(R.id.etDialogAlteracaoEstadoEmpresa);

        Button btDialogAlteracaoEnderecoCancelar = mView.findViewById(R.id.btDialogAlteracaoEnderecoCancelar);
        Button btDialogAlteracaoEnderecoAlterar = mView.findViewById(R.id.btDialogAlteracaoEnderecoAlterar);

        etDialogAlteracaoCepEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getCep());
        etDialogAlteracaoNumeroEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getNumero());
        etDialogAlteracaoLogradouroEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getLogradouro());

        if (getEmpresaControl().getEmpresa().getEndereco().getComplemento() != null && !getEmpresaControl().getEmpresa().getEndereco().getComplemento().isEmpty()) {
            etComplementoCadastroEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getComplemento());
        }

        etDialogAlteracaoBairroEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getBairro());
        etDialogAlteracaoCidadeEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getCidade());
        etDialogAlteracaoEstadoEmpresa.setText(getEmpresaControl().getEmpresa().getEndereco().getEstado());

        btDialogAlteracaoEnderecoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        etDialogAlteracaoCepEmpresa.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    String dados = etDialogAlteracaoCepEmpresa.getText().toString();

                    Endereco endereco = Util.buscaCep(dados);

                    if (endereco != null) {

                        etDialogAlteracaoLogradouroEmpresa.setText(endereco.getLogradouro());
                        etDialogAlteracaoBairroEmpresa.setText(endereco.getBairro());
                        etDialogAlteracaoCidadeEmpresa.setText(endereco.getCidade());
                        etDialogAlteracaoEstadoEmpresa.setText(endereco.getEstado());

                    } else {

                        Toast.makeText(getContext(), Constantes.M_INF_ENDERECO_N_ENCONTRADO, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btDialogAlteracaoEnderecoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Endereco endereco = new Endereco();

                endereco.setIdEndereco(getEmpresaControl().getEmpresa().getEndereco().getIdEndereco());
                endereco.setCep(etDialogAlteracaoCepEmpresa.getText().toString());
                endereco.setNumero(etDialogAlteracaoNumeroEmpresa.getText().toString());
                endereco.setLogradouro(etDialogAlteracaoLogradouroEmpresa.getText().toString());
                endereco.setComplemento(etComplementoCadastroEmpresa.getText().toString());
                endereco.setBairro(etDialogAlteracaoBairroEmpresa.getText().toString());
                endereco.setCidade(etDialogAlteracaoCidadeEmpresa.getText().toString());
                endereco.setEstado(etDialogAlteracaoEstadoEmpresa.getText().toString());

                if (getEmpresaControl().alteracaoEnderecoEmpresa(endereco)) {

                    tvEnderecoBairroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getBairro());
                    tvEnderecoCidadePerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getCidade());
                    tvEnderecoEstadoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getEstado());
                    tvEnderecoNumeroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getNumero());
                    tvEnderecoLogradouroPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getLogradouro());

                    if (getEmpresaControl().getEmpresa().getEndereco().getComplemento() != null &&
                            !getEmpresaControl().getEmpresa().getEndereco().getComplemento().isEmpty()) {
                        tvEnderecoComplementoPerfilEmpresaInformacoes.setTextSize(14);
                        tvEnderecoComplementoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getEndereco().getComplemento());
                    }

                } else {
                    Toast.makeText(getContext(), Constantes.M_FALHA_ALTERAR_ENDERECO_EMPRESA, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();

    }

    private void abrirDialogAlteracaoDescricaoEmpresa() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_alteracao_descricao_empresa, null);

        etDialogAlteracaoDescricaoPerfilEmpresa = mView.findViewById(R.id.etDialogAlteracaoDescricaoPerfilEmpresa);

        Button btDialogAlteracaoDescricaoCancelar = mView.findViewById(R.id.btDialogAlteracaoDescricaoCancelar);
        Button btDialogAlteracaoDescricaoAlterar = mView.findViewById(R.id.btDialogAlteracaoDescricaoAlterar);

        if (getEmpresaControl().getEmpresa().getDescricaoEmpresa() != null && !getEmpresaControl().getEmpresa().getDescricaoEmpresa().isEmpty()) {
            etDialogAlteracaoDescricaoPerfilEmpresa.setText(getEmpresaControl().getEmpresa().getDescricaoEmpresa());
        }

        btDialogAlteracaoDescricaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        btDialogAlteracaoDescricaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEmpresaControl().alteracaoDescricaoEmpresa(etDialogAlteracaoDescricaoPerfilEmpresa.getText().toString())) {
                    tvDescricaoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getDescricaoEmpresa());
                } else {
                    Toast.makeText(getContext(), Constantes.M_FALHA_ALTERAR_DESCRICAO_EMPRESA, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();
    }

    private void abrirDialogAlteracaoNomeEAtendimento() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_alteracao_nome_atendimento_perfil_empresa, null);

        etDialogAlteracaoNomePerfilEmpresa = mView.findViewById(R.id.etDialogAlteracaoNomePerfilEmpresa);
        etDialogAlteracaoAtendimentoUnissexPerfilEmpresa = mView.findViewById(R.id.etDialogAlteracaoAtendimentoUnissexPerfilEmpresa);
        etDialogAlteracaoAtendimentoFemininoPerfilEmpresa = mView.findViewById(R.id.etDialogAlteracaoAtendimentoFemininoPerfilEmpresa);
        etDialogAlteracaoAtendimentoMasculinoPerfilEmpresa = mView.findViewById(R.id.etDialogAlteracaoAtendimentoMasculinoPerfilEmpresa);

        Button btDialogAlteracaoNomeAntendimentoCancelar = mView.findViewById(R.id.btDialogAlteracaoNomeAntendimentoCancelar);
        Button btDialogAlteracaoNomeAntendimentoAlterar = mView.findViewById(R.id.btDialogAlteracaoNomeAntendimentoAlterar);

        etDialogAlteracaoNomePerfilEmpresa.setText(getEmpresaControl().getEmpresa().getNomeFantasia());

        if (getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX)) {
            etDialogAlteracaoAtendimentoUnissexPerfilEmpresa.setChecked(true);
        } else if (getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.FEMININO)) {
            etDialogAlteracaoAtendimentoFemininoPerfilEmpresa.setChecked(true);
        } else if (getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.MASCULINO)) {
            etDialogAlteracaoAtendimentoMasculinoPerfilEmpresa.setChecked(true);
        }

        btDialogAlteracaoNomeAntendimentoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        btDialogAlteracaoNomeAntendimentoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEmpresaControl().alteracaoNomeEAtendimentoEmpresa(etDialogAlteracaoNomePerfilEmpresa.getText().toString(), etDialogAlteracaoAtendimentoUnissexPerfilEmpresa.isChecked() ? Constantes.UNISSEX :
                        etDialogAlteracaoAtendimentoFemininoPerfilEmpresa.isChecked() ? Constantes.FEMININO : Constantes.MASCULINO)) {
                    tvNomePerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getNomeFantasia());
                    tvPublicoAlvoPerfilEmpresaInformacoes.setText(getEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + getEmpresaControl().getEmpresa().getPublicoAlvo());
                } else {
                    Toast.makeText(getContext(), Constantes.M_FALHA_ALTERAR_NOME_ATENDIMENTO_EMPRESA, Toast.LENGTH_SHORT).show();
                }
                dialogAlert.dismiss();
            }
        });

        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();

    }


    private void pedirPermissaoDeAcesso() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA);
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
                    Toast.makeText(getContext(), Constantes.M_INF_PERMISSAO_ACESSO_GALERIA, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void adicionarImagem() {

        final CharSequence[] opcoes = {
                "Pegar uma imagem",
                "Tirar uma foto",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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

            arquivoImagem = new File(caminhoImagemCamera);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoImagem));

            startActivityForResult(intent, Constantes.COD_REQUEST_CAMERA_CLIENTE);
        }
    }

    private void abrirGaleria() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Selecione"), Constantes.COD_REQUEST_GALERIA_CLIENTE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constantes.COD_REQUEST_GALERIA_CLIENTE:

                this.receberFotoDaGaleria(data);

                break;

            case Constantes.COD_REQUEST_CAMERA_CLIENTE:

                this.receberFotoDaCamera();

                break;
        }
    }

    private void receberFotoDaCamera() {

        MediaScannerConnection.scanFile(getContext(), new String[]{caminhoImagemCamera}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

                Log.i("Arquivo", "" + caminhoImagemCamera);

            }
        });
        arquivoImagem = new File(caminhoImagemCamera);
        if (getEmpresaControl().alteracaoLogoEmpresa(arquivoImagem)) {
            ivImagemLogoPerfilEmpresaInformacoes.setImageBitmap(BitmapFactory.decodeFile(caminhoImagemCamera));
        } else {
            Toast.makeText(getContext(), Constantes.M_FALHA_ALTERAR_LOGO_EMPRESA, Toast.LENGTH_SHORT).show();
        }
    }

    private void receberFotoDaGaleria(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String foto = cursor.getString(0);

            arquivoImagem = new File(foto);

            if (getEmpresaControl().alteracaoLogoEmpresa(arquivoImagem)) {
                ivImagemLogoPerfilEmpresaInformacoes.setImageBitmap(BitmapFactory.decodeFile(foto));
            } else {
                Toast.makeText(getContext(), Constantes.M_FALHA_ALTERAR_LOGO_EMPRESA, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void chamarDialogCadastroContato() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_contato_cadastro, null);

        spinnerDialogTipoContato = mView.findViewById(R.id.spinnerDialogTipoContato);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.tipo_contato, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogTipoContato.setAdapter(adapter);
        spinnerDialogTipoContato.setOnItemSelectedListener(this);

        etDialogCadastroContato = mView.findViewById(R.id.etDialogCadastroContato);
        btDialogCancelarCadastroContato = mView.findViewById(R.id.btDialogCancelarCadastroContato);
        btDialogCadastrarContatoMaisUm = mView.findViewById(R.id.btDialogCadastrarContatoMaisUm);
        btDialogCadastroContato = mView.findViewById(R.id.btDialogCadastroContato);

        listContatoParaCadastro = new ArrayList<>();
        btDialogCancelarCadastroContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });
        btDialogCadastrarContatoMaisUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDialogCadastroContato.getText().toString().isEmpty()) {

                    Contato objContato = new Contato();

                    objContato.setTipoContato(tipoContato);
                    objContato.setContato(etDialogCadastroContato.getText().toString());

                    listContatoParaCadastro.add(objContato);
                    etDialogCadastroContato.setText("");
                } else {
                    Toast.makeText(getContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();
                }
            }
        });
        btDialogCadastroContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDialogCadastroContato.getText().toString().isEmpty()) {

                    Contato objContato = new Contato();
                    objContato.setTipoContato(tipoContato);
                    objContato.setContato(etDialogCadastroContato.getText().toString());
                    listContatoParaCadastro.add(objContato);
                    if (getEmpresaControl().cadastrarContatoEmpresa(listContatoParaCadastro)) {
                        adapterListaContato.setListaContato(getEmpresaControl().getEmpresa().getListaContato());
                    } else {
                        Toast.makeText(getContext(), Constantes.M_FALHA_AO_CADASTRAR_CONTATO_EMPRESA, Toast.LENGTH_SHORT).show();
                    }
                    dialogAlert.dismiss();
                } else {
                    Toast.makeText(getContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialog.setView(mView);
        dialogAlert = alertDialog.create();
        dialogAlert.show();
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


}


