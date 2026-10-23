package Principal;

import monitores.OllaComunitaria;
import Personas.Cocinero;
import Personas.Salvaje;


public class ProblemaSalvajes {
    public static void main(String[] args) {
        final int CAPACIDAD_P = 3;
        final int CANT_SALVAJES = 6;

        OllaComunitaria olla = new OllaComunitaria(CAPACIDAD_P);

        // Generamos un hilo para el cocinero (en forma de daemon para que se termine si termina el programa principal
        Thread cocinero = new Thread(new Cocinero(olla), "Cocinero");
        cocinero.setDaemon(true);
        cocinero.start();

        // Creacion de hilos para los salvajes
        for (int i = 1; i <= CANT_SALVAJES; i++) {
            new Thread(new Salvaje(i, olla), "Salvaje-" + i).start();
        }
    }
}
