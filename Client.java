/*
 * 2/25/25
 */
import java.util.*;

/*
 * This class acts like a simulation that helps disaster relief teams determine 
 *      what the best possible paths between certain regions are in order to 
 *      help the most people and save up on cost during a time of disaster. 
 */
public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        // List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createRandomScenario(5, 100, 1000, 100, 1000);
        System.out.println(scenario);
        
        Path allocation = findPath(scenario);
        if (allocation != null) {
            printResult(allocation);
        } else {
            System.out.println("No valid path found. :-(");
        }
    }

    /*
     * This helps to find the most ideal path between regions for disaster relief efforts to take
     *      to give aid to people. First priority is given to amount of people helped 
     *          and then second priority is the cost to do so. Any possible path can only start 
     *          from region one though.
     * Parameters: 
     *      - sites: the list of all the regions that need help
     * Exceptions: 
     *      - IllegalArgumentException(): gets thrown if the list of regions is null
     * Return: 
     *      - Path: The best possible helping path between regions based on people helped 
     *          and money used. Otherwise, null if the list of given region sites is empty. 
     */
    public static Path findPath(List<Region> sites) {
        if (sites == null) {
            throw new IllegalArgumentException("The sites list cannot be empty!");
        }
        if (sites.isEmpty()) {
            return null;
        }
        Path soFar = new Path();
        soFar = soFar.extend(sites.get(0));
        List<Path> potentials = findPath(sites, soFar, new ArrayList<>());
        if (potentials.size() == 0) {
            potentials.add(soFar);
        }
        return bestPath(potentials);
    }

    /*
     * This method contains the logic behind devising potential paths that relief operations
     *      can take. It creates and keeps track of all possibile paths that exist between
     *      the regions provided. Any possible path can only start from region 1. 
     * Parameters: 
     *      - sites: list of all the available regions that a path can be made out of
     *      - soFar: a tracker that keeps track of the regions already considered in path 
     *          calculations
     *      - potentials: a growing list of all the possible paths that relief operations can take
     * Return: 
     *      - List<Path>: all the paths between regions that relief operations can take
     */
    private static List<Path> findPath(List<Region> sites, Path soFar, List<Path> potentials) {
        for (int i = 1; i < sites.size(); i++) {
            if (sites.get(0).canReach(sites.get(i))) {
                if (potentials.contains(soFar) && soFar != null) {
                    int index = potentials.indexOf(soFar);
                    soFar = soFar.extend(sites.get(i));
                    potentials.add(index, soFar);
                } else {
                    soFar = soFar.extend(sites.get(i));
                    potentials.add(soFar);
                }
                Region removal = sites.remove(0);
                sites.add(0, sites.remove(i - 1));
                findPath(sites, soFar, potentials);
                sites.add(i - 1, sites.remove(0));
                sites.add(0, removal);
                soFar = soFar.removeEnd();
            }
        }
        return potentials;
    }

    /*
     * This method contains the logic behind actually finding what the best possible path is. 
     *      The best possible route is chosen based on which is the path that helps
     *      the most people. If that part is tied, then the tiebreaker goes towards which path
     *      has the lower total cost to go through. If both are tied then whicever 
     *      path that appears first in the potential paths list will get selected.
     * Parameters: 
     *      - potentials: a list containing all of the potential paths for relief 
     *          operations to take.
     * Return: 
     *      - Path: the best possible path for the relief operations to take
     */
    private static Path bestPath(List<Path> potentials) {
        int maxPopHelped = 0;
        Path bestPath = null;
        for (Path path: potentials) {
            if (path.totalPeople() > maxPopHelped) {
                maxPopHelped = path.totalPeople();
                bestPath = path;
            }
            else if (path.totalPeople() == maxPopHelped) {
                if (bestPath.totalCost() > path.totalCost()) {
                    bestPath = path;
                }
            }              
        }    
        return bestPath;
     }
    

    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////
    
    /**
    * Prints each path in the provided set. Useful for getting a quick overview
    * of all path currently in the system.
    * @param paths Set of paths to print
    */
    public static void printPaths(Set<Path> paths) {
        System.out.println("All Allocations:");
        for (Path a : paths) {
            System.out.println("  " + a);
        }
    }

    /**
    * Prints details about a specific path result, including the total people
    * helped and total cost.
    * @param path The path to print
    */
    public static void printResult(Path path) {
        System.out.println("Result: ");
        List<Region> regions = path.getRegions();
        System.out.print(regions.get(0).getName());
        for (int i = 1; i < regions.size(); i++) {
            System.out.print(" --($" + regions.get(i - 1).getCostTo(regions.get(i)) + ")-> " + regions.get(i).getName());
        }
        System.out.println();
        System.out.println("  People helped: " + path.totalPeople());
        System.out.printf("  Cost: $%.2f\n", path.totalCost());
    }

    /**
    * Creates a scenario with numRegions regions by randomly choosing the population 
    * and travel costs for each region.
    * @param numRegions Number of regions to create
    * @param minPop Minimum population per region
    * @param maxPop Maximum population per region
    * @param minCost Minimum cost of travel between regions
    * @param maxCost Maximum cost of travel between regions
    * @return A list of randomly generated regions
    */
    public static List<Region> createRandomScenario(int numRegions, int minPop, int maxPop,
                                                    double minCost, double maxCost) {
        List<Region> result = new ArrayList<>();

        // ranomly create regions
        for (int i = 0; i < numRegions; i++) {
            // int pop = RAND.nextInt(minPop, maxPop + 1);
            int pop = RAND.nextInt(maxPop - minPop + 1) + minPop;
            result.add(new Region("Region #" + i, pop));
        }

        // randomly create connections between regions
        for (int i = 0; i < numRegions; i++) {
            // int numConnections = RAND.nextInt(1, numLocs - i);
            Region site = result.get(i);
            for (int j = i + 1; j < numRegions; j++) {
                // flip a coin to decide whether or not to add each connection
                if (RAND.nextBoolean()) {
                    Region other = result.get(j);
                    // double cost = round2(RAND.nextDouble(minCostPer, maxCostPer));
                    double cost = round2(RAND.nextDouble() * (maxCost - minCost) + maxCost);
                    site.addConnection(other, cost);
                    other.addConnection(site, cost);
                }
            }
        }

        return result;
    }

    /**
    * Manually creates a simple list of regions to represent a known scenario.
    * @return A simple list of regions
    */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();
        Region regionOne = new Region("Region #1", 100);
        Region regionTwo = new Region("Region #2", 50);
        Region regionThree = new Region("Region #3", 100);

        regionOne.addConnection(regionTwo, 200);
        regionOne.addConnection(regionThree, 300);

        regionTwo.addConnection(regionOne, 200);

        regionThree.addConnection(regionOne, 300);
        
        result.add(regionOne);   // Region #1: pop. 100 - [Region #2 (200.0), Region #3 (300.0)]
        result.add(regionTwo);   // Region #2: pop. 50 - [Region #1 (200.0)]
        result.add(regionThree); // Region #3: pop. 100 - [Region #1 (300.0)]

        return result;
    }   

    /**
    * Rounds a number to two decimal places.
    * @param num The number to round
    * @return The number rounded to two decimal places
    */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
