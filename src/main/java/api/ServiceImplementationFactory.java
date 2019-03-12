package api;

import service.Service;
import service.ServiceSourceCode;

/**
 * My service implementation factory class
 */
public class ServiceImplementationFactory {

    // class loader
    final private MemoryClassLoader memoryClassLoader = new MemoryClassLoader();

    // source file
    final private SourceFile sourceFile = new SourceFile();

    // singleton
    final private static ServiceImplementationFactory SINGLETON = new ServiceImplementationFactory();

    private ServiceImplementationFactory() {

        // compile source file and register resulting class to this class loader
        memoryClassLoader.addSourceFile(sourceFile);

    }

    private Class<?> getClassOnTheFly() {

        // register the class loader to the current thread
        Thread.currentThread().setContextClassLoader(memoryClassLoader);

        // return class
        try {
            return memoryClassLoader.findClass(ServiceSourceCode.CANONICAL_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Service createServiceImplementation() {
        try {

            // get class
            Class<?> clazz = SINGLETON.getClassOnTheFly();

            // create object
            Object instance = clazz.newInstance();

            // cast and return
            return (Service) instance;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
