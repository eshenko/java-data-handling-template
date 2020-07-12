package com.epam.izh.rd.online.repository;

import java.io.*;
import java.util.Objects;

public class SimpleFileRepository implements FileRepository {
    private long countFiles = 0;
    private long countDir = 1;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директории
     *             * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File file = new File(Objects.requireNonNull(getClass()
                .getClassLoader().getResource(path)).getFile());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                countFilesInDirectory(path + File.separator + f.getName());
            } else {
                countFiles++;
            }
        }
        return countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File file = new File(Objects.requireNonNull(getClass()
                .getClassLoader().getResource(path)).getFile());
        if (!file.exists()) {
            countDir = 0;
        }
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                countDirsInDirectory(path + File.separator + f.getName());
                countDir++;
            }
        }
        return countDir;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + to));
             BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + from))){
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File file = new File(Objects.requireNonNull(getClass()
                .getClassLoader().getResource(path)).getFile() + File.separator + name);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String result = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(getClass()
                .getClassLoader().getResource(fileName)).getFile()))) {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
