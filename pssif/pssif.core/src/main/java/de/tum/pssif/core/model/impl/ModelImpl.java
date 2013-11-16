package de.tum.pssif.core.model.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import de.tum.pssif.core.metamodel.NodeType;
import de.tum.pssif.core.metamodel.impl.EdgeEndImpl;
import de.tum.pssif.core.metamodel.impl.NodeTypeImpl;
import de.tum.pssif.core.model.Edge;
import de.tum.pssif.core.model.Model;
import de.tum.pssif.core.model.Node;
import de.tum.pssif.core.model.operation.CreateEdgeOperation;
import de.tum.pssif.core.model.operation.CreateNodeOperation;
import de.tum.pssif.core.util.PSSIFOption;
import de.tum.pssif.core.util.PSSIFUtil;


public class ModelImpl implements Model {
  private final Multimap<NodeTypeImpl, NodeImpl> nodes = ArrayListMultimap.create();

  @Override
  public Node createNode(CreateNodeOperation operation) {
    NodeImpl result = new NodeImpl(operation.getNodeType());
    nodes.put(operation.getNodeType(), result);
    return result;
  }

  @Override
  public PSSIFOption<Node> findAll(NodeType type) {
    NodeTypeImpl typeImpl = findTypeImpl(type);
    if (typeImpl == null) {
      //TODO throw exception or just find nothing?
      return PSSIFOption.none();
    }
    return PSSIFOption.many(Sets.<Node> newHashSet(nodes.get(typeImpl)));
  }

  @Override
  public Edge createEdge(CreateEdgeOperation createOperation) {
    EdgeImpl newEdge = new EdgeImpl(createOperation.getEdgeTypeImpl());
    //connecten
    //1. resolve node impls
    Multimap<EdgeEndImpl, NodeImpl> connectionsImpl = HashMultimap.create();
    for (EdgeEndImpl ee : createOperation.getConnections().keySet()) {
      ee.getNodeType().apply(this);
    }
    //2. delegate with full map
    //e todo
    return newEdge;
  }

  private NodeTypeImpl findTypeImpl(NodeType nodeType) {
    for (NodeTypeImpl typeImpl : nodes.keySet()) {
      if (PSSIFUtil.areSame(nodeType.getName(), typeImpl.getName())) {
        return typeImpl;
      }
    }
    return null;
  }
}
