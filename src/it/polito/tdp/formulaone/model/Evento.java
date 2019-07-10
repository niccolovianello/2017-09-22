package it.polito.tdp.formulaone.model;

public class Evento {
	
	public enum TipoEvento{
		GAREGGIA,
		TAGLIA_TRAGUARDO,
		PRIMO,
		PITSTOP
	}
	
	private TipoEvento tipo;
	private Race gara;
	private Driver pilota;
	
	public Evento(TipoEvento tipo, Race gara, Driver pilota) {
		this.tipo = tipo;
		this.gara = gara;
		this.pilota = pilota;
	}
	
	public TipoEvento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	public Race getGara() {
		return gara;
	}
	public void setGara(Race gara) {
		this.gara = gara;
	}
	public Driver getPilota() {
		return pilota;
	}
	public void setPilota(Driver pilota) {
		this.pilota = pilota;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gara == null) ? 0 : gara.hashCode());
		result = prime * result + ((pilota == null) ? 0 : pilota.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (gara == null) {
			if (other.gara != null)
				return false;
		} else if (!gara.equals(other.gara))
			return false;
		if (pilota == null) {
			if (other.pilota != null)
				return false;
		} else if (!pilota.equals(other.pilota))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	
	
	

}
