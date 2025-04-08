package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Endereco;
import model.Funcionario;
import service.FuncionarioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioController {

    @FXML private TextField txtMatricula, txtNome, txtCpf, txtCargo, txtSalario;
    @FXML private DatePicker dpNascimento, dpContratacao;
    @FXML private TextField txtLogradouro, txtNumero, txtComplemento, txtBairro, txtCidade, txtEstado, txtCep;

    // Novos campos para filtros
    @FXML private TextField txtFiltroCargo;
    @FXML private TextField txtSalarioMin;
    @FXML private TextField txtSalarioMax;

    @FXML private TableView<Funcionario> tableFuncionarios;
    @FXML private TableColumn<Funcionario, String> colMatricula, colNome, colCargo;
    @FXML private TableColumn<Funcionario, BigDecimal> colSalario;

    private FuncionarioService service;
    private ObservableList<Funcionario> listaTabela;

    @FXML
    public void initialize() {
        service = new FuncionarioService();
        listaTabela = FXCollections.observableArrayList();

        colMatricula.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getMatricula()));
        colNome.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNome()));
        colCargo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCargo()));
        colSalario.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getSalario()));

        atualizarTabela();
    }

    @FXML
    public void onCadastrar() {
        try {
            String matricula = txtMatricula.getText();
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            LocalDate nascimento = dpNascimento.getValue();
            if (nascimento == null) {
                throw new IllegalArgumentException("Selecione uma data de nascimento válida.");
            }
            String cargo = txtCargo.getText();
            BigDecimal salario = new BigDecimal(txtSalario.getText());
            LocalDate contratacao = dpContratacao.getValue();
            if (contratacao == null) {
                throw new IllegalArgumentException("Selecione uma data de contratação válida.");
            }

            String logradouro = txtLogradouro.getText();
            String numero = txtNumero.getText();
            String complemento = txtComplemento.getText();
            String bairro = txtBairro.getText();
            String cidade = txtCidade.getText();
            String estado = txtEstado.getText();
            String cep = txtCep.getText();

            Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
            Funcionario funcionario = new Funcionario(matricula, nome, cpf, nascimento, cargo, salario, contratacao, endereco);

            service.cadastrar(funcionario);
            atualizarTabela();
            limparCampos();
            showInfo("Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            showError("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML
    public void onExcluir() {
        Funcionario selecionado = tableFuncionarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            service.excluir(selecionado.getMatricula());
            atualizarTabela();
            showInfo("Funcionário excluído.");
        } else {
            showError("Selecione um funcionário na tabela.");
        }
    }

    @FXML
    public void onConsultar() {
        String matricula = txtMatricula.getText();
        Funcionario f = service.consultar(matricula);
        if (f != null) {
            showInfo(f.toString());
        } else {
            showError("Funcionário não encontrado.");
        }
    }

    @FXML
    public void onListar() {
        atualizarTabela();
    }

    // 🔎 Filtro por cargo
    @FXML
    public void onFiltrarPorCargo() {
        String cargo = txtFiltroCargo.getText();
        List<Funcionario> filtrados = service.filtrarPorCargo(cargo);
        listaTabela.setAll(filtrados);
        tableFuncionarios.setItems(listaTabela);
    }

    // 💰 Filtro por faixa salarial
    @FXML
    public void onFiltrarPorSalario() {
        try {
            BigDecimal min = new BigDecimal(txtSalarioMin.getText());
            BigDecimal max = new BigDecimal(txtSalarioMax.getText());
            List<Funcionario> filtrados = service.filtrarPorSalario(min, max);
            listaTabela.setAll(filtrados);
            tableFuncionarios.setItems(listaTabela);
        } catch (NumberFormatException e) {
            showError("Valores de salário inválidos. Use apenas números.");
        }
    }

    private void atualizarTabela() {
        listaTabela.setAll(service.listarTodos());
        tableFuncionarios.setItems(listaTabela);
    }

    private void limparCampos() {
        txtMatricula.clear(); txtNome.clear(); txtCpf.clear(); txtCargo.clear(); txtSalario.clear();
        txtLogradouro.clear(); txtNumero.clear(); txtComplemento.clear(); txtBairro.clear(); txtCidade.clear(); txtEstado.clear(); txtCep.clear();
        dpNascimento.setValue(null); dpContratacao.setValue(null);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
