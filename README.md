#Ejercicio autenticacion Global Logic

##Requisitos Previos
Java JDK 11
Gradle 7.4

##Construcción
Para construir el proyecto, ejecutar el siguiente comando en la raíz del proyecto:

    ./gradlew build
##Ejecución
Para ejecutar el proyecto el comando es:

    java -jar autenticadorgl1.0-SNAPSHOT

luego se accedera en la ruta http://localhost:8080
    
##Base de Datos
El proyecto utiliza H2 como base de datos en tiempo de ejecución. No se requiere configuración adicional para la base de datos para el entorno de desarrollo.

##Pruebas
Para ejecutar las pruebas, utiliza:

    ./gradlew test

##Endpoint
### POST - /users/sign-up
Este endpoint es para registrar nuevos usuarios en el sistema.

Datos de Entrada:

 - name: Nombre del usuario (opcional).
 - email: Correo electrónico del usuario.
 - password: Contraseña del usuario, que debe tener solo una Mayúscula y solamente dos números (no necesariamente consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8.
 - phones: Lista de teléfonos, cada uno con número, código de ciudad y código de país (opcional).

        curl --location --request POST 'localhost:8080/users/sign-up' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "name": "test",
            "email": "test@test.cl",
            "password": "a2asSffdfd2f",
            "phones": [
                {
                    "number": 1,
                    "citycode": 56,
                    "countrycode": "CL"
                }
            ]
        }'
### POST - /users/sign-up
Este endpoint es para autenticarte en el sistema y generar token.

Datos de entrada:

Cuerpo:
 - email: Correo electrónico del usuario.
 - password: Contraseña del usuario.
 
Cabecera:
 - token: El token obtenido en el proceso de registro o inicio de sesión previo.

        curl --location --request POST 'localhost:8080/auth/login' \
        --header 'token: Bearer TOKEN-GENERADO' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "email": "test@test.cl",
            "password": "a2asSffdfd2f"
        }' 
