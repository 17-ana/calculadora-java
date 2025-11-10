import static spark.Spark.*;

public class WebCalculator {
    public static void main(String[] args) {
        int port = getPort();
        System.out.println("🚀 Iniciando aplicación en puerto: " + port);
        
        port(port);
        
        get("/", (req, res) -> {
            System.out.println("✅ Request recibido en /");
            return "Calculadora Java - Usa /calculadora?operacion=suma&a=5&b=3";
        });
        
        get("/health", (req, res) -> {
            System.out.println("✅ Request recibido en /health");
            res.status(200);
            return "OK";
        });
        
        get("/calculadora", (req, res) -> {
            System.out.println("✅ Request recibido en /calculadora");
            String operacion = req.queryParams("operacion");
            double a = Double.parseDouble(req.queryParams("a"));
            double b = Double.parseDouble(req.queryParams("b"));
            
            Calculadora calc = new Calculadora();
            double resultado = 0;
            
            switch(operacion) {
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
                    return "Operación no válida";
            }
            
            return String.format("Resultado: %.2f", resultado);
        });
        
        // Agrega esto al final del main
        awaitInitialization();
        System.out.println("✅ Spark iniciado correctamente en puerto: " + port);
        System.out.println("📡 Aplicación lista para recibir requests");
    }
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}