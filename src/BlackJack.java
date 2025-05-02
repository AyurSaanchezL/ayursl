import java.util.Random;

import java.util.Scanner;



public class BlackJack {

    // CONSTANTES PARA CAMBIAR DE COLOR EL TEXTO DE SALIDA
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();


        String continuar;
        int opcion;
        int nuevaCarta1;
        int nuevaCarta2;

        do{ // --> refactorización = crear una función para cada opción y así evitar la repetición de código (switch)
            int pts1 = 0; // --> refactorización = renombrar variables
            int pts2 = 0;

            // Menu inicial del juego
            System.out.println("\n\n=============================================");

            System.out.println("\n♣️ == BLACK JACK == ♦️");

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
                int cartaDos=0;
                int cartaUno = r.nextInt(12) + 1;
                nuevaCarta1 = r.nextInt(12) + 1;
                pts1 += cartaUno + nuevaCarta1;

                cartaDos = r.nextInt(12) + 1;
                nuevaCarta2 = r.nextInt(12) + 1;
                pts2 += cartaDos + nuevaCarta2;

                // Mano del jugador
                System.out.println("Tu mano: ");
                System.out.println(cartasDobles(cartaUno, nuevaCarta1));
                System.out.println("Llevas " + pts1 + " puntos");

                // Mano del jugador
                System.out.println("Mano del crupier: ");
                System.out.println(cartasDobles(cartaDos, nuevaCarta2));
                System.out.println("Crupier: " + pts2 + " puntos");

                if (pts1 < 21){
                    // Preguntar si continuar
                    System.out.print(ANSI_BLUE + "¿Continuar? (s|n): " + ANSI_RESET);
                    continuar = sc.nextLine();

                    // TERMINA LA RONDA 1, EMPIEZAN LAS DEMÁS

                    while (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n")) {
                        System.out.println("❌ Opción no válida");
                        System.out.print(ANSI_BLUE + "¿Continuar? (s|n): " + ANSI_RESET);
                        continuar = sc.nextLine();
                    }
                    Thread.sleep(700);
                    while (continuar.equals("s") && pts1 < 21){
                        System.out.println("\n...........................");
                        nuevaCarta1 = r.nextInt(12) + 1;
                        pts1 += nuevaCarta1;

                        if (pts2 < 17){
                            nuevaCarta2 = r.nextInt(12) + 1;
                            pts2 += nuevaCarta2;
                        }

                        // Mano del jugador
                        System.out.println("Tu mano: ");
                        System.out.println(mostrarCarta(nuevaCarta1));
                        System.out.println("Llevas " + pts1 + " puntos");

                        // Mano del crupier (solo si ha robado cartas)
                        if (pts2 < 17) {
                            System.out.println(mostrarCarta(nuevaCarta2));
                        }
                        System.out.println("Crupier: " + pts2 + " puntos");

                        // Preguntar si continuar
                        if (pts1 <21){
                            System.out.print(ANSI_BLUE + "¿Continuar? (s|n): " + ANSI_RESET);
                            continuar = sc.nextLine();

                            while (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n")) {
                                System.out.println("❌ Opción no válida");
                                System.out.print(ANSI_BLUE + "¿Continuar? (s|n): " + ANSI_RESET);
                                continuar = sc.nextLine();
                            }
                        }

                        if (continuar.equals("n") && pts2 < 17){  // Si el jugador ya no quiere continuar Y el crupier tiene menos de 17 puntos, éste roba una última carta.
                            nuevaCarta2 = r.nextInt(12) + 1;
                            pts2 += nuevaCarta2;
                            System.out.println(mostrarCarta(nuevaCarta2));
                            System.out.println("Crupier: " + pts2 + " puntos");
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
                if (pts1 < 21 && pts2 < 21){
                    if (pts1 > pts2){
                        System.out.println("🏆 Has ganado!!");
                    }else if (pts1 < pts2){
                        System.out.println("❌ Has perdido!!");
                    }else {
                        System.out.println("⚖ Empate!");
                    }
                }else if (pts1 == 21){
                    System.out.println("🏆 Has ganado!!");
                }else{
                    System.out.println("❌ Has perdido!!");
                }


                System.out.println("Puntos finales:");
                System.out.println("Tú: "+pts1);
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