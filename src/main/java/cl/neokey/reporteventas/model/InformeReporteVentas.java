package cl.neokey.reporteventas.model;

import java.math.BigDecimal;

public class InformeReporteVentas {
	private String a_dealer;
	private int n_solicitud;
	private int n_aprobadas;
	private BigDecimal n_porc_aprobadas;
	private int n_cursadas;
	private BigDecimal n_porc_cursadas;

	public String getA_dealer() {
		return a_dealer;
	}

	public void setA_dealer(String a_dealer) {
		this.a_dealer = a_dealer;
	}

	public int getN_solicitud() {
		return n_solicitud;
	}

	public void setN_solicitud(int n_solicitud) {
		this.n_solicitud = n_solicitud;
	}

	public int getN_aprobadas() {
		return n_aprobadas;
	}

	public void setN_aprobadas(int n_aprobadas) {
		this.n_aprobadas = n_aprobadas;
	}

	public BigDecimal getN_porc_aprobadas() {
		return n_porc_aprobadas;
	}

	public void setN_porc_aprobadas(BigDecimal n_porc_aprobadas) {
		this.n_porc_aprobadas = n_porc_aprobadas;
	}

	public int getN_cursadas() {
		return n_cursadas;
	}

	public void setN_cursadas(int n_cursadas) {
		this.n_cursadas = n_cursadas;
	}

	public BigDecimal getN_porc_cursadas() {
		return n_porc_cursadas;
	}

	public void setN_porc_cursadas(BigDecimal n_porc_cursadas) {
		this.n_porc_cursadas = n_porc_cursadas;
	}

	@Override
	public String toString() {
		return "InformeReporteVentas [a_dealer=" + a_dealer + ", n_solicitud=" + n_solicitud + ", n_aprobadas="
				+ n_aprobadas + ", n_porc_aprobadas=" + n_porc_aprobadas + ", n_cursadas=" + n_cursadas
				+ ", n_porc_cursadas=" + n_porc_cursadas + "]";
	}

}
