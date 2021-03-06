/*
 * Copyright (C) 2016 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.graph;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * A base implementation of {@link NodeConnections} for undirected networks.
 *
 * @author James Sexton
 * @param <N> Node parameter type
 * @param <E> Edge parameter type
 */
abstract class AbstractUndirectedNodeConnections<N, E> implements NodeConnections<N, E> {
  /**
   * Keys are edges incident to the origin node, values are the node at the other end.
   */
  protected final Map<E, N> incidentEdgeMap;

  protected AbstractUndirectedNodeConnections(Map<E, N> incidentEdgeMap) {
    this.incidentEdgeMap = checkNotNull(incidentEdgeMap, "incidentEdgeMap");
  }

  @Override
  public Set<E> incidentEdges() {
    return Collections.unmodifiableSet(incidentEdgeMap.keySet());
  }

  @Override
  public N oppositeNode(Object edge) {
    return checkNotNull(incidentEdgeMap.get(edge));
  }

  @Override
  public N removeInEdge(Object edge, boolean isSelfLoop) {
    if (!isSelfLoop) {
      return removeOutEdge(edge);
    }
    return null;
  }

  @Override
  public N removeOutEdge(Object edge) {
    checkNotNull(edge, "edge");
    N previousNode = incidentEdgeMap.remove(edge);
    return checkNotNull(previousNode);
  }

  @Override
  public void addOutEdge(E edge, N node) {
    checkNotNull(edge, "edge");
    checkNotNull(node, "node");
    N previousNode = incidentEdgeMap.put(edge, node);
    checkState(previousNode == null);
  }
}
