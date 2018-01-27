import com.company.model.Asset;
import com.company.model.Dependency;
import com.company.service.impl.DependencyManagementImpl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetDependencyManagementTests {

    private DependencyManagementImpl dependencyManagement = DependencyManagementImpl.getInstance();

    public void setup() {
        Asset m1 = new Asset("m1");
        Asset m2 = new Asset("m2");
        Asset d1 = new Asset("d1");
        Asset d2 = new Asset("d2");

        Dependency m1_d1 = new Dependency(m1, d1);
        Dependency m1_d2 = new Dependency(m1, d2);
        Dependency m2_m1 = new Dependency(m2, m1);
        Dependency m2_d2 = new Dependency(m2, d2);

        dependencyManagement.insertDependency(m1_d1);
        dependencyManagement.insertDependency(m1_d2);
        dependencyManagement.insertDependency(m2_d2);
        dependencyManagement.insertDependency(m2_m1);
    }

    @Test
    public void testSuccessfulInsertions() throws InterruptedException {
        setup();
        Thread.sleep(300);
        assertEquals(4, dependencyManagement.getDependencies().size());
    }

    @Test
    public void testDuplicateInsertionFails() throws ExecutionException, InterruptedException {
        setup();
        Asset m1 = new Asset("m1");
        Asset m2 = new Asset("m2");
        Dependency m2_m1 = new Dependency(m2, m1);
        Future<Boolean> futureInsert = dependencyManagement.insertDependency(m2_m1);
        if(futureInsert.isDone()) {
            assertEquals(false, futureInsert.get());
        }
    }

    @Test
    public void testCircularDependencies() throws ExecutionException, InterruptedException {
        setup();
        Asset m1 = new Asset("m1");
        Asset d1 = new Asset("d1");
        Asset m2 = new Asset("m2");
        Dependency circularDependency1 = new Dependency(d1, m1);
        Dependency circularDependency2 = new Dependency(d1, m2);

        Future<Boolean> successfulCircularDependencyAdd1 = dependencyManagement.insertDependency(circularDependency1);
        Future<Boolean> successfulCircularDependencyAdd2 = dependencyManagement.insertDependency(circularDependency2);

        if(successfulCircularDependencyAdd1.isDone() && successfulCircularDependencyAdd2.isDone()) {
            assertEquals(false, successfulCircularDependencyAdd1.get());
            assertEquals(false, successfulCircularDependencyAdd2.get());
        }
    }

    @Test
    public void testSuccessfulMayDeleteCheck() {
        setup();
        Asset m1 = new Asset("m1");
        Asset m2 = new Asset("m2");
        Asset d1 = new Asset("d1");
        Asset d2 = new Asset("d2");
        Asset a1 = new Asset("a1");
        Future<Boolean> m1Future = dependencyManagement.mayDelete(m1);
        Future<Boolean> d1Future = dependencyManagement.mayDelete(d1);
        Future<Boolean> m2Future = dependencyManagement.mayDelete(m2);
        Future<Boolean> d2Future = dependencyManagement.mayDelete(d2);
        Future<Boolean> a1Future = dependencyManagement.mayDelete(a1);
        if (m1Future.isDone() && d1Future.isDone() && m2Future.isDone() && d2Future.isDone() && a1Future.isDone()) {
            assertEquals(false, m1Future);
            assertEquals(false, d1Future);
            assertEquals(true, m2Future);
            assertEquals(false, d2Future);
            assertEquals(false, a1Future);
        }
    }
}
