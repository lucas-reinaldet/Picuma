package faculdade.br.picuma.model;

public class FotoGaleria {

    private int idFotoGaleria;
    private byte[] fotoAntesGaleria;
    private byte[] fotoDepoisGaleria;
    private int idGaleria;

    public int getIdFotoGaleria() {
        return idFotoGaleria;
    }

    public void setIdFotoGaleria(int idFotoGaleria) {
        this.idFotoGaleria = idFotoGaleria;
    }

    public byte[] getFotoAntesGaleria() {
        return fotoAntesGaleria;
    }

    public void setFotoAntesGaleria(byte[] fotoAntesGaleria) {
        this.fotoAntesGaleria = fotoAntesGaleria;
    }

    public byte[] getFotoDepoisGaleria() {
        return fotoDepoisGaleria;
    }

    public void setFotoDepoisGaleria(byte[] fotoDepoisGaleria) {
        this.fotoDepoisGaleria = fotoDepoisGaleria;
    }
    public int getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(int idGaleria) {
        this.idGaleria = idGaleria;
    }
}
