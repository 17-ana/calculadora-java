import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import org.junit.Before;

public class CalculadoraTest {
    
    private Calculadora calc;
    
    @Before
    public void setUp() {
        calc = new Calculadora();
    }
    
    // Pruebas unitarias básicas
    @Test
    public void testSumar() {
        assertEquals(9.0, calc.sumar(4, 5), 0.001);
    }
    
    @Test
    public void testRestar() {
        assertEquals(2.0, calc.restar(5, 3), 0.001);
    }
    
    @Test
    public void testMultiplicar() {
        assertEquals(15.0, calc.multiplicar(3, 5), 0.001);
    }
    
    @Test
    public void testDividir() {
        assertEquals(4.0, calc.dividir(20, 5), 0.001);
    }
    
    // Prueba para caso especial - División por cero
    @Test
    public void testDivisionPorCero() {
        assertThrows(ArithmeticException.class, () -> {
            calc.dividir(10, 0);
        });
    }
    
    // PRUEBAS DE RENDIMIENTO
    @Test
    public void testRendimientoSuma() {
        long startTime = System.nanoTime();
        
        // Ejecutar 100,000 operaciones de suma
        for (int i = 0; i < 100000; i++) {
            calc.sumar(i, i + 1);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convertir a milisegundos
        
        System.out.println("Tiempo para 100,000 sumas: " + duration + " ms");
        
        // Verificar que el tiempo sea razonable (menos de 100 ms)
        assertEquals(true, duration < 100);
    }
    
    @Test
    public void testRendimientoOperacionesMixtas() {
        long startTime = System.nanoTime();
        
        // Ejecutar operaciones mixtas
        for (int i = 1; i < 10000; i++) {
            calc.sumar(i, i);
            calc.restar(i * 2, i);
            calc.multiplicar(i, 2);
            if (i != 0) {
                calc.dividir(i * 2, i);
            }
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        
        System.out.println("Tiempo para operaciones mixtas: " + duration + " ms");
        
        // Verificar rendimiento aceptable
        assertEquals(true, duration < 200);
    }
    
    @Test
    public void testRendimientoDivision() {
        long startTime = System.nanoTime();
        
        // Ejecutar divisiones (evitando división por cero)
        for (int i = 1; i < 50000; i++) {
            calc.dividir(i * 2, i);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        
        System.out.println("Tiempo para 50,000 divisiones: " + duration + " ms");
        
        // Verificar rendimiento
        assertEquals(true, duration < 150);
    }
    
    // Prueba de estrés con números grandes
    @Test
    public void testRendimientoNumerosGrandes() {
        long startTime = System.nanoTime();
        
        double largeNumber = 1.23456789e10;
        
        for (int i = 0; i < 10000; i++) {
            calc.sumar(largeNumber, largeNumber);
            calc.multiplicar(largeNumber, 2.5);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        
        System.out.println("Tiempo para operaciones con números grandes: " + duration + " ms");
        
        assertEquals(true, duration < 100);
    }
    
    // Prueba de consistencia en el rendimiento
    @Test
    public void testConsistenciaRendimiento() {
        long totalDuration = 0;
        int iterations = 10;
        
        for (int j = 0; j < iterations; j++) {
            long startTime = System.nanoTime();
            
            for (int i = 0; i < 1000; i++) {
                calc.sumar(i, i);
                calc.multiplicar(i, i);
            }
            
            long endTime = System.nanoTime();
            totalDuration += (endTime - startTime);
        }
        
        long averageDuration = (totalDuration / iterations) / 1000000;
        System.out.println("Tiempo promedio para 1,000 operaciones: " + averageDuration + " ms");
        
        // Verificar consistencia (no debería variar mucho)
        assertEquals(true, averageDuration < 50);
    }
}