package application;
import java.time.Duration;
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
                    break;
                case 2:
                    registrarSalida();
                    break;
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
    static void registrarSalida() {
        System.out.println("ingrese el numero de celda al que va a cobrar salida (1 a " + CAPACIDAD_TOTAL + "):");
        int celda = leerEntero(1, CAPACIDAD_TOTAL);
        int posicion = celda - 1;

        if (tiposVehiculos[posicion] == null) {
            System.out.println("esta celda esta vacia no hay nada pendiente por cobrar");
            return;
        }

        String tipo = tiposVehiculos[posicion];
        LocalDateTime ingreso = fechaIngresos[posicion];

        System.out.println("registre la fecha y hora de saida");
        LocalDateTime salida = leerFechaHora();
        while (salida.isBefore(ingreso)) {
            System.out.println("la hora de salida no puede ser menor a la fecha de ingreso");
            salida = leerFechaHora();
        }
        long tarifaHora;
        long tarifaDia;
        if (tipo==CARRO) {
            tarifaHora=TARIFA_HORA_CARRO;
            tarifaDia=TARIFA_DIA_CARRO;
        } else {
            tarifaHora=TARIFA_HORA_MOTO;
            tarifaDia=TARIFA_DIA_MOTO;
        }
        long minutosTotales = Duration.between(ingreso, salida).toMinutes();
        long dias = minutosTotales / (24 * 60);
        long minutosSobrantes = minutosTotales % (24 * 60);
        long horas = (minutosSobrantes + 59) / 60;

        if (horas >= UMBRAL_HORAS_DIA) {
            dias++;
            horas = 0;
        }

        long total = dias * tarifaDia + horas * tarifaHora;

        System.out.println();
        System.out.println("factura");
        System.out.println("celda liberada: " + celda);
        System.out.println("tipo de vehiculo: " + tipo);
        System.out.println("su fecha y hora de ingreso es: " + ingreso.format(FORMATO_FECHA_HORA));
        System.out.println("su fecha y hora de salida es: " + salida.format(FORMATO_FECHA_HORA));
        if (dias > 0) {
            System.out.println(dias + " dia(s) x $" + tarifaDia + " = $" + (dias * tarifaDia));
        }
        if (horas > 0) {
            System.out.println(horas + " hora(s) x $" + tarifaHora + " = $" + (horas * tarifaHora));
        }
        System.out.println("El total a pagar es: $" + total);

        if (tipo==CARRO) {
            recaudoCarros = recaudoCarros + total;
        } else {
            recaudoMotos = recaudoMotos + total;
        }

        tiposVehiculos[posicion] = null;
        fechaIngresos[posicion] = null;
        celdasOcupadas--;
        celdasLibres++;
    }
}
