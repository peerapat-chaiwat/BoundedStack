# BoundedStack (FoodList)

## วิธี compile และรัน

**ตั้งค่าใน VSCode** ให้เปิด `-ea` อัตโนมัติ — เพิ่มใน `.vscode/settings.json`:

```json
{
  "java.debug.settings.vmArgs": "-ea"
}
```

หรือสามารถคอมไพล์และรันผ่าน Terminal/Command Line ได้โดยตรง:

```bash
# คอมไพล์ไฟล์ Java ทั้งหมด
javac BoundedStack.java BoundedStackTest.java

# รันชุดทดสอบพร้อมเปิดใช้งาน Assertion (-ea)
java -ea BoundedStackTest
```
---

## เป้าหมายในการสร้าง

1. **ADT 4 บทบาท:** สร้าง BoundedStack ให้มีเมธอดครบทั้ง 4 กลุ่ม (Creator, Mutator, Observer, Producer)
2. **คุม Rep:** ห้ามใช้ Stack สำเร็จรูป (ใช้ Array แทน) พร้อมเขียน AF, RI และต้องมี `checkRep()` ตรวจสอบสถานะเสมอ
3. **จัดการเคสผิดปกติ:** เช่น สแต็กเต็ม, ว่าง, หรือความจุติดลบ ให้รัดกุมด้วย Exception หรือ Assertion
4. **ทำ Test Runner เอง:** ห้ามใช้ JUnit โดยเขียนระบบเทสต์ 38 เคส ที่รันและสรุปผล PASS/FAIL ได้ด้วยตัวเอง
5. **ส่งงาน:** โค้ดรันด้วย Java เพียวๆ พร้อมแนบ Design Document และข้อมูลผู้จัดทำ

---

## ไฟล์ในโปรเจกต์

| ไฟล์ | คำอธิบาย |
|---|---|
| `BoundedStack.java` | โค้ดหลักของ ADT พร้อม JavaDoc อธิบายสเปกและ RI/AF |
| `BoundedStackTest.java` | ชุดทดสอบ 38 เคส ครอบคลุมทุกฟังก์ชันและขอบเขต (Boundary) |
| `README.md` | ไฟล์คู่มือการใช้งานและการคอมไพล์โปรเจกต์ |

---

เมื่อทำเสร็จถูกต้องทั้งหมด จะได้ผลลัพธ์การรันดังนี้


> **หมายเหตุเรื่องสไตล์โค้ด:** ชุดทดสอบเขียนด้วยไวยากรณ์ Java พื้นฐาน การตรวจ exception ใช้ `try` / `catch` และ `main()` เรียกเมธอดเทสต์ทีละบรรทัด ข้อความในระบบเทสต์เป็นภาษาอังกฤษเพื่อป้องกันปัญหาการแสดงผลภาษาไทยเพี้ยนบน Console ของ Windows

---

## สเปคของ ADT (Abstraction Function & Representation Invariant)

ค่านามธรรม (Abstraction): 
ลำดับของรายการถาดอาหาร เช่น `[1, 2, 3, 4]` โดยลำดับมีความหมาย — ถาดแรกในรายการคือถาดที่อยู่ก้นสแต็ก (ใส่เข้าไปก่อน) และถาดสุดท้ายในรายการคือถาดที่อยู่บนสุด ซึ่งจะถูกหยิบออกก่อนตามหลัก LIFO (Last In, First Out)

### Representation (R)

```java
private final int[] trays;
private final int capacity;
private int top;
```

### กฎที่ต้องรักษาไว้เสมอ (RI - Representation Invariant)
1. ความจุสูงสุด (`capacity`) ต้องมากกว่า 0 เสมอ
2. อาร์เรย์เก็บถาดอาหาร (`trays`) ต้องมีอยู่จริง (ไม่เป็น `null`) และมีขนาดเท่ากับ `capacity` เสมอ
3. จำนวนถาดอาหารปัจจุบัน (`top`) ต้องไม่ติดลบ และต้องไม่เกินความจุสูงสุด (`0 <= top <= capacity`)
4. ถาดอาหารทุกถาดที่จัดเก็บอยู่จริง (ตั้งแต่ index ที่ `0` ถึง `top - 1`) ต้องมีรหัสเมนูที่เป็น 1 (BURGER), 2 (FRIES), 3 (NUGGETS) หรือ 4 (COMBO_MEAL) เท่านั้น

---

## Developer Profile

| ข้อมูลส่วนตัว  | รายละเอียด |
| :--- | :--- |
| **ชื่อ-นามสกุล** | วงศ์วริศ เลิศศาสตร์ |
| **รหัสนิสิต** | 6821651701 |
| **GitHub ID** | [@Wongwarit.L](https://github.com/ShineShineShine0) |

| ข้อมูลส่วนตัว  | รายละเอียด |
| :--- | :--- |
| **ชื่อ-นามสกุล** | พีรพัฒน์ ไชยวัต |
| **รหัสนิสิต** | 6821651574 |
| **GitHub ID** | [@mahasamutsa-ku](https://github.com/peerapat-chaiwat) |
