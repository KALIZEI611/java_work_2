package HW_Module_06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class FileSearchAndFilterTask {
    private final String searchDirectory;
    private final String searchWord;
    private final String bannedWordsFile;
    private final List<String> errorMessages = new CopyOnWriteArrayList<>();

    public FileSearchAndFilterTask(String searchDirectory, String searchWord, String bannedWordsFile) {
        this.searchDirectory = searchDirectory;
        this.searchWord = searchWord;
        this.bannedWordsFile = bannedWordsFile;
    }

    public OperationStatistics execute() {
        long startTime = System.currentTimeMillis();
        boolean success = false;

        try {
            CountDownLatch searchCompletedLatch = new CountDownLatch(1);
            CountDownLatch filterCompletedLatch = new CountDownLatch(1);

            String mergedFile = "merged_result.txt";
            String finalFile = "filtered_result.txt";

            FileSearcher searcher = new FileSearcher(searchDirectory, searchWord, mergedFile,
                    searchCompletedLatch, errorMessages);
            Thread searchThread = new Thread(searcher);
            ContentFilter filter = new ContentFilter(mergedFile, finalFile, bannedWordsFile,
                    searchCompletedLatch, filterCompletedLatch, errorMessages);
            Thread filterThread = new Thread(filter);

            searchThread.start();
            filterThread.start();

            filterCompletedLatch.await();

            success = searcher.isSuccess() && filter.isSuccess();

            return new OperationStatistics(
                    searchDirectory,
                    searchWord,
                    bannedWordsFile,
                    searcher.getFilesFound(),
                    searcher.getMergedFileSize(),
                    filter.getWordsRemoved(),
                    finalFile,
                    System.currentTimeMillis() - startTime,
                    success,
                    errorMessages
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            errorMessages.add("Операция была прервана");
            return new OperationStatistics(searchDirectory, searchWord, bannedWordsFile,
                    0, 0, 0, "", System.currentTimeMillis() - startTime,
                    false, errorMessages);
        }
    }

    public static class OperationStatistics {
        private final String searchDirectory;
        private final String searchWord;
        private final String bannedWordsFile;
        private final int filesFound;
        private final long mergedFileSize;
        private final int wordsRemoved;
        private final String finalOutputFile;
        private final long executionTime;
        private final boolean success;
        private final List<String> errorMessages;

        public OperationStatistics(String searchDirectory, String searchWord, String bannedWordsFile,
                                   int filesFound, long mergedFileSize, int wordsRemoved,
                                   String finalOutputFile, long executionTime, boolean success,
                                   List<String> errorMessages) {
            this.searchDirectory = searchDirectory;
            this.searchWord = searchWord;
            this.bannedWordsFile = bannedWordsFile;
            this.filesFound = filesFound;
            this.mergedFileSize = mergedFileSize;
            this.wordsRemoved = wordsRemoved;
            this.finalOutputFile = finalOutputFile;
            this.executionTime = executionTime;
            this.success = success;
            this.errorMessages = new ArrayList<>(errorMessages);
        }

        public String getSearchDirectory() {
            return searchDirectory;
        }
        public String getSearchWord() {
            return searchWord;
        }
        public String getBannedWordsFile() {
            return bannedWordsFile;
        }
        public int getFilesFound() {
            return filesFound;
        }
        public long getMergedFileSize() {
            return mergedFileSize;
        }
        public int getWordsRemoved() {
            return wordsRemoved;
        }
        public String getFinalOutputFile() {
            return finalOutputFile;
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
