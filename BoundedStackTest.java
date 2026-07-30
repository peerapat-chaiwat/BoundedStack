import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;



public class BoundedStackTest {

    private static int passed = 0 ;
    private static int failed = 0 ;





private static void check(String name, boolean condition){
    if(condition){
        passed++;
        System.out.println("[PASS" + name);
    }else {
        failed++;
        System.out.println("[FAIL]" + name);
    }
}
    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea PlaylistTest\n");
        }
        System.out.println("=== Foodlist Test Suite ===\n");

        testCreators();
        testAdd();
        testRemove();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);

    
    }
}
    private static void testCreators() {
        System.out.println("--Creators--");

        BoundedStack empty = new BoundedStack(20);
        check("new() -> stack must be 0",empty.size() == 0);
        check("new( -> stack must be emoty",empty.isEmpty() == true);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testCreators'");
    }


    private static void testAdd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testAdd'");
    }


    private static void testRemove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testRemove'");
    }


    private static void testObservers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testObservers'");
    }


    private static void testProducer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testProducer'");
    }


    private static void testExposure() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testExposure'");
    }
}

