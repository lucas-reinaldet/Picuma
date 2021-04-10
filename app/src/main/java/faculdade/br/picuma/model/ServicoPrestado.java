package faculdade.br.picuma.model;

import java.util.List;

public class ServicoPrestado {

    private int idServicoPrestado;
    private String valorServico;
    private int tempoAproxServico;
    private String informacoesServico;
    private int idEmpresa;
    private Servico servico;
    private List<Integer> listaIdFuncionario;

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdServicoPrestado() {
        return idServicoPrestado;
    }

    public void setIdServicoPrestado(int idServicoPrestado) {
        this.idServicoPrestado = idServicoPrestado;
    }

    public String getValorServico() {
        return valorServico;
    }

    public void setValorServico(String string) {
        this.valorServico = string;
    }

    public int getTempoAproxServico() {
        return tempoAproxServico;
    }

    public void setTempoAproxServico(int tempoAproxServico) {
        this.tempoAproxServico = tempoAproxServico;
    }

    public String getInformacoesServico() {
        return informacoesServico;
    }

    public void setInformacoesServico(String informacoesServico) {
        this.informacoesServico = informacoesServico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Integer> getListaIdFuncionario() {
        return listaIdFuncionario;
    }

    public void setListaIdFuncionario(List<Integer> listaIdFuncionario) {
        this.listaIdFuncionario = listaIdFuncionario;
    }
}
