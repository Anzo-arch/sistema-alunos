import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lista de Avaliavel, não de Aluno
        ArrayList<Avaliavel> alunos = new ArrayList<>();

        System.out.print("Quantos alunos deseja cadastrar? ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Nome do aluno: ");
            String nome = sc.nextLine();

            System.out.print("Tipo do aluno (1 = Presencial, 2 = Online): ");
            int tipo = sc.nextInt();
            sc.nextLine();

            System.out.print("Total de aulas: ");
            int totalAulas = sc.nextInt();
            sc.nextLine();

            // Declarando a variável como Avaliavel
            Avaliavel aluno;

            if (tipo == 1) {
                aluno = new AlunoPresencial(nome, totalAulas);
            } else {
                aluno = new AlunoOnline(nome, totalAulas);
            }

            // Cadastro de notas
            System.out.print("Quantas notas este aluno tem? ");
            int qtdNotas = sc.nextInt();
            sc.nextLine();

            for (int j = 0; j < qtdNotas; j++) {
                System.out.print("Nota " + (j + 1) + ": ");
                double nota = sc.nextDouble();
                sc.nextLine();

                // Método da classe Aluno, mas aluno é Avaliavel
                if (aluno instanceof Aluno) {
                    ((Aluno) aluno).adicionarNota(nota);
                }
            }

            // Registrar faltas apenas para presencial
            if (aluno instanceof AlunoPresencial) {
                System.out.print("Quantas faltas? ");
                int faltas = sc.nextInt();
                sc.nextLine();

                ((AlunoPresencial) aluno).registrarFaltas(faltas);
            }

            // Adiciona na lista
            alunos.add(aluno);
        }

        // Mostrar resultados usando polimorfismo
        System.out.println("\n=== Resultados ===");
        for (Avaliavel a : alunos) {
            System.out.println(a.verificarResultado());
        }

        sc.close();
    }
}
