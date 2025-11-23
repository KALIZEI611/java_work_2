package HW_Module_06;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

public class AdvancedDirectoryCopier implements Runnable {
    private final String sourceDir;
    private final String targetDir;
    private final List<String> errorMessages;

    private final AtomicInteger filesCopied = new AtomicInteger(0);
    private final AtomicInteger directoriesCreated = new AtomicInteger(0);
    private final AtomicLong totalBytesCopied = new AtomicLong(0);
    private final AtomicInteger totalFiles = new AtomicInteger(0);
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
    public int getTotalFiles() {
        return totalFiles.get();
    }
    public boolean isSuccess() {
        return success;
    }

    public AdvancedDirectoryCopier(String sourceDir, String targetDir, List<String> errorMessages) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.errorMessages = errorMessages;
    }

    @Override
    public void run() {
        System.out.println("Поток копирования директории запущен...");

        try {
            countFiles(new File(sourceDir));
            System.out.println("Всего файлов для копирования: " + totalFiles.get());

            copyDirectory(new File(sourceDir), new File(targetDir));
            success = true;

            System.out.println("\nКопирование завершено успешно!");

        } catch (Exception e) {
            errorMessages.add("Ошибка при копировании: " + e.getMessage());
            System.err.println("Ошибка при копировании: " + e.getMessage());
        }
    }

    private void countFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    countFiles(file);
                } else {
                    totalFiles.incrementAndGet();
                }
            }
        }
    }

    private void copyDirectory(File source, File target) throws IOException {
        if (!target.exists() && target.mkdirs()) {
            directoriesCreated.incrementAndGet();
        }

        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                File targetFile = new File(target, file.getName());

                if (file.isDirectory()) {
                    copyDirectory(file, targetFile);
                } else {
                    copyFile(file, targetFile);

                    int copied = filesCopied.get();
                    int total = totalFiles.get();
                    if (total > 0) {
                        int progress = (int) ((double) copied / total * 100);
                        System.out.printf("Прогресс: %d/%d (%d%%)%n", copied, total, progress);
                    }
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
            }

            filesCopied.incrementAndGet();
            totalBytesCopied.addAndGet(fileSize);

        } catch (IOException e) {
            errorMessages.add("Ошибка при копировании файла " + sourceFile.getName() + ": " + e.getMessage());
            throw e;
        }
    }


}