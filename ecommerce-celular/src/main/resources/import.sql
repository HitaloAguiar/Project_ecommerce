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
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 4);
INSERT INTO municipio (nome, id_estado) VALUES ('Guaraí', 4);
INSERT INTO municipio (nome, id_estado) VALUES ('Belém', 3);
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

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal, id_telefone_opcional)
            VALUES ('João Aguiar', 'joao_aguia@gmail.com', 'joao1234', '09112332145',
                    1, 2, 1);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal)
            VALUES ('Maria Fernanda', 'mariaF@gmail.com', 'senha1234', '89114182345',
                    3, 3);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal, id_telefone_opcional)
            VALUES ('Paulo Vitor', 'paulo_gaymer@gmail.com', 'pa1000ulo', '19429301284',
                    2, 4, 5);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal, id_telefone_opcional)
            VALUES ('Julia Ramos', 'julia.ra@gmail.com', 'julia1234', '90819287304',
                    5, 6, 7);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal)
            VALUES ('Lucas Ferreira', 'lucas_ferreira@gmail.com', 'lucas890', '92874291092',
                    4, 8);

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