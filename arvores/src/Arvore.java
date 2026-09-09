public class Arvore {
    private Folha folha;
    private Arvore esquerda;
    private Arvore direita;


    //construtores para arvore vazia e com folha
    public Arvore (){
        this.folha = null;
        this.esquerda = null;
        this.direita = null;
    }

    public Arvore (Folha folha){
        this.folha = folha;
        this.esquerda = null;
        this.direita = null;
    }

    public boolean isEmpty  (){
        return this.folha == null;
    }

    public void inserir (Folha novo){
        // se vazio mas ja possui nó raiz ele vai ser igual novo
        if(isEmpty()){
            this.folha = novo;
        }
        // se não cria arvore
        else {
            Arvore novaArvore = new Arvore(novo);

            // se valor menor que raiz vai para esquerda
            if (novo.getValor() < this.folha.getValor()){

                //se esquerda for null cria nova esquerda
                if (this.esquerda == null){
                    this.esquerda = novaArvore;

                    System.out.println("Folha: " + novo.getValor() + " a esquerda de Folha : " + this.folha.getValor());
                }

                // se já existir insere valor novo na esquerda
                else {
                     this.esquerda.inserir(novo);
                }
            }

            //se o valor é maior que a raiz vai para a direita
            else if (novo.getValor() > this.folha.getValor()) {

                //se direita for null direita cria nova dreita
                if (this.direita == null) {
                    this.direita = novaArvore;

                    System.out.println("Folha: " + novo.getValor() + " a direita de Folha : " + this.folha.getValor());
                }

                //se já existir direita insere valor na direita
                else {
                    this.direita.inserir(novo);
                }

            }
        }


    }

    public Arvore remover(int valor) {
        if (isEmpty()) {
            return this; //não faz nada
        }

        if(valor < this.folha.getValor()){
            if(this.esquerda != null){
                this.esquerda = this.esquerda.remover(valor);
            }
        }
        else if (valor > this.folha.getValor()){
            if(this.direita != null){
                this.direita = this.direita.remover(valor);
            }
        }

        else {
            // achou nó a remover

            // 1: sem filho (folha)

            if (this.esquerda == null && this.direita == null){
                return null;
            }

            // 2: so um filho
            if (this.esquerda == null) {
                return this.direita;
            }
            if (this.direita == null){
                return this.esquerda;
            }

            //3: nó com dois filhos
            //pega o menor valor da subárvore a diretia (sucesso no inOrder)
            Folha sucessor = this.direita.menorValor();
            this.folha = sucessor;
            this.direita = this.direita.remover(sucessor.getValor());
        }

        return this;

    }

    private Folha menorValor() {
        if (this.esquerda == null) {
            return this.folha;
        }
        return this.esquerda.menorValor();
    }

    public boolean buscar(int valor){
        if(isEmpty()) return false;
        if(this.folha.getValor() == valor) return true;
        if(valor < this.folha.getValor()){
            return this.esquerda != null && this.esquerda.buscar(valor);
        }
        else {
            return this.direita != null && this.direita.buscar(valor);
        }
    }

    public void inOrder () {
        if(isEmpty()) return;
        if(this.esquerda != null){
            esquerda.inOrder();
        }
            System.out.print(folha.getValor() +  " ");

        if(this.direita != null){
            direita.inOrder();
        }
    }
}
