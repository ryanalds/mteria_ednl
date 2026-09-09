import java.util.Scanner;

public class TesteArvore {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArvoreAVL arvore = new ArvoreAVL();
        int opcao;

        do {
            System.out.println("\n=== Árvore AVL ===");
            System.out.println("1 - Incluir nó");
            System.out.println("2 - Remover nó");
            System.out.println("3 - Buscar nó");
            System.out.println("4 - Mostrar árvore");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            while (!entrada.hasNextInt()) {
                System.out.print("Valor inválido. Digite um número: ");
                entrada.next();
            }

            opcao = entrada.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a incluir: ");
                    int valorInsercao = entrada.nextInt();
                    arvore.inserir(valorInsercao);
                    System.out.println("Valor " + valorInsercao + " incluído com sucesso.");
                    break;
                case 2:
                    System.out.print("Digite o valor a remover: ");
                    int valorRemocao = entrada.nextInt();
                    if (arvore.buscar(valorRemocao)) {
                        arvore.remover(valorRemocao);
                        System.out.println("Valor " + valorRemocao + " removido. ");
                    } else {
                        System.out.println("Valor " + valorRemocao + " não encontrado.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o valor a buscar: ");
                    int valorBusca = entrada.nextInt();
                    System.out.println(arvore.buscar(valorBusca)
                            ? "Valor " + valorBusca + " encontrado."
                            : "Valor " + valorBusca + " não encontrado.");
                    break;
                case 4:
                    arvore.mostrar();
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        entrada.close();
    }
}