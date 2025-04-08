package service;

import model.Funcionario;
import util.CSVHelper;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FuncionarioService {

    private List<Funcionario> funcionarios;
    private final String caminho = "funcionarios.csv";


    public FuncionarioService() {
        funcionarios = new ArrayList<>();
        carregar();
    }

    public void cadastrar(Funcionario f) {
        funcionarios.add(f);
        salvar();
    }

    public void excluir(String matricula) {
        funcionarios.removeIf(f -> f.getMatricula().equals(matricula));
        salvar();
    }

    public Funcionario consultar(String matricula) {
        return funcionarios.stream()
                .filter(f -> f.getMatricula().trim().equalsIgnoreCase(matricula.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }

    public List<Funcionario> filtrarPorCargo(String cargo) {
        return funcionarios.stream()
                .filter(f -> f.getCargo().equalsIgnoreCase(cargo))
                .collect(Collectors.toList());
    }

    public List<Funcionario> filtrarPorSalario(BigDecimal min, BigDecimal max) {
        return funcionarios.stream()
                .filter(f -> f.getSalario().compareTo(min) >= 0 && f.getSalario().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    public Map<String, Double> mediaSalarialPorCargo() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getCargo,
                        Collectors.averagingDouble(f -> f.getSalario().doubleValue())
                ));
    }

    public Map<String, List<Funcionario>> agruparPorCidade() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(f -> f.getEndereco().getCidade()));
    }

    private void carregar() {
        File file = new File(caminho);
        if (!file.exists()) return;

        List<String[]> lines = CSVHelper.read(caminho);
        funcionarios = new ArrayList<>();
        for (String[] linha : lines) {
            if (linha.length >= 14) {
                funcionarios.add(Funcionario.fromCSV(linha));
            }
        }
    }

    private void salvar() {
        List<String> linhas = funcionarios.stream()
                .map(f -> String.join(";",
                        f.getMatricula(),
                        f.getNome(),
                        f.getCpf(),
                        f.getDataNascimento().toString(),
                        f.getCargo(),
                        f.getSalario().toString(),
                        f.getDataContratacao().toString(),
                        f.getEndereco().getLogradouro(),
                        f.getEndereco().getNumero(),
                        f.getEndereco().getComplemento(),
                        f.getEndereco().getBairro(),
                        f.getEndereco().getCidade(),
                        f.getEndereco().getEstado(),
                        f.getEndereco().getCep()
                ))
                .toList();

        CSVHelper.write(caminho, linhas);
    }
}
