package faculdade.br.picuma.model;

public class Servico {

    private int idServico;
    private String servico;
    private GrupoServico grupoServico;

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public GrupoServico getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(GrupoServico grupoServico) {
        this.grupoServico = grupoServico;
    }

    @Override
    public String toString() {
        return servico;
    }
}
