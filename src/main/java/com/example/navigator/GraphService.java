package com.example.navigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * GraphService: stores nodes (with coordinates), edges (weights) and computes shortest path (Dijkstra).
 */
@Service
public class GraphService {

    // DTOs for JSON responses
    public class NodeDTO {
        private final String id;
        private final int x;
        private final int y;
        public NodeDTO(String id, int x, int y) { this.id = id; this.x = x; this.y = y; }
        public String getId() { return id; }
        public int getX() { return x; }
        public int getY() { return y; }
    }

    public class EdgeDTO {
        private final String source;
        private final String target;
        private final int weight;
        public EdgeDTO(String source, String target, int weight) {
            this.source = source; this.target = target; this.weight = weight;
        }
        public String getSource() { return source; }
        public String getTarget() { return target; }
        public int getWeight() { return weight; }
    }

    // Internal edge class
    private class Edge {
        String dest;
        int weight;
        Edge(String d, int w) { dest = d; weight = w; }
    }

    // nodes map (keeps insertion order)
    private final Map<String, NodeDTO> nodes = new LinkedHashMap<>();
    // adjacency list
    private final Map<String, List<Edge>> adj = new HashMap<>();

    public GraphService() {
        // Add nodes with coordinates (approx layout)
        addNode("Connaught Place", 400, 120);
        addNode("Delhi Cantt", 180, 300);
        addNode("Vasant Vihar", 240, 350);
        addNode("India Gate", 460, 200);
        addNode("Rajiv Chowk", 390, 150);
        addNode("New Delhi Railway Station", 420, 90);
        addNode("Chandni Chowk", 520, 90);
        addNode("Red Fort", 560, 130);
        addNode("Kashmere Gate", 520, 40);
        addNode("Delhi University", 480, 20);
        addNode("Karol Bagh", 320, 80);
        addNode("Pitampura", 230, 30);
        addNode("Rohini", 190, 10);
        addNode("Punjabi Bagh", 220, 110);
        addNode("Paschim Vihar", 150, 180);
        addNode("Rajouri Garden", 170, 200);
        addNode("Patel Nagar", 250, 130);
        addNode("Janakpuri", 120, 250);
        addNode("Dwarka", 80, 350);
        addNode("NSUT", 100, 420);
        addNode("DTU", 220, 20);
        addNode("IIT Delhi", 300, 350);
        addNode("AIIMS", 450, 320);
        addNode("Hauz Khas", 360, 360);
        addNode("Saket", 360, 460);
        addNode("Qutub Minar", 330, 520);
        addNode("Lotus Temple", 540, 340);
        addNode("Akshardham", 600, 260);
        addNode("Vasant Kunj", 280, 490);
        addNode("IGI Airport", 120, 480);
        addNode("Gurgaon", 220, 620);
        addNode("Noida", 680, 420);

        // Add edges (bidirectional)
        addEdge("Delhi Cantt", "Vasant Vihar", 7);
        addEdge("Delhi Cantt", "IGI Airport", 9);
        addEdge("Delhi Cantt", "Janakpuri", 6);
        addEdge("Delhi Cantt", "Rajouri Garden", 7);
        addEdge("Delhi Cantt", "NSUT", 10);
        addEdge("Delhi Cantt", "Vasant Vihar", 7);
        addEdge("Delhi Cantt", "Rajiv Chowk", 12);
        addEdge("Delhi Cantt", "Gurgaon", 20);
        addEdge("Vasant Vihar", "Vasant Kunj", 4);
        addEdge("Vasant Vihar", "IIT Delhi", 4);
        addEdge("Connaught Place", "Rajiv Chowk", 1);
        addEdge("Janakpuri", "Paschim Vihar", 4);
        addEdge("Paschim Vihar", "Pitampura", 8);
        addEdge("Rajouri Garden", "Punjabi Bagh", 4);
        addEdge("Punjabi Bagh", "Pitampura", 6);
        addEdge("Rajouri Garden", "Patel Nagar", 6);
        addEdge("Patel Nagar", "Karol Bagh", 3);
        addEdge("Connaught Place", "India Gate", 3);
        addEdge("Connaught Place", "Karol Bagh", 4);
        addEdge("Rajiv Chowk", "New Delhi Railway Station", 2);
        addEdge("New Delhi Railway Station", "Chandni Chowk", 4);
        addEdge("Chandni Chowk", "Red Fort", 2);
        addEdge("Chandni Chowk", "Kashmere Gate", 3);
        addEdge("Kashmere Gate", "Delhi University", 4);
        addEdge("Karol Bagh", "Pitampura", 7);
        addEdge("Pitampura", "Rohini", 6);
        addEdge("Rajouri Garden", "Janakpuri", 5);
        addEdge("Janakpuri", "Dwarka", 8);
        addEdge("Dwarka", "NSUT", 3);
        addEdge("DTU", "Karol Bagh", 10);
        addEdge("IIT Delhi", "AIIMS", 3);
        addEdge("AIIMS", "Hauz Khas", 3);
        addEdge("Hauz Khas", "Saket", 5);
        addEdge("Saket", "Qutub Minar", 6);
        addEdge("Qutub Minar", "Vasant Kunj", 6);
        addEdge("Vasant Kunj", "IGI Airport", 5);
        addEdge("India Gate", "Lotus Temple", 5);
        addEdge("Lotus Temple", "Akshardham", 10);
        addEdge("Akshardham", "Noida", 12);
        addEdge("Connaught Place", "AIIMS", 6);
        addEdge("Connaught Place", "New Delhi Railway Station", 3);
        addEdge("Rajiv Chowk", "Karol Bagh", 3);
        addEdge("Karol Bagh", "Delhi University", 6);
        addEdge("Gurgaon", "IGI Airport", 9);
        addEdge("Gurgaon", "Saket", 20);
        addEdge("Noida", "Akshardham", 12);
        addEdge("Janakpuri", "Rajouri Garden", 6);
        addEdge("Pitampura", "DTU", 9);
        addEdge("IIT Delhi", "Hauz Khas", 6);
        // more connectors to ensure graph is well connected
        addEdge("New Delhi Railway Station", "Red Fort", 4);
        addEdge("Delhi University", "Connaught Place", 7);
        addEdge("Saket", "AIIMS", 7);
        addEdge("Lotus Temple", "Qutub Minar", 8);
    }

    private void addNode(String id, int x, int y) {
        nodes.putIfAbsent(id, new NodeDTO(id, x, y));
        adj.putIfAbsent(id, new ArrayList<>());
    }

    private void addEdge(String a, String b, int weight) {
        addNode(a, 0, 0); // ensures nodes and adj exist if missing
        addNode(b, 0, 0);
        adj.get(a).add(new Edge(b, weight));
        adj.get(b).add(new Edge(a, weight));
    }

    public List<NodeDTO> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<EdgeDTO> getEdges() {
        Set<String> seen = new HashSet<>();
        List<EdgeDTO> out = new ArrayList<>();
        for (String u : adj.keySet()) {
            for (Edge e : adj.get(u)) {
                String a = u, b = e.dest;
                String key = a.compareTo(b) <= 0 ? a + "||" + b : b + "||" + a;
                if (seen.contains(key)) continue;
                seen.add(key);
                out.add(new EdgeDTO(a, b, e.weight));
            }
        }
        return out;
    }

    public List<String> shortestPath(String start, String end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) return Collections.emptyList();

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        for (String node : adj.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
            prev.put(node, null);
        }

        class NodeDist implements Comparable<NodeDist> {
            String node; int d;
            NodeDist(String n,int d){this.node=n;this.d=d;}
            public int compareTo(NodeDist o){return Integer.compare(this.d, o.d);}
        }

        PriorityQueue<NodeDist> pq = new PriorityQueue<>();
        dist.put(start, 0);
        pq.add(new NodeDist(start, 0));
        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            NodeDist nd = pq.poll();
            if (visited.contains(nd.node)) continue;
            visited.add(nd.node);
            if (nd.node.equals(end)) break;
            List<Edge> neighbors = adj.getOrDefault(nd.node, new ArrayList<>());
            for (Edge e : neighbors) {
                if (visited.contains(e.dest)) continue;
                int alt = dist.get(nd.node) + e.weight;
                if (alt < dist.get(e.dest)) {
                    dist.put(e.dest, alt);
                    prev.put(e.dest, nd.node);
                    pq.add(new NodeDist(e.dest, alt));
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String at = end;
        if (prev.get(at) == null && !at.equals(start)) {
            // maybe unreachable
            if (!at.equals(start) && dist.get(at) == Integer.MAX_VALUE) return Collections.emptyList();
        }
        while (at != null) {
            path.addFirst(at);
            at = prev.get(at);
        }
        return new ArrayList<>(path);
    }
}

