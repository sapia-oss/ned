package org.sapia.ned.bitset;

import org.sapia.ned.util.Check;

/**
 * Implements a view on arbitrary {@link ImmutableBitSet}s.
 * 
 * @author yduchesne
 *
 */
public class BitSetView extends ImmutableBitSetSupport {
  
  private ImmutableBitSet delegate;
  private int             offset;
  private int             size;
  
  public BitSetView(ImmutableBitSet delegate, int offset, int size) {
    Check.isTrue(offset >= 0, "Offset given is negative: %s", offset);    
    Check.isTrue(size >= 0, "Size given is negative: %s", size);
    
    Check.isTrue(
        offset < delegate.size(), 
        "Invalid offset: %s. Must be smaller than the size of the given bitset (which is %s)", 
        offset, delegate.size()
    );
    
    Check.isTrue(
        delegate.size() >= size, 
        "Invalid size %s. Must be equal to or smaller than the size of the given bitset (which is %s)", 
        size, delegate.size()
    );
    
    Check.isTrue(
        offset + size <= delegate.size(), 
        "Invalid size: offset (%s) + size (%s) must equal to or smaller than the size of the given bitset (which is %s)", 
        offset, size, delegate.size()
    );
    
    this.delegate = delegate;
    this.offset   = offset;
    this.size     = size;
  }
  
  @Override
  public boolean getBitAt(int index) {
    Check.isTrue(index >= 0, "Index must be positive. Got %s", index);
    Check.isTrue(index < size, "Invalid index: %s. Index is expected to be in range [0, %s]", index, size - 1);
    return delegate.getBitAt(offset + index);
  }
  
  @Override
  public int size() {
    return size;
  }

}
