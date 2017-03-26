package org.sapia.ned.util.logic;

import org.sapia.ned.bitset.ImmutableBitSet;
import org.sapia.ned.bitset.ImmutableBitSetSupport;

/**
 * An {@link ImmutableBitSet} that implements NOT boolean logic.
 * 
 * @author yduchesne
 *
 */
public class NotBitSet extends ImmutableBitSetSupport {

  private ImmutableBitSet delegate;
  
  public NotBitSet(ImmutableBitSet delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public boolean getBitAt(int index) {
    return !delegate.getBitAt(index);
  }
  
  @Override
  public int size() {
    return delegate.size();
  }
}
