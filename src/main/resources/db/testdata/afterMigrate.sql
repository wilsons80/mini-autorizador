set foreign_key_checks = 0;

delete from  cartao;
set foreign_key_checks = 1;
alter table  cartao auto_increment = 1;
insert into cartao (codigo, numero_cartao, saldo, senha) values (1, '6549873025634502',500, '1234');