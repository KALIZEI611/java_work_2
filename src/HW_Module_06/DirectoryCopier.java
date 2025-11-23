package HW_Module_06;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

public class DirectoryCopier implements Runnable {
    private final String sourceDir;
    private final String targetDir;
    private final List<String> errorMessages;

    private final AtomicInteger filesCopied = new AtomicInteger(0);
    private final AtomicInteger directoriesCreated = new AtomicInteger(0);
    private final AtomicLong totalBytesCopied = new AtomicLong(0);
    private boolean success = false;

    public int getFilesCopied() {
        return filesCopied.get();
    }
    public int getDirectoriesCreated() {
        return directoriesCreated.get();
    }
    public long getTotalBytesCopied() {
        return totalBytesCopied.get();
    }
    public boolean isSuccess() {
        return success;
    }

    public DirectoryCopier(String sourceDir, String targetDir, List<String> errorMessages) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.errorMessages = errorMessages;
    }

    @Override
    public void run() {
        System.out.println("Поток копирования директории запущен...");
        System.out.println("Источник: " + sourceDir);
        System.out.println("Цель: " + targetDir);

        try {
            copyDirectory(new File(sourceDir), new File(targetDir));
            success = true;
            System.out.println("Копирование завершено успешно!");
        } catch (Exception e) {
            errorMessages.add("Ошибка при копировании: " + e.getMessage());
            System.err.println("Ошибка при копировании: " + e.getMessage());
        }
    }

    private void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            if (target.mkdirs()) {
                directoriesCreated.incrementAndGet();
                System.out.println("Создана директория: " + target.getAbsolutePath());
            } else {
                throw new IOException("Не удалось создать директорию: " + target.getAbsolutePath());
            }
        }

        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                File targetFile = new File(target, file.getName());

                if (file.isDirectory()) {
                    copyDirectory(file, targetFile);
                } else {
                    copyFile(file, targetFile);
                }
            }
        }
    }

    private void copyFile(File sourceFile, File targetFile) throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            long fileSize = 0;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                fileSize += bytesRead;

                if (fileSize > 100000) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Копирование прервано");
                    }
                }
            }

            filesCopied.incrementAndGet();
            totalBytesCopied.addAndGet(fileSize);

            System.out.printf("Скопирован файл: %s (%d байт)%n", sourceFile.getName(), fileSize);

        } catch (IOException e) {
            errorMessages.add("Ошибка при копировании файла " + sourceFile.getName() + ": " + e.getMessage());
            throw e;
        }
    }

}