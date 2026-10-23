package monitores;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class OllaComunitaria {
    private final int capacidadP;
    private int platos;

    // Lock de exclusion mutua (true es para orden FIFO)
    private final Lock mutex = new ReentrantLock(true);

    // Variables condition
    private final Condition ollaVacia = mutex.newCondition();
    private final Condition ollaLlena = mutex.newCondition();

    public OllaComunitaria(int capacidadP) {
        this.capacidadP = capacidadP;
        this.platos = capacidadP;    // inicia lleno
    }

    // Metodo del monitor para que el salvaje se sirva
    public void servirPlato(int idSalvaje) throws InterruptedException {
        mutex.lock();
        try {
            // Mientras la olla está vacía, le avisa al cocinero y espera en la cola
            while (platos == 0) {
                System.out.println("Salvaje " + idSalvaje + " ve olla vacía y despierta al cocinero");
                ollaVacia.signal();     // Si el cocinero ya se desperto, este signal no hace nada
                ollaLlena.await();      // Libera el mutex y duerme en la cola de salvajes
            }

            // El salvaje se sirve su racion
            platos--;
            System.out.println("Salvaje " + idSalvaje + " se sirve un plato. Quedan: " + platos + ".");

            // Si quedan platos para servirse, este salvaje despierta al que sigue en la cola de salvajes
            if (platos > 0) {
                ollaLlena.signal();
            }
        } finally {
            mutex.unlock(); // Libera el Lock
        }
    }

    public void llenarOlla() throws InterruptedException {
        mutex.lock();
        try {
            // Mientras hayan platos para servir el cocinero duerme
            while (platos > 0) {
                ollaVacia.await();
            }

            // Llena la olla
            System.out.println("Cocinero llenando la olla con " + capacidadP + " platos.");
            Thread.sleep(1000);     // para simular tiempo de preparacion
            platos = capacidadP;
            System.out.println("La olla está llena.");

            // Despierta a un solo salvaje (este se encarga de despertar al que sigue)
            ollaLlena.signal();
        } finally {
            mutex.unlock();
        }
    }
}
