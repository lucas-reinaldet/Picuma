package faculdade.br.picuma.model;

public class Funcionario {

    private int idFuncionario;
    private String nomeFuncionario;
    private byte[] fotoFuncionario;
    private int idEmpresa;

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public byte[] getFotoFuncionario() {
        return fotoFuncionario;
    }

    public void setFotoFuncionario(byte[] fotoFuncionario) {
        this.fotoFuncionario = fotoFuncionario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return nomeFuncionario;
    }
}
