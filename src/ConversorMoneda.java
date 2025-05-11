import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class ConversorMoneda {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        String apiKey = "";
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            apiKey = prop.getProperty("API_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                System.out.println("La API Key no se encontró en config.properties.");
                return;
            }
        } catch (IOException ex) {
            System.out.println("Error al leer config.properties: " + ex.getMessage());
            return;
        }

        while (!salir) {

            System.out.println("\n====== MOTORNIKO EXCHANGE ======");
            System.out.println("-- Convierte cualquier moneda! --");
            System.out.println("=====================================");
            System.out.println("(1) Dólar >> Peso argentino");
            System.out.println("(2) Peso argentino => Dólar");
            System.out.println("(3) Dólar => Real brasileño");
            System.out.println("(4) Real brasileño => Dólar");
            System.out.println("(5) Dólar => Peso colombiano");
            System.out.println("(6) Peso colombiano => Dólar");
            System.out.println("(7) Convertir a una moneda no enlistada.");
            System.out.println("(8) Salir");
            System.out.println("===========================================");
            System.out.print("Elija una opción: ");

            String opcion = scanner.nextLine();
            String monedaOrigen = "", monedaDestino = "";

            switch (opcion) {
                case "1": monedaOrigen = "USD"; monedaDestino = "ARS"; break;
                case "2": monedaOrigen = "ARS"; monedaDestino = "USD"; break;
                case "3": monedaOrigen = "USD"; monedaDestino = "BRL"; break;
                case "4": monedaOrigen = "BRL"; monedaDestino = "USD"; break;
                case "5": monedaOrigen = "USD"; monedaDestino = "COP"; break;
                case "6": monedaOrigen = "COP"; monedaDestino = "USD"; break;
                case "8":
                    System.out.println("has salido de MOTORNIKO EXCHANGE.");
                    salir = true;
                    continue;
                case "7":
                    while (true) {
                        System.out.print("Ingrese la moneda de origen (ej: USD): ");
                        monedaOrigen = scanner.nextLine().trim().toUpperCase();
                        if (monedaOrigen.matches("^[A-Z]{3}$")) break;
                        else System.out.println("Código no válido. Intente de nuevo.");
                    }
                    while (true) {
                        System.out.print("Convertir a (ej: EUR): ");
                        monedaDestino = scanner.nextLine().trim().toUpperCase();
                        if (monedaDestino.matches("^[A-Z]{3}$")) break;
                        else System.out.println("Código de moneda incorrecto.");
                    }
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    continue;
            }

            double cantidad = 0;
            while (true) {
                System.out.print("Ingrese la cantidad a convertir: ");
                String entrada = scanner.nextLine().trim();
                try {
                    cantidad = Double.parseDouble(entrada);
                    if (cantidad > 0) break;
                    else System.out.println("La cantidad debe ser mayor a 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
                }
            }

            System.out.println("Convirtiendo... espere un momento.");

            try {
                String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaOrigen;
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    respuesta.append(linea);
                }
                reader.close();

                String json = respuesta.toString();
                String claveBusqueda = "\"" + monedaDestino + "\":";
                int index = json.indexOf(claveBusqueda);
                if (index != -1) {
                    int start = json.indexOf(":", index) + 1;
                    int end = json.indexOf(",", start);
                    if (end == -1) end = json.indexOf("}", start);
                    String tasaStr = json.substring(start, end).trim();
                    double tasa = Double.parseDouble(tasaStr);
                    double resultado = cantidad * tasa;
                    System.out.printf("\n%.2f %s = %.2f %s\n", cantidad, monedaOrigen, resultado, monedaDestino);
                } else {
                    System.out.println("No se encontró la tasa de conversión para " + monedaDestino + ".");
                }

            } catch (Exception e) {
                System.out.println("Error al realizar la conversión: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
