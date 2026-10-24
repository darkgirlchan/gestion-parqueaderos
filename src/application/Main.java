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
        iniciarEspacios();
        int opc=0;
        do {
            menu();
            opc= sc.nextInt();
        }while (opc!=5);

    }
    static void iniciarEspacios() {
        for (int i = 0; i< CAPACIDAD_TOTAL; i++){
            tiposVehiculos[i]= null;
            fechaIngresos[i]=null;
        }
        celdasOcupadas = 0;
        celdasLibres = CAPACIDAD_TOTAL;
        recaudoCarros = 0;
        recaudoMotos = 0;
    }
    static void menu(){
        System.out.println("Bienvenido al parqueadero Parquipass");
        System.out.println("Seleccione una opcion");
        System.out.println("1. Registrar hora de entrada");
        System.out.println("2. Registrar hora de salida");
        System.out.println("3. Reporte de ocupacion");
        System.out.println("4. Reporte financiero");
        System.out.println("5. Salir");
    }
    static void celdaDisponible(String tipo){
        int inicio;
        int fin;
        if (tipo==CARRO) {
            inicio=0;
            fin=CAPACIDAD_CARROS;
        } else if (tipo==MOTO) {
            inicio=0;
            fin=CAPACIDAD_MOTOS;
        }else
            System.out.println("Tipo de vehiculo no valido");
    }
}
