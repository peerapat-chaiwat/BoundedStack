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
 * Playlist — ADT เมนูอาหารที่ผู้ใช้จัดลำดับไว้
 * 
 * 
 * 
 * ตัวอย่างการใช้งาน : 
 * 
 * 
 * 
 * 
 */
public class BoundedStack {
    private final List<String> foodList;
    private final int capacity;

    private void checkRep() {
        assert foodList !=null:"menu is not null";
        assert capacity > 0 :"menu is ready to edit";
        assert foodList.size() <= capacity : "have many menu";
        Set<String> seen = new HashSet<>();
        for (String f : foodList){
            assert f != null:"menu is not null" ;
            assert !f.isEmpty():"menu is empty" ;
            assert f != "" :"menu is empty";
            assert seen.add(f): "menu is duplicate" ;
        }   
    }

    // ===== Creator =====
    public BoundedStack(){
        this.foodList = new ArrayList<>();
        checkRep();
    }

}
