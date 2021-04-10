package faculdade.br.picuma.model;

import java.util.Calendar;

public class HorarioMarcado {

    private int idhorarioMarcado;
    private Calendar horarioInicio;
    private Calendar horarioFim;
    private Calendar dataMarcada;
    private String status;
    private boolean desativado;
    private Cliente cliente;
    private Empresa empresa;
    private ServicoPrestado servicoPrestado;
    private Funcionario funcionario;

    public int getIdhorarioMarcado() {
        return idhorarioMarcado;
    }

    public void setIdhorarioMarcado(int idhorarioMarcado) {
        this.idhorarioMarcado = idhorarioMarcado;
    }

    public Calendar getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Calendar horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Calendar getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Calendar horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Calendar getDataMarcada() {
        return dataMarcada;
    }

    public void setDataMarcada(Calendar dataMarcada) {
        this.dataMarcada = dataMarcada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDesativado() {
        return desativado;
    }

    public void setDesativado(boolean desativado) {
        this.desativado = desativado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ServicoPrestado getServicoPrestado() {
        return servicoPrestado;
    }

    public void setServicoPrestado(ServicoPrestado servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
