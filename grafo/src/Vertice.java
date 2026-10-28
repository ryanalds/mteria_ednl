

public class Vertice<V> {
    private final int identificador;
    private V valor;

    Vertice(int identificador, V valor) {
        this.identificador = identificador;
        this.valor = valor;
    }

    public int getIdentificador() {
        return identificador;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Vertice{" + identificador + ", valor=" + valor + '}';
    }
}
