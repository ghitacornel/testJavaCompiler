package api;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Output class file
 *
 * @author Cornel
 */
class OutputFile extends SimpleJavaFileObject {

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    OutputFile(String name, Kind kind) {
        super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
    }

    byte[] toByteArray() {
        return stream.toByteArray();
    }

    @Override
    public ByteArrayOutputStream openOutputStream() {
        return stream;
    }
}