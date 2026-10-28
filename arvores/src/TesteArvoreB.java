
import java.util.Scanner;

public class TesteArvoreB {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArvoreB arvore = new ArvoreB();
        int opcao;

        do {
            System.out.println("\n=== Árvore B ===");
            System.out.println("1 - Incluir nó");
            System.out.println("2 - Remover nó");
            System.out.println("3 - Buscar nó");
            System.out.println("4 - Mostrar árvore");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro(entrada);

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a incluir: ");
                    int inclusao = lerInteiro(entrada);
                    boolean jaExistia = arvore.buscar(inclusao);
                    arvore.inserir(inclusao);
                    System.out.println(jaExistia ? "O valor já existe na árvore."
                            : "Valor " + inclusao + " incluído com sucesso.");
                    break;
                case 2:
                    System.out.print("Digite o valor a remover: ");
                    int remocao = lerInteiro(entrada);
                    if (arvore.buscar(remocao)) {
                        arvore.remover(remocao);
                        System.out.println("Valor " + remocao + " removido.");
                    } else {
                        System.out.println("Valor " + remocao + " não encontrado.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o valor a buscar: ");
                    int busca = lerInteiro(entrada);
                    System.out.println(arvore.buscar(busca)
                            ? "Valor " + busca + " encontrado."
                            : "Valor " + busca + " não encontrado.");
                    break;
                case 4:
                    arvore.mostrar();
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        entrada.close();
    }

    private static int lerInteiro(Scanner entrada) {
        while (!entrada.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número inteiro: ");
            entrada.next();
        }
        return entrada.nextInt();
    }
}
