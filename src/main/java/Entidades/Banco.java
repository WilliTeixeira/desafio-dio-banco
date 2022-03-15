package Entidades;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Data
public class Banco {

    private String nome;
    public static List<Conta> contas = new ArrayList<>();

    public static void menuInicial() {
        Scanner escolhaUsuario = new Scanner(System.in);

        System.out.println("=======BEM-VINDO=AO=BANCO=DIO=========");
        System.out.println(" Escolha uma das opções disponíveis   ");
        System.out.println(" * 1 - Acessar sua conta              ");
        System.out.println(" * 2 - Criar nova conta               ");
        System.out.println(" * 0 - Sair                           ");
        System.out.println("======================================");

        System.out.print("Qual a sua opção?: ");
        switch (escolhaUsuario.nextInt()){
            case(1):
                acessarConta();
                break;
            case(2):
                novaConta();
                break;
            case(0):
                System.out.println("Até logo!!!");
                break;
            default:
                System.out.println("Opção inválida");
                menuInicial();
                break;
        }
    }

    public static void novaConta() {
        Scanner escolhaUsuario = new Scanner(System.in);
        Scanner entradaUsuario = new Scanner(System.in);
        String nome;
        String senha;
        int escolhe;
        int cpf;
        System.out.println("======================================");
        System.out.println("Qual o tipo de conta que deseja criar?");
        System.out.println(" * 1 - Poupança                       ");
        System.out.println(" * 2 - Corrente                       ");
        System.out.println("======================================");
        System.out.print  ("Escolha 1 ou 2: ");
        escolhe = escolhaUsuario.nextInt();

        System.out.print("Escreva o seu nome: ");
        nome = entradaUsuario.next();
        System.out.print("Qual o seu CPF: ");
        cpf = entradaUsuario.nextInt();
        System.out.print("Crie uma senha: ");
        senha = entradaUsuario.next();
        System.out.println("======================================");

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);

        if(escolhe == 1) {
            Conta conta = new ContaPoupanca(cliente, senha);
            contas.add(conta);
            conta.setTipoConta("Poupança");
        }

        if(escolhe == 2) {
            Conta conta = new ContaCorrente(cliente, senha);
            contas.add(conta);
            conta.setTipoConta("Corrente");
        }

        System.out.println("Conta criada!!!");
        menuInicial();
    }

    public static void acessarConta() {
        Scanner entradaUsuario = new Scanner(System.in);
        String senha;
        int cpf;

        System.out.println("======================================");
        System.out.println("Digite o seu CPF?                     ");
        System.out.print("CPF: ");
        cpf = entradaUsuario.nextInt();
        System.out.println("Digite a sua senha de acesso          ");
        System.out.print("Senha: ");
        senha = entradaUsuario.next();
        System.out.println("======================================");

        Optional<Conta> retorno = contas.stream().filter(e -> e.cliente.getCpf() == cpf && e.senha.equals(senha)).findFirst();
        if (retorno.isPresent()) {
            menuOpcoes(retorno.get());
        } else {
            System.out.println("Senha inválida ou conta inexiste");
            menuInicial();
        }

    }

    public static void menuOpcoes(Conta conta) {
        Scanner escolhaUsuario = new Scanner(System.in);
        Scanner entradaUsuario = new Scanner(System.in);
        double valor = 0d;
        int escolhe = 0;
        System.out.println("=========OPERAÇÕES-DISPONÍVEIS========");
        System.out.println(" * 1 - SAQUE                          ");
        System.out.println(" * 2 - DEPOSITO                       ");
        System.out.println(" * 3 - TRANSFERÊNCIA                  ");
        System.out.println(" * 4 - IMPRIMIR EXTRATO               ");
        System.out.println(" * 0 - SAIR                           ");
        System.out.println("======================================");
        System.out.print  ("Escolha 0,1,2,3 ou 4: ");
        escolhe = escolhaUsuario.nextInt();

        switch (escolhe) {
            case(1):
                System.out.print("Digite o valor a ser sacado: ");
                valor = entradaUsuario.nextDouble();
                conta.sacar(valor);
                menuOpcoes(conta);
                break;
            case(2):
                System.out.print("Digite o valor a ser depositado: ");
                valor = entradaUsuario.nextDouble();
                conta.depositar(valor);
                menuOpcoes(conta);
                break;
            case(3):
                int contaDestino;
                System.out.print("Digite o valor a ser transferido: ");
                valor = entradaUsuario.nextDouble();
                System.out.print("Digite o número da conta destino: ");
                contaDestino = entradaUsuario.nextInt();

                Optional<Conta> retorno = contas.stream().filter(e -> e.getNumero() == contaDestino).findFirst();
                if (retorno.isPresent()) conta.transferir(valor, retorno.get());

                menuOpcoes(conta);
                break;
            case(4):
                conta.imprimirInfoComuns();
                menuOpcoes(conta);
                break;
            case(0):
                menuInicial();
                break;
        }

    }

}
