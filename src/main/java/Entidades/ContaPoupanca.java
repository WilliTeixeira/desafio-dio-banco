package Entidades;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(Cliente cliente, String senha) {
        super(cliente, senha);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Entidades.Conta Poupança ===");
        super.imprimirInfoComuns();
    }

}
