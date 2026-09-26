package util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe utilitária para leitura e validação de entrada no console.
 */
public class InputUtil {

    // Scanner estático e compartilhado.
    // Nunca deve ser fechado, pois fechar a entrada do sistema (System.in) impediria novas leituras.
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String lerString(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Entrada inválida. Não pode ser vazia.");
        }
    }

    public static String lerStringOpcional(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Formato numérico inválido. Tente novamente.");
            }
        }
    }

    public static BigDecimal lerBigDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Formato numérico inválido. Tente novamente.");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }

    /**
     * Lê uma data que não pode ser futura. Retorna null se a entrada for deixada em branco.
     */
    public static LocalDate lerDataPassadaOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return null;
            }
            try {
                LocalDate data = LocalDate.parse(entrada, formatter);
                if (data.isAfter(LocalDate.now())) {
                    System.out.println("A data não pode ser futura.");
                    continue;
                }
                return data;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }

    public static int lerIntPositivo(String mensagem) {
        while (true) {
            int valor = lerInt(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("O valor deve ser maior que zero.");
        }
    }

    public static String lerCpf(String mensagem) {
        return lerDocumento(mensagem, false, 11, 11, "CPF inválido. Informe 11 dígitos (ex.: 123.456.789-01).");
    }

    public static String lerCpfOpcional(String mensagem) {
        return lerDocumento(mensagem, true, 11, 11, "CPF inválido. Informe 11 dígitos (ex.: 123.456.789-01).");
    }

    public static String lerCnpj(String mensagem) {
        return lerDocumento(mensagem, false, 14, 14, "CNPJ inválido. Informe 14 dígitos (ex.: 12.345.678/0001-90).");
    }

    public static String lerCnpjOpcional(String mensagem) {
        return lerDocumento(mensagem, true, 14, 14, "CNPJ inválido. Informe 14 dígitos (ex.: 12.345.678/0001-90).");
    }

    public static String lerTelefone(String mensagem) {
        return lerDocumento(mensagem, false, 10, 11, "Telefone inválido. Informe DDD + número, 10 ou 11 dígitos (ex.: (47) 99999-9999).");
    }

    public static String lerTelefoneOpcional(String mensagem) {
        return lerDocumento(mensagem, true, 10, 11, "Telefone inválido. Informe DDD + número, 10 ou 11 dígitos (ex.: (47) 99999-9999).");
    }

    /**
     * Aceita apenas dígitos e os separadores . - / ( ) e espaço, exigindo entre minDigitos e maxDigitos dígitos.
     * Se opcional, entrada em branco retorna string vazia.
     */
    private static String lerDocumento(String mensagem, boolean opcional, int minDigitos, int maxDigitos, String erro) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                if (opcional) {
                    return entrada;
                }
                System.out.println("Entrada inválida. Não pode ser vazia.");
                continue;
            }
            int digitos = entrada.replaceAll("\\D", "").length();
            if (entrada.matches("[0-9./()\\- ]+") && digitos >= minDigitos && digitos <= maxDigitos) {
                return entrada;
            }
            System.out.println(erro);
        }
    }
}
