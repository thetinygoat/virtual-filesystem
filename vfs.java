import java.io.File;
import java.util.HashSet;

public class vfs {
    public static class Blob {
        String path;
        HashSet<Blob> children = new HashSet<>();

        Blob(String path) {
            this.path = path;
        }
    }

    public static Blob createVirtualFileSystem(String path, String name) {

        File r = new File(path);
        File[] list = r.listFiles();
        Blob root = new Blob(name);
        for (File f : list) {
            if (f.isDirectory()) {
                root.children.add(createVirtualFileSystem(f.getAbsolutePath(), f.getName()));
            } else {
                root.children.add(new Blob(f.getName()));
            }
        }

        return root;
    }

    public static void display(Blob root) {
        System.out.print(root.path + " -> ");
        for (Blob el : root.children) {
            System.out.print(el.path + " / ");
        }
        System.out.println();
        for (Blob el : root.children) {
            display(el);
        }
    }

    public static void main(String[] args) {
        Blob root = createVirtualFileSystem("/home/thetinygoat/Documents", "Documents");
        display(root);
    }

}