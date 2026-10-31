package application;
import java.time.YearMonth;
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
            opc= leerEntero(1,5);
            switch (opc){
                case 1:
                    registrarEntrada();
            }
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
    static int celdaDisponible(String tipo) {
        int inicio;
        int fin;
        if (tipo==CARRO) {
            inicio = 0;
            fin = CAPACIDAD_CARROS;
        } else if (tipo==MOTO) {
            inicio = CAPACIDAD_CARROS;
            fin = CAPACIDAD_TOTAL;
        } else {
            System.out.println("Tipo de vehiculo no valido");
            return -1;
        }

        for (int i = inicio; i < fin; i++) {
            if (tiposVehiculos[i] == null) {
                return i;
            }
        }
        return -1;
    }
    static int leerEntero(int minimo, int maximo) {
        int numero = minimo - 1;
        while (numero < minimo || numero > maximo) {
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Escriba un numero que sea valido");
            }
            numero = sc.nextInt();
            if (numero < minimo || numero > maximo) {
                System.out.println("debe de digitar un numero que este entre " + minimo + " y " + maximo);
            }
        }
        return numero;
    }
    static LocalDateTime leerFechaHora() {
        System.out.println("Año:");
        int anio = leerEntero(2000, 2100);

        System.out.println("Mes del 1 al 12:");
        int mes = leerEntero(1, 12);

        int ultimoDia = YearMonth.of(anio, mes).lengthOfMonth();
        System.out.println("Dia (1-" + ultimoDia + "):");
        int dia = leerEntero(1, ultimoDia);

        System.out.println("Hora (" + HORA_APERTURA + "-" + HORA_CIERRE + "):");
        int hora = leerEntero(HORA_APERTURA, HORA_CIERRE);

        int minuto = 0;
        if (hora != HORA_CIERRE) {
            System.out.println("Minuto de 0 a 59):");
            minuto = leerEntero(0, 59);
        }

        return LocalDateTime.of(anio, mes, dia, hora, minuto);
    }
    static void registrarEntrada() {
        System.out.println("cual es el tipo de vehiculo seleccione 1 para Carro y 2 para Moto):");
        int opcion = leerEntero(1, 2);
        String tipo;
        if (opcion == 1) {
            tipo = CARRO;
        } else {
            tipo = MOTO;
        }

        int posicion = celdaDisponible(tipo);
        if (posicion == -1) {
            System.out.println("No quedan celdas disponibles para ese tipo de vehiculo");
            return;
        }

        System.out.println("Ingrese su fecha y hora de ingreso");
        LocalDateTime ingreso = leerFechaHora();

        tiposVehiculos[posicion] = tipo;
        fechaIngresos[posicion] = ingreso;
        celdasOcupadas++;
        celdasLibres--;

        System.out.println("Su vehiculo a sido guardado en la celda " + (posicion + 1));
    }
}
