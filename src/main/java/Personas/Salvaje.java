package Personas;

import monitores.OllaComunitaria;

public class Salvaje implements Runnable {
    private final int id;
    private final OllaComunitaria olla;

    public Salvaje(int id, OllaComunitaria olla) {
        this.id = id;
        this.olla = olla;
    }

    private void comer() throws InterruptedException {
        // Metodo fuera del monitor para que coma lo que se sirvio sin bloquear el monitor
        System.out.println("Salvaje " + id + " comiendo en su lugar.");
        Thread.sleep(1000);     // para simular que tarda en comer
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                olla.servirPlato(this.id);
                comer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
