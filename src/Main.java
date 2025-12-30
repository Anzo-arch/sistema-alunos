import java.util.Scanner;

public class Main {

    // Método para classificar a nota do aluno
    static String classificarNota(double nota) {
        if (nota >= 9) {
            return "Excelente";
        } else if (nota >= 7) {
            return "Aprovado";
        } else if (nota >= 5) {
            return "Recuperação";
        } else {
            return "Reprovado";
        }
    }

    // Método para mostrar a mensagem do aluno
    static void mostrarMensagem(String nome, int idade, double nota, String classificacao) {
        System.out.println("Olá, " + nome + "! Sua idade é " + idade +
                " e sua nota é " + nota + ". Resultado: " + classificacao);
    }

    // Método para mostrar a tabuada da nota inteira do aluno
    static void mostrarTabuada(int numero) {
        System.out.println("Tabuada da nota " + numero + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.println(numero + " x " + i + " = " + (numero * i));
        }
        System.out.println(); // linha em branco
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double somaNotas = 0;
        int contAprovados = 0;
        int contRecuperacao = 0;
        int contReprovados = 0;
        int totalAlunos = 0;

        while (true) {
            System.out.println("\nCadastro de Aluno " + (totalAlunos + 1) + ":");

            System.out.print("Digite o nome: ");
            String nome = sc.nextLine();

            System.out.print("Digite a idade: ");
            int idade = sc.nextInt();

            System.out.print("Digite a nota: ");
            double nota = sc.nextDouble();
            sc.nextLine(); // Consumir ENTER restante

            String classificacao = classificarNota(nota);
            mostrarMensagem(nome, idade, nota, classificacao);
            mostrarTabuada((int) nota);

            // Atualiza estatísticas
            somaNotas += nota;
            totalAlunos++;
            if (classificacao.equals("Excelente") || classificacao.equals("Aprovado")) {
                contAprovados++;
            } else if (classificacao.equals("Recuperação")) {
                contRecuperacao++;
            } else {
                contReprovados++;
            }

            // Pergunta se quer continuar
            System.out.print("Deseja cadastrar outro aluno? (s/n): ");
            String resposta = sc.nextLine();
            if (resposta.equalsIgnoreCase("n")) {
                break;
            }
        }

        // Estatísticas finais
        if (totalAlunos > 0) {
            double mediaGeral = somaNotas / totalAlunos;
            System.out.println("\n=== Estatísticas Finais ===");
            System.out.println("Média geral das notas: " + mediaGeral);
            System.out.println("Quantidade de aprovados: " + contAprovados);
            System.out.println("Quantidade em recuperação: " + contRecuperacao);
            System.out.println("Quantidade de reprovados: " + contReprovados);
        } else {
            System.out.println("Nenhum aluno cadastrado.");
        }

        sc.close();
    }
}
