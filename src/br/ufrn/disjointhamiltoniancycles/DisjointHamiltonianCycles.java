package br.ufrn.disjointhamiltoniancycles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rayron Victor
 * email: rayronvictor@gmail.com
 */
public class DisjointHamiltonianCycles {

	/** nodes(vertices) of the graph */
	public List<Node> nodes;
	/** number of nodes in the complete graph */
	public int numberOfNodes;
	/** store the found disjoint cycles */
	List<List<Node>> cycles;
	
	public DisjointHamiltonianCycles(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
		
		this.nodes = new ArrayList<Node>();
		this.cycles = new ArrayList<List<Node>>();
		
		initializeNodes();
	}
	
	/** 
	 * initialize the nodes of the complete graph. 
	 */
	public void initializeNodes() {
		for(int i = 1; i <= numberOfNodes; i++) {
			this.nodes.add(new Node(i));
		}
	}
	
	/** 
	 * starts the recursive search looking
	 * for disjoint hamiltonian cycles
	 */
	public void start() {
		
		// store the current cycle
		List<Node> cycle = new ArrayList<Node>();
		// aux list of nodes for simplify the search for neighbors 
		List<Node> auxNodes = new ArrayList<Node>(nodes);
		
		// initializes the current cycle with the first node 
		cycle.add(this.nodes.get(0));
		// remove it from auxNodes for simplify the search for neighbors
		auxNodes.remove(this.nodes.get(0));
		
		// recursive call
		recursiveCall(this.nodes.get(0), cycle, auxNodes);
		
		printCycles();
	}
	
	public boolean recursiveCall(Node currentNode, List<Node> currentCycle, List<Node> auxNodes) {
		boolean result = false;
		
		// missing only close the cycle?
		if(currentCycle.size() == nodes.size()) {

			// is it possible?
			if(!currentNode.connections.contains(currentCycle.get(0))) {
				
				// connect the last node to the first and vice-versa
				currentNode.connections.add(currentCycle.get(0));
				currentCycle.get(0).connections.add(currentNode);
				
				// add again the first node to the current cycle
				currentCycle.add(currentCycle.get(0));
				
				// add the current cycle to solution
				cycles.add(currentCycle);

				// still have cycles?
				if(cycles.size() < Math.floor((numberOfNodes-1)/2)) {
					
					// create new cycle and new auxNodes
					List<Node> newCycle = new ArrayList<Node>();
					List<Node> newAuxNodes = new ArrayList<Node>(nodes);
					
					// initializes with the first node 
					newCycle.add(nodes.get(0));
					// remove it from auxNodes
					newAuxNodes.remove(nodes.get(0));
					
					boolean bool = recursiveCall(nodes.get(0), newCycle, newAuxNodes);
					
					// not found all disjoint cycles?
					if(!bool) {
						// backtrack. by this cycle is not possible
						// find more disjoint cycles. remove it.
						cycles.remove(currentCycle);
						
						// undoes the last connection
						currentNode.connections.remove(currentCycle.get(0));
						currentCycle.get(0).connections.remove(currentNode);
						
						// remove the node that close the cycle,
						// that is always the first node of nodes
						currentCycle.remove(currentCycle.size()-1);

						// fail. let's try another path
						result =  false;
					}else {
						result =  true;
					}
				}else {
					result =  true;
				}
			}
		}else {
			// gets all currentNode neighbors 
			List<Node> neighbors = getNeighbors(currentNode, auxNodes);
			
			if(neighbors.size() > 0) {
				// try all possibilities of neighbors
				for(Node n : neighbors) {
					
					// add to the current cycle
					currentCycle.add(n);
					// and remove it from auxNodes
					auxNodes.remove(n);

					// makes the connections
					currentNode.connections.add(n);
					n.connections.add(currentNode);

					// fail?
					if(!recursiveCall(n, currentCycle, auxNodes)){
						
						// backtrack
						currentCycle.remove(n);
						auxNodes.add(n);

						currentNode.connections.remove(n);
						n.connections.remove(currentNode);
					}else {
						result = true;
						break;
					}
				}
				
			}
		}
		
		return result;
	}
	
	/** 
	 * return the nodes where the edge (node, v) or (v, node)
	 * has not been visited. the nodeList has only the nodes 
	 * that have not been visited in the currently cycle being 
	 * analyzed.
	 */
	public List<Node> getNeighbors(Node node, List<Node> nodeList){
		List<Node> neighbors = new ArrayList<Node>();
		
		for(Node n : nodeList) {
			if(!node.connections.contains(n)) {
				neighbors.add(n);
			}
		}
		
		return neighbors;
	}
	
	/** 
	 * print the disjoint hamiltoninan cycles 
	 */
	public void printCycles() {
		int n = 1;
		for(List<Node> cycle : this.cycles) {
			System.out.format("cycle %2d : [", (n++));
			
			for(int i = 0; i < cycle.size(); i++) {
				if(i==0) System.out.format("%2d", cycle.get(i).id);
				else	 System.out.format(", %2d", cycle.get(i).id);
			}
			
			System.out.println("]");
		}
	}
	
	
}
