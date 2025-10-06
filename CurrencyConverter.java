import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CurrencyConverter {

    
    private static final String API_KEY = "1e9e5c5476ef22d676dbdfe3";
    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Real-Time Currency Converter ---");
        System.out.print("Enter the Base Currency (e.g., USD, EUR, GBP): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the Target Currency (e.g., JPY, CAD, INR): ");
        String targetCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the amount to convert: ");
        double amount = 0;
        if (scanner.hasNextDouble()) {
            amount = scanner.nextDouble();
        } else {
            System.out.println("Invalid amount. Exiting.");
            scanner.close();
            return;
        }

        try {
            double rate = fetchConversionRate(baseCurrency, targetCurrency);
            double convertedAmount = amount * rate;
            
            System.out.println("\n--- Conversion Result ---");
            System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
            System.out.println("-------------------------\n");

        } catch (Exception e) {
            System.out.println("\nAn error occurred during conversion.");
            System.out.println("Possible reasons: Invalid currency code, connection issue, or expired API key.");
        } finally {
            scanner.close();
        }
    }

    private static double fetchConversionRate(String base, String target) throws Exception {
        String urlString = API_BASE_URL + API_KEY + "/latest/" + base;
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        int responseCode = connection.getResponseCode();
        
        if (responseCode != 200) {
            throw new Exception("HTTP GET Request Failed with code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);

        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
        
        if (rates.has(target)) {
             return rates.get(target).getAsDouble();
        } else {
             throw new Exception("Target currency not found in API response.");
        }
    }
}