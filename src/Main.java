import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Map para armazenar alunos pelo nome
        Map<String, Avaliavel> alunosMap = new HashMap<>();
        // Set para evitar nomes duplicados
        Set<String> nomesAlunos = new HashSet<>();

        int n = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Quantos alunos deseja cadastrar? ");
                n = sc.nextInt();
                sc.nextLine();
                if (n > 0) {
                    entradaValida = true;
                } else {
                    System.out.println("❌ Digite um número maior que 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro!");
                sc.nextLine();
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.print("\nNome do aluno: ");
            String nome = sc.nextLine();

            if (nomesAlunos.contains(nome)) {
                System.out.println("❌ Aluno já cadastrado! Digite outro nome.");
                i--; // repete essa iteração
                continue;
            }

            int tipo = 0;
            boolean tipoValido = false;
            while (!tipoValido) {
                try {
                    System.out.print("Tipo do aluno (1 = Presencial, 2 = Online): ");
                    tipo = sc.nextInt();
                    sc.nextLine();
                    if (tipo == 1 || tipo == 2) {
                        tipoValido = true;
                    } else {
                        System.out.println("❌ Digite 1 ou 2!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Entrada inválida. Digite 1 ou 2!");
                    sc.nextLine();
                }
            }

            int totalAulas = 0;
            boolean aulasValidas = false;
            while (!aulasValidas) {
                try {
                    System.out.print("Total de aulas: ");
                    totalAulas = sc.nextInt();
                    sc.nextLine();
                    if (totalAulas > 0) {
                        aulasValidas = true;
                    } else {
                        System.out.println("❌ Digite um número maior que 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Entrada inválida. Digite um número inteiro!");
                    sc.nextLine();
                }
            }

            Avaliavel aluno;
            if (tipo == 1) {
                aluno = new AlunoPresencial(nome, totalAulas);
            } else {
                aluno = new AlunoOnline(nome, totalAulas);
            }

            int qtdNotas = 0;
            boolean notasValidas = false;
            while (!notasValidas) {
                try {
                    System.out.print("Quantas notas este aluno tem? ");
                    qtdNotas = sc.nextInt();
                    sc.nextLine();
                    if (qtdNotas > 0) {
                        notasValidas = true;
                    } else {
                        System.out.println("❌ Digite um número maior que 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Entrada inválida. Digite um número inteiro!");
                    sc.nextLine();
                }
            }

            for (int j = 0; j < qtdNotas; j++) {
                boolean notaValida = false;
                while (!notaValida) {
                    try {
                        System.out.print("Nota " + (j + 1) + ": ");
                        double nota = sc.nextDouble();
                        sc.nextLine();
                        if (nota >= 0 && nota <= 10) {
                            if (aluno instanceof Aluno) {
                                ((Aluno) aluno).adicionarNota(nota);
                            }
                            notaValida = true;
                        } else {
                            System.out.println("❌ Nota inválida. Digite entre 0 e 10.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Entrada inválida. Digite um número válido!");
                        sc.nextLine();
                    }
                }
            }

            if (aluno instanceof AlunoPresencial) {
                boolean faltasValidas = false;
                while (!faltasValidas) {
                    try {
                        System.out.print("Quantas faltas? ");
                        int faltas = sc.nextInt();
                        sc.nextLine();
                        if (faltas >= 0 && faltas <= totalAulas) {
                            ((AlunoPresencial) aluno).registrarFaltas(faltas);
                            faltasValidas = true;
                        } else {
                            System.out.println("❌ Faltas inválidas. Deve estar entre 0 e " + totalAulas);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Entrada inválida. Digite um número inteiro!");
                        sc.nextLine();
                    }
                }
            }

            // Adiciona ao Map e Set
            nomesAlunos.add(nome);
            alunosMap.put(nome, aluno);
        }

        // Exibir resultados
        System.out.println("\n=== Resultados ===");
        for (String nome : alunosMap.keySet()) {
            Avaliavel a = alunosMap.get(nome);
            System.out.println(a.verificarResultado());
        }

        sc.close();
    }
}
