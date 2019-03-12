import api.ServiceImplementationFactory;
import org.junit.Assert;
import org.junit.Test;
import service.Service;

public class TestJavaCompilerCodeGeneration {

    @Test
    public void testGeneratedClass() {

        Service service1 = ServiceImplementationFactory.createServiceImplementation();
        Assert.assertNotNull(service1);
        Assert.assertEquals(service1 + "test data 1", service1.execute("test data 1"));

        Service service2 = ServiceImplementationFactory.createServiceImplementation();
        Assert.assertNotNull(service2);
        Assert.assertEquals(service2 + "test data 2", service2.execute("test data 2"));

        Assert.assertNotSame(service1, service2);

        // class is not generated twice
        Assert.assertSame(service1.getClass(), service2.getClass());

        // class has expected name
        Assert.assertEquals("on.the.fly.GeneratedServiceImplementation", service1.getClass().getCanonicalName());

    }
}