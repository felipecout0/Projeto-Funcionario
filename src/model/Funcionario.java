package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Funcionario {
    private String matricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataContratacao;
    private Endereco endereco;

    public Funcionario(String matricula, String nome, String cpf, LocalDate dataNascimento,
                       String cargo, BigDecimal salario, LocalDate dataContratacao, Endereco endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
        this.endereco = endereco;
    }

    public String toCSV() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.join(";", matricula, nome, cpf, dataNascimento.toString(), cargo,
                salario.toString(), dataContratacao.toString(), endereco.toCSV());
    }

    public static Funcionario fromCSV(String[] fields) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Endereco endereco = Endereco.fromCSV(new String[]{
                fields[7], fields[8], fields[9], fields[10], fields[11], fields[12], fields[13]
        });
        return new Funcionario(
                fields[0],
                fields[1],
                fields[2],
                LocalDate.parse(fields[3], fmt),
                fields[4],
                new BigDecimal(fields[5]),
                LocalDate.parse(fields[6], fmt),
                endereco
        );
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getCargo() { return cargo; }
    public BigDecimal getSalario() { return salario; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public Endereco getEndereco() { return endereco; }

    @Override
    public String toString() {
        return nome + " (" + cargo + ") - R$" + salario;
    }
}
