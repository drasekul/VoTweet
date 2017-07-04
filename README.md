# VoTweet
Proyecto para Taller de Base de Datos 1-2017
## Descripcion
El backend consta de 3 programas:
- Colector de Twitter.
- Depurador.
- Servicio REST.

Además esta el script para las tablas de MySQL y el script de poblacion para los datos de los canidatos y de sus partidos políticos. Se debe crear el schema primero con el nombre de "votweet".

### Colector de Twitter
para que funcione primero debe modificar la conexion de MySQL para conectarse correctamente.
Para eso debe modificar los parametros de entrada de la funcion(línea 279). Ademas de eso verifique que los archivos "ciudades de chile.txt" (línea 358) tengan las rutas correspondientes, sino el programa no podrá guardar los datos correctamente. Luego de eso debe escribir lo siguiente:
```
$ gradle jar
$ java -cp build/libs/colector-twitter-streaming-master-1.0.jar cl.citiaps.twitter.streaming.TwitterStreaming"
```
### Depurador
Para un mejor funcionamiento de este utilice eclipse para abrir la carpeta del programa. Ya abierto la carpeta, modifique la ruta de las librerias necesarias que estan en la carpeta "lib", luego cambie los valores para que este se pueda conectar a las base de datos MySQL y MongoDB (línea 29 y 31 respectivamente). Además se debe modificar las rutas de los archivos "Sentimientos.txt" "ciudades de Chile.txt" (línea 25 y 26 respectivamente).

**NOTA**: Las modificaciones se deben hacer en "ProcesamientoDatos.java"

Ya teniendo listo eso presione el boton "run" de Eclipse

### Servicio REST
Este tiene el nombre de "votweet-backend-master" el cual contiene los servicios necesarios para la aplicación.
Debe modificar las rutas de los archivos "numeros regiones Chile.txt" y "ciudades de Chile-lat.txt" para que funcionen correctamente (CandidatoMetricaService.java linea 38 y 39 respectivamente).
Para que funcione debe generar el archivo .war
```
$ gradle war
```

Luego debe agregar este a Glassfish e iniciar el servicio haciendo click en launch.
