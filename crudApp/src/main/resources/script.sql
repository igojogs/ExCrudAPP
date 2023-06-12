create database crud;
use crud;

create table livros(
    id int auto_increment primary key,
    Titulo varchar(20) not null,
    Autor varchar(20) not null,
    Ano int not null
);

insert into livros(Titulo,Autor,Ano) values("teste", "teste0", 2023);