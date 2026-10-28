

public class Main {
    public static void main(String[] args) {
        Grafo<String, String> grafo = Grafo.multigrafoMisto();
        Vertice<String> a = grafo.adicionarVertice("A");
        Vertice<String> b = grafo.adicionarVertice("B");
        Vertice<String> c = grafo.adicionarVertice("C");

        grafo.adicionarAresta(a, b, "rua de mão dupla", false);
        grafo.adicionarAresta(a, b, "linha expressa", true);
        grafo.adicionarAresta(b, c, "conexão", false);

        System.out.println("Vértices: " + grafo.vertices());
        System.out.println("Arestas: " + grafo.arestas());
        System.out.println("Ordem: " + grafo.ordem());
        System.out.println("Tamanho: " + grafo.tamanho());
        System.out.println("Grau de A: " + grafo.grau(a));
    }
}
