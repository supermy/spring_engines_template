/**
 * Created by moyong on 14/12/27.
 */
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class CalculatorTest extends TestCase {

    private Calculator cal;

    protected void setUp() throws Exception {
        System.out.println("......up");
        cal = new Calculator();
    }

    protected void tearDown() throws Exception {
        System.out.println("......down");

        super.tearDown();
    }

    @Test
    public void testAdd() {
        int result = cal.add(1, 2);
        Assert.assertEquals(3, result);
        //Assert.fail();
    }

    public void testMinus() {
        int result = cal.minus(5, 2);
        Assert.assertEquals(3, result);
    }

    public void testMultiply() {
        int result = cal.multiply(4, 2);
        Assert.assertEquals(8,result);
    }

    public void testDivide() {
        int result = 0;
        try {
            result = cal.divide(10, 5);
        } catch (Exception e) {
            e.printStackTrace();
            // 我们期望result = cal.divide(10,5);正常执行；如果进入到catch中说明失败；
            // 所以我们加上fail。
            Assert.fail();// 如果这行没有执行。说明这部分正确。
        }
        Assert.assertEquals(2, result);
    }

}
