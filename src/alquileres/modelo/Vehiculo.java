package alquileres.modelo;

/**
 * Representa a un vehículo en alquiler
 * De esta clase no se crearán instancias
 * 
 * De un vehículo se conoce su matrícula, marca, modelo y el precio a pagar por
 * día de alquiler
 * 
 * Para todo vehículo se puede calcular su coste de alquiler que depende del nº
 * de días que se alquile (llamaremos a esta operación calcularPrecioAlquiler() )
 * 
 * Dos vehículos pueden compararse por su matrícula (es su orden natural)
 * 
 * Dos vehículos son iguales si además de pertenecer a la misma clase tienen la
 * misma matrícula
 * 
 */
public abstract class Vehiculo implements Comparable<Vehiculo> {
	private String matricula;
	private String marca;
	private String modelo;
	private double precioDia;

	/**
	 * Constructor
	 */
	public Vehiculo(String matricula, String marca, String modelo,
	        double precioDia) {
		this.matricula = matricula.toUpperCase();
		this.marca = marca.toUpperCase();
		this.modelo = modelo.toUpperCase();
		this.precioDia = precioDia;

	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getPrecioDia() {
		return precioDia;
	}

	public void setPrecioDia(double precioDia) {
		this.precioDia = precioDia;
	}
	public abstract double calcularPrecioAlquiler(int dias);

	/**
	 * Redefinición de hashCode()
	 * 
	 */
	@Override
	public int hashCode() {
		return matricula.hashCode() * 13;
	}
	
	public boolean equals(Object obj)
	 {
	if (obj == null)
	{
	return false;
	}
	if (obj == this)
	{
	return true;
	}
	if (this.getClass() != obj.getClass())
	{
	return false;
	}
	Vehiculo v = (Vehiculo) obj;
	return v.getMatricula().equals(this.matricula);
	 }
	
	public int compareTo(Vehiculo otro) {
		return this.matricula.compareToIgnoreCase(otro.getMatricula());
	}
	
	public String toString() {
		return "Matricula: " + matricula + "  |  Marca: " + marca + "  |  Modelo: " + modelo +
				"\nPrecio dia alquiler: " + precioDia + "�";
				
	}


}