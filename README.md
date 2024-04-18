## 🎯 API de Processamento de arquivos para Cotações e Planos de Carro

**Esta API foi desenvolvida para facilitar o processamento eficiente de arquivos Excel (.xlsx) que contêm dados sobre cotações e planos de automóveis.**

* Upload de Arquivos: Permite aos usuários carregar arquivos Excel diretamente na plataforma.
* Processamento de Dados: Após o upload, a API processa automaticamente os dados contidos no arquivo. Isso inclui a validação das células e tipos do dados para as cotações de seguros, modelos de carros, segmentos e dados específicos dos veículos.
* Armazenamento de Informações: Todos os dados processados são armazenados em um banco PostgresSQL. Isso inclui informações sobre modelos, segmentos, e detalhes específicos de cada veículo.
* Gerenciamento de Erros: A API permite salvar os erros em uma tabela especifica para cada linha, caso haja ou falhas durante o processamento dos arquivos. Isso garante que qualquer problema seja identificado e tratado.
      
## ✔️ Tecnologias e bibliotecas usadas
   - `Java 21`
   - `Spring Boot 3.2.4`
   - `REST API`
   - `PostgreSQL`
   - `H2`
   - `Flyway`
   - `Lombok`
   - `Apache POI`

## 🚀 Como usar
## Clone o repositório:

```bash
git clone https://github.com/pauloruszel/api-proc-arquivo.git
```

## 📁 Entre na pasta do projeto:

```bash
cd api-proc-arquivo
```

  
## 🛠️ Build:
   ```bash
    cd api-proc-arquivo
    mvn clean install
   ```

## 🐳 Execute o docker-compose:
```bash
docker-compose up --build
```

## ✨ URL's importantes:

PostgresSQL:

URL: `jdbc:postgresql://postgres:5432/processamentoarquivo`
