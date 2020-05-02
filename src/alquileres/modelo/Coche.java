package alquileres.modelo;

/**
 * Un coche es un vehículo que añade un nº de plazas
 * 
 * El coste final de alquiler depende no solo del nº de días de alquiler 
 * sino también del nº de plazas. Si el nº de plazas es > 4 se añaden 5€ más por día
 */
public class Coche extends Vehiculo{
	private int nPlazas;
	
	public Coche(String matricula, String marca, String modelo,
	        double precioDia, int nPlazas) {
		super(matricula, marca, modelo, precioDia);
		this.nPlazas = nPlazas;
	}

	public int getnPlazas() {
		return nPlazas;
	}

	public void setnPlazas(int nPlazas) {
		this.nPlazas = nPlazas;
	}
	
	public double calcularPrecioAlquiler(int dias) {
		if(nPlazas > 4) {
			super.setPrecioDia(super.getPrecioDia() + 5);
		}
		return super.getPrecioDia() * dias;
	}
	
	public String toString() {
		return "\nCOCHE\n" + super.toString() + "  |  Plazas: " + nPlazas + "\n";
	}
}
