
import java.util.ArrayList;
import java.util.List;


public class ArvoreB {
    private static final int GRAU_MINIMO_PADRAO = 2;

    private static class No {
        final int[] chaves;
        final No[] filhos;
        int quantidade;
        boolean folha;

        No(int grauMinimo, boolean folha) {
            chaves = new int[2 * grauMinimo - 1];
            filhos = new No[2 * grauMinimo];
            this.folha = folha;
        }
    }

    private final int grauMinimo;
    private No raiz;

    public ArvoreB() {
        this(GRAU_MINIMO_PADRAO);
    }

    public ArvoreB(int grauMinimo) {
        if (grauMinimo < 2) {
            throw new IllegalArgumentException("O grau mínimo deve ser pelo menos 2.");
        }
        this.grauMinimo = grauMinimo;
        raiz = new No(grauMinimo, true);
    }

    public boolean isEmpty() {
        return raiz.quantidade == 0 && raiz.folha;
    }

    public boolean buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(No no, int valor) {
        int i = 0;
        while (i < no.quantidade && valor > no.chaves[i]) i++;
        if (i < no.quantidade && valor == no.chaves[i]) return true;
        return !no.folha && buscar(no.filhos[i], valor);
    }

    public void inserir(int valor) {
        if (buscar(valor)) return; // Valores duplicados não são armazenados.
        if (raiz.quantidade == 2 * grauMinimo - 1) {
            No novaRaiz = new No(grauMinimo, false);
            novaRaiz.filhos[0] = raiz;
            dividirFilho(novaRaiz, 0);
            raiz = novaRaiz;
        }
        inserirNaoCheio(raiz, valor);
    }

    private void inserirNaoCheio(No no, int valor) {
        int i = no.quantidade - 1;
        if (no.folha) {
            while (i >= 0 && valor < no.chaves[i]) {
                no.chaves[i + 1] = no.chaves[i--];
            }
            no.chaves[i + 1] = valor;
            no.quantidade++;
            return;
        }
        while (i >= 0 && valor < no.chaves[i]) i--;
        i++;
        if (no.filhos[i].quantidade == 2 * grauMinimo - 1) {
            dividirFilho(no, i);
            if (valor > no.chaves[i]) i++;
        }
        inserirNaoCheio(no.filhos[i], valor);
    }

    private void dividirFilho(No pai, int indice) {
        No cheio = pai.filhos[indice];
        No novo = new No(grauMinimo, cheio.folha);
        novo.quantidade = grauMinimo - 1;
        for (int j = 0; j < grauMinimo - 1; j++)
            novo.chaves[j] = cheio.chaves[j + grauMinimo];
        if (!cheio.folha) {
            for (int j = 0; j < grauMinimo; j++)
                novo.filhos[j] = cheio.filhos[j + grauMinimo];
        }
        cheio.quantidade = grauMinimo - 1;
        for (int j = pai.quantidade; j >= indice + 1; j--)
            pai.filhos[j + 1] = pai.filhos[j];
        pai.filhos[indice + 1] = novo;
        for (int j = pai.quantidade - 1; j >= indice; j--)
            pai.chaves[j + 1] = pai.chaves[j];
        pai.chaves[indice] = cheio.chaves[grauMinimo - 1];
        pai.quantidade++;
    }

    public void remover(int valor) {
        if (!buscar(valor)) return;
        remover(raiz, valor);
        if (raiz.quantidade == 0) {
            raiz = raiz.folha ? new No(grauMinimo, true) : raiz.filhos[0];
        }
    }

    private void remover(No no, int valor) {
        int indice = indiceChave(no, valor);
        if (indice < no.quantidade && no.chaves[indice] == valor) {
            if (no.folha) removerDaFolha(no, indice);
            else removerDeNoInterno(no, indice);
            return;
        }
        if (no.folha) return;
        boolean ultimaSubarvore = indice == no.quantidade;
        if (no.filhos[indice].quantidade < grauMinimo) preencherFilho(no, indice);
        if (ultimaSubarvore && indice > no.quantidade)
            remover(no.filhos[indice - 1], valor);
        else
            remover(no.filhos[indice], valor);
    }

    private int indiceChave(No no, int valor) {
        int i = 0;
        while (i < no.quantidade && no.chaves[i] < valor) i++;
        return i;
    }

    private void removerDaFolha(No no, int indice) {
        for (int i = indice + 1; i < no.quantidade; i++) no.chaves[i - 1] = no.chaves[i];
        no.quantidade--;
    }

    private void removerDeNoInterno(No no, int indice) {
        int valor = no.chaves[indice];
        if (no.filhos[indice].quantidade >= grauMinimo) {
            int antecessor = obterAntecessor(no, indice);
            no.chaves[indice] = antecessor;
            remover(no.filhos[indice], antecessor);
        } else if (no.filhos[indice + 1].quantidade >= grauMinimo) {
            int sucessor = obterSucessor(no, indice);
            no.chaves[indice] = sucessor;
            remover(no.filhos[indice + 1], sucessor);
        } else {
            juntarFilhos(no, indice);
            remover(no.filhos[indice], valor);
        }
    }

    private int obterAntecessor(No no, int indice) {
        No atual = no.filhos[indice];
        while (!atual.folha) atual = atual.filhos[atual.quantidade];
        return atual.chaves[atual.quantidade - 1];
    }

    private int obterSucessor(No no, int indice) {
        No atual = no.filhos[indice + 1];
        while (!atual.folha) atual = atual.filhos[0];
        return atual.chaves[0];
    }

    private void preencherFilho(No pai, int indice) {
        if (indice > 0 && pai.filhos[indice - 1].quantidade >= grauMinimo)
            emprestarAnterior(pai, indice);
        else if (indice < pai.quantidade && pai.filhos[indice + 1].quantidade >= grauMinimo)
            emprestarProximo(pai, indice);
        else if (indice < pai.quantidade) juntarFilhos(pai, indice);
        else juntarFilhos(pai, indice - 1);
    }

    private void emprestarAnterior(No pai, int indice) {
        No filho = pai.filhos[indice], irmao = pai.filhos[indice - 1];
        for (int i = filho.quantidade - 1; i >= 0; i--) filho.chaves[i + 1] = filho.chaves[i];
        if (!filho.folha) for (int i = filho.quantidade; i >= 0; i--) filho.filhos[i + 1] = filho.filhos[i];
        filho.chaves[0] = pai.chaves[indice - 1];
        if (!filho.folha) filho.filhos[0] = irmao.filhos[irmao.quantidade];
        pai.chaves[indice - 1] = irmao.chaves[irmao.quantidade - 1];
        filho.quantidade++;
        irmao.quantidade--;
    }

    private void emprestarProximo(No pai, int indice) {
        No filho = pai.filhos[indice], irmao = pai.filhos[indice + 1];
        filho.chaves[filho.quantidade] = pai.chaves[indice];
        if (!filho.folha) filho.filhos[filho.quantidade + 1] = irmao.filhos[0];
        pai.chaves[indice] = irmao.chaves[0];
        for (int i = 1; i < irmao.quantidade; i++) irmao.chaves[i - 1] = irmao.chaves[i];
        if (!irmao.folha) for (int i = 1; i <= irmao.quantidade; i++) irmao.filhos[i - 1] = irmao.filhos[i];
        filho.quantidade++;
        irmao.quantidade--;
    }

    private void juntarFilhos(No pai, int indice) {
        No filho = pai.filhos[indice], irmao = pai.filhos[indice + 1];
        filho.chaves[grauMinimo - 1] = pai.chaves[indice];
        for (int i = 0; i < irmao.quantidade; i++) filho.chaves[i + grauMinimo] = irmao.chaves[i];
        if (!filho.folha) for (int i = 0; i <= irmao.quantidade; i++) filho.filhos[i + grauMinimo] = irmao.filhos[i];
        filho.quantidade += irmao.quantidade + 1;
        for (int i = indice + 1; i < pai.quantidade; i++) pai.chaves[i - 1] = pai.chaves[i];
        for (int i = indice + 2; i <= pai.quantidade; i++) pai.filhos[i - 1] = pai.filhos[i];
        pai.quantidade--;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("Árvore B vazia.");
            return;
        }
        mostrar(raiz, 0);
    }

    private void mostrar(No no, int nivel) {
        System.out.println("  ".repeat(nivel) + ArraysToString(no.chaves, no.quantidade));
        for (int i = 0; i <= no.quantidade && !no.folha; i++) mostrar(no.filhos[i], nivel + 1);
    }

    private String ArraysToString(int[] chaves, int quantidade) {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) lista.add(chaves[i]);
        return lista.toString();
    }
}
