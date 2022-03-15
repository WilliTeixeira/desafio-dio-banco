package Entidades;

import interfaces.IConta;
import lombok.Data;

import java.util.Scanner;

@Data
public abstract class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected String senha;
    protected String tipoConta;

    public Conta(Cliente cliente, String senha) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = Conta.SEQUENCIAL++;
        this.cliente = cliente;
        this.senha = senha;
    }

    @Override
    public void sacar(double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            System.out.println("Operaçao realizada!");
        } else {
            System.out.println("Saldo insufiente! Operação cancelada");
        }
    }

    @Override
    public void depositar(double valor) {
            this.saldo += valor;
            System.out.println("Operaçao realizada!");
    }

    @Override
    public void transferir(double valor, IConta contaDestino) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            contaDestino.depositar(valor);
            System.out.println("Operaçao realizada!");
        } else {
            System.out.println("Saldo insufiente! Operação cancelada");
        }
    }

    protected void imprimirInfoComuns() {
        System.out.println("*=============EXTRATO===================*");
        System.out.println(String.format("|Titular : %s              ", this.cliente.getNome()));
        System.out.println(String.format("|Agencia : %d              ", this.agencia));
        System.out.println(String.format("|Número  : %d              ", this.numero));
        System.out.println(String.format("|Saldo   : R$ %.2f         ", this.saldo));
        System.out.println("*=====================================*");
    }

    public boolean solicitarSenha() {
        Scanner senhaDigitada = new Scanner(System.in);
        System.out.print("Favor, digite a senha de operação: ");
        if (this.senha.equals(senhaDigitada.next())) {
            return true;
        } {
            System.out.println("Operação não pode ser realizada. Senha inválida");
            return false;
        }
    }

}
