public class Arvore {
    protected static class NoArvore {
        int valor;
        NoArvore esquerda;
        NoArvore direita;
        int altura;

        NoArvore(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
            this.altura = 0;
        }
    }

    protected NoArvore raiz;

    public Arvore() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    protected int altura(NoArvore no) {
        if (no == null) {
            return -1;
        }
        return no.altura;
    }

    protected int fatorBalanceamento(NoArvore no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    protected void atualizarAltura(NoArvore no) {
        if (no != null) {
            no.altura = Math.max(altura(no.esquerda), altura(no.direita)) + 1;
        }
    }

    protected NoArvore rotacaoDireita(NoArvore y) {
        NoArvore x = y.esquerda;
        y.esquerda = x.direita;
        x.direita = y;

        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    protected NoArvore rotacaoEsquerda(NoArvore y) {
        NoArvore x = y.direita;
        y.direita = x.esquerda;
        x.esquerda = y;

        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    protected NoArvore rotacaoEsquerdaDireita(NoArvore z) {
        z.esquerda = rotacaoEsquerda(z.esquerda);
        return rotacaoDireita(z);
    }

    protected NoArvore rotacaoDireitaEsquerda(NoArvore z) {
        z.direita = rotacaoDireita(z.direita);
        return rotacaoEsquerda(z);
    }
}
