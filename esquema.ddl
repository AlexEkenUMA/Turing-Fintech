CREATE TABLE AUTORIZADO (ID BIGINT NOT NULL, APELLIDOS VARCHAR NOT NULL, DIRECCION VARCHAR NOT NULL, ESTADO VARCHAR, FECHA_FIN DATE, FECHA_INICIO DATE, FECHA_NACIMIENTO DATE, IDENTIFICADOR BIGINT UNIQUE, NOMBRE VARCHAR NOT NULL, USUARIO_NOMBRE_USUARIO VARCHAR, PRIMARY KEY (ID))
CREATE TABLE CLIENTE (ID BIGINT NOT NULL, DTYPE VARCHAR(31), CIUDAD VARCHAR NOT NULL, CODIGO_POSTAL INTEGER, DIRECCION VARCHAR NOT NULL, ESTADO VARCHAR NOT NULL, FECHA_ALTA DATE, FECHA_BAJA DATE, IDENTIFICACION BIGINT UNIQUE, PAIS VARCHAR NOT NULL, TIPO_CLIENTE VARCHAR NOT NULL, USUARIO_NOMBRE_USUARIO VARCHAR, APELLIDOS VARCHAR NOT NULL, FECHA_NACIMIENTO DATE, NOMBRE VARCHAR NOT NULL, RAZON_SOCIAL VARCHAR NOT NULL, PRIMARY KEY (ID))
CREATE TABLE CUENTA (IBAN VARCHAR NOT NULL, DTYPE VARCHAR(31), SWIFT VARCHAR, ESTADO BOOLEAN NOT NULL, FECHA_APERTURA DATE NOT NULL, SALDO DOUBLE, TIPO VARCHAR NOT NULL, CLIENTE_ID BIGINT, NOMBRE_BANCO VARCHAR NOT NULL, PAIS VARCHAR, SUCURSAL VARCHAR, DIVISA_ABREVIATURA VARCHAR, SEGREGADA_IBAN VARCHAR, COMISION DOUBLE, CR_IBAN VARCHAR, PRIMARY KEY (IBAN))
CREATE TABLE DIVISA (ABREVIATURA VARCHAR NOT NULL, CAMBIO_EURO DOUBLE NOT NULL, NOMBRE VARCHAR NOT NULL, SIMBOLO CHAR, PRIMARY KEY (ABREVIATURA))
CREATE TABLE TRANSACCION (ID_UNICO BIGINT NOT NULL, CANTIDAD DOUBLE NOT NULL, COMISION DOUBLE, CONCEPTO VARCHAR, FECHA_INSTRUCCION TIMESTAMP NOT NULL, NOMBRE_EMISOR VARCHAR, TIPO_TRANSACCION VARCHAR NOT NULL, DESTINO_IBAN VARCHAR, EMISOR_ABREVIATURA VARCHAR, ORIGEN_IBAN VARCHAR, RECEPTOR_ABREVIATURA VARCHAR, PRIMARY KEY (ID_UNICO))
CREATE TABLE USUARIO (NOMBRE_USUARIO VARCHAR NOT NULL, ADMINISTRATIVO BOOLEAN, CONTRASEÑA VARCHAR NOT NULL, AUTORIZADO_ID BIGINT, CLIENTE_ID BIGINT, PRIMARY KEY (NOMBRE_USUARIO))
CREATE TABLE DEPOSITADAEN (SALDO DOUBLE NOT NULL, CUENTAREFERENCIA_IBAN VARCHAR NOT NULL, POOLEDACCOUNT_IBAN VARCHAR NOT NULL, PRIMARY KEY (CUENTAREFERENCIA_IBAN, POOLEDACCOUNT_IBAN))
CREATE TABLE AUTORIZADO_CLIENTE (autorizados_ID BIGINT NOT NULL, empresas_ID BIGINT NOT NULL, PRIMARY KEY (autorizados_ID, empresas_ID))
CREATE TABLE CLIENTE_CUENTA (Cliente_ID BIGINT NOT NULL, cuentasFintech_IBAN VARCHAR NOT NULL, PRIMARY KEY (Cliente_ID, cuentasFintech_IBAN))
CREATE TABLE CUENTA_TRANSACCION (Cuenta_IBAN VARCHAR NOT NULL, destino_ID_UNICO BIGINT NOT NULL, origen_ID_UNICO BIGINT NOT NULL, PRIMARY KEY (Cuenta_IBAN, destino_ID_UNICO, origen_ID_UNICO))
ALTER TABLE AUTORIZADO ADD CONSTRAINT FK_AUTORIZADO_USUARIO_NOMBRE_USUARIO FOREIGN KEY (USUARIO_NOMBRE_USUARIO) REFERENCES USUARIO (NOMBRE_USUARIO)
ALTER TABLE CLIENTE ADD CONSTRAINT FK_CLIENTE_USUARIO_NOMBRE_USUARIO FOREIGN KEY (USUARIO_NOMBRE_USUARIO) REFERENCES USUARIO (NOMBRE_USUARIO)
ALTER TABLE CUENTA ADD CONSTRAINT FK_CUENTA_CLIENTE_ID FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID)
ALTER TABLE CUENTA ADD CONSTRAINT FK_CUENTA_CR_IBAN FOREIGN KEY (CR_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE CUENTA ADD CONSTRAINT FK_CUENTA_DIVISA_ABREVIATURA FOREIGN KEY (DIVISA_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
ALTER TABLE CUENTA ADD CONSTRAINT FK_CUENTA_SEGREGADA_IBAN FOREIGN KEY (SEGREGADA_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_DESTINO_IBAN FOREIGN KEY (DESTINO_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_ORIGEN_IBAN FOREIGN KEY (ORIGEN_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_EMISOR_ABREVIATURA FOREIGN KEY (EMISOR_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_RECEPTOR_ABREVIATURA FOREIGN KEY (RECEPTOR_ABREVIATURA) REFERENCES DIVISA (ABREVIATURA)
ALTER TABLE USUARIO ADD CONSTRAINT FK_USUARIO_CLIENTE_ID FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID)
ALTER TABLE USUARIO ADD CONSTRAINT FK_USUARIO_AUTORIZADO_ID FOREIGN KEY (AUTORIZADO_ID) REFERENCES AUTORIZADO (ID)
ALTER TABLE DEPOSITADAEN ADD CONSTRAINT FK_DEPOSITADAEN_CUENTAREFERENCIA_IBAN FOREIGN KEY (CUENTAREFERENCIA_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE DEPOSITADAEN ADD CONSTRAINT FK_DEPOSITADAEN_POOLEDACCOUNT_IBAN FOREIGN KEY (POOLEDACCOUNT_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE AUTORIZADO_CLIENTE ADD CONSTRAINT FK_AUTORIZADO_CLIENTE_autorizados_ID FOREIGN KEY (autorizados_ID) REFERENCES AUTORIZADO (ID)
ALTER TABLE AUTORIZADO_CLIENTE ADD CONSTRAINT FK_AUTORIZADO_CLIENTE_empresas_ID FOREIGN KEY (empresas_ID) REFERENCES CLIENTE (ID)
ALTER TABLE CLIENTE_CUENTA ADD CONSTRAINT FK_CLIENTE_CUENTA_cuentasFintech_IBAN FOREIGN KEY (cuentasFintech_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE CLIENTE_CUENTA ADD CONSTRAINT FK_CLIENTE_CUENTA_Cliente_ID FOREIGN KEY (Cliente_ID) REFERENCES CLIENTE (ID)
ALTER TABLE CUENTA_TRANSACCION ADD CONSTRAINT FK_CUENTA_TRANSACCION_origen_ID_UNICO FOREIGN KEY (origen_ID_UNICO) REFERENCES TRANSACCION (ID_UNICO)
ALTER TABLE CUENTA_TRANSACCION ADD CONSTRAINT FK_CUENTA_TRANSACCION_Cuenta_IBAN FOREIGN KEY (Cuenta_IBAN) REFERENCES CUENTA (IBAN)
ALTER TABLE CUENTA_TRANSACCION ADD CONSTRAINT FK_CUENTA_TRANSACCION_destino_ID_UNICO FOREIGN KEY (destino_ID_UNICO) REFERENCES TRANSACCION (ID_UNICO)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
