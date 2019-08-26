# Dependencias
* Java 8
* Gradle 5.6

# Configuración
## En el archivo application.yml están las variables para las credenciales de foursquare

# Instrucciones para correr
## Con gradle
* En la raíz, comando gradle bootRun
## Con el .jar
* Construir el .jar con el comando gradle bootJar
* Ejecutar el .jar con el comando java -jar build/libs/brubank-hotels.jar
## Ejemplo para ejecutar
* http://localhost:8080/app/reservationsForDestination?city=Buenos Aires&country=Argentina

# Mejoras
* La base de datos es un SqlLite, cuando, dada la naturaleza de los datos, convendría usar una base de datos de documentos (Cassandra con partitionKeypor destino?). No lo hice para no 'pelearme' con la portabilidad. No encontré base de datos de documentos para usar o en memoria o con archivo. Podría haber utilizado docker con el docker-compose, pero preferí simplificar
* Hay que normalizar las respuestas, el caso más urgente es el destino de las reservas del servicio de Brubank
* Si las reservas crecen, hay que paginar
* Mejor manejo de exepciones
* Definiciones del contrato. Qué hacer cuando no hay reservas para el destino? Devolver los Hoteles?
