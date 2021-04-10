package faculdade.br.picuma.model;

import java.util.Calendar;
import java.util.List;

public class Cliente {

    private int idCliente;
    private String nomeCliente;
    private Calendar nascimentoCliente;
    private byte[] fotoCliente;
    private boolean clienteAtivo;
    private Calendar desativadoEm;
    private String cpfCliente;
    private String genero;
    private List<Contato> listaContato;
    private List<Favoritos> listaFavoritos;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Calendar getNascimentoCliente() {
        return nascimentoCliente;
    }

    public void setNascimentoCliente(Calendar nascimentoCliente) {
        this.nascimentoCliente = nascimentoCliente;
    }

    public byte[] getFotoCliente() {
        return fotoCliente;
    }

    public void setFotoCliente(byte[] fotoCliente) {
        this.fotoCliente = fotoCliente;
    }

    public boolean isClienteAtivo() {
        return clienteAtivo;
    }

    public void setClienteAtivo(boolean clienteAtivo) {
        this.clienteAtivo = clienteAtivo;
    }

    public Calendar getDesativadoEm() {
        return desativadoEm;
    }

    public void setDesativadoEm(Calendar desativadoEm) {
        this.desativadoEm = desativadoEm;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Contato> getListaContato() {
        return listaContato;
    }

    public void setListaContato(List<Contato> listaContato) {
        this.listaContato = listaContato;
    }

    public List<Favoritos> getListaFavoritos() {
        return listaFavoritos;
    }

    public void setListaFavoritos(List<Favoritos> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }
}
