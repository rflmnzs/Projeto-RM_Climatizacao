# 📦 RM Climatização – Sistema de Gestão (Java + MySQL)

Este repositório contém meu primeiro projeto completo integrando **Java**, **Java Swing** e **MySQL**, desenvolvido para simular o sistema interno de uma empresa real do ramo de climatização.  
O objetivo foi criar um sistema funcional capaz de gerenciar clientes, máquinas, estoque, serviços e notas fiscais, aplicando conceitos de programação orientada a objetos, arquitetura organizada e modelagem de banco de dados.

---

## 🚀 Tecnologias Utilizadas

- **Java 8+**
- **Interface gráfica com Java Swing**
- **MySQL / JDBC**
- **Padrão DAO (Data Access Object)**
- **Arquitetura MVC simplificada**
- **Organização por packages**

---

## 📁 Estrutura do Projeto

src/
├─ interface/ → Telas e interface gráfica (Java Swing)
├─ dao/ → Classes de acesso ao banco (CRUD)
├─ entidades/ → Classes modelo (getters, setters, construtores)
└─ conexaoSQL/ → Conexão com o banco de dados

sql/
└─ RM_CLIMADB.sql → Script completo do banco de dados

## 🛢️ Banco de Dados

O banco foi construído seguindo regras de negócio reais, com tabelas relacionadas, foreign keys e controle detalhado de informações da empresa.

O script SQL inclui:

- Clientes  
- Máquinas  
- Estoque  
- Funcionários  
- Serviços e Tipos de Serviço  
- Notas Fiscais  
- Tabelas relacionais (N:N)

  /sql/RM_CLIMADB.sql


### ✔ Como importar o banco

1. Abra o **MySQL Workbench**  
2. Vá em **File → Run SQL Script**  
3. Selecione `RM_CLIMADB.sql`  
4. Execute

---

## 🖥️ Interface Gráfica (Java Swing)

A interface foi desenvolvida com foco na simplicidade e funcionalidade, permitindo:

- Cadastro e consulta de clientes  
- Gestão de máquinas e estoque  
- Registro e acompanhamento de serviços  
- Emissão e gerenciamento de notas fiscais  

---

## 🔌 Como Executar o Projeto

1. Importe o projeto em sua IDE: **IntelliJ**, **Eclipse** ou **NetBeans**.  
2. Certifique-se de que o MySQL está ativo.  
3. Importe o banco com o script SQL.  
4. Edite os dados de conexão no package `util`:
   - host  
   - porta  
   - usuário  
   - senha  
5. Compile e execute a classe principal no package `interfaces`.

---

## 🎯 Objetivos do Projeto

- Criar um sistema completo para simular uma empresa de climatização  
- Praticar CRUD integrado a MySQL  
- Desenvolver interface gráfica em Swing  
- Trabalhar arquitetura organizada e coesa  
- Modelar um banco de dados funcional para uso real  
- Unir conceitos de POO, SQL e UI em um único projeto

  
- **Próximos passos:** coletar, organizar e analisar os dados gerados pelo sistema para gerar insights e indicadores.

---

## 📄 Scripts SQL



/sql/RM_CLIMADB.sql


Este arquivo cria automaticamente:

- O banco `RM_Climatizacao`  
- Todas as tabelas  
- Todos os relacionamentos  
- Estrutura completa do sistema  

---

## 🤝 Contribuições

Sugestões e melhorias são sempre bem-vindas!  
Você pode abrir **issues**, enviar **pull requests** ou simplesmente entrar em contato.

---

## 📬 Contato

Caso queira conversar sobre o projeto, oportunidades ou tecnologia:

**LinkedIn:** www.linkedin.com/in/rafael-menezes-738b06282

---

## ⭐ Gostou do projeto?

Considere deixar uma **estrela** no repositório! Isso ajuda muito 😄
