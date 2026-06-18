import java.util.Random;

public class HoughCircunferencias {

    static final int FILAS = 20;
    static final int COLUMNAS = 20;
    static final int RADIO = 5;

    public static void main(String[] args) {

        // Simulación de llegada del aro C con una posición desplazada
        Random random = new Random();
        int centroIncX = RADIO + random.nextInt(COLUMNAS - 2 * RADIO);
        int centroIncY = RADIO + random.nextInt(FILAS - 2 * RADIO);

        int[][] imagen = crearCircunferencia(centroIncX, centroIncY, RADIO);

        System.out.println("Posición del Aro C recibida");
        mostrarImagen(imagen);

        transformadaHoughCircunferencias(imagen);
    }


    public static int[][] crearCircunferencia(int centroX, int centroY, int radio) {

        int[][] imagen = new int[FILAS][COLUMNAS];

        for (int angulo = 0; angulo < 360; angulo++) {
            double rad = Math.toRadians(angulo);
            int x = (int) Math.round(centroX + radio * Math.cos(rad));
            int y = (int) Math.round(centroY + radio * Math.sin(rad));
            if (x >= 0 && x < COLUMNAS && y >= 0 && y < FILAS) {
                imagen[y][x] = 1;
            }
        }
        return imagen;
    }

    public static void transformadaHoughCircunferencias(int[][] imagen) {

        int mejorCentroX = 0;
        int mejorCentroY = 0;
        int maxVotos = 0;
        int[][] acumulador = new int[FILAS][COLUMNAS];

        // Votación
        for (int y = 0; y < FILAS; y++) {
            for (int x = 0; x < COLUMNAS; x++) {
                if (imagen[y][x] == 1) {
                    for (int angulo = 0; angulo < 360; angulo++) {
                        double rad = Math.toRadians(angulo);
                        int a = (int) Math.round(x - RADIO * Math.cos(rad));
                        int b = (int) Math.round(y - RADIO * Math.sin(rad));
                        if (a >= 0 && a < COLUMNAS && b >= 0 && b < FILAS) {
                            acumulador[b][a]++;
                            if (acumulador[b][a] > maxVotos) {
                                maxVotos = acumulador[b][a];
                                mejorCentroX = a;
                                mejorCentroY = b;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Centro detectado:");
        System.out.println("X = " + mejorCentroX);
        System.out.println("Y = " + mejorCentroY);
        System.out.println("Radio = " + RADIO);

        int posicionIdealX = COLUMNAS / 2;
        int posicionIdealY = FILAS / 2;

        // Cálculo de la corrección para llevar el centro detectado al punto A
        System.out.println("\nSIMULACION INDUSTRIAL");
        System.out.println("Posicion ideal = (" + posicionIdealX + "," + posicionIdealY + ")");
        System.out.println("Posicion detectada = (" + mejorCentroX + "," + mejorCentroY + ")");
        System.out.println("Correccion en X = " + (posicionIdealX - mejorCentroX));
        System.out.println("Correccion en Y = " + (posicionIdealY - mejorCentroY));
    }

    public static void mostrarImagen(int[][] imagen) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (imagen[i][j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}