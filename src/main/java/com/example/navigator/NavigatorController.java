package com.example.navigator;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NavigatorController {

    private final GraphService graphService;

    public NavigatorController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/nodes")
    public List<GraphService.NodeDTO> nodes() {
        return graphService.getNodes();
    }

    @GetMapping("/edges")
    public List<GraphService.EdgeDTO> edges() {
        return graphService.getEdges();
    }

    @GetMapping("/shortest-path")
    public List<String> shortestPath(@RequestParam String source, @RequestParam String destination) {
        return graphService.shortestPath(source, destination);
    }
}
