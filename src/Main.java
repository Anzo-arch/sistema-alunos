import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static double calcularMedia(ArrayList<Double> notas) {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.size();
    }

    static String verificarResultado(double media) {
        return (media >= 7) ? "Aprovado" : "Reprovado";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<ArrayList<Double>> todasNotas = new ArrayList<>();

        System.out.print("Quantos alunos deseja informar? ");
        int totalAlunos = sc.nextInt();
        sc.nextLine(); // limpar buffer

        System.out.print("Quantas notas por aluno? ");
        int notasPorAluno = sc.nextInt();
        sc.nextLine(); // limpar buffer

        for (int i = 0; i < totalAlunos; i++) {
            System.out.print("Digite o nome do aluno " + (i + 1) + ": ");
            String nome = sc.nextLine();
            nomes.add(nome);

            ArrayList<Double> notas = new ArrayList<>();
            for (int j = 0; j < notasPorAluno; j++) {
                double notaAleatoria = rand.nextInt(11); // 0 a 10
                notas.add(notaAleatoria);
            }
            todasNotas.add(notas);
        }

        // Mostrar resultados
        for (int i = 0; i < nomes.size(); i++) {
            double media = calcularMedia(todasNotas.get(i));
            String resultado = verificarResultado(media);

            System.out.println("\nAluno: " + nomes.get(i));
            System.out.println("Notas: " + todasNotas.get(i));
            System.out.println("Média: " + media);
            System.out.println("Resultado: " + resultado);
        }

        sc.close();
    }
}
