import com.company.model.Asset;
import com.company.model.Dependency;
import com.company.service.impl.DependencyManagementImpl;
import org.junit.jupiter.api.Test;

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
    public void testSuccessfulInsertions(){
        setup();
        assertEquals(4, dependencyManagement.getDependencies().size());
    }

    @Test
    public void testDuplicateInsertionFails(){
        setup();
        Asset m1 = new Asset("m1");
        Asset m2 = new Asset("m2");
        Dependency m2_m1 = new Dependency(m2, m1);
        assertEquals(false, dependencyManagement.insertDependency(m2_m1));
    }

    @Test
    public void testCircularDependencies() {
        setup();
        Asset m1 = new Asset("m1");
        Asset d1 = new Asset("d1");
        Asset m2 = new Asset("m2");
        Dependency circularDependency1 = new Dependency(d1, m1);
        Dependency circularDependency2 = new Dependency(d1, m2);

        boolean successfulCircularDependencyAdd1 = dependencyManagement.insertDependency(circularDependency1);
        boolean successfulCircularDependencyAdd2 = dependencyManagement.insertDependency(circularDependency2);

        assertEquals(false, successfulCircularDependencyAdd1);
        assertEquals(false, successfulCircularDependencyAdd2);
    }

    @Test
    public void testSuccessfulMayDeleteCheck() {
        setup();
        Asset m1 = new Asset("m1");
        Asset m2 = new Asset("m2");
        Asset d1 = new Asset("d1");
        Asset d2 = new Asset("d2");
        Asset a1 = new Asset("a1");

        assertEquals(false, dependencyManagement.mayDelete(m1));
        assertEquals(false, dependencyManagement.mayDelete(d1));
        assertEquals(true, dependencyManagement.mayDelete(m2));
        assertEquals(false, dependencyManagement.mayDelete(d2));
        assertEquals(false, dependencyManagement.mayDelete(a1));
    }
}
