package com.assignment.tsc711.utils;

import java.util.Arrays;

public class ColumnarTranspositionCipher {
    private final String keyword;
    private final int[] key;

    public ColumnarTranspositionCipher(String keyword) {
        this.keyword = keyword;
        this.key = getKeyPositions(keyword);
    }

    // this function use columnar transposition cipher algorithm for encryption

    public String encrypt(String plaintext) {
        long startTime = System.currentTimeMillis();
        int[] key = getKeyPositions(this.keyword);
        int columns = this.keyword.length();
        int rows = (plaintext.length() + columns - 1) / columns;
        int paddingLength = (rows * columns) - plaintext.length();

        char[][] matrix = new char[rows][columns];
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (index < plaintext.length()) {
                    matrix[row][col] = plaintext.charAt(index);
                } else {
                    matrix[row][col] = 'X'; // Padding character
                }
                index++;
            }
        }

        StringBuilder encryptedText = new StringBuilder();
        for (int pos : key) {
            for (int row = 0; row < rows; row++) {
                encryptedText.append(matrix[row][pos]);
            }
        }
        // Add the padding length to the end of the encrypted message
        encryptedText.append((char)('A' + paddingLength));
        String result = encryptedText.toString();
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;
        System.out.println("Processing time: " + processingTime + " ms");
        return result;
    }

    public String decrypt(String encryptedTextWithPadding) {
        long startTime = System.currentTimeMillis();
        int paddingLength = encryptedTextWithPadding.charAt(encryptedTextWithPadding.length() - 1) - 'A';
        String encryptedText = encryptedTextWithPadding.substring(0, encryptedTextWithPadding.length() - 1);

        int[] key = getKeyPositions(this.keyword);
        int columns = this.keyword.length();
        int rows = encryptedText.length() / columns;

        char[][] matrix = new char[rows][columns];
        int index = 0;

        for (int pos : key) {
            for (int row = 0; row < rows; row++) {
                matrix[row][pos] = encryptedText.charAt(index);
                index++;
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        int totalChars = rows * columns - paddingLength;
        int charCount = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (charCount < totalChars) {
                    decryptedText.append(matrix[row][col]);
                    charCount++;
                } else {
                    break;
                }
            }
        }
        String result = decryptedText.toString();
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;
        System.out.println("Processing time: " + processingTime + " ms");
        return result;
    }

    // return an array of position based on the keyworld.
    // split keyword into individual characters, sort them,
    // then find the index of each character in the original keyword
    // The found index is the stored in the positions array
    // each matched character is replaced with _ for avoid future match.
    // array that contain key position is returned
    private static int[] getKeyPositions(String keyword) {
        String[] keyArray = keyword.split("");
        Arrays.sort(keyArray);
        String sortedKeyword = String.join("", keyArray);

        int[] positions = new int[keyword.length()];
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            int newIndex = sortedKeyword.indexOf(c);
            positions[i] = newIndex;

            // Replace the character so it won't be matched again
            sortedKeyword = sortedKeyword.substring(0, newIndex) + '_' + sortedKeyword.substring(newIndex + 1);
        }
        return positions;
    }
}
