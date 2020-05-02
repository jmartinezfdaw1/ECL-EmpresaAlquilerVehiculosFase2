package alquileres.modelo;

/**
 * Una furgoneta es un vehículo que añade la característica del volumen de carga
 * (valor de tipo double)
 * 
 * El coste de alquiler de una furgoneta no solo depende del nº de días de
 * alquiler
 * Tendrá un incremento que dependerá de su volumen de carga: hasta 5 m3
 * exclusive ( metros cúbicos) de volumen el incremento sobre el precio
 * final será de 10€, entre 5 y 10 (inclusive) el incremento sobre el precio
 * final será de 15€, si volumen > 10 el incremento sobre el precio final será de
 * 25€
 * 
 */
public class Furgoneta extends Vehiculo{
        private double volumenCarga;
        
        public Furgoneta(String matricula, String marca, String modelo,
                double precioDia, double volumenCarga) {
            super(matricula, marca, modelo, precioDia);
            this.volumenCarga = volumenCarga;
        }
        
        public double getVolumenCarga() {
            return volumenCarga;
        }

        public void setVolumenCarga(double volumenCarga) {
            this.volumenCarga = volumenCarga;
        }

        public double calcularPrecioAlquiler(int dias) {
            double precio = super.getPrecioDia() * dias;
            if(volumenCarga < 5) {
                precio += 10;
            }
            else if(volumenCarga > 10) {
                precio += 25;
            }
            else {
                precio += 15;
            }
            return precio;
        }
        public String toString() {
            return "\nFURGONETA\n" + super.toString() + "  |  Volumen: " + volumenCarga + " (m3)" + "\n";
        }
}
