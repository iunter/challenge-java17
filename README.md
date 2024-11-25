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

![App levantada](https://github.com/user-attachments/assets/9b1b713f-1be0-4875-8d36-719ecbea6264)

#### Se recomienda testear el endpoint mediante la ui de swagger o mediante aplicaciones como 'Postman'

## Swagger
Se documentó la aplicación utilizando swagger.

Para ingresar a la UI de swagger levantar la aplicación y dirigirse  a http://localhost:8080/q/swagger-ui/#/ 

![swaggerui](https://github.com/user-attachments/assets/2e51b241-68cc-4411-a5f7-ea9eb8066435)


## Consola de h2
La aplicacion ya cuenta con un pequeño init de datos (init.sql), en caso de querer expandir los datos existentes se puede
realizar desde la [consola de h2](http://localhost:8080/h2) 

USER = root

PASSWORD = password

#### IMPORTANTE:  Al cortar la ejecucion de la aplicación se perderan los cambios realizados en la base de datos.

![h2 console login](https://github.com/user-attachments/assets/30475905-2fa2-4023-a7c5-14f01d775cd4)

![consola h2](https://github.com/user-attachments/assets/cc0ed8ed-8ae8-4ba1-93da-8481996f4937)

