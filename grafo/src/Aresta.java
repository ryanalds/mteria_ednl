

public class Aresta<V, A> {
    private final int identificador;
    private final Vertice<V> origem;
    private final Vertice<V> destino;
    private final boolean direcionada;
    private A valor;

    Aresta(int identificador, Vertice<V> origem, Vertice<V> destino,
           A valor, boolean direcionada) {
        this.identificador = identificador;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.direcionada = direcionada;
    }

    public int getIdentificador() {
        return identificador;
    }

    public Vertice<V> getOrigem() {
        return origem;
    }

    public Vertice<V> getDestino() {
        return destino;
    }

    public A getValor() {
        return valor;
    }

    public void setValor(A valor) {
        this.valor = valor;
    }

    public boolean isDirecionada() {
        return direcionada;
    }

    @Override
    public String toString() {
        String conector = direcionada ? " -> " : " -- ";
        return origem.getIdentificador() + conector + destino.getIdentificador()
                + " (" + valor + ')';
    }
}
