package Personas;

import monitores.OllaComunitaria;

public class Cocinero implements Runnable {
    private final OllaComunitaria olla;

    public Cocinero(OllaComunitaria olla) {
        this.olla = olla;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                olla.llenarOlla();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
