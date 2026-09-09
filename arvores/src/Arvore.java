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
}
