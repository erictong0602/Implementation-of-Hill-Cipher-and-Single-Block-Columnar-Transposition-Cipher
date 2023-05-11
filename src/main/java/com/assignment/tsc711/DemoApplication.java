package com.assignment.tsc711;

import com.assignment.tsc711.utils.ColumnarTranspositionCipher;
import com.assignment.tsc711.utils.HillCipher;

public class DemoApplication {
    private final static String defaultMessage = "Oracle Releases Java 17, the Latest Version of the Popular Programming Language\n\n" +
            "Oracle has released Java 17, the latest version of the popular programming language. This release includes " +
            "new features, enhancements, and bug fixes, making it a significant update for Java developers.\n\n" +
            "One of the key new features in Java 17 is support for sealed classes and interfaces. Sealed classes and " +
            "interfaces restrict the set of classes and interfaces that can extend or implement them, providing " +
            "enhanced security and maintainability. This feature has been a long time coming, with the JEP (Java " +
            "Enhancement Proposal) for sealed classes first proposed back in 2017.\n\n" +
            "Another major addition to Java 17 is the introduction of a new foreign memory access API. This API " +
            "provides a safe and efficient way to access memory outside of the Java heap, which is particularly " +
            "useful for working with native libraries and low-level systems programming.\n\n" +
            "Java 17 also includes a number of smaller improvements and bug fixes. For example, the language now " +
            "supports new syntax for switch statements, allowing for more concise and expressive code. There are " +
            "also updates to the garbage collector, improved Unicode support, and various performance improvements.\n\n" +
            "This release is part of Oracle's commitment to continue supporting and developing Java, which remains " +
            "one of the most popular programming languages in the world. According to the TIOBE Index for September " +
            "2021, Java is currently the second most popular language, behind only Python.\n\n" +
            "Java 17 is a long-term support (LTS) release, which means it will be supported for a longer period " +
            "than non-LTS releases. The LTS release schedule is designed to give developers more stability and " +
            "predictability, with LTS releases receiving updates and bug fixes for a period of several years. " +
            "This should make Java 17 a particularly attractive option for enterprise applications and other " +
            "large-scale projects.\n\n" +
            "Developers can download Java 17 from the Oracle website, or through their preferred package manager. " +
            "It's also worth noting that Java 17 requires Java SE 11 or later, so developers will need to update " +
            "their installations if they're running an older version.\n\n" +
            "Overall, Java 17 is a significant update that should make the language more secure, efficient, and " +
            "expressive. With its long-term support status, it's likely to be a popular choice for Java developers " +
            "for years to come.";
    private final static String columnarKeyword = "Magic123456789123456789123456789";

    public static void main(String[] args) throws Exception {
        System.out.println("Original Message: " + defaultMessage);

        ColumnarTranspositionCipher cipher = new ColumnarTranspositionCipher(columnarKeyword);

        HillCipher hillCipher = new HillCipher(new int[][]{{17, 17, 5}, {21, 18, 21}, {2, 2, 19}});

        String encryptedText = hillCipher.encrypt(defaultMessage);
//        System.out.println("Hill Cipher Encrypted Message: " + encryptedText);

        encryptedText = cipher.encrypt(encryptedText);
//        System.out.println("ColumnarTransposition Cipher Encrypted Message: " + encryptedText);

        String decryptedText = cipher.decrypt(encryptedText);
//        System.out.println("ColumnarTransposition Cipher Decrypted Message: " + decryptedText);

        decryptedText = hillCipher.decrypt(decryptedText);
//        System.out.println("Hill Cipher Decrypted Message: " + decryptedText);
        System.out.println("Encryption result: "+decryptedText.equals(defaultMessage));
    }
}
