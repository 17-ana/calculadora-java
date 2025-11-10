import static spark.Spark.*;

public class WebCalculator {
    public static void main(String[] args) {
        try {
            int port = getPort();
            System.out.println("Iniciando aplicación en puerto: " + port);
            port(port);
            
            // Habilitar CORS para desarrollo
            options("/*", (req, res) -> {
                return "OK";
            });
            
            before((req, res) -> {
                System.out.println("📨 Request: " + req.requestMethod() + " " + req.url());
                System.out.println("Query: " + req.queryString());
            });
            
            get("/", (req, res) -> {
                return "Calculadora Java - Usa /calculadora?operacion=suma&a=5&b=3";
            });
            
            get("/health", (req, res) -> {
                res.status(200);
                return "OK";
            });
            
            get("/calculadora", (req, res) -> {
                System.out.println("🔍 Parámetros recibidos:");
                System.out.println("operacion: " + req.queryParams("operacion"));
                System.out.println("a: " + req.queryParams("a"));
                System.out.println("b: " + req.queryParams("b"));
                
                String operacion = req.queryParams("operacion");
                String aParam = req.queryParams("a");
                String bParam = req.queryParams("b");
                
                if (operacion == null || aParam == null || bParam == null) {
                    res.status(400);
                    return "Parámetros faltantes. Use: /calculadora?operacion=suma&a=5&b=3";
                }
                
                try {
                    double a = Double.parseDouble(aParam);
                    double b = Double.parseDouble(bParam);
                    Calculadora calc = new Calculadora();
                    double resultado = 0;
                    
                    switch(operacion.toLowerCase()) {
                        case "suma":
                            resultado = calc.sumar(a, b);
                            break;
                        case "resta":
                            resultado = calc.restar(a, b);
                            break;
                        case "multiplicacion":
                            resultado = calc.multiplicar(a, b);
                            break;
                        case "division":
                            resultado = calc.dividir(a, b);
                            break;
                        default:
                            res.status(400);
                            return "Operación no válida. Use: suma, resta, multiplicacion, division";
                    }
                    
                    return String.format("{\"resultado\": %.2f, \"operacion\": \"%s\"}", resultado, operacion);
                    
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "Error: a y b deben ser números";
                } catch (ArithmeticException e) {
                    res.status(400);
                    return "Error: " + e.getMessage();
                }
            });
            
            // Ruta de fallback para debugging
            get("*", (req, res) -> {
                return "Ruta no encontrada: " + req.pathInfo() + ". Rutas disponibles: /, /health, /calculadora";
            });
            
            awaitInitialization();
            System.out.println("✅ Spark iniciado en puerto: " + port);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}