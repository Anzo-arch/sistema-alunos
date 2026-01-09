public class AlunoPresencial extends Aluno {

    public AlunoPresencial(String nome, int totalAulas) {
        super(nome, totalAulas);
    }

    @Override
    public String verificarResultado() {
        if (calcularMedia() >= 7 && calcularPresenca() >= 75) {
            return nome + ": Aprovado (Presencial)";
        } else {
            return nome + ": Reprovado (Presencial)";
        }
    }
}
