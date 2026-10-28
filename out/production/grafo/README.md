# Módulo Java `tad.grafo`

O TAD usa matriz de adjacência. Cada célula contém uma lista de arestas (`ArrayList`), permitindo representar arestas paralelas. Arestas não direcionadas são armazenadas nas duas células simétricas; arestas direcionadas aparecem na célula da origem para o destino.

O construtor padrão cria um grafo simples não direcionado. Use `Grafo.multigrafoMisto()` para permitir laços, arestas paralelas e a combinação de arestas direcionadas e não direcionadas. Também é possível configurar as permissões no construtor `Grafo(boolean permiteParalelas, boolean permiteMisto)`.

As classes `Grafo`, `Vertice` e `Aresta` são genéricas e guardam valores associados. A API permite adicionar/remover vértices e arestas, consultar adjacência, grau total, grau de entrada e saída, ordem, tamanho, vértices e arestas.

Compile a partir da pasta `grafo` com `javac -d out src/module-info.java src/*.java`. A classe `grafo.Main` demonstra a criação de um multigrafo misto.
