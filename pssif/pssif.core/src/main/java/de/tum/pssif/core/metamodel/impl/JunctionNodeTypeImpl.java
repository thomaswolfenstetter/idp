package de.tum.pssif.core.metamodel.impl;

import java.util.Collection;

import com.google.common.collect.Sets;

import de.tum.pssif.core.common.PSSIFOption;
import de.tum.pssif.core.metamodel.ConnectionMapping;
import de.tum.pssif.core.metamodel.EdgeType;
import de.tum.pssif.core.metamodel.NodeType;
import de.tum.pssif.core.metamodel.mutable.MutableJunctionNodeType;
import de.tum.pssif.core.model.Edge;
import de.tum.pssif.core.model.JunctionNode;
import de.tum.pssif.core.model.Model;
import de.tum.pssif.core.model.Node;


public class JunctionNodeTypeImpl extends NodeTypeImpl implements MutableJunctionNodeType {
  public JunctionNodeTypeImpl(String name) {
    super(name);
  }

  @Override
  public JunctionNode create(Model model) {
    return new CreateJunctionNodeOperation(this).apply(model);
  }

  @Override
  public PSSIFOption<Node> apply(Model model, boolean includeSubtypes) {
    PSSIFOption<Node> result = PSSIFOption.none();
    if (includeSubtypes) {
      for (NodeType special : getSpecials()) {
        result = PSSIFOption.merge(result, special.apply(model, includeSubtypes));
      }
    }
    return PSSIFOption.merge(result, new ReadJunctionNodesOperation(this).apply(model));
  }

  @Override
  public PSSIFOption<Node> apply(Model model, String id, boolean includeSubtypes) {
    PSSIFOption<Node> result = PSSIFOption.none();
    if (includeSubtypes) {
      for (NodeType special : getSpecials()) {
        result = PSSIFOption.merge(result, special.apply(model, id, includeSubtypes));
      }
    }
    return PSSIFOption.merge(result, new ReadJunctionNodeOperation(this, id).apply(model));
  }


  @Override
  public Collection<NodeType> leftClosure(EdgeType edgeType, Node node) {
    Collection<NodeType> result = Sets.<NodeType> newHashSet(this);
    for (ConnectionMapping incomingMapping : edgeType.getIncomingMappings(this).getMany()) {
      for (Edge incomingEdge : incomingMapping.applyIncoming(node).getMany()) {
        Node fromConnected = incomingMapping.applyFrom(incomingEdge);
        result.addAll(incomingMapping.getFrom().leftClosure(edgeType, fromConnected));
      }
    }
    return result;
  }

  @Override
  public int junctionIncomingEdgeCount(EdgeType edgeType, Node node) {
    int result = 0;

    for (ConnectionMapping incomingMapping : edgeType.getIncomingMappings(this).getMany()) {
      result += incomingMapping.applyIncoming(node).size();
    }

    return result;
  }


  @Override
  public Collection<NodeType> rightClosure(EdgeType edgeType, Node node) {
    Collection<NodeType> result = Sets.<NodeType> newHashSet(this);
    for (ConnectionMapping outgoingMapping : edgeType.getOutgoingMappings(this).getMany()) {
      for (Edge outgoingEdge : outgoingMapping.applyOutgoing(node).getMany()) {
        Node toConnected = outgoingMapping.applyTo(outgoingEdge);
        result.addAll(outgoingMapping.getTo().rightClosure(edgeType, toConnected));
      }
    }
    return result;
  }

  @Override
  public int junctionOutgoingEdgeCount(EdgeType edgeType, Node node) {
    int result = 0;

    for (ConnectionMapping outgoingMapping : edgeType.getOutgoingMappings(this).getMany()) {
      result += outgoingMapping.applyOutgoing(node).size();
    }

    return result;
  }

  @Override
  public void onIncomingEdgeCreated(Node targetNode, ConnectionMapping mapping, Edge edge) {
    // TODO Auto-generated method stub
    super.onIncomingEdgeCreated(targetNode, mapping, edge);
  }

  @Override
  public void onOutgoingEdgeCreated(Node sourceNode, ConnectionMapping mapping, Edge edge) {
    // TODO Auto-generated method stub
    super.onOutgoingEdgeCreated(sourceNode, mapping, edge);
  }
}