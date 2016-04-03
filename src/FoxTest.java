import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FoxTest {

    /**
     * Basic fox character used for unit tests.
     */
    private Fox mFox;

    private Fox mPunchingBag;

    @Before
    public void setUp() throws Exception {
        mFox = new Fox("Battle Fox");
        mPunchingBag = new Fox("Punching Bag Fox");
    }

    @Test
    public void testFox() {
        Assert.assertTrue(mPunchingBag.getPercentDmg() == 0);
        Assert.assertTrue(mFox.attack(mPunchingBag) == mFox.specialAttack(mPunchingBag));
        Assert.assertTrue(mPunchingBag.getPercentDmg() > 0);
    }

}