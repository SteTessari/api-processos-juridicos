package com.example.api_processos_juridicos.utils;

public class DocumentoUtils {
    public static boolean isCpf(String documento) {
        return documento.length() == 11 && validarCpf(documento);
    }

    public static boolean isCnpj(String documento) {
        return documento.length() == 14 && validarCnpj(documento);
    }

    private static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.chars().allMatch(Character::isDigit)) {
            return false;
        }

        // Verifica se o CPF contém 11 dígitos iguais
        String primeiroDigito = cpf.substring(0, 1);
        if (cpf.equals(primeiroDigito.repeat(11))) {
            return false;
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        primeiroDigitoVerificador = (primeiroDigitoVerificador >= 10) ? 0 : primeiroDigitoVerificador;

        if (digitos[9] != primeiroDigitoVerificador) return false;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        segundoDigitoVerificador = (segundoDigitoVerificador >= 10) ? 0 : segundoDigitoVerificador;

        return digitos[10] == segundoDigitoVerificador;
    }



    private static boolean validarCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.chars().allMatch(Character::isDigit)) {
            return false;
        }

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma1 = 0;
        for (int i = 0; i < 12; i++) {
            soma1 += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        }
        int digito1 = 11 - (soma1 % 11);
        digito1 = (digito1 >= 10) ? 0 : digito1;

        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma2 = 0;
        for (int i = 0; i < 13; i++) {
            soma2 += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        }
        int digito2 = 11 - (soma2 % 11);
        digito2 = (digito2 >= 10) ? 0 : digito2;

        return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
                digito2 == Character.getNumericValue(cnpj.charAt(13));
    }

}
