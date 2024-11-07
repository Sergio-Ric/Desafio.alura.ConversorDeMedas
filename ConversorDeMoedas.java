
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConversorDeMoedas {


    private static final String API_URL = "https://v6.exchangerate-api.com/v6/ca35681f931b839159394e77/latest/USD";


    public static String obterRespostaDaAPI() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println("Erro ao obter as taxas de câmbio: " + e.getMessage());
            return null;
        }
    }

    public static Map<String, Double> obterTaxasDeCambio() {
        String respostaJson = obterRespostaDaAPI();
        if (respostaJson == null) {
            return null;
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(respostaJson, JsonObject.class);
        JsonObject taxasJson = jsonObject.getAsJsonObject("conversion_rates");

        return gson.fromJson(taxasJson, Map.class);
    }

    public static int exibirMenuEObterEscolha() {
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        System.out.println("Selecione uma das opções de conversão:");
        System.out.println("1. Dólar Americano (USD) para Euro (EUR)");
        System.out.println("2. Dólar Americano (USD) para Real Brasileiro (BRL)");
        System.out.println("3. Dólar Americano (USD) para Iene Japonês (JPY)");
        System.out.println("4. Dólar Americano (USD) para Libra Esterlina (GBP)");
        System.out.println("5. Dólar Americano (USD) para Dólar Canadense (CAD)");
        System.out.println("6. Dólar Americano (USD) para Franco Suíço (CHF)");

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nDigite o número da opção desejada: ");
        return scanner.nextInt();
    }

    public static void converterMoeda() {
        Map<String, Double> taxas = obterTaxasDeCambio();
        if (taxas == null) {
            return;
        }

        int escolha = exibirMenuEObterEscolha();
        Scanner scanner = new Scanner(System.in);

        String moedaCodigo = "";
        String moedaNome = "";

        switch (escolha) {
            case 1:
                moedaCodigo = "EUR";
                moedaNome = "Euro";
                break;
            case 2:
                moedaCodigo = "BRL";
                moedaNome = "Real Brasileiro";
                break;
            case 3:
                moedaCodigo = "JPY";
                moedaNome = "Iene Japonês";
                break;
            case 4:
                moedaCodigo = "GBP";
                moedaNome = "Libra Esterlina";
                break;
            case 5:
                moedaCodigo = "CAD";
                moedaNome = "Dólar Canadense";
                break;
            case 6:
                moedaCodigo = "CHF";
                moedaNome = "Franco Suíço";
                break;
            default:
                System.out.println("Opção inválida! Por favor, escolha um número de 1 a 6.");
                return;
        }

        System.out.print("\nDigite o valor em USD para converter em " + moedaNome + ": ");
        double valorUSD = scanner.nextDouble();
        double taxaConversao = taxas.get(moedaCodigo);
        double valorConvertido = valorUSD * taxaConversao;

        System.out.printf( "\n" + valorUSD + " USD equivale a " + valorConvertido + " " + moedaNome);
    }

    public static void main(String[] args) {
        converterMoeda();
    }
}
