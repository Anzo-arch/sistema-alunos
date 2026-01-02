import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Aluno> alunos = new ArrayList<>();

        // Total de alunos
        int totalAlunos = 0;
        while (true) {
            System.out.print("Quantos alunos deseja cadastrar? ");
            if (sc.hasNextInt()) {
                totalAlunos = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Digite um número inteiro!");
                sc.nextLine();
            }
        }

        for (int i = 0; i < totalAlunos; i++) {
            System.out.print("\nDigite o nome do aluno " + (i + 1) + ": ");
            String nome = sc.nextLine();
            Aluno aluno = new Aluno();
            aluno.setNome(nome);

            // Número de aulas e faltas
            int totalAulas = 0;
            while (true) {
                System.out.print("Total de aulas no período: ");
                if (sc.hasNextInt()) {
                    totalAulas = sc.nextInt();
                    sc.nextLine();
                    aluno.setTotalAulas(totalAulas);
                    break;
                } else {
                    System.out.println("Digite um número inteiro válido!");
                    sc.nextLine();
                }
            }

            int faltas = 0;
            while (true) {
                System.out.print("Número de faltas do aluno: ");
                if (sc.hasNextInt()) {
                    faltas = sc.nextInt();
                    sc.nextLine();
                    if (faltas <= totalAulas) {
                        aluno.setFaltas(faltas);
                        break;
                    } else {
                        System.out.println("Faltas não podem ser maiores que o total de aulas!");
                    }
                } else {
                    System.out.println("Digite um número inteiro válido!");
                    sc.nextLine();
                }
            }

            // Notas do aluno
            int totalNotas = 0;
            while (true) {
                System.out.print("Quantas notas esse aluno tem? ");
                if (sc.hasNextInt()) {
                    totalNotas = sc.nextInt();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("Digite um número inteiro!");
                    sc.nextLine();
                }
            }

            for (int j = 0; j < totalNotas; j++) {
                while (true) {
                    System.out.print("Digite a nota " + (j + 1) + ": ");
                    if (sc.hasNextDouble()) {
                        double nota = sc.nextDouble();
                        sc.nextLine();
                        aluno.adicionarNota(nota);
                        break;
                    } else {
                        System.out.println("Digite um número válido para a nota!");
                        sc.nextLine();
                    }
                }
            }

            alunos.add(aluno);
        }

        // Mostrar resultados
        for (Aluno aluno : alunos) {
            System.out.println("\nAluno: " + aluno.getNome());
            System.out.println("Notas: " + aluno.getNotas());
            System.out.println("Média: " + aluno.calcularMedia());
            System.out.println("Presença: " + aluno.calcularPresenca() + "%");
            System.out.println("Resultado: " + aluno.verificarResultado());
        }

        sc.close();
    }
}
