package diffusion;

import core.components.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class DiffusionMechanismTest {

    //tests complete graph of n=3 for IC model with prob = 100%
    @Test
    public void testInitialCondition1() throws Exception {
        DiffusionMechanism diff = new DiffusionMechanism();
        Graph<ICVertex,Edge> G = new SparseGraph<>();

        //create vertices
        for (int i = 0; i < 3; i++) {
            G.addVertex(new ICVertex(""+i,1.0));
        }

        Iterator<ICVertex> iter = G.getVertices().iterator();

        //get handle on vertices
        ICVertex v1 = iter.next();
        ICVertex v2 = iter.next();
        ICVertex v3 = iter.next();

        //create edges
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v1,v2);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v2,v3);
        G.addEdge(new Edge(v3.getId()+"-"+v1.getId()),v3,v1);

        //should be diffusion with 100% infection prob
        String ans = "timestep,Total Infected,Newly Infected \n" +
                "0,0,1\n" +
                "1,1,2\n" +
                "2,3,0\n";

        ArrayList seed = new ArrayList();
        seed.add(G.getVertices().iterator().next());
        assertEquals(ans, diff.InitialCondition(G,seed));

    }

    //Test a chain of 20 vertices for IC model with prob = 100%
    @Test
    public void testInitialCondition2() throws Exception {
        DiffusionMechanism diff = new DiffusionMechanism();
        Graph<ICVertex,Edge> G = new SparseGraph<>();

        //create vertices
        for (int i = 0; i < 20; i++) {
            G.addVertex(new ICVertex(""+i,1.0));
        }


        Iterator<ICVertex> iter = G.getVertices().iterator();

        //get handle on vertices
        ICVertex v1 = iter.next();
        ICVertex v2 = iter.next();
        ICVertex v3 = iter.next();
        ICVertex v4 = iter.next();
        ICVertex v5 = iter.next();
        ICVertex v6 = iter.next();
        ICVertex v7 = iter.next();
        ICVertex v8 = iter.next();
        ICVertex v9 = iter.next();
        ICVertex v10 = iter.next();
        ICVertex v11 = iter.next();
        ICVertex v12 = iter.next();
        ICVertex v13 = iter.next();
        ICVertex v14 = iter.next();
        ICVertex v15 = iter.next();
        ICVertex v16 = iter.next();
        ICVertex v17 = iter.next();
        ICVertex v18 = iter.next();
        ICVertex v19 = iter.next();
        ICVertex v20 = iter.next();


        //create edges
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v1,v2);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v2,v3);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v3,v4);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v4,v5);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v5,v6);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v6,v7);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v7,v8);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v8,v9);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v9,v10);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v10,v11);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v11,v12);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v12,v13);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v13,v14);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v14,v15);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v15,v16);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v16,v17);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v17,v18);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v18,v19);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v19,v20);

        //should be diffusion with 100% infection prob
        String ans = "timestep,Total Infected,Newly Infected \n" +
                "0,0,1\n" +
                "1,1,1\n" +
                "2,2,1\n" +
                "3,3,1\n" +
                "4,4,1\n" +
                "5,5,1\n" +
                "6,6,1\n" +
                "7,7,1\n" +
                "8,8,1\n" +
                "9,9,1\n" +
                "10,10,1\n" +
                "11,11,1\n" +
                "12,12,1\n" +
                "13,13,1\n" +
                "14,14,1\n" +
                "15,15,1\n" +
                "16,16,1\n" +
                "17,17,1\n" +
                "18,18,1\n" +
                "19,19,1\n" +
                "20,20,0\n";

        ArrayList seed = new ArrayList();
        seed.add(v1);
        assertEquals(ans, diff.InitialCondition(G,seed));
    }

    //tests complete graph of n=3 for IC model with prob = 0%
    @Test
    public void testInitialCondition3() throws Exception {
        DiffusionMechanism diff = new DiffusionMechanism();
        Graph<ICVertex,Edge> G = new SparseGraph<>();

        //create vertices
        for (int i = 0; i < 3; i++) {
            G.addVertex(new ICVertex(""+i,0.0));
        }

        Iterator<ICVertex> iter = G.getVertices().iterator();

        //get handle on vertices
        ICVertex v1 = iter.next();
        ICVertex v2 = iter.next();
        ICVertex v3 = iter.next();

        //create edges
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v1,v2);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v2,v3);
        G.addEdge(new Edge(v3.getId()+"-"+v1.getId()),v3,v1);

        //should be diffusion with 100% infection prob
        String ans = "timestep,Total Infected,Newly Infected \n" +
                "0,0,1\n" +
                "1,1,0\n";

        ArrayList seed = new ArrayList();
        seed.add(v1);
        assertEquals(ans, diff.InitialCondition(G,seed));

    }

    //tests complete graph of n=3 for LT model with threshhold = 1
    @Test
    public void testLinearCascade1() throws Exception {
        DiffusionMechanism diff = new DiffusionMechanism();
        Graph<LTVertex,Edge> G = new SparseGraph<>();

        //create vertices
        for (int i = 0; i < 3; i++) {
            G.addVertex(new LTVertex(""+i,1.0));
        }

        Iterator<LTVertex> iter = G.getVertices().iterator();

        //get handle on verttices
        LTVertex v1 = iter.next();
        LTVertex v2 = iter.next();
        LTVertex v3 = iter.next();

        //create edges
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v1,v2);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v2,v3);
        G.addEdge(new Edge(v3.getId()+"-"+v1.getId()),v3,v1);

        //should be diffusion with 100% infection prob
        String ans = "timestep,Total Infected,Newly Infected \n" +
                "0,0,1\n" +
                "1,1,0\n";

        ArrayList seed = new ArrayList();
        seed.add(v1);
        assertEquals(ans, diff.LinearCascade(G,seed));
    }

    //tests a chain of 20 vertices for LT model with threshhold = 0
    @Test
    public void testLinearCascade2() throws Exception {
        DiffusionMechanism diff = new DiffusionMechanism();
        Graph<LTVertex,Edge> G = new SparseGraph<>();

        //create vertices
        for (int i = 0; i < 20; i++) {
            G.addVertex(new LTVertex(""+i,0.0));
        }


        Iterator<LTVertex> iter = G.getVertices().iterator();

        //get handle on vertices
        LTVertex v1 = iter.next();
        LTVertex v2 = iter.next();
        LTVertex v3 = iter.next();
        LTVertex v4 = iter.next();
        LTVertex v5 = iter.next();
        LTVertex v6 = iter.next();
        LTVertex v7 = iter.next();
        LTVertex v8 = iter.next();
        LTVertex v9 = iter.next();
        LTVertex v10 = iter.next();
        LTVertex v11 = iter.next();
        LTVertex v12 = iter.next();
        LTVertex v13 = iter.next();
        LTVertex v14 = iter.next();
        LTVertex v15 = iter.next();
        LTVertex v16 = iter.next();
        LTVertex v17 = iter.next();
        LTVertex v18 = iter.next();
        LTVertex v19 = iter.next();
        LTVertex v20 = iter.next();


        //create edges
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v1,v2);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v2,v3);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v3,v4);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v4,v5);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v5,v6);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v6,v7);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v7,v8);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v8,v9);
        G.addEdge(new Edge(v1.getId()+"-"+v2.getId()),v9,v10);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v10,v11);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v11,v12);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v12,v13);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v13,v14);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v14,v15);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v15,v16);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v16,v17);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v17,v18);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v18,v19);
        G.addEdge(new Edge(v2.getId()+"-"+v3.getId()),v19,v20);

        //should be diffusion with 100% infection prob
        String ans = "timestep,Total Infected,Newly Infected \n" +
                "0,0,1\n" +
                "1,1,1\n" +
                "2,2,1\n" +
                "3,3,1\n" +
                "4,4,1\n" +
                "5,5,1\n" +
                "6,6,1\n" +
                "7,7,1\n" +
                "8,8,1\n" +
                "9,9,1\n" +
                "10,10,1\n" +
                "11,11,1\n" +
                "12,12,1\n" +
                "13,13,1\n" +
                "14,14,1\n" +
                "15,15,1\n" +
                "16,16,1\n" +
                "17,17,1\n" +
                "18,18,1\n" +
                "19,19,1\n" +
                "20,20,0\n";

        ArrayList seed = new ArrayList();
        seed.add(v1);
        assertEquals(ans, diff.LinearCascade(G,seed));
    }
}