import com.pzlvv.coco.COCO;
import org.junit.Test;

import java.io.IOException;

public class COCOTest {
    @Test
    public void testCOCO() throws IOException {
        System.out.println("hehe");
        COCO coco = new COCO("/home/zane/tmp/annotations/stuff_val2017.json");
    }
}
