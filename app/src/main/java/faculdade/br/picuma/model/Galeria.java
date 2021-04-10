package faculdade.br.picuma.model;

import java.util.List;

public class Galeria {

    private int idGaleria;
    private int idEmpresa;
    private GrupoServico grupoServico;
    private List<FotoGaleria> listaFotoGaleria;

    public int getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(int idGaleria) {
        this.idGaleria = idGaleria;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public GrupoServico getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(GrupoServico grupoServico) {
        this.grupoServico = grupoServico;
    }

    public List<FotoGaleria> getListaFotoGaleria() {
        return listaFotoGaleria;
    }

    public void setListaFotoGaleria(List<FotoGaleria> listaFotoGaleria) {
        this.listaFotoGaleria = listaFotoGaleria;
    }
}
