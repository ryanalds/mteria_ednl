

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** TAD Grafo implementado por matriz de adjacência com listas de arestas nas células. */
public class Grafo<V, A> {
    private final boolean permiteParalelas;
    private final boolean permiteMisto;
    private final List<Vertice<V>> vertices = new ArrayList<>();
    private final List<List<List<Aresta<V, A>>>> matriz = new ArrayList<>();
    private int proximoIdVertice;
    private int proximoIdAresta;

    /** Cria um grafo simples não direcionado. */
    public Grafo() {
        this(false, false);
    }

    /**
     * @param permiteParalelas permite arestas paralelas e laços
     * @param permiteMisto permite combinar arestas direcionadas e não direcionadas
     */
    public Grafo(boolean permiteParalelas, boolean permiteMisto) {
        this.permiteParalelas = permiteParalelas;
        this.permiteMisto = permiteMisto;
    }

    public static <V, A> Grafo<V, A> multigrafoMisto() {
        return new Grafo<>(true, true);
    }

    public Vertice<V> adicionarVertice(V valor) {
        Vertice<V> vertice = new Vertice<>(proximoIdVertice++, valor);
        vertices.add(vertice);

        for (List<List<Aresta<V, A>>> linha : matriz) {
            linha.add(new ArrayList<>());
        }

        List<List<Aresta<V, A>>> novaLinha = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            novaLinha.add(new ArrayList<>());
        }
        matriz.add(novaLinha);
        return vertice;
    }

    public boolean removerVertice(Vertice<V> vertice) {
        int indice = indiceDe(vertice);
        if (indice < 0) {
            return false;
        }

        vertices.remove(indice);
        matriz.remove(indice);
        for (List<List<Aresta<V, A>>> linha : matriz) {
            linha.remove(indice);
        }
        return true;
    }

    public Aresta<V, A> adicionarAresta(Vertice<V> origem, Vertice<V> destino,
                                        A valor, boolean direcionada) {
        int indiceOrigem = indiceDe(origem);
        int indiceDestino = indiceDe(destino);
        if (indiceOrigem < 0 || indiceDestino < 0) {
            throw new IllegalArgumentException("Os vértices devem pertencer ao grafo.");
        }

        if (!permiteParalelas && (indiceOrigem == indiceDestino
                || !matriz.get(indiceOrigem).get(indiceDestino).isEmpty())) {
            throw new IllegalArgumentException("Grafo simples não permite laços ou arestas paralelas.");
        }

        List<Aresta<V, A>> existentes = arestas();
        if (!permiteMisto && !existentes.isEmpty()
                && existentes.get(0).isDirecionada() != direcionada) {
            throw new IllegalArgumentException("Este grafo não permite misturar tipos de aresta.");
        }

        Aresta<V, A> aresta = new Aresta<>(proximoIdAresta++, origem, destino, valor, direcionada);
        matriz.get(indiceOrigem).get(indiceDestino).add(aresta);
        if (!direcionada && indiceOrigem != indiceDestino) {
            matriz.get(indiceDestino).get(indiceOrigem).add(aresta);
        }
        return aresta;
    }

    public Aresta<V, A> adicionarAresta(Vertice<V> origem, Vertice<V> destino, A valor) {
        return adicionarAresta(origem, destino, valor, false);
    }

    public boolean removerAresta(Aresta<V, A> aresta) {
        if (aresta == null) {
            return false;
        }

        int indiceOrigem = indiceDe(aresta.getOrigem());
        int indiceDestino = indiceDe(aresta.getDestino());
        if (indiceOrigem < 0 || indiceDestino < 0
                || !matriz.get(indiceOrigem).get(indiceDestino).remove(aresta)) {
            return false;
        }

        if (!aresta.isDirecionada() && indiceOrigem != indiceDestino) {
            matriz.get(indiceDestino).get(indiceOrigem).remove(aresta);
        }
        return true;
    }

    public boolean saoAdjacentes(Vertice<V> primeiro, Vertice<V> segundo) {
        int i = indiceDe(primeiro);
        int j = indiceDe(segundo);
        return i >= 0 && j >= 0 && !matriz.get(i).get(j).isEmpty();
    }

    /** Grau total: cada laço contribui com dois. */
    public int grau(Vertice<V> vertice) {
        int indice = indiceDe(vertice);
        if (indice < 0) {
            throw new IllegalArgumentException("Vértice não pertence ao grafo.");
        }

        int total = 0;
        for (int i = 0; i < vertices.size(); i++) {
            for (Aresta<V, A> aresta : matriz.get(indice).get(i)) {
                total++;
            }
            for (Aresta<V, A> aresta : matriz.get(i).get(indice)) {
                if (aresta.isDirecionada()) {
                    total++;
                }
            }
        }
        for (Aresta<V, A> aresta : matriz.get(indice).get(indice)) {
            if (!aresta.isDirecionada()) {
                total++;
            }
        }
        return total;
    }

    public int grauEntrada(Vertice<V> vertice) {
        int indice = indiceDe(vertice);
        if (indice < 0) {
            throw new IllegalArgumentException("Vértice não pertence ao grafo.");
        }

        int total = 0;
        for (int i = 0; i < vertices.size(); i++) {
            for (Aresta<V, A> aresta : matriz.get(i).get(indice)) {
                if (aresta.isDirecionada()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int grauSaida(Vertice<V> vertice) {
        int indice = indiceDe(vertice);
        if (indice < 0) {
            throw new IllegalArgumentException("Vértice não pertence ao grafo.");
        }

        int total = 0;
        for (int j = 0; j < vertices.size(); j++) {
            for (Aresta<V, A> aresta : matriz.get(indice).get(j)) {
                if (aresta.isDirecionada()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int ordem() {
        return vertices.size();
    }

    public int tamanho() {
        return arestas().size();
    }

    public boolean isVazio() {
        return vertices.isEmpty();
    }

    public boolean permiteParalelas() {
        return permiteParalelas;
    }

    public boolean permiteMisto() {
        return permiteMisto;
    }

    public List<Vertice<V>> vertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<Aresta<V, A>> arestas() {
        List<Aresta<V, A>> resultado = new ArrayList<>();
        for (List<List<Aresta<V, A>>> linha : matriz) {
            for (List<Aresta<V, A>> celula : linha) {
                for (Aresta<V, A> aresta : celula) {
                    if (!resultado.contains(aresta)) {
                        resultado.add(aresta);
                    }
                }
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public List<Aresta<V, A>> arestasEntre(Vertice<V> origem, Vertice<V> destino) {
        int i = indiceDe(origem);
        int j = indiceDe(destino);
        if (i < 0 || j < 0) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(matriz.get(i).get(j)));
    }

    private int indiceDe(Vertice<V> vertice) {
        if (vertice == null) {
            return -1;
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) == vertice) {
                return i;
            }
        }
        return -1;
    }
}
