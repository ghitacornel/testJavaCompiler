package service;

/**
 * Service implementation source code for service.Service
 */
public class ServiceSourceCode {

    final private static String PACKAGE_NAME = "on.the.fly";
    final private static String CLASS_NAME = "GeneratedServiceImplementation";

    final public static String CANONICAL_CLASS_NAME = PACKAGE_NAME + "." + CLASS_NAME;
    public static String CODE = "";

    // that's the Java code
    static {
        CODE += "package " + PACKAGE_NAME + ";";
        CODE += "public class " + CLASS_NAME + " implements service.Service { ";
        CODE += "public String execute(String x){";
        CODE += "return this.toString()+x;";
        CODE += "}}";
    }
}
