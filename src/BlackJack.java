import java.util.Random;

import java.util.Scanner;



public class BlackJack {

    // CONSTANTES PARA CAMBIAR DE COLOR EL TEXTO DE SALIDA
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final int PUNTOS_GANAR = 21;

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
        
        String continuar;
        int opcion;
        int nuevaCarta1;
        int nuevaCarta2;

        do{ // --> refactorización = crear una función para cada opción y así evitar la repetición de código (switch)
            int puntosJugador1 = 0; // --> refactorización = renombrar variables
            int pts2 = 0;

            // Menu inicial del juego
            System.out.println("\n\n=============================================");

            System.out.println("♣️ == BLACK JACK == ♦️");

            System.out.println("1. Nueva partida");

            System.out.println("2. Reglas del juego");

            System.out.println("3. Salir");

            System.out.print(ANSI_GREEN + "Elige una acción: " + ANSI_RESET);

            opcion = sc.nextInt();
            sc.nextLine(); // Para limpiar el buffer


            // NUEVA PARTIDA
            if (opcion == 1){

                // RONDA 1

                System.out.println("\n🎲 -= Nueva Partida =- 🎴");

                // Randomizador de cartas
                int cartaUno = r.nextInt(12) + 1;
                nuevaCarta1 = r.nextInt(12) + 1;
                puntosJugador1 += cartaUno + nuevaCarta1;

                int cartaDos = r.nextInt(12) + 1;
                nuevaCarta2 = r.nextInt(12) + 1;
                pts2 += cartaDos + nuevaCarta2;

                // Mano del jugador
                manoJugador(cartaUno, nuevaCarta1, puntosJugador1);

                // Mano del jugador
                manoCrupier(cartaDos, nuevaCarta2, pts2);

                if (puntosJugador1 < PUNTOS_GANAR){

                    // Preguntar si continuar
                    continuar = quiereContinuar();

                    // TERMINA LA RONDA 1, EMPIEZAN LAS DEMÁS

                    while (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n")) {
                        System.out.println("❌ Opción no válida");
                        continuar = quiereContinuar();
                    }
                    Thread.sleep(700);
                    while (continuar.equals("s") && puntosJugador1 < PUNTOS_GANAR){
                        System.out.println("\n...........................");
                        nuevaCarta1 = r.nextInt(12) + 1;
                        puntosJugador1 += nuevaCarta1;

                        if (pts2 < 17){
                            nuevaCarta2 = r.nextInt(12) + 1;
                            pts2 += nuevaCarta2;
                        }

                        // Mano del jugador
                        manoJugador(0, nuevaCarta1, puntosJugador1);

                        // Mano del crupier (solo si ha robado cartas)
                        if (pts2 < 17) {
                            manoCrupier(0, nuevaCarta2, pts2);
                        }else{
                            System.out.println("Crupier: " + pts2 + " puntos");
                        }

                        // Preguntar si continuar
                        if (puntosJugador1 < PUNTOS_GANAR){
                            continuar = quiereContinuar();

                            while (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n")) {
                                System.out.println("❌ Opción no válida");
                                continuar = quiereContinuar();
                            }
                        }

                        if (continuar.equals("n") && pts2 < 17){  // Si el jugador ya no quiere continuar Y el crupier tiene menos de 17 puntos, éste roba una última carta.
                            nuevaCarta2 = r.nextInt(12) + 1;
                            pts2 += nuevaCarta2;
                            manoCrupier(0, nuevaCarta2, pts2);
                        }

                        Thread.sleep(650);
                        // El juego termina si el jugador se planta, si gana o si pierde
                    }
                    if (continuar.equals("n") && pts2 < 17){  // Si el jugador ya no quiere continuar Y el crupier tiene menos de 17 puntos, éste roba una última carta.
                        nuevaCarta2 = r.nextInt(12) + 1;
                        pts2 += nuevaCarta2;
                        System.out.println(mostrarCarta(nuevaCarta2));
                        System.out.println("Crupier: " + pts2 + " puntos");
                    }
                }
                Thread.sleep(1000);
                System.out.println("\n\n🛑 == Fin del juego == 🛑\n");

                // Cuando sale comprueba quién ha ganado
                if (puntosJugador1 < PUNTOS_GANAR && pts2 < PUNTOS_GANAR){
                    if (puntosJugador1 > pts2){
                        System.out.println("🏆 Has ganado!!");
                    }else if (puntosJugador1 < pts2){
                        System.out.println("❌ Has perdido!!");
                    }else {
                        System.out.println("⚖ Empate!");
                    }
                }else if (puntosJugador1 == PUNTOS_GANAR){
                    System.out.println("🏆 Has ganado!!");
                }else{
                    System.out.println("❌ Has perdido!!");
                }


                System.out.println("Puntos finales:");
                System.out.println("Jugador: "+puntosJugador1);
                System.out.println("Crupier: "+pts2);
                Thread.sleep(2000);
                System.out.println("\n\n\n");


            }else if (opcion == 2){

                System.out.println(ANSI_CYAN + "\n\n💠  -= Reglas del juego =- 💠");

                System.out.println("\nObjetivo: llegar a 21 puntos");

                System.out.println("\n -> Ganas si: llegas a 21 puntos, si eres el jugador que más cerca se queda o si el jugador 2 pierda.");

                System.out.println(" -> Pierdes si: te pasas de 21 puntos o el jugador 2 gana.");

                System.out.println("\nOpciones: \n 1. Continuar (introduce 's')\n 2. Plantarse (introduce 'n')"  + ANSI_RESET);

                Thread.sleep(3500);

            }else if (opcion == 3){
                System.out.println("\n\n Hasta la próxima!! 👋");
            }else{
                System.out.println("\n❌ *ERROR* opción no válida\n");
            }

        }while (opcion != 3);

    }

    private static void manoJugador(int cartaUno, int nuevaCarta1, int puntosJugador1) {
        if (cartaUno == 0){
            System.out.println("Tu mano: ");
            System.out.println(mostrarCarta(nuevaCarta1));
            System.out.println("Llevas " + puntosJugador1 + " puntos");
        }else{
            System.out.println("Tu mano: ");
            System.out.println(cartasDobles(cartaUno, nuevaCarta1));
            System.out.println("Llevas " + puntosJugador1 + " puntos");
        }
    }

    private static void manoCrupier(int cartaDos, int nuevaCarta2, int pts2) {
        if (cartaDos == 0){
            System.out.println("Mano del crupier: ");
            System.out.println(mostrarCarta(nuevaCarta2));
            System.out.println("Crupier: " + pts2 + " puntos");
        }else{
            System.out.println("Mano del crupier: ");
            System.out.println(cartasDobles(cartaDos, nuevaCarta2));
            System.out.println("Crupier: " + pts2 + " puntos");
        }
    }

    private static String quiereContinuar() {
        String continuar;
        System.out.print(ANSI_BLUE + "¿Continuar? (s|n): " + ANSI_RESET);
        continuar = sc.nextLine();
        return continuar;
    }


    private static String cartasDobles(int puntos1, int puntos2){
        StringBuilder representacion = new StringBuilder();
        representacion.append("┌────┐ ┌────┐\n");
        representacion.append("│ ").append(puntos1).append("  │ │ ");
        representacion.append(puntos2);
        representacion.append("  │\n");
        representacion.append("│    │ │    │\n");
        representacion.append("└────┘ └────┘\n");
        return representacion.toString();
    }

    private static String mostrarCarta(int puntos){
        StringBuilder representacion = new StringBuilder();
        representacion.append("┌────┐\n");
        representacion.append("│ ").append(puntos).append("  │\n");
        representacion.append("│    │\n");
        representacion.append("└────┘\n");
        return representacion.toString();
    }

}
