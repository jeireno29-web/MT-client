package com.mtclient;

import java.nio.file.Files;
import java.nio.file.Path;

/** Handles runtime directories and Java executable discovery. */
public final class RuntimeManager {
    public Path rootDirectory() {
        String home = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return Path.of(home, "AppData", "Roaming", ".mtclient");
        if (os.contains("mac")) return Path.of(home, "Library", "Application Support", "mtclient");
        return Path.of(home, ".mtclient");
    }

    public String findJava() {
        String override = System.getenv("MT_CLIENT_JAVA_HOME");
        if (override != null && !override.isBlank()) {
            Path java = Path.of(override, "bin", executableName());
            if (Files.isExecutable(java)) return java.toString();
        }

        String javaHome = System.getProperty("java.home");
        if (javaHome != null) {
            Path java = Path.of(javaHome, "bin", executableName());
            if (Files.isExecutable(java)) return java.toString();
        }
        return executableName();
    }

    public boolean is64Bit() {
        return "64".equals(System.getProperty("sun.arch.data.model", "64"));
    }

    private String executableName() {
        return System.getProperty("os.name").toLowerCase().contains("win") ? "java.exe" : "java";
    }
}
