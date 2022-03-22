# Arquetipo Maven para proyecto multi-módulo con un módulo de JPA

Este arquetipo se ha diseñado para la entrega de la tarea T1 de la asignatura de Sistemas de Información para Internet de 3º del Grado de Ingeniería Informática de la Universidad de Málaga.

## Instalación del arquetipo

Para instalar el arquetipo en el repositorio de Maven local basta con seguir los siguientes pasos:

1. Clonamos el reppositorio con el siguiente comando (también se puede descargar el código fuente como un fichero Zip)
```
git clone git@github.com/jfrchicanog/jpa-multi-archetype
```
2. Usando la línea de comandos nos movemos a la carpeta donde se haya clonado el código del arquetipo.
```
cd jpa-ulti-archetype
```
3. Por último, ejecutamos maven con la fase `install` para que incluya el arquetipo en el repositorio local:
```
mvn install
```

## Ejecución del arquetipo

Una vez instalado, para generar un nuevo proyecto usando el arquetipo debemos ir al directorio donde queremos crear el proyecto y ejecutamos lo siguiente (__cuidado__: el comando siguiente funciona en Linux y Mac, en Windows hay que sustituir las `\` por `^`):
```
mvn archetype:generate \
-DarchetypeGroupId=es.uma.informatica.sii \
-DarchetypeArtifactId=jpa-archetype \
-DarchetypeVersion=1.0.1 \
-DgroupId=[groupId del proyecto nuevo] \
-DartifactId=[artifactId del proyecto nuevo] \
-Dversion=[versión del proyecto nuevo] \
-Dpackage=[paquete de donde colgará todo el código del nuevo proyecto]
```
En el comando de arriba hay que establecer los valores de las propiedades `groupId`, `artifactId`, `version` y `package` para el proyecto nuevo.

Lo siguiente es un ejemplo concreto estableciendo el valor para esos parámetros (__cuidado__: el comando siguiente funciona en Linux y Mac, en Windows hay que sustituir las `\` por `^`):

```
mvn archetype:generate \
-DarchetypeGroupId=es.uma.informatica.sii \
-DarchetypeArtifactId=jpa-archetype \
-DarchetypeVersion=1.0.1 \
-DgroupId=es.uma \
-DartifactId=proyecto \
-Dversion=1.0.0-SNAPSHOT \
-Dpackage=es.uma.proyecto
```
