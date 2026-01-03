public class AlunoPresencial extends Aluno implements Avaliavel {

    public AlunoPresencial(String nome, int totalAulas) {
        super(nome, totalAulas);
    }

    @Override
    public String verificarResultado() {
        if (calcularMedia() >= 7 && calcularPresenca() >= 75) {
            return "Aprovado (Presencial)";
        } else {
            return "Reprovado (Presencial)";
        }
    }
}
