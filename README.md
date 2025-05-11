# 💱 Conversor de Monedas

Este proyecto permite convertir entre distintas monedas utilizando la API de [ExchangeRate API](https://www.exchangerate-api.com/). Incluye un menú interactivo y una opción para convertir monedas no listadas directamente.

---

## 🔧 Configuración del proyecto

Este proyecto utiliza una clave de API para conectarse con ExchangeRate API. Para mantener la clave segura y evitar subirla al repositorio, debes seguir estos pasos:

### 1. Crear el archivo `config.properties`

Crea un archivo llamado `config.properties` en la raíz del proyecto (junto al archivo `.java`) con el siguiente contenido:

```
API_KEY=TU_API_KEY_AQUI
```

🔐 **¡No compartas este archivo ni subas tu API Key a GitHub!**

---

### 2. Verifica que esté en `.gitignore`

Este archivo ya está listado en `.gitignore`, por lo que **no será subido** al repositorio:

```
config.properties
```

---

### 3. Usa el archivo de ejemplo

Este repositorio incluye un archivo `config-example.properties` que muestra el formato esperado. Solo debes copiarlo y renombrarlo:

```bash
cp config-example.properties config.properties
```

Luego edita el nuevo archivo y reemplaza `TU_API_KEY_AQUI` por tu clave real.

---

## ▶️ Instrucciones de uso

Una vez configurado el proyecto en el paso anterior puedes hacer uso del conversor.

1. Inicia el programa.
2. Elige una opción del menú.
3. Indica el monto de monedas que deseas transformar.
4. El programa mostrará el resultado por pantalla.

---

## ❓ Cómo usar la opción "Convertir a una moneda no enlistada"

1. Elige la opción **"Convertir a una moneda no enlistada"** en el menú principal.
2. Indica el tipo de moneda que deseas convertir (ej: `CLP`).
3. Indica el monto que deseas convertir.
4. Indica la moneda de destino (ej: `USD`).
5. El resultado se mostrará por pantalla.

---

## 📦 Requisitos

- Java 8 o superior
- Conexión a Internet

---

## 🔐 Seguridad

Recuerda **no subir tu archivo `config.properties`** ni compartir tu clave de API.
