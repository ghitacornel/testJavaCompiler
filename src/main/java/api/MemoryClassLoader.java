package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

class MemoryClassLoader extends ClassLoader {

    private final JavaCompiler compiler;

    private final MemoryFileManager filemanager;

    // defined on the fly classes
    final private Map<String, Class<?>> definedClasses = new HashMap<String, Class<?>>();

    MemoryClassLoader() {

        // get compiler
        compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new NullPointerException("Null default compiler, please run with an SDK not a JRE");
        }

        // create file manager
        filemanager = new MemoryFileManager(compiler);

    }

    void addSourceFile(SourceFile source) {
        List<SourceFile> list = new ArrayList<SourceFile>();
        list.add(source);
        compiler.getTask(null, filemanager, null, null, null, list).call();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {

            // already defined ?
            Class<?> definedClass = definedClasses.get(name);
            if (definedClass != null) {
                return definedClass;
            }

            // not defined
            synchronized (filemanager) {

                OutputFile outputFile = filemanager.getMap().get(name);
                if (outputFile == null) {
                    throw new ClassNotFoundException("could not find class " + name);
                }

                byte[] array = outputFile.toByteArray();

                // define a new class
                Class<?> newClass = defineClass(name, array, 0, array.length);

                // register the new class
                definedClasses.put(name, newClass);

                // return the newly defined and registered class
                return newClass;

            }

        }

    }
}