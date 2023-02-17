package dev.dex;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class CopyTree extends SimpleFileVisitor<Path> {
    private Path src;
    private Path dst;

    public CopyTree(Path src, Path dst) {
        this.src = src;
        this.dst = dst;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path relativizedPath = src.relativize(file);
        Path resolvedPathForCopy = dst.resolve(relativizedPath);

        try {
            Files.copy(file, resolvedPathForCopy);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("visitFile: copy " + file.getFileName() + " to " + dst.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path relativizedPath = src.relativize(dir);
        Path resolvedPathForCopy = dst.resolve(relativizedPath);
        try {
            Files.createDirectory(resolvedPathForCopy);
        } catch (IOException ex) {
            ex.printStackTrace();
            return FileVisitResult.SKIP_SUBTREE;
        }
        System.out.println("preVisitDirectory: copy " + dir.getFileName() + " to " + resolvedPathForCopy.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error: " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}

//public class CopyTree extends SimpleFileVisitor<Path> {
//    Path dst = Paths.get("FileTree\\dir4");
//    @Override
//    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//        dst = Paths.get(dst.toString() + "\\" + file.getFileName());
//        Files.copy(file, dst);
//        System.out.println("visitFile: copy " + file.getFileName() + " to " + dst.toAbsolutePath());
//        dst = dst.getParent();
//        return FileVisitResult.CONTINUE;
//    }
//
//    @Override
//    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//        dst = Paths.get(dst.toString() + "\\" + dir.getFileName());
//        Files.createDirectory(dst);
//        System.out.println("preVisitDirectory: copy " + dir.getFileName() + " to " + dst.toAbsolutePath());
//        return FileVisitResult.CONTINUE;
//    }
//
//    @Override
//    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//        dst = dst.getParent();
//        System.out.println("postVisitDirectory: dst = " + dst.toAbsolutePath());
//        return FileVisitResult.CONTINUE;
//    }
//
//    @Override
//    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//        System.out.println("Error: " + exc.getMessage());
//        return FileVisitResult.CONTINUE;
//    }
//}
