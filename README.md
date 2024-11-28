## Projeto de Engenharia de Software

## Requisitos
- Docker >= 27.0.3
## Inicialização
- Crie o arquivo mongo.env na raiz do projeto com o seguinte formato:

    ```shell
    MONGO_INITDB_ROOT_USERNAME=[USUARIO ROOT DO BANCO]
    MONGO_INITDB_ROOT_PASSWORD=[SENHA DO USUARIO ROOT]
    ```
- Usuário e senha serão utilizados para inicializar o container do banco de dados.
- Com o Intellij aberto, abrir o menu lateral direito do Gradle, ir em _Tasks->application_ e clicar em _bootRun_.
- Aguardar o container _db-import_ encerrar a importação dos dados para o banco local.
- A primeira inicialização pode levar cerca de 5 minutos.

## Configurações

- Outras configurações estão disponíveis na pasta _docs_ do diretório raiz.

## Testes
- Com o Intellij aberto, abrir o menu lateral direito do Gradle, ir em _Tasks->verification_ e clicar em _tests_ para executar todos os testes.
