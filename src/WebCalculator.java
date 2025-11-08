import static spark.Spark.*;

public class WebCalculator {
    public static void main(String[] args) {
        port(getPort());
        
        get("/", (req, res) -> {
            return "Calculadora Java - Usa /calculadora?operacion=suma&a=5&b=3";
        });
        
        get("/health", (req, res) -> {
            res.status(200);
            return "OK";
        });
        
        get("/calculadora", (req, res) -> {
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
    }
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}