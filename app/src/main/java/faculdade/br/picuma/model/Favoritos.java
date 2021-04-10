package faculdade.br.picuma.model;

public class Favoritos {


	private int idCliente;
	private Integer idEmpresa;
	private String nomeFantasia;
	private byte[] logoEmpresa;
	private byte[] fotoAreaAtuacao;

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

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

	public byte[] getLogoEmpresa() {
		return logoEmpresa;
	}

	public void setLogoEmpresa(byte[] logoEmpresa) {
		this.logoEmpresa = logoEmpresa;
	}

	public byte[] getFotoAreaAtuacao() {
		return fotoAreaAtuacao;
	}

	public void setFotoAreaAtuacao(byte[] fotoAreaAtuacao) {
		this.fotoAreaAtuacao = fotoAreaAtuacao;
	}

}
