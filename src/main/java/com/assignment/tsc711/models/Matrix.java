package com.assignment.tsc711.models;

public class Matrix {
    private int[][] matrix;
    private int rows;
    private int cols;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    public int[][] getArray() {
        return matrix;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.matrix[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(Matrix m) {
        Matrix result = new Matrix(rows, m.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                int sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += matrix[i][k] * m.matrix[k][j];
                }
                result.matrix[i][j] = sum % 26;
            }
        }
        return result;
    }

    public Matrix inverse() {
        int det = determinant(matrix);
        if (det == 0) {
            throw new ArithmeticException("Matrix is not invertible");
        }
        int[][] adj = adjugate(matrix);
        Matrix adjMatrix = new Matrix(adj);
        Matrix result = adjMatrix.transpose().scalarMultiply(modularInverse(det));
        return result;
    }

    private int determinant(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        int det = 0;
        for (int i = 0; i < n; i++) {
            det += ((i % 2 == 0) ? 1 : -1) * matrix[0][i] * determinant(getSubmatrix(matrix, 0, i));
        }
        return det;
    }

    private int[][] getSubmatrix(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] submatrix = new int[n - 1][n - 1];
        int r = 0;
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (i != row) {
                c = 0;
                for (int j = 0; j < n; j++) {
                    if (j != col) {
                        submatrix[r][c] = matrix[i][j];
                        c++;
                    }
                }
                r++;
            }
        }
        return submatrix;
    }

    private int[][] adjugate(int[][] matrix) {
        int n = matrix.length;
        int[][] adj = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adj[i][j] = ((i + j) % 2 == 0 ? 1 : -1) * determinant(getSubmatrix(matrix, i, j));
            }
        }
        return adj;
    }

    private int modularInverse(int a) {
        a = a % 26;
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                return i;
            }
        }
        return 1;
    }

    public Matrix scalarMultiply(int scalar) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.matrix[i][j] = (matrix[i][j] * scalar) % 26;
            }
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] keyMatrix = {{1, 2}, {3, 4}};
        Matrix key = new Matrix(keyMatrix);
        Matrix plaintext = new Matrix(new int[][]{{7}, {19}});
        Matrix ciphertext = key.multiply(plaintext);
        ciphertext.print();
    }
}
