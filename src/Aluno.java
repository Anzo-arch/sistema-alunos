import java.util.ArrayList;

public class Aluno {
    private String nome;
    private ArrayList<Double> notas;
    private int faltas;
    private int totalAulas;

    // Construtor vazio
    public Aluno() {
        notas = new ArrayList<>();
        faltas = 0;
        totalAulas = 0;
    }

    // Construtor completo
    public Aluno(String nome, ArrayList<Double> notas, int faltas, int totalAulas) {
        this.nome = nome;
        this.notas = notas;
        this.faltas = faltas;
        this.totalAulas = totalAulas;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public ArrayList<Double> getNotas() { return notas; }
    public void setNotas(ArrayList<Double> notas) { this.notas = notas; }

    public int getFaltas() { return faltas; }
    public void setFaltas(int faltas) { this.faltas = faltas; }

    public int getTotalAulas() { return totalAulas; }
    public void setTotalAulas(int totalAulas) { this.totalAulas = totalAulas; }

    // Método para adicionar nota
    public void adicionarNota(double nota) { notas.add(nota); }

    // Método para calcular média
    public double calcularMedia() {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return notas.size() > 0 ? soma / notas.size() : 0;
    }

    // Método para calcular presença em %
    public double calcularPresenca() {
        return totalAulas > 0 ? ((totalAulas - faltas) * 100.0 / totalAulas) : 0;
    }

    // Método que verifica aprovação considerando nota e presença
    public String verificarResultado() {
        if (calcularMedia() >= 7 && calcularPresenca() >= 75) {
            return "Aprovado";
        } else if (calcularMedia() < 7 && calcularPresenca() < 75) {
            return "Reprovado por nota e falta";
        } else if (calcularMedia() < 7) {
            return "Reprovado por nota";
        } else {
            return "Reprovado por falta";
        }
    }
}
