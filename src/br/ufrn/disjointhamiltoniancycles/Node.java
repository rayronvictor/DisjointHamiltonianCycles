package br.ufrn.disjointhamiltoniancycles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rayron Victor
 * email: rayronvictor@gmail.com
 */
public class Node {

	public final int id;
	public List<Node> connections;
	
	public Node(int id) {
		this.id = id;
		this.connections = new ArrayList<Node>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + connections.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Node other = (Node) obj;

		if (id != other.id) {
			return false;
		}
		
		if (!connections.equals(other.connections)) {
			return false;
		}

		return true;
	}
}
