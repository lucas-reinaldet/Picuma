package faculdade.br.picuma.model;

import java.util.Calendar;
import java.util.List;

public class Empresa {
    private Integer idEmpresa;
    private String nomeFantasia;
    private String cnpjEmpresa;
    private String cpfEmpresa;
    private String descricaoEmpresa;
    private byte[] logoEmpresa;
    private boolean empresaAtivada;
    private Calendar desativadaEm;
    private String publicoAlvo;
    private AreaAtuacao areaAtuacao;
    private Endereco endereco;
    private List<HorarioEmpresa> listaHorarioEmpresa;
    private List<Contato> listaContato;
    private List<ServicoPrestado> listaServicoPrestado;
    private List<Funcionario> listaFuncionarios;
    private List<Galeria> listaGaleria;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getCpfEmpresa() {
        return cpfEmpresa;
    }

    public void setCpfEmpresa(String cpfEmpresa) {
        this.cpfEmpresa = cpfEmpresa;
    }

    public String getDescricaoEmpresa() {
        return descricaoEmpresa;
    }

    public void setDescricaoEmpresa(String descricaoEmpresa) {
        this.descricaoEmpresa = descricaoEmpresa;
    }

    public byte[] getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(byte[] logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public boolean isEmpresaAtivada() {
        return empresaAtivada;
    }

    public void setEmpresaAtivada(boolean empresaAtivada) {
        this.empresaAtivada = empresaAtivada;
    }

    public Calendar getDesativadaEm() {
        return desativadaEm;
    }

    public void setDesativadaEm(Calendar desativadaEm) {
        this.desativadaEm = desativadaEm;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public AreaAtuacao getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(AreaAtuacao areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<HorarioEmpresa> getListaHorarioEmpresa() {
        return listaHorarioEmpresa;
    }

    public void setListaHorarioEmpresa(List<HorarioEmpresa> listaHorarioEmpresa) {
        this.listaHorarioEmpresa = listaHorarioEmpresa;
    }

    public List<Contato> getListaContato() {
        return listaContato;
    }

    public void setListaContato(List<Contato> listaContato) {
        this.listaContato = listaContato;
    }

    public List<ServicoPrestado> getListaServicoPrestado() {
        return listaServicoPrestado;
    }

    public void setListaServicoPrestado(List<ServicoPrestado> listaServicoPrestado) {
        this.listaServicoPrestado = listaServicoPrestado;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    public List<Galeria> getListaGaleria() {
        return listaGaleria;
    }

    public void setListaGaleria(List<Galeria> listaGaleria) {
        this.listaGaleria = listaGaleria;
    }
}
