package org.sapia.ned.util.logic;

import org.sapia.ned.bitset.ImmutableBitSet;
import org.sapia.ned.bitset.ImmutableBitSetSupport;
import org.sapia.ned.util.Check;

/**
 * An {@link ImmutableBitSet} that implements AND boolean logic. An instance of this class
 * takes two bitsets, and performs bit-by-bit AND logic on each respective bits of the given bitsets.
 * <p>
 * Note: both bitsets must have the same size.
 * 
 * @author yduchesne
 *
 */
public class AndBitSet extends ImmutableBitSetSupport {
  
  private ImmutableBitSet a, b;
  
  public AndBitSet(ImmutableBitSet a, ImmutableBitSet b) {
    Check.isTrue(a.size() == b.size(), "Both bitsets must have the same size (got %s vs %s)", a.size(), b.size());
    this.a = a;
    this.b = b;
  }
  
  @Override
  public boolean getBitAt(int index) {
    return a.getBitAt(index) && b.getBitAt(index);
  }
  
  @Override
  public int size() {
    return a.size();
  }

}
