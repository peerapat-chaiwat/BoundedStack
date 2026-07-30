import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Collections;
/*
  Peerapat Chaiwat 6821651574
Wongwarit Lerdsart 6821651701
*/
/**
 * <p>
 * <b>กฎสำคัญในการใช้งาน:</b>
 * </p>
 * <ul>
 * <li><b>ขนาดความจุคงที่:</b> จำนวนถาดสูงสุดต้องถูกกำหนดไว้ตั้งแต่ตอนสร้างสแต็ก 
 * เมื่อสแต็กถาดเต็มแล้ว จะไม่สามารถเพิ่มถาดลงไปได้อีก</li>
 * <li><b>การกรองรายการ:</b> รองรับเฉพาะรหัสรายการเมนูดังต่อไปนี้เท่านั้น:
 * 1 (เบอร์เกอร์), 2 (เฟรนช์ฟรายส์), 3 (นักเก็ต), 4 (ชุดคอมโบ)</li>
 * </ul>
 *
 * <p>
 * <b>ตัวอย่างการใช้งานเบื้องต้น:</b>
 * </p>
 *
 * <pre>{@code
 * // 1. สร้างสแต็กถาดอาหารที่จุได้สูงสุด 20 ถาด
 * BoundedStack orderStack = new BoundedStack(20);
 *
 * // 2. ดัน (Push) ถาดลงในสแต็ก
 * orderStack.pushItem(10); // ดัน ชุดคอมโบ ลงไป
 * orderStack.pushItem(2);  // ดัน เฟรนช์ฟรายส์ ลงไปซ้อนทับด้านบน
 *
 * // 3. เสิร์ฟถาดอาหาร (ถาดที่เพิ่งใส่ลงไปล่าสุด เช่น เฟรนช์ฟรายส์ จะถูกนำออกมาเป็นอันดับแรก)
 * int served = orderStack.popItem();
 * }</pre>
 *
 * @version 1.2
 */
public class BoundedStack {

    // === รหัสรายการเมนูอาหาร ===
    public static final int BURGER = 1;
    public static final int FRIES = 2;
    public static final int NUGGETS = 3; 
    public static final int COMBO_MEAL = 4;

    // === โครงสร้างการจัดเก็บข้อมูล (Representation) ===
    private final int[] trays;
    private final int capacity;
    private int top;

    // === Abstraction Function (AF) & Representation Invariant (RI) ===
    // AF:
    // AF(trays, capacity, top) = สแต็กถาดอาหารที่มีความจุสูงสุดเท่ากับ `capacity`
    // เก็บถาดจากล่างขึ้นบนเป็น trays[0], trays[1], ..., trays[top - 1]
    // โดยถาดบนสุด (ถาดที่จะถูกเสิร์ฟก่อนเป็นอันดับแรก) คือ trays[top - 1] เมื่อ top > 0
    //
    // RI:
    // 1. capacity > 0
    // 2. 0 <= top <= capacity
    // 3. trays ต้องไม่เป็น null และ trays.length == capacity
    // 4. ข้อมูลตั้งแต่ trays[0] ถึง trays[top - 1] ต้องเป็นรหัสเมนูอาหารที่ถูกต้องเท่านั้น
    // (BURGER, FRIES, NUGGETS, COMBO_MEAL)

    /**
     * ตรวจสอบว่าเงื่อนไข RI ยังคงถูกต้องอยู่เสมอ
     * (ควรถูกเรียกใช้ตอนท้ายของทุกๆ Creator, Mutator และ Producer)
     */
    private void checkRep() {
        assert capacity > 0 : "ความจุ (Capacity) ต้องมีค่ามากกว่า 0 เท่านั้น";
        assert trays != null : "อาร์เรย์ trays ต้องไม่เป็น null";
        assert trays.length == capacity : "ความยาวของอาร์เรย์ trays ต้องเท่ากับความจุที่กำหนด";
        assert top >= 0 && top <= capacity : "ดัชนี top ต้องมีค่าอยู่ระหว่าง 0 ถึง capacity";

        for (int i = 0; i < top; i++) {
            assert isValidItem(trays[i]) : "รายการอาหารที่ตำแหน่ง index " + i + " ต้องเป็นรหัสเมนูที่ถูกต้อง";
        }
    }

    /**
     * ตรวจสอบว่าค่าที่ระบุเป็นรหัสเมนูอาหารที่อนุญาตให้ใช้หรือไม่
     *
     * @param item รหัสรายการอาหารที่ต้องการตรวจสอบ
     * @return true ถ้าเป็น BURGER, FRIES, NUGGETS หรือ COMBO_MEAL
     */
    private static boolean isValidItem(int item) {
        return item == BURGER || item == FRIES || item == NUGGETS || item == COMBO_MEAL;
    }

    // === Creator ===
    /**
     * สร้างสแต็กถาดอาหารที่ว่างเปล่าตามขนาดความจุสูงสุดที่กำหนด
     *
     * @param capacity จำนวนถาดสูงสุดที่สแต็กนี้สามารถรองรับได้
     * @throws IllegalArgumentException ถ้า capacity <= 0
     */
    public BoundedStack(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("ความจุ (Capacity) ต้องมีค่ามากกว่า 0");
        this.capacity = capacity;
        this.trays = new int[capacity];
        this.top = 0;
        checkRep();
    }

    // === Mutator ===
    /**
     * ดัน (Push) ถาดรายการอาหารที่ระบุลงไปบนสแต็ก
     *
     * @param item รหัสรายการเมนูอาหาร (ต้องเป็น BURGER, FRIES, NUGGETS หรือ COMBO_MEAL)
     * @throws IllegalStateException    ถ้าสแต็กเต็มแล้ว
     * @throws IllegalArgumentException ถ้ารหัสรายการอาหารไม่ถูกต้อง
     */
    public void pushItem(int item) {
        if (isFull())
            throw new IllegalStateException("สแต็กถาดอาหารเต็มแล้ว ไม่สามารถเพิ่มถาดได้อีก");
        if (!isValidItem(item))
            throw new IllegalArgumentException("รองรับเฉพาะรหัสเมนู BURGER, FRIES, NUGGETS หรือ COMBO_MEAL เท่านั้น");

        trays[top] = item;
        top++;
        checkRep();
    }

    /**
     * เสิร์ฟ (นำออก) ถาดอาหารที่เพิ่งถูกดันลงสแต็กล่าสุด
     *
     * @return รหัสรายการอาหารของถาดที่ถูกเสิร์ฟออกไป
     * @throws IllegalStateException ถ้าสแต็กว่างเปล่า
     */
    public int popItem() {
        if (isEmpty())
            throw new IllegalStateException("สแต็กถาดอาหารว่างเปล่า ไม่มีถาดให้เสิร์ฟ");
        top--;
        int item = trays[top];
        checkRep();
        return item;
    }

    // === Observer ===
    /**
     * ดูรหัสรายการอาหารบนถาดบนสุดโดยไม่นำถาดออก
     *
     * @return รหัสรายการอาหารของถาดบนสุด
     * @throws IllegalStateException ถ้าสแต็กว่างเปล่า
     */
    public int peekTopItem() {
        if (isEmpty())
            throw new IllegalStateException("สแต็กถาดอาหารว่างเปล่า");
        return trays[top - 1];
    }

    /**
     * ดึงจำนวนถาดที่มีอยู่ในสแต็ก ณ ปัจจุบัน
     *
     * @return จำนวนถาดที่อยู่ในสแต็กปัจจุบัน
     */
    public int size() {
        return top;
    }

    /**
     * ดึงค่าความจุสูงสุดของสแต็กถาดอาหารนี้
     *
     * @return จำนวนถาดสูงสุดที่สแต็กสามารถรองรับได้
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * ตรวจสอบว่าสแต็กถาดอาหารว่างเปล่าหรือไม่
     *
     * @return {@code true} ถ้าไม่มีถาดอยู่ในสแต็กเลย,
     *         {@code false} ถ้ามีถาดอยู่อย่างน้อยหนึ่งถาด
     */
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * ตรวจสอบว่าสแต็กถาดอาหารเต็มความจุแล้วหรือไม่
     *
     * @return {@code true} ถ้าสแต็กเต็มและไม่สามารถรับถาดเพิ่มได้อีก,
     *         {@code false} ถ้ายังเหลือพื้นที่ว่าง
     */
    public boolean isFull() {
        return top == capacity;
    }

    // === Producer ===
    /**
     * สร้างสแต็กถาดอาหารอันใหม่ที่มีรายการและลำดับเหมือนกับสแต็กปัจจุบันทุกประการ (Deep Copy)
     * การเปลี่ยนแปลงในสแต็กใหม่จะไม่ส่งผลกระทบต่อสแต็กเดิม
     *
     * @return สแต็ก BoundedStack อันใหม่ที่มีข้อมูลเหมือนเดิมทุกอย่าง
     */
    public BoundedStack copy() {
        BoundedStack newStack = new BoundedStack(this.capacity);
        for (int i = 0; i < this.top; i++) {
            newStack.trays[i] = this.trays[i];
        }
        newStack.top = this.top;
        this.checkRep();
        newStack.checkRep();
        return newStack;
    }
}
