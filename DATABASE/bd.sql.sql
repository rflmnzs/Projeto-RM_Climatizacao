DROP DATABASE IF EXISTS RM_Climatizacao;
CREATE DATABASE RM_Climatizacao;
USE RM_Climatizacao;

CREATE TABLE Clientes (
ID_Cliente INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
CPF CHAR(11) NOT NULL,
tipo_contrato VARCHAR(100),
endereco VARCHAR(150)
);

CREATE TABLE Maquina (
ID_Maquina INT PRIMARY KEY AUTO_INCREMENT,
Tipo_Maquina VARCHAR(50),
Modelo VARCHAR (50) NOT NULL,
BTUs INT
);

CREATE TABLE ClinteMaquina (
ID_Maquina INT,
ID_Cliente INT,
local_instalado VARCHAR(100),
data_intalacao date,
PRIMARY KEY (ID_Maquina, ID_Cliente),
FOREIGN KEY (ID_Maquina) REFERENCES Maquina(ID_Maquina),
FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);

CREATE TABLE Estoque (
ID_Estoque INT PRIMARY KEY AUTO_INCREMENT,
ID_Maquina INT,
quantidade INT,
FOREIGN KEY (ID_Maquina) REFERENCES Maquina(ID_Maquina)
);

CREATE TABLE CalculoBTUs (
ID_Calculo INT PRIMARY KEY AUTO_INCREMENT,
ID_Maquina INT,
area_m2 DECIMAL(10,2),
n_pessoas INT,
resultado_BTUs INT,
FOREIGN KEY (ID_Maquina) REFERENCES Maquina(ID_Maquina)
);

CREATE TABLE Funcionario (
ID_Funcionario INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
CPF CHAR(11) NOT NULL,
areaAtuacao VARCHAR(100)
);
 
 CREATE TABLE TipoServico (
 ID_TipoServico INT PRIMARY KEY AUTO_INCREMENT,
 descricao VARCHAR(100) NOT NULL
 );
 
 CREATE TABLE Servico (
 ID_Servico INT PRIMARY KEY AUTO_INCREMENT,
 data_solicitacao DATE,
 data_conclusao DATE,
 status VARCHAR(50),
 valor_total Decimal (10,2),
 ID_TipoServico INT,
 FOREIGN KEY (ID_TipoServico) REFERENCES TipoServico (ID_TipoServico)
 );
 
CREATE TABLE Servico_Funcionario (
ID_Servico INT,
ID_Funcionario INT,
PRIMARY KEY (ID_Servico, ID_Funcionario),
FOREIGN KEY (ID_Servico) REFERENCES Servico(ID_Servico),
FOREIGN KEY (ID_Funcionario) REFERENCES Funcionario(ID_Funcionario)
);

CREATE TABLE NotaFiscal (
ID_NotaFiscal INT PRIMARY KEY AUTO_INCREMENT,
ID_Servico INT,
ID_Cliente INT,
FOREIGN KEY (ID_Servico) REFERENCES Servico(ID_Servico),
FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);

CREATE TABLE Recebe (
ID_NotaFiscal INT,
ID_Servico INT,
ID_Cliente INT,
PRIMARY KEY (ID_NotaFiscal, ID_Servico, ID_Cliente),
FOREIGN KEY (ID_NotaFiscal) REFERENCES NotaFiscal(ID_NotaFiscal),
FOREIGN KEY (ID_Servico) REFERENCES Servico(ID_Servico),
FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);

CREATE TABLE Documenta (
ID_NotaFiscal INT,
ID_Servico INT,
ID_Cliente INT,
PRIMARY KEY (ID_NotaFiscal, ID_Servico, ID_Cliente),
FOREIGN KEY (ID_NotaFiscal) REFERENCES NotaFiscal(ID_NotaFiscal),
FOREIGN KEY (ID_Servico) REFERENCES Servico(ID_Servico),
FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);
 
 
USE RM_Climatizacao;
SHOW TABLES;