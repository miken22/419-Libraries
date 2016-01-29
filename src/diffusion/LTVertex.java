package diffusion;

import core.components.Vertex;

/**
 * Vertex for a graph which will undergo the Linear Threshhold
 * model for diffusion of information
 */
public class LTVertex extends Vertex{

    private double threshhold;
    private boolean infected = false;

    /**
     * vertex of a graph that will undergo the
     * Linear Threshhold diffusion process
     * @param T the threshhold of a node in order for it to be infected
     */
    public LTVertex (double T){
        super();
        threshhold = T;
    }

    /**
     * vertex of a graph that will undergo the
     * Linear Threshhold diffusion process
     * @param s String value inherited from vertex class
     * @param T the threshhold of a node in order for it to be infected
     */
    public LTVertex(String s, double T){
        super(s);
        threshhold = T;
    }

    protected boolean getStatus(){
        return infected;
    }

    protected void setStatus(boolean t){
        infected = t;
    }

    protected double getThreshhold(){
        return threshhold;
    }
    
}
