/**
 * Test runner สำหรับ BoundedStack
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed Leaw: " + passed);
        System.out.println("Failed Leaw: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // --- Partition: capacity ปกติ / capacity ผิดเงื่อนไข ---
    private static void testCreators() {
        System.out.println("-- Creators --");

        BoundedStack s = new BoundedStack(5);
        check("new(5) -> empty", s.isEmpty());
        check("new(5) -> size 0", s.size() == 0);
        check("new(5) -> capacity 5", s.getCapacity() == 5);
        check("new(5) -> not full", !s.isFull());

        // boundary: capacity = 1 คือขอบล่างที่ถูกต้อง
        BoundedStack one = new BoundedStack(1);
        check("new(1) -> capacity 1", one.getCapacity() == 1);

        // capacity ผิดเงื่อนไขต้องโยน exception
        boolean threwZero = false;
        try {
            new BoundedStack(0);
        } catch (IllegalArgumentException e) {
            threwZero = true;
        }
        check("new(0) -> throws IllegalArgumentException", threwZero);

        boolean threwNegative = false;
        try {
            new BoundedStack(-3);
        } catch (IllegalArgumentException e) {
            threwNegative = true;
        }
        check("new(-3) -> throws IllegalArgumentException", threwNegative);
    }

    // --- Mutator: pushItem ต้องรักษาลำดับและกันของผิดเงื่อนไข ---
    private static void testPush() {
        System.out.println("\n-- Push --");

        BoundedStack s = new BoundedStack(3);
        s.pushItem(BoundedStack.BURGER);
        check("push(BURGER) -> size 1", s.size() == 1);
        check("push(BURGER) -> top is BURGER", s.peekTopItem() == BoundedStack.BURGER);

        s.pushItem(BoundedStack.FRIES);
        check("push(FRIES) -> top becomes FRIES", s.peekTopItem() == BoundedStack.FRIES);
        check("push -> size 2", s.size() == 2);

        // รหัสเมนูที่ไม่ถูกต้องต้องโยน exception
        boolean threwInvalid = false;
        try {
            s.pushItem(99);
        } catch (IllegalArgumentException e) {
            threwInvalid = true;
        }
        check("push(invalid item) -> throws IllegalArgumentException", threwInvalid);
        check("failed push leaves size unchanged", s.size() == 2);

        // boundary: ดันจนเต็มพอดีแล้วดันเพิ่ม
        s.pushItem(BoundedStack.COMBO_MEAL);
        check("can fill up to capacity", s.isFull());
        check("full stack -> size equals capacity", s.size() == s.getCapacity());

        boolean threwFull = false;
        try {
            s.pushItem(BoundedStack.NUGGETS);
        } catch (IllegalStateException e) {
            threwFull = true;
        }
        check("push when full -> throws IllegalStateException", threwFull);
        check("failed push on full stack leaves size unchanged", s.size() == 3);
    }

    // --- Mutator: popItem ทั้งกรณีปกติและกรณีว่างเปล่า ---
    private static void testPop() {
        System.out.println("\n-- Pop --");

        BoundedStack s = new BoundedStack(3);
        s.pushItem(BoundedStack.BURGER);
        s.pushItem(BoundedStack.FRIES);
        s.pushItem(BoundedStack.NUGGETS);

        check("pop -> returns last pushed item (NUGGETS)",
                s.popItem() == BoundedStack.NUGGETS);
        check("pop -> size decreases", s.size() == 2);
        check("pop -> next top is FRIES", s.peekTopItem() == BoundedStack.FRIES);

        check("pop -> returns FRIES", s.popItem() == BoundedStack.FRIES);
        check("pop -> returns BURGER", s.popItem() == BoundedStack.BURGER);

        // boundary: pop จนว่างเปล่า
        check("stack is empty after popping everything", s.isEmpty());

        boolean threwEmpty = false;
        try {
            s.popItem();
        } catch (IllegalStateException e) {
            threwEmpty = true;
        }
        check("pop on empty stack -> throws IllegalStateException", threwEmpty);
    }

    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack s = new BoundedStack(4);
        s.pushItem(BoundedStack.BURGER);
        s.pushItem(BoundedStack.COMBO_MEAL);

        check("size reports 2", s.size() == 2);
        check("getCapacity reports 4", s.getCapacity() == 4);
        check("peekTopItem shows top without removing", s.peekTopItem() == BoundedStack.COMBO_MEAL);
        check("isEmpty is false when items exist", !s.isEmpty());
        check("isFull is false when not full", !s.isFull());

        int before = s.size();
        s.size();
        s.getCapacity();
        s.peekTopItem();
        s.isEmpty();
        s.isFull();
        check("observers have no side effects", s.size() == before);

        // peek บนสแต็กว่างต้องโยน exception
        BoundedStack empty = new BoundedStack(2);
        boolean threwEmptyPeek = false;
        try {
            empty.peekTopItem();
        } catch (IllegalStateException e) {
            threwEmptyPeek = true;
        }
        check("peekTopItem on empty stack -> throws IllegalStateException", threwEmptyPeek);
    }

    // --- Producer ต้องคืนตัวใหม่ (deep copy) ไม่แก้ตัวเดิม ---
    private static void testProducer() {
        System.out.println("\n-- Producer (copy) --");

        BoundedStack original = new BoundedStack(5);
        original.pushItem(BoundedStack.BURGER);
        original.pushItem(BoundedStack.FRIES);
        original.pushItem(BoundedStack.COMBO_MEAL);

        BoundedStack copy = original.copy();

        check("copy has the same size", copy.size() == original.size());
        check("copy has the same capacity", copy.getCapacity() == original.getCapacity());
        check("copy has the same top item", copy.peekTopItem() == original.peekTopItem());

        // mutate ตัวคัดลอกต้องไม่กระทบตัวเดิม
        copy.popItem();
        check("popping from copy does not affect original",
                original.size() == 3 && copy.size() == 2);

        copy.pushItem(BoundedStack.NUGGETS);
        check("pushing to copy does not affect original",
                original.peekTopItem() == BoundedStack.COMBO_MEAL
                        && copy.peekTopItem() == BoundedStack.NUGGETS);

        // boundary: copy สแต็กว่างต้องไม่พัง
        BoundedStack emptyCopy = new BoundedStack(3).copy();
        check("copying an empty stack is safe", emptyCopy.size() == 0);
        check("copied empty stack keeps its capacity", emptyCopy.getCapacity() == 3);
    }
}
