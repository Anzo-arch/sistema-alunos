import java.util.Scanner;

public class Main {

    static double calcularMedia(double[] notas) {
        double soma = 0;
        for (double nota : notas) { // for-each para somar as notas
            soma += nota;
        }
        return soma / notas.length;
    }

    static String verificarResultado(double media) {
        return (media >= 7) ? "Aprovado" : "Reprovado";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Quantos alunos deseja informar? ");
        int totalAlunos = sc.nextInt();
        System.out.print("Quantas notas por aluno? ");
        int totalNotas = sc.nextInt();
        sc.nextLine(); // Limpar buffer do teclado

        String[] nomes = new String[totalAlunos];
        double[][] notas = new double[totalAlunos][totalNotas];

        // Ler nomes e notas
        for (int i = 0; i < totalAlunos; i++) {
            System.out.print("Digite o nome do aluno " + (i + 1) + ": ");
            nomes[i] = sc.nextLine();

            for (int j = 0; j < totalNotas; j++) {
                System.out.print("Digite a nota " + (j + 1) + " de " + nomes[i] + ": ");
                notas[i][j] = sc.nextDouble();
            }
            sc.nextLine(); // Limpar buffer
        }

        // Mostrar resultados usando for-each
        int alunoIndex = 0;
        for (String nome : nomes) {
            double media = calcularMedia(notas[alunoIndex]);
            String resultado = verificarResultado(media);

            System.out.println("\nAluno: " + nome);
            System.out.println("Média: " + media);
            System.out.println("Resultado: " + resultado);

            alunoIndex++;
        }

        sc.close();
    }
}
