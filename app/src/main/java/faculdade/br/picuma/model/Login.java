package faculdade.br.picuma.model;

/**
 * Created by lucas on 17/04/2018.
 */

public class Login {

    private int idLogin;
    private String usuario;
    private String senha;
    private String loginGoogle;
    private Cliente cliente;
    private Empresa empresa;
    private boolean encontrado;


    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLoginGoogle() {
        return loginGoogle;
    }

    public void setLoginGoogle(String loginGoogle) {
        this.loginGoogle = loginGoogle;
    }

    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setNomeCliente("XXX");
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }
}
