package diffusion;

import core.components.Edge;
import core.components.Vertex;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 *
 * Vertex for a graph which will undergo the Initial Cascade model
 * for diffusion of information
 *
 */
public class ICVertex extends Vertex {

    private double infectProb;
    private boolean infected = false;

    /**
     * vertex of a graph that will undergo the
     * Initial Cascade diffusion process
     * @param p infection probability of a node
     */
    public ICVertex (double p){
        super();
        infectProb = p;
    }

    /**
     * vertex of a graph that will undergo the
     * Initial Cascade diffusion process
     * @param p infection probability of a node
     * @param s String value inherited from vertex class
     */
    public ICVertex(String s, double p){
        super(s);
        infectProb = p;
    }

    protected boolean getStatus(){
        return infected;
    }

    protected void setStatus(boolean t){
        infected = t;
    }


    /**
     * the method that runs the infection process
     * @param graph the graph the node belongs to
     * @return the set of nodes infected by this one
     */
    protected Collection<ICVertex> infect(Graph<ICVertex,Edge> graph) {

        Random rnd = new Random();
        ArrayList<ICVertex> infected = new ArrayList<>();

        graph.getNeighbors(this).stream()
                .forEach(e -> {
                    double chance = rnd.nextDouble();
                    if (!e.getStatus() && chance < infectProb) {
                        e.setStatus(true);
                        infected.add(e);
                    }
                });

        return infected;
    }
}
