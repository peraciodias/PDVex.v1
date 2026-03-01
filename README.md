# PDVex-v1 🛒

## 📌 Funcionalidades

- ✔️ Cadastro de produto
- ✔️ Cadastro e gerenciamento de fornecedores
- ✔️ Cadastro e gerenciamento de Clientes Pessoa física
- ✔️ Cadastro e gerenciamento de de Clientes Pessoa jurídica
- ✔️ Cadastro e gerenciamento de usuarios
- ✔️ Controle de estoque
- ✔️ Gerenciamento de Caixas
- ✔️ Relatórios
- ✔️ Configuração de impressoaras
- ✔️ Interface moderna em Swing
- ✔️ Arquitetura organizada (MVC simplificado)
- ✔️ Conexão MySQL centralizada


## 🏗️ Estrutura do Projeto (Maven)

## 🛢️ Banco de Dados

### 📌 Requisitos
-Postgresql
- Driver JDBC para PostgreSQL
  org.postgresql/<artifactId>postgresql</artifactId>/<version>42.7.2</version>

1. Execute o script
   `Usar arquivo Mysql /PDVex-v1/src/main/resources/TABELA_PRODUTOS	sql`

### 📌 conexão com jdbc:
private static final String URL  = "jdbc:mysql://localhost:3306/BCO_DADOS_MERCADO";
private static final String USER = "root";
private static final String PASS = "root";


### 📌 Como executar no bash:
2. Clone o repositório:
   git clone
   cd MERCADO-VS1

3. Compile com Maven
   mvn clean install

4. Execute:
   mvn exec:java -Dexec.mainClass="br.com.creativex.Main"

ou se preferir

java -jar target/PDVex-vs1.jar

🖥️ Tecnologias Utilizadas

Java 21

Swing

PostgreSQL

JDBC

Maven

MVC modularizado


🧩 Arquitetura

O projeto segue uma arquitetura simples para facilitar manutenção:

Model

Representa os dados (ex: Produto).

DAO

Acesso ao banco e operações CRUD (ProdutoDAO).

UI

Interface visual construída com JFrame/JPanel (ProdutoForm).

DB

Conexão centralizada com o banco.

🛠️ Melhorias Futuras (roadmap)

📌 Adicionar login de usuário

📌 Suporte a imagens de produtos

📌 Filtragem avançada

📌 Migração para JavaFX

📌 Relatórios em PDF

📌 API REST (Spring Boot)

📚 Autor
🛠️
Perácio Dias
