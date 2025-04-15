Cadastro de Funcionários em JavaFX
Descrição do Projeto
Este projeto consiste em uma aplicação desktop construída com JavaFX que permite realizar o cadastro de
funcionários com seus respectivos dados pessoais e endereço. Os dados são armazenados em arquivo CSV
e a interface gráfica foi desenvolvida com FXML (SceneBuilder).


Funcionalidades
- Cadastrar novo funcionário
- Excluir funcionário
- Consultar funcionário por matrícula
- Listar todos os funcionários
- Filtros:
 - Por cargo
 - Por faixa salarial (mínimo e máximo)
- Relatórios com uso de Stream API:
 - Média salarial por cargo
 - Agrupamento por cidade

Estrutura do Projeto
src/
  application/
     Main.java
     
README - Cadastro de Funcionários em JavaFX
controller/
 FuncionarioController.java
     model/
        Endereco.java
        Funcionario.java
    service/
        FuncionarioService.java
    util/
        CSVHelper.java
    view/
        FuncionarioView.fxml

Como Executar
1. Pré-requisitos:
- JDK 17 instalado (Corretto ou OpenJDK)
- JavaFX SDK 17 ou superior baixado e configurado no IntelliJ IDEA
- SceneBuilder (para editar a interface gráfica)
2. Configuração no IntelliJ IDEA:
- Vá em File > Project Structure > Modules > Dependencies
- Adicione os arquivos .jar da pasta 'lib' do seu JavaFX SDK
- Em Run/Debug Configurations, adicione ao VM options:


Cadastro de Funcionários em JavaFX
 --module-path "CAMINHO/para/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
3. Executar:
- Rode a classe Main.java
Arquivos Importantes
- 'funcionarios.csv': armazena os dados dos funcionários
- 'FuncionarioView.fxml': layout da interface feito com SceneBuilder
