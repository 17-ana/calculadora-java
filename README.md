# Calculadora Java

Proyecto de calculadora básica en Java con pruebas unitarias.

## Funcionalidades
- Suma
- Resta  
- Multiplicación
- División

### Pipeline de CI

Este proyecto incluye un pipeline de integración continua que se ejecuta en GitHub Actions. El pipeline:

- Compila el código fuente y las pruebas
- Ejecuta pruebas automatizadas con JUnit
- Informa si las pruebas pasan o fallan

Puedes ver los resultados en la pestaña "Actions" del repositorio.

## Notificaciones de fallos

Este pipeline incluye una integración con Slack. Si las pruebas fallan, se envía automáticamente un mensaje al canal #devops-alertas con el estado del pipeline.

La URL del webhook está protegida como secreto en GitHub.

# Despliegue con Blue-Green

## Estrategia de Despliegue

Este proyecto implementa **Blue-Green Deployment** usando Railway para lograr despliegues con cero tiempo de inactividad.

### Entornos:
- **BLUE** (Producción): https://calculadora-blue.up.railway.app
- **GREEN** (Staging): https://calculadora-green.up.railway.app

### Pipeline CI/CD:
- **Pruebas automáticas** con JUnit en cada push
- **Build automático** del proyecto Java
- **Despliegue automático** a entorno GREEN
- **Estrategia Blue-Green** para rollback inmediato
