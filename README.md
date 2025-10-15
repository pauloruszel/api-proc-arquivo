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

## ⚙️ Configuração

Antes de executar em ambientes que utilizam PostgreSQL defina as variáveis de ambiente abaixo (nenhuma credencial padrão fica embarcada no `application.properties`):

| Variável | Descrição |
| --- | --- |
| `SPRING_DATASOURCE_URL` | URL JDBC para o banco PostgreSQL. |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco. |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco. |
| `PROCESSAMENTO_LIMITE_REGISTROS` | (Opcional) Limite máximo de linhas processadas por arquivo. Valor padrão: `500` em produção e `150` no perfil `dev`. |

O limite efetivamente utilizado é registrado em cada linha da tabela `processamento`, facilitando auditorias futuras.

## 🔄 Fluxo de processamento e monitoramento

* O upload passa a ser assíncrono: o endpoint responde rapidamente com HTTP `202 Accepted`, liberando o cliente para outras operações enquanto o processamento ocorre em background.
* Após o envio, utilize os novos endpoints para acompanhar o andamento:
  * `GET /api/v1/processamentos/{id}` — retorna status, totais processados e o limite aplicado ao processamento.
  * `GET /api/v1/processamentos/{id}/erros` — lista as falhas de validação encontradas por linha.

As mensagens de erro seguem um padrão com códigos estáveis, facilitando automações e internacionalização.

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
