package HW_Module_06;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DirectoryCopyTask {
    private final String sourceDir;
    private final String targetDir;
    private final List<String> errorMessages = new CopyOnWriteArrayList<>();

    public DirectoryCopyTask(String sourceDir, String targetDir) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
    }

    public CopyStatistics execute() {
        long startTime = System.currentTimeMillis();
        boolean success = false;

        try {
            File source = new File(sourceDir);
            if (!source.exists() || !source.isDirectory()) {
                errorMessages.add("Исходная директория не существует или не является директорией: " + sourceDir);
                return new CopyStatistics(sourceDir, targetDir, 0, 0, 0,
                        System.currentTimeMillis() - startTime, false, errorMessages);
            }

            File target = new File(targetDir);
            if (!target.exists()) {
                if (!target.mkdirs()) {
                    errorMessages.add("Не удалось создать целевую директорию: " + targetDir);
                    return new CopyStatistics(sourceDir, targetDir, 0, 0, 0,
                            System.currentTimeMillis() - startTime, false, errorMessages);
                }
            }

            DirectoryCopier copier = new DirectoryCopier(sourceDir, targetDir, errorMessages);
            Thread copyThread = new Thread(copier);
            copyThread.start();
            copyThread.join();
            success = copier.isSuccess();

            return new CopyStatistics(
                    sourceDir,
                    targetDir,
                    copier.getFilesCopied(),
                    copier.getDirectoriesCreated(),
                    copier.getTotalBytesCopied(),
                    System.currentTimeMillis() - startTime,
                    success,
                    errorMessages
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            errorMessages.add("Операция была прервана");
            return new CopyStatistics(sourceDir, targetDir, 0, 0, 0,
                    System.currentTimeMillis() - startTime, false, errorMessages);
        }
    }

    public static class CopyStatistics {
        private final String sourceDirectory;
        private final String targetDirectory;
        private final int filesCopied;
        private final int directoriesCreated;
        private final long totalBytesCopied;
        private final long executionTime;
        private final boolean success;
        private final List<String> errorMessages;

        public CopyStatistics(String sourceDirectory, String targetDirectory,
                              int filesCopied, int directoriesCreated, long totalBytesCopied,
                              long executionTime, boolean success, List<String> errorMessages) {
            this.sourceDirectory = sourceDirectory;
            this.targetDirectory = targetDirectory;
            this.filesCopied = filesCopied;
            this.directoriesCreated = directoriesCreated;
            this.totalBytesCopied = totalBytesCopied;
            this.executionTime = executionTime;
            this.success = success;
            this.errorMessages = new ArrayList<>(errorMessages);
        }

        public String getSourceDirectory() {
            return sourceDirectory;
        }
        public String getTargetDirectory() {
            return targetDirectory;
        }
        public int getFilesCopied() {
            return filesCopied;
        }
        public int getDirectoriesCreated() {
            return directoriesCreated;
        }
        public long getTotalBytesCopied() {
            return totalBytesCopied;
        }
        public long getExecutionTime() {
            return executionTime;
        }
        public boolean isSuccess() {
            return success;
        }
        public List<String> getErrorMessages() {
            return new ArrayList<>(errorMessages);
        }
    }
}
