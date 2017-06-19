DROP TABLE IF EXISTS candidato;
DROP TABLE IF EXISTS cdto_met;
DROP TABLE IF EXISTS keyword;
DROP TABLE IF EXISTS metrica;
DROP TABLE IF EXISTS opinion;
DROP TABLE IF EXISTS partido;
DROP TABLE IF EXISTS usuario;


CREATE TABLE candidato
(
   cdto_id              INT NOT NULL AUTO_INCREMENT,
   ptdo_id              INT,
   cdto_nombre          VARCHAR(255),
   cdto_cuenta_twitter  VARCHAR(255),
   cdto_fecha_nacimiento DATE,
   cdto_edad            INT,
   cdto_descripcion     TEXT,
   cdto_imagen          VARCHAR(255),
   cdto_activo          BOOLEAN DEFAULT '1',
   urlInicio			VARCHAR(255),
   PRIMARY KEY (cdto_id)
);

CREATE TABLE cdto_met
(
   met_id               INT NOT NULL,
   cdto_id              INT NOT NULL,
   PRIMARY KEY (met_id, cdto_id)
);

CREATE TABLE keyword
(
   kwd_id               INT NOT NULL AUTO_INCREMENT,
   cdto_id				INT NOT NULL,
   kwd_texto            VARCHAR(255),
   kwd_activo			BOOLEAN DEFAULT '1',
   PRIMARY KEY (kwd_id)
);

CREATE TABLE metrica
(
   met_id               INT NOT NULL AUTO_INCREMENT,
   met_nombre           VARCHAR(255),
   met_valor			DOUBLE,
   met_fecha            TIMESTAMP,
   met_activo			BOOLEAN DEFAULT '1',
   PRIMARY KEY (met_id)
);

CREATE TABLE opinion
(
   opinion_id           INT NOT NULL AUTO_INCREMENT,
   cdto_id              INT,
   opinion_fecha        TIMESTAMP,
   opinion_sentimiento  TINYINT(1),
   opinion_pais_ubicacion  VARCHAR(255),
   opinion_region_ubicacion  VARCHAR(255),
   opinion_ciudad_ubicacion  VARCHAR(255),
   opinion_retweets     INT,
   opinion_likes        INT,
   opinion_autor        VARCHAR(255),
   opinion_menciona_candidato BOOLEAN,
   opinion_resp_candidato BOOLEAN,
   opinion_activo		BOOLEAN DEFAULT '1',
   PRIMARY KEY (opinion_id)
);

CREATE TABLE partido
(
   ptdo_id              INT NOT NULL AUTO_INCREMENT,
   ptdo_nombre          VARCHAR(255),
   ptdo_logo            VARCHAR(255),
   ptdo_descripcion     TEXT,
   ptdo_activo			BOOLEAN DEFAULT '1',
   PRIMARY KEY (ptdo_id)
);

CREATE TABLE usuario
(
   usr_id               INT NOT NULL AUTO_INCREMENT,
   cdto_id              INT,
   usr_privilegio		INT DEFAULT '0',
   usr_nombre           VARCHAR(255),
   usr_apellido         VARCHAR(255),
   usr_correo           VARCHAR(255) UNIQUE,
   usr_password         VARCHAR(255),
   usr_activo           BOOLEAN DEFAULT '1',
   usr_fecha_registro   TIMESTAMP,
   PRIMARY KEY (usr_id)
);

ALTER TABLE candidato ADD CONSTRAINT fk_pertenece FOREIGN KEY (ptdo_id)
      REFERENCES partido (ptdo_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE cdto_met ADD CONSTRAINT fk_cdto_met FOREIGN KEY (met_id)
      REFERENCES metrica (met_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE cdto_met ADD CONSTRAINT fk_cdto_met2 FOREIGN KEY (cdto_id)
      REFERENCES candidato (cdto_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE opinion ADD CONSTRAINT fk_recibe FOREIGN KEY (cdto_id)
      REFERENCES candidato (cdto_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE usuario ADD CONSTRAINT fk_apoya FOREIGN KEY (cdto_id)
      REFERENCES candidato (cdto_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
	  
alter table keyword add constraint FK_keyword_cdto foreign key (cdto_id)
      references candidato (cdto_id) on delete restrict on update restrict;



/*
--- Aprobación relativa al total de datos
--- todo esto es en relación a los tweets que contengan info de las candidaturas
tpos_total = total de tweets positivos entre todos los candidatos
tpos[i] = tpos_candidato[i]/tpos_total

likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos
likes[i] = likes_candidato[i]/likes_total

rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos
rts[i] = rts_candidato[i]/rts_total

aprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1

--- Total de tweets positivos
SELECT COUNT(*) AS TotalTweetsPos FROM opinion WHERE opinion_sentimiento > 0;

---Total de likes a los tweets positivos totales
SELECT SUM(opinion_likes) AS TotalLikesTweetsPos FROM opinion WHERE opinion_sentimiento > 0;

--- Total de tweets positivos para el candidato 1
SELECT COUNT(*) FROM opinion WHERE opinion_sentimiento > 0 AND cdto_id = 1;

*/