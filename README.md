## Projeto de Engenharia de Software

## Requisitos
- Docker >= 27.0.3
## Inicialização
- Crie o arquivo mongo.env na raiz do projeto com o seguinte formato:

    ```shell
    MONGO_INITDB_ROOT_USERNAME=[USUARIO ROOT BANCO]
    MONGO_INITDB_ROOT_PASSWORD=[SENHA USUARIO ROOT]
    ```
- MONGO_INITDB_ROOT_USERNAME significa o nome do usuário do banco de dados

- MONGO_INITDB_ROOT_PASSWORD significa a senha do usuário do banco de dados 

- Com o Intellij aberto, abrir o menu lateral direito do Gradle, ir em _Tasks->application_ e clicar em _bootRun_.

- O docker db-import importa dados para o mongo. Para rodar com arquivos json locais, crie uma pasta chamada 
  'datasets' e coloque os arquivos dentro dela. Caso a pasta esteja vazia, ele baixa os arquivos do INEP.

## Configurações

- Outras configurações estão disponíveis na pasta _docs_ do diretório raiz.
