show databases;

-- a linha abaixo cria um banco de dados
create database dbinfox;

-- a linha abaixo escolhe o banco de dados a ser usado
use dbinfox;

-- o bloco de instruções abaixo cria uma tabela
create table tbusuarios(
iduser int primary key,
usuario varchar(50) not null,
fone varchar(15),
login varchar(15) not null unique,
senha varchar(15) not null
);

-- o comando abaixo descreve a tabela
describe tbusuarios;

-- a linha abaixo insere dados na tabela (CRUD)
-- Create -> INSERT
insert into tbusuarios(iduser, usuario, fone, login, senha)
values(1, 'Janitor Prates', '9999-9999', 'janitorprates', '123456');

-- a linha abaixo exibe os dados da tabela (CRUD)
-- Read -> SELECT
SELECT * FROM tbusuarios;

INSERT INTO tbusuarios(iduser, usuario, fone, login, senha)
VALUES(2, 'Administrador', '9999-9999', 'admin', 'admin');
INSERT INTO tbusuarios(iduser, usuario, fone, login, senha)
VALUES(3, 'Palloma Guimaraes', '9999-9999', 'palloma', '123456');

-- a linha abaixo modifica dados na tabela (CRUD)
-- update - > UPDATE
UPDATE tbusuarios SET fone='8888-8888' WHERE iduser=2;


-- a linha abaixo apaga um registro da tabela (CRUD)
-- delete -> DELETE
DELETE FROM tbusuarios WHERE iduser=3;

-------------------------------------------------------------------------------

CREATE TABLE tbclientes2(
idcli INT(11) PRIMARY KEY AUTO_INCREMENT,
nomecli VARCHAR(50) NOT NULL,
endcli VARCHAR(100) NOT NULL,
fonecli VARCHAR(50) NOT NULL,
emailcli VARCHAR(50)
);

-- alterar o tipo de dados de uma coluna em uma tabela
ALTER TABLE tbclientes MODIFY COLUMN idcli INT(11)  AUTO_INCREMENT;

alter table tbclientes modify column endcli varchar(100);

-- instrução é usada para descartar uma tabela existente em um banco de dados
DROP TABLE tbclientes2;

DESCRIBE tbclientes;

INSERT INTO tbclientes(nomecli, endcli, emailcli, fonecli)
VALUES('Jair Souza', 'Sobradinho', 'jrcofres@gmail.com', '9999-9999');

select * from tbclientes;
-- o like ignora acentos e se as letras estão em maisculas ou não
-- ex: jA ou ja ou Jâ vai ser considerado iguais

select * from tbclientes where nomecli like 'j%'; -- retorna os que começam com J
SELECT * FROM tbclientes where nomecli like 'jo%'; -- retorna os que começam com Jo
select * from tbclientes where nomecli like 'ja%';


create table tbos(
os int primary key auto_increment,
data_os timestamp default current_timestamp,
equipamento varchar(150) not null,
defeito varchar(150) not null,
servico varchar(150),
tecnico varchar(30),
valor decimal(10,2),
idcli int not null,
foreign key(idcli) references tbclientes(idcli)
);

describe tbos;

insert into tbos (equipamento, defeito, servico, tecnico, valor, idcli)
values('Notebook', 'Não liga', 'Troa da fonte', 'Paulo', 98.60, 1);

update tbos set servico='Troca da fonte' where os=1;

select * from tbos;

-- o código abaixo traz informações de duas tabelas
select 
O.os, equipamento, defeito, servico, valor,
C.nomecli, fonecli
from tbos as O
inner join tbclientes as C
on (O.idcli = C.idcli);

select * from tbusuarios;

insert into tbusuarios(iduser, usuario, fone, login, senha)
values(3, 'Palloma Guimarães', '88888-66666', 'palloma', '1234');

describe tbusuarios;
select * from tbusuarios;

update tbusuarios set senha='123456' where iduser=3;

-- a linha abaixo adiciona um campo a tabela
alter table tbusuarios add column perfil varchar(20) not null;

-- a linha abaixo remove um campo de uma tabela
alter table tbusuarios drop column perfil;

update tbusuarios set perfil='admin' where iduser=2;
update tbusuarios set perfil='admin' where iduser=1;
update tbusuarios set perfil='user' where iduser=3;

-- apelido das colunas
select idcli as Id, nomecli as Nome, endcli as Endereço, 
fonecli as Telefone, emailcli as Email from tbclientes;

select idcli as Id, nomecli as Nome, fonecli as Telefone from tbclientes where nomecli like 'jo%';

describe tbos;
-- a linha abaixo altera a tabela adicionando um campo em uma determinada posição
alter table tbos add tipo varchar(15) not null after data_os;
alter table tbos add situacao varchar(20) not null after tipo;

select * from tbos;

update tbos set defeito='muito sujo' where os=5;
UPDATE tbos set tipo='Ordem de Serviço' where os=1;
alter table tbos modify column tipo varchar(20);

select os, date_format(data_os, '%d/%m/%Y - %H:%I'), tipo, situacao,
equipamento, defeito, servico, tecnico, valor, idcli from tbos where os=1;

-- a instrução abaixo seleciona e ordena por nome todos os clientes cadastrados
select * from tbclientes order by nomecli;

use dbinfox;
-- o bloco de instruções abaixo faz a seleção e união de dados de 2 tabelas
-- OSER é uma variável que contém os cmapos desejados da tabela OS
-- CLI é outra variável que contém os campos desejados da tabela clientes
select
OSER.os, data_os, tipo, situacao, equipamento, valor,
CLI.nomecli, fonecli
from tbos as OSER
inner join tbclientes as CLI
on (CLI.idcli = OSER.idcli); -- on ou where


-- mostra o último número gerado pelo auto_increment
-- maior valor da coluna os 
select max(os) from tbos;
select * from tbos;