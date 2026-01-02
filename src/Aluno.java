import java.util.ArrayList;

public class Aluno {
    // Encapsulamento
    private String nome;
    private ArrayList<Double> notas;

    // Construtor vazio
    public Aluno() {
        notas = new ArrayList<>();
    }

    // Construtor que já recebe nome e notas
    public Aluno(String nome, ArrayList<Double> notas) {
        this.nome = nome;
        this.notas = notas;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Double> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Double> notas) {
        this.notas = notas;
    }

    // Método que calcula a média do próprio aluno
    public double calcularMedia() {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.size();
    }

    // Método que verifica aprovação
    public String verificarResultado() {
        return (calcularMedia() >= 7) ? "Aprovado" : "Reprovado";
    }

    // Adicionar nota
    public void adicionarNota(double nota) {
        notas.add(nota);
    }
}
