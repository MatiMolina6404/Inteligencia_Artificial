public class HoughRectas {
    static final int FILAS = 20;
    static final int COLUMNAS = 20;

    public static void main(String[] args) {
        int[][] imagen = crearImagen();
        System.out.println("IMAGEN ORIGINAL");
        mostrarImagen(imagen);
        detectarRecta(imagen);
    }

    public static int[][] crearImagen() {
        int[][] imagen = new int[FILAS][COLUMNAS];
        // Generación de una recta diagonal en la imagen
        for (int i = 2; i < 15; i++) {
            imagen[i][i] = 1;
        }
        return imagen;
    }

    public static void detectarRecta(int[][] imagen) {

        int maxVotos = 0;
        int mejorTheta = 0;
        int mejorRho = 0;
        int diagonal = (int) Math.sqrt(FILAS * FILAS + COLUMNAS * COLUMNAS);
        int[][] acumulador = new int[180][2 * diagonal];

        // Votación en el acumulador de Hough
        for (int y = 0; y < FILAS; y++) {
            for (int x = 0; x < COLUMNAS; x++) {
                if (imagen[y][x] == 1) {
                    for (int theta = 0; theta < 180; theta++) {
                        double rad = Math.toRadians(theta);
                        int rho = (int) Math.round(x * Math.cos(rad) + y * Math.sin(rad));
                        rho += diagonal;
                        acumulador[theta][rho]++;
                        if (acumulador[theta][rho] > maxVotos) {
                            maxVotos = acumulador[theta][rho];
                            mejorTheta = theta;
                            mejorRho = rho - diagonal;
                        }
                    }
                }
            }
        }

        System.out.println("\nRECTA DETECTADA");
        System.out.println("Theta = " + mejorTheta + " grados");
        System.out.println("Rho = " + mejorRho);
        System.out.println("Votos = " + maxVotos);
    }

    public static void mostrarImagen(int[][] imagen) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(imagen[i][j] == 1 ? "#" : ".");
            }
            System.out.println();
        }
    }
}

