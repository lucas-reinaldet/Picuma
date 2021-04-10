package faculdade.br.picuma.control;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.R;
import faculdade.br.picuma.dialog.DialogDatePicker;
import faculdade.br.picuma.model.Cliente;
import faculdade.br.picuma.model.Comentario;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.model.Galeria;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.view.VisualizarPerfilEmpresaActivity;
import faculdade.br.picuma.webService.CadastrarAgendamento;
import faculdade.br.picuma.webService.SolicitarEmpresaPorId;
import faculdade.br.picuma.webService.SolicitarListaComentario;
import faculdade.br.picuma.webService.SolicitarListaGaleriaFotos;
import faculdade.br.picuma.webService.SolicitarListaGrupoServicoPrestado;
import faculdade.br.picuma.webService.SolicitarListaServicoPrestado;

public class VisualizarEmpresaControl {

    private List<byte[]> listaByteFotos;
    private List<GrupoServico> listaGrupoServicoPrestado;
    private GrupoServico paramParaListagemDeServicosPrestados;
    private boolean listadoProfissionaisPorServico;
    private List<Funcionario> paramParaListagemDeProfissional;
    private FragmentManager fragmentManager;
    private ServicoPrestado servicoPrestadoParaAgendamento;

    public static VisualizarEmpresaControl getInstance() {
        if (instance == null) {
            instance = new VisualizarEmpresaControl();
        }
        return instance;
    }

    private static VisualizarEmpresaControl instance;
    private Empresa empresa;
    private Cliente cliente;
    private Galeria paramParaListagemDeFotoEmpresa;
    private List<Comentario> listaComentario;
    private boolean primeiraExcComentarioEmpresa = true;
    private boolean primeiraExecucaoInfoEmpresa = true;
    private boolean primeiraExecucaoServicosPrestados = true;
    private boolean listadoFotoGaleria = false;
    private boolean apresentandoFotosGaleria = false;
    private boolean listadoServicoPrestado = false;
    private View viewInfoEmpresa;
    private View viewListaGaleriaEmpresa;
    private View viewGaleriaEmpresa;
    private View viewComentarioEmpresa;
    private View viewServicosEmpresa;
    private View viewGrupoServicosEmpresa;
    private HorarioMarcado dadosAgendamento;
    private DialogDatePicker dialogDatePicker;

    public FragmentManager getSupportFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public HorarioMarcado getDadosAgendamento() {
        if (dadosAgendamento == null) {
            dadosAgendamento = new HorarioMarcado();
        }
        return dadosAgendamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void prepararDadosParaVisualizar(Empresa empresa, Context context, Cliente cliente) {
        if (solicitarDadosEmpresa(empresa.getIdEmpresa())) {
            this.cliente = cliente;
            solicitarDadosBancoDeDados();
            Intent intent = new Intent(context, VisualizarPerfilEmpresaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, Constantes.M_FALHA_AO_ACESSAR_PERFIL_EMPRESA, Toast.LENGTH_SHORT).show();
        }
    }

    private void solicitarDadosBancoDeDados() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    solicitarComentarios();
                    solicitarGaleriaEFotos();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void solicitarComentarios() throws ExecutionException, InterruptedException {
        SolicitarListaComentario solicitarListaComentario = new SolicitarListaComentario();
        setListaComentario(solicitarListaComentario.execute(getEmpresa().getIdEmpresa()).get());
    }

    private void solicitarGaleriaEFotos() throws ExecutionException, InterruptedException {
        SolicitarListaGaleriaFotos solicitarListaGaleriaFotos = new SolicitarListaGaleriaFotos();
        getEmpresa().setListaGaleria(solicitarListaGaleriaFotos.execute(getEmpresa().getIdEmpresa()).get());
    }

    private void solicitarServicoPrestado() throws ExecutionException, InterruptedException {
        SolicitarListaServicoPrestado solicitarListaServicoPrestado = new SolicitarListaServicoPrestado();
        getEmpresa().setListaServicoPrestado(solicitarListaServicoPrestado.execute(getEmpresa().getIdEmpresa()).get());
    }

    private void solicitarGrupoServicoPrestado() throws ExecutionException, InterruptedException {
        SolicitarListaGrupoServicoPrestado solicitarListaGrupoServicoPrestado = new SolicitarListaGrupoServicoPrestado();
        setListaGrupoServicoPrestado(solicitarListaGrupoServicoPrestado.execute(getEmpresa().getIdEmpresa()).get());
    }

    private boolean solicitarDadosEmpresa(int idEmpresa) {
        SolicitarEmpresaPorId solicitarEmpresaPorId = new SolicitarEmpresaPorId();
        try {
            Empresa dadosEmpresa = solicitarEmpresaPorId.execute(idEmpresa).get();
            if (dadosEmpresa != null) {
                setEmpresa(null);
                setEmpresa(dadosEmpresa);
                solicitarGrupoServicoPrestado();
                solicitarServicoPrestado();
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void destroyInstance() {
        viewInfoEmpresa = null;
        viewComentarioEmpresa = null;
        viewGaleriaEmpresa = null;
        viewGrupoServicosEmpresa = null;
        viewListaGaleriaEmpresa = null;
        viewServicosEmpresa = null;
        primeiraExcComentarioEmpresa = true;
        primeiraExecucaoInfoEmpresa = true;
        primeiraExecucaoServicosPrestados = true;
        listadoFotoGaleria = false;
        apresentandoFotosGaleria = false;
        listadoServicoPrestado = false;
        dialogDatePicker = null;
        dadosAgendamento = null;
    }

    public boolean primeiraExecucaoComentarioEmpresa() {
        if (primeiraExcComentarioEmpresa) {
            primeiraExcComentarioEmpresa = false;
            return true;
        }
        return primeiraExcComentarioEmpresa;
    }


    public void denunciarComentario(View v, Comentario comentario) {
    }

    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    public View getViewComentarioEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewComentarioEmpresa == null) {
            viewComentarioEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizar_classificacao_empresa, container, false);
        }
        return viewComentarioEmpresa;
    }

    public View getViewGaleriaEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewGaleriaEmpresa == null) {
            viewGaleriaEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizar_galeria_empresa, container, false);
        }
        return viewGaleriaEmpresa;
    }

    public void setParamParaListagemDeFotoEmpresa(Galeria paramParaListagemDeFotoEmpresa) {
        this.paramParaListagemDeFotoEmpresa = paramParaListagemDeFotoEmpresa;
    }

    public Galeria getParamParaListagemDeFotoEmpresa() {
        return paramParaListagemDeFotoEmpresa;
    }

    public void setListadoFotoGaleria(boolean listadoFotoGaleria) {
        this.listadoFotoGaleria = listadoFotoGaleria;
    }

    public boolean isListadoFotoGaleria() {
        return listadoFotoGaleria;
    }

    public View getViewListaGaleriaEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewListaGaleriaEmpresa == null) {
            viewListaGaleriaEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_grupo_galeria_empresa, container, false);
        }
        return viewListaGaleriaEmpresa;
    }

    public View getViewInfoEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewInfoEmpresa == null) {
            viewInfoEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizar_informacoes_empresa, container, false);
        }
        return viewInfoEmpresa;
    }

    public boolean getPrimeiraExecucaoInfoEmpresa() {
        if (primeiraExecucaoInfoEmpresa) {
            primeiraExecucaoInfoEmpresa = false;
            return true;
        } else {
            return primeiraExecucaoInfoEmpresa;
        }
    }

    public List<byte[]> getListaByteFotos() {
        return listaByteFotos;
    }

    public void setListaByteFotos(List<byte[]> listaByteFotos) {
        this.listaByteFotos = listaByteFotos;
    }

    public void setApresentandoFotosGaleria(boolean apresentandoFotosGaleria) {
        this.apresentandoFotosGaleria = apresentandoFotosGaleria;
    }

    public boolean isApresentandoFotosGaleria() {
        return apresentandoFotosGaleria;
    }

    public List<GrupoServico> getListaGrupoServicoPrestado() {
        return listaGrupoServicoPrestado;
    }

    public void setParamParaListagemDeServicosPrestados(GrupoServico paramParaListagemDeServicosPrestados) {
        this.paramParaListagemDeServicosPrestados = paramParaListagemDeServicosPrestados;
    }

    public GrupoServico getParamParaListagemDeServicosPrestados() {
        return paramParaListagemDeServicosPrestados;
    }

    public void setListadoServicoPrestado(boolean listadoServicoPrestado) {
        this.listadoServicoPrestado = listadoServicoPrestado;
    }

    public View getViewServicosEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewServicosEmpresa == null) {
            viewServicosEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_visualizar_servicos_empresa, container, false);
        }
        return viewServicosEmpresa;
    }

    public boolean isListadoServicoPrestado() {
        return listadoServicoPrestado;
    }

    public View getViewGrupoServicosEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewGrupoServicosEmpresa == null) {
            viewGrupoServicosEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_grupo_servico, container, false);
        }
        return viewGrupoServicosEmpresa;
    }

    public boolean getPrimeiraExecucaoServicosPrestados() {
        if (primeiraExecucaoServicosPrestados) {
            primeiraExecucaoServicosPrestados = false;
            return true;
        }
        return primeiraExecucaoServicosPrestados;
    }

    public void iniciarCadastroAgendamento(Context context) {
        openDatePicker();
    }

    public void setListadoProfissionaisPorServico(boolean listadoProfissionaisPorServico) {
        this.listadoProfissionaisPorServico = listadoProfissionaisPorServico;
    }

    public List<Funcionario> getParamParaListagemDeProfissional() {
        return paramParaListagemDeProfissional;
    }

    public boolean getListadoProfissionaisPorServico() {
        return listadoProfissionaisPorServico;
    }

    public void setParamParaListagemDeProfissional(List<Funcionario> paramParaListagemDeProfissional) {
        this.paramParaListagemDeProfissional = paramParaListagemDeProfissional;
    }

    private void openDatePicker() {
        getDialogDatePicker().show(getSupportFragmentManager(), "dialog");
    }

    public void setServicoPrestadoParaAgendamento(ServicoPrestado servicoPrestadoParaAgendamento) {
        this.servicoPrestadoParaAgendamento = servicoPrestadoParaAgendamento;
    }

    public ServicoPrestado getServicoPrestadoParaAgendamento() {
        return servicoPrestadoParaAgendamento;
    }

    public DialogDatePicker getDialogDatePicker() {
        if (dialogDatePicker == null) {
            dialogDatePicker = new DialogDatePicker();
        }
        return dialogDatePicker;
    }

    public void setDadosAgendamento(HorarioMarcado dadosAgendamento) {
        this.dadosAgendamento = dadosAgendamento;
    }

    public boolean cadastrarAgendamento() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(getEmpresa().getIdEmpresa());
        getDadosAgendamento().setEmpresa(empresa);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(getCliente().getIdCliente());
        getDadosAgendamento().setCliente(cliente);
        String param = new Gson().toJson(getDadosAgendamento());
        CadastrarAgendamento cadastrarAgendamento = new CadastrarAgendamento();
        try {
            if (cadastrarAgendamento.execute(param).get()) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setListaGrupoServicoPrestado(List<GrupoServico> listaGrupoServicoPrestado) {
        this.listaGrupoServicoPrestado = listaGrupoServicoPrestado;
    }

    public void setListaComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
