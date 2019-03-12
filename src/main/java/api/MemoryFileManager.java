package api;

import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject.Kind;

class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, OutputFile> map = new HashMap<String, OutputFile>();

    MemoryFileManager(JavaCompiler compiler) {
        super(compiler.getStandardFileManager(null, null, null));
    }

    @Override
    public OutputFile getJavaFileForOutput(Location location, String className,
                                           Kind kind, FileObject source) {

        // get output file
        OutputFile outputFile = map.get(className);

        // not found ? => create and register one
        if (outputFile == null) {
            outputFile = new OutputFile(className, kind);
            map.put(className, outputFile);
        }

        // return result
        return outputFile;

    }

    Map<String, OutputFile> getMap() {
        return map;
    }

}