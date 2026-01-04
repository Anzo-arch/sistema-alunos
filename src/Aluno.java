import java.util.ArrayList;

public abstract class Aluno {
    protected String nome;
    protected ArrayList<Double> notas;
    protected int faltas;
    protected int totalAulas;

    public Aluno(String nome, int totalAulas) {
        this.nome = nome;
        this.totalAulas = totalAulas;
        this.notas = new ArrayList<>();
        this.faltas = 0;
    }

    public void adicionarNota(double nota) {
        notas.add(nota);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double n : notas) {
            soma += n;
        }
        return notas.size() > 0 ? soma / notas.size() : 0;
    }

    public double calcularPresenca() {
        return totalAulas > 0 ? ((totalAulas - faltas) * 100.0) / totalAulas : 0;
    }

    public void registrarFaltas(int faltas) {
        this.faltas = faltas;
    }

    public abstract String verificarResultado();
}
