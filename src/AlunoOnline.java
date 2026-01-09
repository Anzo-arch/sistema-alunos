public class AlunoOnline extends Aluno {

    public AlunoOnline(String nome, int totalAulas) {
        super(nome, totalAulas);
    }

    @Override
    public String verificarResultado() {
        if (calcularMedia() >= 7) {
            return nome + ": Aprovado (Online)";
        } else {
            return nome + ": Reprovado (Online)";
        }
    }
}
