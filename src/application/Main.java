package application;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    static final int CAPACIDAD_CARROS = 10;
    static final int CAPACIDAD_MOTOS = 20;
    static final int CAPACIDAD_TOTAL = CAPACIDAD_CARROS + CAPACIDAD_MOTOS;
    static final long TARIFA_HORA_CARRO = 4000;
    static final long TARIFA_DIA_CARRO = 30000;
    static final long TARIFA_HORA_MOTO = 2000;
    static final long TARIFA_DIA_MOTO = 15000;
    static final int UMBRAL_HORAS_DIA = 8;
    static final int HORA_APERTURA = 8;
    static final int HORA_CIERRE = 22;
    static final String CARRO = "Carro";
    static final String MOTO = "Moto";
    static final Scanner sc = new Scanner(System.in);
    static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String[] tiposVehiculos = new String[CAPACIDAD_TOTAL];
    static LocalDateTime[] fechaIngresos = new LocalDateTime[CAPACIDAD_TOTAL];
    static int celdasOcupadas;
    static int celdasLibres;
    static long recaudoCarros;
    static long recaudoMotos;
    public static void main(String[] args){

    }

}
