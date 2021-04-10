package faculdade.br.picuma.control;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.R;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaInformacoes;
import faculdade.br.picuma.model.AreaAtuacao;
import faculdade.br.picuma.model.Comentario;
import faculdade.br.picuma.model.Contato;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.model.FotoGaleria;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.model.Galeria;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.model.HorarioEmpresa;
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.model.Servico;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.view.AgendamentosPerfilEmpresaActivity;
import faculdade.br.picuma.view.PerfilEmpresaActivity;
import faculdade.br.picuma.webService.AlterarDescricaoEmpresa;
import faculdade.br.picuma.webService.AlterarEnderecoEmpresa;
import faculdade.br.picuma.webService.AlterarLogoEmpresa;
import faculdade.br.picuma.webService.AlterarNomeEAtendimentoEmpresa;
import faculdade.br.picuma.webService.AlterarStatusHorarioMarcado;
import faculdade.br.picuma.webService.CadastrarContatoEmpresa;
import faculdade.br.picuma.webService.CadastrarFotoGaleriaEmpresa;
import faculdade.br.picuma.webService.CadastrarFuncionarioEmpresa;
import faculdade.br.picuma.webService.CadastrarHorarioEmpresa;
import faculdade.br.picuma.webService.CadastrarServicoPrestadoEmpresa;
import faculdade.br.picuma.webService.CadastroUsuario;
import faculdade.br.picuma.webService.DesassociarFuncionarioServicoPrestado;
import faculdade.br.picuma.webService.DesativarFuncionarioEmpresa;
import faculdade.br.picuma.webService.RemoverContatoEmpresa;
import faculdade.br.picuma.webService.RemoverFotoGaleriaEmpresa;
import faculdade.br.picuma.webService.RemoverHorarioEmpresa;
import faculdade.br.picuma.webService.SolicitarGeocodeEndereco;
import faculdade.br.picuma.webService.SolicitarListaAreaAtuacao;
import faculdade.br.picuma.webService.SolicitarListaComentario;
import faculdade.br.picuma.webService.SolicitarListaGaleriaFotos;
import faculdade.br.picuma.webService.SolicitarListaGrupoServicoPorIdAreaAtuacao;
import faculdade.br.picuma.webService.SolicitarListaGrupoServicoPrestado;
import faculdade.br.picuma.webService.SolicitarListaServicoPorIdAreaAtuacao;
import faculdade.br.picuma.webService.SolicitarListaServicoPrestado;

public class EmpresaControl {

    private List<AreaAtuacao> listaAreaAtuacao;
    private View viewClassificacaoEmpresa;
    private List<Comentario> listaComentario;
    private View viewInfomacoesEmpresa;
    private boolean primeiraExecucaoInfoEmpresa = true;
    private Empresa empresa;
    private View viewServicosEmpresa;
    private View viewGrupoServicosEmpresa;
    private boolean listadoFotoGaleria;
    private boolean apresentandoFotosGaleria;
    private List<GrupoServico> listaGrupoServicoPrestado;
    private GrupoServico paramParaListagemDeServicosPrestados;
    private boolean listadoServicoPrestado;
    private List<Funcionario> paramParaListagemDeProfissional;
    private boolean listadoProfissionaisPorServico;
    private View viewGaleriaEmpresa;
    private View viewListaGaleriaEmpresa;
    private Galeria paramParaListagemDeFotoEmpresa;
    private List<byte[]> listaByteFotos;
    private Login login;
    private View viewFuncionariosEmpresa;
    private PerfilEmpresaActivity perfilEmpresaActivity;
    private FragmentPerfilEmpresaInformacoes fragmentPerfilEmpresaInformacoes;
    private List<GrupoServico> listaGrupoServico;
    private List<Servico> listaServico;
    private SupportMapFragment supportMapFragment;
    private List<HorarioMarcado> listaHorarioMarcado;
    private ServicoPrestado paramDeListagemFuncionarioServicoPrestado;

    private EmpresaControl() {
    }

    private void solicitarDadosBancoDeDados() {
        try {
            solicitarGrupoServicoPrestado();
            solicitarServicoPrestado();
            solicitarGrupoServico();
            solicitarServico();
            solicitarGaleriaEFotos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void solicitarComentarios() {
        SolicitarListaComentario solicitarListaComentario = new SolicitarListaComentario();
        try {
            setListaComentario(solicitarListaComentario.execute(getEmpresa().getIdEmpresa()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    private void solicitarServico() throws ExecutionException, InterruptedException {

        SolicitarListaServicoPorIdAreaAtuacao solicitarListaServico = new SolicitarListaServicoPorIdAreaAtuacao();
        setListaServico(solicitarListaServico.execute(getEmpresa().getAreaAtuacao().getIdAreaAtuacao()).get());
    }

    private void solicitarGrupoServico() throws ExecutionException, InterruptedException {

        SolicitarListaGrupoServicoPorIdAreaAtuacao solicitarListaGrupoServico = new SolicitarListaGrupoServicoPorIdAreaAtuacao();
        setListaGrupoServico(solicitarListaGrupoServico.execute(getEmpresa().getAreaAtuacao().getIdAreaAtuacao()).get());
    }

    private static EmpresaControl instance;

    public static EmpresaControl getInstance() {
        if (instance == null) {
            instance = new EmpresaControl();
        }
        return instance;
    }

    public List<AreaAtuacao> getAreaAtuacao() {
        SolicitarListaAreaAtuacao buscaAreaAtuacao = new SolicitarListaAreaAtuacao();
        try {
            listaAreaAtuacao = buscaAreaAtuacao.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return listaAreaAtuacao;
    }

    public void cadastrarEmpresaControl(Context applicationContext, Login dadosParaCadastro) {

        dadosParaCadastro.getEmpresa().setEndereco(this.getLatitudeLongituteEndereco(dadosParaCadastro.getEmpresa().getEndereco()));

        if (dadosParaCadastro.getEmpresa().getEndereco() != null) {
            String jsonParacadastro = new Gson().toJson(dadosParaCadastro).replace("\"", "'");

            CadastroUsuario cadastroUsuario = new CadastroUsuario();

            String[] valores = new String[]{
                    Constantes.PATH_CADASTRO_EMPRESA,
                    jsonParacadastro
            };
            try {
                String result = cadastroUsuario.execute(valores).get();
                if (result != null && !result.isEmpty()) {
                    dadosParaApresentacao(applicationContext, new Gson().fromJson(result, Login.class));
                } else {
                    Toast.makeText(applicationContext, Constantes.M_FALHA_AO_CADASTRAR, Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(Constantes.TAG_CADASTRO_EMPRESA, Constantes.M_FALHA_RECUPERAR_LAT_LNG);
            Toast.makeText(applicationContext, Constantes.M_FALHA_AO_CADASTRAR, Toast.LENGTH_LONG).show();
        }
    }

    private Endereco getLatitudeLongituteEndereco(Endereco endereco) {

        SolicitarGeocodeEndereco buscaGeocodeEndereco = new SolicitarGeocodeEndereco();

        Endereco valores = null;

        String busca = endereco.getNumero().replace(" ", "") + ",+"
                + endereco.getLogradouro().replace(" ", "+") + ",+"
                + endereco.getBairro().replace(" ", "+") + ",+"
                + endereco.getCidade().replace(" ", "+") + ",+"
                + endereco.getEstado();

        try {
            valores = buscaGeocodeEndereco.execute(busca).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (valores != null) {

            endereco.setLongitute(valores.getLongitute());
            endereco.setLatitude(valores.getLatitude());

            return endereco;
        }
        return endereco;
    }

    public void dadosParaApresentacao(Context context, Login dados) {

        login = new Login();
        login.setUsuario(dados.getUsuario());
        login.setSenha(dados.getSenha());
        login.setLoginGoogle(dados.getLoginGoogle());
        login.setIdLogin(dados.getIdLogin());

        empresa = dados.getEmpresa();

        new Thread(new Runnable() {
            @Override
            public void run() {
                solicitarDadosBancoDeDados();
            }
        }).start();

        Intent intent = new Intent(context, PerfilEmpresaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public View getViewClassificacaoEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewClassificacaoEmpresa == null) {
            viewClassificacaoEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_classificacao, container, false);
        }
        return viewClassificacaoEmpresa;
    }

    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    public View getViewInfomacoesEmpresa(LayoutInflater inflater, ViewGroup container, FragmentPerfilEmpresaInformacoes fragmentPerfilEmpresaInformacoes) {
        if (viewInfomacoesEmpresa == null) {
            viewInfomacoesEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_informacoes, container, false);
            setFragmentPerfilEmpresaInformacoes(fragmentPerfilEmpresaInformacoes);
        }
        return viewInfomacoesEmpresa;
    }

    public boolean getPrimeiraExecucaoInfoEmpresa() {
        if (primeiraExecucaoInfoEmpresa) {
            primeiraExecucaoInfoEmpresa = false;
            return true;
        }
        return primeiraExecucaoInfoEmpresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public View getViewServicosEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewServicosEmpresa == null) {
            viewServicosEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_servicos, container, false);
        }
        return viewServicosEmpresa;
    }

    public View getViewGrupoServicosEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewGrupoServicosEmpresa == null) {
            viewGrupoServicosEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_grupo_servico_perfil_empresa, container, false);
        }
        return viewGrupoServicosEmpresa;
    }

    public void setListadoFotoGaleria(boolean listadoFotoGaleria) {
        this.listadoFotoGaleria = listadoFotoGaleria;
    }

    public void setApresentandoFotosGaleria(boolean apresentandoFotosGaleria) {
        this.apresentandoFotosGaleria = apresentandoFotosGaleria;
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

    public void setParamParaListagemDeProfissional(List<Funcionario> paramParaListagemDeProfissional) {
        this.paramParaListagemDeProfissional = paramParaListagemDeProfissional;
    }

    public void setListadoProfissionaisPorServico(boolean listadoProfissionaisPorServico) {
        this.listadoProfissionaisPorServico = listadoProfissionaisPorServico;
    }

    public List<Funcionario> getParamParaListagemDeProfissional() {
        return paramParaListagemDeProfissional;
    }

    public View getViewGaleriaEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewGaleriaEmpresa == null) {
            viewGaleriaEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_galeria, container, false);
        }
        return viewGaleriaEmpresa;
    }

    public View getViewListaGaleriaEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewListaGaleriaEmpresa == null) {
            viewListaGaleriaEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_lista_galeria, container, false);
        }
        return viewListaGaleriaEmpresa;
    }

    public void setParamParaListagemDeFotoEmpresa(Galeria paramParaListagemDeFotoEmpresa) {
        this.paramParaListagemDeFotoEmpresa = paramParaListagemDeFotoEmpresa;
    }

    public Galeria getParamParaListagemDeFotoEmpresa() {
        return paramParaListagemDeFotoEmpresa;
    }

    public void setListaByteFotos(List<byte[]> listaByteFotos) {
        this.listaByteFotos = listaByteFotos;
    }

    public View getViewFuncionariosEmpresa(LayoutInflater inflater, ViewGroup container) {
        if (viewFuncionariosEmpresa == null) {
            viewFuncionariosEmpresa = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_perfil_empresa_funcionarios, container, false);
        }
        return viewFuncionariosEmpresa;
    }

    public boolean alteracaoNomeEAtendimentoEmpresa(String nomeEmpresa, String atendimento) {
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(nomeEmpresa);
        empresa.setPublicoAlvo(atendimento);
        empresa.setIdEmpresa(getEmpresa().getIdEmpresa());

        AlterarNomeEAtendimentoEmpresa alterarNomeEAtendimentoEmpresa = new AlterarNomeEAtendimentoEmpresa();
        String param = new Gson().toJson(empresa).replace("\"", "'");
        try {
            if (alterarNomeEAtendimentoEmpresa.execute(param).get()) {
                getEmpresa().setNomeFantasia(empresa.getNomeFantasia());
                getEmpresa().setPublicoAlvo(empresa.getPublicoAlvo());
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

    public boolean alteracaoDescricaoEmpresa(String descricaoPerfilEmpresaText) {
        Empresa empresa = new Empresa();
        empresa.setDescricaoEmpresa(descricaoPerfilEmpresaText);
        empresa.setIdEmpresa(getEmpresa().getIdEmpresa());

        AlterarDescricaoEmpresa alterarDescricaoEmpresa = new AlterarDescricaoEmpresa();
        String param = new Gson().toJson(empresa).replace("\"", "'");
        try {
            if (alterarDescricaoEmpresa.execute(param).get()) {
                getEmpresa().setDescricaoEmpresa(empresa.getDescricaoEmpresa());
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

    public boolean alteracaoLogoEmpresa(File arquivoImagem) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(getEmpresa().getIdEmpresa());

        int tam = (int) arquivoImagem.length();
        byte[] fileArray = new byte[tam];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(arquivoImagem);
            fis.read(fileArray, 0, tam);
        } catch (Exception e) {
            System.err.println(e);
        }
        empresa.setLogoEmpresa(fileArray);

        AlterarLogoEmpresa alterarLogoEmpresa = new AlterarLogoEmpresa();
        String param = new Gson().toJson(empresa).replace("\"", "'");
        try {
            if (alterarLogoEmpresa.execute(param).get()) {
                getEmpresa().setLogoEmpresa(empresa.getLogoEmpresa());
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

    public boolean alteracaoEnderecoEmpresa(Endereco endereco) {

        AlterarEnderecoEmpresa alterarEnderecoEmpresa = new AlterarEnderecoEmpresa();
        String param = new Gson().toJson(getLatitudeLongituteEndereco(endereco)).replace("\"", "'");
        try {
            if (alterarEnderecoEmpresa.execute(param).get()) {
                getEmpresa().setEndereco(endereco);
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

    public boolean cadastrarContatoEmpresa(List<Contato> listaContatoParaCadastro) {
        if (listaContatoParaCadastro != null && listaContatoParaCadastro.size() > 0) {
            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(getEmpresa().getIdEmpresa());
            empresa.setListaContato(listaContatoParaCadastro);
            String param = new Gson().toJson(empresa).replace("\"", "'");
            CadastrarContatoEmpresa cadastrarContatoEmpresa = new CadastrarContatoEmpresa();

            try {
                List<Contato> listaContatoAtualizado = cadastrarContatoEmpresa.execute(param).get();
                if (listaContatoAtualizado != null && listaContatoAtualizado.size() > 0) {
                    getEmpresa().setListaContato(listaContatoAtualizado);
                    return true;
                } else {
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean removerContatoEmpresa(Contato contato) {
        contato.setIdEmpresa(getEmpresa().getIdEmpresa());
        String param = new Gson().toJson(contato).replace("\"", "'");
        RemoverContatoEmpresa removerContatoEmpresa = new RemoverContatoEmpresa();
        try {
            if (removerContatoEmpresa.execute(param).get()) {
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

    public boolean removerHorarioEmpresa(HorarioEmpresa horarioEmpresa) {
        horarioEmpresa.setIdEmpresa(getEmpresa().getIdEmpresa());
        String param = new Gson().toJson(horarioEmpresa).replace("\"", "'");
        RemoverHorarioEmpresa removerHorarioEmpresa = new RemoverHorarioEmpresa();
        try {
            if (removerHorarioEmpresa.execute(param).get()) {
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

    public PerfilEmpresaActivity getPerfilEmpresaActivity() {
        return perfilEmpresaActivity;
    }

    public void setPerfilEmpresaActivity(PerfilEmpresaActivity perfilEmpresaActivity) {
        this.perfilEmpresaActivity = perfilEmpresaActivity;
    }

    public FragmentPerfilEmpresaInformacoes getFragmentPerfilEmpresaInformacoes() {
        return fragmentPerfilEmpresaInformacoes;
    }

    public void setFragmentPerfilEmpresaInformacoes(FragmentPerfilEmpresaInformacoes fragmentPerfilEmpresaInformacoes) {
        this.fragmentPerfilEmpresaInformacoes = fragmentPerfilEmpresaInformacoes;
    }

    public boolean cadastrarHorarioEmpresa(HorarioEmpresa horarioEmpresa) {
        horarioEmpresa.setIdEmpresa(getEmpresa().getIdEmpresa());
        String param = new Gson().toJson(horarioEmpresa).replace("\"", "'");
        CadastrarHorarioEmpresa cadastrarHorarioEmpresa = new CadastrarHorarioEmpresa();
        try {

            List<HorarioEmpresa> listaHorarioEmpresa = cadastrarHorarioEmpresa.execute(param).get();
            if (listaHorarioEmpresa != null) {
                getEmpresa().setListaHorarioEmpresa(listaHorarioEmpresa);
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

    public boolean cadastrarFuncionarioEmpresa(List<Funcionario> listaDeFuncionarioParaAdicionar) {

        String param = new Gson().toJson(listaDeFuncionarioParaAdicionar).replace("\"", "'");
        CadastrarFuncionarioEmpresa cadastrarFuncionarioEmpresa = new CadastrarFuncionarioEmpresa();
        try {
            List<Funcionario> listaAtualizadaEmpresa = cadastrarFuncionarioEmpresa.execute(param).get();
            if (listaAtualizadaEmpresa != null && listaAtualizadaEmpresa.size() > 0) {
                getEmpresa().setListaFuncionarios(listaAtualizadaEmpresa);
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

    public boolean desativarFuncionarioEmpresa(Funcionario funcionario) {
        DesativarFuncionarioEmpresa desativarFuncionarioEmpresa = new DesativarFuncionarioEmpresa();

        try {
            if (desativarFuncionarioEmpresa.execute(funcionario.getIdFuncionario()).get()) {
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

    public List<GrupoServico> getListaGrupoServico() {
        if (listaGrupoServico == null) {
            listaGrupoServico = new ArrayList<>();
        }
        return listaGrupoServico;
    }

    public List<Servico> getListaServico() {
        return listaServico;
    }

    public boolean cadastrarServicoPrestado(ServicoPrestado servicoPrestado) {
        servicoPrestado.setIdEmpresa(getEmpresa().getIdEmpresa());
        String param = new Gson().toJson(servicoPrestado).replace("\"", "'");

        CadastrarServicoPrestadoEmpresa cadastrarServicoPrestadoEmpresa = new CadastrarServicoPrestadoEmpresa();
        try {
            if (cadastrarServicoPrestadoEmpresa.execute(param).get()) {

                boolean existe = false;
                for (GrupoServico grupoServico : getListaGrupoServicoPrestado()) {
                    if (grupoServico.getIdGrupoServico() == servicoPrestado.getServico().getGrupoServico().getIdGrupoServico()) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    getListaGrupoServicoPrestado().add(servicoPrestado.getServico().getGrupoServico());
                }
                getEmpresa().getListaServicoPrestado().add(servicoPrestado);
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

    public boolean cadastrarFotoGaleria(Galeria galeria) {
        galeria.setIdEmpresa(getEmpresa().getIdEmpresa());
        byte[] fotoGrupoServico = galeria.getGrupoServico().getFotoGrupoServico();
        galeria.getGrupoServico().setFotoGrupoServico(null);
        String param = new Gson().toJson(galeria).replace("\"", "'");

        CadastrarFotoGaleriaEmpresa cadastrarFotoGaleriaEmpresa = new CadastrarFotoGaleriaEmpresa();
        try {
            int id = cadastrarFotoGaleriaEmpresa.execute(param).get();
            if (id != 0) {

                boolean existe = false;
                if (getEmpresa().getListaGaleria() != null) {
                    for (int i = 0; i < getEmpresa().getListaGaleria().size(); i++) {
                        if (getEmpresa().getListaGaleria().get(i).getGrupoServico().getIdGrupoServico() == galeria.getGrupoServico().getIdGrupoServico()) {
                            galeria.getListaFotoGaleria().get(0).setIdFotoGaleria(id);
                            getEmpresa().getListaGaleria().get(i).getListaFotoGaleria().add(galeria.getListaFotoGaleria().get(0));
                            return true;
                        }
                    }
                } else {
                    getEmpresa().setListaGaleria(new ArrayList<Galeria>());
                    galeria.getListaFotoGaleria().get(0).setIdFotoGaleria(id);
                    galeria.getGrupoServico().setFotoGrupoServico(fotoGrupoServico);
                    getEmpresa().getListaGaleria().add(galeria);
                    return true;
                }

                if (!existe) {
                    galeria.getListaFotoGaleria().get(0).setIdFotoGaleria(id);
                    galeria.getGrupoServico().setFotoGrupoServico(fotoGrupoServico);
                    getEmpresa().getListaGaleria().add(galeria);
                }
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

    public boolean removerFotoGaleria(FotoGaleria fotoGaleria) {
        String param = new Gson().toJson(fotoGaleria).replace("\"", "'");
        RemoverFotoGaleriaEmpresa removerFotoGaleriaEmpresa = new RemoverFotoGaleriaEmpresa();
        try {
            if (removerFotoGaleriaEmpresa.execute(param).get()) {
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

    public void irParaAgendamentos(Context applicationContext) {
        Intent intent = new Intent(applicationContext, AgendamentosPerfilEmpresaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent);
    }

    public void setSupportMapFragment(SupportMapFragment supportMapFragment) {
        this.supportMapFragment = supportMapFragment;
    }

    public SupportMapFragment getSupportMapFragment() {
        return supportMapFragment;
    }

    public List<HorarioMarcado> getListaHorarioMarcado() {
        return listaHorarioMarcado;
    }

    public boolean recusarAgendamento(HorarioMarcado horarioMarcado) {
        String param = horarioMarcado.getIdhorarioMarcado() + "," + Constantes.STATUS_RECUSADO;
        AlterarStatusHorarioMarcado alterarStatusHorarioMarcado = new AlterarStatusHorarioMarcado();
        try {
            if (alterarStatusHorarioMarcado.execute(param).get()) {
                for (int i = 0; i < getListaHorarioMarcado().size(); i++) {
                    if (getListaHorarioMarcado().get(i).getIdhorarioMarcado() == horarioMarcado.getIdhorarioMarcado()) {
                        getListaHorarioMarcado().get(i).setStatus(Constantes.STATUS_RECUSADO);
                        return true;
                    }
                }
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

    public boolean confirmarAgendamento(HorarioMarcado horarioMarcado) {
        String param = horarioMarcado.getIdhorarioMarcado() + "," + Constantes.STATUS_CONFIRMADO;
        AlterarStatusHorarioMarcado alterarStatusHorarioMarcado = new AlterarStatusHorarioMarcado();
        try {
            if (alterarStatusHorarioMarcado.execute(param).get()) {
                for (int i = 0; i < getListaHorarioMarcado().size(); i++) {
                    if (getListaHorarioMarcado().get(i).getIdhorarioMarcado() == horarioMarcado.getIdhorarioMarcado()) {
                        getListaHorarioMarcado().get(i).setStatus(Constantes.STATUS_CONFIRMADO);
                        return true;
                    }
                }
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
        instance = null;
    }

    public boolean confirmarServicoRealizado(HorarioMarcado horarioMarcado) {
        String param = horarioMarcado.getIdhorarioMarcado() + "," + Constantes.STATUS_SERVICO_REALIZADO;
        AlterarStatusHorarioMarcado alterarStatusHorarioMarcado = new AlterarStatusHorarioMarcado();

        try {
            if (alterarStatusHorarioMarcado.execute(param).get()) {
                for (int i = 0; i < getListaHorarioMarcado().size(); i++) {
                    if (getListaHorarioMarcado().get(i).getIdhorarioMarcado() == horarioMarcado.getIdhorarioMarcado()) {
                        getListaHorarioMarcado().get(i).setStatus(Constantes.STATUS_SERVICO_REALIZADO);
                        return true;
                    }
                }
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

    public void setListaServico(List<Servico> listaServico) {
        this.listaServico = listaServico;
    }

    public void setListaGrupoServicoPrestado(List<GrupoServico> listaGrupoServicoPrestado) {
        this.listaGrupoServicoPrestado = listaGrupoServicoPrestado;
    }

    public void setListaGrupoServico(List<GrupoServico> listaGrupoServico) {
        this.listaGrupoServico = listaGrupoServico;
    }

    public List<byte[]> getListaByteFotos() {
        return listaByteFotos;
    }

    public void setListaComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    public boolean isListadoFotoGaleria() {
        return listadoFotoGaleria;
    }

    public boolean isListadoServicoPrestado() {
        return listadoServicoPrestado;
    }

    public boolean getListadoProfissionaisPorServico() {
        return listadoProfissionaisPorServico;
    }

    public boolean isApresentandoFotosGaleria() {
        return apresentandoFotosGaleria;
    }

    public void setParamDeListagemFuncionarioServicoPrestado(ServicoPrestado paramDeListagemFuncionarioServicoPrestado) {
        this.paramDeListagemFuncionarioServicoPrestado = paramDeListagemFuncionarioServicoPrestado;
    }

    public ServicoPrestado getParamDeListagemFuncionarioServicoPrestado() {
        return paramDeListagemFuncionarioServicoPrestado;
    }

    public boolean desassociarFuncionarioServico(ServicoPrestado paramDeListagemFuncionarioServicoPrestado, Funcionario funcionario) {
        String param = paramDeListagemFuncionarioServicoPrestado.getIdServicoPrestado() + "," + funcionario.getIdFuncionario();

        DesassociarFuncionarioServicoPrestado desassociarFuncionarioServicoPrestado = new DesassociarFuncionarioServicoPrestado();

        try {
            if (desassociarFuncionarioServicoPrestado.execute(param).get()) {
                return true;
            } else
                return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setListaHorarioMarcado(List<HorarioMarcado> listaHorarioMarcado) {
        this.listaHorarioMarcado = listaHorarioMarcado;
    }
}
