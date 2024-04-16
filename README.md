# API de Processamento de Arquivos para Planos de Carro

## Descrição

Esta API Spring Boot é projetada para o processamento de arquivos Excel (.xlsx) relacionados a planos de carro. Ela permite o upload de arquivos, processa os dados contidos e armazena informações sobre planos, processamentos e erros.

## Funcionalidades

* **Upload de Arquivos Excel:** Permite o upload de arquivos Excel para processamento de dados de planos de carro.
* **Processamento de Dados:** Analisa os dados do arquivo Excel e processa informações de planos de carro.
* **Gerenciamento de Processamentos e Erros:** Armazena informações sobre os processamentos realizados e eventuais erros encontrados durante o processamento dos dados.

## Tecnologias Utilizadas

* Spring Boot
* Lombok
* Apache POI para leitura de arquivos Excel
* JPA / Hibernate para persistência de dados
* Banco de dados relacional (configuração dependente do ambiente)

## Estrutura do Projeto

O projeto é estruturado em pacotes, seguindo o padrão MVC, complementado com repositórios para interação com o banco de dados e entidades que representam as tabelas do banco.

## Principais Pacotes

* **controller:** Contém ProcessamentoController para lidar com as requisições HTTP.
* **service:** Contém ArquivoService para lógica de negócio do processamento de arquivos.
* **repository:** Interfaces JPA para interação com o banco de dados.
* **entity:** Classes que representam as entidades do banco de dados.
* **helper:** Utilitários, como validações de arquivo e geração de nomes aleatórios.

## Entidades

* **Processamento:** Representa um processamento de arquivo, incluindo status e quantidade de registros processados.
* **ProcessamentoErro:** Registra erros ocorridos durante o processamento de um arquivo.
* **Planos:** Dados dos planos processados a partir dos arquivos.

## Configuração e Execução

**Configuração do Ambiente:**

* Certifique-se de que o Java e o Maven estão instalados e configurados.
* Configure as propriedades do banco de dados no application.properties.

**Execução:**

* Execute o projeto com o Maven, usando `mvn spring-boot:run`.

## Uso

Para usar a API, envie um arquivo Excel (.xlsx) para o endpoint `/api/v1/arquivos/upload` usando um cliente HTTP, como Postman ou cURL. O arquivo deve seguir o formato esperado pela aplicação para ser processado corretamente.

