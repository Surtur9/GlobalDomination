# Global Domination - Game

Este es el repositorio del juego Global Domination. Puedes ejecutar el juego descargando el archivo `.jar` proporcionado o compilando los archivos fuente de Java que se encuentran en la carpeta `src`.

## Ejecución del Archivo `.jar`

Para ejecutar el juego, asegúrate de tener instalado Java 8 en tu computadora. Si no tienes Java 8, puedes descargarlo [aquí](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

### Instrucciones para ejecutar el `.jar`:
1. Descarga el archivo `GlobalDomination.jar` desde la carpeta `out/artifacts` o desde los releases de este repositorio.
2. Abre una terminal y navega al directorio donde se encuentre el archivo `.jar` descargado.
3. Ejecuta el siguiente comando:
   ```sh
   java -jar GlobalDomination.jar
   ```

## Instalación de Java 8

Si no tienes Java 8 instalado, sigue estos pasos para instalarlo:
1. Visita [esta página](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).
2. Descarga la versión correspondiente a tu sistema operativo.
3. Sigue las instrucciones de instalación proporcionadas por Oracle.

Para comprobar que Java se ha instalado correctamente, abre una terminal y escribe:
```sh
java -version
```
Asegúrate de que la versión mostrada sea 1.8.

## Compilación y Ejecución del Código Fuente

Si prefieres compilar el código fuente en tu máquina local, sigue estos pasos:

### Paso 1: Clonar el Repositorio

Clona el repositorio en tu máquina local utilizando Git:
```sh
git clone https://github.com/Surtur9/GlobalDomination.git
```

### Paso 2: Navegar a la Carpeta `src`

Dentro del repositorio, encontrarás una carpeta llamada `src` que contiene todos los archivos fuente (`.java`) y la carpeta `resources` con los archivos necesarios para el juego.

### Paso 3: Compilar el Código

1. Navega a la carpeta raíz del repositorio donde se encuentra la carpeta `src`.
2. Usa el siguiente comando para compilar todos los archivos `.java`:
   ```sh
   javac -d out src/*.java
   ```
   Esto compilará todos los archivos `.java` y colocará los archivos `.class` en la carpeta `out`.

### Paso 4: Ejecutar el Juego

Una vez compilado el código, puedes ejecutar el juego con el siguiente comando:
```sh
java -cp out PantallaInicial
```

## Estructura del Repositorio

- `src/`: Contiene todos los archivos fuente (`.java`) y la carpeta `resources` con todos los recursos necesarios para el juego.
- `out/artifacts/`: Contiene el archivo `.jar` listo para ser ejecutado.
- `README.md`: Este archivo con instrucciones para ejecutar el juego.

## Notas

- **Java 8**: Asegúrate de tener Java 8 instalado, ya que el juego fue desarrollado y probado con esta versión.
- **Compatibilidad**: El juego puede no ser compatible con versiones más nuevas de Java debido a cambios en la API y características del lenguaje.

Si tienes algún problema para ejecutar el juego, no dudes en abrir un **Issue** en el repositorio.

[Repositorio en GitHub](https://github.com/Surtur9/GlobalDomination)

