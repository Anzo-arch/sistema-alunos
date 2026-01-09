import java.io.*;
import java.util.*;

public class Main {
    private static final String ARQUIVO = "data/alunos.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Aluno> alunosMap = new HashMap<>();
        Set<String> nomesAlunos = new HashSet<>();

        // Carregar alunos do arquivo, se existir
        carregarAlunos(alunosMap, nomesAlunos);

        int opcao = 0;
        do {
            System.out.println("\n=== Sistema de Alunos ===");
            System.out.println("1 - Inserir Novo Aluno");
            System.out.println("2 - Exibir Resultados");
            System.out.println("3 - Salvar Alunos");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Digite um número válido!");
                sc.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarAluno(sc, alunosMap, nomesAlunos);
                    break;
                case 2:
                    exibirResultados(alunosMap);
                    break;
                case 3:
                    salvarAlunos(alunosMap);
                    break;
                case 4:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }

        } while (opcao != 4);

        sc.close();
    }

    private static void cadastrarAluno(Scanner sc, Map<String, Aluno> alunosMap, Set<String> nomesAlunos) {
        System.out.print("Nome do aluno: ");
        String nome = sc.nextLine();
        if (nomesAlunos.contains(nome)) {
            System.out.println("❌ Aluno já cadastrado!");
            return;
        }

        int tipo = 0, totalAulas = 0;
        while (true) {
            try {
                System.out.print("Tipo (1=Presencial,2=Online): ");
                tipo = sc.nextInt();
                sc.nextLine();
                if (tipo == 1 || tipo == 2) break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Digite 1 ou 2!");
                sc.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Total de aulas: ");
                totalAulas = sc.nextInt();
                sc.nextLine();
                if (totalAulas > 0) break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Digite um número válido!");
                sc.nextLine();
            }
        }

        Aluno aluno = (tipo == 1) ? new AlunoPresencial(nome, totalAulas) : new AlunoOnline(nome, totalAulas);

        int qtdNotas = 0;
        while (true) {
            try {
                System.out.print("Quantas notas? ");
                qtdNotas = sc.nextInt();
                sc.nextLine();
                if (qtdNotas > 0) break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Digite um número válido!");
                sc.nextLine();
            }
        }

        for (int i = 0; i < qtdNotas; i++) {
            while (true) {
                try {
                    System.out.print("Nota " + (i + 1) + ": ");
                    double nota = sc.nextDouble();
                    sc.nextLine();
                    if (nota >= 0 && nota <= 10) {
                        aluno.adicionarNota(nota);
                        break;
                    } else {
                        System.out.println("❌ Nota entre 0 e 10!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Digite um número válido!");
                    sc.nextLine();
                }
            }
        }

        if (aluno instanceof AlunoPresencial) {
            while (true) {
                try {
                    System.out.print("Faltas: ");
                    int faltas = sc.nextInt();
                    sc.nextLine();
                    if (faltas >= 0 && faltas <= aluno.totalAulas) {
                        ((AlunoPresencial) aluno).registrarFaltas(faltas);
                        break;
                    } else {
                        System.out.println("❌ Faltas inválidas!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Digite um número inteiro!");
                    sc.nextLine();
                }
            }
        }

        nomesAlunos.add(nome);
        alunosMap.put(nome, aluno);
        System.out.println("✅ Aluno cadastrado com sucesso!");
    }

    private static void exibirResultados(Map<String, Aluno> alunosMap) {
        if (alunosMap.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado!");
            return;
        }
        System.out.println("\n=== Resultados ===");
        for (Aluno a : alunosMap.values()) {
            System.out.println(a.verificarResultado());
        }
    }

    private static void salvarAlunos(Map<String, Aluno> alunosMap) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Aluno a : alunosMap.values()) {
                bw.write(a.toString());
                bw.newLine();
            }
            System.out.println("✅ Alunos salvos em " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static void carregarAlunos(Map<String, Aluno> alunosMap, Set<String> nomesAlunos) {
        File f = new File(ARQUIVO);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Formato: nome;totalAulas;faltas;[notas];Tipo
                String[] partes = linha.split(";");
                String nome = partes[0];
                int totalAulas = Integer.parseInt(partes[1]);
                int faltas = Integer.parseInt(partes[2]);
                String notasStr = partes[3].replaceAll("[\\[\\]\\s]", "");
                String tipo = partes[4];

                Aluno aluno = tipo.equals("AlunoPresencial") ? new AlunoPresencial(nome, totalAulas)
                        : new AlunoOnline(nome, totalAulas);

                ((Aluno) aluno).registrarFaltas(faltas);
                if (!notasStr.isEmpty()) {
                    for (String n : notasStr.split(",")) {
                        ((Aluno) aluno).adicionarNota(Double.parseDouble(n));
                    }
                }

                nomesAlunos.add(nome);
                alunosMap.put(nome, aluno);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("❌ Erro ao carregar alunos: " + e.getMessage());
        }
    }
}
