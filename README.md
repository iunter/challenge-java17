# challenge-java17

## Buildeo y deploy de la aplicacion

Correr los siguientes comandos en orden desde la carpeta root del proyecto

```shell script
./mvnw clean install
```
```shell script
./mvnw package
```
En este punto los tests correrán automaticamente y se generará el jar del proyecto.

Para correr la aplicación ingresar el siguiente comando.
```shell script
java -jar target/quarkus-app/quarkus-run.jar
```
Al levantarse la aplicación se debería ver lo siguiente en la terminal de su sistema

COMPLETAR CON FOTO
## Swagger

Para ingresar a la UI de swagger levantar la aplicación y dirigirse  a http://localhost:8080/q/swagger-ui/#/ 

COMPLETAR CON FOTO

## Consola de h2
La aplicacion ya cuenta con un pequeño init de datos (init.sql), en caso de querer expandir los datos existentes se puede
realizar desde la [consola de h2](http://localhost:8080/h2) 

USER = root

PASSWORD = password

#### IMPORTANTE:  Al cortar la ejecucion de la aplicación se perderan los cambios realizados en la base de datos.