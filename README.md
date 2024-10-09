# Simon Dice - Android Game

## Descripción

Simon Dice es un juego interactivo para Android basado en la clásica mecánica del juego de Simon. El objetivo es recordar y repetir una secuencia de colores generada aleatoriamente. Los jugadores deben tocar los colores en el mismo orden en que se presentan. El juego aumenta la dificultad a medida que se avanza, y se lleva un registro del puntaje más alto alcanzado.

## Características

- **Interfaz de usuario**: Diseñado con Jetpack Compose para una experiencia de usuario moderna y fluida.
- **Generación de secuencias**: El juego genera una secuencia de colores aleatorios para que el jugador la repita.
- **Feedback**: Mensajes claros que indican si la entrada del jugador es correcta o incorrecta.
- **Registro de puntuación**: Mantiene un registro del puntaje más alto alcanzado durante el juego.
- **Reinicio del juego**: Opción para reiniciar el juego y generar una nueva secuencia al tocar el botón "Start".

## Estructura del Proyecto

- **MainActivity**: La actividad principal que configura la interfaz del juego.
- **SimonGameScreen**: Composable que contiene la lógica del juego, donde se gestionan las secuencias y la entrada del usuario.
- **SimonButtons**: Composable que representa los botones de colores que el jugador puede tocar.
- **ColorButton**: Composable que define el aspecto de cada botón de color.
- **Record**: Clase que gestiona la puntuación más alta alcanzada.

## Requisitos

- Android Studio
- Kotlin
- Jetpack Compose

## Cómo Ejecutar

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Asegúrate de tener un dispositivo Android conectado o un emulador configurado.
4. Ejecuta la aplicación desde Android Studio.
