INSERT INTO PESSOA (id, nome, cpf, data_nascimento) VALUES (1, 'Gabriel', '12345678900', '1999-02-13');
INSERT INTO ENDERECO (id, rua, numero, bairro, cidade, estado, cep, pessoa_id) VALUES (1, 'Rua X', '123', 'Bairro X', 'Cidade X', 'Estado X', '00000000', 1);
INSERT INTO ENDERECO (id, rua, numero, bairro, cidade, estado, cep, pessoa_id) VALUES (2, 'Rua Y', '123', 'Bairro Y', 'Cidade Y', 'Estado Y', '00000000', 1);
INSERT INTO ENDERECO (principal, id, rua, numero, bairro, cidade, estado, cep, pessoa_id) VALUES (true, 3, 'Rua Y', '123', 'Bairro Y', 'Cidade Y', 'Estado Y', '00000000', 1);