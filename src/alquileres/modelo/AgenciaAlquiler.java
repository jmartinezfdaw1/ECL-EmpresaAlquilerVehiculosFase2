package alquileres.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 */
public class AgenciaAlquiler {
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos
	static final String FICHERO_ENTRADA = "flota.csv";
	static final String FICHERO_SALIDA = "marcasmodelos.txt";

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.nombre = nombre;
		this.flota = new ArrayList<>();
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo v) {
		if(!flota.contains(v)) {
			flota.add(v);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo
	 * correspondiente
	 * 
	 * Formato de la línea:
	 * C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después
	 * de cada dato
	 */
	private Vehiculo obtenerVehiculo(String linea) throws NumberFormatException{
        String[] separado = linea.split(",");
		String[] variables = new String[separado.length];
		for (int i = 0; i < separado.length; i++) {
			variables[i] = separado[i].trim();
		}
		if (variables[0].equalsIgnoreCase("F")) {
			return new Furgoneta(variables[1], variables[2], variables[3],
					Double.parseDouble(variables[4]), Double.parseDouble(variables[5]));
		}else {
			return new Coche(variables[1], variables[2], variables[3],
					Double.parseDouble(variables[4]), Integer.parseInt(variables[5]));
		}
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos
	 * de la flota de vehículos  
	 */
	public int cargarFlota() {
		int errores = 0;
		String linea = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(FICHERO_ENTRADA)));
			while ((linea = br.readLine()) != null) {
				try {
					Vehiculo vehiculo = obtenerVehiculo(linea);
					this.addVehiculo(vehiculo);
				} catch (Exception e) {
					errores++;
				}
			}
		} catch (Exception e) {
			System.out.println(linea);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.out.println("Error al cerrar el fichero");
			} catch (NullPointerException e) {
				System.out.println("La variable del archivo contiene el valor null");
			}
		}
		return errores;

	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vehiculos en alquiler de la agencia " + nombre + "\n");
		sb.append("Total vehiculos: " + flota.size() + "\n");
		for(Vehiculo vehiculo: flota) {
			sb.append(vehiculo.toString() + "\n-----------------------------------------------------");
		}
		return sb.toString();
	}

	/**
	 * Busca todos los coches de la agencia
	 * Devuelve un String con esta información y lo que
	 * costaría alquilar cada coche el nº de días indicado * 
	 *  
	 */
	public String buscarCoches(int dias) {

		StringBuilder sb = new StringBuilder();
		for(Vehiculo vehiculo: flota) {
			if(vehiculo instanceof Coche) {
				sb.append(vehiculo.toString());
				sb.append("Coste alquiler " + dias + " dias: " + vehiculo.calcularPrecioAlquiler(dias) + "�");
				sb.append("\n---------------");
			}
		}
		return sb.toString();

	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * 
	 */
	public List<Coche> cochesOrdenadosMatricula() {
		List<Coche> coches = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while(it.hasNext()) {
			Vehiculo vehiculo = it.next();
			if(vehiculo instanceof Coche && (((Coche) vehiculo)).getnPlazas() > 4){
				coches.add((Coche) vehiculo);
			}
		}
		Collections.sort(coches);
		return coches;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de
	 * mayor a menor volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {
		List<Furgoneta> furgonetas = new ArrayList<>();
		for(Vehiculo vehiculo: flota) {
			if(vehiculo instanceof Furgoneta) {
				furgonetas.add((Furgoneta) vehiculo);
			}
		}
		Collections.sort(furgonetas, new Comparator<Furgoneta>() {
			public int compare(Furgoneta f1, Furgoneta f2) {
				return f2.compareTo(f1);
			}});
		return furgonetas;
	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden) 
	 * de los modelos en cada marca como valor asociado
	 */
	public Map<String, Set<String>> marcasConModelos() {
		Map<String, Set<String>> marcas = new TreeMap<>();
		for (Vehiculo v: flota){
			if (marcas.get(v.getMarca()) == null) {
				Set<String> modelos = new TreeSet<>();
				modelos.add(v.getModelo());
				marcas.put(v.getMarca(), modelos);
			}else {
				marcas.get(v.getMarca()).add(v.getModelo());
			}
		}
		return marcas;
	}
	public void guardarMarcasModelos() throws IOException, NullPointerException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(FICHERO_SALIDA))));
		Map<String, Set<String>> marcas = this.marcasConModelos();
		for (Map.Entry<String, Set<String>> marca : marcas.entrySet()) {
			pw.println("Marca: " + marca.getKey());
			pw.printf("%8sModelos: %s \n\n", " ", marca.getValue());
		}
		pw.close();
	}

}
