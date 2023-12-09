INSERT INTO tb_usuario (first_name, last_name, email, password) VALUES ('Fufu', 'Brown', 'fufu@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_usuario (first_name, last_name, email, password) VALUES ('Maria', 'Joana', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (1, 1);
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (2, 1);
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (2, 2);


INSERT INTO tb_categoria (descricao) VALUES ('Eletrônicos');
INSERT INTO tb_categoria (descricao) VALUES ('Alimentos');
INSERT INTO tb_categoria (descricao) VALUES ('Jogos');

INSERT INTO tb_produto (preco, quantidade, descricao, nome) VALUES (3000.0, 3, 'O ultimo top de linha super', 'Play Station Ultra');
INSERT INTO tb_produto (preco, quantidade, descricao, nome) VALUES (8000.0, 2, 'Smartphone da samsung de última geração', 'Samsung Galaxy S23 Ultra');
INSERT INTO tb_produto (preco, quantidade, descricao, nome) VALUES (4000.0, 3, 'Assista aos filmes em imagem 4k', 'TV LG 50 pol');

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (1, 1);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (1, 3);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (2, 1);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (3, 1);