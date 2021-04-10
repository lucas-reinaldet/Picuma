package faculdade.br.picuma.control;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.model.AreaAtuacao;
import faculdade.br.picuma.model.Cliente;
import faculdade.br.picuma.model.Comentario;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Favoritos;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.model.HorarioMarcado;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.view.PerfilClienteActivity;
import faculdade.br.picuma.webService.CadastrarComentario;
import faculdade.br.picuma.webService.CadastrarFavorito;
import faculdade.br.picuma.webService.CadastroUsuario;
import faculdade.br.picuma.webService.ExcluirFavorito;
import faculdade.br.picuma.webService.SolicitarListaAreaAtuacao;
import faculdade.br.picuma.webService.SolicitarListaEmpresa;
import faculdade.br.picuma.webService.SolicitarListaFavoritosCliente;
import faculdade.br.picuma.webService.SolicitarListaGrupoServico;

public class ClienteControl {

    private static ClienteControl instance;
    private List<AreaAtuacao> listaAreaAtuacao;
    private List<Empresa> listaEmpresa;
    private List<GrupoServico> listaGrupoServico;
    private AreaAtuacao paramParaListagemDeGrupoServicoPorAreaAtuacao;
    private GrupoServico paramParaListagemDeEmpresaPorCategoria;
    private boolean listadoEmpresaPorCategoria;
    private boolean listadoGrupoServico;
    private Cliente cliente;
    private Login login;
    private LatLng latLngCliente;
    private List<HorarioMarcado> listaHorarioMarcado;
    private VisualizarEmpresaControl visualizarEmpresaControl;

    public GrupoServico getParamParaListagemDeEmpresaPorCategoria() {
        return paramParaListagemDeEmpresaPorCategoria;
    }

    public void setParamParaListagemDeEmpresaPorCategoria(GrupoServico paramParaListagemDeEmpresaPorCategoria) {
        this.paramParaListagemDeEmpresaPorCategoria = paramParaListagemDeEmpresaPorCategoria;
    }

    public void setListadoEmpresaPorCategoria(boolean listadoEmpresaPorCategoria) {
        this.listadoEmpresaPorCategoria = listadoEmpresaPorCategoria;
    }

    public static ClienteControl getInstance() {
        if (instance == null) {
            instance = new ClienteControl();
        }
        return instance;
    }

    public void cadastrarCliente(Context applicationContext, Login dados) {

        Gson gson = new Gson();
        String jsonParacadastro = gson.toJson(dados);
        CadastroUsuario cadastroUsuario = new CadastroUsuario();

        String[] valores = new String[]{
                Constantes.PATH_CADASTRO_CLIENTE,
                jsonParacadastro.replace("\"", "'")
        };
        try {
            String result = cadastroUsuario.execute(valores).get();
            if (result != null && !result.isEmpty()) {
                Login login = new Gson().fromJson(result, Login.class);
                dadosParaApresentacao(applicationContext, login);
            } else {
                Toast.makeText(applicationContext, Constantes.M_FALHA_AO_CADASTRAR, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void dadosParaApresentacao(Context applicationContext, Login dadosCliente) {

        this.cliente = dadosCliente.getCliente();
        this.login = new Login();
        login.setUsuario(dadosCliente.getUsuario());
        login.setSenha(dadosCliente.getSenha());
        login.setLoginGoogle(dadosCliente.getLoginGoogle());

        solicitarDados();
        Intent intent = new Intent(applicationContext, PerfilClienteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent);
    }

    private void solicitarDados() {
        solicitarAreaAtuacaoWebService();
        solicitarCategoriaWebService();
        solicitarListaEmpresaWebService();

        new Thread(new Runnable() {
            @Override
            public void run() {
                solicitarFavoritos();
            }
        }).start();
    }

    private void solicitarFavoritos() {
        SolicitarListaFavoritosCliente solicitarListaFavoritosCliente = new SolicitarListaFavoritosCliente();
        try {
            getCliente().setListaFavoritos(solicitarListaFavoritosCliente.execute(getCliente().getIdCliente()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void solicitarAreaAtuacaoWebService() {

        SolicitarListaAreaAtuacao solicitarListaAreaAtuacao = new SolicitarListaAreaAtuacao();

        try {
            listaAreaAtuacao = solicitarListaAreaAtuacao.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void solicitarCategoriaWebService() {

        SolicitarListaGrupoServico solicitarListaGrupoServico = new SolicitarListaGrupoServico();

        try {
            listaGrupoServico = solicitarListaGrupoServico.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void solicitarListaEmpresaWebService() {

        SolicitarListaEmpresa solicitarListaEmpresa = new SolicitarListaEmpresa();

        try {
            listaEmpresa = solicitarListaEmpresa.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public LatLng getLatLngCliente() {
        return latLngCliente;
    }

    public List<GrupoServico> getListaGrupoServico() {
        return listaGrupoServico;
    }

    public boolean desfavoritarEmpresa(Favoritos favoritos) {
        String param = getCliente().getIdCliente() + "," + favoritos.getIdEmpresa();
        ExcluirFavorito excluirFavorito = new ExcluirFavorito();

        try {
            if (excluirFavorito.execute(param).get()) {
                for (int i = 0; i < getCliente().getListaFavoritos().size(); i++) {
                    if (favoritos.getIdEmpresa() == getCliente().getListaFavoritos().get(i).getIdEmpresa()) {
                        getCliente().getListaFavoritos().remove(i);
                    }
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

    public boolean adicionarEmpresaAosFavoritos(Favoritos favoritos) {

        String param = getCliente().getIdCliente() + "," + favoritos.getIdEmpresa();
        CadastrarFavorito cadastrarFavorito = new CadastrarFavorito();

        try {
            if (cadastrarFavorito.execute(param).get()) {
                getCliente().getListaFavoritos().add(favoritos);
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

    public void visualizarPerfilEmpresa(Context context, Empresa empresa) {
        getVisualizarEmpresaControl().destroyInstance();
        getVisualizarEmpresaControl().prepararDadosParaVisualizar(empresa, context, getCliente());
    }

    public List<AreaAtuacao> getListaAreaAtuacao() {
        return listaAreaAtuacao;
    }

    public void setListadoGrupoServico(boolean listadoGrupoServico) {
        this.listadoGrupoServico = listadoGrupoServico;
    }

    public void setParamParaListagemDeGrupoServicoPorAreaAtuacao(AreaAtuacao paramParaListagemDeGrupoServicoPorAreaAtuacao) {
        this.paramParaListagemDeGrupoServicoPorAreaAtuacao = paramParaListagemDeGrupoServicoPorAreaAtuacao;
    }

    public boolean isListadoGrupoServico() {
        return listadoGrupoServico;
    }

    public AreaAtuacao getParamParaListagemDeGrupoServicoPorAreaAtuacao() {
        return paramParaListagemDeGrupoServicoPorAreaAtuacao;
    }

    public boolean isListadoEmpresaPorCategoria() {
        return listadoEmpresaPorCategoria;
    }

    public void setLatLngCliente(LatLng latLngCliente) {
        this.latLngCliente = latLngCliente;
    }

    public List<HorarioMarcado> getListaHorarioMarcado() {
        return listaHorarioMarcado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void destroyCliente() {
        instance = null;
    }

    public void setListaHorarioMarcado(List<HorarioMarcado> listaHorarioMarcado) {
        this.listaHorarioMarcado = listaHorarioMarcado;
    }

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    public boolean cadastrarComentario(Comentario comentario) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(getCliente().getIdCliente());
        comentario.setCliente(cliente);
        String paran = new Gson().toJson(comentario);
        CadastrarComentario cadastrarComentario = new CadastrarComentario();
        try {
            if (cadastrarComentario.execute(paran).get()) {
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
}

