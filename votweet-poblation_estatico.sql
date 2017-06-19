
/*INSERT INTO `metrica` (`met_id`,`met_valor`, `met_nombre`) VALUES (NULL, 1,'Aprobación Nacional');
*/



INSERT INTO `usuario` (`usr_id`, `cdto_id`, `usr_privilegio`, `usr_nombre`, `usr_apellido`, `usr_correo`, `usr_password`, `usr_activo`, `usr_fecha_registro`) VALUES (NULL, NULL, '1', 'Admin', 'Omnipotweet', 'admin@omnipotweet.cl', 'admin', '1', CURRENT_TIMESTAMP);

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Independiente', NULL, NULL);

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Chile Vamos', 'chilevamos.jpg', 'Chile Vamos es una coalición política chilena que agrupa a cuatro partidos de centro, centroderecha y derecha. Fue creada el 29 de enero de 2015 por los secretarios generales de los partidos que la componen: Javier Macaya (UDI), Mario Desbordes (RN), Eduardo Salas (PRI) y Jorge Saint-Jean (Evópoli), en un acto realizado en el edificio del ex Congreso Nacional de Chile. El 27 de abril de 2016 quedó oficialmente inscrita ante el Servicio Electoral para competir en sus elecciones primarias de alcaldes y las elecciones municipales de ese año.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Nueva Mayoría', 'nuevamayoria.jpg', 'Nueva Mayoría es una coalición política chilena que agrupa a un conjunto de partidos de centroizquierda e izquierda, creada en 2013. Fue estrenada en las elecciones presidencial, parlamentarias y de consejeros regionales de 2013 —siendo inscrita por primera vez en el Servicio Electoral el 30 de abril de ese año— y posteriormente en las elecciones municipales de 2016.\nEstá conformada por los cuatro partidos de la Concertación de Partidos por la Democracia —el Partido Socialista de Chile (PS), el Partido Demócrata Cristiano de Chile (PDC), el Partido por la Democracia (PPD) y el Partido Radical Socialdemócrata (PRSD)—, además del Partido Comunista de Chile (PCCh), la Izquierda Ciudadana (IC), el Movimiento Amplio Social (MAS) e independientes de centroizquierda.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Frente Amplio', 'frenteamplio.jpg', 'El Frente Amplio (FA) es una coalición política chilena conformada por partidos y movimientos políticos de izquierda, liberales y ciudadanos que se plantean superar la dicotomía del bipartidismo chileno, conformada por la Nueva Mayoría y Chile Vamos.\r\nLa coalición está conformada por Revolución Democrática y el Partido Humanista, ambos partidos políticos legalmente constituidos.\r\n\r\nA ellos se suman los movimientos políticos:\r\nIzquierda Libertaria: fundada el 11 de junio de 2016 como confluencia del Frente de Estudiantes Libertarios (FEL), la Organización Comunista Libertaria (OCL), la Unidad Muralista Luchador Ernesto Miranda y otros colectivos menores.\r\nMovimiento Autonomista: fundado en mayo de 2016 y liderado por Gabriel Boric tras su salida de Izquierda Autónoma.16\r\nNueva Democracia: creado en julio de 2016, constituido oficialmente el 4 de septiembre del mismo año y liderado por Cristián Cuevas; vinculada a la Fundación Crea, cercana a la Unión Nacional Estudiantil (UNE) y a la Fundación Emerge.\r\nConvergencia de Izquierdas: fundada el 26 de julio de 2014 e incluye al Movimiento Nueva Izquierda (NI), el Movimiento Amplio de Izquierda (MAIZ), Acción Socialista Allendista (ASA) e integrantes del extinto Partido de Izquierda (PAIZ).\r\nTambién participan dentro del Frente Amplio otros partidos políticos como por ejemplo Poder, Igualdad, Ecologista Verde y el Partido Liberal, además del movimiento político Izquierda Autónoma y el Partido Pirata de Chile.19 20 También forma parte el Movimiento Democrático Progresista, integrado principalmente por exmilitantes del Partido Progresista que renunciaron a dicha colecividad en enero de 2017.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Todos', 'todos.jpg', 'Todos es un partido político chileno de centro fundado en 2015 que actualmente está constituido de forma legal ante el Servicio Electoral de Chile.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Unión Patriótica', 'unionpatriotica.jpg', 'Unión Patriótica, UPA es un partido político chileno cuya escritura de constitución data del 14 de septiembre de 2015 ante notario público Myriam Amigo Arancibia, de la Vigésimo primera notaria de Santiago, con fecha de publicación en el diario oficial de 2 de octubre de 2015.\r\n\r\nUnión Patriótica se define como un partido político instrumental de los luchadores sociales y políticos de distintas procedencias, sin distinciones ideológicas, nacionalidad, étnica o religión, pero que están de acuerdo con los principios generales que mueven al partido, como son la Democracia, Justicia y Dignidad, en pos de una Patria Nueva que ponga en el centro la defensa de sus intereses nacionales por sobre los intereses de empresas trasnacionales o potencias extranjeras.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Unidos en la Fe', 'unidosenlafe.jpg', 'Partido en formación.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'Partido Progresista', 'partidoprogresista.jpg', 'El Partido Progresista (PRO) es un partido político chileno fundado en 2010, como respuesta al abandono de las banderas progresistas de la entonces Concertación. Su principal líder y fundador, Marco Enríquez-Ominami, ex diputado del Partido Socialista de Chile y fue candidato a la presidencia en 2009 y en 2013.');

INSERT INTO `partido` (`ptdo_id`, `ptdo_nombre`, `ptdo_logo`, `ptdo_descripcion`) VALUES (NULL, 'País', 'pais.jpg', 'El partido fue presentado públicamente en un acto en Santiago el 3 de septiembre de 2016, mientras que se anunció su fundación oficial para el 10 del mismo mes en la ciudad de Concepción.\r\n\r\nEntre los fundadores del partido se encontraba el senador Alejandro Navarro, quien anunció en agosto de 2016 su alejamiento de la Nueva Mayoría, situación que no fue compartida por el resto de MAS Región, partido del cual fue uno de sus fundadores en 2009. También forma parte de País el exsecretario general del Partido Socialista Gonzalo Martner Fanta.');





INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '2', 'Sebastián Piñera', 'sebastianpinera', '1949-12-01', '67', 'Miguel Juan Sebastián Piñera Echenique (Santiago, 1 de diciembre de 1949) es un empresario, inversor y político chileno. Fue presidente de Chile entre los años 2010 y 2014. También fue el primer Presidencia pro tempore de CELAC.\r\n\r\nHijo de José Piñera Carvallo, un funcionario público democratacristiano chileno que se desempeñó como embajador durante el gobierno del presidente Eduardo Frei Montalva, Sebastián Piñera estudió Ingeniería Comercial con mención en Economía de la Pontificia Universidad Católica de Chile y posteriormente obtuvo un Máster y Doctorado en Economía en la Universidad de Harvard. Su vida ha estado ligada al negocio bursátil y a la política.\r\n\r\nEn el ámbito financiero, es dueño de una de las mayores fortunas de su país, con un capital estimado de 2700 millones de dólares en 2017, según la revista Forbes.\r\n\r\nEn el campo político, se desempeñó como senador por la circunscripción Santiago Oriente entre 1990 y 1998; posteriormente postuló en dos oportunidades a la presidencia de Chile: primero en 2005, siendo derrotado por Michelle Bachelet en segunda vuelta, y luego en 2009, donde superó a Eduardo Frei Ruiz-Tagle en el balotaje, convirtiéndose en el primer presidente de derecha en ser elegido democráticamente desde 19588 y el primero en ejercicio desde que Augusto Pinochet dejara el cargo en 1990. Militó durante veinte años en el partido de centroderecha Renovación Nacional, colectividad que llegó a presidir durante 2001-2004, pero de la cual se desligó antes de asumir la jefatura del Estado, cumpliendo con los estatutos del partido.', 'https://somos9.files.wordpress.com/2012/01/sebastian_pinera1.jpg', '1','PineraInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '2', 'Manuel José Ossandón', 'mjossandon', '1962-08-24', '54', 'Manuel José Ossandón Irarrázabal (Viña del Mar, Región de Valparaíso, 24 de agosto de 1962) es un técnico agrícola y político chileno. Fue alcalde de las comunas de Pirque, entre 1992 y 2000, y de Puente Alto, entre 2000 y 2012. Actualmente, es senador por la 8º Circunscripción Senatorial, Santiago Oriente, elegido en las elecciones parlamentarias de 2013, para el periodo 2014-2022. Militó en el partido de centroderecha Renovación Nacional (RN), al cual renunció en julio de 2016.', 'http://www.adnradio.cl/images_remote/330/3305526_n_vir3.jpg', '1','OssandonInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '2', 'Felipe Kast', 'felipekast', '1977-07-09', '39', 'Felipe José Kast Sommerhoff (Santiago, 9 de junio de 1977) es un economista, académico, investigador, consultor y político chileno. En la actualidad es diputado por Santiago.\r\n\r\nEn su calidad de independiente de derecha, participó activamente en el Gobierno del presidente Sebastián Piñera, primero como ministro de Estado y luego como delegado presidencial para los campamentos y aldeas de emergencia que ocupaban los damnificados del terremoto de febrero de 2010.\r\n\r\nEs miembro de Evolución Política (Evópoli), partido del cual fue presidente.', 'https://www.amchamchile.cl/wp-content/uploads/Felipe_Kast_w.jpg', '1','KastInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '3', 'Alejandro Guillier', 'SenadorGuillier', '1953-04-05', '64', 'Alejandro René Eleodoro Guillier Álvarez (La Serena, Región de Coquimbo; 5 de marzo de 1953) es un sociólogo, periodista de radio y televisión y político chileno independiente. Es senador por la II Circunscripción de Antofagasta y el candidato presidencial del Partido Radical Social Demócrata, el Partido Socialista y el Partido Comunista.', 'http://www.duna.cl/media/2016/09/Alejandro-Guillier-900x506.jpg', '1', 'GuillerInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '3', 'Carolina Goic', 'carolinagoic', '1972-12-20', '44', 'Carolina Goic Boroevic (Santiago, 20 de diciembre de 1972) es una asistente social y política chilena, militante del Partido Demócrata Cristiano. El 2 de abril de 2016 asumió como presidenta de dicha colectividad.\r\n\r\nDesde el 11 de marzo de 2014 es senadora de Chile por la Circunscripción 19º de la Región de Magallanes y de la Antártica Chilena y fue diputada entre 2006 y 2014 por el distrito 60, que comprende las comunas de Río Verde, Antártica, Laguna Blanca, Natales, Cabo de Hornos, Porvenir, Primavera, Punta Arenas, San Gregorio, Timaukel y Torres del Paine.', 'http://static.emol.cl/emol50/Fotos/2016/04/02/file_20160402165038.jpg', '1','GoicInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '4', 'Beatriz Sánchez', 'labeasanchez', '1970-12-24', '46', 'Beatriz de Jesús Sánchez Muñoz (Viña del Mar, 24 de diciembre de 1970) es una periodista y política chilena. Es conocida por dedicarse al periodismo político y las entrevistas de actualidad.\r\n\r\nFue presentadora del programa Hora 20 en La Red. Ha trabajado en las principales radios informativas de Chile, como Radio Bío-Bío, Radio ADN, Radio Cooperativa y Radio Chilena. Actualmente es la candidata por el Movimiento Autonomista y Revolución Democrática a las primarias del Frente Amplio.', 'http://www.adnradio.cl/images/3457039_n_vir3.jpg', '1','beaInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '4', 'Alberto Mayol', 'AlbertoMayol', '1976-07-08', '40', 'Alberto Mayol Miranda (8 de julio de 1976) es un sociólogo y analista político chileno. Es académico, investigador y autor de varios trabajos sobre ciencias sociales, política y cultura.\r\n\r\nEn marzo de 2017 anunció su candidatura para participar de las primarias del Frente Amplio, de las que se elegirá al representante de dicha coalición para participar en la elección presidencial de Chile de 2017.', 'http://cdn.elperiscopio.cl/wp-content/uploads/2017/05/Aton_250326.jpg', '1','mayolInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '1', 'José Antonio Kast', 'joseantoniokast', '1966-01-18', '51', 'José Antonio Kast Rist (18 de enero de 1966) es un abogado y político chileno.\r\n\r\nActualmente es diputado por el Distrito Nº 24, correspondiente a las comunas de La Reina y Peñalolén. Anteriormente se desempeñó como diputado por el Distrito Nº 30 (2002-2014) y como concejal de la comuna de Buin. Fue un militante histórico de la Unión Demócrata Independiente (UDI) hasta el anuncio de su renuncia el 31 de mayo de 2016, y actualmente busca levantar su candidatura independiente a la elección presidencial de 2017.', 'http://www.gamba.cl/wp-content/uploads/2016/03/jose-antonio-kast-7.jpg', '1','JoseKastInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '5', 'Nicolás Shea', 'nicoshea', NULL, '42', 'Emprendedor social de 42 años, elegido Joven Líder Global por el Foro Económico Mundial el año 2014 y dos veces ganador del premio nacional de innovación AVONNI. Ex asesor de innovación y emprendimiento del Ministerio de Economía. ', 'http://d2vpb0i3hb2k8a.cloudfront.net/wp-content/uploads/sites/7/2017/01/06/18-e1483727579919.jpg', '1','SheaInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '5', 'Nicolás Larraín', 'nicolaslarrain', '1965-05-19', '51', 'Nicolás Enrique Larraín de Toro (Santiago, 19 de mayo de 1965) es un presentador de televisión, locutor radial y empresario chileno. Es conocido por haber conducido programas de televisión como Chile Tuday y la versión chilena de Caiga quien caiga. Actualmente es precandidato presidencial de Chile.', 'http://www.calidadturistica.cl/wp-content/uploads/2014/03/1.1.jpg', '1','LarrainInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '1', 'Carola Canelo', 'CaneloCarola', NULL, '45', 'Carola Canelo Figueroa, (Santiago,1971), es abogada, Master of Laws in\r\nInternational Legal Studies en American University y académica de la Facultad de\r\nDerecho de la Universidad de Chile; está casada con el Ingeniero Civil Industrial\r\nÁlvaro Orellana con quien tiene dos hijos.\r\nEs hija de padres profesores, cursó sus estudios en el Colegio San Agustín. En\r\n1989 obtuvo la Beca Presidente de la República para estudiar Derecho en la\r\nFacultad de Derecho de la Universidad de Chile. En calidad de estudiante fue\r\nayudante adhonorem en las cátedras de Derecho Procesal, Historia del Derecho,\r\nClínicas Jurídicas y Derecho del Trabajo.\r\nEn 1999 obtuvo el Premio Pedro Nicolás Montenegro por haber obtenido las más\r\naltas calificaciones de su promoción (1989 a 1993). Dos años consigue el grado\r\nacadémico de Licenciada en Ciencias Jurídicas y Sociales de la Facultad de\r\nDerecho de la Universidad de Chile, con distinción máxima. En 1997 la Corte\r\nSuprema de Justicia le otorga el título de abogada.', '
https://upload.wikimedia.org/wikipedia/commons/a/ad/Carola_Canelo_%28cropped%29.jpg', '1', 'CaneloInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '6', 'Eduardo Artés', 'eduardo_artes', '1979-05-08', '65', 'Eduardo Artés Brichetti (San Vicente de Tagua Tagua, 25 de octubre de 1951) es un profesor y político chileno, actual Secretario General del Partido Comunista Chileno (Acción Proletaria) (PC (AP)) y presidente de la Unión Patriótica (UPA).\r\n\r\nFue precandidato a presidente de Chile en la elección de 2009.', 'http://www.labatalla.cl/wp-content/uploads/2017/04/eduardoartesportada1.jpg', '1','ArtesInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '7', 'Franco Parisi', 'Fr_parisi', '1967-05-25', '49', 'Franco Aldo Parisi Fernández (Santiago, 25 de agosto de 1967) es un ingeniero comercial y académico chileno. Se hizo conocido por realizar, junto a su hermano Antonino, programas de radio y televisión sobre economía. En 2012 lanzó su candidatura independiente para la elección presidencial de 2013 de su país, donde obtuvo el cuarto lugar de las preferencias con el 10,11%. Ideológicamente se define como socioliberal.', 'http://media.devup.network/wp-content/uploads/2017/04/04163608/parisiAton_238534.jpg', '1','ParisiInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '8', 'Marco Enríquez-Ominami', 'marcoporchile', '1973-06-12', '43', 'Marco Antonio Enríquez-Ominami Gumucio (Santiago, 12 de junio de 1973) es un político y cineasta franco-chileno.\r\n\r\nMiembro del Partido Socialista entre 1990 y 2009, fue diputado entre 2006 y 2010 por el Distrito nº 10. En 2009 renunció a dicho partido para participar como candidato independiente en las elecciones presidenciales de ese año, en las que salió tercero. En 2010 fundó el Partido Progresista, cuyo consejo federal lo proclamó unánimemente en julio de 2013 candidato a las presidenciales de fines de año, donde obtuvo nuevamente la tercera mayoría.\r\n\r\nEn artes visuales, es mayormente conocido por haber dirigido la serie de televisión La vida es una lotería, transmitida en cinco temporadas entre 2002 y 2005 por TVN y entre 2006 y 2007 por Mega. Desde mediados de 2013 conduce el programa Cambio de switch en radio Universidad de Chile. Es profesor honoris causa de la Universidad de Aquino, Bolivia y profesor invitado en la Universidad Nacional de Rosario, Argentina.', 'http://static.t13.cl/images/sizes/1200x675/1477670550-auno723298.jpg', '1','OminamiInicio.html');

INSERT INTO `candidato` (`cdto_id`, `ptdo_id`, `cdto_nombre`, `cdto_cuenta_twitter`, `cdto_fecha_nacimiento`, `cdto_edad`, `cdto_descripcion`, `cdto_imagen`, `cdto_activo`, `urlInicio`) VALUES (NULL, '9', 'Alejandro Navarro', 'senadornavarro', '1958-11-20', '58', 'Alejandro Navarro Brain (Santiago, 20 de noviembre de 1958) es un político chileno. Actualmente ejerce como senador por la Circunscripción electoral XII Biobío Costa.\r\n\r\nEn 1994 fue electo diputado por el distrito Nº 45. Fundó en enero de 2009 el Movimiento Amplio Social (MAS), mismo año en que fue proclamado como precandidato a la Presidencia de la República. Renunció en 2016 a dicho partido para integrarse al País, colectividad que le pidió ser precandidato presidencial para 2017.', 'http://www.elciudadano.cl/wp-content/uploads/2016/07/navarro.jpg', '1','NavarroInicio.html');





INSERT INTO `keyword` (`kwd_id`,`cdto_id`,`kwd_texto`) VALUES (NULL,'1','Sebastián Piñera');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'Sebastian Piñera');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'SebastianPiñera');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', '#TiemposMejores');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'SebastianPiñeraPresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'Sebastian Pinera');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'Sebastián Pinera');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', 'ChileVamos');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'1', '#ChileVamos');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'Manuel José Ossandón');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'Manuel Jose Ossandon');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'ManuelJoseOssandon');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'ManuelJoséOssandón');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'OssandónPresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', '#ChileDiferente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'2', 'mjossandon');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', 'Felipe Kast');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', 'FelipeKast');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', '#EvoluciónAhora');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', 'felipekast2018');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', 'Evopoli');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'3', 'Evópoli');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'Alejandro Guillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'AlejandroGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'SenadorGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'Senador Guillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'AlejandroGuillier.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'Gobernar con la gente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'Gobernarconlagente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', '#Gobernarconlagente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', '#FirmexGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', '#YoFirmoxGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'JovenesGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'MujeresGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', 'PCxGuillier');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'4', '#MeGustaGuillier');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'5', 'Carolina Goic');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'5', 'CarolinaGoic');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'5', '#GoicPresidenta');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'Beatriz Sánchez');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'BeatrizSánchez');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'Beatriz Sanchez');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'BeatrizSanchez');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'BeatrizSanchez.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'labeasanchez');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'beasanchezytu');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'elfrente_amplio');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', 'frente amplio');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#frenteamplio');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#vampospormas');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#vampospormás');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#dependedeti');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#ConfianzaQueCambiaChile');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'6', '#RegionesConBeaSánchez');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'7', 'AlbertoMayol');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'7', 'Alberto Mayol');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'7', 'MayolPresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'7', 'mayopresidente2017');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'7', '#MayolPresidente');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', 'José Antonio Kast');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', 'Jose Antonio Kast');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', 'JoseAntonioKast');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', 'Kast.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', '#PorLaVerdad');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', '#PorLaFamilia');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', '#PorLaSeguridad');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', '#PorLaVida');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'8', '#KastPresidente');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'Nicolás Shea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'Nicolas Shea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'NicolásShea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'NicolasShea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'NicoShea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'Nico Shea');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'NicoShea.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'NicoSheaPresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', '#TODOS');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'9', 'TodosCL');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'Nicolás Larraín');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'Nicolas Larrain');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'NicolásLarraín');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'NicolasLarrain');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'NicolásLarraín');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', '#ElDíaDelNicoCambiaChile');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'nicolaslarrain.com');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', 'liberenanicolas');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', '#ElDiaDelNico');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'10', '#ElDíaDelNico');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'11', 'Carola Canelo');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'11', 'CarolaCanelo');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'11', 'caneloabogados.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'11', 'CaneloCarola');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'11', '#SinFirmasNoaHayCandidatura');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'Eduardo Artés');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'EduardoArtés');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'Eduardo Artes');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'EduardoArtes');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'unionpatriotica');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'unionpatriotica.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'union patriotica');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', '#eduardoartespresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', '#eduardoarteéspresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'artespresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'12', 'artéspresidente');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', 'Franco Parisi');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', 'FrancoParisi');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', 'fr_parisi');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', '#yovotoparisi2018');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', '#yovotoporparisi');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', '#fr_parisi');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', '#FrancoParisiUCV');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', 'democraciaregional');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'13', 'parisioficial');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14','Marco Enríquez-Ominami');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14','Marco Enriquez-Ominami');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'MarcoEnríquez-Ominami');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'PrensaProgre');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'marcoporchile');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'marco enríquez-o');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'marcoenriquiezominami.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'marcoenriquiezominami');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'14', 'marcoenriquiezominami.cl');

INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'Alejandro Navarro');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'AlejandroNavarro');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'SenadorNavarro');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'Senador Navarro');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'pais_chile');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'navarro.cl');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', '#TiempoDeDignidad');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'NavarroPresidente');
INSERT INTO `keyword` (`kwd_id`,`cdto_id`, `kwd_texto`) VALUES (NULL,'15', 'Navarro Presidente');




