import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstCaseTest() {
        Region regionOne = new Region("Region #1", 500);
        Region regionTwo = new Region("Region #2", 700);
        Region regionThree = new Region("Region #3", 900);
        Region regionFour = new Region("Region #4", 400);
        Region regionFive = new Region("Region #5", 300);
        Region regionSix = new Region("Region #6", 800);

        regionOne.addConnection(regionTwo, 2000);
        regionOne.addConnection(regionFour, 1500);
        regionOne.addConnection(regionFive, 1800);

        regionTwo.addConnection(regionOne, 2000);
        regionTwo.addConnection(regionThree, 1500);
        regionTwo.addConnection(regionFour, 500);
        regionTwo.addConnection(regionFive, 700);

        regionThree.addConnection(regionTwo, 1500);
        
        regionFour.addConnection(regionOne, 1500);
        regionFour.addConnection(regionTwo, 500);
        regionFour.addConnection(regionFive, 1400);
        regionFour.addConnection(regionSix, 200);

        regionFive.addConnection(regionOne, 1800);
        regionFive.addConnection(regionTwo, 700);
        regionFive.addConnection(regionFour, 1400);

        regionSix.addConnection(regionFour, 200);
        List<Region> sites = new ArrayList<>();
        sites.add(regionOne);
        sites.add(regionTwo);
        sites.add(regionThree);
        sites.add(regionFour);
        sites.add(regionFive);
        sites.add(regionSix);
        Path expectedPath = new Path();
        expectedPath = expectedPath.extend(regionOne);
        expectedPath = expectedPath.extend(regionFour);
        expectedPath = expectedPath.extend(regionFive);
        expectedPath = expectedPath.extend(regionTwo);
        expectedPath = expectedPath.extend(regionThree);
        assertEquals(expectedPath, Client.findPath(sites));
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondCaseTest() {
        Region regionOne = new Region("Region #1", 1200);
        Region regionTwo = new Region("Region #2", 9000);
        Region regionThree = new Region("Region #3", 4500);
        Region regionFour = new Region("Region #4", 4600);
        Region regionFive = new Region("Region #5", 1300);
        Region regionSix = new Region("Region #6", 7800);
        Region regionSeven = new Region("Region #7", 2400);

        regionOne.addConnection(regionTwo, 2900);
        regionOne.addConnection(regionFour, 2400);

        regionTwo.addConnection(regionOne, 2900);
        regionTwo.addConnection(regionThree, 1600);
        regionTwo.addConnection(regionFour, 1300);
        regionTwo.addConnection(regionFive, 3100);

        regionThree.addConnection(regionTwo, 1600);
        regionThree.addConnection(regionFive, 900);
        
        regionFour.addConnection(regionOne, 2400);
        regionFour.addConnection(regionTwo, 1300);
        regionFour.addConnection(regionSix, 1700);
        regionFour.addConnection(regionSeven, 1200);

        regionFive.addConnection(regionTwo, 3100);
        regionFive.addConnection(regionThree, 900);

        regionSix.addConnection(regionFour, 1700);
        regionSix.addConnection(regionSeven, 600);

        regionSeven.addConnection(regionFour, 1200);
        regionSeven.addConnection(regionSix, 600);
        List<Region> sites = new ArrayList<>();
        sites.add(regionOne);
        sites.add(regionTwo);
        sites.add(regionThree);
        sites.add(regionFour);
        sites.add(regionFive);
        sites.add(regionSix);
        sites.add(regionSeven);
        Path expectedPath = new Path();
        expectedPath = expectedPath.extend(regionOne);
        expectedPath = expectedPath.extend(regionTwo);
        expectedPath = expectedPath.extend(regionFour);
        expectedPath = expectedPath.extend(regionSeven);
        expectedPath = expectedPath.extend(regionSix);
        assertEquals(expectedPath, Client.findPath(sites));
    }

    @Test
    @DisplayName("STUDENT TEST - DIY")
    public void diyTest() {
        Region regionOne = new Region("Region #1", 500);
        Region regionTwo = new Region("Region #2", 100);
        Region regionThree = new Region("Region #3", 300);
        Region regionFour = new Region("Region #4", 200);
        regionOne.addConnection(regionTwo, 300);
        regionOne.addConnection(regionFour, 500);

        regionTwo.addConnection(regionOne, 300);
        regionTwo.addConnection(regionThree, 600);

        regionThree.addConnection(regionTwo, 200);
        regionThree.addConnection(regionFour, 400);

        List<Region> sites = new ArrayList<>();
        sites.add(regionOne);
        sites.add(regionTwo);
        sites.add(regionThree);
        sites.add(regionFour);

        Path expectedPath = new Path();
        expectedPath = expectedPath.extend(regionOne);
        expectedPath = expectedPath.extend(regionTwo);
        expectedPath = expectedPath.extend(regionThree);
        expectedPath = expectedPath.extend(regionFour);

        assertEquals(expectedPath, Client.findPath(sites));
    }
}
