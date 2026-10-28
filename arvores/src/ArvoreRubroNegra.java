
public class ArvoreRubroNegra {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private static final class No {
        int valor;
        boolean cor;
        No esquerda;
        No direita;
        No pai;

        No(int valor, boolean cor) {
            this.valor = valor;
            this.cor = cor;
        }
    }

    private final No nil = new No(0, PRETO);
    private No raiz = nil;

    public ArvoreRubroNegra() {
        nil.esquerda = nil;
        nil.direita = nil;
        nil.pai = nil;
    }

    public boolean isEmpty() {
        return raiz == nil;
    }

    public void inserir(int valor) {
        No pai = nil;
        No atual = raiz;
        while (atual != nil) {
            pai = atual;
            if (valor == atual.valor) return;
            atual = valor < atual.valor ? atual.esquerda : atual.direita;
        }

        No novo = new No(valor, VERMELHO);
        novo.esquerda = nil;
        novo.direita = nil;
        novo.pai = pai;
        if (pai == nil) raiz = novo;
        else if (valor < pai.valor) pai.esquerda = novo;
        else pai.direita = novo;
        corrigirInsercao(novo);
    }

    private void corrigirInsercao(No no) {
        while (no.pai.cor == VERMELHO) {
            if (no.pai == no.pai.pai.esquerda) {
                No tio = no.pai.pai.direita;
                if (tio.cor == VERMELHO) {
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    no = no.pai.pai;
                } else {
                    if (no == no.pai.direita) {
                        no = no.pai;
                        rotacaoEsquerda(no);
                    }
                    no.pai.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    rotacaoDireita(no.pai.pai);
                }
            } else {
                No tio = no.pai.pai.esquerda;
                if (tio.cor == VERMELHO) {
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    no = no.pai.pai;
                } else {
                    if (no == no.pai.esquerda) {
                        no = no.pai;
                        rotacaoDireita(no);
                    }
                    no.pai.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    rotacaoEsquerda(no.pai.pai);
                }
            }
        }
        raiz.cor = PRETO;
    }

    public boolean buscar(int valor) {
        return localizar(valor) != nil;
    }

    private No localizar(int valor) {
        No atual = raiz;
        while (atual != nil && atual.valor != valor)
            atual = valor < atual.valor ? atual.esquerda : atual.direita;
        return atual;
    }

    public void remover(int valor) {
        No removido = localizar(valor);
        if (removido == nil) return;

        No substituto = removido;
        boolean corOriginal = substituto.cor;
        No filho;
        if (removido.esquerda == nil) {
            filho = removido.direita;
            transplantar(removido, removido.direita);
        } else if (removido.direita == nil) {
            filho = removido.esquerda;
            transplantar(removido, removido.esquerda);
        } else {
            substituto = minimo(removido.direita);
            corOriginal = substituto.cor;
            filho = substituto.direita;
            if (substituto.pai == removido) {
                filho.pai = substituto;
            } else {
                transplantar(substituto, substituto.direita);
                substituto.direita = removido.direita;
                substituto.direita.pai = substituto;
            }
            transplantar(removido, substituto);
            substituto.esquerda = removido.esquerda;
            substituto.esquerda.pai = substituto;
            substituto.cor = removido.cor;
        }
        if (corOriginal == PRETO) corrigirRemocao(filho);
        if (raiz != nil) raiz.pai = nil;
        nil.pai = nil;
    }

    private void corrigirRemocao(No no) {
        while (no != raiz && no.cor == PRETO) {
            if (no == no.pai.esquerda) {
                No irmao = no.pai.direita;
                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    no.pai.cor = VERMELHO;
                    rotacaoEsquerda(no.pai);
                    irmao = no.pai.direita;
                }
                if (irmao.esquerda.cor == PRETO && irmao.direita.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    no = no.pai;
                } else {
                    if (irmao.direita.cor == PRETO) {
                        irmao.esquerda.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacaoDireita(irmao);
                        irmao = no.pai.direita;
                    }
                    irmao.cor = no.pai.cor;
                    no.pai.cor = PRETO;
                    irmao.direita.cor = PRETO;
                    rotacaoEsquerda(no.pai);
                    no = raiz;
                }
            } else {
                No irmao = no.pai.esquerda;
                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    no.pai.cor = VERMELHO;
                    rotacaoDireita(no.pai);
                    irmao = no.pai.esquerda;
                }
                if (irmao.direita.cor == PRETO && irmao.esquerda.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    no = no.pai;
                } else {
                    if (irmao.esquerda.cor == PRETO) {
                        irmao.direita.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacaoEsquerda(irmao);
                        irmao = no.pai.esquerda;
                    }
                    irmao.cor = no.pai.cor;
                    no.pai.cor = PRETO;
                    irmao.esquerda.cor = PRETO;
                    rotacaoDireita(no.pai);
                    no = raiz;
                }
            }
        }
        no.cor = PRETO;
    }

    private No minimo(No no) {
        while (no.esquerda != nil) no = no.esquerda;
        return no;
    }

    private void transplantar(No antigo, No novo) {
        if (antigo.pai == nil) raiz = novo;
        else if (antigo == antigo.pai.esquerda) antigo.pai.esquerda = novo;
        else antigo.pai.direita = novo;
        novo.pai = antigo.pai;
    }

    private void rotacaoEsquerda(No eixo) {
        No topo = eixo.direita;
        eixo.direita = topo.esquerda;
        if (topo.esquerda != nil) topo.esquerda.pai = eixo;
        topo.pai = eixo.pai;
        if (eixo.pai == nil) raiz = topo;
        else if (eixo == eixo.pai.esquerda) eixo.pai.esquerda = topo;
        else eixo.pai.direita = topo;
        topo.esquerda = eixo;
        eixo.pai = topo;
    }

    private void rotacaoDireita(No eixo) {
        No topo = eixo.esquerda;
        eixo.esquerda = topo.direita;
        if (topo.direita != nil) topo.direita.pai = eixo;
        topo.pai = eixo.pai;
        if (eixo.pai == nil) raiz = topo;
        else if (eixo == eixo.pai.direita) eixo.pai.direita = topo;
        else eixo.pai.esquerda = topo;
        topo.direita = eixo;
        eixo.pai = topo;
    }

    public void mostrar() {
        if (raiz == nil) {
            System.out.println("Árvore rubro-negra vazia.");
            return;
        }
        imprimir(raiz, "", true);
    }

    private void imprimir(No no, String prefixo, boolean ultimo) {
        if (no == nil) return;
        System.out.println(prefixo + (ultimo ? "└── " : "├── ") + no.valor
                + (no.cor == VERMELHO ? " (V)" : " (P)"));
        String proximo = prefixo + (ultimo ? "    " : "│   ");
        boolean temDireita = no.direita != nil;
        if (no.esquerda != nil) imprimir(no.esquerda, proximo, !temDireita);
        if (temDireita) imprimir(no.direita, proximo, true);
    }
}
