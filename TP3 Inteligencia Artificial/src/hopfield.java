import java.util.Random;

public class Hopfield {

    static final int filas = 10;
    static final int columnas = 10;
    static final int N = filas * columnas;
    static final int iteraciones = 10;

    public static void main(String[] args) {

        // Patrón de referencia
        int[][] patronOriginal = crearPatron();

        // Imagen con ruido
        int[][] imagenRuidosa = agregarRuido(patronOriginal, 0.10);

        // Entrenamiento con Hebb
        double[][] pesos = entrenarHebb(patronOriginal);

        // Recuperación de imagen
        int[][] imagenReconstruida = ejecutarHopfield(pesos, imagenRuidosa);

        // Mostrar resultados
        System.out.println("=== IMAGEN ORIGINAL ===");
        mostrarImagen(patronOriginal);

        System.out.println("\n=== IMAGEN CON RUIDO ===");
        mostrarImagen(imagenRuidosa);

        System.out.println("\n=== IMAGEN RECONSTRUIDA ===");
        mostrarImagen(imagenReconstruida);
    }

    // Crear figura geometrica
    public static int[][] crearPatron() {

        int[][] patron = new int[filas][columnas];

        // Fondo vacío
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                patron[i][j] = -1;
            }
        }
        // Figura geométrica
        for (int i = 2; i <= 7; i++) {
            for (int j = 2; j <= 7; j++) {
                if (i == 2 || i == 7 || j == 2 || j == 7) {
                    patron[i][j] = 1;
                }
            }
        }
        // Punto de referencia fijo
        patron[9][0] = 1;
        return patron;
    }

    // Generar ruido
    public static int[][] agregarRuido(int[][] original, double probabilidadRuido) {

        Random random = new Random();
        int[][] ruidosa = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (random.nextDouble() < probabilidadRuido) {
                    ruidosa[i][j] = -original[i][j];
                } else {
                    ruidosa[i][j] = original[i][j];
                }
            }
        }
        return ruidosa;
    }

    // Aprendizaje Hebb
    public static double[][] entrenarHebb(int[][] patron) {

        double[][] W = new double[N][N];
        int[] vector = convertirVector(patron);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Diagonal nula
                if (i != j) {
                    W[i][j] = vector[i] * vector[j];
                }
            }
        }
        return W;
    }

    // Recuperación del patrón
    public static int[][] ejecutarHopfield(double[][] W, int[][] entrada) {

        int[] estado = convertirVector(entrada);

        for (int iteracion = 0; iteracion < iteraciones; iteracion++) {
            int[] nuevoEstado = estado.clone();
            for (int i = 0; i < N; i++) {
                double suma = 0;
                for (int j = 0; j < N; j++) {
                    suma += W[i][j] * estado[j];
                }
                // Función de activación binaria
                nuevoEstado[i] = (suma >= 0) ? 1 : -1;
            }
            if (compararVectores(estado, nuevoEstado)) {
                break;
            }
            estado = nuevoEstado;
        }
        return convertirMatriz(estado);
    }

    // Convertir matriz a vector
    public static int[] convertirVector(int[][] matriz) {

        int[] vector = new int[N];
        int indice = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                vector[indice++] = matriz[i][j];
            }
        }
        return vector;
    }

    // Convertir vector a matriz
    public static int[][] convertirMatriz(int[] vector) {

        int[][] matriz = new int[filas][columnas];
        int indice = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = vector[indice++];
            }
        }
        return matriz;
    }

    // Comparar vectores
    public static boolean compararVectores(int[] a, int[] b) {

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    // Mostrar imagen
    public static void mostrarImagen(int[][] matriz) {

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}