# Explicación del Proyecto de Calculadora

## Estructura por Capas

El proyecto sigue una arquitectura por capas que separa las responsabilidades:

### 1. Capa de Modelo (`model`)
- **Operacion**: Data class que almacena:
    - `operacion`: String con la expresión matemática (ej. "5 + 3")
    - `resultado`: Double con el resultado
    - `fecha`: Timestamp del momento de la operación
- **Operadores**: Enum que define los operadores matemáticos soportados (+, -, *, /)

### 2. Capa de Acceso a Datos (DAO - `data/dao`)
- **IOperaciones**: Interfaz que define:
    - `registrarOperacion(operacion: Operacion)`
    - `obtenerHistorial(): List<Operacion>`
- **OperacionesDAOH2**: Implementación concreta que persiste en una base de datos H2

### 3. Capa de Acceso a Datos (DB - `data/DB`)
- Utiliza un ``DriverManager`` para abrir y cerrar conexiones a prueba de excepciones

### 4. Capa de Servicio (`service`)
- **IOperService**: Interfaz con métodos idénticos al DAO pero con propósito diferente (lógica de negocio)
- **OperService**: Implementación que:
    - Recibe `IOperaciones` por constructor (inyección de dependencias)
    - Delega las operaciones al DAO (patrón delegación)

### 5. Capa de Aplicación (`app`)
- **Calculadora**: Clase principal que:
    - Maneja la interacción con el usuario
    - Realiza los cálculos matemáticos
    - Registra operaciones mediante `IOperService`
    - Maneja excepciones personalizadas (`InfoCalcException`)

### 6. Capa de UI (`ui`)
- **IEntradaSalida**: Interfaz para entrada/salida
- **Consola**: Implementación concreta para consola

### 7. Utilidades (`utils`)
- **ControlFichero**/**GestionFicheros**: Para manejo de logs y archivos (Ya no se utiliza ahora se utiliza el H2)

## Flujo de Datos

1. **Aplicación (Calculadora)**:
    - Recibe input del usuario
    - Realiza cálculos
    - Crea objetos `Operacion`

2. **Servicio (OperService)**:
    - Recibe la operación de la capa de aplicación
    - Podría añadir lógica de negocio (validaciones, transformaciones)
    - Delega al DAO

3. **DAO (OperacionesDAOH2)**:
    - Persiste la operación en la base de datos
    - Recupera el historial cuando se solicita

## Ejemplo de Flujo Completo

1. Usuario introduce "5 + 3"
2. `Calculadora` crea un objeto `Operacion("5 + 3", 8.0, fechaActual)`
3. Llama a `operService.registrarOperacion(operacion)`
4. `OperService` delega a `operDAO.registrarOperacion(operacion)`
5. `OperacionesDAOH2` guarda en la base de datos H2
6. Cuando se pide el historial, el flujo es inverso:
   DAO → Service → Aplicación → UI