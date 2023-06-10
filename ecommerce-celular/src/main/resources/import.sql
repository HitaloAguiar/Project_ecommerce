-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

INSERT INTO marca (nome, cnpj) VALUES ('Apple', '12.738.020/0001-02');
INSERT INTO marca (nome, cnpj) VALUES ('Samsung', '93.031.562/0001-93');
INSERT INTO marca (nome, cnpj) VALUES ('LG', '72.021.892/0001-07');
INSERT INTO marca (nome, cnpj) VALUES ('Motorola', '85.829.018/0001-72');
INSERT INTO marca (nome, cnpj) VALUES ('Nokia', '93.092.283/0001-83');

INSERT INTO produto (nome, preco, estoque, id_marca) VALUES ('LG K62', 1399.90, 50, 3);
INSERT INTO produto (nome, preco, estoque, id_marca) VALUES ('iPhone 10', 2000.00, 45, 1);
INSERT INTO produto (nome, preco, estoque, id_marca) VALUES ('Moto G32', 1150.00, 60, 4);
INSERT INTO produto (nome, preco, estoque, id_marca) VALUES ('Galaxy A23', 1199.00, 100, 2);
INSERT INTO produto (nome, preco, estoque, id_marca) VALUES ('iPhone 13', 7599.90, 25, 1);

INSERT INTO celular (versaosistemaoperacional, sistemaoperacional, cor, id) VALUES (11.0, 1, 2, 1);
INSERT INTO celular (versaosistemaoperacional, sistemaoperacional, cor, id) VALUES (11.0, 2, 10, 2);
INSERT INTO celular (versaosistemaoperacional, sistemaoperacional, cor, id) VALUES (12.0, 1, 7, 3);
INSERT INTO celular (versaosistemaoperacional, sistemaoperacional, cor, id) VALUES (12.0, 1, 3, 4);
INSERT INTO celular (versaosistemaoperacional, sistemaoperacional, cor, id) VALUES (15.7, 2, 3, 5);

INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

INSERT INTO municipio (nome, id_estado) VALUES ('Manaus', 2);
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 5);
INSERT INTO municipio (nome, id_estado) VALUES ('Guaraí', 5);
INSERT INTO municipio (nome, id_estado) VALUES ('Belém', 4);
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 3);

INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('alameda 12', 'Quadra 708 Sul', 'lote 10', '77082-012', 2);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('avenida Bernado Sayão', 'Setor Aeroporto', 'número 3564', '77700-001', 3);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('rua Piauí', 'Quadra 301 Norte', 'numero 102', '77030-030', 1);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('alameda 08', 'Quadra 1200 Sul', 'numero 092', '77092-839', 3);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('alameda 13', 'Setor Bueno', 'lote 18', '77903-029', 1);

INSERT INTO telefone (codigoarea, numero) VALUES ('011', '98456-7812');
INSERT INTO telefone (codigoarea, numero) VALUES ('061', '99901-5842');
INSERT INTO telefone (codigoarea, numero) VALUES ('061', '99933-0572');
INSERT INTO telefone (codigoarea, numero) VALUES ('063', '99933-0572');
INSERT INTO telefone (codigoarea, numero) VALUES ('078', '98203-3301');
INSERT INTO telefone (codigoarea, numero) VALUES ('092', '98382-0912');
INSERT INTO telefone (codigoarea, numero) VALUES ('012', '99928-0912');
INSERT INTO telefone (codigoarea, numero) VALUES ('071', '99283-8723');

INSERT INTO pessoa (nome) VALUES ('João Aguiar');
INSERT INTO pessoa (nome) VALUES ('Maria Fernanda');
INSERT INTO pessoa (nome) VALUES ('Paulo Vitor');
INSERT INTO pessoa (nome) VALUES ('Julia Ramos');
INSERT INTO pessoa (nome) VALUES ('Lucas Ferreira');
INSERT INTO pessoa (nome) VALUES ('João Jorilo');

INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('09112332145', 'joao_aguia@gmail.com', 1, 1);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('89114182345', 'mariaF@gmail.com', 2, 2);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('19429301284', 'paulo_gaymer@gmail.com', 1, 3);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('90819287304', 'julia.ra@gmail.com', 2, 4);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('92874291092', 'lucas_ferreira@gmail.com', 1, 5);
INSERT INTO pessoaFisica (email, id) VALUES ('jubiscreisson@outlook.com', 6);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco, id_telefone_principal,
                id_telefone_opcional) VALUES (1, 'JoaoA', 'ZXChMgzI4VI5Jx+KKCL0AnuRaug9sWorJdV7iCDgWIDNVms7vyhaZeXP+5x26q6uDWKJmyQySZzE8hvoncjgCA==', 1, 2, 1);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco, id_telefone_principal)
            VALUES (2, 'MariaFer', 'x6JkviFo/CZc/dYoTsn+KjkyXu9rqbOwZ89vC1horO3B+ZT2N9nhquEvkFxm2WZahBpo5wgui91vSF00c1BYPA==', 3, 3);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco, id_telefone_principal,
                id_telefone_opcional) VALUES (3, 'PauloVitor', 'EDCT26TOqyKJg1i5rpN/tOkmr8RSjKfPP1qdPhjlj+sA3Wd++oZFkG5YChaMMRndKipiyVxfL12CUYWybBk+aA==', 2, 4, 5);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco, id_telefone_principal,
                id_telefone_opcional) VALUES (4, 'Juh', '/t7jPylqAlsn/BU03MiTgXI3m7B49BDsA3B8wBgk6dSj2a3G+1hgWJ+TNanb3cm8/iyX/io6DErKm/HPiwP/SA==', 5, 6, 7);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco, id_telefone_principal)
            VALUES (5, 'LucasFerreira', 'Tv7l6PLjIMgRTG8n32VVbtgHZyUj3L9nbtTz77T96tP52xepJQ25AoczGr8MA89dZ8cOErN3WcuGyOurMPCaOA==', 4, 8);

INSERT INTO usuario (id_pessoa_fisica, login, senha)
            VALUES (6, 'Joao_dos_Isekai', '89ud9FUF967ZPp2GxHJ6ITVrXHnVfA0uf1AsYZ0V0SYuA0OCjSKXEgH72aTLeGBaQr3m7WuVsgWlx76WK/gWuA==');

INSERT INTO perfis (id_usuario, perfil) VALUES (1, 'Admin');
INSERT INTO perfis (id_usuario, perfil) VALUES (2, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (1, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (3, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (4, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (5, 'Admin');
INSERT INTO perfis (id_usuario, perfil) VALUES (6, 'User_Basic');

INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 3);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (2, 3);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 1);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 5);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (4, 4);

INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-01-22', 3, 1, 1);
INSERT INTO avaliacao (comentario, data, estrela, id_produto, id_usuario)
                VALUES ('muito bão', '2022-11-09', 5, 3, 2);
INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-02-08', 1, 2, 1);
INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-03-14', 4, 1, 1);
INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2022-10-28', 5, 1, 3);

INSERT INTO pagamento (valor, confirmacaoPagamento, dataConfirmacaoPagamento) VALUES (511, true, '2023-06-10');
INSERT INTO pagamento (valor, confirmacaoPagamento, dataConfirmacaoPagamento) VALUES (1228.25, true, '2023-06-15');

INSERT INTO pix (nome, cpf, dataExpiracaoTokenPix, id) VALUES ('Maria Fernanda', '89114182345', '2023-06-11', 1);
INSERT INTO boletoBancario (id, nome, cpf, dataGeracaoBoleto, dataVencimento)
            VALUES (2, 'Maria Fernanda', '89114182345', '2023-06-15', '2023-06-25');

INSERT INTO compra (dataCompra, totalCompra, ifConcluida, id_endereco, id_pagamento, id_usuario)
            VALUES ('2023-06-10', 511, true, 3, 1, 2);

INSERT INTO compra (dataCompra, totalCompra, ifConcluida, id_endereco, id_pagamento, id_usuario)
            VALUES ('2023-06-15', 1228.25, true, 3, 2, 2);

INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (1, 10, 34.95, 1);
INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (1, 5, 32.30, 2);
INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 15, 34.95, 1);
INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 2, 29.00, 3);
INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 20, 32.30, 2);