import java.util.*;
import java.io.*;

public class Main {
    private static final String ARQUIVO = "alunos.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Aluno> alunosMap = carregarAlunos();

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== Sistema de Alunos ===");
            System.out.println("1 - Inserir Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Listar Aprovados");
            System.out.println("4 - Listar Reprovados");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInt(sc);

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do aluno: ");
                    String nome = sc.nextLine();

                    if (alunosMap.containsKey(nome)) {
                        System.out.println("Aluno já cadastrado!");
                        break;
                    }

                    TipoAluno tipo = null;
                    while (tipo == null) {
                        System.out.print("Tipo (1 = Presencial, 2 = Online): ");
                        int t = lerInt(sc);
                        if (t == 1) tipo = TipoAluno.PRESENCIAL;
                        else if (t == 2) tipo = TipoAluno.ONLINE;
                        else System.out.println("Opção inválida!");
                    }

                    System.out.print("Total de aulas: ");
                    int totalAulas = lerInt(sc);

                    Aluno aluno = (tipo == TipoAluno.PRESENCIAL) ?
                            new AlunoPresencial(nome, totalAulas) :
                            new AlunoOnline(nome, totalAulas);

                    System.out.print("Quantas notas este aluno tem? ");
                    int qtdNotas = lerInt(sc);

                    for (int i = 0; i < qtdNotas; i++) {
                        System.out.print("Nota " + (i + 1) + ": ");
                        double nota = lerDouble(sc);
                        aluno.adicionarNota(nota);
                    }

                    if (tipo == TipoAluno.PRESENCIAL) {
                        System.out.print("Quantas faltas? ");
                        int faltas = lerInt(sc);
                        ((AlunoPresencial) aluno).registrarFaltas(faltas);
                    }

                    alunosMap.put(nome, aluno);
                    salvarAlunos(alunosMap);
                    System.out.println("Aluno cadastrado com sucesso!");
                }

                case 2 -> exibirTodos(alunosMap);
                case 3 -> exibirPorResultado(alunosMap, true);
                case 4 -> exibirPorResultado(alunosMap, false);
                case 5 -> {
                    rodando = false;
                    System.out.println("Encerrando sistema...");
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }

    // ---------------- Funções Auxiliares ----------------

    private static int lerInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }

    private static double lerDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite uma nota válida: ");
            }
        }
    }

    private static void exibirTodos(Map<String, Aluno> alunosMap) {
        System.out.println("\n=== Todos os Alunos ===");
        alunosMap.values().stream()
                .sorted(Comparator.comparing(a -> a.nome))
                .forEach(a -> System.out.println(a.verificarResultado()));
    }

    private static void exibirPorResultado(Map<String, Aluno> alunosMap, boolean aprovados) {
        System.out.println(aprovados ? "\n=== Aprovados ===" : "\n=== Reprovados ===");
        alunosMap.values().stream()
                .filter(a -> (a.calcularMedia() >= 7 &&
                        (a instanceof AlunoPresencial ? ((AlunoPresencial) a).calcularPresenca() >= 75 : true)) == aprovados)
                .sorted(Comparator.comparing(a -> a.nome))
                .forEach(a -> System.out.println(a.verificarResultado()));
    }

    private static void salvarAlunos(Map<String, Aluno> alunosMap) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Aluno a : alunosMap.values()) {
                String notas = String.join(",", a.notas.stream().map(String::valueOf).toArray(String[]::new));
                String tipo = (a instanceof AlunoPresencial) ? "PRESENCIAL" : "ONLINE";
                int faltas = (a instanceof AlunoPresencial) ? ((AlunoPresencial) a).faltas : 0;
                pw.println(a.nome + ";" + a.totalAulas + ";" + faltas + ";" + notas + ";" + tipo);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    private static Map<String, Aluno> carregarAlunos() {
        Map<String, Aluno> map = new HashMap<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return map;

        try (Scanner sc = new Scanner(arquivo)) {
            while (sc.hasNextLine()) {
                String[] dados = sc.nextLine().split(";");
                String nome = dados[0];
                int totalAulas = Integer.parseInt(dados[1]);
                int faltas = Integer.parseInt(dados[2]);
                String[] notasArray = dados[3].split(",");
                String tipo = dados[4];

                Aluno aluno = tipo.equals("PRESENCIAL") ?
                        new AlunoPresencial(nome, totalAulas) :
                        new AlunoOnline(nome, totalAulas);

                for (String n : notasArray) {
                    if (!n.isEmpty()) aluno.adicionarNota(Double.parseDouble(n));
                }

                if (aluno instanceof AlunoPresencial) ((AlunoPresencial) aluno).registrarFaltas(faltas);

                map.put(nome, aluno);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar alunos: " + e.getMessage());
        }
        return map;
    }
}
