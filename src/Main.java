import java.io.*;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final String ARQUIVO = "alunos.dat";
    private static Map<String, Aluno> alunosMap = new TreeMap<>();

    public static void main(String[] args) {
        carregarAlunos();

        while (true) {
            System.out.println("\n=== Sistema de Alunos Avançado ===");
            System.out.println("1 - Inserir aluno");
            System.out.println("2 - Visualizar alunos");
            System.out.println("3 - Remover aluno");
            System.out.println("4 - Ordenar e exibir aprovados/reprovados");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInt();

            switch (opcao) {
                case 1 -> inserirAluno();
                case 2 -> exibirAlunos();
                case 3 -> removerAluno();
                case 4 -> exibirResultadosOrdenados();
                case 5 -> {
                    salvarAlunos();
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void inserirAluno() {
        System.out.print("Nome do aluno: ");
        String nome = sc.nextLine();
        if (alunosMap.containsKey(nome)) {
            System.out.println("Aluno já cadastrado!");
            return;
        }

        System.out.print("Total de aulas: ");
        int totalAulas = lerInt();

        System.out.print("Tipo (1 = Presencial, 2 = Online): ");
        int tipo = lerInt();

        Aluno aluno = tipo == 1 ? new AlunoPresencial(nome, totalAulas) : new AlunoOnline(nome, totalAulas);

        System.out.print("Quantas notas este aluno possui? ");
        int qtdNotas = lerInt();

        for (int i = 0; i < qtdNotas; i++) {
            System.out.print("Nota " + (i + 1) + ": ");
            double nota = lerDouble();
            aluno.adicionarNota(nota);
        }

        if (aluno instanceof AlunoPresencial) {
            System.out.print("Quantas faltas? ");
            int faltas = lerInt();
            ((AlunoPresencial) aluno).registrarFaltas(faltas);
        }

        alunosMap.put(nome, aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void exibirAlunos() {
        if (alunosMap.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado!");
            return;
        }
        alunosMap.values().forEach(a -> System.out.println(a.getNome()));
    }

    private static void removerAluno() {
        System.out.print("Nome do aluno a remover: ");
        String nome = sc.nextLine();
        if (alunosMap.remove(nome) != null) {
            System.out.println("Aluno removido com sucesso!");
        } else {
            System.out.println("Aluno não encontrado!");
        }
    }

    private static void exibirResultadosOrdenados() {
        if (alunosMap.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado!");
            return;
        }

        System.out.println("\n=== Resultados Ordenados ===");
        alunosMap.values()
                 .stream()
                 .sorted(Comparator.comparing(Aluno::getNome))
                 .forEach(a -> System.out.println(a.verificarResultado()));
    }

    private static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido! Digite um número inteiro: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido! Digite um número decimal: ");
            }
        }
    }

    private static void salvarAlunos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(alunosMap);
            System.out.println("Alunos salvos em " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void carregarAlunos() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            alunosMap = (Map<String, Aluno>) ois.readObject();
            System.out.println("Alunos carregados de " + ARQUIVO);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar alunos: " + e.getMessage());
        }
    }
}
