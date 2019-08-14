package cl.neokey.reporteventas.model;

import java.util.List;

import cl.neokey.reporteventas.model.InformeReporteVentas;

public class InformeReporteVentasDTO {
	private List<InformeReporteVentas> all;
	private List<InformeReporteVentas> mundoCredito;
	private List<InformeReporteVentas> amicar;

	public List<InformeReporteVentas> getAll() {
		return all;
	}

	public void setAll(List<InformeReporteVentas> all) {
		this.all = all;
	}

	public List<InformeReporteVentas> getMundoCredito() {
		return mundoCredito;
	}

	public void setMundoCredito(List<InformeReporteVentas> mundoCredito) {
		this.mundoCredito = mundoCredito;
	}

	public List<InformeReporteVentas> getAmicar() {
		return amicar;
	}

	public void setAmicar(List<InformeReporteVentas> amicar) {
		this.amicar = amicar;
	}

	@Override
	public String toString() {
		return "InformeReporteVentasDTO [all=" + all + ", mundoCredito=" + mundoCredito + ", amicar=" + amicar + "]";
	}

}
