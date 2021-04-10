package faculdade.br.picuma.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFuncionario;
import faculdade.br.picuma.adapter.AdapterListaGrupoServico;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.model.Servico;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;

public class FragmentListaGrupoServicoPrestadoPerfilEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentManager fragmentManager;
    private ListView lvListaGrupoServicoPerfilEmpresa;
    private AdapterListaGrupoServico adapter;

    private EmpresaControl empresaControl;
    private ImageButton ibAdicionarServicoPerfilEmpresaServicosPrestados;
    private AlertDialog dialogAlert;
    private Spinner spinnerDialogGrupoServicoServicoPrestado;
    private Spinner spinnerDialogServicoCadastroServicoPrestado;
    private EditText etDialogTempoServicoCadastroServicoPrestado;
    private EditText etDialogDescricaoServicoCadastroServicoPrestado;
    private EditText etDialogValorServicoCadastroServicoPrestado;
    private Button btDialogCancelarCadastroServicoPrestado;
    private Button btDialogCadastroServicoPrestado;
    private ArrayList<Servico> listaServicoParaCadastro;
    private Servico servicoSelecionadoParaCadastro;
    private ListView lvDialogAssFuncionarioServicoCadastroServicoPrestado;
    private AdapterListaFuncionario adapterListaFuncionario;
    private List<Funcionario> listaAssFuncionarioServico;
    private Button btDialogAssFuncionarioCadastroServico;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = getEmpresaControl().getViewGrupoServicosEmpresa(inflater, container);
        getEmpresaControl().setListadoFotoGaleria(false);
        getEmpresaControl().setApresentandoFotosGaleria(false);
        getEmpresaControl().setListadoServicoPrestado(false);
        fragmentManager = getFragmentManager();
        lvListaGrupoServicoPerfilEmpresa = v.findViewById(R.id.lvListaGrupoServicoPerfilEmpresa);
        adapter = new AdapterListaGrupoServico(getContext());
        lvListaGrupoServicoPerfilEmpresa.setAdapter(adapter);
        lvListaGrupoServicoPerfilEmpresa.setOnItemClickListener(this);
        if (getEmpresaControl().getEmpresa().getListaServicoPrestado() != null &&
                getEmpresaControl().getEmpresa().getListaServicoPrestado().size() > 0) {
            updateList(getEmpresaControl().getListaGrupoServicoPrestado());
        }

        ibAdicionarServicoPerfilEmpresaServicosPrestados = v.findViewById(R.id.ibAdicionarServicoPerfilEmpresaServicosPrestados);

        ibAdicionarServicoPerfilEmpresaServicosPrestados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_cadastro_servico_prestado, null);
                listaAssFuncionarioServico = new ArrayList<>();
                spinnerDialogGrupoServicoServicoPrestado = mView.findViewById(R.id.spinnerDialogGrupoServicoServicoPrestado);
                spinnerDialogServicoCadastroServicoPrestado = mView.findViewById(R.id.spinnerDialogServicoCadastroServicoPrestado);
                etDialogTempoServicoCadastroServicoPrestado = mView.findViewById(R.id.etDialogTempoServicoCadastroServicoPrestado);
                etDialogDescricaoServicoCadastroServicoPrestado = mView.findViewById(R.id.etDialogDescricaoServicoCadastroServicoPrestado);
                etDialogValorServicoCadastroServicoPrestado = mView.findViewById(R.id.etDialogValorServicoCadastroServicoPrestado);
                btDialogCancelarCadastroServicoPrestado = mView.findViewById(R.id.btDialogCancelarCadastroServicoPrestado);
                btDialogCadastroServicoPrestado = mView.findViewById(R.id.btDialogCadastroServicoPrestado);
                lvDialogAssFuncionarioServicoCadastroServicoPrestado = mView.findViewById(R.id.lvDialogAssFuncionarioServicoCadastroServicoPrestado);
                btDialogAssFuncionarioCadastroServico = mView.findViewById(R.id.btDialogAssFuncionarioCadastroServico);

                adapterListaFuncionario = new AdapterListaFuncionario(getContext());
                lvDialogAssFuncionarioServicoCadastroServicoPrestado.setAdapter(adapterListaFuncionario);
                if (listaAssFuncionarioServico != null && listaAssFuncionarioServico.size() > 0) {
                    adapterListaFuncionario.setListaFuncionario(listaAssFuncionarioServico);
                }
                lvDialogAssFuncionarioServicoCadastroServicoPrestado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                                    listaAssFuncionarioServico.remove(position);
                                    adapterListaFuncionario.setListaFuncionario(listaAssFuncionarioServico);
                                    dialog.dismiss();
                                } else {

                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();

                        return true;
                    }
                });

                btDialogAssFuncionarioCadastroServico.setOnClickListener(new View.OnClickListener() {

                    public ArrayList<Funcionario> resultListaFuncionario;
                    public AdapterListaFuncionario adapterDialogListaFunc;
                    public Button btDialogCancelarListaFuncionario;
                    public ListView lvDialogListaFuncionario;
                    public EditText etDialogProcuraFuncionario;
                    public AlertDialog dialogAlertAssFunc;

                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        View mView = getLayoutInflater().inflate(R.layout.dialog_lista_funcionario, null);

                        etDialogProcuraFuncionario = mView.findViewById(R.id.etDialogProcuraFuncionario);
                        lvDialogListaFuncionario = mView.findViewById(R.id.lvDialogListaFuncionario);
                        btDialogCancelarListaFuncionario = mView.findViewById(R.id.btDialogCancelarListaFuncionario);

                        adapterDialogListaFunc = new AdapterListaFuncionario(getContext());
                        lvDialogListaFuncionario.setAdapter(adapterDialogListaFunc);
                        resultListaFuncionario = new ArrayList<>();
                        Pesquisar();

                        updateListFuncionarioPesquisa(resultListaFuncionario);
                        btDialogCancelarListaFuncionario.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAlertAssFunc.dismiss();
                            }
                        });

                        lvDialogListaFuncionario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                listaAssFuncionarioServico.add(resultListaFuncionario.get(position));
                                updateListaFuncionarioSelecionado(listaAssFuncionarioServico);
                                dialogAlertAssFunc.dismiss();
                            }
                        });

                        etDialogProcuraFuncionario.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                Pesquisar();
                                updateListFuncionarioPesquisa(resultListaFuncionario);
                            }
                        });

                        alertDialog.setView(mView);
                        dialogAlertAssFunc = alertDialog.create();
                        dialogAlertAssFunc.show();
                    }

                    private void updateListFuncionarioPesquisa(ArrayList<Funcionario> resultListaFuncionario) {
                        adapterDialogListaFunc.setListaFuncionario(resultListaFuncionario);
                    }

                    public void Pesquisar() {
                        int textlength = etDialogProcuraFuncionario.getText().length();
                        resultListaFuncionario.clear();

                        for (Funcionario funcionario : getEmpresaControl().getEmpresa().getListaFuncionarios()) {
                            if (textlength <= funcionario.getNomeFuncionario().length()) {
                                if (etDialogProcuraFuncionario.getText().toString().equalsIgnoreCase((String) funcionario.getNomeFuncionario().subSequence(0, textlength))) {
                                    resultListaFuncionario.add(funcionario);
                                }
                            }
                        }
                    }

                    private void updateListaFuncionarioSelecionado(List<Funcionario> listaAssFuncionarioServico) {
                        adapterListaFuncionario.setListaFuncionario(listaAssFuncionarioServico);
                    }
                });

                final ArrayAdapter<GrupoServico> adapterSpinnerGrupo = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getEmpresaControl().getListaGrupoServico());
                ArrayAdapter<GrupoServico> spinnerAdapter = adapterSpinnerGrupo;
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDialogGrupoServicoServicoPrestado.setAdapter(spinnerAdapter);

                spinnerDialogGrupoServicoServicoPrestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        listaServicoParaCadastro = new ArrayList<>();

                        for (Servico servico : getEmpresaControl().getListaServico()) {
                            if (servico.getGrupoServico().getIdGrupoServico() == getEmpresaControl().getListaGrupoServico().get(position).getIdGrupoServico()) {
                                listaServicoParaCadastro.add(servico);
                            }
                        }

                        if (listaServicoParaCadastro.size() > 0) {
                            ArrayAdapter<Servico> adapterSpinnerServico = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listaServicoParaCadastro);
                            ArrayAdapter<Servico> spinnerAdapter = adapterSpinnerServico;
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDialogServicoCadastroServicoPrestado.setAdapter(spinnerAdapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerDialogServicoCadastroServicoPrestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        servicoSelecionadoParaCadastro = listaServicoParaCadastro.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btDialogCancelarCadastroServicoPrestado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlert.dismiss();
                    }
                });

                btDialogCadastroServicoPrestado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etDialogTempoServicoCadastroServicoPrestado.getText() != null
                                && !etDialogTempoServicoCadastroServicoPrestado.getText().toString().isEmpty()) {
                            if (etDialogValorServicoCadastroServicoPrestado.getText() != null
                                    && !etDialogValorServicoCadastroServicoPrestado.getText().toString().isEmpty()) {
                                ServicoPrestado servicoPrestado = new ServicoPrestado();

                                servicoPrestado.setServico(servicoSelecionadoParaCadastro);
                                servicoPrestado.setValorServico(etDialogValorServicoCadastroServicoPrestado.getText().toString());
                                servicoPrestado.setTempoAproxServico(Integer.valueOf(etDialogTempoServicoCadastroServicoPrestado.getText().toString()));

                                if (etDialogDescricaoServicoCadastroServicoPrestado.getText() != null && !etDialogDescricaoServicoCadastroServicoPrestado.getText().toString().isEmpty()) {
                                    servicoPrestado.setInformacoesServico(etDialogDescricaoServicoCadastroServicoPrestado.getText().toString());
                                }

                                if (listaAssFuncionarioServico.size() > 0) {
                                    List<Integer> listaFuncId = new ArrayList<>();
                                    for (Funcionario funcionario : listaAssFuncionarioServico) {
                                        listaFuncId.add(funcionario.getIdFuncionario());
                                    }
                                    servicoPrestado.setListaIdFuncionario(listaFuncId);
                                }

                                if (getEmpresaControl().cadastrarServicoPrestado(servicoPrestado)) {
                                    updateList(getEmpresaControl(). getListaGrupoServicoPrestado());
                                    dialogAlert.dismiss();
                                } else {
                                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_CADASTRAR_SERVICO_EMPRESA, Toast.LENGTH_SHORT).show();
                                    dialogAlert.dismiss();
                                }
                            } else {
                                Toast.makeText(getContext(), Constantes.M_AT_VALOR_SERVICO_VAZIO, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), Constantes.M_AT_TEMPO_SERVICO_VAZIO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertDialog.setView(mView);
                dialogAlert = alertDialog.create();
                dialogAlert.show();
            }
        });
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FragmentManager fragmentManagerAux = getFragmentManager();
        FragmentTransaction fragmentTransactionAux = fragmentManagerAux.beginTransaction();
        fragmentTransactionAux.replace(R.id.container_perfil_empresa_galeria, new FragmentPerfilEmpresaListaGaleria(), "galeria");
        fragmentTransactionAux.commit();

        getEmpresaControl().setParamParaListagemDeServicosPrestados(getEmpresaControl().getListaGrupoServicoPrestado().get(position));
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaServicosPrestadosPerfilEmpresa(), "servicos_prestados");
        fragmentTransaction.commit();
    }

    private void updateList(List<GrupoServico> dados) {
        adapter.setListaGrupoServicos(dados);
    }
}
