import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Contains every class to test.
 */
@RunWith(Suite.class)
@SuiteClasses({
    TestBackgroundRenderer.class,
    TestGameplay.class
})

// Run AllTests to test everything within @SuitClasses
public class AllTests {}
