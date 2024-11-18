## Projeto de Engenharia de Software

## Requisitos
- Docker >= 27.0.3
## Inicialização
- Crie o arquivo mongo.env na raiz do projeto com o seguinte formato:

    ```shell
    MONGO_INITDB_ROOT_USERNAME=[USUARIO ROOT BANCO]
    MONGO_INITDB_ROOT_PASSWORD=[SENHA USUARIO ROOT]
    ```

- Com o Intellij aberto, abrir o menu lateral direito do Gradle, ir em _Tasks->application_ e clicar em _bootRun_.

- Aguarde o container _db_import_ finalizar para utilizar a aplicação. A primeira execução pode demorar um pouco para baixar e processar todos os dados.

## Configurações

- Outras configurações estão disponíveis na pasta _docs_ do diretório raiz.