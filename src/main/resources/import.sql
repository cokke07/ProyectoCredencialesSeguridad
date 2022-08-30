DROP TABLE IF EXISTS users;
create table users(email varchar, password varchar, role varchar);
insert into users (email,password,role) 
values ('correo1@gmail.com','$2a$10$IdlAEJJxMAS3Bipv0WC0POgV926LESZuwkav3EjN/dvcSwyERsZEG','CLIENT');
insert into users (email,password,role) 
values ('correo2@gmail.com','$2a$10$.ok3D4ipAkGb.o.OyxedEuUW7Dj2SYKcfTKytKWKAn76wB4QCFnvC','ADMIN');
--user
--admin