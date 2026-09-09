public class ArvoreAVL extends Arvore {

    public ArvoreAVL() {
        super();
    }

    public void inserir(int valor) {
        this.raiz = inserirRecursivo(this.raiz, valor);
    }

    private NoArvore inserirRecursivo(NoArvore no, int valor) {
        if (no == null) {
            return new NoArvore(valor);
        }

        if (valor < no.valor) {
            no.esquerda = inserirRecursivo(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserirRecursivo(no.direita, valor);
        } else {
            return no;
        }

        atualizarAltura(no);
        return balancear(no);
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(this.raiz, valor) != null;
    }

    private NoArvore buscarRecursivo(NoArvore no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor == no.valor) {
            return no;
        }

        if (valor < no.valor) {
            return buscarRecursivo(no.esquerda, valor);
        }

        return buscarRecursivo(no.direita, valor);
    }

    public void remover(int valor) {
        this.raiz = removerRecursivo(this.raiz, valor);
    }

    private NoArvore removerRecursivo(NoArvore no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor < no.valor) {
            no.esquerda = removerRecursivo(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerRecursivo(no.direita, valor);
        } else {
            if (no.esquerda == null) {
                return no.direita;
            }

            if (no.direita == null) {
                return no.esquerda;
            }

            NoArvore sucessor = menorValor(no.direita);
            no.valor = sucessor.valor;
            no.direita = removerRecursivo(no.direita, sucessor.valor);
        }

        atualizarAltura(no);
        return balancear(no);
    }

    private NoArvore menorValor(NoArvore no) {
        NoArvore atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    private NoArvore balancear(NoArvore no) {
        int fb = fatorBalanceamento(no);

        if (fb > 1) {
            if (fatorBalanceamento(no.esquerda) < 0) {
                return rotacaoEsquerdaDireita(no);
            }
            return rotacaoDireita(no);
        }

        if (fb < -1) {
            if (fatorBalanceamento(no.direita) > 0) {
                return rotacaoDireitaEsquerda(no);
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void mostrar() {
        if (this.raiz == null) {
            System.out.println("Árvore AVL vazia.");
            return;
        }

        imprimir(this.raiz, "", true);
    }

    private void imprimir(NoArvore no, String prefixo, boolean ultimo) {
        if (no == null) {
            return;
        }

        String marcador = ultimo ? "└── " : "├── ";
        System.out.println(prefixo + marcador + no.valor + " [" + fatorBalanceamento(no) + "]");

        String novoPrefixo = prefixo + (ultimo ? "    " : "│   ");
        imprimir(no.esquerda, novoPrefixo, false);
        imprimir(no.direita, novoPrefixo, true);
    }
}
