package faculdade.br.picuma.model;

public class AreaAtuacao {

    private int idAreaAtuacao;
    private String areaAtuacao;
    private byte[] fotoAreaAtuacao;

    public int getIdAreaAtuacao() {
        return idAreaAtuacao;
    }

    public void setIdAreaAtuacao(int idAreaAtuacao) {
        this.idAreaAtuacao = idAreaAtuacao;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public byte[] getFotoAreaAtuacao() {
        return fotoAreaAtuacao;
    }

    public void setFotoAreaAtuacao(byte[] fotoAreaAtuacao) {
        this.fotoAreaAtuacao = fotoAreaAtuacao;
    }

    @Override
    public String toString() {
        return areaAtuacao;
    }
}
