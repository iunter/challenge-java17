-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into Empresa(id, cuit, razonSocial, fechaAdhesion) values(1, '11111111111', 'empresa1', '2024-11-22');
insert into Empresa(id, cuit, razonSocial, fechaAdhesion) values(2, '11111111112', 'empresa2', '2024-10-21');
insert into Empresa(id, cuit, razonSocial, fechaAdhesion) values(3, '11111111113', 'empresa3', '2024-11-10');
alter sequence empresa_seq restart with 4;

insert into Transferencia(id, importe, idEmpresa, cuentaDebito, cuentaCredito, fecha ) values (1, 20, 2, '123', '456', '2024-11-22');