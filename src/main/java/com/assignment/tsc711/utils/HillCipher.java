package com.assignment.tsc711.utils;

import java.util.Scanner;

public class HillCipher {
    private final String invalidKeyMatrix = "Invalid key matrix. Please enter a matrix with a non-zero determinant modulo 26.";

    private final int[][] keyMatrix;
    private boolean[] isUppercase;

    public HillCipher(int[][] keyMatrix) throws Exception {
        if (!isValidKeyMatrix(keyMatrix)) {
            System.out.println(invalidKeyMatrix);
            throw new Exception(invalidKeyMatrix);
        }
        this.keyMatrix = keyMatrix;
    }

    private static boolean isValidKeyMatrix(int[][] keyMatrix) {
        int determinant = findDeterminant(keyMatrix);
        return gcd(determinant, 26) == 1;
    }

    public String encrypt(String plaintext) {
        long startTime = System.currentTimeMillis();
        // Convert plaintext to uppercase and pad with 'X' if necessary
        this.isUppercase = new boolean[plaintext.length()];
        for (int i = 0; i < plaintext.length(); i++) {
            this.isUppercase[i] = Character.isUpperCase(plaintext.charAt(i));
        }
        String result = transformText(plaintext, this.keyMatrix, true);
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;
        System.out.println("Processing time: " + processingTime + " ms");
        return result;
    }

    public String decrypt(String encryptedText) {
        long startTime = System.currentTimeMillis();
        String decryptedText = transformText(encryptedText, findInverseMatrix(this.keyMatrix), true);

        // Restore the original case of the input plaintext
        StringBuilder originalCaseDecryptedText = new StringBuilder();
        for (int i = 0; i < decryptedText.length(); i++) {
            char c = decryptedText.charAt(i);
            originalCaseDecryptedText.append(this.isUppercase[i] ? c : Character.toLowerCase(c));
        }
        String result = originalCaseDecryptedText.toString();
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;
        System.out.println("Processing time: " + processingTime + " ms");
        // Convert decryptedText to the original case of the input plaintext
        return result;
    }
    private static String transformText(String text, int[][] keyMatrix, boolean encrypt) {
        StringBuilder transformedText = new StringBuilder();
        int blockSize = keyMatrix.length;

        for (int i = 0; i < text.length(); i += blockSize) {
            int[] block = new int[blockSize];
            int validChars = 0;
            for (int j = 0; j < blockSize && i + j < text.length(); j++) {
                char c = text.charAt(i + j);
                if (Character.isLetter(c)) {
                    block[j] = Character.toUpperCase(c) - 'A';
                    validChars++;
                } else {
                    block[j] = -1;
                }
            }

            if (validChars == blockSize || !encrypt) {
                int[] transformedBlock = multiplyMatrix(keyMatrix, block, encrypt);
                for (int j = 0; j < blockSize && i + j < text.length(); j++) {
                    if (block[j] != -1) {
                        transformedText.append((char) ((transformedBlock[j] % 26 + 26) % 26 + 'A'));
                    } else {
                        transformedText.append(text.charAt(i + j));
                    }
                }
            } else {
                for (int j = 0; j < blockSize && i + j < text.length(); j++) {
                    transformedText.append(text.charAt(i + j));
                }
            }
        }

        return transformedText.toString();
    }

    private static int[] multiplyMatrix(int[][] matrix, int[] vector, boolean encrypt) {
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (vector[j] != -1) {
                    sum += matrix[i][j] * (encrypt ? vector[j] : (vector[j] + 26) % 26);
                }
            }
            result[i] = sum % 26;
        }
        return result;
    }

    private static int[][] findInverseMatrix(int[][] matrix) {
        int determinant = findDeterminant(matrix);
        int inverseDeterminant = findModularInverse(determinant, 26);

        if (inverseDeterminant == -1) {
            return null;
        }

        int[][] adjugate = findAdjugate(matrix);
        int[][] inverseMatrix = new int[adjugate.length][adjugate.length];

        for (int i = 0; i < adjugate.length; i++) {
            for
            (int j = 0; j < adjugate[i].length; j++) {
                inverseMatrix[i][j] = (adjugate[i][j] * inverseDeterminant) % 26;
                if (inverseMatrix[i][j] < 0) {
                    inverseMatrix[i][j] += 26;
                }
            }
        }
        return inverseMatrix;
    }

    private static int findDeterminant(int[][] matrix) {
        int determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
                matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        return (determinant % 26 + 26) % 26;
    }

    private static int[][] findAdjugate(int[][] matrix) {
        int[][] adjugate = new int[matrix.length][matrix.length];

        adjugate[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
        adjugate[0][1] = matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2];
        adjugate[0][2] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
        adjugate[1][0] = matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2];
        adjugate[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
        adjugate[1][2] = matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2];
        adjugate[2][0] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
        adjugate[2][1] = matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1];
        adjugate[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        return adjugate;
    }

    private static int findModularInverse(int a, int m) {
        a = (a % m + m) % m;

        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}