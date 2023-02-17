package dev.dex;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("FileTree" + File.separator
                        +"dir2"),
                p -> Files.isRegularFile(p))) {
            for (Path file: stream) {
                if (Files.isDirectory(file)) {

                }
                System.out.println(file.getFileName());
            }
        }
        catch (IOException | DirectoryIteratorException ex) {
            ex.printStackTrace();
        }

        String separator = File.separator;
        System.out.println(separator);
        separator = FileSystems.getDefault().getSeparator();
        System.out.println(separator);

        try {
            Path tempFile = Files.createTempFile("myapp", ".appext");
            System.out.println("Temporary file path = " + tempFile.toAbsolutePath());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        for (FileStore store: stores) {
            System.out.println(store);
            System.out.println(store.name());
        }

        Iterable<Path> rootPaths = FileSystems.getDefault().getRootDirectories();
        for (Path path: rootPaths) {
            System.out.println(path);
        }

        System.out.println("---Walking Tree for dir2---");
        Path dir2Path = Paths.get("FileTree\\dir2");
        try {
            Files.walkFileTree(dir2Path, new PrintNames());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

//        System.out.println("---Copying Entire Tree");
//        try {
//            Files.walkFileTree(dir2Path, new CopyTree(dir2Path,
//                    Paths.get("FileTree\\dir4\\dir2_copy")));
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }

        File file = new File("FileTree\\file1.txt");
        Path convertedPath = file.toPath();
        System.out.println("ConvertedPath = " + convertedPath);

        File parent = new File("FileTree");
        File resolvedFile = new File(parent, "dir1\\file1.txt");
        System.out.println(resolvedFile.toPath());

        resolvedFile = new File("FileTree\\dir1\\file1.txt");
        System.out.println(resolvedFile.toPath());

        Path parentPath = Paths.get("FileTree");
        Path childRelativePath = Paths.get("dir1\\file1.txt");
        System.out.println(parentPath.resolve(childRelativePath));

        File workingDirectory = new File("").getAbsoluteFile();
        System.out.println("Working directory = " + workingDirectory.getAbsolutePath());

        System.out.println("--- print dir2 contents using list() ---");
        File dir2File = new File(workingDirectory, "FileTree/Dir2");
        String dir2[] = dir2File.list();
        System.out.println(Arrays.toString(dir2));

        System.out.println("--- print dir2 contents using listFiles() ---");
        File dir2Files[] = dir2File.listFiles();
        for (var f: dir2Files) {
            System.out.println(f.getName());
        }

    }
}
