package faculdade.br.picuma.model;

import java.util.Calendar;

public class HorarioEmpresa {

    private int idHorarioEmpresa;
    private Calendar inicioExpediente;
    private Calendar inicioIntervalo;
    private Calendar fimIntervalo;
    private Calendar fimExpediente;
    private DiaSemana diaSemana;
    private Integer idEmpresa;

    public int getIdHorarioEmpresa() {
        return idHorarioEmpresa;
    }

    public void setIdHorarioEmpresa(int idHorarioEmpresa) {
        this.idHorarioEmpresa = idHorarioEmpresa;
    }

    public Calendar getInicioExpediente() {
        return inicioExpediente;
    }

    public void setInicioExpediente(Calendar inicioExpediente) {
        this.inicioExpediente = inicioExpediente;
    }

    public Calendar getInicioIntervalo() {
        return inicioIntervalo;
    }

    public void setInicioIntervalo(Calendar inicioIntervalo) {
        this.inicioIntervalo = inicioIntervalo;
    }

    public Calendar getFimIntervalo() {
        return fimIntervalo;
    }

    public void setFimIntervalo(Calendar fimIntervalo) {
        this.fimIntervalo = fimIntervalo;
    }

    public Calendar getFimExpediente() {
        return fimExpediente;
    }

    public void setFimExpediente(Calendar fimExpediente) {
        this.fimExpediente = fimExpediente;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
