package faculdade.br.picuma.model;

import java.util.Calendar;

public class Comentario {

    private int idComentario;
    private Calendar dataComentario;
    private String comentario;
    private boolean comentarioAtivado;
    private Cliente cliente;
    private int idEmpresa;

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public Calendar getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Calendar dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isComentarioAtivado() {
        return comentarioAtivado;
    }

    public void setComentarioAtivado(boolean comentarioAtivado) {
        this.comentarioAtivado = comentarioAtivado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
